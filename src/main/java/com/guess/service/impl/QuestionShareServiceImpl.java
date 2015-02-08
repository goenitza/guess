package com.guess.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guess.dao.QustionShareDao;
import com.guess.model.QuestionShare;
import com.guess.service.QuestionShareService;

@Component
public class QuestionShareServiceImpl extends BaseServiceImpl<QuestionShare, String>
	implements QuestionShareService{
	private QustionShareDao userShareQustionDao;

	public QustionShareDao getUserShareQustionDao() {
		return userShareQustionDao;
	}
	@Autowired
	public void setUserShareQustionDao(QustionShareDao userShareQustionDao) {
		super.setBaseDao(userShareQustionDao);
		this.userShareQustionDao = userShareQustionDao;
	}
}
