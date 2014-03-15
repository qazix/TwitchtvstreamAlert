

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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
       
	private String mAuthKey;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public callback() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		response.getWriter().println("Your code is " + code + "<br />");
		
		if (code != "" && code != null)
		{
			try 
			{
				Map<String, Object> authkeyMap = getAuthMap(code);
				mAuthKey = authkeyMap.get("access_token").toString();
				Map<String, Object> userMap = getUserMap();
				
				response.getWriter().println(userMap.get("display_name") + "<br />");
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
	
	private Map<String, Object> getAuthMap(String pCode) throws Exception
	{
		URL tokenReq = new URL("https://api.twitch.tv/kraken/oauth2/token");
		String params = "client_id=jnu1pncqiy1terszj189ejzjomd911r" +
						"&client_secret=amgjqsl072wc33trwujysixxs3aqlua" +
						"&grant_type=authorization_code" +
						"&redirect_uri=http://localhost:8080/streamAlert/callback" +
						"&code=" + pCode;
	
		HttpURLConnection cnx = (HttpURLConnection) tokenReq.openConnection();
		cnx.setDoOutput(true);
		cnx.setRequestMethod("POST");
		cnx.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		cnx.setRequestProperty("charset", "utf-8");
		cnx.setRequestProperty("Content-Length", "" + Integer.toString(params.getBytes().length));
		cnx.setUseCaches(false);
	
		DataOutputStream os = new DataOutputStream(cnx.getOutputStream());
		os.writeBytes(params);
		os.flush();
		os.close();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(cnx.getInputStream()));
		String reply = br.readLine();
				
		return JSONtoMap(reply);
	}
	
	private Map<String, Object> getUserMap() throws Exception
	{		
		URL userReq = new URL("https://api.twitch.tv/kraken/user");
		URLConnection cnx = userReq.openConnection();
		
		cnx.setRequestProperty("X-Requested-With", "Curl");
		cnx.setRequestProperty("Accept", "application/vnd.twitchtv.v2+json");
		cnx.setRequestProperty("Authorization", "OAuth " + mAuthKey);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(cnx.getInputStream()));
		String reply = br.readLine();
		
		return JSONtoMap(reply);
	}
	
	private Map<String, Object> JSONtoMap(String pJSON) throws Exception
	{
		ObjectMapper mapper = new ObjectMapper();  

	    @SuppressWarnings("unchecked")
		Map<String,Object> map = mapper.readValue(pJSON.getBytes(), Map.class); 
//	    System.out.println("Got " + map); 
	    
	    return map;
	}
}
