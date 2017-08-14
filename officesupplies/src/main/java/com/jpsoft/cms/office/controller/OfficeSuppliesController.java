package com.jpsoft.cms.office.controller;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
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

import com.jpsoft.cms.office.entity.OfficeSupplies;
import com.jpsoft.cms.office.entity.User;
import com.jpsoft.cms.office.service.OfficeSuppliesService;
import com.jpsoft.cms.utils.ImageSpek;



@Controller
@RequestMapping("/office/supplies")
public class OfficeSuppliesController {
	
	@Resource(name="officeSuppliesService")
	private OfficeSuppliesService officeSuppliesService;
	
	@RequestMapping("")
	public String index(Model model)
	{
		return "/office/supplies/list";
	}
	
	
	@RequestMapping(value="list",method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> list(
			@RequestParam(value="page",defaultValue="1")int pageNumber,
			@RequestParam(value="rows",defaultValue = "10")int pageSize,
			@RequestParam(value="name",defaultValue="")String name)
	{
		Map<String,Object> searchParams = new HashMap<String,Object>();
		if(StringUtils.isNoneEmpty(name))
		{
			searchParams.put("LIKE_name", "%"+name+"%");
		}
		
		Page<OfficeSupplies> page = officeSuppliesService.pageSearch(searchParams, pageNumber, pageSize, "");
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
	
		resultMap.put("total", page.getTotalElements());
		resultMap.put("rows", page.getContent());
		System.out.println("resultMap的值:"+resultMap);
		return new ResponseEntity(resultMap,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "detail/{id}")
	public String detail(@PathVariable("id") String id, Model model) {
		model.addAttribute("officeSupplies", officeSuppliesService.get(id));

		return "/office/supplies/detail";
	}
	
	
	@RequestMapping(value = "savee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> save(
			@Valid @ModelAttribute("officeSupplies") OfficeSupplies officeSupplies,HttpServletRequest request) {	
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HttpSession session = request.getSession(false);
		User user = null;
		if(session!=null)
		{
			user = (User) session.getAttribute("user");
			officeSupplies.setCreator(user.getUsername());
		}
		
		if(officeSupplies!=null)
		{
			officeSupplies.setCreateTime(new Date());
			
			officeSuppliesService.save(officeSupplies);
			resultMap.put("result", "1");
		}
		else
		{
			resultMap.put("result", null);
		}
		

		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	

	@RequestMapping(value = "deletee", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> delete(
			@RequestParam(value = "checkedItems", defaultValue = "") String checkedItems) {

		String[] arr = checkedItems.split(",");
		System.out.println(arr[0]);
		officeSuppliesService.deleteByIds(Arrays.asList(arr));
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", true);

		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	@RequestMapping(value="uploadPicPage",method = {RequestMethod.GET})
	public String uploadPicPage()
	{
		return "/office/supplies/uploadPic";
	}
	
	/**
	 * 图片上传
	 */
	
	@RequestMapping(value="uploadPic",method ={RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<?> upload(@RequestParam("file")MultipartFile filedata,HttpServletRequest request) throws Exception
	{
		//第一步、得到上传文件的真实路径
		String path = request.getServletContext().getRealPath("/upload");
		
		//使用日历对象
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		
		//第二步、生成路径
		path += File.separator + year + File.separator + month;
		
		String fileName = filedata.getOriginalFilename();
		
		String ext = fileName.substring(fileName.lastIndexOf("."));
		
		String newFileName = System.currentTimeMillis() + ext;
		
		File dir = new File(path);
		if(!dir.exists())
		{
			dir.mkdirs();
		}
		
		//第三步、保存到目标文件
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
			String outputFileName = System.currentTimeMillis() +"_resize" + ext;
			String result = ImageSpek.compressPic(path,path,newFileName,outputFileName,1024,1024,true);
			
			if(("ok").equals(result))
			{
				//删除原文件
				targetFile.delete();
				newFileName = outputFileName;
			}
		}
		
		String pathName = request.getContextPath()+ "/upload" +year + "/" + month + "/" + newFileName;
		String picUrl = pathName;
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("result",true);
		resultMap.put("picUrl", picUrl);
		return new ResponseEntity(resultMap,HttpStatus.OK);
	}
}
