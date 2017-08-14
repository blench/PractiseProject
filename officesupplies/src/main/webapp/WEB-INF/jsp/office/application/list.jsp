<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String contextPath = request.getContextPath();%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="com.jpsoft.cms.office.entity.User" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	User user = (User)session.getAttribute("user");
	if(user==null)
	{%>
		<script type="text/javascript">
			window.location = "<%=path%>/login";
		</script>
	<%}
	
%>
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
		    url:'<%=contextPath%>/office/application/list',   
		    columns:[[
				{field:'id',checkbox:true},
			    {field:'subject',title:'主题',width:100},
			    {field:'applicant',title:'申请人',width:100},
			    {field:'sortNo',title:'排序',width:100},
			    {field:'createTime',title:'创建时间',width:100,
			    	formatter:function(value,row,index){
				        var obj = new Date(value);
				        return obj.format("yyyy-MM-dd hh:mm:ss"); 
	                }
			    },
			    {field:'creator',title:'创建者',width:100},
			    {field:'isverify',title:'审核状态',width:100},
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
		var detailDialog = art.dialog.open('<%=contextPath%>/office/application/detail/'+id, 
		{
			title: '办公用品编辑',
			opacity: 0.87,	// 透明度,
			width:800,
			height:300,
		    ok: function () {
		    	var win = this.iframe.contentWindow;
		    	
		    	if(win.validate()){
					var form = win.document.getElementById("form1");
			    	var result = false;
		        	$.ajax({
		        			type:"post",
		        			url:"<%=contextPath%>/office/application/savee/",
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
			name:$("#subject").val()
		});
	}
	
	function append(){
		showDialog(-1);
	}
	
	function edit(){
		var row = $('#grid1').datagrid('getSelected');
	
		if(row!=null){
			showDialogCheck(row.id);
		}
		else{
			$.messager.alert("系统提示","请点击要修改的记录!");
		}
	}
	
	//注意这里才是保存审核状态的地方
	function showDialogCheck(id){
		var detailDialog = art.dialog.open('<%=contextPath%>/office/application/check/'+id, 
		{
			title: '办公用品审批',
			opacity: 0.87,	// 透明度,
			width:500,
			height:300,
		    ok: function () {
		    	var win = this.iframe.contentWindow;
		    	
		    	if(win.validate()){
					var form = win.document.getElementById("form1");
			    	var result = false;
		        	$.ajax({
		        			type:"post",
		        			url:"<%=contextPath%>/office/application/saveChecked/",
		        		    data:win.getFormData(),
		        		    async:false,
		        		    success:function(json){
		        		    	result = json.result;
						    	if(json.result){
						    		$('#grid1').datagrid('reload');
						    	}
						    	else{
						    		//alert(json.result);
						    		//$("#grid1").close();
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

</script>
</head>
<body class="easyui-layout">
	<div region="north" title="查询" style="height:65px;margin-top:5px;">
		<form id="searchForm" method="post" action="" target="_blank">
		    <table>  
		    	<tr>
		    		<td>主题:</td>
		    		<td><input type="text" id="subject" name="subject" class="input-text" style="width:200px;"/></td>
		    		<td>
		    			<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="query()">查询</a>
		    			<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="searchForm.reset()">清空</a>
		    		</td>
		    	</tr>
	    	</table>
		</form>
	</div>
	<div data-options="region:'center'" title=">栏目列表">
		<div id="tb" style="height:auto">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">申请办公用品</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="edit()">审核批准</a>
		</div>
		<table fit="true" id="grid1"></table>
	</div>
</body>
</html>