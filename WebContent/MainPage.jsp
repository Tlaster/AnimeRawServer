<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Animate Raws</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
$.getJSON('AnimateList', function(data) {
    var table = $('<table>').appendTo($('#data'));
    var header = $('<tr>').appendTo(table);
    $('<td>').text("Name").appendTo(header);
    $('<td>').text("LastUpdate").appendTo(header);
    $.each(data, function(i, item) {
        var row = $('<tr>').appendTo(table);
        $('<a>').attr("href","AnimateInfoPage.jsp?id="+item.ID).text(item.name).appendTo($('<td>').appendTo(row));
        $('<td>').text(item.lastUpdate).appendTo(row);
    });
});
</script>
</head>
<body>
<div id="data"></div>
</body>
</html>