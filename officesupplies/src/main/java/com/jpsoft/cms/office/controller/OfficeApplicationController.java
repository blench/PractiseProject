package com.jpsoft.cms.office.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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

import com.jpsoft.cms.office.entity.OfficeApplication;

import com.jpsoft.cms.office.entity.OfficeSupplies;
import com.jpsoft.cms.office.entity.User;
import com.jpsoft.cms.office.service.OfficeApplicationService;
import com.jpsoft.cms.office.service.OfficeSuppliesService;

@Controller
@RequestMapping(value="/office/application")
public class OfficeApplicationController {
	
	@Autowired
	private OfficeApplicationService officeApplicationService;
	
	@Autowired
	private OfficeSuppliesService officeSuppliesService;
	
	@RequestMapping(value="")
	public String index(Model model)
	{
		return "/office/application/list";
	}
	
	
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> list(
			@RequestParam(value="page",defaultValue="1")int pageNumber,
			@RequestParam(value="rows",defaultValue = "10")int pageSize,
			@RequestParam(value="subject",defaultValue="")String name)
	{
		Map<String,Object> searchParams = new HashMap<String,Object>();
		if(StringUtils.isNoneEmpty(name))
		{
			searchParams.put("LIKE_subject","%"+ name + "%");
		}
		
		Page<OfficeApplication> page = officeApplicationService.pageSearch(searchParams, pageNumber, pageSize, "createTime");
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("total",page.getTotalElements());
		resultMap.put("rows",page.getContent());
		return new ResponseEntity(resultMap,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="detail/{id}")
	public String detail(@PathVariable("id") String id,Model model)
	{
		model.addAttribute("officeApplication", officeApplicationService.get(id));
		return "/office/application/detail";
	}
	
	@RequestMapping(value="savee",method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> save(
			@Valid @ModelAttribute("officeApplication") OfficeApplication officeApplication,
			@RequestParam(value = "checkedItems", defaultValue = "") String checkedItems,
			HttpServletRequest request)
	{
		
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		HttpSession session = request.getSession(false);
		User user = null;
		if(session!=null)
		{
			user = (User) session.getAttribute("user");
			officeApplication.setCreator(user.getUsername());
		}
		System.out.println("checkedItems:"+checkedItems);
		if(checkedItems!=null)
		{
			
			String[] str = checkedItems.split(",");
			List<OfficeSupplies> list = officeSuppliesService.findByIds(Arrays.asList(str));
			Set<OfficeSupplies> set = new HashSet<OfficeSupplies>();
			for(OfficeSupplies office:list)
			{
				set.add(office);
			}
			System.out.println("set:"+set);
			officeApplication.setOffices(set);
		}
		
		if(officeApplication!=null)
		{
			officeApplication.setCreateTime(new Date());
			
			officeApplicationService.save(officeApplication);
			resultMap.put("result", "1");
		}
		else
		{
			resultMap.put("result", "0");
		}
		

		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="saveChecked",method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> saveChecked(String id,String isverify)
	{
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		System.out.println("id:"+id);
		System.out.println("isverify:"+isverify);
		if(StringUtils.isNoneEmpty(id))
		{
			OfficeApplication officeApplication = officeApplicationService.get(id);
			officeApplication.setIsverify(isverify);
			officeApplicationService.save(officeApplication);
			resultMap.put("result",true);
		}
		else
		{
			resultMap.put("result",false);
		}
		
		return new ResponseEntity(resultMap,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="select")
	public String select()
	{
		return "/office/application/select";
	}
	
	@RequestMapping(value="selectList")
	@ResponseBody
	public ResponseEntity<?> selectList()
	{
		List<OfficeSupplies> list = officeSuppliesService.findAll();
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		//resultMap.put("", value)
		return new ResponseEntity(list,HttpStatus.OK);
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="getselectresult",produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<?> getSeslectResult(
			@RequestParam(value = "checkedItems", defaultValue = "") String checkedItems,Model model)
	{
		String [] arr = checkedItems.split(",");
		
		for(String str:arr)
		{
			System.out.print(str+"\t");
		}
		
		List<OfficeSupplies> list = officeSuppliesService.findByIds(Arrays.asList(arr));
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		System.out.println("list:"+list);
		resultMap.put("total", list.size());
		resultMap.put("rows", list);
		resultMap.put("result", true);
		model.addAttribute("suppliesList",list);
		//记住这里要清空数据缓存
		//list.clear();
		System.out.println("resultMap:"+resultMap);
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	@RequestMapping(value="check/{id}")
	public String check(@PathVariable("id")String id,Model model)
	{
		OfficeApplication office = null;
		System.out.println(id);
		if(StringUtils.isNoneEmpty(id))
		{
			office = officeApplicationService.get(id);
		}
		System.out.println("office:"+office);
		model.addAttribute("office", office);
		return "/office/application/check";
	}
	
	@RequestMapping(value="showSelect/{checkedItems}")
	public ResponseEntity<?> showSelect(@PathVariable("checkedItems")String checkedItems,Model model)
	{
		String [] arr = checkedItems.split(",");
		for(String str:arr)
		{
			System.out.print(str+"\t");
		}
		
		List<OfficeSupplies> suppliesList = officeSuppliesService.findByIds(Arrays.asList(arr));
		model.addAttribute("suppliesList", suppliesList);
		return new ResponseEntity(HttpStatus.OK);
		
	}
}
