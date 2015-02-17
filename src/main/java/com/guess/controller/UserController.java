package com.guess.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.guess.enums.CircleUserType;
import com.guess.enums.MessageType;
import com.guess.enums.UserRole;
import com.guess.model.Category;
import com.guess.model.CircleUser;
import com.guess.model.Individual;
import com.guess.model.Message;
import com.guess.model.Organization;
import com.guess.model.User;
import com.guess.service.CategoryService;
import com.guess.service.CircleUserService;
import com.guess.service.ImageService;
import com.guess.service.IndividualService;
import com.guess.service.MessageService;
import com.guess.service.OrganizationService;
import com.guess.util.ImageUtil;
import com.guess.vo.IndividualVO;
import com.guess.vo.OrgVO;
import com.guess.vo.Result;
import com.guess.vo.UserInSession;

@Controller
@RequestMapping(value = "/user", produces = "application/json;charset=utf-8")
public class UserController {

	private static Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private IndividualService individualService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private CircleUserService circleUserService;

	@RequestMapping("/register")
	@ResponseBody
	public String register(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("passwordConfirm") String passwordConfirm,
			@RequestParam("role") String role,
			@RequestParam(value = "nickname") String nickname,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Result result = new Result();
		UserRole userRole = null;
		try {
			userRole = UserRole.valueOf(StringUtils.upperCase(role));
			
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("用户角色不存在");
			logger.info("role does not exists: " + role);
			return result.toJson();
		}
		
		Pattern pattern = Pattern
				.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
		Matcher matcher = pattern.matcher(username);
		if (!matcher.matches()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("用户名须为邮箱");
			logger.info("the format of email is incorrect: " + username);
			return result.toJson();
		}
		
		if (!password.equals(passwordConfirm)) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("两次输入的密码不一致");
			logger.info("password and passwordConfirm are not same");
			return result.toJson();
		}
		
		if(userRole == UserRole.INDIVIDUAL){
			Individual individual = individualService.getByUsername(username);
			if(individual != null){
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.setError("用户名已被注册");
				logger.info("username has been registered: " + username);
				return result.toJson();
			}else {
				Individual in = new Individual();
				in.setUsername(username);
				in.setPassword(DigestUtils.md5Hex(password));
				in.setRole(userRole);
				in.setNickname(nickname);
				
				String contextPath = request.getSession().getServletContext().getRealPath("/");
				
				String id = individualService.save(in, contextPath);
				
				UserInSession userInSession = new UserInSession();
				userInSession.id = id;
				userInSession.username = username;
				userInSession.role = UserRole.INDIVIDUAL;
				userInSession.nickname = nickname;
				
				request.getSession().setAttribute("user", userInSession);
				result.set("id", id);
				logger.info("individual register: " + username);
				return result.toJson();
			}
		}else if(userRole == UserRole.ORG){
			Organization organization = organizationService.getByUsername(username);
			if(organization != null){
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.setError("用户名已被注册");
				logger.info("username has been registered: " + username);
				return result.toJson();
			}else {
				Organization org = new Organization();
				org.setUsername(username);
				org.setPassword(DigestUtils.md5Hex(password));
				org.setRole(userRole);
				org.setNickname(nickname);
				
				String contextPath = request.getSession().getServletContext().getRealPath("/");
				
				String id = organizationService.save(org, contextPath);
				
				UserInSession userInSession = new UserInSession();
				userInSession.id = id;
				userInSession.username = username;
				userInSession.role = UserRole.ORG;
				userInSession.nickname = nickname;
				
				request.getSession().setAttribute("user", userInSession);
				result.set("id", id);
				logger.info("organization register: " + username);
				return result.toJson();
			}
		}else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("用户角色不存在");
			logger.info("role does not exists: " + role);
			return result.toJson();
		}
		
	}

	@RequestMapping("/login")
	@ResponseBody
	public String login(@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("role") String role, 
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		UserRole userRole = null;
		try {
			userRole = UserRole.valueOf(StringUtils.upperCase(role));
			
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("用户角色不存在");
			logger.info("role does not exists: " + role);
			return result.toJson();
		}
		
		User user = null;
		if(userRole == UserRole.INDIVIDUAL){
			user = individualService.getByUsername(username);
		}else if (userRole == UserRole.ORG) {
			user = organizationService.getByUsername(username);
		}else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("用户角色不存在");
			logger.info("role does not exists: " + role);
			return result.toJson();
		}
		
	
		if (user == null
				|| !user.getPassword().equals(DigestUtils.md5Hex(password))) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("用户名或密码错误");
			logger.info("username or password is not correct: " + username);
		} else if (user.getIsFrozen()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("此用户已被冻结");
			logger.info("the user has been frozen: " + username);
		} else {
			UserInSession userInSession = new UserInSession();
			userInSession.id = user.getId();
			userInSession.role = userRole;
			userInSession.username = username;
			userInSession.nickname = user.getNickname();
			
			request.getSession().setAttribute("user", userInSession);
			 result.set("id", user.getId());
			logger.info("user login: " + username);
		}
		return result.toJson();
	}

	@RequestMapping("/logout")
	@ResponseBody
	public String logout(HttpServletRequest request) {
		Result result = new Result();
		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
		String id = "";
		if (userInSession != null){
			id = userInSession.id;
			request.getSession().removeAttribute("user");
		}
		logger.info("user logout: " + id);
		return result.toJson();
	}

	@RequestMapping("/update_interested_category")
	@ResponseBody
	public String updateInterestedCategory(
			@RequestParam("categories") String categories,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		List<Category> interestedCategories = JSON.parseArray(categories, Category.class);
		List<Category> categoriyList = categoryService.getAllList();
		for (Category c : interestedCategories) {
			if (!categoriyList.contains(c)) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				result.setError(c.getName() + "不存在");
				logger.info("category does not exists: " + c.getName());
				return result.toJson();
			}
		}
		UserInSession userInSession = (UserInSession) request.getSession()
				.getAttribute("user");
		if(userInSession.role == UserRole.INDIVIDUAL){
			Individual individual = individualService.get(userInSession.id);
			individual.setInterestedCategories(JSON.toJSONString(interestedCategories));
			individualService.update(individual);
		}else if(userInSession.role == UserRole.ORG){
			Organization organization = organizationService.get(userInSession.id);
			organization.setInterestedCategories(JSON.toJSONString(interestedCategories));
			organizationService.update(organization);
		}else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("用户角色不存在");
			logger.info("role does not exists: " + userInSession.role);
		}
		return result.toJson();
	}

	@RequestMapping("/get")
	@ResponseBody
	public String get(@RequestParam("username") String username,
			@RequestParam("role") String role,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		UserRole userRole = null;
		try {
			userRole = UserRole.valueOf(StringUtils.upperCase(role));
			
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("用户角色不存在");
			logger.info("role does not exists: " + role);
			return result.toJson();
		}
		
		//individual
		if(userRole == UserRole.INDIVIDUAL){
			Individual individual = individualService.getByUsername(username);
			if(individual == null){
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				result.setError("用户不存在");
				logger.info("user does not exist: " + username);
			}else {
				IndividualVO individualVO = new IndividualVO();
				individualVO.setId(individual.getId());
				individualVO.setUsername(individual.getUsername());
				individualVO.setNickname(individual.getNickname());
				individualVO.setAvatar(individual.getAvatar());
				result.set("user", JSON.toJSON(individualVO));
				logger.info("get user: " + username);
			}
			return result.toJson();
		}
		
		//org
		if(userRole == UserRole.ORG){
			Organization organization = organizationService.getByUsername(username);
			if(organization == null){
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				result.setError("用户不存在");
				logger.info("user does not exist: " + username);
			}else {
				OrgVO orgVO = new OrgVO();
				orgVO.setId(organization.getId());
				orgVO.setUsername(organization.getUsername());
				orgVO.setNickname(organization.getNickname());
				orgVO.setAvatar(organization.getAvatar());
				orgVO.setIsVerified(organization.getIsVerified());
				result.set("user", JSON.toJSON(organization));
				logger.info("get user: " + username);
			}
			return result.toJson();
		}
		
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		result.setError("用户角色不存在");
		logger.info("role does not exists: " + role);
		return result.toJson();
	}

	@RequestMapping("/apply_friend")
	@ResponseBody
	public String applyFriend(@RequestParam("id") String id,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		
		UserInSession userInSession = (UserInSession) request.getSession()
				.getAttribute("user");
		
		if(id.equals(userInSession.id)){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("不允许添加自己为好友");
			logger.info("Do not allow to apply the user himself for a friend: "+ id);
			return result.toJson();
		}
		
		Individual individual = individualService.get(id);
		
		if(individual == null){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			result.setError("找不到此用户");
			logger.info("the user does not exists: " + id);
			return result.toJson();
		}
		
		if(circleUserService.exists(userInSession.id, id)){
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			result.setError("此用户已经是你的好友");
			logger.info("the user is already your friend: " + id);
			return result.toJson();
		}
		
		Message message = new Message();
		message.setType(MessageType.FRIEND_APPLICATION);
		message.setReceiverId(id);
		message.setSenderId(userInSession.id);
		message.setSenderNickname(userInSession.nickname);
		message.setDate(new Date());
		messageService.save(message);
		logger.info("apply friend: " + userInSession.id + " -> " + id);
		
		return result.toJson();
	}

	@RequestMapping("/pay_attention")
	@ResponseBody
	public String addAttention(@RequestParam("id") String id,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		
		UserInSession userInSession = (UserInSession) request.getSession()
				.getAttribute("user");
		
		if (id.equals(userInSession.id)) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("不允许关注自己");
			logger.info("Do not allow to apply the user himself for a friend: " + id);
			return result.toJson();
		} 
		
		Organization org = organizationService.get(id);
		
		if (org == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			result.setError("找不到此用户");
			logger.info("the user does not exists: " + id);
			return result.toJson();
		} 
		
		if(circleUserService.exists(userInSession.id, id)){
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			result.setError("您已关注此用户");
			logger.info("You have already payed attention to this user: " + id);
			return result.toJson();
		}
		
		circleUserService.payAttention(userInSession.id, id, userInSession.role, userInSession.nickname);
		logger.info("pay attention: " + userInSession.id + "->" + id);

		return result.toJson();
	}

	@RequestMapping(value = {"/delete_friend", "/delete_attention"})
	@ResponseBody
	public String deleteFriendOrAttention(@RequestParam("id") String id,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		UserInSession userInSession = (UserInSession) request.getSession()
				.getAttribute("user");
		circleUserService.deleteFollowingAndFollower(userInSession.id, id);
		
		logger.info("delete friend or attention: " + userInSession.id + "->" + id);
		return result.toJson();
	}

	@RequestMapping("/upload_avatar")
	@ResponseBody
	public String uploadAvatar(@RequestParam("avatar") MultipartFile avatar,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		if (!ImageUtil.check(avatar)) {
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			result.setError("图片格式须为jpg、jpeg、png或者gif并且大小不能超过1M");
			logger.info("image format is not supported or size > 1M");
		} else {
			UserInSession userInSession = (UserInSession) request.getSession()
					.getAttribute("user");
			String contextPath = request.getSession().getServletContext()
					.getRealPath("/");
			try {
				imageService.saveAtatar(contextPath, avatar,
						userInSession.id);
			} catch (IOException e) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				result.setError(ExceptionUtils.getFullStackTrace(e));
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			logger.info("upload avatar: " + userInSession.id);
		}

		return result.toJson();
	}
	
	@RequestMapping("get_all_friend")
	@ResponseBody
	public String getAllFriend(HttpServletRequest request){
		Result result = new Result();
		//to do 
		
		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
		List<IndividualVO> users = circleUserService.getAllFriend(userInSession.id);
		result.set("users", JSON.toJSON(users));
		logger.info("get all friend: " + userInSession.id);
		return result.toJson();
	}
	
	@RequestMapping("get_all_follower")
	@ResponseBody
	public String getAllFollower(HttpServletRequest request){
		Result result = new Result();
		//to do 
		
		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
		List<IndividualVO> users = circleUserService.getAllFollower(userInSession.id);
		result.set("users", JSON.toJSON(users));
		return result.toJson();
	}
	
	@RequestMapping("get_all_following")
	@ResponseBody
	public String getAllFollowing(HttpServletRequest request){
		Result result = new Result();
		//to do 
		
		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
		List<OrgVO> users = circleUserService.getAllFollowing(userInSession.id);
		result.set("users", JSON.toJSON(users));
		
		return result.toJson();
	}
	
	@RequestMapping("get_by_circle")
	@ResponseBody
	public String getFriendOrFollowerByCircle(@RequestParam("circleId") String circleId, 
			HttpServletRequest request){
		Result result = new Result();		
		//to do 
		
		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
		List<IndividualVO> users = circleUserService.getFriendOrFollowerByCircle(userInSession.id, circleId);
		result.set("users", JSON.toJSON(users));
		
		return result.toJson();
	}
	
	@RequestMapping("add_to_circle")
	@ResponseBody
	public String addToCircle(@RequestParam("userId") String userId, @RequestParam("circleId") String circleId,
			HttpServletRequest request, HttpServletResponse response){
		Result result = new Result();
		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
		
		if(circleUserService.exists(userInSession.id, userId, circleId)){
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			result.setError("不允许重复添加好友到同一分组中");
			logger.info("do not allow to add the user to the same circle ");
			return result.toJson();
		}
		
		CircleUser circleUser = new CircleUser();
		circleUser.setUserId(userInSession.id);
		circleUser.set_userId(userId);
		circleUser.setCircleId(circleId);
		if(userInSession.role == UserRole.INDIVIDUAL){
			circleUser.setType(CircleUserType.FRIEND);
		}else {
			circleUser.setType(CircleUserType.FOLLOWER);
		}
		
		circleUserService.save(circleUser);
		
		return result.toJson();
	}
	
	@RequestMapping("delete_from_circle")
	@ResponseBody
	public String deleteFromCircle(@RequestParam("userId") String userId, @RequestParam("circleId") String circleId,
			HttpServletRequest request, HttpServletResponse response){
		Result result = new Result();
		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
		
		circleUserService.deleteFromCircle(userInSession.id, userId, circleId);
		
		return result.toJson();
	}
}