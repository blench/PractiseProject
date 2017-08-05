package com.jpsoft.cms.admin.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpsoft.cms.admin.entity.News;

@Repository(value="newsDAO")
public interface NewsDao extends PagingAndSortingRepository<News, String>,JpaSpecificationExecutor<News> {
	
	
	@Query(" select new map(n.id as id,n.content as content,n.title as title,n.createTime as createTime,n.author as author,n.sortNo as sortNo," +
			"n.cover as cover,n.creator as creator,n.lanmuId as lanmuId)" +
		   " from News n where n.title like :title")
	Page<Map> pageSearch(@Param("title") String title,Pageable pr);
	
	@Modifying
	@Query("delete from News where id in(:ids)")
	void deleteByIds(@Param("ids") List<String> ids);
	
	
	@Query("select m from News m,Lanmu l where m.id = l.id and l.id = :id")
	List<News> findNewsByLanmuId(@Param("id") String id);
}