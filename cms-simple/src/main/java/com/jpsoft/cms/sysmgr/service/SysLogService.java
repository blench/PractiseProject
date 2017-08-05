package com.jpsoft.cms.sysmgr.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilterEx;

import com.jpsoft.cms.sysmgr.entity.SysLog;
import com.jpsoft.cms.sysmgr.repository.SysLogDAO;

@Transactional
@Component(value="sysLogService")
public class SysLogService {
	@Resource(name="sysLogDAO")
	private SysLogDAO sysLogDAO;
	
	public SysLog get(String id) {
		// TODO Auto-generated method stub
		return sysLogDAO.findOne(id);
	}

	public void save(String userName,String content){
		SysLog sysLog = new SysLog();
		sysLog.setUserName(userName);
		sysLog.setContent(content);
		sysLog.setCreateTime(new Date());
		
		sysLogDAO.save(sysLog);
	}
	
	public void save(SysLog sysLog) {
		// TODO Auto-generated method stub
		sysLogDAO.save(sysLog);
	}

	public void deleteByIds(List<String> list) {
		// TODO Auto-generated method stub
		sysLogDAO.deleteByIds(list);
	}

	public Page<SysLog> pageSearch(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<SysLog> spec = buildSpecification(searchParams);
				
		return sysLogDAO.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		return new PageRequest(pageNumber - 1, pagzSize, SearchFilterEx.parseSort(sortType));
	}
	
	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<SysLog> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilterEx.parse(searchParams);
		Specification<SysLog> spec = DynamicSpecifications.bySearchFilter(filters.values(), SysLog.class);
	
		return spec;
	}
}