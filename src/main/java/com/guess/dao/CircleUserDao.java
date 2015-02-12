package com.guess.dao;

import com.guess.model.CircleUser;

public interface CircleUserDao extends BaseDao<CircleUser, String>{
	boolean exist(String userId, String _userId);
	CircleUser get(String userId, String _userId);
}
