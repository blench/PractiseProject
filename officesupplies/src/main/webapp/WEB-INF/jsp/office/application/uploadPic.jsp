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
<title>上传图标</title>
<jsp:include page="/jsp/inc/meta.jsp"></jsp:include>
<script type="text/javascript">
function ajaxFileUpload(){
	$.ajaxFileUpload({
		url:'<%=contextPath%>/office/supplies/uploadPic',
		secureuri:false, 
		fileElementId:'file',
		dataType:'json', 
		 success:function(data, status){
			 if(data.result){
				alert("上传成功!");
				artDialog.open.origin.setPicUrl(data.picUrl);
				artDialog.close();
			 }
		 },
		 error:function(data, status, e){ 
			 $.messager.alert("系统提示",data.msg);
		 }
	});
}
</script>
</head>
<body>
	<div id="result"></div>
	<table class="divTable">
		<tr>
			<td>图片选择:</td>
			<td><input type="file" id="file" name="file" />
			 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="ajaxFileUpload()">上传</a> 
		</tr>
	</table>
</body>
</body>
</html>