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

function getTestStuff(func)
{
	func(testString);
	return 2;
}

function updateDB()
{
	var formElements = document.settings.elemements;
	var params = ""; 
	
	for (var i = 0; i < formElements.length; ++i)
	{
		var fieldName = formElements[i].name;
		var fieldValue = formElements[i].value;
		
		params += fieldName + '=' + fieldValue + '&';
	}
	
	params = params.substring(0, substring.length - 1);
	
	alert(params);
}

function showAlert(name, imgUrl)
{
	document.getElementById("nameField").innerHTML = name;
	document.getElementById("logo").setAttribute("src", imgUrl);
}

function test()
{
	showAlert("Test Alert", "http://www.iiacanadanationalconference.com/wp-content/uploads/2013/01/test.jpg");
}

function fetch()
{
//	var xmlRequest = getXMLRequest(function(text){
	var testRequest = getTestStuff(function(text){
		var JSONobj =  eval('('+text+')');
		
		for(var i = 0; i < JSONobj.length; ++i)
		{
			showAlert(JSONobj[i].user.display_name,
					  JSONobj[i].user.logo);
		}
	});
//	xmlRequest.open("GET", "Followers", true);
//	xmlRequest.send();
}
