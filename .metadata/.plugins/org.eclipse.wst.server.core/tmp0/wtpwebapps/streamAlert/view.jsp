<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Name</title>
</head>
<body>
	<h1>After a lot of work this is your user name "${name}"</h1>
	<ul>
      <c:forEach items="${followers}" var="follower">
         <li>${follower}</li>
      </c:forEach>
   </ul>

</body>
</html>