package com.guess.service;

import com.guess.controller.Result;
import com.guess.model.User;

public interface UserService extends BaseService<User, String>{
	boolean isUnique(String username);
	User getByUsername(String username);
	Result payAttention(User user, User org);
	void deleteFriend(String username, String friendUsername);
	void deleteAttention(String username, String rgUsername);
}
