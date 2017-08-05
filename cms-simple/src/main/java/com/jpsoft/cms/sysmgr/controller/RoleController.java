package com.jpsoft.cms.sysmgr.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
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

import com.jpsoft.cms.sysmgr.entity.SysRole;
import com.jpsoft.cms.sysmgr.entity.SysUser;
import com.jpsoft.cms.sysmgr.service.PopedomService;
import com.jpsoft.cms.sysmgr.service.RoleService;
import com.jpsoft.cms.sysmgr.service.SysLogService;
import com.jpsoft.cms.sysmgr.service.UserService;
import com.jpsoft.cms.utils.Constants;

@Controller
@RequestMapping(value = "/sysmgr/role")
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PopedomService popedomService;
	
	@Autowired
	private EhCacheManager shiroEhcacheManager;
	@Autowired
	private SysLogService sysLogService;
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value = "")
	public String index() {
		return "/sysmgr/role/list";
	}
	
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> list(
			@RequestParam(value = "page", defaultValue = "1") int pageIndex,
			@RequestParam(value = "rows", defaultValue = "10") int pageSize,
			@RequestParam(value = "userName", defaultValue = "") String userName){		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		
		//加入查询条件 如
		searchParams.put("LIKE_name", "%" + userName + "%");
		
		Page<SysRole> page = roleService.pageSearch(searchParams,
								pageIndex, pageSize, "createDate");
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", page.getTotalElements());
		resultMap.put("rows", page.getContent());
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	@ModelAttribute
	public void preparable(@RequestParam(value = "id", defaultValue = "") String id,Model model) {
		if (StringUtils.isNotEmpty(id)) {
			SysRole role = roleService.get(id);
			
			if (role!=null) {
				model.addAttribute("role",  role);
			}
		}
	}
	
	@RequestMapping(value = "detail/{id}")
	public String detail(@PathVariable("id") String id, Model model) {
		model.addAttribute("role", roleService.get(id));

		return "/sysmgr/role/detail";
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> save(
			@Valid @ModelAttribute("role") SysRole role) {	
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		if(role!=null)
		{
			roleService.save(role);
			resultMap.put("result", "1");
		}
		else
		{
			resultMap.put("result", null);
		}
		
				
		Subject subject = SecurityUtils.getSubject();
		SysUser user = (SysUser)subject.getPrincipal();
		sysLogService.save(user.getName(), "添加或修改角色");

		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	@RequestMapping(value = "delete", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> delete(
			@RequestParam(value = "checkedItems", defaultValue = "") String checkedItems) {

		String[] arr = checkedItems.split(",");

		roleService.deleteByIds(Arrays.asList(arr));

		//更新缓存
		shiroEhcacheManager.getCache(Constants.SHIRO_CACHE_NAME).clear();
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", true);
		
		Subject subject = SecurityUtils.getSubject();
		SysUser user = (SysUser)subject.getPrincipal();
		sysLogService.save(user.getName(), "删除角色");

		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	@RequestMapping(value = "allocPage")
	public String allocPage(@RequestParam("userId") String userId, Model model) {
		model.addAttribute("userId", userId);
		
		return "/sysmgr/role/alloc";
	}
	
	@RequestMapping(value = "allocList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> allocList(
			@RequestParam("userId") String userId,
			@RequestParam(value = "page", defaultValue = "1") int pageIndex,
			@RequestParam(value = "rows", defaultValue = "10") int pageSize){		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		
		//加入查询条件 如searchParams.put("LIKE_name", "%" + userName + "%");
		
		Page<SysRole> page = roleService.pageSearch(searchParams,
								pageIndex, pageSize, "createDate");
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		List<SysRole> roleList = page.getContent();
		
		Set<String> roleSet = popedomService.findRolesByUserId(userId);
		
		List<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
		
		for (SysRole role : roleList) {
			HashMap<String,Object> map = new HashMap<String, Object>();
			
			map.put("id", role.getId());
			map.put("name", role.getName());
			map.put("description", role.getDescription());
			
			if (roleSet.contains(role.getName())) {
				map.put("checked", true);
			}
			else{
				map.put("checked", false);
			}
			
			mapList.add(map);
		}
		
		resultMap.put("total", page.getTotalElements());
		resultMap.put("rows", mapList);
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	@RequestMapping(value = "alloc", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> alloc(@RequestParam("userId") String userId,
							@RequestParam("roles") String roles) {
		
		popedomService.addUserRoleRelation(userId,roles.split(","));
		SysUser sysUser=userService.get(userId);
		for(String roleId:roles.split(",")){
			SysRole role=roleService.get(roleId);
			if("ent".equals(role.getName())){
				sysUser.setUserType(2);
			}else if("admin".equals(role.getName())){
				sysUser.setUserType(1);
			}
			userService.save(sysUser);
		}
		Subject subject = SecurityUtils.getSubject();
		SysUser user = (SysUser)subject.getPrincipal();
		sysLogService.save(user.getName(), "分配权限");
		
		//更新缓存
		shiroEhcacheManager.getCache(Constants.SHIRO_CACHE_NAME).clear();
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", true);
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
}
