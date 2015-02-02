package com.guess.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.guess.dao.MessageDao;
import com.guess.model.Message;
import com.guess.model.MessageType;

@Component
public class MessageDaoImpl extends BaseDaoImpl<Message, String> implements MessageDao{

	public List<Message> getByUser(String username) {
		String query = "from Message m where m.receiver = ?";
		List<Message> messages = hibernateTemplate.find(query, username);
		return messages;
	}

	public List<Message> getByType(String username, MessageType messageType) {
		String query = "from Message m where m.receiver = ? and m.type = ?";
		List<Message> messages = hibernateTemplate.find(query, username, messageType);
		return messages;
	}
}
