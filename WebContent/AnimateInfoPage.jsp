<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
$.ajax({url:"AnimateInfo",type:"post",dataType:"json",data:"{ id:<%=request.getParameter("id") %> }",success:function(data) {
	$('<h1>').text(data.name).appendTo($('#data'));
    var table = $('<table>').appendTo($('#data'));
    $.each(data.setList, function(i, item) {
        var row = $('<tr>').appendTo(table);
        $('<td>').text(item.fileName).appendTo(row);
        $('<td>').text(item.itemPath).appendTo(row);
    });
}});
</script>
</head>
	<body>
		<div id="data"></div>
	</body>
</html>