package com.jpsoft.cms.sysmgr.service;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.security.utils.Cryptos;
import org.springside.modules.utils.Encodes;

import com.jpsoft.cms.sysmgr.entity.SysMenu;
import com.jpsoft.cms.sysmgr.entity.SysUser;
import com.jpsoft.cms.sysmgr.repository.SysUserDAO;
import com.jpsoft.cms.sysmgr.repository.SysUserRoleDAO;
import com.jpsoft.cms.sysmgr.security.AESCredentialsMatcher;
import com.jpsoft.cms.utils.CodeUtils;

@Transactional
@Component(value="userService")
public class UserService {
	@Resource(name="sysUserDAO")
	private SysUserDAO userDAO;
	
	@Resource(name="sysUserRoleDAO")
	private SysUserRoleDAO userRoleDAO;

	public SysUser get(String id) {
		// TODO Auto-generated method stub
		SysUser user =  userDAO.findOne(id);
		
		if (user!=null) {
			try {
				byte[] encPwd = Encodes.decodeHex(user.getPassword());
				String plainPassword = Cryptos.aesDecrypt(encPwd,AESCredentialsMatcher.KEY.getBytes("UTF-8"));
				
				user.setPlainPassword(plainPassword);
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}		
		
		return user;
	}

	public void save(SysUser user) {
		// TODO Auto-generated method stub
		userDAO.save(user);
	}

	public void deleteByIds(List<String> list) {
		// TODO Auto-generated method stub
		userDAO.deleteByIds(list);
	}

	public Page<SysUser> pageSearch(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<SysUser> spec = buildSpecification(searchParams);
		
		return userDAO.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		
		if ("createDate".equals(sortType)) {
			sort = new Sort(Direction.ASC, "createDate");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<SysUser> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);

		Specification<SysUser> spec = DynamicSpecifications.bySearchFilter(filters.values(), SysUser.class);
	
		return spec;
	}

	public boolean exist(final String userName, final String userId) {
		// TODO Auto-generated method stub
		
		Specification<SysUser> spec = new Specification<SysUser>() {
			@Override
			public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate p1 = cb.notEqual(root.get("id").as(String.class),userId);
				Predicate p2 = cb.equal(root.get("name").as(String.class),userName);
					
				query.where(cb.and(p1,p2));
				
				return query.getRestriction();
			}
		};
		
		if(userDAO.count(spec)>0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean isExist(String userName){
		List<SysUser> userList=userDAO.isExist(userName);
		if(userList.isEmpty()&&userList.size()==0){
			return true;
		}else{
			return false;
		}
	}
}