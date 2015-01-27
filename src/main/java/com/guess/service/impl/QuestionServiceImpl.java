package com.guess.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.guess.dao.QuestionDao;
import com.guess.model.Question;
import com.guess.model.QuestionCategory;
import com.guess.model.UserCreateQuestion;
import com.guess.service.QuestionCategoryService;
import com.guess.service.QuestionService;
import com.guess.service.UserCreateQuestionService;

@Component
public class QuestionServiceImpl extends BaseServiceImpl<Question, String> implements QuestionService{
	
	private QuestionDao questionDao;
	@Autowired
	private QuestionCategoryService questionCategoryService;
	@Autowired
	private UserCreateQuestionService userCreateQuestionService;
	
	public QuestionDao getQuestionDao() {
		return questionDao;
	}
	@Autowired
	public void setQuestionDao(QuestionDao questionDao) {
		super.setBaseDao(questionDao);
		this.questionDao = questionDao;
	}

	@Transactional()
	@Override
	public String save(Question question){
		String id = super.save(question);
		
		QuestionCategory questionCategory = new QuestionCategory();
		questionCategory.setQuestionId(id);
		questionCategory.setCategoryId(question.getCategoryIds());
		
		UserCreateQuestion userCreateQuestion = new UserCreateQuestion();
		userCreateQuestion.setDate(question.getDate());
		userCreateQuestion.setQuestionId(id);
		userCreateQuestion.setUserId(question.getUserId());
		
		questionCategoryService.save(questionCategory);
		userCreateQuestionService.save(userCreateQuestion);
		
		return id;
	}
}
