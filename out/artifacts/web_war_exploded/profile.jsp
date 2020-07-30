<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <jsp:include page="head.jsp">
        <jsp:param name="title" value="Profile" />
    </jsp:include>

    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="page" value="My Profile" />
        </jsp:include>

        <main>
            <h2><c:out value="${user.firstName} ${user.lastName}"/></h2>

            <c:choose>
                <c:when test="${user.relations.size() == 0}">
                    <h5>No matches yet</h5>
                </c:when>
                <c:otherwise>
                    <table>
                        <tr>
                            <th>Matches</th>
                            <th></th>
                        </tr>
                        <c:forEach var="r" items="${user.relations}">
                            <tr>
                                <td><c:out value="${r.lover_1}"/> met <c:out value="${r.lover_2}"/></td>
                                <td><a href="Controller?action=Chat">Chat</a></td> <%--//todo chat met persoon--%>
                            </tr>
                        </c:forEach>
                    </table>
                </c:otherwise>
            </c:choose>

            <p><a href="Controller?action=EditPassword">Edit profile</a></p>
        </main>

        <jsp:include page="footer.jsp"/>
    </body>
</html>
