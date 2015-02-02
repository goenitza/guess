package com.guess.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.guess.dao.MessageDao;
import com.guess.model.Message;

@Component
public class MessageDaoImpl extends BaseDaoImpl<Message, String> implements MessageDao{

	public List<Message> getByUser(String username) {
		String query = "from Message m where m.receiver = ?";
		List<Message> messages = hibernateTemplate.find(query, username);
		return messages;
	}
}
