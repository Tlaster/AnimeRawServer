package servlet;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.nio.file.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import model.*;
import net.sf.json.*;
import org.apache.commons.io.FilenameUtils;

/**
 * Servlet implementation class AnimateInfo
 */
@WebServlet("/AnimateInfo")
public class AnimateInfo extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	String _connectionUrl = "jdbc:sqlserver://localhost:1433;" +
			"database=AnimateDatabase;integratedSecurity=true;";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnimateInfo() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		StringBuffer jb = new StringBuffer();
		String line = null;
		try
		{
			BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		}
		catch (Exception e) 
		{ 
		}
		JSONObject jsonObject = JSONObject.fromObject(jb.toString());
		int id = jsonObject.getInt("id");
		ArrayList<AnimateSetModel> list = new ArrayList<AnimateSetModel>();
		AnimateInfoModel item = new AnimateInfoModel();
		item.setID(id);
		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			try(Connection con = DriverManager.getConnection(_connectionUrl))
			{
				try(Statement stmt = con.createStatement())
				{
		    		String SQL = "select FileName,ClickCount from SetDetail where ID = " + id;
					try(ResultSet rs=stmt.executeQuery(SQL))
					{
						while(rs.next())
							list.add(new AnimateSetModel(rs.getString(1),rs.getInt(2)));
					}
		    		SQL = "select Name from AnimateList where ID = " + id;
					try(ResultSet rs=stmt.executeQuery(SQL))
					{
						rs.next();
						item.setName(rs.getString(1));
					}		    		
					SQL = "select Description from AnimateInfo where ID = " + id;
					try(ResultSet rs=stmt.executeQuery(SQL))
					{
						rs.next();
						item.setDescription(rs.getString(1));
					}
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		item.setSetList(list);
		response.getWriter().write(JSONObject.fromObject(item).toString());
	}

}
