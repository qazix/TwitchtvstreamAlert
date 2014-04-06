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
	func(testString);
	return 2;
}

function checkFontSize(sizeElem)
{
	var size = sizeElem.value;
	if (size <= 64 && size >= 10)
	{
		sizeElem.style.borderColor = '#FFFFFF';
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
	var extCSS = document.getElementById("extCSS").value;
	isGood = checkUrl(extCSS);
	
	return isGood;
}

function showAlert(name, imgUrl)
{
	document.getElementById("nameField").innerHTML = name + " has followed you!";
	document.getElementById("logo").setAttribute("src", imgUrl);

	document.getElementById("footer").style.height = "64px";
	setTimeout(function hideAlert() {
        document.getElementById("footer").style.height = "0px";
    }, 5000);
}

function test()
{
	showAlert("Test_Alert", "http://www.iiacanadanationalconference.com/wp-content/uploads/2013/01/test.jpg");
}

function fetch()
{
//	var xmlRequest = getXMLRequest(function(text){
	var testRequest = getTestStuff(function(text){
		var JSONobj =  eval('('+text+')');
		
		setTimeout(fetch, 15000 * (JSONobj.length + 1));
		
		for(var i = 0; i < JSONobj.length; ++i)
		{
			setTimeout(showAlert(JSONobj[i].user.display_name,
					   JSONobj[i].user.logo), 15000 * i);
		}
	});
//	xmlRequest.open("GET", "Followers", true);
//	xmlRequest.send();
}
