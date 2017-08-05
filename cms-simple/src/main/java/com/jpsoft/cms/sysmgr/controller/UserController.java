package com.jpsoft.cms.sysmgr.controller;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.security.utils.Cryptos;
import org.springside.modules.utils.Encodes;

import com.jpsoft.cms.sysmgr.entity.SysUser;
import com.jpsoft.cms.sysmgr.security.AESCredentialsMatcher;
import com.jpsoft.cms.sysmgr.service.SysLogService;
import com.jpsoft.cms.sysmgr.service.UserService;

@Controller
@RequestMapping(value = "/sysmgr/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private SysLogService sysLogService;
	
	@RequestMapping(value = "")
	public String index() {
		return "/sysmgr/user/list";
	}
	
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> list(
			@RequestParam(value = "page", defaultValue = "1") int pageIndex,
			@RequestParam(value = "rows", defaultValue = "10") int pageSize,
			@RequestParam(value = "userName", defaultValue = "") String userName){		
		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (StringUtils.isNoneEmpty(userName)) {
			searchParams.put("LIKE_name", "%" + userName + "%");
		}
		
		Page<SysUser> page = userService.pageSearch(searchParams,
								pageIndex, pageSize, "createDate");
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", page.getTotalElements());
		resultMap.put("rows", page.getContent());
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	@RequestMapping(value = "exist", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> exist(
			@RequestParam(value = "userName", defaultValue = "") String userName,
			@RequestParam(value = "userId", defaultValue = "") String userId){
		boolean result = userService.exist(userName,userId);
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("result", result);
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	@ModelAttribute
	public void preparable(@RequestParam(value = "id", defaultValue = "") String id,Model model) {
		if (StringUtils.isNotEmpty(id)) {
			SysUser user = userService.get(id);
			
			if (user!=null) {
				model.addAttribute("user", user);
			}
		}
	}
	
	/**
	 * 根据id得到
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}")
	public String detail(@PathVariable("id") String id, Model model) {
		model.addAttribute("user", userService.get(id));

		return "/sysmgr/user/detail";
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> save(
			@Valid @ModelAttribute("user") SysUser user) {
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("result", "1");
		
		if (user.getCreateDate()==null) {
			user.setCreateDate(new Date());	
		}
		
		//对密码进行加密
		try {
			byte[] encPwd = Cryptos.aesEncrypt(user.getPassword().getBytes("UTF-8"),
							AESCredentialsMatcher.KEY.getBytes("UTF-8"));

			user.setPassword(Encodes.encodeHex(encPwd));} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userService.save(user);
		Subject subject = SecurityUtils.getSubject();
		SysUser sysuser = (SysUser)subject.getPrincipal();
		sysLogService.save(sysuser.getName(), "创建或修改用户："+user.getName());

		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	@RequestMapping(value = "delete", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> delete(
			@RequestParam(value = "checkedItems", defaultValue = "") String checkedItems) {

		String[] arr = checkedItems.split(",");

		userService.deleteByIds(Arrays.asList(arr));

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", true);
		Subject subject = SecurityUtils.getSubject();
		SysUser user = (SysUser)subject.getPrincipal();
		sysLogService.save(user.getName(), "删除用户："+checkedItems);

		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
}