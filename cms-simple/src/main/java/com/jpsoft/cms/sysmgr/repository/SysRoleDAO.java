package com.jpsoft.cms.sysmgr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpsoft.cms.sysmgr.entity.SysRole;

@Repository(value="sysRoleDAO")
public interface SysRoleDAO extends PagingAndSortingRepository<SysRole, String>,JpaSpecificationExecutor<SysRole> {
	@Modifying
	@Query("delete from SysRole where id in(:ids)")
	void deleteByIds(@Param("ids") List<String> ids);
}