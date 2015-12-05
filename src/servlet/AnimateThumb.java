package servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AnimateThumb
 */
@WebServlet("/AnimateThumb/*")
public class AnimateThumb extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
	private final String _connectionUrl = "jdbc:sqlserver://localhost:1433;" +
			"database=AnimateDatabase;integratedSecurity=true;";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnimateThumb() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		String reqFile = request.getPathInfo();
		System.out.println(reqFile);
		String[] result = reqFile.split("[/]+");
		String filePath = null;
		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			try(Connection con = DriverManager.getConnection(_connectionUrl))
			{
				try(Statement stmt = con.createStatement())
				{
		    		String SQL = "select FileThumb from SetDetail where ID = " + result[1] + "and FileName = " + result[2];
					try(ResultSet rs=stmt.executeQuery(SQL))
					{
						rs.next();
						filePath = rs.getString(1);
					}
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		if(filePath == null) return;
		File file = new File(filePath);
		response.setContentType("image/png");
		try(ServletOutputStream out = response.getOutputStream())
		{
			try(BufferedInputStream input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE))
			{
				byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
				int bytesRead;
				while ((bytesRead = input.read(bytes)) != -1) 
				{
					out.write(bytes, 0, bytesRead);
				}
			}
		}
	}

}
