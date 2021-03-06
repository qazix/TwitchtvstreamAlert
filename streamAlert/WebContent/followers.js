/**
 * 
 */
var testString = '[{"created_at":"2014-03-27T21:45:13Z","_links":{"self":"https://api.twitch.tv/kraken/users/reynoldscahoon/follows/channels/streamalert"},"user":{"display_name":"ReynoldsCahoon","_id":20795283,"name":"reynoldscahoon","type":"user","bio":"I\'m 27, have a wife, a Corgi, and am studying web design.","created_at":"2011-02-28T01:20:11Z","updated_at":"2014-03-30T06:20:11Z","logo":"http://static-cdn.jtvnw.net/jtv_user_pictures/reynoldscahoon-profile_image-cc337f384de39eb2-300x300.jpeg","_links":{"self":"https://api.twitch.tv/kraken/users/reynoldscahoon"}}},{"created_at":"2014-03-21T22:05:38Z","_links":{"self":"https://api.twitch.tv/kraken/users/mistershmi/follows/channels/streamalert"},"user":{"display_name":"MisterShmi","_id":49795726,"name":"mistershmi","type":"user","bio":null,"created_at":"2013-10-05T03:48:47Z","updated_at":"2014-02-18T02:36:05Z","logo":"http://static-cdn.jtvnw.net/jtv_user_pictures/mistershmi-profile_image-9a19c5e38ef3f94a-300x300.jpeg","_links":{"self":"https://api.twitch.tv/kraken/users/mistershmi"}}},{"created_at":"2014-03-21T22:02:27Z","_links":{"self":"https://api.twitch.tv/kraken/users/truebluelua/follows/channels/streamalert"},"user":{"display_name":"truebluelua","_id":48886511,"name":"truebluelua","type":"user","bio":"Loves corgis!","created_at":"2013-09-12T08:27:36Z","updated_at":"2014-03-21T21:57:16Z","logo":"http://static-cdn.jtvnw.net/jtv_user_pictures/truebluelua-profile_image-411d89b65f2d423a-300x300.jpeg","_links":{"self":"https://api.twitch.tv/kraken/users/truebluelua"}}}]';

function getXMLRequest(func)
{
	var xmlRequest = new XMLHttpRequest();
      xmlRequest.onreadystatechange = function() {
         if (xmlRequest.readyState == 4 && xmlRequest.status == 200)
         {
            func(xmlRequest.responseText);
         }
      };
   
   return xmlRequest;   
}

function timer()
{
}

function getTestStuff(func)
{
//	func(testString);
	func("");
	return 2;
}

function checkFontSize(sizeElem)
{
	var size = sizeElem.value;
	if (size <= 64 && size >= 10)
	{
		sizeElem.style.borderColor = '#FFFFFF';
		document.getElementById("footer").style.fontSize = size + "px";
		return true;
	}
	else
	{
		sizeElem.style.borderColor = '#FF0000';
		return false;
	}
}

function checkUrl(urlElem)
{
	var check = urlElem.value;
	var urlregex = /^(((https|http):\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$)?/;
	if (urlregex.test(check))
	{
		urlElem.style.borderColor = '#FFFFFF';
		return true;
	}
	else
	{
		urlElem.style.borderColor = '#FF0000';
		return false;
	}
}

function checkHex(hexElem)
{
	var check = hexElem.value;
	var colorregex = /^#?[A-Fa-f0-5]{6}/;
	var isGood = colorregex.test(check);
	if(isGood)
	{
		if(check.charAt(0) != '#')
			check = '#' + check;
			
		if(hexElem.name == "chroma")
			document.getElementById("body").style.backgroundColor = check;
		else
		{
			if(hexElem.name == "background")
				document.getElementById("footer").style.backgroundColor = check;
			else
				document.getElementById("footer").style.color = check;
		}
			
		hexElem.style.borderColor = '#FFFFFF';
		hexElem.value = check;
		return true;
	}
	else
	{
		hexElem.style.borderColor = '#FF0000';
		return false;
	}
}

function updateDB()
{
	var extCSS = document.getElementById("externalcss");
	var chroma = document.getElementById("chroma");
	var bgColor = document.getElementById("background");
	var fontcolor = document.getElementById("fontcolor");
	var fontsize = document.getElementById("fontsize");
	var soundfile = document.getElementById("soundfile");

	var isGood = checkUrl(extCSS) && checkHex(chroma) && checkHex(bgColor) &&
			 		checkHex(fontcolor) && checkFontSize(fontsize) && checkUrl(soundfile);
	
	if (isGood)
	{
		//chroma.value = chroma.value.substring(1);
		//bgColor.value = bgColor.value.substring(1);
		//fontcolor.value = fontcolor.value.substring(1);
		
		return true;
	}
	else
		return false;
}

function showAlert(name, imgUrl)
{
	document.getElementById("nameField").innerHTML = name + " has followed you!";
	if(imgUrl == "" || imgUrl == null)
	{
		document.getElementById("logo").setAttribute("src",
				"http://static-cdn.jtvnw.net/jtv_user_pictures/xarth/404_user_150x150.png");
	}
	else
	{
		document.getElementById("logo").setAttribute("src", imgUrl);
	}
	document.getElementById("music").play();
	document.getElementById("footer").style.height = "64px";
	setTimeout(function hideAlert() {
        document.getElementById("footer").style.height = "0px";
    }, 5000);
}

function test()
{
	showAlert("Ash Ketchum", "ash.jpg");
}

function load()
{
	var soundfile = document.getElementById("soundfile").value;
	if (soundfile != null && soundfile != "")
	{
		document.getElementById("music").setAttribute("src", soundfile);
	}
	fetch();
}

function fetch()
{
	var xmlRequest = getXMLRequest(function(text){
//	var testRequest = getTestStuff(function(text){
		if (text != "null")
		{
			JSONobj =  eval('('+text+')');
			setTimeout(fetch, 8000 * (JSONobj.length) + 15000);
		
			for(var i = 0; i < JSONobj.length; ++i)
			{
				setTimeout(showAlert, 8000 * i, 
						JSONobj[i].user.display_name,
						JSONobj[i].user.logo);
			}
		}
		else
			setTimeout(fetch, 15000);
	});
	xmlRequest.open("GET", "Followers", true);
	xmlRequest.send();
}

function updateVolume(){
	document.getElementById("music").volume = document.getElementById("volume").value * .01;
}