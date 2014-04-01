

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class getFollowers
 */
@WebServlet("/getFollowers")
public class Followers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int mNumFollowers;
	private List<Object> mFollowers;
	private Date mLastDate;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Followers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	/**
	 * Returns the JSON object of followers
	 * @param pUser The user who's channel we're looking for
	 * @return JSON object of followers
	 * @throws Exception
	 */
	public static Map<String, Object> getFollowers(String pUser) throws Exception
	{
		URL followerReq = new URL("https://api.twitch.tv/kraken/channels/" + 
								  pUser + "/follows");
		URLConnection cnx = followerReq.openConnection();
		
		cnx.setRequestProperty("X-Requested-With", "Curl");
		cnx.setRequestProperty("Accept", "application/vnd.twitchtv.v2+json");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(cnx.getInputStream()));
		String reply = br.readLine();
		
		return PropertiesService.JSONtoMap(reply);
	}
}
