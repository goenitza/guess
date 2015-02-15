package com.guess.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guess.model.Message;
import com.guess.service.CircleUserService;
import com.guess.service.MessageService;
import com.guess.vo.Result;
import com.guess.vo.UserInSession;

@Controller
@RequestMapping(value = "/user/message", produces = "application/json;charset=utf-8")
public class MessageController {
	private static Logger logger = LogManager.getLogger(MessageController.class);
	@Autowired
	private MessageService messageService;
	@Autowired
	private CircleUserService circleUserService;
	
	@RequestMapping("/get_all")
	@ResponseBody
	public String getAll(HttpServletRequest request){
		Result result = new Result();
		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
		//to do paging
		
		
		List<Message> messages = messageService.getAll(userInSession.id);
		result.set("messages", messages);
		logger.info("get all messages: " + userInSession.id);
		return result.toJson();
	}
	
//	@RequestMapping("/get_by_type")
//	@ResponseBody
//	public String getByType(@RequestParam("type") String type,
//			HttpServletRequest request){
//		Result result = new Result();
//		MessageType messageType = MessageType.valueOf(StringUtils.upperCase(type));
//		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
//		List<Message> messages = messageService.getByType(userInSession.username, messageType);
//		result.set("messages", messages);
//		return result.toJson();
//	}
	
	@RequestMapping("process_friend_application")
	@ResponseBody
	public String processFriendApplication(@RequestParam("id") String id, 
			@RequestParam("isAgreed") boolean isAgreed, 
			HttpServletRequest request,
			HttpServletResponse response){
		Result result = new Result();
		Message message = messageService.get(id);
		if(message == null){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			result.setError("找不到具有此id的message");
			logger.info("the message does not exist: " + id);
			return result.toJson();
		} 
		
		if(circleUserService.exists(message.getReceiverId(), message.getSenderId())){
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			result.setError("此用户已经是你的好友");
			logger.info("the user is already your friend: " + id);
			return result.toJson();
		}
		
		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
		
		messageService.processFriendApplication(message, isAgreed,  
				userInSession.nickname, userInSession.getAvatar());
		logger.info("process friend application message: " + message.getReceiverId());
		
		return result.toJson();
	}
	
	@RequestMapping("/set_processed")
	@ResponseBody
	public String setProcessed(@RequestParam("id") String id,
			HttpServletResponse response){
		Result result = new Result();
		Message message = messageService.get(id);
		if(message == null){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			result.setError("找不到具有此id的message");
			logger.info("the message does not exist: " + id);
		}else {
			message.setIsProcessed(true);
			messageService.update(message);
			logger.info("set processed: " + id);
		}
		return result.toJson();
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(@RequestParam("ids") String ids, 
			HttpServletRequest request, HttpServletResponse response){
		Result result = new Result();
		messageService.delete(ids);
		logger.info("delete messages");
		return result.toJson();
	}
}
