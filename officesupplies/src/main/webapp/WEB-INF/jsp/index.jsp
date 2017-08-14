<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><spring:message code="siteName"/></title>
	<link rel="shortcut icon" href="<%=path%>/favicon.ico" type="image/x-icon" />
	<link rel="icon"  type="image/x-icon" href="<%=path%>/favicon.ico" />
	<style>
		/*读取menu菜单的图标项并设置为类*/
		<c:forEach items="${iconMenuList}" var="menu">
		.icon-${menu.id},.tree-node .icon-${menu.id}{
			background:url('<%=path%>/menuIcons/${menu.iconUrl}') no-repeat center center;
			background-size:16px 16px;
		}
		</c:forEach>
	</style>
	<jsp:include page="/jsp/inc/meta.jsp"></jsp:include>
	<script type="text/javascript">
	$(document).ready(function(){
		$.mTab = $("#indexTabs");
		
		addTab($.mTab, {
			url : "<%=path%>/home",
			title : "首页",
			closable:true
		});
	});
	
	window.onresize = function(){
		//调整tab里的iframe大小
		
	}
	
	function clickTreeNode(node){
    	var url = node.attributes.url;
    	var target=  node.attributes.target;
    	
    	if(url!=null && url.length!=0){
    		if(target!=null && target=='_blank'){
    			//弹出窗口
    			window.open("<%=path%>" + url);
    		}
    		else{
				addTab($.mTab, {
					url : "<%=path%>" + url,
					title : node.text,
					iconCls : node.iconCls
				});
    		}
    	}
	}
	
	function logout(){
		if(confirm("确认退出?")){
			window.location = "<%=path%>/logout";
		}
	}
	</script>
</head>
<body class="easyui-layout">
	<div id="header" data-options="region:'north',border:false">
		<div id="siteInfoDiv" style="position: absolute; left: 20px; top: 20px;font-family:微软雅黑,黑体;font-size:24px;font-weight:bold;color:white;">
		<spring:message code="siteName"/>
		</div>
		<div id="infoDiv" style="position: absolute; right: 0px; top: 0px;text-align:left;padding-right:10px;padding-bottom:0;height:58px;width:300px;" class="alert alert-info">
			<div id="sessionInfoDiv" style="font-size:14px;font-weight:bold;">
				欢迎您:【${sessionScope.user.username}】
			</div>
			<div style="margin-top:8px;">
				<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu'">控制面板</a>
			</div>
		</div>
		<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
			<div onclick="modifyPersonInfo()" iconCls="icon-user">个人信息</div>
			<div class="menu-sep"></div>
			<div onclick="logout()" iconCls="icon-off">注销</div>
		</div>
	</div>
	<div region="west" split="true" title="导航菜单" style="width:180px;">
		<div class="easyui-accordion" fit="true">
		
		<c:forEach items="${menuList}" var="menu">
			<!--  data-options="iconCls:'icon_${menu.id}'" -->
			<div title="${menu.name}" data-options="iconCls:'icon-${menu.id}'">
				<div style="padding:5px;">
		    		<ul class="easyui-tree" data-options="url:'<%=path%>/sysmgr/menu/loadTree?position=left&root=${menu.id}',onClick:clickTreeNode"></ul>
		    	</div>
		    </div>
		    <script type="text/javascript">
				//alert("${menu}");
			</script>
		</c:forEach>
		</div>
	</div>
	<div region="center" >
		<div id="indexTabs" class="easyui-tabs" border="false" plain="true" fit="true"></div>
	</div>
	<div id="footer" data-options="region:'south',border:false"><p style="margin-top:2px;">Copyright &copy; 2015 jpsoft Inc. All Rights Reserved.</p></div>
</body>
</html>