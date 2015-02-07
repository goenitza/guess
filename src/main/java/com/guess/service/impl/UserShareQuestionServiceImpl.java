package com.guess.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guess.dao.UserShareQustionDao;
import com.guess.model.UserShareQuestion;
import com.guess.service.UserShareQuestionService;

@Component
public class UserShareQuestionServiceImpl extends BaseServiceImpl<UserShareQuestion, String>
	implements UserShareQuestionService{
	private UserShareQustionDao userShareQustionDao;

	public UserShareQustionDao getUserShareQustionDao() {
		return userShareQustionDao;
	}
	@Autowired
	public void setUserShareQustionDao(UserShareQustionDao userShareQustionDao) {
		super.setBaseDao(userShareQustionDao);
		this.userShareQustionDao = userShareQustionDao;
	}
}
