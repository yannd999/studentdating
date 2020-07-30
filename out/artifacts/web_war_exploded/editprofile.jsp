<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <jsp:include page="head.jsp">
        <jsp:param name="title" value="Edit Profile" />
    </jsp:include>

    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="page" value="Change Password" />
        </jsp:include>

        <main>
            <h2>Change user whole?</h2>

            <c:if test="${errors.size() > 0}">
                <div class="danger">
                    <ul>
                        <c:forEach var="error" items="${errors}">
                            <li>${error}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <form method="post" action="Controller?action=EditPassword">
                <p>
                    <label for="oldpassword">Old password</label>
                    <input type="password" id="oldpassword" name="oldpassword">
                </p>
                <p>
                    <label for="newpassword">New password</label>
                    <input type="password" id="newpassword" name="newpassword">
                </p>
                <p>
                    <label for="repnewpassword">Repeat new password</label>
                    <input type="password" id="repnewpassword" name="repnewpassword">
                </p>
                <p>
                    <input type="submit" value="Submit">
                    <!-- todo forgot password? -->
                </p>
            </form>
        </main>

        <jsp:include page="footer.jsp">
            <jsp:param name="title" value="Home" />
        </jsp:include>
    </body>
</html>
