package com.jpsoft.cms.sysmgr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpsoft.cms.sysmgr.entity.SysRole;
import com.jpsoft.cms.sysmgr.entity.SysUserRole;

@Repository(value="sysUserRoleDAO")
public interface SysUserRoleDAO extends PagingAndSortingRepository<SysUserRole, String>,JpaSpecificationExecutor<SysUserRole> {
	@Query("select r from SysUserRole ur,SysRole r where ur.userId = :userId and ur.roleId = r.id")
	List<SysRole> findRoleByUserId(@Param("userId") String userId);

	@Modifying
	@Query("delete from SysUserRole where user_id=:userId")
	void deleteByUserId(@Param("userId") String userId);
	
	@Query("select ur from SysUserRole ur,SysUser u where ur.userId = u.id and u.userType = :userType")
	List<SysUserRole> findByUserType(@Param("userType") Integer userType);
}