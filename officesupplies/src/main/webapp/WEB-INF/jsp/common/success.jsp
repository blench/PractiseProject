<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统提示</title>
<link href="<%=basePath%>js/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen"/>
</head>
<body>
<div class="alert alert-success" style="margin:5px;">
    <button type="button" class="close" data-dismiss="success">&times;</button>
    <h4>系统提示</h4>  
    ${msg}
</div>
</body>
</html>