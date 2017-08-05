package com.jpsoft.cms.sysmgr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpsoft.cms.sysmgr.entity.SysUser;

@Repository(value="sysUserDAO")
public interface SysUserDAO extends PagingAndSortingRepository<SysUser, String>,JpaSpecificationExecutor<SysUser> {
	SysUser findByName(String name);
	
	@Modifying
	@Query("delete from SysUser where id in(:ids)")
	void deleteByIds(@Param("ids") List<String> ids);

	@Modifying
	@Query("delete from SysUser where id=:id")
	void deleteById(@Param("id") String id);
	
	@Query("select s from SysUser s where s.name=:userName")
	List<SysUser> isExist(@Param("userName") String userName);
}
