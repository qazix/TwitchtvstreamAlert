<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.Map" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Twitch.tv StreamAlert</title>
		<script type="text/javascript" src="followers.js"></script>
		<link href='http://fonts.googleapis.com/css?family=Bangers' rel='stylesheet' type='text/css'>
		<link href='css/default.css' rel='stylesheet' type='text/css'>
		<style> /* User Style Settings Created at page load from DB */

		</style>
		<link href='${ExtCSS}' rel='stylesheet' type='text/css'> 
	</head>
	<body onload="timer()">
		<header>
			<h1>StreamAlert<h1>
			<h2>ReynoldsCahoon</h2>
			<form id="settings">
				<fieldset id="style">
					<input type="color" name="chroma" value="${ChromaColor}" />
					<input type="color" name="background" value="${BGColor}" />
					<input type="color" name="fontcolor" value="${FontColor}" />
					<input type="number" name="fontsize" min="10" max="64" value="${FontSize}" />
					<input type="url" name="externalcss" value="${ExtCSS}"/>
				</fieldset>
				<fieldset id="notif">
					<label for="userpicture">Show User Picture</label>
					<input type="checkbox" name="userpicture" value="true" />
					<button>Test Alert</button>
				</fieldset>
			</form>
		</header>
		<footer>
			Test Notification
		</footer>
	</body>
</html>