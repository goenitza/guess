package com.guess.dao;

import com.guess.model.User;

public interface UserDao extends BaseDao<User, String>{
	boolean isUnique(String username);
	User getByUsername(String username);
}
