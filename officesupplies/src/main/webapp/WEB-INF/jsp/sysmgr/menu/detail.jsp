<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>菜单信息</title>
	<jsp:include page="/jsp/inc/meta.jsp"></jsp:include>
	<script>	
	function selectParent(){
		art.dialog.open('<%=contextPath%>/sysmgr/menu/tree', 
				{
					title: '选择上级菜单',
					opacity: 0.87,	// 透明度,
					width:400,
					height:300,
					button:[]
				}
		);
	}
	
	function setIconUrl(value){
		$("input[name='iconUrl']").val(value);
	}
	
	function uploadIcon(){
		art.dialog.open('<%=contextPath%>/sysmgr/menu/uploadIconPage', 
				{
					title: '上传图标',
					opacity: 0.87,	// 透明度,
					width:400,
					height:300,
					cancel: true
				}
		);
	}
	
	function retObj(obj){
		$("#parentName").val(obj.menuName);
		$("input[name='parent.id']").val(obj.menuId);
		$("input[name='parent.code']").val(obj.menuCode);
	}
	
	function validate(){
		return $("#form1").form('validate');
	}
	</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="padding:10px;">
		<div data-options="title:'菜单信息'">
			<form id="form1" method="post">
				<input type="hidden" name="id" value="<c:out value="${menu.id}"></c:out>"/>
				<div class="tbl_padding">
				<table>
					<tr>
						<td>菜单名称：</td>
						<td><input type="text" name="name" class="easyui-validatebox" value="<c:out value="${menu.name}"></c:out>" data-options="required:true"/></td>
					</tr>
					<tr>
						<td>上级菜单:</td>
						<td>
							<input id="parentName" type="text" name="parent.name" class="easyui-validatebox" value="<c:out value="${menu.parent.name}"></c:out>"/>
							<input type="hidden" name="parent.id" value="<c:out value="${menu.parent.id}"></c:out>"/>
							<input type="hidden" name="parent.code" value="<c:out value="${menu.parent.code}"></c:out>"/>
							<a href="javascript:selectParent()">选择</a>
						</td>
					</tr>
					<tr>
						<td>序号:</td>
						<td><input type="text" name="sortNo" class="easyui-validatebox" value="<c:out value="${menu.sortNo}"></c:out>"/></td>
					</tr>
					<tr>
						<td>类型:</td>
						<td>
							<select id="displayType" name="displayType"class="easyui-combobox">
								<option value="树状菜单">树状菜单</option>
								<option value="按钮">按钮</option>
							</select>
							<script>
								$("#displayType").val("${menu.displayType}");
							</script>
						</td>
					</tr>
					<tr>
						<td>访问地址:</td>
						<td><input type="text" name="pageUrl" class="easyui-validatebox" value="<c:out value="${menu.pageUrl}"></c:out>" style="width:300px;"/></td>
					</tr>	
					<tr>
						<td>目标:</td>
						<td><input type="text" name="target" class="easyui-validatebox" value="<c:out value="${menu.target}"></c:out>"/></td>
					</tr>
					<tr>
						<td>图标:</td>
						<td>
							<input type="text" name="iconUrl" class="easyui-validatebox" value="<c:out value="${menu.iconUrl}"></c:out>"/>					
							<a href="javascript:uploadIcon()">上传</a>
						</td>
					</tr>													
				</table>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>