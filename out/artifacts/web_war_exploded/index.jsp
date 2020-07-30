<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <jsp:include page="head.jsp">
        <jsp:param name="title" value="Home" />
    </jsp:include>

    <body>
        <c:if test="${user == null}">
            <jsp:include page="header.jsp">
                <jsp:param name="page" value="Log In" />
            </jsp:include>
        </c:if>

        <c:if test="${user != null}">
            <jsp:include page="header.jsp">
                <jsp:param name="page" value="Home" />
            </jsp:include>
        </c:if>

        <main>
            <c:if test="${errors.size() > 0}">
                <div class="danger">
                    <ul>
                        <c:forEach var="error" items="${errors}">
                            <li>${error}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <c:choose>
                <c:when test="${user != null}">
                    <div id="welcome" class="success">
                        <c:if test="${welcome != null}"><p>Welcome <c:out value="${welcome}"/>!</p></c:if>
                    </div>

                    <h3>Relatie vormen</h3>

                    <label for="itemsA">Persoon A:</label>
                    <select id="itemsA">
                    <c:forEach var="person" items="${persons}">
                        <option value="<c:out value="${person.firstName} ${person.lastName}"/>"><c:out value="${person.firstName} ${person.lastName}"/></option>
                    </c:forEach>
                    </select>

                    <label for="itemsB">Persoon B:</label>
                    <select id="itemsB">
                        <c:forEach var="person" items="${persons}">
                            <option id="keuzeB" value="<c:out value="${person.firstName} ${person.lastName}"/>"><c:out value="${person.firstName} ${person.lastName}"/></option>
                        </c:forEach>
                    </select>

                    <input type="button" id="relation" value="Toevoegen"/> <%--todo form of href, javascript? angular? web4--%>

                    <p>See all <a href="Controller?action=Profiles">profiles</a></p>
                </c:when>
                <c:otherwise>
        <%--
                    //todo aparte pagina?
        --%>
                <div id="success" class="success">
                </div>

                <form method="post" action="Controller?action=LogIn" id="login">
                    <p>
                        <label for="email">Your email</label>
                        <input type="email" id="email" name="email" value="test@mail.com">
                    </p>
                    <p>
                        <label for="password">Your password</label>
                        <input type="password" id="password" name="password" value="t">
                    </p>
                    <p>
                        <input type="submit" id="loginbutton" value="Log in">
            <!-- todo forgot password? -->
                    </p>
                </form>
                <br>
                <p>Nog geen account? <a id="registreren">Registreer</a></p>

                <div id="errors" class="danger">
                </div>
        <%--
                <form method="post" action="Controller?action=Registreer" id="registratieForm" style="display:none;">--%>
                <div id="registratieForm" style="display:none;">
                    <p>
                        <label for="lastName">Name</label>
                        <input type="text" id="lastName" name="lastName" value="<c:out value="${lastname}"/>">
                    </p>
                    <p>
                        <label for="firstName">First name</label>
                        <input type="text" id="firstName" name="firstName" value="<c:out value="${firstname}"/>">
                    </p>
                    <p>
                        <label for="remail">Email</label>
                        <input type="email" id="remail" name="email" value="<c:out value="${email}"/>"> <!-- todo values -->
                    </p>
                    <p>
                        <label for="age">Age</label>
                        <input type="number" min="15" max="120" id="age" name="age" value="<c:out value="${age}"/>">
                    </p>
                    <p>
                        <label for="rgeslacht">Sex (M/F)</label>
                        <input type="text" id="rgeslacht" name="sex" value="<c:out value="${sex}"/>">
                    </p>
                    <p>
                        <label for="location">Location</label>
                        <input type="text" id="location" name="location" value="<c:out value="${location}"/>">
                    </p>
                    <p>
                        <label for="rpassword">Password</label>
                        <input type="password" id="rpassword" name="password">
                    </p>
                    <p>
                        <label for="rherpassword">Repeat password</label>
                        <input type="password" id="rherpassword" name="reppassword">
                    </p>
                    <p>
                        <input type="submit" value="Registreer" id="registreer">
                    </p>
                </div>
                    <%--
                </form>--%>
                </c:otherwise>
            </c:choose>
        </main>

        <jsp:include page="footer.jsp"/>
    </body>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="js/home.js"></script>
</html>
