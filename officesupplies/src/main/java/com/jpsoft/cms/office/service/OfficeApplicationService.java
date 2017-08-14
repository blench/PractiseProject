package com.jpsoft.cms.office.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilterEx;

import com.jpsoft.cms.office.entity.OfficeApplication;
import com.jpsoft.cms.office.repository.IOfficeApplicationDao;

@Transactional 
@Component("officeApplicationService")
public class OfficeApplicationService {
	
	@Resource(name="officeApplicationDao")
	private IOfficeApplicationDao officeApplicationDao;
	
	
	public OfficeApplication get(String id)
	{
		return officeApplicationDao.findOne(id);
	}
	
	public void save(OfficeApplication entity)
	{
		officeApplicationDao.save(entity);
	}
	
	public List<OfficeApplication> findAll()
	{
		return (List<OfficeApplication>) officeApplicationDao.findAll();
	}
	
	public Page<OfficeApplication> pageSearch(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<OfficeApplication> spec = buildSpecification(searchParams);
				
		return officeApplicationDao.findAll(spec, pageRequest);
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
	private Specification<OfficeApplication> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilterEx.parse(searchParams);
		Specification<OfficeApplication> spec = DynamicSpecifications.bySearchFilter(filters.values(), OfficeApplication.class);
	
		return spec;
	}
}
