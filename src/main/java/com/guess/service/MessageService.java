package com.guess.service;

import java.util.List;

import com.guess.model.Message;

public interface MessageService extends BaseService<Message, String>{
	List<Message> getByUser(String username);
	void processFriendApplication(Message message, boolean isAgreed);
}
