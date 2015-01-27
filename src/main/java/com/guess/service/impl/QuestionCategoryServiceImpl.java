package com.guess.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guess.dao.QuestionCategoryDao;
import com.guess.model.QuestionCategory;
import com.guess.service.QuestionCategoryService;

@Component
public class QuestionCategoryServiceImpl extends BaseServiceImpl<QuestionCategory, String> implements QuestionCategoryService{
	private QuestionCategoryDao questionCategoryDao;

	public QuestionCategoryDao getQuestionCategoryDao() {
		return questionCategoryDao;
	}
	@Autowired
	public void setQuestionCategoryDao(QuestionCategoryDao questionCategoryDao) {
		super.setBaseDao(questionCategoryDao);
		this.questionCategoryDao = questionCategoryDao;
	}
}
