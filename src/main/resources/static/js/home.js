window.addEventListener("load", init, false);

var makeRelation = new XMLHttpRequest();

function init() {
    if (document.getElementById("registreren") != null) document.getElementById("registreren").addEventListener("click", showRegistratieForm, false);

    // event listeners
    $('#relation').click(function() { relatie(); });
    $('#registreer').click(function() { registreer(); });
    $('#errors').innerHTML = "";
}


function relatie() {
    var a = document.getElementById("itemsA");
    var personA = a.options[a.selectedIndex].value;

    var b =  document.getElementById("itemsB");
    var personB = b.options[b.selectedIndex].value;

    if (personA === personB) alert("You must choose two different people to form a relation."); //todo relationship?
    else {
        $.post("/relation", {personA: personA, personB: personB}); //todo myself as a person??

/*        $.ajax({
            type: "POST",
            url: "http://localhost:8080/relation",
            contentType: 'application/json',
            data: JSON.stringify(personA, personB),
            dataType: 'json',
            succes: function(result) {
                if (result.status == "succes: " + result) console.log("Post nr relation is a success"); //todo
                else console.log("fail: " + result);
            },
            error: function(e) {
                console.log("error: ", e);
            }
        });*/
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
