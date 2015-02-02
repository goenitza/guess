package com.guess.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guess.dao.UserDao;
import com.guess.model.User;
import com.guess.service.UserService;

@Component
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService{
	
	private UserDao userDao;
	
//	public UserServiceImpl(){
//		super.setBaseDao(userDao);
//	}

	public UserDao getUserDao() {
		return userDao;
	}
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
		super.setBaseDao(userDao);
	}
	
	public boolean isUnique(String username) {
		
		return userDao.getByUsername(username) == null;
	}
	public User getByUsername(String username) {
		return userDao.getByUsername(username);
	}
	public void addAttention(User user, User enterprise) {
		
	}
}
