<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <jsp:include page="head.jsp">
        <jsp:param name="title" value="Chat" />
    </jsp:include>

    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="page" value="Chat" />
        </jsp:include>

        <main>
            <c:if test="${user != null}">

                <h1 id="userName" style="display: none;"><c:out value="${user.firstName} ${user.lastName}"/></h1>

                <div class="grid-container">
                    <table class="table item1">
                        <tr>
                            <th>Chat with</th>
                        </tr>
                        <c:forEach var="person" items="${list}">
                            <tr class="filterDiv ${person.sex} ${person.age} ${person.location}">
                                <td id="<c:out value="${person.firstName} ${person.lastName}"/>" class="link"><c:out value="${person.firstName} ${person.lastName} ${person.age}(${person.sex})"/></td>
                            </tr>
                        </c:forEach>
                    </table>

                    <div class="item1" id="chat"></div>
                </div>
            </c:if>
        </main>

        <jsp:include page="footer.jsp"/>
    </body>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="js/home.js"></script>
</html>
