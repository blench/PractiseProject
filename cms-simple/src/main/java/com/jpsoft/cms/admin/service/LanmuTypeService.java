package com.jpsoft.cms.admin.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

import com.jpsoft.cms.admin.entity.LanmuType;

import com.jpsoft.cms.admin.repository.LanmuTypeDao;

@Transactional
@Repository(value="lanmuTypeService")
public class LanmuTypeService {
	
	@Resource(name="lanmuTypeDao")
	private LanmuTypeDao lanmuTypeDao;
	
	
	public LanmuType get(String id)
	{
		return lanmuTypeDao.findOne(id);
	}
	
	
	
	public void save(LanmuType category)
	{
		lanmuTypeDao.save(category);
	}
	
	public void deleteByIds(List<String> ids)
	{
		lanmuTypeDao.deleteByIds(ids);
	}
	
	
	public LanmuTypeDao getLanmuTypeDao() {
		return lanmuTypeDao;
	}
	
	public void setLanmuTypeDao(LanmuTypeDao categoryDao) {
		this.lanmuTypeDao = categoryDao;
	}
	
	public List<LanmuType> findAll()
	{
		return (List<LanmuType>) lanmuTypeDao.findAll();
	}
	
	public Page<LanmuType> pageSearch(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
//		Order order1 = new Order(Direction.ASC,"sort");
//		Order order2 = new Order(Direction.DESC,"createDate");
		
//		Sort sort = new Sort(Arrays.asList(new Order[]{order1,order2}));
		Order order1 = new Order(Direction.ASC,"shuju");
		Sort sort = new Sort(order1);
		Pageable pageable = new PageRequest(pageNumber-1,pageSize,sort);
//		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<LanmuType> spec = buildSpecification(searchParams);

		return lanmuTypeDao.findAll(spec, pageable);
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
	private Specification<LanmuType> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<LanmuType> spec = DynamicSpecifications.bySearchFilter(filters.values(), LanmuType.class);
	
		return spec;
	}

	public Page<Map> pageSearch(String name, int pageIndex, int pageSize) {
//		 TODO Auto-generated method stub
		Order order1 = new Order(Direction.DESC,"shuju");
		Sort sort = new Sort(order1);
		
		PageRequest pageRequest = new PageRequest(pageIndex,pageSize,sort);
		return lanmuTypeDao.pageSearch(name,pageRequest);
	}
	
	
}
