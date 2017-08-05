package com.jpsoft.cms.admin.controller;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpsoft.cms.admin.entity.Lanmu;
import com.jpsoft.cms.admin.entity.LanmuType;
import com.jpsoft.cms.admin.service.LanMuService;
import com.jpsoft.cms.admin.service.LanmuTypeService;
import com.jpsoft.cms.sysmgr.entity.SysMenu;
import com.jpsoft.cms.sysmgr.entity.SysUser;
import com.jpsoft.cms.sysmgr.service.SysLogService;
import com.jpsoft.cms.utils.CodeUtils;

@Controller
@RequestMapping(value = "/admin/newsColumns")
public class NewsLanMuController {
	@Autowired
	private LanMuService lanmuService;
	@Autowired
	private LanmuTypeService lanmuTypeService;
	@Autowired
	private SysLogService sysLogService;
	@RequestMapping(value = "")
	public String index(Model model) {
//		SysMenu menu = menuService.findByCode(menuCode);
//		
//		model.addAttribute("menu", menu);
//		System.out.println("菜单的类型"+menu.toString());
//		List<LanmuType> lanmuTypes = lanmuTypeService.findAll();
//		model.addAllAttributes(lanmuTypes);
		return "/admin/newsColumns/list";
	}
	
	@RequestMapping(value = "combox")
	public String combox()
	{
		return "/admin/newsColumns/combox";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "combobox", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> combobox(@RequestParam("id")String id,String name)
	{
		List<LanmuType> lanmuTypes = lanmuTypeService.findAll();
		List<HashMap<String, Object>> resultMap = new ArrayList<HashMap<String,Object>>();
		LanmuType lanmuType = null;
		if(StringUtils.isNoneEmpty(id))
		{
			lanmuType = lanmuTypeService.get(id);
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("id",lanmuType.getId());
			map.put("name",lanmuType.getName());
			resultMap.add(map);
		}else
		{
			for(LanmuType lanType:lanmuTypes)
			{
				HashMap<String ,Object> map = new HashMap<String,Object>();
				if(("0").equals(lanType.getId()))
				{
					continue;
				}
				map.put("id", lanType.getId());
				map.put("name", lanType.getName());
				resultMap.add(map);
			}
		}
		System.out.println("lanmuTypes的值"+lanmuTypes);
		return new ResponseEntity(resultMap,HttpStatus.OK);
	}
	
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> list(
			@RequestParam(value = "currentLanmu", defaultValue = "") String currentLanmu,
			@RequestParam(value = "parentId", defaultValue = "") String parentId,
			@RequestParam(value = "page", defaultValue = "1") int pageIndex,
			@RequestParam(value = "rows", defaultValue = "10") int pageSize,
			@RequestParam(value = "includeAll", defaultValue = "") String includeAll,
			@RequestParam(value = "name", defaultValue = "") String name) {
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(StringUtils.isNoneEmpty(name))
		{
			searchParams.put("LIKE_name", "%"+name+"%");
		}
		if (StringUtils.isEmpty(includeAll)) {
			if (StringUtils.isNoneEmpty(parentId)) {
				searchParams.put("EQ_parent.id", parentId);
			}
		}
		else{
			if (StringUtils.isNoneEmpty(currentLanmu)) {
				searchParams.put("LIKE_parent.currentLanmu","%"+currentLanmu + "%");
			}
		}
		
		
		//不显示根目录
		searchParams.put("GT_id", "0");
		
		Page<Lanmu> page = lanmuService.pageSearch(searchParams,
				pageIndex, pageSize,"bianma");
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", page.getTotalElements());
		resultMap.put("rows", page.getContent());
		System.out.println(page.getContent());
		System.out.println(page.getTotalPages());
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

	@RequestMapping(value = "detail/{id}")
	public String detail(@RequestParam(value = "bianma", defaultValue = "") String bianma,@PathVariable("id") String id, Model model) {
		Lanmu lanmu = lanmuService.get(id);
		LanmuType lanmuType = null;
		String name = "";
		if(lanmu!=null)
		{
			lanmuType = lanmu.getLanmuTypeId();
			name = lanmuType.getName();
		}		
		model.addAttribute("lanmu",lanmu);
		model.addAttribute("name1",name);
		model.addAttribute("lanmuType",lanmuType);
		return "/admin/newsColumns/detail";
	}


	@RequestMapping(value = "savee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> save(Lanmu lanmu,@RequestParam("lanTypeId")String lanmuTypeId) {
		
		if(lanmu.getParent()==null || StringUtils.isEmpty(lanmu.getParent().getId())){
			Lanmu rootMenu = lanmuService.get("0");
			lanmu.setParent(rootMenu);
		}
			
		Lanmu target = null;
		LanmuType lanmuType = null;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		DecimalFormat df = new DecimalFormat("00");

		if (lanmu.getParent().getId().equals("0")) {
			lanmu.setBianma(df.format(lanmu.getSortNo()));
		}
		else{
			System.out.println(lanmu.getParent().getBianma());
			lanmu.setBianma(lanmu.getParent().getBianma() + df.format(lanmu.getSortNo()));
		}
		

		//查询分组编码是否重复
		if(lanmuService.exist(lanmu.getId(),lanmu.getBianma())){
			resultMap.put("result",false);
			resultMap.put("msg", "菜单编码重复!\r\n原因：同级菜单下序号有重复,请修改!");
		}
		else{
			
			target = lanmuService.get(lanmu.getId());
			try {
				if(target!=null){
					CodeUtils.extendPojo(target, lanmu);
				}
				else{
					target = lanmu;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if(StringUtils.isNotEmpty(lanmuTypeId))
			{
				lanmuTypeId = lanmuTypeId.replace(",", "");
				lanmuType = lanmuTypeService.get(lanmuTypeId);
			}
			Subject subject = SecurityUtils.getSubject();
			SysUser user = (SysUser) subject.getPrincipal();
			

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
			Date date = new Date();

			lanmu.setDisplayType("树状菜单");
			
			if(lanmu.getLanmuTypeId()==null)
			{
				lanmu.setLanmuTypeId(lanmuTypeService.get("402881ea5d7911ac015d7916df600002"));
			}
			lanmu.setCreateTime(date);
			lanmu.setCreator(user.getName());
			lanmu.setLanmuTypeId(lanmuType);
			target = lanmu;
			System.out.println("栏目是否为空"+lanmu==null);
			lanmuService.save(target);
			resultMap.put("result", "1");
			sysLogService.save(user.getName(), "添加或修改菜单："+target.getName());
		}
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "deletee", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> delete(
			@RequestParam(value = "checkedItems", defaultValue = "") String checkedItems) {

		String[] arr = checkedItems.split(",");
		for(String str:arr)
		{
			System.out.println(str+"\t");
		}
		
		lanmuService.deleteByIds(Arrays.asList(arr));

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", true);

		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	@RequestMapping(value = "tree")
	public String tree(){
		return "/admin/newsColumns/tree";
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
			searchParams.put("LIKE_parent.id", root);
		}
		else{
			searchParams.put("LIKE_parent.id", parentId);
		}

		Page<Lanmu> modules = lanmuService.pageSearch(searchParams, 1,100, "bianma");
		System.out.println("modules的值"+modules);

		List<HashMap<String, Object>> mapList = new ArrayList<HashMap<String, Object>>();
		
		Subject subject = SecurityUtils.getSubject();
		
		SysUser user = (SysUser)subject.getPrincipal();
		
		List<Lanmu> allocMenuList = lanmuService.findByParentId(parentId);
		System.out.println("allocMenuList的值为:"+allocMenuList);
		for (Lanmu menu : modules.getContent()) {
			HashMap<String, Object> map = new HashMap<String, Object>();

			if (position.equals("left")) {
				//首页左侧菜单只显示用户分配的菜单
				boolean bAlloc = false;
				
				for (Lanmu lanmu : allocMenuList) {
					if(lanmu.getBianma()!=null && menu.getBianma()!=null && lanmu.getBianma().indexOf(menu.getBianma())==0)
					{
						bAlloc = true;
						break;
					}
				}
				
				if (!bAlloc) {
					continue;
				}
			}
			
			
			//只显示树状菜单
			if (!("树状菜单").equals(menu.getDisplayType())) {
				continue;
			}
			map.put("id", menu.getId());
			
//			if(!menu.getBianma().equals("01"))
//			{
//				continue;
//			}
//			
			
			
			if (position.equals("left")) {
				map.put("text",menu.getName());
			}
			else{
//				map.put("text",menu.getName() + "[" + menu.getCode() + "]");	
				map.put("text",menu.getName()+"["+menu.getBianma()+"]");
			}

			HashMap<String, Object> attrMap = new HashMap<String, Object>();

			attrMap.put("name", menu.getName());
//			attrMap.put("menuCode", menu.getCode());
//			attrMap.put("pageUrl", menu.getPageUrl());
			attrMap.put("bianma", menu.getBianma());
//			attrMap.put("target", menu.getTarget());
			attrMap.put("id",menu.getId());
			attrMap.put("sortNO", menu.getSortNo());
			
			map.put("attributes", attrMap);
			
			//是否有子节点
			System.out.println("attrMap的值:"+attrMap);
            long count = 0;
            
            if (position.equals("left")) {
            	count = lanmuService.getChildrenCount(menu.getId(),"树状菜单");
            }
            else{
            	count = lanmuService.getChildrenCount(menu.getId());
            }
            	
            if (count>0) {
                map.put("state", "closed");
            }
            else {
            	map.put("state", "open");
            }
            
//            if (StringUtils.isNotEmpty(menu.getIconUrl())) {
//            	map.put("iconCls", "icon-" + menu.getId());
//			}

			mapList.add(map);
			System.out.println("NewsControl 中mapList的值是："+mapList);
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
	
	
	@RequestMapping(value = "allocPage")
	public String allocPage(@RequestParam("roleId") String roleId, Model model) {
		model.addAttribute("roleId",roleId);
		
		return "/sysmgr/menu/alloc";
	}
	
	
	/**
	 * 根据序号生成编码
	 */
	@RequestMapping(value = "genCode",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> generateCode(@RequestParam(value = "id", defaultValue = "0") String parentId){
		HashSet<String> lanmuSet = new HashSet<String>();
		genCodeLoop(parentId,lanmuSet);
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", true);

		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	
	
	/**
	 * 递归生成编码
	 * @param parentId
	 * @param menuSet
	 */
	
	private void genCodeLoop(String parentId,Set<String> lanmuSet){
		if (!lanmuSet.contains(parentId)) {
			Lanmu parentLanmu = lanmuService.get(parentId);
			Set<Lanmu> children = parentLanmu.getChildren();
			
			lanmuSet.add(parentId);
			
			for (Lanmu lanmu : children) {
				if (lanmu.getSortNo()!=null) {
					DecimalFormat df = new DecimalFormat("00");
					
					if (parentId.equals("0")) {
						lanmu.setBianma(df.format(lanmu.getSortNo()));
					}
					else{
						lanmu.setBianma(lanmu.getBianma() + df.format(lanmu.getSortNo()));
					}
					
					lanmuService.save(lanmu);
				}
				
				genCodeLoop(lanmu.getId(),lanmuSet);
			}
		}
	}
	
	
	/*@RequestMapping(value = "allocTree", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void allocTree(@RequestParam("id") String idd,HttpServletResponse response){
		//遍历生成所有菜单树
		ArrayList<HashMap<String,Object>> nodeList = new ArrayList<HashMap<String,Object>>();
		
		List<SysMenu> menuList = null;
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
//		popedomService.addRoleMenuRelation(roleId,menuIds.split(","));
//		
//		//更新缓存
//		shiroEhcacheManager.getCache(Constants.SHIRO_CACHE_NAME).clear();
//		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", true);

		return new ResponseEntity(resultMap, HttpStatus.OK);
	}*/
}
