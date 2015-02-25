package com.guess.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.guess.dao.QuestionCategoryDao;
import com.guess.model.CategoryQuestion;
import com.guess.service.QuestionCategoryService;

@Component
@Transactional
public class QuestionCategoryServiceImpl extends BaseServiceImpl<CategoryQuestion, String> implements QuestionCategoryService{
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
