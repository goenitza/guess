package com.guess.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.guess.dao.UserCreateQuestionDao;
import com.guess.model.UserCreateQuestion;
import com.guess.service.UserCreateQuestionService;

@Component
@Transactional
public class UserCreateQuestionServiceImpl extends BaseServiceImpl<UserCreateQuestion, String> implements UserCreateQuestionService{
	private UserCreateQuestionDao userCreateQuestionDao;

	public UserCreateQuestionDao getUserCreateQuestionDao() {
		return userCreateQuestionDao;
	}
	@Autowired
	public void setUserCreateQuestionDao(UserCreateQuestionDao userCreateQuestionDao) {
		super.setBaseDao(userCreateQuestionDao);
		this.userCreateQuestionDao = userCreateQuestionDao;
	}
}
