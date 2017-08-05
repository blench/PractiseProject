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
	<title>用户信息</title>
	<jsp:include page="/jsp/inc/meta.jsp"></jsp:include>
	<script>
	function validate(){
		if($("#form1").form('validate')){
			//判断用户名是否重复
			var exist = false;
			
			$.ajax({
			  url: "<%=contextPath%>/sysmgr/user/exist",
			  async: false,
			  data:{
				userName:$("input[name='name']").val(),
			  	userId:$("input[name='id']").val(),
			  },
			  success: function(json){
				  exist = json.result;
			  },
			  "dataType":"json"
			});
			
			if(exist){
				alert("登录用户名称已存在!");
				return false;
			}
			else{
				return true;
			}
		}
		else{
			return false;
		}
	}
	</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="padding:10px;">
			<form id="form1" method="post">
				<input type="hidden" name="id" value="<c:out value="${user.id}"></c:out>"/>
				<input type="hidden" name="userType" value="1"/>
				<div class="tbl_padding">
				<table>
					<tr>
						<td>用户名称：</td>
						<td>
							<input type="text" name="name" class="easyui-validatebox" value="<c:out value="${user.name}"></c:out>" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						<td>真实名称:</td>
						<td><input type="text" name="realName" class="easyui-validatebox" value="<c:out value="${user.realName}"></c:out>" data-options="required:true"/></td>
					</tr>
					<tr>
						<td>登录密码:</td>
						<td><input type="password" name="password" class="easyui-validatebox" value="<c:out value="${user.plainPassword}"></c:out>" data-options="required:true"/></td>
					</tr>								
				</table>
				</div>
			</form>
	</div>
</div>
</body>
</html>