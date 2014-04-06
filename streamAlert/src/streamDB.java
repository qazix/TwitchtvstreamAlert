

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class streamDB
 */
@WebServlet("/streamDB")
public class streamDB extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private static final String DB_URL = "jdbc:mysql://localhost/streamalert";
//	private static final String DB_URL = "jdbc:mysql://ec2-54-236-111-22.compute-1.amazonaws.com:3306/streamalert";
	
	//  Database credentials
	private static final String USER = "TSADBuser";//"TSAawayUser";
	private static final String PASS = "userTSADB";//"awayUserTSA";
		
	private Connection conn;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public streamDB() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = (String) request.getSession().getAttribute("name");
		Statement stmt = null;
		String BGColor = "";
		String ChromaColor = "";
		String FontColor = "";
		int FontSize = 32;
		String ExtCSS = "";
		boolean ShowPic = true;
		
		try
		{
	    	Class.forName(JDBC_DRIVER);
	    	conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			System.out.println("creating a statement");
			String sql = "SELECT * FROM user WHERE twitchid = '" + name + "'";
			ResultSet rs = stmt.executeQuery(sql);
			
			if (!rs.next())
			{
				System.out.println("no user in db creating row");
				sql = "INSERT INTO user SET twitchid = '" + name + "'";
				int numRs = stmt.executeUpdate(sql);
				if (numRs < 1)
					throw(new Exception());
				else
				{
					sql = "SELECT * FROM user WHERE twitchid = '" + name + "'";
					rs.close();
					rs = stmt.executeQuery(sql);
					if(!rs.next())
						throw(new Exception());
				}
			}
			
			BGColor = rs.getString("bgcolor");
			ChromaColor = rs.getString("chromacolor");
			FontColor = rs.getString("fontcolor");
			FontSize = rs.getInt("fontsize");
			ExtCSS = rs.getString("externalcss");
			ShowPic = rs.getInt("userpicture") > 0;
			
			stmt.close();
			conn.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			//finally block used to close resources
			try
			{
				if(stmt!=null)
					stmt.close();
		    }
			catch(SQLException se2)
			{ }// nothing we can do
		    try
		    {
		    	if(conn!=null)
		    		conn.close();
		    }
		    catch(SQLException se)
		    {
		         se.printStackTrace();
		    }//end finally try
		}//end try
		
		request.setAttribute("BGColor", BGColor);
		request.setAttribute("ChromaColor", ChromaColor);
		request.setAttribute("FontColor", FontColor);
		request.setAttribute("FontSize", FontSize);
		request.setAttribute("ExtCSS", ExtCSS);
		request.setAttribute("ShowPic", ShowPic);
		
		request.getRequestDispatcher("/view.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
	    	Class.forName("com.mysql.jdbc.Driver");
	    	conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			String sql = "UPDATE user SET bgcolor = '" + request.getAttribute("BGColor") +
						 "', chromacolor = '" + request.getAttribute("ChromaColor") + 
						 "', fontcolor = '" + request.getAttribute("FontColor") +
						 "', fontsize = " + request.getAttribute("FontSize") + 
						 ", externalcss = '" + request.getAttribute("ExtCSS") + 
						 "', userpicture = " + request.getAttribute("ShowPic") + 
						 " WHERE twitchid = '" + request.getSession().getAttribute("name") + "'";
			
			stmt.executeUpdate(sql);
			conn.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		response.sendRedirect("/streamDB");
	}
}
