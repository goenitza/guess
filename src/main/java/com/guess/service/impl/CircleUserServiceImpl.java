package com.guess.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.guess.dao.CircleDao;
import com.guess.dao.CircleUserDao;
import com.guess.dao.IndividualDao;
import com.guess.dao.OrganizationDao;
import com.guess.enums.CircleType;
import com.guess.enums.CircleUserType;
import com.guess.enums.UserRole;
import com.guess.model.Circle;
import com.guess.model.CircleUser;
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
	public void payAttention(String userId, String _userId, UserRole role) {
		String defaultFollowingCircleId = getDefualtFollowerCircleId(userId);
		String defaultFollowerCircleId = getDefualtFollowerCircleId(_userId);
		
		CircleUser circleUser = new CircleUser();
		circleUser.setCircleId(defaultFollowingCircleId);
		circleUser.setUserId(userId);
		circleUser.set_userId(_userId);
		circleUser.setType(CircleUserType.FOLLOWING);
		circleUserDao.save(circleUser);
		
		CircleUser _circleUser = new CircleUser();
		_circleUser.setCircleId(defaultFollowerCircleId);
		_circleUser.setUserId(_userId);
		_circleUser.set_userId(userId);
		_circleUser.setType(CircleUserType.FOLLOWER);
		circleUserDao.save(_circleUser);
	}
	
	String getDefualtFollowingCircleId(String userId, UserRole role){
		User user = null;
		if(role == UserRole.INDIVIDUAL){
			user = individualDao.get(userId);
		}else if(role == UserRole.ORG){
			user = organizationDao.get(userId);
		}else {
			throw new RuntimeException("the type of user is incorrect: "  + role);
		}
		Set<Circle> circles = user.getCircles();
		for(Circle circle : circles){
			if(circle.getType() == CircleType.FOLLOWING_DEFAULT);{
				return circle.getId();
			}
		}
		throw new RuntimeException("Default following circle is not found");
	}
	
	String getDefualtFriendCircleId(String userId){
		User user = individualDao.get(userId);
		
		Set<Circle> circles = user.getCircles();
		for(Circle circle : circles){
			if(circle.getType() == CircleType.FRIEND_DEFAULT);{
				return circle.getId();
			}
		}
		
		throw new RuntimeException("Default friend circle is not found");
	}
	
	String getDefualtFollowerCircleId(String userId){
		User user = organizationDao.get(userId);
		
		Set<Circle> circles = user.getCircles();
		for(Circle circle : circles){
			if(circle.getType() == CircleType.FRIEND_DEFAULT);{
				return circle.getId();
			}
		}
		throw new RuntimeException("Default follower circle is not found");
	}
}
