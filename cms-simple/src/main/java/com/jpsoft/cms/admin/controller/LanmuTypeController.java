package com.jpsoft.cms.admin.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.jpsoft.cms.admin.entity.LanmuType;
import com.jpsoft.cms.admin.service.LanmuTypeService;
import com.jpsoft.cms.utils.CodeUtils;

@Controller
@RequestMapping(value = "/admin/columnsType")
public class LanmuTypeController {
	@Autowired
	private LanmuTypeService lanmuTypeService;
	
	
	@RequestMapping(value = "")
	public String index(Model model) {
//		SysMenu menu = menuService.findByCode(menuCode);
//		
//		model.addAttribute("menu", menu);
//		System.out.println("菜单的类型"+menu.toString());
		
		return "/admin/columnsType/list";
	}

	@RequestMapping(value = "list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> list(
			@RequestParam(value = "page", defaultValue = "1") int pageIndex,
			@RequestParam(value = "rows", defaultValue = "10") int pageSize,
			@RequestParam(value = "name", defaultValue = "") String name) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		if (StringUtils.isNoneEmpty(name)) {
			params.put("LIKE_name", "%" + name + "%");
		}
	
 		Page<LanmuType> page = lanmuTypeService.pageSearch(params, pageIndex, pageSize,"shuju");

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", page.getTotalElements());
		System.out.println("page:"+page.getTotalElements());
		resultMap.put("rows", page.getContent());
		System.out.println("栏目类型内容："+page.getContent());
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

	@RequestMapping(value = "detail/{id}")
	public String detail(@RequestParam(value = "menuCode", defaultValue = "") String menuCode,@PathVariable("id") String id, Model model) {
		LanmuType lanmuType = lanmuTypeService.get(id);
			
		
		if (lanmuType==null) {
			lanmuType = new LanmuType();			
		}
		
		
		
//		news.setMenu(menu);
		
		model.addAttribute("lanmuType",lanmuType);
		
		return "/admin/columnsType/detail";
	}

	@RequestMapping(value = "savee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> save(LanmuType lanmuType) {
		LanmuType target = null;
		
		if(StringUtils.isNotEmpty(lanmuType.getId())){
			target = lanmuTypeService.get(lanmuType.getId());

			try {
				CodeUtils.extendPojo(target, lanmuType);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
//			Subject subject = SecurityUtils.getSubject();
//			SysUser user = (SysUser) subject.getPrincipal();
			
			
//			SysMenu menu=new SysMenu();
//			news.setMenu(menu);
			
			lanmuType.setShuju(0);
			target = lanmuType;
		}
//		System.out.println("系统菜单的ID"+news.getMenu().toString());
		lanmuTypeService.save(target);

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
		
		lanmuTypeService.deleteByIds(Arrays.asList(arr));

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", true);

		return new ResponseEntity(resultMap, HttpStatus.OK);
	}

	
}
