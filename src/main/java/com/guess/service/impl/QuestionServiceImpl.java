package com.guess.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.guess.dao.QuestionDao;
import com.guess.model.Message;
import com.guess.model.MessageType;
import com.guess.model.Question;
import com.guess.model.QuestionCategory;
import com.guess.model.QuestionStatistics;
import com.guess.model.UserCreateQuestion;
import com.guess.model.UserShareQuestion;
import com.guess.service.MessageService;
import com.guess.service.QuestionCategoryService;
import com.guess.service.QuestionService;
import com.guess.service.QuestionStatisticsService;
import com.guess.service.UserCreateQuestionService;
import com.guess.service.UserShareQuestionService;
import com.guess.vo.Category;

@Component
public class QuestionServiceImpl extends BaseServiceImpl<Question, String> implements QuestionService{
	
	private QuestionDao questionDao;
	@Autowired
	private QuestionCategoryService questionCategoryService;
	@Autowired
	private UserCreateQuestionService userCreateQuestionService;
	@Autowired
	private QuestionStatisticsService questionStatisticsService;
	@Autowired
	private UserShareQuestionService userShareQuestionService;
	@Autowired
	private MessageService messageService;
	
	public QuestionDao getQuestionDao() {
		return questionDao;
	}
	@Autowired
	public void setQuestionDao(QuestionDao questionDao) {
		super.setBaseDao(questionDao);
		this.questionDao = questionDao;
	}

	@Transactional()
	public String save(Question question, String friends){
		//Question
		String id = super.save(question);
		
		//UserCreateQuestion
		UserCreateQuestion userCreateQuestion = new UserCreateQuestion();
		userCreateQuestion.setDate(question.getDate());
		userCreateQuestion.setQuestionId(id);
		userCreateQuestion.setUsername(question.getUsername());
		userCreateQuestionService.save(userCreateQuestion);
		
		//QuestionCategory
		String categories = question.getCategories();
		if(StringUtils.isNotBlank(categories)){
			List<Category> categoryList = JSON.parseArray(categories, Category.class);
			for(Category c : categoryList){
				QuestionCategory questionCategory = new QuestionCategory();
				questionCategory.setQuestionId(id);
				questionCategory.setCategoryId(c.getId());
				questionCategoryService.save(questionCategory);
			}
		}
		
		//QuestionStatistics
		QuestionStatistics questionStatistics = new QuestionStatistics();
		questionStatistics.setIsObjective(StringUtils.isBlank(question.getAnswer()) ? false : true);
		questionStatisticsService.save(questionStatistics);
		
		if(!question.getIsPublic() && question.getIsPublished()){
			if(friends != null){
				//UserShareQuestion
				UserShareQuestion userShareQuestion = new UserShareQuestion();
				userShareQuestion.setQuestionId(id);
				userShareQuestion.setFriends(friends);
				userShareQuestionService.save(userShareQuestion);
				
				//send message to friends
				List<String> usernames = JSON.parseArray(friends, String.class);
				for(String username : usernames){
					Message message = new Message();
					message.setType(MessageType.QUESTION_SHARE);
					message.setSender(question.getUsername());
					message.setReceiver(username);
					message.setRelatedObject(id);
					message.setDate(question.getDate());
					messageService.save(message);
				}
			}
		}
		
		return id;
	}
	
	public void share(String id, String friends) {
		Question question = questionDao.get(id);
		if(friends != null){
			//UserShareQuestion
			UserShareQuestion userShareQuestion = new UserShareQuestion();
			userShareQuestion.setQuestionId(id);
			userShareQuestion.setFriends(friends);
			userShareQuestionService.save(userShareQuestion);
			
			//send message to friends
			List<String> usernames = JSON.parseArray(friends, String.class);
			for(String username : usernames){
				Message message = new Message();
				message.setType(MessageType.QUESTION_SHARE);
				message.setSender(question.getUsername());
				message.setReceiver(username);
				message.setRelatedObject(id);
				message.setDate(new Date());
				messageService.save(message);
			}
		}
	}
}
