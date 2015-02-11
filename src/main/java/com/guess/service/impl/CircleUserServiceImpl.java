package com.guess.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guess.dao.CircleUserDao;
import com.guess.model.CircleUser;
import com.guess.service.CircleUserService;

@Component
public class CircleUserServiceImpl extends BaseServiceImpl<CircleUser, String> implements CircleUserService{
	private CircleUserDao circleUserDao;

	public CircleUserDao getCircleUserDao() {
		return circleUserDao;
	}
	@Autowired
	public void setCircleUserDao(CircleUserDao circleUserDao) {
		super.setBaseDao(circleUserDao);
		this.circleUserDao = circleUserDao;
	}
}
