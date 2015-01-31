package com.guess.service.impl;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
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
		
		UserCreateQuestion userCreateQuestion = new UserCreateQuestion();
		userCreateQuestion.setDate(question.getDate());
		userCreateQuestion.setQuestionId(id);
		userCreateQuestion.setUserId(question.getUserId());
		userCreateQuestionService.save(userCreateQuestion);
		
		String categories = question.getCategories();
		Map<String, String> map = (Map<String, String>) JSON.parse(categories);
		Set<Entry<String, String>> entrySet = map.entrySet();
		for(Entry<String, String> entry : entrySet){
			QuestionCategory questionCategory = new QuestionCategory();
			questionCategory.setQuestionId(id);
			questionCategory.setCategoryId(entry.getKey());
			questionCategoryService.save(questionCategory);
		}
		
		return id;
	}
}
