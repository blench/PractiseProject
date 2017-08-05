<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String contextPath = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${menu.name}管理</title>
<jsp:include page="/jsp/inc/meta.jsp"></jsp:include>
<script>
	$(document).ready(function(){
		//去掉最后一个逗号
		$('#grid1').datagrid({
		    url:'<%=contextPath%>/admin/news/list?menuCode=${menu.code}',   
		    columns:[[
				{field:'id',checkbox:true},
			    {field:'title',title:'新闻标题',width:400},
			    {field:'createTime',title:'创建时间',width:180,
			    	formatter:function(value,row,index){
				        var obj = new Date(value);
				        return obj.format("yyyy-MM-dd hh:mm:ss"); 
	                }
			    },
			    {field:'author',title:'发布人',width:100},
			    {field:'hits',title:'点击数',width:100}
		    ]],
		    rownumbers:true,
		    singleSelect:true,
		    selectOnCheck:false,
		    checkOnSelect:false,
		    pagination:true,
		    pageList:[5,10,20,50],
		    pageSize:20,
		    toolbar: '#tb',
		    fit:true
		});
	});
	
	function showDialog(id){
		var detailDialog = art.dialog.open('<%=contextPath%>/admin/news/detail/' + id + "?menuCode=${menu.code}", 
		{
			title: '新闻编辑',
			opacity: 0.87,	// 透明度,
			width:800,
			height:500,
		    ok: function () {
		    	var win = this.iframe.contentWindow;
		    	
		    	if(win.validate()){
					var form = win.document.getElementById("form1");
			    	var result = false;
			    	
		        	$.ajax({
		        			type:"post",
		        			url:"<%=contextPath%>/admin/news/savee",
		        		    data:win.getFormData(),
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
		        	
		        	return result;
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
			title:$("#title").val()
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
				
				$.post("<%=contextPath%>/admin/news/delete",{
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
</script>
</head>
<body class="easyui-layout">
	<div region="north" title="查询" style="height:65px;margin-top:5px;">
		<form id="searchForm" method="post" action="" target="_blank">
		    <table>  
		    	<tr>
		    		<td>名称:</td>
		    		<td><input type="text" id="title" name="title" class="input-text" style="width:200px;"/></td>
		    		<td>
		    			<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="query()">查询</a>
		    			<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="searchForm.reset()">清空</a>
		    		</td>
		    	</tr>
	    	</table>
		</form>
	</div>
	<div data-options="region:'center'" title="${menu.name}>新闻列表">
		<div id="tb" style="height:auto">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="edit()">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeRow()">删除</a>
		</div>
		<table fit="true" id="grid1"></table>
	</div>
</body>
</html>