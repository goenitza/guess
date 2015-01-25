package com.guess.dao;

import com.guess.model.User;

public interface UserDao extends BaseDao<User, String>{
	User getByUsername(String username);
}
