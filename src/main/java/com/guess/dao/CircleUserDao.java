package com.guess.dao;

import java.util.List;

import com.guess.model.CircleUser;

public interface CircleUserDao extends BaseDao<CircleUser, String>{
	boolean exist(String userId, String _userId);
	boolean exist(String userId, String _userId, String circleId);
	CircleUser get(String userId, String _userId);
	CircleUser get(String userId, String _userId, String circleId);
	List<String> getAllFriendIds(String userId);
	List<String> getAllFollowerIds(String userId);
	List<String> getAllFollowingIds(String userId);
	List<String> getFriendOrFollowerByCircle(String userId, String circleId);
}
