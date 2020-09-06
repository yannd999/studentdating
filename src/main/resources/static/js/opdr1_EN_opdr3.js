window.addEventListener("load", init, false);

var getStatusRequest = new XMLHttpRequest();
var newStatusRequest = new XMLHttpRequest();
//var chatRequest = new XMLHttpRequest();

function init() {
    // event listeners
    for (var j = 0; j < document.getElementsByClassName("persoon").length; j++) {
        document.getElementsByClassName("persoon")[j].addEventListener("click", function (evt) {
            chatMetPersoon(evt.target.id);
        }, false);
    }

    getChats();
    //getStatus();
}


////////////////////////////////////todo test met xml http
function getStatus() {
    getStatusRequest.open("GET", "Controller?action=Status", true);
    getStatusRequest.onreadystatechange = function() {
        if (getStatusRequest.readyState === 4 && getStatusRequest.status === 200) {
            var response = JSON.parse(getStatusRequest.responseText);
            document.getElementById("status").innerHTML = response.status;
            setTimeout(getStatus, 5000);
        }
    };
    getStatusRequest.send(null);
}

function setStatus() {
    var status = document.getElementById("statustext").value;
    if (status !== "") {
        document.getElementById("statustext").value = "";
        var request = "status=" + encodeURIComponent(status);
        newStatusRequest.open("POST", "Controller?action=Status", true);
        newStatusRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        newStatusRequest.send(request);
    }
    // quick update (stap 1.2: Als je je status	aanpast	dan	moet dat ook onmiddellijk getoond worden)
    getStatus();
}////////////////////////// todo

// chatten
// chat venster openen
function chatMetPersoon(naam) {
    // geen twee chats openen met zelfde persoon
    if (document.getElementById('div' + naam) === null) { //todo.innerHTML === "") { //todo werkt niet met: length === 0) {
        var divId = "div" + naam;
        var div2Id = "div2" + naam;
        var inputId = "bericht" + naam;
        var buttonId = "button" + naam;
        var html =
            "<div id=" + divId + " style=\"margin-top:5rem;border:2px dashed #000;padding:2rem;border-radius:1.2rem;\">" +
            "<h3>Chat met " + naam + "</h3>" + "<br>" + //todo chat met email not good
            "<div class='div2' id=" + div2Id + ">" +
            "</div>" + "<br>" +
            "<input type='text' id=" + inputId + ">" +
            "<input type='button' value='Send' id=" + buttonId + ">" +
            "</div>";

        $('#chat').append(html);
        document.getElementById("button" + naam).addEventListener("click", function() {sendMessage(naam);}, false);
    }
}

function sendMessage(id) {
    var bericht = document.getElementById("bericht" + id).value;
    var myName = $('#userName').text();
    if (bericht !== "") {
        document.getElementById("bericht" + id).value = "";
        $.post("/createChat", {zender: myName, ontvanger: id, bericht: bericht});

       /* var request = "zender=" + encodeURIComponent(myName) + "&ontvanger=" + encodeURIComponent(id) + "&bericht=" + encodeURIComponent(bericht);
        chatRequest.open("POST", "/createChat", true);
        chatRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        chatRequest.send(request);*/
    }
    // quick update
    getChats();
}

function getChats() {
    if ($('#userName').text() !== "") {
        $.get("/chats", function (result) {
            writeChats(result);
        });
    }
}

function writeChats(chats) {
    // vorige berichten wegdoen //todo ipv heel div te deleten delete enkel message div (niet input text) door op te roepen met klas
    if (chats.length !== 0) {
        for (var j = 0; j < document.getElementsByClassName("div2").length; j++) {
            document.getElementsByClassName("div2")[j].innerHTML = "";
        }
    }
    var myName = $('#userName').text();
    for (var i = 0; i < chats.length; i++) {
        // als ik bericht krijg -> open chat venster met zender
        if (chats[i]["bericht"] === "") chatMetPersoon(chats[i]["zender"]);
        // bericht schrijven
        if (myName === chats[i]["ontvanger"]) {
            var zender = (chats[i]["zender"]);
            chatMetPersoon(zender);
            var pzender = document.createElement("p");
            pzender.appendChild(document.createTextNode(chats[i]["bericht"]));
            pzender.style.padding = "1rem";
            pzender.style.border = "1px solid black";
            pzender.style.borderRadius = "1rem";
            //pzender.appendChild(document.createTextNode(zender + ": " + chats[i]["bericht"]));
            document.getElementById("div2" + zender).append(pzender); //"<p>" + zender + ": " + chats[i]["bericht"] + "</p>");
        }
        // als ik bericht heb gestuurd -> venster openen met ontvanger
        else if (myName === chats[i]["zender"]) {
            var ontvanger = chats[i]["ontvanger"];
            chatMetPersoon(ontvanger);
            var pontvanger = document.createElement("p");
            pontvanger.appendChild(document.createTextNode(chats[i]["bericht"]));
            pontvanger.style.padding = "1rem 1rem 1rem 20%";
            pontvanger.style.borderRadius = "1rem";
            pontvanger.style.backgroundColor = "#32383e";
            pontvanger.style.color = "white"; //todo choose chatcolor
            pontvanger.style.width = "60%";
            document.getElementById("div2" + ontvanger).append(pontvanger);
            //document.getElementById("div2" + ontvanger).append(pontvanger);//"<p>" + ontvanger + ": " + chats[i]["bericht"] + "</p>");
        }
    }
    setTimeout(getChats, 5000);
}
