package com.guess.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.guess.dao.UserDao;
import com.guess.model.User;

@Component
public class UserDaoImpl extends BaseDaoImpl<User, String> implements UserDao{

	public boolean isUnique(String username) {
		String query = "from User u where u.username = ?";
		List<User> users = hibernateTemplate.find(query, username);
		return users.size() == 0;
	}

	public User getByUsername(String username) {
		String query = "from User u where u.username = ?";
		List<User> users = hibernateTemplate.find(query, username);
		if(users.size() == 0)
			return null;
		else {
			return users.get(0);
		}
	}
}
