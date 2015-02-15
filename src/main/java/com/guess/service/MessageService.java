package com.guess.service;

import java.util.List;

import com.guess.model.Message;

public interface MessageService extends BaseService<Message, String>{
	void processFriendApplication(Message message, boolean isAgreed, String circleId, String nickname);
	List<Message> getAll(String receiverId);
	void processFriendApplicationReply(Message message, String id,
			String circleId);
}
