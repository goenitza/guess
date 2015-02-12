package com.guess.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.guess.dao.CircleUserDao;
import com.guess.model.CircleUser;

@Component
public class CircleUserDaoImpl extends BaseDaoImpl<CircleUser, String> implements CircleUserDao{

	public boolean exist(String userId, String _userId) {
		return get(userId, _userId) == null ? false : true;
	}
	
	public CircleUser get(String userId, String _userId){
		String query = "from CircleUser cu where cu.userId = ? and cu._userId = ?";
		List<CircleUser> circleUsers = hibernateTemplate.find(query, userId, _userId);
		return circleUsers.size() == 0 ? null : circleUsers.get(0);
	}

}
