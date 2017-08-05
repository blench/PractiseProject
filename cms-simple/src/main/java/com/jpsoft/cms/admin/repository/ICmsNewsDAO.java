package com.jpsoft.cms.admin.repository;

import java.util.List;

import com.jpsoft.cms.admin.entity.News;

public interface ICmsNewsDAO {
	
	public void insert(News news);
	
	public void delete(String id);
	
	public void update(News news);
	
	public News get(String id);
	
	public List<News> list(String id);

}
