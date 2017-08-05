package com.jpsoft.cms.sysmgr.security;

import java.util.Date;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springside.modules.security.utils.Cryptos;
import org.springside.modules.utils.Encodes;

import com.jpsoft.cms.sysmgr.entity.SysLog;
import com.jpsoft.cms.sysmgr.entity.SysUser;
import com.jpsoft.cms.sysmgr.service.SysLogService;
import com.jpsoft.cms.sysmgr.service.UserService;

@Component("aesCredentialsMatcher")
public class AESCredentialsMatcher implements CredentialsMatcher {
	@Autowired
	private UserService userService;
	
	@Autowired
	private SysLogService logService;
	
	public static final String KEY = "jpsoft0123456789";
	
	private static Logger logger = LoggerFactory.getLogger(AESCredentialsMatcher.class);
	
	@Override
	public boolean doCredentialsMatch(AuthenticationToken authcToken,
			AuthenticationInfo ai) {
		// TODO Auto-generated method stub
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		SysUser user = (SysUser)ai.getPrincipals().getPrimaryPrincipal();
		
		boolean match = false;
		
		if (token.getUsername().equals(user.getName())) {
			try{
				byte[] encPwd = Encodes.decodeHex(user.getPassword());
				String plainPwd = Cryptos.aesDecrypt(encPwd,KEY.getBytes("UTF-8"));
				
				if (plainPwd.equals(String.valueOf(token.getPassword()))) {
					match = true;
					
					user.setLastVisitDate(new Date());
					user.setRemoteAddr(token.getHost());
					
					userService.save(user);
					
					//记录日志
					logService.save(user.getName(),"登录成功!");
				}
				else{
					logService.save(user.getName(),"登录失败!密码错误!");
				}
			}
			catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}

		return match;
	}
}