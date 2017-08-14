<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色分配</title>
<jsp:include page="/jsp/inc/meta.jsp"></jsp:include><html>
<script>

	
	$(document).ready(function(){
		$("input").attr('disabled','false');
		$("button").click(function()
		{
			alert($(this).html());
			$("#isverify").val($(this).html());
			//alert($("#isverify").val());
			
		});
		//alert("${officeApplication.id}");
		$("#id").val("${office.id}");
	});
	
	
	function getSelectRoles(){
		var chkRows = $('#grid1').datagrid('getChecked');
		
		var checkedItems = new Array();
		
		for(var i=0;i<chkRows.length;i++){
			checkedItems.push(chkRows[i].id);
		}
		
		return checkedItems.join(",");
	}
	
	
	function validate(){
		return $("#form1").form('validate');
	}
	
	function getFormData(){
		//var data = $("#grid1").serializeArray();
		var data = {
			"id":$("#id").val(),
			"isverify":$("#isverify").val()
		};
		return data;
	}
	
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<form id="form1" method="post">
				<div class="tbl_padding">
		<table fit="true" id="grid1" title="办公用品列表" align="center">
			<tr>
				<td><button type="button" name="check">通过</button></td>
				<td><button type="button" name="check">不通过</button></td>
				<td><input type="hidden" id="isverify" name="isverify"/></td>
				<td><input type="hidden" id="id" name="id" value="<c:out value="${office.id}"/>"/></td>
				<script type="text/javascript">
					
				</script>
			</tr>
			
		</table>
		</div>
	</form>
	</div>
</body>
</html>