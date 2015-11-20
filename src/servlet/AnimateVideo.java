package servlet;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AnimateVideo
 */
@WebServlet("/AnimateVideo/*")
public class AnimateVideo extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private final String _connectionUrl = "jdbc:sqlserver://localhost:1433;" +
			"database=AnimateDatabase;integratedSecurity=true;";

    private static final int DEFAULT_BUFFER_SIZE = 10240; // ..bytes = 10KB.
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnimateVideo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

        processRequest(request, response);
	}
	
    private static long sublong(String value, int beginIndex, int endIndex) 
    {
        String substring = value.substring(beginIndex, endIndex);
        return (substring.length() > 0) ? Long.parseLong(substring) : -1;
    }

    private static void copy(RandomAccessFile input, OutputStream output, long start, long length)
        throws IOException
    {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int read;

        if (input.length() == length)
        {
            while ((read = input.read(buffer)) > 0)
            {
                output.write(buffer, 0, read);
            }
        } 
        else
        {
            input.seek(start);
            long toRead = length;
            while ((read = input.read(buffer)) > 0) 
            {
                if ((toRead -= read) > 0)
                {
                    output.write(buffer, 0, read);
                }
                else 
                {
                    output.write(buffer, 0, (int) toRead + read);
                    break;
                }
            }
        }
    }

    

    private void processRequest
        (HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
		String reqFile = request.getPathInfo();
		String[] result = reqFile.split("[/]+");
		String filePath = "";
		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			try(Connection con = DriverManager.getConnection(_connectionUrl))
			{
				try(Statement stmt = con.createStatement())
				{
		    		String SQL = "select DirPath from AnimateList where ID = " + result[1];
					try(ResultSet rs=stmt.executeQuery(SQL))
					{
						rs.next();
						filePath = rs.getString(1) +"\\" + result[2] + ".mp4";
					}
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		File file = new File(filePath);
        if (!file.exists()) 
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String fileName = file.getName();
        long length = file.length();
        String eTag = fileName + "_" + length;
        Range full = new Range(0, length - 1, length);
        List<Range> ranges = new ArrayList<Range>();
        String range = request.getHeader("Range");
        if (range != null) 
        {
            if (!range.matches("^bytes=\\d*-\\d*(,\\d*-\\d*)*$")) 
            {
                response.setHeader("Content-Range", "bytes */" + length);
                response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                return;
            }
            String ifRange = request.getHeader("If-Range");
            if (ifRange != null && !(ifRange == eTag)) 
            {
                ranges.add(full);
            }
            if (ranges.isEmpty()) 
            {
                for (String part : range.substring(6).split(",")) 
                {
                    long start = sublong(part, 0, part.indexOf("-"));
                    long end = sublong(part, part.indexOf("-") + 1, part.length());

                    if (start == -1)
                    {
                        start = length - end;
                        end = length - 1;
                    } 
                    else if (end == -1 || end > length - 1) 
                    {
                        end = length - 1;
                    }
                    if (start > end) 
                    {
                        response.setHeader("Content-Range", "bytes */" + length);
                        response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                        return;
                    }
                    ranges.add(new Range(start, end, length));
                }
            }
        }
        String contentType = getServletContext().getMimeType(fileName);
        if (contentType == null)
        {
            contentType = "application/octet-stream";
        }

        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setHeader("Content-Disposition", "inline;filename=\"" + fileName + "\"");
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("ETag", eTag);
    	try(RandomAccessFile input = new RandomAccessFile(file, "r"))
    	{
    		try(OutputStream output = response.getOutputStream())
    		{
    	            if (ranges.isEmpty() || ranges.get(0) == full) 
    	            {
    	                Range r = full;
    	                response.setContentType(contentType);
    	                response.setHeader("Content-Range", "bytes " + r.start + "-" + r.end + "/" + r.total);
                        response.setHeader("Content-Length", String.valueOf(r.length));
	                    copy(input, output, r.start, r.length);
    	            } 
    	            else if (ranges.size() == 1)
    	            {
    	                Range r = ranges.get(0);
    	                response.setContentType(contentType);
    	                response.setHeader("Content-Range", "bytes " + r.start + "-" + r.end + "/" + r.total);
    	                response.setHeader("Content-Length", String.valueOf(r.length));
    	                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
	                    copy(input, output, r.start, r.length);
    	            } 
    	            else
    	            {
    	                response.setContentType("multipart/byteranges; boundary=MULTIPART_BYTERANGES");
    	                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
	                    try(ServletOutputStream sos = (ServletOutputStream) output)
	                    {
		                    for (Range r : ranges)
		                    {
		                        sos.println();
		                        sos.println("--MULTIPART_BYTERANGES");
		                        sos.println("Content-Type: " + contentType);
		                        sos.println("Content-Range: bytes " + r.start + "-" + r.end + "/" + r.total);
		                        copy(input, output, r.start, r.length);
		                    }
		                    sos.println();
		                    sos.println("--MULTIPART_BYTERANGES--");
	                    }
    	            }
    		}
    	}
    }
    class Range
    {
        long start;
        long end;
        long length;
        long total;
        public Range(long start, long end, long total) 
        {
            this.start = start;
            this.end = end;
            this.length = end - start + 1;
            this.total = total;
        }

    }
	
}
