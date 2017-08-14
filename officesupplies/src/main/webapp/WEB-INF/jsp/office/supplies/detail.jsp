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
	
	
	function setPicUrl(value)
	{
		$("input[name='pic']").val(value);	
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
	
	
	
	
	function getFormData(){
		var data = $("#form1").serializeArray();
		return data;
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
						<td>名称:</td>
						<td>
						<input type="text" name="name" class="easyui-validatebox" value="<c:out value="${officeSupplies.name}"/>" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td>规格:</td>
						<td>
						<input type="text" name="spec" class="easyui-validatebox" value="<c:out value="${officeSupplies.spec}"/>"  data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td>单位:</td>
						<td>
						<input type="text" name="unit" class="easyui-validatebox" value="<c:out value="${officeSupplies.unit}"/>"  data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td>品牌:</td>
						<td>
						<input type="text" name="brand" class="easyui-validatebox" value="<c:out value="${officeSupplies.brand}"/>"  data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td>供应商:</td>
						<td>
						<input type="text" name="provider" class="easyui-validatebox" value="<c:out value="${officeSupplies.provider}"/>"  data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td>类别：</td>
						<td>
							<select id="type" name="type"class="easyui-combobox">
								<option value="消耗品">消耗品</option>
								<option value="固定资产">固定资产</option>
							</select>
							<script>
								$("#type").val("${officeSupplies.type}");
							</script>
						</td>
					</tr>
					<tr>
						<td>图片:</td>
						<td>
						<input type="text" name="pic" id="pic" class="easyui-validatebox" value="<c:out value="${officeSupplies.pic}"/>"  data-options="required:true"/>
						<a href="javascript:uploadPic()">上传</a>
						</td>
					</tr>
				</table>
				</div>
			</form>
	</div>
</div>
</body>
</html>