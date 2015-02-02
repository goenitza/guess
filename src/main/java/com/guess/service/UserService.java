package com.guess.service;

import com.guess.model.User;

public interface UserService extends BaseService<User, String>{
	boolean isUnique(String username);
	User getByUsername(String username);
	void addAttention(User user, User enterprise);
}
