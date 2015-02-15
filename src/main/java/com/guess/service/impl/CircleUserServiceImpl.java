package com.guess.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.guess.dao.CircleDao;
import com.guess.dao.CircleUserDao;
import com.guess.dao.IndividualDao;
import com.guess.dao.MessageDao;
import com.guess.dao.OrganizationDao;
import com.guess.enums.CircleUserType;
import com.guess.enums.MessageType;
import com.guess.enums.UserRole;
import com.guess.model.CircleUser;
import com.guess.model.Message;
import com.guess.model.User;
import com.guess.service.CircleUserService;

@Component
public class CircleUserServiceImpl extends BaseServiceImpl<CircleUser, String> implements CircleUserService{
	
	private CircleUserDao circleUserDao;
	@Autowired
	private CircleDao circleDao;
	@Autowired
	private IndividualDao individualDao;
	@Autowired
	private OrganizationDao organizationDao;
	@Autowired
	private MessageDao messageDao;

	public CircleUserDao getCircleUserDao() {
		return circleUserDao;
	}
	@Autowired
	public void setCircleUserDao(CircleUserDao circleUserDao) {
		super.setBaseDao(circleUserDao);
		this.circleUserDao = circleUserDao;
	}
	
	public boolean exists(String userId, String _userId) {
		return circleUserDao.exist(userId, _userId);
	}
	
	@Transactional
	public void deleteFollowingAndFollower(String userId, String _userId) {
		CircleUser circleUser = circleUserDao.get(userId, _userId);
		if(circleUser != null){
			CircleUser _circleUser = circleUserDao.get(_userId, userId);
			circleUserDao.delete(circleUser);
			circleUserDao.delete(_circleUser);
		}
		
	}
	
	@Transactional
	public void payAttention(String userId, String _userId, UserRole role, String nickname) {
		
		CircleUser circleUser = new CircleUser();
		circleUser.setUserId(userId);
		circleUser.set_userId(_userId);
		circleUser.setType(CircleUserType.FOLLOWING);
		circleUserDao.save(circleUser);
		
		CircleUser _circleUser = new CircleUser();
		_circleUser.setUserId(_userId);
		_circleUser.set_userId(userId);
		_circleUser.setType(CircleUserType.FOLLOWER);
		circleUserDao.save(_circleUser);
		
		Message message = new Message();
		message.setType(MessageType.ORG_ATTENTION);
		message.setReceiverId(_userId);
		message.setSenderId(userId);
		message.setSenderNickname(nickname);
		message.setDate(new Date());
		messageDao.save(message);
	}
	
	@Transactional
	public List<User> getAll(String userId) {
		List<CircleUser> circleUsers = circleUserDao.getAll(userId);
		
		
		
		return null;
	}
}
