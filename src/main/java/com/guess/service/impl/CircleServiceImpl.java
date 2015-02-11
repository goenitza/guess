package com.guess.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guess.dao.CircleDao;
import com.guess.model.Circle;
import com.guess.service.CircleService;

@Component
public class CircleServiceImpl extends BaseServiceImpl<Circle, String> implements CircleService{
	private CircleDao circleDao;

	public CircleDao getCircleDao() {
		return circleDao;
	}
	@Autowired
	public void setCircleDao(CircleDao circleDao) {
		super.setBaseDao(circleDao);
		this.circleDao = circleDao;
	}
}
