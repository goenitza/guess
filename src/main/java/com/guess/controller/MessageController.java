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
import com.guess.service.MessageService;

@Controller
@RequestMapping(value = "/user/message", produces = "application/json;charset=utf-8")
public class MessageController {
	private static Logger logger = LogManager.getLogger(MessageController.class);
	@Autowired
	private MessageService messageService;
	
	@RequestMapping("/get_all")
	@ResponseBody
	public String getAll(HttpServletRequest request){
		Result result = new Result();
		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
		List<Message> messages = messageService.getByUser(userInSession.username);
		result.set("messages", messages);
		logger.info("get all messages: " + userInSession.username);
		return result.toJson();
	}
	
	@RequestMapping("process_friend_application")
	@ResponseBody
	public String processFriendApplication(@RequestParam("messageId") String messageId, 
			@RequestParam("isAgreed") boolean isAgreed, HttpServletResponse response){
		Result result = new Result();
		Message message = messageService.get(messageId);
		if(message == null){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			result.setError("找不到具有此id的message");
			logger.info("the message does not exist: " + messageId);
		}else {
			String senderName = message.getSender();
			String receiverName = message.getReceiver();
			messageService.processFriendApplication(message, isAgreed);
			logger.info("add friend: " + senderName + " <-> " + receiverName);
		}
		return result.toJson();
	}
}
