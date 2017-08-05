package com.jpsoft.cms.sysmgr.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpsoft.cms.sysmgr.entity.SysMenu;

@Repository(value="sysMenuDAO")
public interface SysMenuDAO extends PagingAndSortingRepository<SysMenu, String>,JpaSpecificationExecutor<SysMenu> {	
	@Query("select count(*) from SysMenu where parent.id = :parentId")
	long getChildrenCount(@Param("parentId") String parentId);
	
	@Modifying
	@Query("delete from SysMenu where id in(:ids)")
	void deleteByIds(@Param("ids") List<String> ids);

	@Query("select count(*) from SysMenu where parent.id = :parentId and displayType=:displayType")
	long getChildrenCount(@Param("parentId") String parentId,@Param("displayType") String displayType);
}