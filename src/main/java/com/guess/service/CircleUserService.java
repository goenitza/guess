package com.guess.service;

import com.guess.enums.UserRole;
import com.guess.model.CircleUser;

public interface CircleUserService extends BaseService<CircleUser, String>{
	boolean exists(String userId, String _userId);
	void deleteFollowingAndFollower(String userId, String _userId);
	void payAttention(String userId, String _userId, UserRole role, String nickname);
	String getDefualtFriendCircleId(String userId);
}
