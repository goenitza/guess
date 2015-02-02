package com.guess.dao;

import java.util.List;

import com.guess.model.Message;
import com.guess.model.MessageType;

public interface MessageDao extends BaseDao<Message, String>{
	List<Message> getByUser(String username);
	List<Message> getByType(String username, MessageType messageType);
}
