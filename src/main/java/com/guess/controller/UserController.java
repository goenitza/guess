package com.guess.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import com.guess.constant.Constant;
import com.guess.enums.CircleType;
import com.guess.enums.MessageType;
import com.guess.enums.UserRole;
import com.guess.model.Category;
import com.guess.model.Circle;
import com.guess.model.Individual;
import com.guess.model.Message;
import com.guess.model.Organization;
import com.guess.model.User;
import com.guess.service.CategoryService;
import com.guess.service.ImageService;
import com.guess.service.IndividualService;
import com.guess.service.MessageService;
import com.guess.service.OrganizationService;
import com.guess.service.UserService;
import com.guess.util.ImageUtil;
import com.guess.vo.FriendDB;
import com.guess.vo.Result;
import com.guess.vo.UserBrief;
import com.guess.vo.UserInSession;

@Controller
@RequestMapping(value = "/user", produces = "application/json;charset=utf-8")
public class UserController {

	private static Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	private UserService userService;
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
		UserRole userRole = UserRole.valueOf(StringUtils.upperCase(role));
		if (userRole == null) {
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
				in.setPassword(password);
				in.setRole(userRole);
				in.setNickname(nickname);
				String contextPath = request.getSession().getServletContext()
						.getRealPath("/");
				String avatar = imageService.setDefaultAvatar(contextPath,
						username);
				in.setAvatar(avatar);
				
				Circle defaultFriendCircle = new Circle();
				defaultFriendCircle.setType(CircleType.FRIEND_DEFAULT);
				defaultFriendCircle.setName(Constant.DEFAULT_FRIEND_CIRCLE_NAME);
				
				Circle defaultFollowingCircle = new Circle();
				defaultFollowingCircle.setType(CircleType.FOLLOWING_DEFAULT);
				defaultFollowingCircle.setName(Constant.DEFAULT_FOLLOWING_CIRCLE_NAME);
				
				Set<Circle> circles = new HashSet<Circle>();
				circles.add(defaultFriendCircle);
				circles.add(defaultFollowingCircle);
				
				in.setCircles(circles);
				
				String id = individualService.save(in);
				UserInSession userInSession = new UserInSession();
				userInSession.id = id;
				userInSession.role = UserRole.INDIVIDUAL;
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
				org.setPassword(password);
				org.setRole(userRole);
				org.setNickname(nickname);
				String contextPath = request.getSession().getServletContext()
						.getRealPath("/");
				String avatar = imageService.setDefaultAvatar(contextPath,
						username);
				org.setAvatar(avatar);
				
				Circle defaultFriendCircle = new Circle();
				defaultFriendCircle.setType(CircleType.FOLLOWER_DEFAULT);
				defaultFriendCircle.setName(Constant.DEFAULT_FOLLOWER_CIRCLE_NAME);
				
				Circle defaultFollowingCircle = new Circle();
				defaultFollowingCircle.setType(CircleType.FOLLOWING_DEFAULT);
				defaultFollowingCircle.setName(Constant.DEFAULT_FOLLOWING_CIRCLE_NAME);
				
				Set<Circle> circles = new HashSet<Circle>();
				circles.add(defaultFriendCircle);
				circles.add(defaultFollowingCircle);
				
				org.setCircles(circles);
				
				String id = organizationService.save(org);
				UserInSession userInSession = new UserInSession();
				userInSession.id = id;
				userInSession.role = UserRole.INDIVIDUAL;
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
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		User user = userService.get(username);
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
			userInSession.username = username;
			userInSession.role = UserRole.USER;
			request.getSession().setAttribute("user", userInSession);
			// result.set("id", user.getId());
			logger.info("user login: " + username);
		}
		return result.toJson();
	}

	@RequestMapping("/logout")
	@ResponseBody
	public String logout(HttpServletRequest request) {
		Result result = new Result();
		User user = (User) request.getSession().getAttribute("user");
		String username = null;
		if (user != null)
			username = user.getUsername();
		request.getSession().removeAttribute("user");
		logger.info("user logout: " + username);
		return result.toJson();
	}

	@RequestMapping("/update_interested_category")
	@ResponseBody
	public String updateInterestedCategory(
			@RequestParam("categories") String categories,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		List<Category> cgs = JSON.parseArray(categories, Category.class);
		List<Category> cgsDB = categoryService.getAllList();
		boolean hasError = false;
		for (Category c : cgs) {
			if (!cgsDB.contains(c)) {
				hasError = true;
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				result.setError(c.getName() + "不存在");
				logger.info("category does not exists: " + c.getName());
				break;
			}
		}
		if (!hasError) {
			UserInSession userInSession = (UserInSession) request.getSession()
					.getAttribute("user");
			User user = userService.get(userInSession.username);
			user.setInterestedCategories(categories);
			userService.update(user);
		}
		return result.toJson();
	}

	@RequestMapping("/get")
	@ResponseBody
	public String get(@RequestParam("username") String username,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		User user = userService.get(username);
		UserInSession userInSession = (UserInSession) request.getSession()
				.getAttribute("user");
		if (user == null || user.getUsername().equals(userInSession.username)) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			result.setError("找不到此用户");
			logger.info("the user does not exists: " + username);
		} else {
			UserBrief userBrief = new UserBrief();
			userBrief.setUsername(username);
			userBrief.setNickName(user.getNickname());
			userBrief.setAvatar(user.getAvatar());
			userBrief.setRole(user.getRole());
			userBrief.setIsVerified(user.getIsVerified());
			result.set("user", userBrief);
			logger.info("get user: " + username);
		}
		return result.toJson();
	}

	@RequestMapping("/apply_friend")
	@ResponseBody
	public String applyFriend(@RequestParam("username") String username,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		User friend = userService.get(username);
		UserInSession userInSession = (UserInSession) request.getSession()
				.getAttribute("user");
		if (friend == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			result.setError("找不到此用户");
			logger.info("the user does not exists: " + username);
		} else if (friend.getRole() != UserRole.USER) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("用户类型不正确");
			logger.info("the type of user is incorrect: " + username + "/"
					+ friend.getRole());
		} else if (friend.getUsername().equals(userInSession.username)) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("不允许添加自己为好友");
			logger.info("Do not allow to apply the user himself for a friend: "
					+ username);
		} else {
			User user = userService.get(userInSession.username);
			boolean exists = false;
			if (StringUtils.isNotBlank(user.getFriends())) {
				List<FriendDB> friendDBs = JSON.parseArray(user.getFriends(),
						FriendDB.class);
				for (FriendDB friendDB : friendDBs) {
					if (friendDB.getUsername().equals(username)) {
						exists = true;
						break;
					}
				}
			}
			if (exists) {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.setError("此用户已经是你的好友");
				logger.info("the user is already your friend: " + username);
			} else {
				Message message = new Message();
				message.setType(MessageType.FRIEND_APPLICATION);
				message.setSender(userInSession.username);
				message.setReceiver(username);
				message.setDate(new Date());
				String id = messageService.save(message);
				logger.info("apply friend: " + userInSession.username + " -> "
						+ username);
			}
		}
		return result.toJson();
	}

	@RequestMapping("/delete_friend")
	@ResponseBody
	public String deleteFriend(@RequestParam("username") String username,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		UserInSession userInSession = (UserInSession) request.getSession()
				.getAttribute("user");
		userService.deleteFriend(userInSession.username, username);
		logger.info("delete friend: " + userInSession.username + "->"
				+ username);
		return result.toJson();
	}

	@RequestMapping("/pay_attention")
	@ResponseBody
	public String addAttention(@RequestParam("username") String username,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		User org = userService.get(username);
		UserInSession userInSession = (UserInSession) request.getSession()
				.getAttribute("user");
		if (org == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			result.setError("找不到此用户");
			logger.info("the user does not exists: " + username);
		} else if (org.getRole() != UserRole.ORG) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("用户类型不正确");
			logger.info("the type of user is incorrect: " + username + "/"
					+ org.getRole());
		} else if (org.getUsername().equals(userInSession.username)) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("不允许关注自己");
			logger.info("Do not allow to apply the user himself for a friend: "
					+ username);
		} else {
			User user = userService.get(userInSession.username);
			Result r = userService.payAttention(user, org);
			if (r != null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				result = r;
				logger.info("the user has payed attention to the org: "
						+ userInSession.username + "->" + username);
			} else {
				logger.info("pay attention: " + userInSession.username + "->"
						+ username);
			}
		}
		return result.toJson();
	}

	@RequestMapping("/delete_attention")
	@ResponseBody
	public String deleteAttention(@RequestParam("username") String username,
			HttpServletRequest request) {
		Result result = new Result();
		UserInSession userInSession = (UserInSession) request.getSession()
				.getAttribute("user");
		userService.deleteAttention(userInSession.username, username);
		logger.info("delete attention: " + userInSession.username + "->"
				+ username);
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
						userInSession.username);
			} catch (IOException e) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				result.setError(ExceptionUtils.getFullStackTrace(e));
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			logger.info("upload avatar: " + userInSession.username);

		}

		return result.toJson();
	}

}