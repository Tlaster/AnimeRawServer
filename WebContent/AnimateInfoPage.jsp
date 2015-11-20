<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
li{ padding:4px; list-style-type: none; float: left; }
a{ text-align:center;display:block; }
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
$.ajax({url:"AnimateInfo",type:"post",dataType:"json",data:"{ id:<%=request.getParameter("id") %> }",success:function(data) {
	$('<h1>').text(data.name).appendTo($('#data'));
    var table = $('<ul>').appendTo($('#data'));
    $.each(data.setList, function(i, item) {
        var row = $('<li>').appendTo(table);
        var imgdiv = $('<div>').appendTo(row);
        var herfdiv = $('<div>').appendTo(row);
        $('<img>').attr("src","AnimateThumb/"+data.ID+"/"+item).attr("width","196px").attr("height","110px").appendTo($('<a>').attr("href","AnimatePlayPage.jsp?id="+data.ID+"&file="+item).appendTo(imgdiv));
        $('<a>').attr("href","AnimatePlayPage.jsp?id="+data.ID+"&file="+item).text(item).appendTo(herfdiv);
    });
}});
</script>
</head>
	<body>
		<div id="data"></div>
	</body>
</html>