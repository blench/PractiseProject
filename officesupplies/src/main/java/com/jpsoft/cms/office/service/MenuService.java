package com.jpsoft.cms.office.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import com.jpsoft.cms.office.entity.SysMenu;
import com.jpsoft.cms.office.repository.SysMenuDAO;


@Transactional
@Component(value="menuService")
public class MenuService {
	@Resource(name="sysMenuDAO")
	private SysMenuDAO sysMenuDAO;
	
	public SysMenu get(String id) {
		return sysMenuDAO.findOne(id);
	}

	public void save(SysMenu entity) {
		sysMenuDAO.save(entity);
	}

	public void delete(String id) {
		sysMenuDAO.delete(id);
	}
	
	public void deleteByIds(List ids){
		sysMenuDAO.deleteByIds(ids);
	}

	public Page<SysMenu> pageSearch(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<SysMenu> spec = buildSpecification(searchParams);
		
		return sysMenuDAO.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		
		if ("code".equals(sortType)) {
			sort = new Sort(Direction.ASC, "code");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<SysMenu> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		Specification<SysMenu> spec = DynamicSpecifications.bySearchFilter(filters.values(), SysMenu.class);
	
		return spec;
	}

	public long getChildrenCount(String parentId) {
		// TODO Auto-generated method stub
//		Map<String, Object> searchParams = new HashMap<String, Object>();
//
//		searchParams.put("EQ_parentId", moduleId);
//		
//		Specification<ModuleInfo> spec = buildSpecification(searchParams);
//		return moduleDAO.count(spec);
		
		return sysMenuDAO.getChildrenCount(parentId);
	}

	public long getChildrenCount(String parentId, String displayType) {
		// TODO Auto-generated method stub
		return sysMenuDAO.getChildrenCount(parentId,displayType);
	}

	
	
	public boolean exist(final String id,final String code) {
		// TODO Auto-generated method stub
		
		Specification<SysMenu> spec = new Specification<SysMenu>() {
			@Override
			public Predicate toPredicate(Root<SysMenu> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate p1 = cb.notEqual(root.get("id").as(String.class),id);
				Predicate p2 = cb.equal(root.get("code").as(String.class),code);
					
				query.where(cb.and(p1,p2));
				
				return query.getRestriction();
			}
		};
		
		long count = sysMenuDAO.count(spec);
		
		if (count>0) {
			return true;
		}
		else{
			return false;
		}
	}

	public SysMenu findByCode(final String menuCode) {
		// TODO Auto-generated method stub
		SysMenu menu = sysMenuDAO.findOne(new Specification<SysMenu>() {
			@Override
			public Predicate toPredicate(Root<SysMenu> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate p1 = cb.equal(root.get("code").as(String.class),menuCode);
					
				query.where(cb.and(p1));
				
				return query.getRestriction();
			}
		});
		
		return menu;
	}

	public List<SysMenu> findAll() {
		// TODO Auto-generated method stub
		return (List<SysMenu>) sysMenuDAO.findAll();
	}
}
