package com.guess.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.guess.dao.IndividualDao;
import com.guess.model.Individual;
import com.guess.model.UserStatistics;
import com.guess.service.IndividualService;
import com.guess.service.UserStatisticsService;

@Component
public class IndividualServiceImpl extends BaseServiceImpl<Individual, String> implements IndividualService{
	private IndividualDao individualDao;
	@Autowired
	private UserStatisticsService userStatisticsService;

	public IndividualDao getIndividualDao() {
		return individualDao;
	}
	@Autowired
	public void setIndividualDao(IndividualDao individualDao) {
		super.setBaseDao(individualDao);
		this.individualDao = individualDao;
	}
	
	public Individual getByUsername(String username) {
		return individualDao.getByUsername(username);
	}
	
	@Transactional
	@Override
	public String save(Individual individual){
		String id = individualDao.save(individual);
		
		UserStatistics userStatistics = new UserStatistics();
		userStatistics.setUserId(id);
		userStatisticsService.save(userStatistics);
		
		return id;
	}
}
