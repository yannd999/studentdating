<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <jsp:include page="head.jsp">
        <jsp:param name="title" value="Error" />
    </jsp:include>
<%--
    //todo header, title
--%>

    <body>
        <header>
            <nav>
                <ul>
                    <li style="float:left;"><a href="Controller">Home</a></li>
                </ul>
            </nav>
        </header>
        <main><br>
            <p style="margin-top: 5rem;">Deze pagina bestaat niet.</p>
        </main>
    </body>
</html>
