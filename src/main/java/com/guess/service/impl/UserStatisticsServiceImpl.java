package com.guess.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.guess.dao.UserStatisticsDao;
import com.guess.model.UserStatistics;
import com.guess.service.UserStatisticsService;

@Component
@Transactional
public class UserStatisticsServiceImpl extends BaseServiceImpl<UserStatistics, String>
	implements UserStatisticsService{
	private UserStatisticsDao userStatisticsDao;

	public UserStatisticsDao getUserStatisticsDao() {
		return userStatisticsDao;
	}
	@Autowired
	public void setUserStatisticsDao(UserStatisticsDao userStatisticsDao) {
		super.setBaseDao(userStatisticsDao);
		this.userStatisticsDao = userStatisticsDao;
	}
}
