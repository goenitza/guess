package com.guess.controller;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.guess.model.Category;
import com.guess.model.Message;
import com.guess.model.MessageType;
import com.guess.model.User;
import com.guess.model.UserRole;
import com.guess.service.CategoryService;
import com.guess.service.MessageService;
import com.guess.service.UserService;

@Controller
@RequestMapping(value = "/user", produces = "application/json;charset=utf-8")
public class UserController{
	
	private static Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private MessageService messageService;
	
	@RequestMapping("/register")
	@ResponseBody
	public String register(@RequestParam("username") String username, 
			@RequestParam("password") String password, @RequestParam("passwordConfirm") String passwordConfirm,
			@RequestParam("email")String email, @RequestParam("role") String role, 
			@RequestParam(value = "sickname", required = false) String sickname,
			HttpServletRequest request, HttpServletResponse response){
		Result result = new Result();
		UserRole userRole = UserRole.valueOf(StringUtils.upperCase(role));
		if(userRole == null){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("用户角色不存在");
			logger.info("role does not exists: " + role);
		}else if(!userService.isUnique(username)){
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			result.setError("用户名已被注册");
			logger.info("username has been registered: " + username);
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
				logger.info("the format of email is incorrect: " + email);
			}else {
				if(sickname == null)
					sickname = username;
				User user = new User();
				user.setUsername(username);
				user.setPassword(DigestUtils.md5Hex(password));
				user.setRole(userRole);
				user.setEmail(email);
				user.setSickname(sickname);
				String id = userService.save(user);
				UserInSession userInSession = new UserInSession();
				userInSession.id = id;
				userInSession.username = username;
				userInSession.role = UserRole.USER;
				request.getSession().setAttribute("user", userInSession);
				result.set("id", id);
				logger.info("user regisger: " + username);
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
			logger.info("username or password is not correct: " + username);
		}else if(user.getIsFrozen()){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("此用户已被冻结");
			logger.info("the user has been frozen: " + username);
		}else {
			UserInSession userInSession = new UserInSession();
			userInSession.id = user.getId();
			userInSession.username = username;
			userInSession.role = UserRole.USER;
			request.getSession().setAttribute("user", userInSession);
			result.set("id", user.getId());
			logger.info("user login: " + username);
		}
		return result.toJson();
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public String logout(HttpServletRequest request){
		Result result = new Result();
		User user = (User) request.getSession().getAttribute("user");
		String username = null;
		if(user != null)
			username = user.getUsername();
		request.getSession().removeAttribute("user");
		logger.info("user logout: " + username);
		return result.toJson();
	}
	
	@RequestMapping("/update_interested_category")
	@ResponseBody
	public String updateInterestedCategory(@RequestParam("categories") String categories, 
			HttpServletRequest request, HttpServletResponse response){
		Result result = new Result();
		List<Category> cgs = JSON.parseArray(categories, Category.class);
		List<Category> cgsDB = categoryService.getAllList();
		boolean hasError = false;
		for(Category c : cgs){
			if(!cgsDB.contains(c)){
				hasError = true;
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				result.setError(c.getName() + "不存在");
				logger.info("category does not exists: " + c.getName());
				break;
			}
		}
		if(!hasError){
			UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
			User user = userService.get(userInSession.id);
			user.setInterestedCategories(categories);
			userService.update(user);
		}
		return result.toJson();
	}
	
	@RequestMapping("/get")
	@ResponseBody
	public String get(@RequestParam("username") String username, 
			HttpServletRequest request, HttpServletResponse response){
		Result result = new Result();
		User user = userService.getByUsername(username);
		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
		if(user == null || user.getUsername().equals(userInSession.username)){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			result.setError("找不到此用户");
			logger.info("the user does not exists: " + username);
		}else {
			UserBrief userBrief = new UserBrief();
			userBrief.username = username;
			userBrief.avatar = user.getAvatar();
			result.set("user", userBrief);
			logger.info("get user: " + username);
		}
		return result.toJson();
	}
	
	@RequestMapping("/apply_friend")
	@ResponseBody
	public String applyFriend(@RequestParam("username") String username, 
			HttpServletRequest request, HttpServletResponse response){
		Result result = new Result();
		User user = userService.getByUsername(username);
		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
		if(user == null || user.getUsername().equals(userInSession.username)){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			result.setError("找不到此用户");
			logger.info("the user does not exists: " + username);
		}else {
			
			Message message = new Message();
			message.setType(MessageType.FRIEND_APPLICATION);
			message.setSender(userInSession.username);
			message.setReceiver(username);
			message.setDate(new Date());
			String id = messageService.save(message);
			logger.info("apply friend: " + userInSession.username + " -> " + username);
		}
		return result.toJson();
	}
}

class UserBrief{
	String username;
	String avatar;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}