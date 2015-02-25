package com.guess.service.impl;

import java.util.ArrayList;
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
import com.guess.model.Individual;
import com.guess.model.Message;
import com.guess.model.Organization;
import com.guess.service.CircleUserService;
import com.guess.vo.IndividualVO;
import com.guess.vo.OrgVO;

@Component
@Transactional
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
	
	public boolean exists(String userId, String _userId, String circleId) {
		return circleUserDao.exist(userId, _userId, circleId);
	}
	
	public void deleteFollowingAndFollower(String userId, String _userId) {
		CircleUser circleUser = circleUserDao.get(userId, _userId);
		if(circleUser != null){
			CircleUser _circleUser = circleUserDao.get(_userId, userId);
			circleUserDao.delete(circleUser);
			circleUserDao.delete(_circleUser);
		}
		
	}
	
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
	
	public List<IndividualVO> getAllFriend(String userId) {
		List<String> ids = circleUserDao.getAllFriendIds(userId);
		List<IndividualVO> individualVOs = new ArrayList<IndividualVO>();
		for(String id : ids){
			Individual individual = individualDao.get(id);
			IndividualVO individualVO = new IndividualVO();
			individualVO.setId(individual.getId());
			individualVO.setNickname(individual.getNickname());
			individualVO.setUsername(individual.getUsername());
			individualVO.setAvatar(individual.getAvatar());
			individualVOs.add(individualVO);
		}
		return individualVOs;
	}
	
	public List<IndividualVO> getAllFollower(String userId) {
		List<String> ids = circleUserDao.getAllFollowerIds(userId);
		List<IndividualVO> individualVOs = new ArrayList<IndividualVO>();
		for(String id : ids){
			Individual individual = individualDao.get(id);
			IndividualVO individualVO = new IndividualVO();
			individualVO.setId(individual.getId());
			individualVO.setNickname(individual.getNickname());
			individualVO.setUsername(individual.getUsername());
			individualVO.setAvatar(individual.getAvatar());
			individualVOs.add(individualVO);
		}
		return individualVOs;
	}
	
	public List<OrgVO> getAllFollowing(String userId) {
		List<String> ids = circleUserDao.getAllFollowingIds(userId);
		List<OrgVO> orgVOs = new ArrayList<OrgVO>();
		for(String id : ids){
			Organization organization = organizationDao.get(id);
			OrgVO orgVO = new OrgVO();
			orgVO.setId(organization.getId());
			orgVO.setNickname(organization.getNickname());
			orgVO.setUsername(organization.getUsername());
			orgVO.setAvatar(organization.getAvatar());
			orgVO.setIsVerified(organization.getIsVerified());
			orgVOs.add(orgVO);
		}
		return orgVOs;
	}
	
	public List<IndividualVO> getFriendOrFollowerByCircle(String userId,
			String circleId) {
		List<String> ids = circleUserDao.getFriendOrFollowerByCircle(userId, circleId);
		List<IndividualVO> individualVOs = new ArrayList<IndividualVO>();
		for(String id : ids){
			Individual individual = individualDao.get(id);
			IndividualVO individualVO = new IndividualVO();
			individualVO.setId(individual.getId());
			individualVO.setNickname(individual.getNickname());
			individualVO.setUsername(individual.getUsername());
			individualVO.setAvatar(individual.getAvatar());
			individualVOs.add(individualVO);
		}
		return individualVOs;
	}
	
	public void deleteFromCircle(String id, String userId, String circleId) {
		CircleUser circleUser = circleUserDao.get(id, userId, circleId);
		if(circleUser != null){
			circleUserDao.delete(circleUser);
		}
		
	}
}
