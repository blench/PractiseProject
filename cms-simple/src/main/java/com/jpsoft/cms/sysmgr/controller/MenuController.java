package com.jpsoft.cms.sysmgr.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.subject.Subject;
import org.json.JSONArray;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpsoft.cms.sysmgr.entity.SysMenu;
import com.jpsoft.cms.sysmgr.entity.SysUser;
import com.jpsoft.cms.sysmgr.service.MenuService;
import com.jpsoft.cms.sysmgr.service.PopedomService;
import com.jpsoft.cms.sysmgr.service.SysLogService;
import com.jpsoft.cms.utils.CodeUtils;
import com.jpsoft.cms.utils.Constants;

@Controller
@RequestMapping(value = "/sysmgr/menu")
public class MenuController {
	@Resource
	private MenuService menuService;

	@Autowired
	private PopedomService popedomService;
	
	@Autowired
	private EhCacheManager shiroEhcacheManager;
	@Autowired
	private SysLogService sysLogService;
	
	@RequestMapping(value = "")
	public String index() {
		return "/sysmgr/menu/list";
	}

	@RequestMapping(value = "list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> list(
			@RequestParam(value = "page", defaultValue = "1") int pageIndex,
			@RequestParam(value = "rows", defaultValue = "10") int pageSize,
			@RequestParam(value = "menuName", defaultValue = "") String menuName,
			@RequestParam(value = "parentId", defaultValue = "") String parentId,
			@RequestParam(value = "parentCode", defaultValue = "") String parentCode,
			@RequestParam(value = "includeAll", defaultValue = "") String includeAll
			) {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (StringUtils.isNoneEmpty(menuName)) {
			searchParams.put("LIKE_name", "%" + menuName + "%");
		}
		
		if (StringUtils.isEmpty(includeAll)) {
			if (StringUtils.isNoneEmpty(parentId)) {
				searchParams.put("EQ_parent.id", parentId);
			}
		}
		else{
			if (StringUtils.isNoneEmpty(parentCode)) {
				searchParams.put("LIKE_parent.code", parentCode + "%");
			}
		}
		
		//不显示根目录
		searchParams.put("GT_id", "0");
		
		Page<SysMenu> modules = menuService.pageSearch(searchParams,
				pageIndex, pageSize, "code");

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", modules.getTotalElements());
		resultMap.put("rows", modules.getContent());

		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

	@RequestMapping(value = "detail/{id}")
	public String detail(@PathVariable("id") String menuId, Model model) {
		model.addAttribute("menu", menuService.get(menuId));

		return "/sysmgr/menu/detail";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> save(
			@Valid @ModelAttribute("menu") SysMenu menu) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		if(menu.getParent()==null || StringUtils.isEmpty(menu.getParent().getId())){
			SysMenu rootMenu = menuService.get("0");
			menu.setParent(rootMenu);
		}
		
		DecimalFormat df = new DecimalFormat("00");
		
		if (menu.getParent().getId().equals("0")) {
			menu.setCode(df.format(menu.getSortNo()));
		}
		else{
			menu.setCode(menu.getParent().getCode() + df.format(menu.getSortNo()));
		}
		
		//查询分组编码是否重复
		if(menuService.exist(menu.getId(),menu.getCode())){
			resultMap.put("result",false);
			resultMap.put("msg", "菜单编码重复!\r\n原因：同级菜单下序号有重复,请修改!");
		}
		else{
			SysMenu target = menuService.get(menu.getId());
			
			try {
				if(target!=null){
					CodeUtils.extendPojo(target, menu);
				}
				else{
					target = menu;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			menuService.save(target);
	
			resultMap.put("result", true);
			Subject subject = SecurityUtils.getSubject();
			SysUser user = (SysUser)subject.getPrincipal();
			sysLogService.save(user.getName(), "添加或修改菜单："+target.getName());
		}

		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 当提价form中有id属性，就会执行该方法.
	 */
//  (避免数据库中读取对象后，在查询时强制更新对象)
//	@ModelAttribute
//	public void preparable(@RequestParam(value = "id", defaultValue = "") String menuId,Model model) {
//		if (StringUtils.isNotEmpty(menuId)) {
//			SysMenu menu = menuService.get(menuId);
//			
//			if (menu!=null) {
//				model.addAttribute("menu", menu);
//			}
//		}
//	}

	@RequestMapping(value = "delete", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> delete(
			@RequestParam(value = "checkedItems", defaultValue = "") String checkedItems) {

		String[] arr = checkedItems.split(",");

		menuService.deleteByIds(Arrays.asList(arr));

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", true);
		Subject subject = SecurityUtils.getSubject();
		SysUser user = (SysUser)subject.getPrincipal();
		sysLogService.save(user.getName(), "删除菜单："+checkedItems);

		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	@RequestMapping(value = "tree")
	public String tree(){
		return "/sysmgr/menu/tree";
	}

	@RequestMapping(value = "loadTree", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void loadTree(
			@RequestParam(value = "root", defaultValue = "0") String root,
			@RequestParam(value = "id", defaultValue = "") String parentId,
			@RequestParam(value = "position", defaultValue = "") String position,
			HttpServletResponse response) {
		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (parentId.length()==0){
			searchParams.put("EQ_parent.id", root);
		}
		else{
			searchParams.put("EQ_parent.id", parentId);
		}

		Page<SysMenu> modules = menuService.pageSearch(searchParams, 1,100, "code");

		List<HashMap<String, Object>> mapList = new ArrayList<HashMap<String, Object>>();
		
		Subject subject = SecurityUtils.getSubject();
		
		SysUser user = (SysUser)subject.getPrincipal();
		
		List<SysMenu> allocMenuList = popedomService.findMenuByUserId(user.getId());
	
		for (SysMenu menu : modules.getContent()) {
			HashMap<String, Object> map = new HashMap<String, Object>();

			if (position.equals("left")) {
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
			}
			
			//只显示树状菜单
			if (!menu.getDisplayType().equals("树状菜单")) {
				continue;
			}
			
			map.put("id", menu.getId());
			
			if (position.equals("left")) {
				map.put("text",menu.getName());
			}
			else{
				map.put("text",menu.getName() + "[" + menu.getCode() + "]");	
			}

			HashMap<String, Object> attrMap = new HashMap<String, Object>();

			attrMap.put("menuName", menu.getName());
			attrMap.put("menuCode", menu.getCode());
			attrMap.put("url", menu.getPageUrl());
			attrMap.put("target", menu.getTarget());

			map.put("attributes", attrMap);
			
			//是否有子节点
            long count = 0;
            
//            if (position.equals("left")) {
        	count = menuService.getChildrenCount(menu.getId(),"树状菜单");
//            }
//            else{
//            	count = menuService.getChildrenCount(menu.getId());
//            }
            	
            if (count>0) {
                map.put("state", "closed");
            }
            else {
            	map.put("state", "open");
            }
            
            if (StringUtils.isNotEmpty(menu.getIconUrl())) {
            	map.put("iconCls", "icon-" + menu.getId());
			}

			mapList.add(map);
			System.out.println("MenuControl 中mapList的值是："+mapList);
		}

		try {
			PrintWriter out = response.getWriter();

			//JSONArray arr = new JSONArray(mapList);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(mapList);
			out.write(json);
			out.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 根据序号生成编码
	 */
	@RequestMapping(value = "genCode",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> generateCode(@RequestParam(value = "id", defaultValue = "0") String parentId){
		HashSet<String> menuSet = new HashSet<String>();
		genCodeLoop(parentId,menuSet);
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", true);

		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 递归生成编码
	 * @param parentId
	 * @param menuSet
	 */
	private void genCodeLoop(String parentId,Set<String> menuSet){
		if (!menuSet.contains(parentId)) {
			SysMenu parentMenu = menuService.get(parentId);
			Set<SysMenu> children = parentMenu.getChildren();
			
			menuSet.add(parentId);
			
			for (SysMenu menu : children) {
				if (menu.getSortNo()!=null) {
					DecimalFormat df = new DecimalFormat("00");
					
					if (parentId.equals("0")) {
						menu.setCode(df.format(menu.getSortNo()));
					}
					else{
						menu.setCode(parentMenu.getCode() + df.format(menu.getSortNo()));
					}
					
					menuService.save(menu);
				}
				
				genCodeLoop(menu.getId(),menuSet);
			}
		}
	}
	
	@RequestMapping(value = "allocPage")
	public String allocPage(@RequestParam("roleId") String roleId, Model model) {
		model.addAttribute("roleId",roleId);
		
		return "/sysmgr/menu/alloc";
	}
	
	@RequestMapping(value = "allocTree", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void allocTree(@RequestParam("roleId") String roleId,HttpServletResponse response){
		//遍历生成所有菜单树
		ArrayList<HashMap<String,Object>> nodeList = new ArrayList<HashMap<String,Object>>();
		
		List<SysMenu> menuList = popedomService.findMenuByRoleId(roleId);
		
		HashSet<String> allocMenuSet = new HashSet<String>();
		
		for (SysMenu menu : menuList) {
			allocMenuSet.add(menu.getId());
		}
		
		HashSet<String> menuSet = new HashSet<String>();
		
		buildAllocTree("0",allocMenuSet,menuSet,nodeList);
		
		try {
			PrintWriter out = response.getWriter();

			JSONArray arr = new JSONArray(nodeList);

			out.write(arr.toString());
			out.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void buildAllocTree(String parentId, HashSet<String> allocMenuSet,HashSet<String> menuSet,ArrayList<HashMap<String,Object>> nodeList) {
		// TODO Auto-generated method stub
		if (!menuSet.contains(parentId)) {
			SysMenu parentMenu = menuService.get(parentId);
			
			if (parentMenu!=null) {
				menuSet.add(parentMenu.getId());	
								
				if (parentMenu.getChildren()!=null) {
//					ArrayList<HashMap<String,Object>> childList = new ArrayList<HashMap<String,Object>>();
//					map.put("children", childList);
									
					for(SysMenu subMenu : parentMenu.getChildren()){
						HashMap<String,Object> map = new HashMap<String, Object>();
						
						map.put("id",subMenu.getId());
						map.put("text", subMenu.getName());
						map.put("open", true);
						
						if(subMenu.getParent()!=null){
							map.put("pId", parentMenu.getId());
						}
						
						if (allocMenuSet.contains(subMenu.getId())) {
							map.put("checked", true);
						}

						nodeList.add(map);
						
						buildAllocTree(subMenu.getId(),allocMenuSet,menuSet,nodeList);
					}
				}
			}	
		}
	}

	@RequestMapping(value = "alloc", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> alloc(@RequestParam("roleId") String roleId,
							@RequestParam("menuIds") String menuIds,Model model) {
		popedomService.addRoleMenuRelation(roleId,menuIds.split(","));
		
		//更新缓存
		shiroEhcacheManager.getCache(Constants.SHIRO_CACHE_NAME).clear();
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", true);

		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	@RequestMapping(value = "uploadIconPage", method = {RequestMethod.GET })
	public String uploadIconPage(){
		return "/sysmgr/menu/uploadIcon";
	}
	
	@RequestMapping(value = "uploadIcon", method = {RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile filedata,HttpServletRequest request)
			throws IOException {
		String path = request.getSession().getServletContext().getRealPath("/menuIcons");

		Calendar calendar = Calendar.getInstance();
		
		String fileName = filedata.getOriginalFilename();
		
		String ext = fileName.substring(fileName.lastIndexOf("."));
		
		String newFileName = calendar.getTimeInMillis() + ext;
		
		File dir = new File(path);
		
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		File targetFile = new File(path, newFileName);
				
		// 保存
		try {
			filedata.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String iconUrl = newFileName;
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result",true);
		resultMap.put("iconUrl", iconUrl);
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
}