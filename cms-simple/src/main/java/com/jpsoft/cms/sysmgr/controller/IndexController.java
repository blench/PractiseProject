package com.jpsoft.cms.sysmgr.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jpsoft.cms.sysmgr.entity.SysMenu;
import com.jpsoft.cms.sysmgr.entity.SysUser;
import com.jpsoft.cms.sysmgr.service.MenuService;
import com.jpsoft.cms.sysmgr.service.PopedomService;

@Controller
public class IndexController {
	@Resource
	private MenuService menuService;

	@Autowired
	private PopedomService popedomService;
	
	@RequestMapping(value = "",method = RequestMethod.GET) 
	public String index(Model model){
		Map<String, Object> searchParams = new HashMap<String, Object>();

		searchParams.put("EQ_parent.id", "0");

		Page<SysMenu> modules = menuService.pageSearch(searchParams, 1,100, "code");

		List<SysMenu> menuList = new ArrayList<SysMenu>();
		
		Subject subject = SecurityUtils.getSubject();
		
		SysUser user = (SysUser)subject.getPrincipal();
		
		List<SysMenu> allocMenuList = popedomService.findMenuByUserId(user.getId());

		for (SysMenu menu : modules.getContent()) {
			HashMap<String, Object> map = new HashMap<String, Object>();

			//首页左侧菜单只显示用户分配的菜单
			boolean bAlloc = false;
			
			for (SysMenu allocMenu : allocMenuList) {
				if (allocMenu.getCode()!=null 
				 && menu.getCode()!=null 
				 && allocMenu.getCode().indexOf(menu.getCode())==0) {
					bAlloc = true;
					break;
				}
			}
			
			if (!bAlloc) {
				continue;
			}
			
			//只显示树状菜单
			if (!menu.getDisplayType().equals("树状菜单")) {
				continue;
			}
			
			menuList.add(menu);
		}
		
		model.addAttribute("menuList", menuList);
		
		//读取图标项不为空的菜单
		List<SysMenu> allMenuList = menuService.getAll();
		
		ArrayList<SysMenu> iconMenuList = new ArrayList<SysMenu>();
		
		for (SysMenu sysMenu : allMenuList) {
			if (StringUtils.isNotEmpty(sysMenu.getIconUrl())) {
				iconMenuList.add(sysMenu);
			}
		}
		
		model.addAttribute("iconMenuList", iconMenuList);
		
		return "index";
	}

	@RequestMapping(value = "home",method = RequestMethod.GET) 
	public String home(Model model){
		return "home";
	}
}