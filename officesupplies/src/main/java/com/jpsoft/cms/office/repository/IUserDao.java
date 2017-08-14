package com.jpsoft.cms.office.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.jpsoft.cms.office.entity.User;


@Repository(value="userDao")
public interface IUserDao extends PagingAndSortingRepository<User, String>,JpaSpecificationExecutor<User>{
	
	@Query("select u from User u where u.username = ?1 and u.password = ?2")
	public List<User> exists(@Param("username")String username,@Param("password")String password);
}
