package com.jpsoft.cms.sysmgr.security;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("frameperms")
public class FramePermissionsAuthorizationFilter extends PermissionsAuthorizationFilter{
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws IOException {
		boolean permitted = false;
		
		Subject subject = SecurityUtils.getSubject();
		
		HttpServletRequest req = (HttpServletRequest) request;

		//uri不会带参数
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		
		int i=uri.indexOf(contextPath);
		
		if(i>-1){
			uri=uri.substring(i+contextPath.length());
		}
		
		if(StringUtils.isBlank(uri)){
			uri="/";
		}
		
		if("/".equals(uri)){
			 permitted=true;
		 }else{
			 /*
			 	将所有菜单放入role中
				已将所有能访问的菜单访问地址放入permisssion中
			 	如/sysmgr/menu,/sysmgr/menu/detail,/sysmgr/menu/save
			 	如果访问url包含在菜单中则判断
			 */
			//会调用realm的doGetAuthorizationInfo授权方法
			if (subject.hasRole(uri)) {
				permitted = subject.isPermitted(uri);
			}
			else{
				permitted = true;
			}
		 }
		
		return permitted;
	}
}