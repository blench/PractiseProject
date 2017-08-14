<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
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
<%
	String contextPath = request.getContextPath();
	System.out.println("contextPath:"+contextPath);
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
	
	
	
	function uploadPic(){
		art.dialog.open('<%=path%>/office/supplies/uploadPicPage', 
				{
					title: '上传图片',
					opacity: 0.87,	// 透明度,
					width:400,
					height:300,
					cancel: true
				}
		);
	}
	
	$(document).ready(function(){
		$("#showSelect").hide();
	});
	
	var array = new Array();
	
	function getFormData(){
		$("#checkedItems").val(array);
		var data = $("#form1").serializeArray();
		return data;
	}
	

	
	function select(){
		detailDialog = art.dialog.open('<%=path%>/office/application/select?rnd='+Math.random(),
		{
			title:'选择办公用品',
			opacity:0.87,
			width:400,
			height:300,
			ok:function()
			{
				//得到当前页面的窗口对象，从而调用getSelectRoles()
				var win = this.iframe.contentWindow;
				var checkedItems = new Array();
				//注意要记得清除append(),注意但要保存第一项
				$("#showSelect tr:gt(0)").remove();
				//粗心，少了两个ed应该为checkedItems而我写成了checkItems
				checkedItems = win.getSelectRoles();
				$.ajax({
					type:"post",
					url:"<%=contextPath%>/office/application/getselectresult?rnd="+Math.random(),
					//此处应该注意值是以键值对的方式传递的,这是必须的要求，
					data:'checkedItems='+checkedItems,
					async:false,
					success:function(json){
						result = json.result;
						if(result)
						{
							$("#showSelect").show();
							var arr = new Array();
							arr = json.rows;
							for(var i=0;i<arr.length;i++)
							{
								array.push(arr[i].id);
								$("#showSelect").append("<tr>"+"<td>"+arr[i].name+"</td>"+"<td>"+arr[i].brand+"</td>"+"<td>"+arr[i].provider+"</td>"+"</tr>");
							}
						}
					},
					dataType:"json"	
				});
			},
		cancel:true
		});
		
		
	}
	
	
	
	function getCheckedResult()
	{
		var checkedItems = new Array();
		checkedItems = getSelectRoles();
		//alert(checkedItems);
		var result = false;
		
		$.ajax({
			type:"post",
			url:"<%=contextPath%>/office/application/getselectresult",
			//此处应该注意值是以键值对的方式传递的,这是必须的要求，
			data:'checkedItems='+checkedItems,
			async:false,
			success:function(json){
				result = json.result;
				if(result)
				{
					show(json);
				}
			},
			dataType:"json"
		});
	}
	
	</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="padding:10px;">
			<form id="form1" method="post">
				<input type="hidden" name="id" value="<c:out value="${officeSupplies.id}"/>"/>
				<div class="tbl_padding">
				<table>
					<tr>
						<td>主题:</td>
						<td>
						<input type="text" name="subject" class="easyui-validatebox" value="<c:out value="${officeApplicant.subject}"/>" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td>申请人:</td>
						<td>
						<input type="text" name="applicant" class="easyui-validatebox" value="<c:out value="${officeApplicant.applicant}"/>"  data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td>排序:</td>
						<td>
						<input type="text" name="sortNo" class="easyui-validatebox" value="<c:out value="${officeApplicant.sortNo}"/>"  data-options="required:true"/>
						</td>
					</tr>
				
					<tr>
						<td>
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="javascript:select()">选择</a>
						</td>
					</tr>
					<tr>
						<input type="hidden" name="checkedItems" id="checkedItems"/>
					</tr>
					<table id="showSelect">
						<tr>
							<th>名称</th>
							<th>品牌</th>
							<th>供应商</th>
						</tr>
					</table>
				</table>
				</div>
			</form>
	</div>
</div>
</body>
</html>