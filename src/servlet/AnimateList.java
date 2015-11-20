package servlet;

import java.awt.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AnimateListModel;
import net.sf.json.JSONArray;

/**
 * Servlet implementation class AnimateList
 */
@WebServlet("/AnimateList")
public class AnimateList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String _connectionUrl = "jdbc:sqlserver://localhost:1433;" +
			"database=AnimateDatabase;integratedSecurity=true;";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnimateList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		ArrayList<AnimateListModel> list = new ArrayList<AnimateListModel>();
		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			try(Connection con = DriverManager.getConnection(_connectionUrl))
			{
				try(Statement stmt = con.createStatement())
				{
		    		String SQL = "select Name,ID from AnimateList";
					try(ResultSet rs=stmt.executeQuery(SQL))
					{
						while(rs.next())
							list.add(new AnimateListModel(rs.getString(1),rs.getInt(2)));
					}
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		response.getWriter().write(JSONArray.fromObject(list).toString());
		System.out.println(JSONArray.fromObject(list).toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
