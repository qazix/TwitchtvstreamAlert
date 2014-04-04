<!--
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
-->
<!DOCTYPE html>
<html>
	<head>
		<title>Twitch.tv StreamAlert</title>
		<link href='http://fonts.googleapis.com/css?family=Bangers' rel='stylesheet' type='text/css'>
		<style>
			html{
				font-family: arial;
				margin: 0;
				padding: 0;
				height: 100%;
			}
			body{
				width: 1000px;
				height: 100%;
				padding: 0 10px;
				margin: 0 auto;
				box-shadow: 0 0 5px 1px #ebebeb;
			}
			header{
				position: relative;
				padding-top: 20px;
				background-image: url('Twitch-tv-logo.png');
				background-repeat: no-repeat;
				background-size: 80px 80px;
				padding-left: 80px;
				background-position: 0 20px;
			}
			header h1{
				margin: 0;
				font-family: 'Bangers', cursive;
				font-size: 80px;
				color: #6441a5;
			}
			p.description{
				padding: 0 10px;
				font-size: 20px;
				text-align: justify;
				text-justify: inter-word;
			}
			footer{
				text-align: center;
				font-style: italic;
				font-size: small;
			}
			button {
				position: absolute;
				top: 50%;
				margin-top: -16px;
				right: 20px;
				vertical-align: 20px;
				height: 32px;
				width: 128px;
				background-color: #6441a5;
				color: white;
				font-family: 'Bangers', cursive;
				border: none;
				border-radius: 4px;
				font-size: 24px;
			}
			button:hover {
				margin-top: -12px;
				cursor: pointer;
			}
		</style>
	</head>
	<body>
		<header>
			<h1>Twitch.tv StreamAlert<h1>
			<button onClick="parent.location='/streamAlert/signIn'">Try it!</button>
		</header>
		<main>
			<p class="description">
				Twitch.tv StreamAlert is a free app created specifically for unpartnered streamers on Twitch.tv. The app allows you to track your followers in realtime, and overlay notifications and sounds right on your stream, to foster audience participation and boost your viewers!
			</p>
			<ul class="features">
				<li>Track followers and subscribers in real time.</li>
				<li>Edit and save your display preferences.</li>
				<li>Use in conjunction with your video capture software to overlay animated notifications.</li>
				<li>Log in with your Twitch.tv account.</li>
			</ul>
		</main>
		<footer>
			&copy; 2014 Braden Beer &amp; Aaron Hanich
		</footer>
	</body>
</html>