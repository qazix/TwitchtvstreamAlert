<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.Map" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="followers.js"></script>
<title>User Name</title>
</head>
<body onload="timer()">
	<h1>After a lot of work this is your user name "${name}" you have ${numFollowers} friends</h1>
	<ul>
		<c:forEach items="${followers}" var="follower">
			<li>${follower}<img src="${follower.user.logo}" /></li>
		</c:forEach>
	</ul>
	<img id=logo />
	<div id="nameField"></div>
</body>
</html>