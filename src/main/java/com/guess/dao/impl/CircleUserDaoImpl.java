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
		String query = "from CircleUser cu where cu.userId = :userId and cu._userId = :_userId";
		return (CircleUser) currentSession().createQuery(query).setString("userId", userId)
			.setString("_userId", _userId).setMaxResults(1).uniqueResult();
	}
	
	public CircleUser get(String userId, String _userId, String circleId){
		String query = "from CircleUser cu where cu.userId = :userId and cu._userId = :_userId and cu.circleId = :circleId";
		return (CircleUser) currentSession().createQuery(query).setString("userId", userId)
				.setString("_userId", _userId).setString("circleId", circleId).uniqueResult();
	}

	public List<String> getAllFriendIds(String userId) {
		String query = "select distinct cu._userId from CircleUser cu where cu.userId = :userId and cu.type = '" + CircleUserType.FRIEND + "'";
		return currentSession().createQuery(query).setString("userId", userId)
				.list();
	}

	public List<String> getAllFollowerIds(String userId) {
		String query = "select distinct cu._userId from CircleUser cu where cu.userId = :userId and cu.type = '" + CircleUserType.FOLLOWER + "'";
		return currentSession().createQuery(query).setString("userId", userId).list();
	}

	public List<String> getAllFollowingIds(String userId) {
		String query = "select distinct cu._userId from CircleUser cu where cu.userId = :userId and cu.type = '" + CircleUserType.FOLLOWING + "'";
		return currentSession().createQuery(query).setString("userId", userId).list();
	}

	public List<String> getFriendOrFollowerByCircle(String userId,
			String circleId) {
		String query = "select distinct cu._userId from CircleUser cu where cu.userId = :userId and cu.circleId = :circleId and ( cu.type = '" 
			+ CircleUserType.FRIEND + "' or cu.type = '" + CircleUserType.FOLLOWER + "')";
		return currentSession().createQuery(query).setString("userId", userId)
				.setString("circleId", circleId).list();
	}
}
