package com.jpsoft.cms.office.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilterEx;


import com.jpsoft.cms.office.entity.OfficeSupplies;
import com.jpsoft.cms.office.repository.IOfficeSuppliesDao;



@Transactional
@Service(value="officeSuppliesService")
public class OfficeSuppliesService {
	
	@Resource(name="officeSuppliesDao")
	private IOfficeSuppliesDao officeSuppliesDao;
	
	public OfficeSupplies get(String id) {
		// TODO Auto-generated method stub
		return officeSuppliesDao.findOne(id);
	}

	@Transactional
	public void save(OfficeSupplies officeSupplies) {
		// TODO Auto-generated method stub
		officeSuppliesDao.save(officeSupplies);
	}
	
	@Modifying
	public void deleteByIds(List<String> list) {
		// TODO Auto-generated method stub
		officeSuppliesDao.deleteByIds(list);
	}

	
	public List<OfficeSupplies> findAll()
	{
		return (List<OfficeSupplies>) officeSuppliesDao.findAll();
	}
	public Page<OfficeSupplies> pageSearch(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<OfficeSupplies> spec = buildSpecification(searchParams);
				
		return officeSuppliesDao.findAll(spec, pageRequest);
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
	private Specification<OfficeSupplies> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilterEx.parse(searchParams);
		Specification<OfficeSupplies> spec = DynamicSpecifications.bySearchFilter(filters.values(), OfficeSupplies.class);
	
		return spec;
	}
	
	public List<OfficeSupplies> findByIds(List<String> ids)
	{
		return officeSuppliesDao.findByIds(ids);
	}
}

