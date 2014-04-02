

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	private List<Object> mFollowers;
	private long mLastDate;
	
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
		int lastSlashIndex = requestURI.lastIndexOf("/");

		String cbURL = requestURI.substring(0, lastSlashIndex) + "/callback";
		
		//this is the code autheticating our client
		String code = request.getParameter("code");
		
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
				mFollowers = Followers.getRecentFollowers(mUser);				
				
				//Gets the last follower and the time he followed you
				Map<String, Object> follower = (Map<String, Object>) mFollowers.get(0);
				mLastDate = Followers.getSDF().parse((String) follower.get("created_at")).getTime();
				
				request.getSession().setAttribute("name", mUser);
				request.setAttribute("numFollowers", mFollowers.size());
				request.setAttribute("followers", mFollowers);
				request.getSession().setAttribute("lastDate", mLastDate);
				
				response.getWriter().println(mFollowers);
				
				request.getRequestDispatcher("/streamDB").forward(request, response);
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
		String params = "client_id=" + PropertiesService.get("clientID") +
						"&client_secret=" + PropertiesService.get("client_secret")  + 
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
				
		return PropertiesService.JSONtoMap(reply);
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
		
		return PropertiesService.JSONtoMap(reply);
	}
}
