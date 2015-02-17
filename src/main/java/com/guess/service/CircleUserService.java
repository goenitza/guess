package com.guess.service;

import java.util.List;

import com.guess.enums.UserRole;
import com.guess.model.CircleUser;
import com.guess.vo.IndividualVO;
import com.guess.vo.OrgVO;

public interface CircleUserService extends BaseService<CircleUser, String>{
	boolean exists(String userId, String _userId);
	boolean exists(String userId, String _userId, String circleId);
	void deleteFollowingAndFollower(String userId, String _userId);
	void payAttention(String userId, String _userId, UserRole role, String nickname);
	List<IndividualVO> getAllFriend(String userId);
	List<IndividualVO> getAllFollower(String userId);
	List<OrgVO> getAllFollowing(String userId);
	List<IndividualVO> getFriendOrFollowerByCircle(String userId, String circleId);
	void deleteFromCircle(String id, String userId, String circleId);
}
