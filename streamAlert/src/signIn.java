
import java.io.IOException;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class streamAlert
 */
@WebServlet("/signIn")
public class signIn extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public signIn() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getScheme() + "://" + request.getServerName() + ":" + 
							request.getServerPort() + request.getRequestURI();
//		System.out.println(requestURI);
		int lastSlashIndex = requestURI.lastIndexOf("/");
		
		String cbURL = requestURI.substring(0, lastSlashIndex) + "/callback";
		String clientID = PropertiesService.get("clientID");
		
		URL twitchAuthURL = new URL("https://api.twitch.tv/kraken/oauth2/authorize?response_type=code&client_id=" +
								clientID + "&redirect_uri=" + cbURL + "&scope=user_read");
		
//		response.getWriter().print(twitchAuthURL);
		response.sendRedirect(twitchAuthURL.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
