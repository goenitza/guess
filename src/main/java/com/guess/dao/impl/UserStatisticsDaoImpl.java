package com.guess.dao.impl;

import org.springframework.stereotype.Component;

import com.guess.dao.UserStatisticsDao;
import com.guess.model.UserStatistics;

@Component
public class UserStatisticsDaoImpl extends BaseDaoImpl<UserStatistics, String>
	implements UserStatisticsDao{

}
