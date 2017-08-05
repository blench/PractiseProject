/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.jpsoft.cms.sysmgr.security;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import com.jpsoft.cms.sysmgr.entity.SysUser;
import com.jpsoft.cms.sysmgr.service.PopedomService;
import com.jpsoft.cms.utils.Constants;

@Component("shiroDbRealm")
public class ShiroDbRealm extends AuthorizingRealm {
	@Resource(name="popedomService")
	protected PopedomService popedomService;

	@Resource(name="aesCredentialsMatcher")
	AESCredentialsMatcher matcher;
	
	 
	public ShiroDbRealm() {
		// 认证缓存(认证是不想缓存的,登陆的时候查询一下数据库也没有什么,所以设置了 false,)
		super.setAuthenticationCachingEnabled(false);
		// 授权缓存(授权是要缓存的,也指定了缓存的名称,主要是为了刷新用户的权限缓存)
		super.setAuthorizationCacheName(Constants.SHIRO_CACHE_NAME);
	}
	
	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		
		SysUser user = popedomService.findByUserName(token.getUsername());

		SimpleAuthenticationInfo ai = new SimpleAuthenticationInfo(user, user.getPassword(), getName());  
		
		return ai;
	}
	
	/**
	 * 设定Password校验方法
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		setCredentialsMatcher(matcher);
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SysUser user = (SysUser) principals.getPrimaryPrincipal();
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		Set<String> allMenuUrl = popedomService.findAllMenuUrl();
		Set<String> allowAccessMenuUrl = popedomService.findMenuUrlByUserId(user.getId());
		
		//首先将所有菜单放入role中
		info.addRoles(allMenuUrl);
		
		//将能访问的菜单放入permission中
		info.addStringPermissions(allowAccessMenuUrl);

		return info;
	}
}
