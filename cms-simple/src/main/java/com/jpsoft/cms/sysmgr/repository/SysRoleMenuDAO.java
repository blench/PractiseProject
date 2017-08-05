package com.jpsoft.cms.sysmgr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpsoft.cms.sysmgr.entity.SysMenu;
import com.jpsoft.cms.sysmgr.entity.SysRole;
import com.jpsoft.cms.sysmgr.entity.SysRoleMenu;

@Repository(value="sysRoleMenuDAO")
public interface SysRoleMenuDAO extends PagingAndSortingRepository<SysRoleMenu, String>,JpaSpecificationExecutor<SysRoleMenu> {

	@Query("select m from SysMenu m where m.id in(select rm.menuId from SysUserRole ur,SysRoleMenu rm where ur.userId = :userId and ur.roleId = rm.roleId)")
	List<SysMenu> findByUserId(@Param("userId") String userId);

	@Modifying
	@Query("delete from SysRoleMenu where role_id=:roleId")
	void deleteByRoleId(@Param("roleId") String roleId);

	@Query("select m from SysRoleMenu rm,SysMenu m where rm.menuId = m.id and rm.roleId = :roleId")
	List<SysMenu> findByRoleId(@Param("roleId") String roleId);
}