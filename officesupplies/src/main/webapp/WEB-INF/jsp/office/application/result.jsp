<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="com.jpsoft.cms.office.entity.User" %>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath+"/";
%>
<table>
	
		<c:forEach var="item" items="${suppliesList}">
			<tr>
				<td>${item.name }</td>
				<td>${item.brand }</td>
				<td>${item.provider }</td>
			</tr>
		</c:forEach>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
</table>