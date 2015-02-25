package com.guess.dao.impl;

import org.springframework.stereotype.Component;

import com.guess.dao.AdminDao;
import com.guess.model.Admin;

@Component
public class AdminDaoImpl extends BaseDaoImpl<Admin, String> implements AdminDao{

	public Admin getByUsername(String username) {
		String query = "from Admin a where a.username = :username";
		return (Admin) currentSession().createQuery(query).setString("username", username)
			.uniqueResult();
	}

}
