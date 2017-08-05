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
		    url:'<%=contextPath%>/admin/news/allocList?lanmuId=${lanmuId}&rnd=' + Math.random(),   
		    columns:[[
				{field:'id',checkbox:true},
			    {field:'name',title:'栏目名称',width:100},
			    {field:'sortNo',title:'排序',width:100}
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
	
	//注意：这里传得是一个数组，后台也必须要用数组来接收或者使用取出数组的第一个元素字符串 类型
	function getSelectLanmus(){
		var chkRows = $('#grid1').datagrid('getChecked');
		
		var checkedItems = new Array();
		
		for(var i=0;i<chkRows.length;i++){
			checkedItems.push(chkRows[i].id);
		}
		
		//分号不能少
		return checkedItems;
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<table fit="true" id="grid1" title="栏目列表"></table>
	</div>
</body>
</html>