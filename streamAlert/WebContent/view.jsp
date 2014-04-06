<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.Map" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<title>StreamAlert</title>
		<script type="text/javascript" src="followers.js"></script>
		<link href='http://fonts.googleapis.com/css?family=Bangers' rel='stylesheet' type='text/css'>
		<!-- IE -->
		<link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
		<!-- other browsers -->
		<link rel="icon" type="image/x-icon" href="favicon.ico" />
		<link href='css/default.css' rel='stylesheet' type='text/css'>
		<style> /* User Style Settings Created at page load from DB */
			body{
				background-color: #${ChromaColor};
			}
			footer{
				background-color: #${BGColor}; 
				font-size: ${FontSize}; 
				color: #${FontColor};
			}
			header h2{
				background-image: url(${picURL});
			}
		</style>
		<link href='${ExtCSS}' rel='stylesheet' type='text/css'> 
	</head>
	<body id="body" onload="fetch()">
		<header>
			<h1>StreamAlert</h1>
			<h2>${name}</h2>
			<form id="settings" method="POST" onsubmit="return updateDB()" action="/streamAlert/streamDB">
				<fieldset id="style">
					<label for="chroma">Chroma Color</label>
					<input type="color" id="chroma" name="chroma" value="${ChromaColor}" onchange="checkHex(this)" />
					<label for="background">Alert Background Color</label>
					<input type="color" id="background" name="background" value="${BGColor}" onchange="checkHex(this)" />
					<label for="fontcolor">Font Color</label>
					<input type="color" id="fontcolor" name="fontcolor" value="${FontColor}" onchange="checkHex(this)" />
					<label for="fontsize">Font Size</label>
					<input type="number" id="fontsize" name="fontsize" min="10" max="64" value="${FontSize}" onchange="checkFontSize(this)" />
					<label for="externalcss">External CSS</label>
					<input type="url" id="externalcss" name="externalcss" value="${ExtCSS}" onchange="checkUrl(this)" placeholder="http://www.yourwebsite.com/style.css" />
					<input type="submit" value="Save Settings" />
				</fieldset>
				<fieldset id="notif">
					<label for="userpicture">Show User Picture</label>
					<input type="checkbox" name="userpicture" value="true" />
					<input type="button" value="Test Alert" onclick="test()" />
				</fieldset>
			</form>
		</header>
		<footer id="footer">
			<img id="logo" />
			<p id="nameField"></p>
		</footer>
	</body>
</html>