package com.guess.service;

import java.util.List;

import com.guess.enums.UserRole;
import com.guess.model.CircleUser;
import com.guess.model.User;

public interface CircleUserService extends BaseService<CircleUser, String>{
	boolean exists(String userId, String _userId);
	void deleteFollowingAndFollower(String userId, String _userId);
	void payAttention(String userId, String _userId, UserRole role, String nickname);
	List<User> getAll(String userId);
}
