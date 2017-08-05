<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单管理</title>
<jsp:include page="/jsp/inc/meta.jsp"></jsp:include>
<script>
	var detailDialog;
	
	$(document).ready(function(){
		$('#grid1').datagrid({
		    url:'<%=contextPath%>/sysmgr/menu/list',   
		    columns:[[
			{field:'id',checkbox:true},
			{field:'sortNo',title:'序号'},
		    {field:'code',title:'菜单编码',width:200},
		    {field:'name',title:'菜单名称',width:100},
		    {field:'parentName',title:'上级菜单',width:100},
		    {field:'type',title:'菜单类型',width:100},
		    {field:'pageUrl',title:'访问地址',width:200},
		    {field:'displayType',title:'显示类型',width:200},
		    {field:'target',title:'目标',width:200},
		    {field:'iconUrl',title:'图标',width:200}
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
		detailDialog = art.dialog.open('<%=contextPath%>/sysmgr/menu/detail/' + id, 
		{
			title: '菜单信息',
			opacity: 0.87,	// 透明度,
			width:500,
			height:400,
		    ok: function () {
		    	var win = this.iframe.contentWindow;
		    	
		    	if(win.validate()){
					var form = win.document.getElementById("form1");
			    	var result = false;
					
					$.ajax({
						  type: "POST",
						  url: "<%=contextPath%>/sysmgr/menu/save",
						  data:$(form).serializeArray(),
						  async:false,
						  success: function(json){
						  	if(json.result){
						    		$('#grid1').datagrid('reload');
						    		detailDialog.close();
							}
							else{
								alert(json.msg);
							}
						  	
						  	result = json.result;
						  },
						  dataType: "json"
					});
					
					if(!result){
						return false;
					}
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
			parentId: $("#parentId").val(),
			parentCode: $("#parentCode").val(),
			menuName: $("#menuName").val(),
			includeAll:$("#includeAll").attr("checked") ? "1" : ""
		});
	}
	
	function selectParent(){
		art.dialog.open('<%=contextPath%>/sysmgr/menu/tree', 
				{
					title: '选择上级模块',
					opacity: 0.87,	// 透明度,
					width:400,
					height:300,
					button:[]
				}
		);
	}
	
	function retObj(obj){
		$("#parentId").val(obj.menuId);
		$("#parentName").val(obj.menuName);
		$("#parentCode").val(obj.menuCode);
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
				
				$.post("<%=contextPath%>/sysmgr/menu/delete",{
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
	
	function genCode(){
		if(confirm("是否确认?")){
			$.post("<%=contextPath%>/sysmgr/menu/genCode",{parentId:$("#parentId").val()},function(json){
				if(json.result){
					$('#grid1').datagrid('reload');
				}
				else{
					$.messager.alert("系统提示","生成编码失败!");
				}
			},"json");
		}
	}
</script>
</head>
<body class="easyui-layout">
	<div region="north" title="查询" style="height:65px;margin-top:5px;">
	<form id="searchForm" method="post" action="" target="_blank">
	    <table>  
	    	<tr>
	    		<td>菜单名称:</td>
	    		<td><input type="text" id="menuName" class="input-text"/></td>
	    		<td>上级菜单:</td>
	    		<td>
	    			<input type="text" id="parentName" class="input-text"/>
	    			<a href="javascript:selectParent()">选择</a>
	    			&nbsp;
	    			<input type="hidden" id="parentId"/>
	    			<input type="hidden" id="parentCode"/>
	    			<input type="checkbox" id="includeAll"/>包含所有子菜单
	    			&nbsp;
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
		<div id="tb" style="height:auto">
			<shiro:hasPermission name="/sysmgr/menu/save">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">新增</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="/sysmgr/menu/edit">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="edit()">修改</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="/sysmgr/menu/delete">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeRow()">删除</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="/sysmgr/menu/genCode">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="genCode()">根据序号生成编码</a>
			</shiro:hasPermission>
		</div>
		<table id="grid1" title="菜单列表"></table>
	</div>
</body>
</html>