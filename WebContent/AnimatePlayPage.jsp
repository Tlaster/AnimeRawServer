<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
video { margin: 0 auto; width: 800px;display:block; }
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<video src="AnimateVideo/<%=request.getParameter("id") %>/<%=request.getParameter("file") %>" controls="controls" type="video/mp4">
	</video>
</body>
</html>