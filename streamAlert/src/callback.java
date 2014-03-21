

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class callback
 */
@WebServlet("/callback")
public class callback extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	/**
	 * The authentication key from Twitch
	 */
	private String mAuthKey;
	private String mUser;
	private int mNumFollowers;
	private List<Object> mFollowers;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public callback() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getScheme() + "://" + request.getServerName() + ":" + 
				request.getServerPort() + request.getRequestURI();
		//System.out.println(requestURI);
		int lastSlashIndex = requestURI.lastIndexOf("/");

		String cbURL = requestURI.substring(0, lastSlashIndex) + "/callback";
		
		//this is the code autheticating our client
		String code = request.getParameter("code");
//		response.getWriter().println("Your code is " + code + "<br />");
		
		if (code != "" && code != null)
		{
			try 
			{
				//Gets the JSON object as a map which contains the authentication key
				Map<String, Object> authkeyMap = getAuthMap(code, cbURL);
				mAuthKey = authkeyMap.get("access_token").toString();
				
				//Get the User JSON object
				Map<String, Object> userMap = getUserMap();
				mUser = (String)userMap.get("name");
				
				//Grab that users followers
				Map<String, Object> followerMap = getFollowers();
				mNumFollowers =  (int) followerMap.get("_total");
				mFollowers = (ArrayList<Object>) followerMap.get("follows");
				List<Object> followerName = new ArrayList<Object>();
				
				response.getWriter().println("Num Followers: " + mNumFollowers + "<br />");
				
				for (int i = 0; i < mFollowers.size(); ++i)
				{
					
					Map<String, Object> follower = (Map<String, Object>) mFollowers.get(i);
					Map<String, Object> followerInfo = (Map<String, Object>) follower.get("user");
					followerName.add(followerInfo.get("display_name"));
				}
				request.setAttribute("name", mUser);
				request.setAttribute("numFollowers", mNumFollowers);
				request.setAttribute("followers", followerName);
				
				request.getRequestDispatcher("/view.jsp").forward(request, response);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Gets the Authentication key and access right
	 * @param pCode 
	 * @return Code that was received earlier
	 * @throws Exception Propagates throws
	 */
	private Map<String, Object> getAuthMap(String pCode, String cbURL) throws Exception
	{		
		//This is the URL to get the authentication key and Post params
		URL tokenReq = new URL("https://api.twitch.tv/kraken/oauth2/token");
		String params = "client_id=jnu1pncqiy1terszj189ejzjomd911r" +
						"&client_secret=amgjqsl072wc33trwujysixxs3aqlua" +
						"&grant_type=authorization_code" +
						"&redirect_uri=" + cbURL +
						"&code=" + pCode;
	
		//Needs to be an HTTP URL to use the POST method 
		HttpURLConnection cnx = (HttpURLConnection) tokenReq.openConnection();
		cnx.setDoOutput(true);
		//Header requirements
		cnx.setRequestMethod("POST");
		cnx.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		cnx.setRequestProperty("charset", "utf-8");
		cnx.setRequestProperty("Content-Length", "" + Integer.toString(params.getBytes().length));
		cnx.setUseCaches(false);
	
		//Write the params into the body per the POST method
		DataOutputStream os = new DataOutputStream(cnx.getOutputStream());
		os.writeBytes(params);
		os.flush();
		os.close();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(cnx.getInputStream()));
		String reply = br.readLine();
				
		return JSONtoMap(reply);
	}
	
	/**
	 * Gets the user's info from Twitch using valid API
	 * @return Map of the reply
	 * @throws Exception Propagates throws
	 */
	private Map<String, Object> getUserMap() throws Exception
	{		
		//Get user data from Twitch
		URL userReq = new URL("https://api.twitch.tv/kraken/user");
		URLConnection cnx = userReq.openConnection();
		
		//Requested with Curl to recieve a JSON object with our auth key
		cnx.setRequestProperty("X-Requested-With", "Curl");
		cnx.setRequestProperty("Accept", "application/vnd.twitchtv.v2+json");
		cnx.setRequestProperty("Authorization", "OAuth " + mAuthKey);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(cnx.getInputStream()));
		String reply = br.readLine();
		
		return JSONtoMap(reply);
	}
	
	/**
	 * Returns the JSON object of followers
	 * @param pUser The user who's channel we're looking for
	 * @return JSON object of followers
	 * @throws Exception
	 */
	private Map<String, Object> getFollowers() throws Exception
	{
		URL followerReq = new URL("https://api.twitch.tv/kraken/channels/" + 
								  mUser + "/follows");
		URLConnection cnx = followerReq.openConnection();
		
		cnx.setRequestProperty("X-Requested-With", "Curl");
		cnx.setRequestProperty("Accept", "application/vnd.twitchtv.v2+json");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(cnx.getInputStream()));
		String reply = br.readLine();
		
		return JSONtoMap(reply);
	}
	
	/**
	 * Uses Jackson to convert a JSON string to an object
	 * @param pJSON STring in a JSON format
	 * @return Map of the same JSON object
	 * @throws Exception propagates the exceptions thrown by Jackson's functions
	 */
	private Map<String, Object> JSONtoMap(String pJSON) throws Exception
	{
		ObjectMapper mapper = new ObjectMapper();  

	    @SuppressWarnings("unchecked")
		Map<String,Object> map = mapper.readValue(pJSON.getBytes(), Map.class); 
//	    System.out.println("Got " + map); 
	    
	    return map;
	}
}
