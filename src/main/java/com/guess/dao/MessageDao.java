package com.guess.dao;

import java.util.List;

import com.guess.model.Message;

public interface MessageDao extends BaseDao<Message, String>{
	List<Message> getByUser(String username);
}
