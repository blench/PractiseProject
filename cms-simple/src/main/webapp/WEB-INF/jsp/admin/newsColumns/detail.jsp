<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新闻栏目</title>
<jsp:include page="/jsp/inc/meta.jsp"></jsp:include>
<script type="text/javascript"
	src="<%=contextPath%>/js/xheditor/xheditor-1.2.1.min.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/js/xheditor/xheditor_lang/zh-cn.js"></script>
<script>
	function validate() {
		return $("#form1").form('validate');
	}

	function getFormData() {
		var data = $("#form1").serializeArray();
		//alert(data[2].value);
		return data;
	}
	
	/*function selectLanmuType(){
		art.dialog.open('<%=contextPath%>/admin/newsColumns/tree', 
				{
					title: '选择栏目类型',
					opacity: 0.87,	// 透明度,
					width:400,
					height:300,
					button:[]
				}
		);
	}*/
	
	function selectParent(){
		art.dialog.open('<%=contextPath%>/admin/newsColumns/tree', 
				{
					title: '选择上级菜单',
					opacity: 0.87,	// 透明度,
					width:400,
					height:300,
					button:[]
				}
		);
	}
	function retObj(obj){
		$("#parentName").val(obj.name);
		$("input[name='parent.id']").val(obj.id);
		$("input[name='parent.bianma']").val(obj.bianma);
	}
	
	
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false" title=""
			style="padding: 10px;">
			<form id="form1" method="post">
				<input type="hidden" name="id"
					value="<c:out value="${lanmu.id}"/>" />
				<div class="tbl_padding">
					<table>
						<tr>
							<td>栏目名:</td>
							<td><input type="text" name="name"
								class="easyui-validatebox"
								value="<c:out value="${lanmu.name}"/>" class="easyui-validatebox"   data-options="required:true" /></td>
						</tr>
						<tr>
							<td>所属栏目类型:</td>
							<td>
							<input class="easyui-combobox" style="width:180px;" name="lanTypeId" id="lanTypeId"   
				                data-options = "
				                url:'<%=contextPath%>/admin/newsColumns/combobox?id='+$('#lanTypeId').val(),
				                editable:false,
				                valueField:'id',
				                textField:'name',
				                panelHeight:'auto',
				                onLoadSuccess:function(data)
				                {
				                	if(data.length == 0)
				                	{
				                		$('#lanTypeId').val('数据加载中...');
				                	}else
				                	{
				                		$('#lanTypeId').val($('#id'));
				                	}
				        
				                }"
				                 
               				 />
               				 </td>
						</tr>
						<tr>
							<td>序号:</td>
							<td><input type="text" name="sortNo" id="sortNo"
								class="easyui-validatebox"
								value="<c:out value="${lanmu.sortNo}"/>" class="easyui-validatebox"   data-options="required:true" /></td>
						</tr>
						<tr>
							<td>上级菜单:</td>
							<td>
							<input id="parentName" type="text" name="parent.name" class="easyui-validatebox" value="<c:out value="${lanmu.parent.name}"></c:out>"/>
							<input type="hidden" name="parent.id" value="<c:out value="${lanmu.parent.id}"></c:out>"/>
							<input type="hidden" name="parent.bianma" value="<c:out value="${lanmu.parent.bianma}"></c:out>"/>
							<a href="javascript:selectParent()">选择</a>
						</td>
						</tr>
						<tr>
					</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
	function selectLanmuType()
	{
        $('#lanTypeId').combobox({      
            url:"<%=contextPath%>/admin/newsColumns/combox", 
            valueField:'id',      
            textField:'name',  
            method:"post",
            editable:false
        });   
	}
	</script>
</body>
</html>