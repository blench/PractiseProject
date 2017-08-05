<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>导入数据</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/bootstrap/css/bootstrap.min.css"/>
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.2.min.js" charset="utf-8"></script>
</head>
<body>
	<div style="padding:10px;">
		<form enctype="multipart/form-data" method="post" action="<%=basePath%>sysmgr/dataSync/import">
		  <fieldset>
		    <legend>纳税端数据导入</legend>
		    <label>选择导入文件</label>
		    <input type="file" style="width:300px;" name="file1">
		    <button type="submit" class="btn">导入</button>
		  </fieldset>
		</form>
	</div>
</body>
</html>