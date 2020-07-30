<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<header>
	<h1>Student Dating</h1>
	<h2>${param.page}</h2>

	<c:if test="${user != null}">
	<nav>
		<ul>
			<li><a href="Controller?action=Home">Home</a></li> <%--// todo nuttig om extra home te hebben ?--%>
			<li><a href="Controller?action=Profile">My Profile</a></li>
			<li><a href="Controller?action=Profiles">Students</a></li>
			<c:if test="${user.email == 'admin@gmail.com'}">
			<li><a href="Controller?action=RelationPage">Relations</a></li>
			</c:if>
			<li><a href="Controller?action=ChatPage">Chat</a></li>
			<li><a href="Controller?action=LogOut">Log Out</a></li>
		</ul>
	</nav>
	</c:if>
</header>
