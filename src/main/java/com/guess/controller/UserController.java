package com.guess.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.guess.model.User;
import com.guess.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController{
	
	private static Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/register")
	@ResponseBody
	public String register(@RequestParam("username") String username, 
			@RequestParam("password") String password, @RequestParam("passwordConfirm") String passwordConfirm,
			@RequestParam("email")String email, @RequestParam(value = "sickname", required = false) String sickname,
			HttpServletRequest request, HttpServletResponse response){
		Result result = new Result();
		if(!userService.isUnique(username)){
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			result.setError("用户名已被注册");
			logger.info(username + " has been registered");
		}else if(!password.equals(passwordConfirm)){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("两次输入的密码不一致");
			logger.info("password and passwordConfirm are not same");
		}else{
			Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
			Matcher matcher = pattern.matcher(email);
			if(!matcher.matches()){
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				result.setError("邮箱格式不正确");
				logger.info(email + " the format of email is incorrect");
			}else {
				if(sickname == null)
					sickname = username;
				User user = new User();
				user.setUsername(username);
				user.setPassword(DigestUtils.md5Hex(password));
				user.setEmail(email);
				user.setSickname(sickname);
				String id = userService.save(user);
				UserInSession userInSession = new UserInSession();
				userInSession.id = id;
				userInSession.role = Role.USER;
				request.getSession().setAttribute("user", userInSession);
				result.set("id", id);
				logger.info(username + " regisger");
			}
		} 
		return result.toJson();
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public String login(@RequestParam("username") String username, @RequestParam("password") String password,
			HttpServletRequest request, HttpServletResponse response){
		Result result = new Result();
		User user = userService.getByUsername(username);
		if(user == null || !user.getPassword().equals(DigestUtils.md5Hex(password))){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("用户名或密码错误");
			logger.info(username + "/" + password + " username or password is not correct");
		}else {
			UserInSession userInSession = new UserInSession();
			userInSession.id = user.getId();
			userInSession.role = Role.USER;
			request.getSession().setAttribute("user", userInSession);
			result.set("id", user.getId());
			logger.info(username + " login");
		}
		return result.toJson();
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public String logout(@RequestParam("id") String id, HttpServletRequest request){
		Result result = new Result();
		request.getSession().removeAttribute(id);
		logger.info(id + " logout");
		return result.toJson();
	}
}