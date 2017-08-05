package com.jpsoft.cms.sysmgr.service;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jpsoft.cms.sysmgr.entity.SysMenu;
import com.jpsoft.cms.sysmgr.entity.SysRole;
import com.jpsoft.cms.sysmgr.entity.SysRoleMenu;
import com.jpsoft.cms.sysmgr.entity.SysUser;
import com.jpsoft.cms.sysmgr.entity.SysUserRole;
import com.jpsoft.cms.sysmgr.repository.SysMenuDAO;
import com.jpsoft.cms.sysmgr.repository.SysRoleDAO;
import com.jpsoft.cms.sysmgr.repository.SysRoleMenuDAO;
import com.jpsoft.cms.sysmgr.repository.SysUserDAO;
import com.jpsoft.cms.sysmgr.repository.SysUserRoleDAO;
import com.jpsoft.cms.utils.CodeUtils;

@Transactional
@SuppressWarnings("unused")
@Component(value="popedomService")
public class PopedomService {
	@Resource(name="sysMenuDAO")
	private SysMenuDAO menuDAO;
	
	@Resource(name="sysRoleDAO")
	private SysRoleDAO roleDAO;
	
	@Resource(name="sysRoleMenuDAO")
	private SysRoleMenuDAO roleMenuDAO;
	
	@Resource(name="sysUserDAO")
	private SysUserDAO userDAO;
	
	@Resource(name="sysUserRoleDAO")
	private SysUserRoleDAO userRoleDAO;
	
	public SysRole saveRole(SysRole role){
		return roleDAO.save(role);
	}
	
	public SysUser saveUser(SysUser user){		
		return userDAO.save(user);
	}
	
	public Set<String> findRolesByUserId(String userId){
		HashSet<String> roleSet = new HashSet<String>();
		
		List<SysRole> roleList = userRoleDAO.findRoleByUserId(userId);
		
		for (SysRole role : roleList) {
			if (!roleSet.contains(role.getName())) {
				roleSet.add(role.getName());
			}
		}
		
		return roleSet;
	}
	
	public Set<String> findMenuUrlByUserId(String userId){
		HashSet<String> menuUrlSet = new HashSet<String>();
		
		List<SysMenu> menuList = roleMenuDAO.findByUserId(userId);
		
		for (SysMenu sysMenu : menuList) {
			if (StringUtils.isNoneEmpty(sysMenu.getPageUrl())) {
				menuUrlSet.add(sysMenu.getPageUrl());
			}
		}
		
		return menuUrlSet;
	}
	
	public void saveUserRole(SysUserRole userRole){
		userRoleDAO.save(userRole);
	}
	
	public void saveMenu(SysMenu menu){
		 menuDAO.save(menu);
	}
	
	public void saveRoleMenu(SysRoleMenu roleMenu){
		roleMenuDAO.save(roleMenu);
	}
	
	public SysUser findByUserName(String userName){
		return userDAO.findByName(userName);
	}

	public Set<String> findAllMenuUrl() {
		// TODO Auto-generated method stub
		Iterable<SysMenu> iter = menuDAO.findAll();
		HashSet<String> menuUrlSet = new HashSet<String>();
		
		for (SysMenu sysMenu : iter) {
			menuUrlSet.add(sysMenu.getPageUrl());
		}
		
		return menuUrlSet;
	}

	public void deleteRoleMenuRelationByRoleId(String roleId) {
		// TODO Auto-generated method stub
		roleMenuDAO.deleteByRoleId(roleId);
	}

	public void addUserRoleRelation(String userId, String[] roleIds) {
		// TODO Auto-generated method stub
		userRoleDAO.deleteByUserId(userId);
		
		for (int i = 0; i < roleIds.length; i++) {
			SysUserRole ur = new SysUserRole();
			
			ur.setUserId(userId);
			ur.setRoleId(roleIds[i]);
			
			userRoleDAO.save(ur);
		}
	}
	
	public void addUserRoleRelation(String userId, String roleId) {
		SysUserRole ur = new SysUserRole();
		
		ur.setUserId(userId);
		ur.setRoleId(roleId);
		
		userRoleDAO.save(ur);
	}

	public List<SysMenu> findMenuByRoleId(String roleId) {
		// TODO Auto-generated method stub
		return roleMenuDAO.findByRoleId(roleId);
	}

	public void addRoleMenuRelation(String roleId, String[] menuIds) {
		// TODO Auto-generated method stub
		roleMenuDAO.deleteByRoleId(roleId);
		
		for (String menuId : menuIds) {
			SysRoleMenu rm = new SysRoleMenu();
			
			rm.setMenuId(menuId);
			rm.setRoleId(roleId);
			
			roleMenuDAO.save(rm);
		}
	}

	public List<SysMenu> findMenuByUserId(String userId) {
		// TODO Auto-generated method stub
		return roleMenuDAO.findByUserId(userId);
	}

	public SysRole findRoleByName(final String roleName) {
		// TODO Auto-generated method stub
		Specification<SysRole> spec = new Specification<SysRole>() {
			@Override
			public Predicate toPredicate(Root<SysRole> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate p1 = cb.equal(root.get("name").as(String.class),roleName);

				query.where(p1);
				
				return query.getRestriction();
			}
		};
		
		return roleDAO.findOne(spec);
	}

	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		userDAO.deleteById(userId);
	}
	
	public StringBuilder batchExportUserRoleRelationInSql(Integer userType){
		List<SysUserRole> list = userRoleDAO.findByUserType(userType);
		
		StringBuilder pattern = new StringBuilder();
		pattern.append("insert into sys_user_role (id_,user_id,role_id)");
		pattern.append("values ({0},{1},{2})");
		
		StringBuilder sb = new StringBuilder();
		
		for (SysUserRole ur : list) {
			String sql = MessageFormat.format(pattern.toString(),
							new Object[]{
								CodeUtils.wrapperParam(ur.getId()),
								CodeUtils.wrapperParam(ur.getUserId()),
								CodeUtils.wrapperParam(ur.getRoleId())
							}
						);
			
			sb.append(sql);
			sb.append("\r\n");
		}
		
		return sb;
	}
}