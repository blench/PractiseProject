<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新闻编辑</title>
	<jsp:include page="/jsp/inc/meta.jsp"></jsp:include>
	<script type="text/javascript" src="<%=contextPath%>/js/xheditor/xheditor-1.2.1.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/xheditor/xheditor_lang/zh-cn.js"></script>
	<script>
	function validate(){
		return $("#form1").form('validate');
	}
	
	$(document).ready(function(){
		$('#content').xheditor({
			tools:'Blocktag,Fontface,FontSize,Bold,Italic,Underline,Strikethrough,FontColor,BackColor,|,Align,List,|,Link,Img,|,Source,Fullscreen',
			upImgUrl:"<%=contextPath%>/admin/news/upload",
			upImgExt:"jpg,jpeg,gif,png",
			upLinkUrl:"<%=contextPath%>/admin/news/upload",
			upLinkExt:"zip,rar,txt,doc,xls,ppt",
			html5Upload : false
		});
	});
	
	function getFormData(){
		var data = $("#form1").serializeArray();
		//alert(data[2].value);
		return data;
	}
	</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="padding:10px;">
			<form id="form1" method="post">
				<input type="hidden" name="id" value="<c:out value="${news.id}"/>"/>
				<input type="hidden" name="menu.id" value="<c:out value="${news.menu.id}"/>"/>
				<table width="100%" height="100%">
					<tr>
						<td>新闻标题:</td>
						<td>
						<input type="text" name="title" class="easyui-validatebox" value="<c:out value="${news.title}"/>" size="100"/>
						</td>
					</tr>
					<tr>
						<td>新闻内容:</td>
						<td>
						<textarea id="content" name="content" rows="25" cols="80"><c:out value="${news.content}"/></textarea>
						</td>
					</tr>
				</table>
			</form>
	</div>
</div>
</body>
</html>