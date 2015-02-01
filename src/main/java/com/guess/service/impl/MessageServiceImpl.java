package com.guess.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guess.dao.MessageDao;
import com.guess.model.Message;
import com.guess.service.MessageService;

@Component
public class MessageServiceImpl extends BaseServiceImpl<Message, String> implements MessageService{
	private MessageDao messageDao;

	public MessageDao getMessageDao() {
		return messageDao;
	}
	@Autowired
	public void setMessageDao(MessageDao messageDao) {
		super.setBaseDao(messageDao);
		this.messageDao = messageDao;
	}
}
