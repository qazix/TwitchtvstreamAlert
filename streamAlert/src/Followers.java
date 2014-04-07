

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
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
 * Servlet implementation class getFollowers
 */
@WebServlet("/Followers")
public class Followers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
       
	
	public static SimpleDateFormat getSDF()
	{ return sdf; }
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Followers() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = (String) request.getSession().getAttribute("name");
		long lastDate = Long.parseLong(request.getSession().getAttribute("lastDate").toString());
		
		System.out.println("user: " + user + " lastDate: " + lastDate);
		
		try
		{
			List<Object> iFollowers = getRecentFollowers(user, lastDate);
			
			if(iFollowers.isEmpty())
			{
				response.setContentType("text/html");
				response.getWriter().print("null");
			}
			else
			{
				@SuppressWarnings("unchecked")
				Map<String, Object> iFollower = (Map<String, Object>) iFollowers.get(0);
				request.getSession().setAttribute("lastDate", sdf.parse((String) iFollower.get("created_at")).getTime());
			
				OutputStream out = new ByteArrayOutputStream();
				ObjectMapper mapper = new ObjectMapper();
			
				mapper.writeValue(out, iFollowers);
				response.setContentType("application/json");
				response.getWriter().print(out.toString());
			}
		}
		catch (Exception e) 
		{
			response.setContentType("text/html");
			response.getWriter().print("null");
			e.printStackTrace();
		}	
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
	
	public static List<Object> getRecentFollowers(String pUser) throws Exception
	{
		return getRecentFollowers(pUser, 0);
	}
	
	/**
	 * Gets a list of map objects that are after the lastDate or the last if it's 0
	 * @param pUser The user for whom's data we are looking for
	 * @param pDate The threshold for recent Followers
	 * @return List of map of the follower info
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> getRecentFollowers(String pUser, long pDate) throws Exception
	{
		List<Object> iRecentFollowers = new ArrayList<Object>();
		Map<String, Object> iFollowerMap = getFollowers(pUser);
		List<Object> iFollowers;
		
		if((int) iFollowerMap.get("_total") > 0)
		{
			iFollowers = (ArrayList<Object>) iFollowerMap.get("follows");
			Map<String, Object> iFollower;
		
			if(pDate > 0)
			{
				for (int i = 0; i < iFollowers.size(); ++i)
				{
					iFollower = (Map<String, Object>) iFollowers.get(i);
					if (sdf.parse((String) iFollower.get("created_at")).getTime() > pDate)
					{
						iRecentFollowers.add(iFollower);
					}
					else
					{
						break;
//						iRecentFollowers.add(iFollower);
					}
				}
			}
			else 
			{
//				System.out.println(iFollowers.get(0));
				iRecentFollowers.add((Map<String, Object>) iFollowers.get(0));
			}
		}
		
		return iRecentFollowers;
	}
}
