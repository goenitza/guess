package com.guess.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.guess.dao.AdminDao;
import com.guess.model.Admin;

@Component
public class AdminDaoImpl extends BaseDaoImpl<Admin, String> implements AdminDao{

	public Admin getByUsername(String username) {
		String query = "from Admin a where a.username = ?";
		List<Admin> admins = hibernateTemplate.find(query, username);
		if(admins.size() == 0){
			return null;
		}else {
			return admins.get(0);
		}
	}

}
