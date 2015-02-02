package com.guess.service;

import java.util.List;

import com.guess.model.Message;
import com.guess.model.MessageType;

public interface MessageService extends BaseService<Message, String>{
	List<Message> getByUser(String username);
	void processFriendApplication(Message message, boolean isAgreed);
	List<Message> getByType(String username, MessageType messageType);
}
