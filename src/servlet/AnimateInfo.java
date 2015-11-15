package servlet;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import model.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class AnimateInfo
 */
@WebServlet("/AnimateInfo")
public class AnimateInfo extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	String _connectionUrl = "jdbc:sqlserver://localhost:1433;" +
			"database=AnimateDataBase;integratedSecurity=true;";
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
		String dirPath = "";
		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			try(Connection con = DriverManager.getConnection(_connectionUrl))
			{
				try(Statement stmt = con.createStatement())
				{
		    		String SQL = "select Name,DirPath from AnimateList where ID = " + id;
					try(ResultSet rs=stmt.executeQuery(SQL))
					{
						rs.next();
						item.setName(rs.getString(1));
						item.setID(id);
						dirPath = rs.getString(2);
					}
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		if(dirPath.length() == 0) return;
		File folder = new File(dirPath);
		for(File file : folder.listFiles())
			list.add(new AnimateSetModel(file.getName(),file.getPath()));
		item.setSetList(list);
		response.getWriter().write(JSONObject.fromObject(item).toString());
		System.out.println(JSONObject.fromObject(item).toString());
	}

}