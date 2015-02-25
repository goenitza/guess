package com.guess.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.guess.dao.AdminDao;
import com.guess.model.Admin;
import com.guess.service.AdminService;

@Component
@Transactional
public class AdminServiceImpl extends BaseServiceImpl<Admin, String> implements AdminService{

	private AdminDao adminDao;

	public AdminDao getAdminDao() {
		return adminDao;
	}
	@Autowired
	public void setAdminDao(AdminDao adminDao) {
		super.setBaseDao(adminDao);
		this.adminDao = adminDao;
	}
	
	public Admin getByUsername(String username) {
		return adminDao.getByUsername(username);
	}
}
