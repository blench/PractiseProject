<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%response.setStatus(200);%>

<!DOCTYPE html>
<html>
<head>
	<title>401 - 页面未授权访问</title>
</head>

<body>
	<h2>401 - 页面未授权访问.</h2>
	<p><a href="<c:url value="/"/>">返回首页</a></p>
</body>
</html>