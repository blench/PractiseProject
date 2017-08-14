package com.jpsoft.cms.office.service;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jpsoft.cms.office.entity.User;
import com.jpsoft.cms.office.repository.IUserDao;


@Transactional
@Component(value="userService")
public class UserService {
	@Resource(name="userDao")
	private IUserDao userDao;
	
	public List<User> exists(String username,String password)
	{
		return userDao.exists(username, password);
	}
}
