package com.guess.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.guess.dao.UserDao;
import com.guess.enums.MessageType;
import com.guess.model.Message;
import com.guess.model.User;
import com.guess.model.UserStatistics;
import com.guess.service.MessageService;
import com.guess.service.UserService;
import com.guess.service.UserStatisticsService;
import com.guess.vo.FriendDB;
import com.guess.vo.OrgDB;
import com.guess.vo.Result;

@Component
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService{
	
	private UserDao userDao;
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserStatisticsService userStatisticsService;
	
	public UserDao getUserDao() {
		return userDao;
	}
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
		super.setBaseDao(userDao);
	}
	
	public boolean isUnique(String username) {
		
		return userDao.get(username) == null;
	}
	
	@Override
	@Transactional
	public String save(User user){
		String id = super.save(user);
		UserStatistics userStatistics = new UserStatistics();
		userStatistics.setId(user.getUsername());
		userStatisticsService.save(userStatistics);
		return id;
	}
	
	@Transactional
	public Result payAttention(User user, User org) {
		List<OrgDB> orgDBs;
		if(StringUtils.isBlank(user.getOrgs())){
			orgDBs = new ArrayList<OrgDB>();
		}else {
			orgDBs = JSON.parseArray(user.getOrgs(), OrgDB.class);
		}
		
		//confirm whether the user has already payed attention to the org
		for(OrgDB o : orgDBs){
			if(o.getUsername().equals(org.getUsername())){
				Result result = new Result();
				result.setError("你已关注此组织");
				return result;
			}
		}
		
		//update 'orgs' of user
		OrgDB orgDB = new OrgDB();
		orgDB.setUsername(org.getUsername());
		orgDB.setNickname(org.getNickname());
		orgDB.setAvatar(org.getAvatar());
		orgDB.setIsVerified(org.getIsVerified());
		orgDBs.add(orgDB);
		user.setOrgs(JSON.toJSONString(orgDBs));
		userDao.update(user);
		
		//update 'friends' of org
		List<FriendDB> friendDBs;
		if(StringUtils.isBlank(org.getFriends())){
			friendDBs = new ArrayList<FriendDB>();
		}else {
			friendDBs = JSON.parseArray(org.getFriends(), FriendDB.class);
		}
		FriendDB friendDB = new FriendDB();
		friendDB.setUsername(user.getUsername());
		friendDB.setNickname(user.getNickname());
		friendDB.setAvatar(user.getAvatar());
		friendDBs.add(friendDB);
		org.setFriends(JSON.toJSONString(friendDBs));
		userDao.update(org);
		
		Message message = new Message();
		message.setType(MessageType.ORG_ATTENTION);
		message.setReceiver(org.getUsername());
		message.setSender(user.getUsername());
		message.setDate(new Date());
		messageService.save(message);
		
		return null;
	}
	@Transactional
	public void deleteFriend(String username, String friendUsername) {
		//update 'friends' of user
		User user = userDao.get(username);
		if(StringUtils.isNotBlank(user.getFriends())){
			List<FriendDB> userFriendDBs = JSON.parseArray(user.getFriends(), FriendDB.class);
			Iterator<FriendDB> iterator = userFriendDBs.iterator();
			FriendDB userFriendDB;
			boolean exists = false;
			while(iterator.hasNext()){
				userFriendDB = iterator.next();
				if(userFriendDB.getUsername().equals(friendUsername)){
					exists = true;
					iterator.remove();
					break;
				}
			}
			if(exists){
				user.setFriends(JSON.toJSONString(userFriendDBs));
				userDao.update(user);
			}
		}
		
		//update 'friends' of friend
		User friend = userDao.get(friendUsername);
		if(StringUtils.isNotBlank(friend.getFriends())){
			List<FriendDB> friendFriendDBs = JSON.parseArray(friend.getFriends(), FriendDB.class);
			Iterator<FriendDB> iterator = friendFriendDBs.iterator();
			boolean exists = false;
			FriendDB friendFriendDB;
			while(iterator.hasNext()){
				friendFriendDB = iterator.next();
				if(friendFriendDB.getUsername().equals(username)){
					exists = true;
					iterator.remove();
					break;
				}
			}
			if(exists){
				friend.setFriends(JSON.toJSONString(friendFriendDBs));
				userDao.update(friend);
			}
		}
	}
	@Transactional
	public void deleteAttention(String username, String orgUsername) {
		//update 'orgs' of user
		User user = userDao.get(username);
		if(StringUtils.isNotBlank(user.getOrgs())){
			List<OrgDB> userOrgDBs = JSON.parseArray(user.getOrgs(), OrgDB.class);
			boolean exists = false;
			OrgDB userOrgDB;
			Iterator<OrgDB> iterator = userOrgDBs.iterator();
			while(iterator.hasNext()){
				userOrgDB = iterator.next();
				if(userOrgDB.getUsername().equals(orgUsername)){
					exists = true;
					iterator.remove();
					break;
				}
			}
			if(exists){
				user.setOrgs(JSON.toJSONString(userOrgDBs));
				userDao.update(user);
			}
		}
		//update 'friends' of org
		User org = userDao.get(orgUsername);
		if(StringUtils.isNotBlank(org.getFriends())){
			List<FriendDB> friendDBs = JSON.parseArray(org.getFriends(), FriendDB.class);
			boolean exists = false;
			FriendDB friendDB;
			Iterator<FriendDB> iterator = friendDBs.iterator();
			while(iterator.hasNext()){
				friendDB = iterator.next();
				if(friendDB.getUsername().equals(username)){
					exists = true;
					iterator.remove();
					break;
				}
			}
			if(exists){
				org.setFriends(JSON.toJSONString(friendDBs));
				userDao.update(org);
			}
		}
	}
}
