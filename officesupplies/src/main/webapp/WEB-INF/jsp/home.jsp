<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<jsp:include page="/jsp/inc/meta.jsp"></jsp:include>
<link href="<%=basePath%>css/home.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(document).ready(function() {
		WdatePicker({
			eCont : 'calendarBox',
			onpicked : function(dp) {
				alert('你选择的日期是:' + dp.cal.getDateStr())
			}
		});

		$("a[rel='tabs']").each(function() {
			$(this).click(function() {
				parent.addTab(parent.$.mTab, {
					url : $(this).attr("href"),
					title : $(this).attr("title")
				});

				return false;
			});
		});

		var clientHeight = document.documentElement.clientHeight;
		$("#sideBar").css("height", clientHeight);
	});
</script>
</head>
<body scroll="no">
	<div id="dashboard">
		<div id="main-content" class="clearfix">
			<div id="breadCrumb">
				<h1>首页</h1>
			</div>
			<ul id="newsList">
				<li class="newsWrap">
					<div class="newsWrapBox" style="height:100px;">欢迎您!</div>
				</li>
			</ul>
		</div>
		<div id="sideBar">
			<div id="info">
				<h1>个人信息</h1>
				<div class="infoTxt">
					<p>
						【${sessionScope.user.username}】，您好欢迎登录
						<spring:message code="siteName" />
						。
					</p>
					<p>
						上次登录时间为： <br> <span><%%></span>
					</p>
				</div>
			</div>
			<div id="calendar">
				<h1>日历</h1>
				<div id="calendarBox"></div>
			</div>
		</div>
	</div>
</body>
</html>