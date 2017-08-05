<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>分配菜单</title>
	<jsp:include page="/jsp/inc/meta.jsp"></jsp:include>
	<link  href="<%=contextPath%>/js/zTree/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <script src="<%=contextPath%>/js/zTree/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		//改用ztree,easyui tree中如果设置cascadeCheck，如果代码中父节点设置为checked，则子节点都是checked。
		
		$('#tree1').tree({
		    url: "<%=contextPath%>/admin/newsColumns/allocTree?id=${id}&rnd=" + Math.random(),   
		    method:'get',
		    animate:true,
		    cascadeCheck:false,
		    checkbox:true,
		    onBeforeLoad:function(node, param){
				$.messager.progress({
					title:'请稍等',
					msg:'加载数据中...'
				});
		    },
		    onLoadSuccess:function(node, data){
		    	$.messager.progress('close');
		    }
		});
		
		
		var setting = {
            check: {
                enable: true,
                autoCheckTrigger: true
            },
            async: {
                enable: true,
                url: "<%=contextPath%>/admin/newsColumns/allocTree?idd=${id}&rnd=" + Math.random(),   
                autoParam: ["id"],
                type: "post"
            },
            data: {
                key: {
                    name: "text"
                },
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId"
                }
            }
        };
		
 		$.fn.zTree.init($("#tree1"), setting);
	});
	
	function getSelectMenus(){
		//var nodes = $('#tree1').tree('getChecked');
		 var treeObj = $.fn.zTree.getZTreeObj("tree1");
         var nodes = treeObj.getCheckedNodes(true);
            
		var checkedItems = new Array();
		
		for(var i=0;i<nodes.length;i++){
			checkedItems.push(nodes[i].id);
		}
		
		return checkedItems.join(",");
	}
	</script>
</head>
<body>
	<ul class="ztree" id="tree1"></ul>
</body>
</html>