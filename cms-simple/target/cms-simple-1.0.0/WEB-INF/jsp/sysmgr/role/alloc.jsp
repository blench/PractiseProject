<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		//去掉最后一个逗号
		$('#grid1').datagrid({
		    url:'<%=contextPath%>/sysmgr/role/allocList?userId=${userId}&rnd=' + Math.random(),   
		    columns:[[
				{field:'id',checkbox:true},
			    {field:'name',title:'角色名称',width:100},
			    {field:'description',title:'角色描述',width:100}
		    ]],
		    rownumbers:true,
		    pagination:true,
		    pageList:[5,10,20,50],
		    singleSelect:true,
		    toolbar: '#tb',
		    fit:true,
		    onLoadSuccess:function(data){      
		    	if(data.rows!=null){
		    		$.each(data.rows, function(index, item){
			    		if(item.checked){
			    			$('#grid1').datagrid("checkRow", index);
			    		}
		    		});
		    	}
	    	}
		});
	});
	
	function getSelectRoles(){
		var chkRows = $('#grid1').datagrid('getChecked');
		
		var checkedItems = new Array();
		
		for(var i=0;i<chkRows.length;i++){
			checkedItems.push(chkRows[i].id);
		}
		
		return checkedItems.join(",");
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<table fit="true" id="grid1" title="角色列表"></table>
	</div>
</body>
</html>