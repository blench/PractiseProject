<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<jsp:include page="/jsp/inc/meta.jsp"></jsp:include><html>
<script>
	var detailDialog;
	
	$(document).ready(function(){
		$('#grid1').datagrid({
		    url:'<%=contextPath%>/sysmgr/user/list',   
		    columns:[[
			{field:'id',checkbox:true},
		    {field:'name',title:'用户名称',width:200},
		    {field:'realName',title:'真实名称',width:100},
		    {field:'createDateF',title:'创建时间',width:200},
		    {field:'lastVisitDateF',title:'最后访问时间',width:200}		    
		    ]],
		    rownumbers:true,
		    singleSelect:true,
		    selectOnCheck:false,
		    checkOnSelect:false,
		    pagination:true,
		    pageList:[5,10,20,50],
		    pageSize:20,
		    toolbar: '#tb',
		    fit:true,
		    onDblClickRow:function(rowIndex, rowData){
		    }
		});
	});
	
	function showDialog(id){
		detailDialog = art.dialog.open('<%=contextPath%>/sysmgr/user/detail/' + id, 
		{
			title: '用户信息',
			opacity: 0.87,	// 透明度,
			width:400,
			height:300,
		    ok: function () {
		    	var win = this.iframe.contentWindow;
		    	
		    	if(win.validate()){
					var form = win.document.getElementById("form1");
			    	
		        	$.post("<%=contextPath%>/sysmgr/user/save",
		        		   $(form).serializeArray(),
		        		    function(json){
						    	if(json.result){
						    		$('#grid1').datagrid('reload');
						    		detailDialog.close();
						    	}
		        			},
		        			"json");
		    	}
		    	else{
		    		return false;
		    	}
		    },
			cancel: true,
		    okVal:"保存"
		});
	}
	
	function query(){
		$('#grid1').datagrid('load', {   
			userName: $("#userName").val()
		});
	}
	
	function append(){
		showDialog(-1);
	}
	
	function edit(){
		var row = $('#grid1').datagrid('getSelected');
		
		if(row!=null){
			showDialog(row.id);
		}
		else{
			$.messager.alert("系统提示","请点击要修改的记录!");
		}
	}
	
	function removeRow(){
		var chkRows = $('#grid1').datagrid('getChecked');
	
		if(chkRows.length>0){
			if(confirm("确定删除选择记录?")){
				var checkedItems = new Array();
				
				for(var i=0;i<chkRows.length;i++){
					checkedItems.push(chkRows[i].id);
				}
				
				$.post("<%=contextPath%>/sysmgr/user/delete",{
					checkedItems:checkedItems.join(",")
				},
				function(rs){
					if(rs.result){
						$('#grid1').datagrid('reload');
					}
					else{
						$.messager.alert("删除失败!","记录被引用!");
					}
				},"json");
			}
		}
		else{
			alert("请勾选记录!");
		}
	}

	function allocRole(){
		var row = $('#grid1').datagrid('getSelected');
		
		if(row!=null){
			var dialog = art.dialog.open('<%=contextPath%>/sysmgr/role/allocPage?userId=' + row.id, 
			{
				title: '分配角色',
				opacity: 0.87,	// 透明度,
				width:500,
				height:400,
			    ok: function () {
			    	var win = this.iframe.contentWindow;
			    	
					var roles = win.getSelectRoles();
					if(confirm("是否确定?")){
        					$.post("<%=contextPath%>/sysmgr/role/alloc",{'userId':row.id,'roles':roles},
		        		    function(json){
						    	if(json.result){
						    		art.dialog({
			        				    icon: 'succeed',
			        				    content: '分配成功!'
			        				});
						    		dialog.close();
						    	}
						    	$('#grid1').datagrid('reload');
		        			},
		        			"json");
					}
			    },
				cancel: true,
			    okVal:"保存"
			});
		}
		else{
			$.messager.alert("系统提示","请选择记录!");
		}
	}
	
	function allocBoxGroup(){
		var row = $('#grid1').datagrid('getSelected');
		
		if(row!=null){
			var dialog = art.dialog.open('<%=contextPath%>/bromgr/boxGroup/allocPage?userId=' + row.id, 
			{
				title: '分配广告机组',
				opacity: 0.87,	// 透明度,
				width:500,
				height:400,
			    ok: function () {
			    	var win = this.iframe.contentWindow;
			    	
					var selectObj = win.getSelectNodes();
			    	
					if(confirm("是否确定?")){
        					$.post("<%=contextPath%>/bromgr/boxGroup/alloc",
		        		    {
		        				userId : row.id,
		        				boxIds : selectObj.boxIds,
		        				groupIds : selectObj.groupIds
		        		    },
		        		    function(json){
						    	if(json.result){
						    		art.dialog({
			        				    icon: 'succeed',
			        				    content: '分配成功!'
			        				});
						    	}
		        			},
		        			"json");
					}
			    },
				cancel: true,
			    okVal:"保存"
			});
		}
		else{
			$.messager.alert("系统提示","请选择记录!");
		}
	}
	
	function showDialogEnt(id){
		detailDialog = art.dialog.open('<%=contextPath%>/taxesadmin/ent/detail/' + id, 
		{
			title: '企业信息',
			opacity: 0.87,	// 透明度,
			width:510,
			height:410,
		    ok: function () {
		    	var win = this.iframe.contentWindow;
		    	
		    	if(win.validate()){
					var form = win.document.getElementById("form1");
			    	
		        	$.ajax({
	        			type:"post",
	        			url:"<%=contextPath%>/taxesadmin/ent/save",
	        		    data:$(form).serializeArray(),
	        		    async:false,
	        		    success:function(json){
	        		    	result = json.result;
	        		    	
					    	if(json.result){
					    		$('#grid1').datagrid('reload');
					    	}
					    	else{
					    		alert(json.msg);
					    	}
	        			},
	        			dataType:"json"
	        	});
		    	}
		    	else{
		    		return false;
		    	}
		    },
			cancel: true,
		    okVal:"保存"
		});
	}
		
		function appendEnt(){
			showDialogEnt(-1);
		}
		
		function editEnt(){
			var row = $('#grid1').datagrid('getSelected');
			
			if(row!=null){
				showDialogEnt(row.id);
			}
			else{
				$.messager.alert("系统提示","请点击要修改的记录!");
			}
		}
	
</script>
</head>
<body class="easyui-layout">
	<div region="north" title="查询" style="height:65px;margin-top:5px;">
	<form id="searchForm" method="post" action="" target="_blank">
    <table>  
    	<tr>
    		<td>用户名称:</td>
    		<td><input type="text" id="userName" class="input-text"/></td>
    		<td>
    			<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="query()">查询</a>
    			<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="searchForm.reset()">清空</a>
    		</td>
    	</tr>
   	</table>
	</form>
	</div>
	<div data-options="region:'center'">
		<div id="tb" style="height:auto">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendEnt()">新增企业用户</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="edit()">修改登陆信息</a>
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="editEnt()">修改企业用户</a> -->
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeRow()">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="allocRole()">分配角色</a> 
		</div>
		<table fit="true" id="grid1" title="用户列表"></table>
	</div>
</body>
</html>