<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ page import="org.apache.shiro.SecurityUtils"%>
<%@ page import="org.apache.shiro.subject.Subject"%>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%
	Subject subject = SecurityUtils.getSubject();

	if(subject.getPrincipal()!=null){
%>
<script>
	window.location = "${ctx}";
</script>
<%
	}
%>
<!DOCTYPE html>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><spring:message code="siteName"/></title>
	<link href="${ctx}/js/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/js/jquery-validation/validate.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/css/login.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/js/jquery-1.8.2.min.js" type="text/javascript"></script>
	<script src="${ctx}/js/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctx}/js/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>
	<script src="${ctx}/js/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginForm").validate();
		});
	</script>
</head>
<body>
	<div class="container">
	<div id="header">
		<div id="title">
		    <h1><a href="/quickstart"><spring:message code="siteName"/></a><small>--登录界面</small>
		    
			</h1>
		</div>
	</div>
	<div id="content">
		<form id="loginForm" action="${ctx}/login" method="post" class="form-horizontal">
		<%
		String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if(error != null){
		%>
			<div class="alert alert-error input-medium controls">
				<button class="close" data-dismiss="alert">×</button>登录失败，请重试.
			</div>
		<%
		}
		%>
			<div class="control-group">
				<label for="username" class="control-label">名称:</label>
				<div class="controls">
					<input type="text" id="username" name="username"  value="${username}" class="input-medium required"/>
				</div>
			</div>
			<div class="control-group">
				<label for="password" class="control-label">密码:</label>
				<div class="controls">
					<input type="password" id="password" name="password" class="input-medium required"/>
				</div>
			</div>
					
			<div class="control-group">
				<div class="controls">
					<label class="checkbox" for="rememberMe"><input type="checkbox" id="rememberMe" name="rememberMe"/> 记住我</label>
					<input id="submit_btn" class="btn btn-primary" type="submit" value="登录"/>
				</div>
			</div>
		</form>
	</div>
	<div id="footer">
		Copyright &copy; 2015 <a href="http://www.jpsoft.com.cn">www.jpsoft.com.cn</a>
	</div>
</body>
</html>
