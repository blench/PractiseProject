package com.jpsoft.cms.sysmgr.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jpsoft.cms.sysmgr.entity.SysLog;
import com.jpsoft.cms.sysmgr.service.SysLogService;

@Controller
@RequestMapping(value = "/sysmgr/log")
public class LogController {
	@Autowired
	private SysLogService sysLogService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value = "")
	public String index() {
		return "/sysmgr/log/list";
	}
	
	@RequestMapping(value = "list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> list(
			@RequestParam(value = "page", defaultValue = "1") int pageIndex,
			@RequestParam(value = "rows", defaultValue = "10") int pageSize,
			@RequestParam(value = "userName", defaultValue = "") String userName,
			@RequestParam(value = "beginTime", defaultValue = "") String beginTime,
			@RequestParam(value = "endTime", defaultValue = "") String endTime){		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		
		//加入查询条件 如searchParams.put("LIKE_name", "%" + userName + "%");
		
		if (StringUtils.isNotEmpty(userName)) {
			searchParams.put("LIKE_userName", "%" + userName + "%");	
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try{
			if (StringUtils.isNotEmpty(beginTime)) {
				searchParams.put("GT_createTime", sdf.parse(beginTime));
			}
			
			if (StringUtils.isNotEmpty(endTime)) {
				searchParams.put("LT_createTime", sdf.parse(endTime));
			}
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
		}
		
		Page<SysLog> page = sysLogService.pageSearch(searchParams,
								pageIndex, pageSize, "createTime desc");
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", page.getTotalElements());
		resultMap.put("rows", page.getContent());
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
}