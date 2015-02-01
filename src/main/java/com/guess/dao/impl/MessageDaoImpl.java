package com.guess.dao.impl;

import org.springframework.stereotype.Component;

import com.guess.dao.MessageDao;
import com.guess.model.Message;

@Component
public class MessageDaoImpl extends BaseDaoImpl<Message, String> implements MessageDao{

}
