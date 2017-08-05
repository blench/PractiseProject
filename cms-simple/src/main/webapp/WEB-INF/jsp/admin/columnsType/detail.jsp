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
	<title>新闻栏目</title>
	<jsp:include page="/jsp/inc/meta.jsp"></jsp:include>
	<script type="text/javascript" src="<%=contextPath%>/js/xheditor/xheditor-1.2.1.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/xheditor/xheditor_lang/zh-cn.js"></script>
	<script>
	function validate(){
		return $("#form1").form('validate');
	}
	
	
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
				<input type="hidden" name="id" value="<c:out value="${lanmuType.id}"/>"/>
				<div class="tbl_padding">
				<table>
					<tr>
						<td>栏目类别名称:</td>
						<td>
						<input type="text" name="name" class="easyui-validatebox" value="<c:out value="${lanmuType.name}"/>"  class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td>统计:</td>
						<td>
						<label  id="shuju" name="shuju"><c:out value="${lanmuType.shuju}"/></label>
						</td>
					</tr>
				</table>
				</div>
			</form>
	</div>
</div>
</body>
</html>