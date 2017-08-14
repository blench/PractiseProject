<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择模块</title>
<jsp:include page="/jsp/inc/meta.jsp"></jsp:include>
<script>
$(document).ready(function(){
	
	$('#menuTree').tree({
	    url: "<%=path%>/sysmgr/menu/loadTree?rnd=" + Math.random(),
	    method:'get',
	    animate:true,
	    onClick : function(node) {
	    	var data = {};
	    	
	    	data.menuName = node.attributes.menuName;
	    	data.menuCode = node.attributes.menuCode;
	    	data.menuId = node.id;
	    	
			artDialog.open.origin.retObj(data);
			
			artDialog.close();
		}
	});
});
</script>
</head>
<body>
	<ul id="menuTree"></ul>
</body>
</html>