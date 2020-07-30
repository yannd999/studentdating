<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <jsp:include page="head.jsp">
        <jsp:param name="title" value="All Profiles" />
    </jsp:include>

    <style>
        .filterDiv {
            display: none;
        }

        .show {
            display: block;
        }
    </style>

    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="page" value="Students" />
        </jsp:include>

        <main>

            <div id="myBtnContainer">
                <h3>Filter</h3>
                <button id="showAll" class="btn active">Show all</button>
                <button id="showMale" class="btn">Male</button>
                <button id="showFemale" class="btn">Female</button> <%--todo location, age--%>
            </div>
            <br>
            <table class="table">
                <tr>
                    <th>Name</th>
                    <th>Location</th>
                </tr>
                <c:forEach var="person" items="${list}">
                <tr class="filterDiv ${person.sex} ${person.age} ${person.location}">
                    <td><c:out value="${person.firstName} ${person.lastName} ${person.age}(${person.sex})"/></td>
                    <td><c:out value="${person.location}"/></td>
                </tr>
                </c:forEach>
            </table>
        </main>

        <jsp:include page="footer.jsp">
            <jsp:param name="title" value="Home" />
        </jsp:include>
    </body>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script>
        // thanks to w3schools ( https://www.w3schools.com/howto/tryit.asp?filename=tryhow_js_filter_elements )
        $('#showAll').click(function() { filterSelection("all") });
        $('#showMale').click(function() { filterSelection("M") });
        $('#showFemale').click(function() { filterSelection("F") });
        filterSelection("all");

        function filterSelection(c) {
            var x, i;
            x = document.getElementsByClassName("filterDiv");
            if (c === "all") c = "";
            for (i = 0; i < x.length; i++) {
                w3RemoveClass(x[i], "show");
                if (x[i].className.indexOf(c) > -1) w3AddClass(x[i], "show");
            }
        }

        function w3AddClass(element, name) {
            var i, arr1, arr2;
            arr1 = element.className.split(" ");
            arr2 = name.split(" ");
            for (i = 0; i < arr2.length; i++) {
                if (arr1.indexOf(arr2[i]) === -1) {element.className += " " + arr2[i];}
            }
        }

        function w3RemoveClass(element, name) {
            var i, arr1, arr2;
            arr1 = element.className.split(" ");
            arr2 = name.split(" ");
            for (i = 0; i < arr2.length; i++) {
                while (arr1.indexOf(arr2[i]) > -1) {
                    arr1.splice(arr1.indexOf(arr2[i]), 1);
                }
            }
            element.className = arr1.join(" ");
        }

        // Add active class to the current button (highlight it)
        var btnContainer = document.getElementById("myBtnContainer");
        var btns = btnContainer.getElementsByClassName("btn");
        for (var i = 0; i < btns.length; i++) {
            btns[i].addEventListener("click", function(){
                var current = document.getElementsByClassName("active");
                current[0].className = current[0].className.replace(" active", "");
                this.className += " active";
            });
        }
    </script>
</html>
