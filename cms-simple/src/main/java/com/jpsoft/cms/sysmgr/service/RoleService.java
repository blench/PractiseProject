package com.jpsoft.cms.sysmgr.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

import com.jpsoft.cms.sysmgr.entity.SysRole;
import com.jpsoft.cms.sysmgr.repository.SysRoleDAO;
import com.jpsoft.cms.sysmgr.repository.SysRoleMenuDAO;

@Transactional
@Component(value="roleService")
public class RoleService {
	@Resource(name="sysRoleDAO")
	private SysRoleDAO roleDAO;
	
	@Resource(name="sysRoleMenuDAO")
	private SysRoleMenuDAO roleMenuDAO;
	
	public SysRole get(String id) {
		// TODO Auto-generated method stub
		return roleDAO.findOne(id);
	}

	public void save(SysRole role) {
		// TODO Auto-generated method stub
		roleDAO.save(role);
	}

	public void deleteByIds(List<String> list) {
		// TODO Auto-generated method stub
		roleDAO.deleteByIds(list);
	}

	public Page<SysRole> pageSearch(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<SysRole> spec = buildSpecification(searchParams);
		
		return roleDAO.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<SysRole> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<SysRole> spec = DynamicSpecifications.bySearchFilter(filters.values(), SysRole.class);
	
		return spec;
	}
}
