<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String contextPath = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${moduleName}管理</title>
<jsp:include page="/jsp/inc/meta.jsp"></jsp:include>
<script>
	$(document).ready(function(){
		//去掉最后一个逗号
		$('#grid1').datagrid({
		    url:'<%=contextPath%>/sysmgr/log/list',   
		    columns:[[
		    {field:'userName',title:'用户名 ',width:100},
		    {field:'content',title:'日志内容',width:500},
		    {field:'createTimeF',title:'记录时间',width:200}
		    ]],
		    rownumbers:true,
		    singleSelect:true,
		    selectOnCheck:false,
		    checkOnSelect:false,
		    pagination:true,
		    pageList:[5,10,20,50],
		    pageSize:20,
		    fit:true
		});
	});
	
	function query(){
		$('#grid1').datagrid('load', {
			userName: $("#userName").val(),
			beginTime: $("#beginTime").datebox('getValue'),
			endTime: $("#endTime").datebox('getValue')
		});
	}
</script>
</head>
<body class="easyui-layout">
	<div region="north" title="查询" style="height:65px;margin-top:5px;">
		<form id="searchForm" method="post" action="" target="_blank">
		    <table>  
		    	<tr>
		    		<td>用户名:</td>
		    		<td><input type="text" id="userName" class="input-text"/></td>
		    		<td>记录时间:</td>
		    		<td>
		    			<input type="text" id="beginTime" name="beginTime" class="easyui-datetimebox" style="width:150px;"/>
		    			至
		    			<input type="text" id="endTime" name="endTime" class="easyui-datetimebox" style="width:150px;"/>
		    		</td>
		    		<td>
		    			<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="query()">查询</a>
		    			<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="searchForm.reset()">清空</a>
		    		</td>
		    	</tr>
	    	</table>
		</form>
	</div>
	<div data-options="region:'center'">
		<table fit="true" id="grid1" title="日志列表"></table>
	</div>
</body>
</html>