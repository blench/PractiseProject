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
	<title>新闻编辑</title>
	<jsp:include page="/jsp/inc/meta.jsp"></jsp:include>
	<script type="text/javascript" src="<%=contextPath%>/js/xheditor/xheditor-1.2.1.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/js/xheditor/xheditor_lang/zh-cn.js"></script>
	<script>
	function validate(){
		return $("#form1").form('validate');
	}
	
	$(document).ready(function(){
		$('#content').xheditor({
			tools:'Blocktag,Fontface,FontSize,Bold,Italic,Underline,Strikethrough,FontColor,BackColor,|,Align,List,|,Link,Img,|,Source,Fullscreen',
			upImgUrl:"<%=contextPath%>/admin/news/upload",
			upImgExt:"jpg,jpeg,gif,png",
			upLinkUrl:"<%=contextPath%>/admin/news/upload",
			upLinkExt:"zip,rar,txt,doc,xls,ppt",
			html5Upload : false
		});
		
	});
	
	function getFormData(){
		var data = $("#form1").serializeArray();
		//alert(data[2].value);
		//alert(data);
		return data;
	}
	
	
	function selectLanmu()
	{
		art.dialog.open('<%=contextPath%>/admin/news/tree', 
				{
					title: '选择栏目',
					opacity: 0.87,	// 透明度,
					width:400,
					height:300,
					button:[]
				}
		);
		//alert($("#parantId").val());
	}

	function retObj(obj){
		$("#parentId").val(obj.id);
		$("#name").val(obj.name);
		//alert($("#parentId").val());
		
	}
	</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="padding:10px;">
			<form id="form1" method="post">
				<input type="hidden" name="id" value="<c:out value="${news.id}"/>"/>
				<div class="tbl_padding">
				<table width="100%" height="100%">
					<tr>
						<td>新闻标题:</td>
						<td>
						<input type="text" name="title" class="easyui-validatebox" value="<c:out value="${news.title}"/>" />
						</td>
					</tr>
					<tr>
						<td>所属栏目:</td>
						<td>
						<input id="name" name="name" value="<c:out value="${name}"/>"></input>
						<a href="javascript:selectLanmu()">选择</a>
						<input type="hidden" id="parentId" name="parentId"/>
	    				<input type="hidden" id="name"/>
						</td>
					</tr>
					<tr>
						<td>新闻内容:</td>
						<td>
						<textarea id="content" name="content" rows="25" cols="80"><c:out value="${news.content}"/></textarea>
						</td>
					</tr>
					<tr>
						<td>创建时间:</td>
						<td>
						<label id="creatTime" name="creatTime"><c:out value="${news.createTime}"/></label>
						</td>
					</tr>
					<tr>
						<td>创建人:</td>
						<td>
						<label id="creator" name="creator"><c:out value="${news.creator}"/></label>
						</td>
					</tr>
				</table>
				</div>
			</form>
	</div>
</div>
</body>
</html>