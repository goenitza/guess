package com.guess.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guess.model.Admin;
import com.guess.service.AdminService;

@Controller
@RequestMapping(value = "/admin", produces = "application/json;charset=utf-8")
public class AdminController {
	
	private static Logger logger = LogManager.getLogger(AdminController.class);
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping("/login")
	@ResponseBody
	public String login(@RequestParam("username") String username, @RequestParam("password") String password,
			HttpServletRequest request, HttpServletResponse response){
		Result result = new Result();
		Admin admin = adminService.getByUsername(username);
		if(admin == null || !admin.getPassword().equals(DigestUtils.md5Hex(password))){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("用户名或密码错误");
			logger.info(username + "/" + password + " is incorrect");
		}else {
			UserInSession userInSession = new UserInSession();
			userInSession.id = admin.getId();
			userInSession.role = Role.ADMIN;
			request.getSession().setAttribute("user", userInSession);
			result.set("id", admin.getId());
			logger.info(username + " loggin");
		}
		return result.toJson();
	}
}
