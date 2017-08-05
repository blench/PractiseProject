package com.jpsoft.cms.admin.controller;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpsoft.cms.admin.entity.Lanmu;
import com.jpsoft.cms.admin.entity.LanmuType;
import com.jpsoft.cms.admin.entity.News;
import com.jpsoft.cms.admin.service.LanMuService;
import com.jpsoft.cms.admin.service.LanmuTypeService;
import com.jpsoft.cms.admin.service.NewsService;
import com.jpsoft.cms.sysmgr.entity.SysMenu;
import com.jpsoft.cms.sysmgr.entity.SysRole;
import com.jpsoft.cms.sysmgr.entity.SysUser;
import com.jpsoft.cms.sysmgr.service.MenuService;
import com.jpsoft.cms.sysmgr.service.PopedomService;
import com.jpsoft.cms.utils.CodeUtils;
import com.jpsoft.cms.utils.Constants;
import com.jpsoft.cms.utils.ImageSpek;

@Controller
@RequestMapping(value = "/admin/news")
public class NewsController {
	@Autowired
	private NewsService newsService;
	@Autowired
	private LanMuService lanmuService;

	@Autowired
	private PopedomService popedomService;
	
	@Autowired
	private LanmuTypeService lanmuTypeService;
	@RequestMapping(value = "")
	public String index(Model model) {
//		SysMenu menu = menuService.findByCode(menuCode);
//		
//		model.addAttribute("menu", menu);
//		System.out.println("菜单的类型"+menu.toString());
		
		return "/admin/news/list";
	}


	@RequestMapping(value = "list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> list(
			@RequestParam(value = "menuCode", defaultValue = "") String menuCode,
			@RequestParam(value = "page", defaultValue = "1") int pageIndex,
			@RequestParam(value = "rows", defaultValue = "10") int pageSize,
			@RequestParam(value = "title", defaultValue = "") String title) {
		
		
		
		@SuppressWarnings("rawtypes")
		Page<Map> page = newsService.pageSearch("%" + title + "%", pageIndex, pageSize);
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", page.getTotalElements());
		resultMap.put("rows", page.getContent());
		long totalElements = page.getTotalElements();
		List<Map> content = page.getContent();
		System.out.println("totalElements"+totalElements);
		System.out.println("content"+content);
//		System.out.println(page.getContent().toString());
//		System.out.println("page"+totalElements);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

	@RequestMapping(value = "detail/{id}")
	public String detail(@PathVariable("id") String id, Model model) {
			News news = newsService.get(id);
			String lanmuId = "";
			Lanmu lanmu = null;
			String name= "";
			if(news!=null)
			{
				lanmu = news.getLanmuId();
				lanmuId = lanmu.getId();
				lanmu = lanmuService.get(lanmuId);
				name = lanmu.getName();
//				System.out.println("栏目id:"+lanmuId);
//				System.out.println("栏目名："+name);
//				System.out.println("name="+name);
			}
			
			
		if (news==null) {
			news = new News();	
			
		}
		
		
		
//		news.setMenu(menu);
//		news.setLanmuId(lanmuId);
		model.addAttribute("news",news);
		model.addAttribute("name",name);
	
		return "/admin/news/detail";
	}
	
	@RequestMapping(value = "allocPage")
	public String allocPage(@RequestParam("newsId") String newsId, Model model) {
//		model.addAttribute("lanmuId", lanmuId);
		model.addAttribute("newsId",newsId);
//		System.out.println("lanmuId="+lanmuId);
//		System.out.println("newsId="+newsId);
		return "/admin/news/alloc";
	}
	
	@RequestMapping(value = "savee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> save(News news,@RequestParam("parentId")String id) {
		
		
		News target = null;
		Lanmu lanmu = null;
		if(StringUtils.isNotEmpty(news.getId())){
			target = newsService.get(news.getId());
			if(StringUtils.isNotEmpty(id))
			{
				id = id.replace(",", "");
				lanmu =	lanmuService.get(id);
			}
			else
			{
				lanmu = target.getLanmuId();
			}
			
			try {
				CodeUtils.extendPojo(target, news);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			Subject subject = SecurityUtils.getSubject();
			SysUser user = (SysUser) subject.getPrincipal();
			news.setAuthor(user.getRealName());
			news.setCreateTime(new Date());
			news.setCreator(user.getName());
			
			if(StringUtils.isNotEmpty(id))
			{
				lanmu = lanmuService.get(id);
			}
			
			news.setLanmuId(lanmu);
			target = news;
		}
//		System.out.println("系统菜单的ID"+news.getMenu().toString());
		newsService.save(target);

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", "1");

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
		
		newsService.deleteByIds(Arrays.asList(arr));

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", true);

		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

	
	
	@RequestMapping(value = "allocList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> allocList(
			@RequestParam("lanmuId") String lanmuId,
			@RequestParam(value = "page", defaultValue = "1") int pageIndex,
			@RequestParam(value = "rows", defaultValue = "10") int pageSize){		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		
		//加入查询条件 如searchParams.put("LIKE_name", "%" + userName + "%");
		
		Page<Lanmu> page = lanmuService.pageSearch(searchParams,
								pageIndex, pageSize, lanmuId);
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Lanmu> lanmuList = page.getContent();
		
//		Set<String> roleSet = popedomService.findRolesByUserId(lanmuId);
		Set<String> lanmuSet =  lanmuService.findTypeByLanmuId(lanmuId);
		
		List<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
		
		for (Lanmu lanmu : lanmuList) {
			HashMap<String,Object> map = new HashMap<String, Object>();
			
			map.put("id", lanmu.getId());
			map.put("name", lanmu.getName());
			map.put("sortNo", lanmu.getSortNo());
//			System.out.println("栏目的id： "+lanmu.getId());
//			System.out.println("栏目的名称： "+lanmu.getName());
			if ((lanmuSet).contains(lanmu.getName())) {
				map.put("checked", true);
			}
			else{
				map.put("checked", false);
			}
			
			mapList.add(map);
		}
		
		resultMap.put("total", page.getTotalElements());
		//这里要和页面中的rows相对应
		resultMap.put("rows", mapList);
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	@RequestMapping(value = "alloc", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> alloc(String newsId,String lanmuId) {
		Lanmu lanmu = lanmuService.get(lanmuId);
		News news  = newsService.get(newsId);
//		System.out.println("栏目是否为空："+lanmu==null);
//		System.out.println("id是否为空："+lanmu.getId());
		if(news.getSortNo()!=null)
		{
			lanmu.setSortNo(lanmu.getSortNo()+1);
		}
		
		String id = lanmu.getId();
//		System.out.println(id==null);
		news.setLanmuId(lanmu);
//		List<News> newsList = newsService.findNewsByLanmuId(lanmuId);
//		System.out.println(newsList==null);
		newsService.save(news);
		
////		lanmuService.save(lanmuType);
//		popedomService.addUserRoleRelation(userId,roles.split(","));
//		SysUser sysUser=userService.get(userId);
//		for(String roleId:roles.split(",")){
//			SysRole role=roleService.get(roleId);
//			if("ent".equals(role.getName())){
//				sysUser.setUserType(2);
//			}else if("admin".equals(role.getName())){
//				sysUser.setUserType(1);
//			}
//			userService.save(sysUser);
//		}
		
//		Subject subject = SecurityUtils.getSubject();
//		SysUser user = (SysUser)subject.getPrincipal();
//		sysLogService.save(user.getName(), "分配权限");
		
		//更新缓存
//		shiroEhcacheManager.getCache(Constants.SHIRO_CACHE_NAME).clear();
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", true);
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

	@RequestMapping(value="tree")
	public String tree()
	{
		return "admin/news/tree";
	}
	
	@RequestMapping(value="loadTree",produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void loadTree(@RequestParam(value = "root", defaultValue = "0") String root,
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
			if (!("树状菜单").equals(menu.getDisplayType())){
				continue;
			}
			
//			if(!menu.getBianma().equals("01"))
//			{
//				continue;
//			}
//			
			map.put("id", menu.getId());
			
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
	
	/*
	 * 
	 * 图片上传
	 */
	@RequestMapping(value="/upload" ,method={RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<?> upload(MultipartFile filedata,HttpServletRequest request) throws Exception
	{
		String path = request.getSession().getServletContext().getRealPath("/upload");
		
		Calendar calendar = Calendar.getInstance();
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		
		path += File.separator + year +File.separator + month;
		
		String fileName = filedata.getOriginalFilename();
		
		String ext = fileName.substring(fileName.lastIndexOf("."));
		
		String newFileName = System.currentTimeMillis() + ext;
		
		File dir = new File(path);
		if(!dir.exists())
		{
			dir.mkdirs();
		}
		
		File targetFile = new File(path,newFileName);
		
		//保存
		try
		{
			filedata.transferTo(targetFile);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//如果是图片且长或宽大于1024则压缩
		if((".jpg").equals(ext.toLowerCase()))
		{
			String outputFileName = System.currentTimeMillis() + "_resize" + ext;
			String result = ImageSpek.compressPic(path,path,newFileName,outputFileName,1024,1024,true);
			
			if(("ok").equals(result))
			{
				//删除原文件
				targetFile.delete();
				newFileName = outputFileName;
			}
		}
		
		String pathName = request.getContextPath() + "/upload/" +year +"/" + month +"/" + newFileName;
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("errz", "");
		resultMap.put("msg",pathName);
		return new ResponseEntity(resultMap,HttpStatus.OK); 
	}

}
