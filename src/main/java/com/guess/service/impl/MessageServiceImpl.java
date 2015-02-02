package com.guess.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.guess.dao.MessageDao;
import com.guess.model.Message;
import com.guess.model.MessageType;
import com.guess.model.User;
import com.guess.service.MessageService;
import com.guess.service.UserService;
import com.guess.vo.FriendDB;

@Component
public class MessageServiceImpl extends BaseServiceImpl<Message, String> implements MessageService{
	private MessageDao messageDao;
	@Autowired
	private UserService userService;

	public MessageDao getMessageDao() {
		return messageDao;
	}
	@Autowired
	public void setMessageDao(MessageDao messageDao) {
		super.setBaseDao(messageDao);
		this.messageDao = messageDao;
	}
	public List<Message> getByUser(String username) {
		return messageDao.getByUser(username);
	}
	@Transactional
	public void processFriendApplication(Message message, boolean isAgreed) {
		if(isAgreed){
			String senderName = message.getSender();
			String receiverName = message.getReceiver();
			
			User sender = userService.getByUsername(senderName);
			User receiver = userService.getByUsername(receiverName);
			
			FriendDB senderFriendDB = new FriendDB();
			senderFriendDB.setUsername(sender.getUsername());
			senderFriendDB.setAvatar(sender.getAvatar());
			FriendDB receiverFriendDB = new FriendDB();
			receiverFriendDB.setUsername(receiver.getUsername());
			receiverFriendDB.setAvatar(receiver.getAvatar());
			
			List<FriendDB> senderFriendDBs;
			if(StringUtils.isBlank(sender.getFriends())){
				senderFriendDBs = new ArrayList<FriendDB>();
			}else {
				senderFriendDBs = JSON.parseArray(sender.getFriends(), FriendDB.class);
			}
			senderFriendDBs.add(receiverFriendDB);
			sender.setFriends(JSON.toJSONString(senderFriendDBs));
			List<FriendDB> receiverFriendDBs;
			if(StringUtils.isBlank(receiver.getFriends())){
				receiverFriendDBs = new ArrayList<FriendDB>();
			}else {
				receiverFriendDBs = JSON.parseArray(receiver.getFriends(), FriendDB.class);
			}
			receiverFriendDBs.add(senderFriendDB);
			receiver.setFriends(JSON.toJSONString(receiverFriendDBs));
			
			userService.update(sender);
			userService.update(receiver);
			
			Message replyMessage = new Message();
			replyMessage.setType(MessageType.FRIEND_APPLICATION_REPLAY);
			replyMessage.setReceiver(senderName);
			replyMessage.setSender(receiverName);
			replyMessage.setDate(new Date());
			messageDao.save(replyMessage);
		}
		message.setIsProcessed(true);
		messageDao.update(message);
	}
	public List<Message> getByType(String username, MessageType messageType) {
		return messageDao.getByType(username, messageType);
	}
}