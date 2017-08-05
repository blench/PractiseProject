package com.test.service;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.springframework.data.domain.Page;

import com.jpsoft.cms.admin.service.NewsService;

public class TestNewsService {
	
	private NewsService newsService;
	@Test
	public void testGet() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteByIds() {
		fail("Not yet implemented");
	}

	@Test
	public void testPageSearch() {
		Page<Map> pageSearch = newsService.pageSearch("", 1, 10);
		System.out.println(pageSearch.getContent());
	}

	@Test
	public void testFindNewsByLanmuId() {
		fail("Not yet implemented");
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

}
