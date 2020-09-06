window.onload = function () {
    connect();
    var inputButtons = document.getElementsByClassName("button");
    for(var i = 0; i < inputButtons.length;i++){
        inputButtons[i].addEventListener("click", function () {
            sendCommenter(this.id)//de id = 1... id html
        })
    }
};
window.addEventListener("unload",function () {
    disconnect();
});

var stompClient = null;

function connect() {
    var socket = new SockJS('/stompsocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/blogtopic', function (bodyComment) {
            showCommenter(JSON.parse(bodyComment.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function sendCommenter(id) {
    var comment = document.getElementById("comment" + id).value;
    var naam = document.getElementById("naam" + id).value;
    var rating = document.getElementById("rating" + id).value;
    var json = JSON.stringify({'id': id, 'naam': naam, 'comment':comment,'rating':rating});
    stompClient.send("/application/hello", {}, json);
    document.getElementById("comment" + id).value = ""; //clear all fields
    document.getElementById("naam" + id).value = "";
    document.getElementById("rating" + id).value = "";
}

//1. comment displayen
//2. alles parsen, daarna in de html steken
function showCommenter(commenter) { //commenter krijg je binnen in JSON formaat
    var id = commenter.id;

    var string = "Comment: " + commenter.comment + " | " + "Rating: " + commenter.rating + " | " +"By: " + commenter.naam;

    var li = document.createElement("li");
    li.appendChild(document.createTextNode(string));

    document.getElementById("output" + id).appendChild(li);

}

document.getElementById("registreerbutton").addEventListener("click", checkPasswordsMatch, false);

function checkPasswordsMatch(){
    var pass1 = document.getElementById("password2").value;
    var pass2 = document.getElementById("repeatPassword").value;

    if(pass1 !== pass2){
        alert("Passwords don't match!")
    }
}
