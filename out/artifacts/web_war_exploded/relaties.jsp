<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <jsp:include page="head.jsp">
        <jsp:param name="title" value="All Relations" />
    </jsp:include>

    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="page" value="Relaties" />
        </jsp:include>

        <main>
            <table class="table">
                <tr>
                    <th>A</th>
                    <th>B</th>
                </tr>
                <c:forEach var="relation" items="${relations}">
                <tr>
                    <td><c:out value="${relation.lover_1}"/></td>
                    <td><c:out value="${relation.lover_2}"/></td>
                </tr>
                </c:forEach>
            </table>
        </main>

        <jsp:include page="footer.jsp">
            <jsp:param name="title" value="Home" />
        </jsp:include>
    </body>
</html>
