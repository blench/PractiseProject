package com.jpsoft.cms.admin.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jpsoft.cms.admin.entity.Lanmu;
import com.jpsoft.cms.admin.entity.News;
import com.jpsoft.cms.admin.repository.NewsDao;


@Transactional
@Component(value="newsService")
public class NewsService {
	@Resource(name="newsDAO")
	private NewsDao newsDAO;
	
	public News get(String id) {
		// TODO Auto-generated method stub
		return newsDAO.findOne(id);
	}

	public void save(News model) {
		// TODO Auto-generated method stub
		newsDAO.save(model);
	}

	public void deleteByIds(List<String> list) {
		// TODO Auto-generated method stub
		newsDAO.deleteByIds(list);
	}
	
	

	public Page<Map> pageSearch(String title, int pageNumber, int pageSize) {
		Order order1 = new Order(Direction.ASC,"sortNo");
		Order order2 = new Order(Direction.DESC,"createTime");
		
		Sort sort = new Sort(Arrays.asList(new Order[]{order1,order2}));
		
		Pageable pageable = new PageRequest(pageNumber-1,pageSize,sort);
		return newsDAO.pageSearch(title, pageable);
	}
	
	public List<News> findNewsByLanmuId(@Param("id") String id)
	{
		return newsDAO.findNewsByLanmuId(id);
	}
}
