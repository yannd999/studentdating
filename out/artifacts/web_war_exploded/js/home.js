window.addEventListener("load", init, false);

function init() {
if (document.getElementById("registreren") != null) document.getElementById("registreren").addEventListener("click", showRegistratieForm, false);
var makeRelation = new XMLHttpRequest();

// event listeners
for (var j = 0; j < document.getElementsByClassName("link").length; j++) {
    document.getElementsByClassName("link")[j].addEventListener("click", function (evt) {
        chatMetPersoon(evt.target.id);
    }, false);
}//todo chat

$('#relation').click(function() { relatie(); });
$('#registreer').click(function() { registreer(); });

    $('#errors').innerHTML = "";
    getChats();
}

function relatie() {
    var a = document.getElementById("itemsA");
    var personA = a.options[a.selectedIndex].value;

    var b =  document.getElementById("itemsB");
    var personB = b.options[b.selectedIndex].value;

    if (personA === personB) alert("You must choose two different people to form a relation."); //todo relationship?
    else {
        $.post("Controller?action=Relation", {personA: personA, personB: personB}); //todo myself as a person??
    }
}

function showRegistratieForm() {
    $('#registratieForm').toggle();
}

function registreer() {
    // empty errors
    $('#errors').empty();//todo

    var lastName = document.getElementById("lastName").value;// $('lastName').val();
    if (lastName === "") schrijfFout("Last name is blank");
    var firstName = document.getElementById("firstName").value;
    if (firstName === "") schrijfFout("First name is blank");
    var email = document.getElementById("remail").value;
    if (email === "") schrijfFout("Email is empty"); //todo match regex email
    var age = document.getElementById("age").value;
    if (age === "" || age < 15) schrijfFout("Please fill in (valid) age");
    var geslacht = document.getElementById("rgeslacht").value;
    if (geslacht === "" || geslacht !== "M" && geslacht !== "F") schrijfFout("Sex is invalid"); //todo lgbt?
    var location = document.getElementById("location").value;
    if (location === "") schrijfFout("Fill in your location");
    var rpassword = document.getElementById("rpassword").value;
    if (rpassword === "") schrijfFout("You forgot password");
    var rherpassword = document.getElementById("rherpassword").value;
    if (rherpassword === "") schrijfFout("Repeat your password");
    if (rpassword !== "" && rpassword !== rherpassword) schrijfFout("Passwords do not match");
    // erase password
    document.getElementById("rpassword").value = "";
    document.getElementById("rherpassword").value = "";
    // send (not null) parameters
    if (document.getElementsByClassName("regfout").length === 0) {
        $.post("Controller?action=Registreer", {
            lastName: lastName,
            firstName: firstName,
            email: email,
            age: age,
            geslacht: geslacht,
            location: location,
            rpassword: rpassword,
            rherpassword: rherpassword
        });
        document.getElementById("lastName").value = "";
        document.getElementById("firstName").value = "";
        document.getElementById("remail").value = "";
        document.getElementById("age").value = "";
        document.getElementById("rgeslacht").value = "";
        document.getElementById("location").value = "";

        var success = document.createElement("p");
        success.appendChild(document.createTextNode("Account created successfully"));
        $('#success').append(success);
    }//todo myself as a person??

   /* $('lastName').empty();
    $('firstName').empty();
    $('remail').empty();
    $('age').empty();
    $('rgeslacht').empty();
    $('location').empty();*/
}

function schrijfFout(fout) {
    var p = document.createElement("p");
    p.className = "regfout";
    p.appendChild(document.createTextNode(fout));
    $('#errors').append(p);
}

// chatten

// chat venster openen
function chatMetPersoon(naam) {
    // geen twee chats openen met zelfde persoon
    if ($('#div' + naam.split(" ")[0]).length === 0) {
        var divId = "div" + naam;
        var div2Id = "div2" + naam;
        var inputId = "bericht" + naam;
        var buttonId = "button" + naam;
        var html =
            "<div id=" + divId + " style='margin-top:5rem;border:2px dashed #000;padding:2rem;border-radius:1.2rem;'>" +
            "<h3>Chat met " + naam + "</h3>" + "<br>" + //todo chat met email not good
            "<div class='div2' id=" + div2Id + ">" +
            "</div>" + "<br>" +
            "<input type='text' id=" + inputId + ">" +
            "<input type='button' value='Send' id=" + buttonId + ">" +
            "</div>";

        $('#chat').append(html);
        $('#button' + naam.split(" ")[0]).click(function () {
            sendMessage(naam);
        });
    }
}

function sendMessage(id) {
    var bericht = $('#bericht' + id.split(" "[0])).val();
    var myName = $('#userName').text();
    if (bericht !== "") {
        $.post("Controller?action=Chat", {zender: myName, ontvanger: id, bericht: bericht});
        $('#bericht' + id).val("");
    }
    // quick update
    getChats();
}

function getChats() {
    if ($('#userName').text() !== "") {
        $.get("Controller?action=Chat", function (result) {
            writeChats(result["chats"]);
        });
    }
}

function writeChats(chats) {
    // vorige berichten wegdoen
    if (chats.length !== 0) $('#chat').empty();
    var myName = $('#userName').text();
    for (var i = 0; i < chats.length; i++) {
        // als ik bericht krijg -> open chat venster met zender
        if (chats[i]["bericht"] === "") chatMetPersoon(chats[i]["zender"]);
        // bericht schrijven
        if (myName === chats[i]["ontvanger"]) {
            chatMetPersoon(chats[i]["zender"]);
            $('#div2' + chats[i]["zender"].split(" ")[0]).append("<p>" + chats[i]["zender"].split(" ")[0] + ": " + chats[i]["bericht"] + "</p>");
        }
        // als ik bericht heb gestuurd -> venster openen met ontvanger
        else if (myName === chats[i]["zender"]) {
            chatMetPersoon(chats[i]["ontvanger"]);
            $('#div2' + chats[i]["ontvanger"].split(" ")[0]).append("<p>" + chats[i]["zender"].split(" ")[0] + ": " + chats[i]["bericht"] + "</p>");
        }
    }
    setTimeout(getChats, 5000);
}
/*

// opdracht 3.2 zoekfunctie vrienden

function sendSearch() {
    var name = $('#srcfriendtext').val();
    if (name !== "") $.post("Controller?action=SearchFriend", {vriend: name}, function(res) {foundFriends(res["vrienden"]);});
    else if (name === "") $('#found').empty();
    $('#srcfriendtext').val("");
}

function foundFriends(vrienden) {
    $('#found').text("");
    if (vrienden.length !== 0) {
        var gevonden = "<p>Gevonden:</p>";
        for (var i = 0; i < vrienden.length; i++) {
            gevonden +=
                "<p>" + vrienden[i] + "</p>";
        }
        $('#found').append(gevonden);
    }
    else $('#found').append("<p>Niet gevonden</p>");
}
*/
