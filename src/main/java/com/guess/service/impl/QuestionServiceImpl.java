package com.guess.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.guess.dao.QuestionDao;
import com.guess.enums.MessageType;
import com.guess.model.Category;
import com.guess.model.Message;
import com.guess.model.Question;
import com.guess.model.CategoryQuestion;
import com.guess.model.QuestionStatistics;
import com.guess.model.UserCreateQuestion;
import com.guess.model.QuestionShare;
import com.guess.model.UserStatistics;
import com.guess.service.CategoryService;
import com.guess.service.MessageService;
import com.guess.service.QuestionCategoryService;
import com.guess.service.QuestionService;
import com.guess.service.QuestionStatisticsService;
import com.guess.service.UserCreateQuestionService;
import com.guess.service.QuestionShareService;
import com.guess.service.UserStatisticsService;
import com.guess.vo.CategoryVO;

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
	private QuestionShareService questionShareService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserStatisticsService userStatisticsService;
	@Autowired
	private CategoryService categoryService;
	
	public QuestionDao getQuestionDao() {
		return questionDao;
	}
	@Autowired
	public void setQuestionDao(QuestionDao questionDao) {
		super.setBaseDao(questionDao);
		this.questionDao = questionDao;
	}

	@Transactional
	public String save(Question question, String friends){
		//Question
		String id = super.save(question);
		
		//UserCreateQuestion
		UserCreateQuestion userCreateQuestion = new UserCreateQuestion();
		userCreateQuestion.setId(question.getUsername());
		userCreateQuestion.setDate(question.getDate());
		userCreateQuestion.setQuestionId(id);
		userCreateQuestionService.save(userCreateQuestion);
		
		//UserStatistics
		UserStatistics userStatistics = userStatisticsService.get(question.getUsername());
		userStatistics.setCreateCount(userStatistics.getCreateCount() + 1);
		if(StringUtils.isNotBlank(question.getAnswer())){
			userStatistics.setCreateObjectiveCount(userStatistics.getCreateObjectiveCount() + 1);
		}
		userStatisticsService.update(userStatistics);
		
		String categories = question.getCategories();
		if(StringUtils.isNotBlank(categories)){
			List<CategoryVO> categoryList = JSON.parseArray(categories, CategoryVO.class);
			for(CategoryVO c : categoryList){
				//CategoryQuestion
				CategoryQuestion categoryQuestion = new CategoryQuestion();
				categoryQuestion.setCategoryId(c.getId());
				categoryQuestion.setQuestionId(id);
				questionCategoryService.save(categoryQuestion);
				
				//Category
				Category category = categoryService.get(c.getId());
				category.setQuestionCount(category.getQuestionCount() + 1);
				categoryService.update(category);
			}
		}
		
		int shareCount = 0;
		
		if(!question.getIsPublic() && question.getIsPublished()){
			if(friends != null){
				//UserShareQuestion
				QuestionShare questionSharen = new QuestionShare();
				questionSharen.setId(id);
				questionSharen.setFriends(friends);
				questionShareService.save(questionSharen);
				
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
				shareCount = usernames.size();
			}
			
			//QuestionStatistics
			QuestionStatistics questionStatistics = new QuestionStatistics();
			questionStatistics.setShareCount(shareCount);
			questionStatistics.setIsObjective(StringUtils.isBlank(question.getAnswer()) ? false : true);
			questionStatisticsService.save(questionStatistics);
		}
		
		return id;
	}
	
	public void share(String username, String id, String friends) {
		if(friends != null){
			List<String> friendList = JSON.parseArray(friends, String.class);

			//UserShareQuestion
			QuestionShare questionShare = questionShareService.get(id);
			if(questionShare == null){
				questionShare = new QuestionShare();
				questionShare.setId(id);
				questionShare.setFriends(friends);
				questionShareService.save(questionShare);
			}else {
				List<String> list = JSON.parseArray(questionShare.getFriends(), String.class);
				if(list == null){
					questionShare.setFriends(friends);
				}else {
					list.addAll(friendList);
					questionShare.setFriends(JSON.toJSONString(list));
				}
				questionShareService.update(questionShare);
			}
			
			
			//send message to friends
			for(String friend : friendList){
				Message message = new Message();
				message.setType(MessageType.QUESTION_SHARE);
				message.setSender(username);
				message.setReceiver(friend);
				message.setRelatedObject(id);
				message.setDate(new Date());
				messageService.save(message);
			}
			
			//update QuestionStatistics
			QuestionStatistics questionStatistics = questionStatisticsService.get(id);
			questionStatistics.setShareCount(questionStatistics.getShareCount() 
					+ friendList.size());
			questionStatisticsService.update(questionStatistics);
		}
	}
}
