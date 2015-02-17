package com.guess.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.guess.dao.CircleUserDao;
import com.guess.enums.CircleUserType;
import com.guess.model.CircleUser;

@Component
public class CircleUserDaoImpl extends BaseDaoImpl<CircleUser, String> implements CircleUserDao{

	public boolean exist(String userId, String _userId) {
		return get(userId, _userId) == null ? false : true;
	}
	
	public boolean exist(String userId, String _userId, String circleId) {
		return get(userId, _userId, circleId) == null ? false : true;
	}
	
	public CircleUser get(String userId, String _userId){
		String query = "from CircleUser cu where cu.userId = ? and cu._userId = ?";
		List<CircleUser> circleUsers = hibernateTemplate.find(query, userId, _userId);
		return circleUsers.size() == 0 ? null : circleUsers.get(0);
	}
	
	public CircleUser get(String userId, String _userId, String circleId){
		String query = "from CircleUser cu where cu.userId = ? and cu._userId = ? and cu.circleId = ?";
		List<CircleUser> circleUsers = hibernateTemplate.find(query, userId, _userId, circleId);
		return circleUsers.size() == 0 ? null : circleUsers.get(0);
	}

	public List<String> getAllFriendIds(String userId) {
		String query = "select distinct cu._userId from CircleUser cu where cu.userId = ? and cu.type = '" + CircleUserType.FRIEND + "'";
		List<String> ids = hibernateTemplate.find(query, userId);
		return ids;
	}

	public List<String> getAllFollowerIds(String userId) {
		String query = "select distinct cu._userId from CircleUser cu where cu.userId = ? and cu.type = '" + CircleUserType.FOLLOWER + "'";
		List<String> ids = hibernateTemplate.find(query, userId);
		return ids;
	}

	public List<String> getAllFollowingIds(String userId) {
		String query = "select distinct cu._userId from CircleUser cu where cu.userId = ? and cu.type = '" + CircleUserType.FOLLOWING + "'";
		List<String> ids = hibernateTemplate.find(query, userId);
		return ids;
	}

	public List<String> getFriendOrFollowerByCircle(String userId,
			String circleId) {
		String query = "select distinct cu._userId from CircleUser cu where cu.userId = ? and cu.circleId = ? and ( cu.type = '" 
			+ CircleUserType.FRIEND + "' or cu.type = '" + CircleUserType.FOLLOWER + "')";
		List<String> ids = hibernateTemplate.find(query, userId, circleId);
		return ids;
	}

}
