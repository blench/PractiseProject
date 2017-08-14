package com.jpsoft.cms.office.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jpsoft.cms.office.entity.User;
import com.jpsoft.cms.office.service.UserService;

/**
 * 验证登录
 * @author chenxiyu
 *
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String login()
	{
		return "login";
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public String fail(String username,String password,Model model,HttpSession session)
	{
		System.out.println("username"+username);
		System.out.println("password"+password);
		List<User> user = userService.exists(username, password);
		if(!user.isEmpty())
		{
			session.setAttribute("user",user.get(0));
		}
		else
		{
			session.setAttribute("user",null);
		}
		
		return "redirect:/index";
		
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		if(session!=null)
			session.invalidate();
		return "login";
	}
	
}
