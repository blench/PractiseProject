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
	<title>角色信息</title>
	<jsp:include page="/jsp/inc/meta.jsp"></jsp:include>
	<script>
	function validate(){
		return $("#form1").form('validate');
	}
	</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="padding:10px;">
		<div data-options="title:'角色信息'">
			<form id="form1" method="post">
				<input type="hidden" name="id" value="<c:out value="${role.id}"/>"/>
				<div class="tbl_padding">
				<table>
					<tr>
						<td>角色名称:</td>
						<td><input type="text" name="name" class="easyui-validatebox" value="<c:out value="${role.name}"/>"/></td>
					</tr>
					<tr>
						<td>角色描述:</td>
						<td><input type="text" name="description" class="easyui-validatebox" value="<c:out value="${role.description}"/>"/></td>
					</tr>
				</table>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>