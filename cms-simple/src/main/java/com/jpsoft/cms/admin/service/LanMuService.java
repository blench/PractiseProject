package com.jpsoft.cms.admin.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

import com.jpsoft.cms.admin.entity.Lanmu;
import com.jpsoft.cms.admin.entity.News;
import com.jpsoft.cms.admin.entity.LanmuType;
import com.jpsoft.cms.admin.repository.LanMuDao;
import com.jpsoft.cms.admin.repository.NewsDao;
import com.jpsoft.cms.sysmgr.entity.SysMenu;


@Transactional
@Component(value="lanmuService")
public class LanMuService {
	@Resource(name="lanmuDAO")
	private LanMuDao lanmuDAO;
	
	public Lanmu get(String id) {
		// TODO Auto-generated method stub
		return lanmuDAO.findOne(id);
	}

	public void save(Lanmu model) {
		// TODO Auto-generated method stub
		lanmuDAO.save(model);
	}

	public void deleteByIds(List<String> list) {
		// TODO Auto-generated method stub
		lanmuDAO.deleteByIds(list);
	}

	public List<Lanmu> findAll() {
		// TODO Auto-generated method stub
		
		return (List<Lanmu>) lanmuDAO.findAll();
	}
	public Page<Lanmu> pageSearch(Map<String,Object> searchParams, int pageNumber, int pageSize) {
		Order order1 = new Order(Direction.ASC,"sortNo");
		Order order2 = new Order(Direction.DESC,"createTime");
		
		Sort sort = new Sort(Arrays.asList(new Order[]{order1,order2}));
		
//		
		
		Pageable pageable = new PageRequest(pageNumber-1,pageSize,sort);
		return lanmuDAO.findAll(pageable);
	}
	
	
	public Set<String> findTypeByLanmuId(String lanmuId)
	{
		HashSet<String> nameSet = new HashSet<String>();
		List<LanmuType> typeList = lanmuDAO.findTypeByLanmuId(lanmuId);
		
		for(LanmuType type:typeList)
		{
			if(!nameSet.contains(type.getName()))
			{
				nameSet.add(type.getName());
			}
		}
		return nameSet;
	}
	
	
	public List<Lanmu> findLanmuByCurrentId(String id)
	{
		return lanmuDAO.findLanmuByCurrentId(id);
	}
	public Page<Lanmu> pageSearch(Map<String, Object> searchParams,
			int pageNumber, int pageSize, String sortType) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Lanmu> spec = buildSpecification(searchParams);

		return lanmuDAO.findAll(spec, pageRequest);
	}
	
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		
		if("bianma".equals(sortType))
		{
			sort = new Sort(sortType);
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Lanmu> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Lanmu> spec = DynamicSpecifications.bySearchFilter(filters.values(), Lanmu.class);
	
		return spec;
	}

	public LanMuDao getLanmuDAO() {
		return lanmuDAO;
	}

	public void setLanmuDAO(LanMuDao lanmuDAO) {
		this.lanmuDAO = lanmuDAO;
	}

	/**
	 * 创建动态查询条件组合.
	 */
	
	
	public long getChildrenCount(String parentId) {
		// TODO Auto-generated method stub
//		Map<String, Object> searchParams = new HashMap<String, Object>();
//
//		searchParams.put("EQ_parentId", moduleId);
//		
//		Specification<ModuleInfo> spec = buildSpecification(searchParams);
//		return moduleDAO.count(spec);
		
//		return lanmuDAO.getChildrenCount(parentId);
		return lanmuDAO.getChildrenCount(parentId);
	}

	public List<Lanmu> findByParentId(String parentId) {
		// TODO Auto-generated method stub
		return (List<Lanmu>) lanmuDAO.findAll();
	}
	
	
	public boolean exist(final String id,final String code) {
		// TODO Auto-generated method stub
		
		Specification<Lanmu> spec = new Specification<Lanmu>() {
			@Override
			public Predicate toPredicate(Root<Lanmu> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate p1 = cb.notEqual(root.get("id").as(String.class),id);
				Predicate p2 = cb.equal(root.get("bianma").as(String.class),code);
					
				query.where(cb.and(p1,p2));
				
				return query.getRestriction();
			}
		};
		
		long count = lanmuDAO.count(spec);
		
		if (count>0) {
			return true;
		}
		else{
			return false;
		}
	}
	
	public long getChildrenCount(String parentId, String displayType) {
		// TODO Auto-generated method stub
		return lanmuDAO.getChildrenCount(parentId,displayType);
	}

//	public List<Lanmu> getChildrenByParentId(String id) {
//		// TODO Auto-generated method stub
//		return lanmuDAO.getChildrenByParentId(id);
//	}


}
