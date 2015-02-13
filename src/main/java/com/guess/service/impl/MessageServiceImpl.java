package com.guess.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.guess.dao.MessageDao;
import com.guess.enums.CircleUserType;
import com.guess.enums.MessageType;
import com.guess.model.CircleUser;
import com.guess.model.Message;
import com.guess.service.CircleUserService;
import com.guess.service.MessageService;

@Component
public class MessageServiceImpl extends BaseServiceImpl<Message, String> implements MessageService{
	private MessageDao messageDao;
	@Autowired
	private CircleUserService circleUserService;

	public MessageDao getMessageDao() {
		return messageDao;
	}
	@Autowired
	public void setMessageDao(MessageDao messageDao) {
		super.setBaseDao(messageDao);
		this.messageDao = messageDao;
	}
	
	public List<Message> getAll(String receiverId){
		return messageDao.getAll(receiverId);
	}
	
	@Transactional
	public void processFriendApplication(Message message, boolean isAgreed, String circleId,
			String nickname, String avatar) {
		String receiverId = message.getReceiverId();
		String senderId = message.getSenderId();
		
		if(isAgreed){
			if(circleId == null){
				circleId = circleUserService.getDefualtFriendCircleId(receiverId);
			}
			
			CircleUser circleUser = new CircleUser();
			circleUser.setCircleId(circleId);
			circleUser.setUserId(receiverId);
			circleUser.set_userId(senderId);
			circleUser.setType(CircleUserType.FRIEND);
			circleUserService.save(circleUser);
			
			String senderDefaultCircleId = circleUserService.getDefualtFriendCircleId(senderId);
			CircleUser _circleUser = new CircleUser();
			_circleUser.setCircleId(senderDefaultCircleId);
			_circleUser.setUserId(senderId);
			_circleUser.set_userId(receiverId);
			_circleUser.setType(CircleUserType.FRIEND);
			circleUserService.save(_circleUser);
			
			Message replyMessage = new Message();
			replyMessage.setType(MessageType.FRIEND_APPLICATION_REPLAY);
			replyMessage.setReceiverId(senderId);
			replyMessage.setSenderId(receiverId);
			replyMessage.setDate(new Date());
			replyMessage.setSenderNickname(nickname);
			replyMessage.setSenderAvatar(avatar);
			messageDao.save(replyMessage);
		}
		message.setIsProcessed(true);
		messageDao.update(message);
	}
}