package com.guess.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.guess.dao.QuestionStatisticsDao;
import com.guess.model.QuestionStatistics;
import com.guess.service.QuestionStatisticsService;

@Component
@Transactional
public class QuestionStatisticsServiceImpl extends BaseServiceImpl<QuestionStatistics, String>
	implements QuestionStatisticsService{
	private QuestionStatisticsDao questionStatisticsDao;

	public QuestionStatisticsDao getQuestionStatisticsDao() {
		return questionStatisticsDao;
	}
	@Autowired
	public void setQuestionStatisticsDao(QuestionStatisticsDao questionStatisticsDao) {
		super.setBaseDao(questionStatisticsDao);
		this.questionStatisticsDao = questionStatisticsDao;
	}
}
