<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isErrorPage="true" %>
<!DOCTYPE html>
<html>
	<jsp:include page="head.jsp">
		<jsp:param name="title" value="Exception" />
	</jsp:include>

	<body>
		<header role="banner">
			<img alt="Toscane" src="images/banner.png">
		</header>
		<main>
			<article>
			<h1>Oeps!</h1>
			</article>
			<p>You caused a ${pageContext.exception} on the server !</p>
		</main>
    </body>
</html>
