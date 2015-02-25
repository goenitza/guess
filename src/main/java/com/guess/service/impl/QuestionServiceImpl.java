package com.guess.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
import com.guess.service.ImageService;
import com.guess.service.MessageService;
import com.guess.service.QuestionCategoryService;
import com.guess.service.QuestionService;
import com.guess.service.QuestionStatisticsService;
import com.guess.service.UserCreateQuestionService;
import com.guess.service.QuestionShareService;
import com.guess.service.UserStatisticsService;
import com.guess.vo.CategoryVO;
import com.guess.vo.QuestionOption;
import com.guess.vo.QuestionOptions;

@Component
@Transactional
public class QuestionServiceImpl extends BaseServiceImpl<Question, String>
		implements QuestionService {

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
	@Autowired
	private ImageService imageService;

	public QuestionDao getQuestionDao() {
		return questionDao;
	}

	@Autowired
	public void setQuestionDao(QuestionDao questionDao) {
		super.setBaseDao(questionDao);
		this.questionDao = questionDao;
	}

	public String save(String nickname,String avatar, 
			Question question, MultipartFile contentImage, String options,
			MultipartFile[] optionsImages, String contextPath, List<String> userIds) throws IOException {
		// Question
		String id = super.save(question);
		
		String fileName = question.getId() + "_content"; 
		String contentUrl = imageService.saveQuestionContentImage(contextPath, contentImage, fileName);
		question.setContentUrl(contentUrl);
		
		QuestionOptions questionOptions = JSON.parseObject(options, QuestionOptions.class);
		List<QuestionOption> optionList = questionOptions.getOptions();
		for(int i = 0; i < optionList.size(); i++){
			QuestionOption option = optionList.get(i);
			if(option.getContainsImage()){
				fileName = question.getId() + "_option_" + (i + 1);
				String imageUrl = imageService.saveQuestionOptionImage(contextPath, optionsImages[i], fileName);
				option.setImageUrl(imageUrl);
			}
		}
		question.setOptions(JSON.toJSONString(questionOptions));
		questionDao.update(question);

		// UserCreateQuestion
		UserCreateQuestion userCreateQuestion = new UserCreateQuestion();
		userCreateQuestion.setUserId(question.getUserId());
		userCreateQuestion.setDate(question.getDate());
		userCreateQuestion.setQuestionId(id);
		userCreateQuestionService.save(userCreateQuestion);

		// UserStatistics
		UserStatistics userStatistics = userStatisticsService.get(question
				.getUserId());
		userStatistics.setCreateCount(userStatistics.getCreateCount() + 1);
		if (StringUtils.isNotBlank(question.getAnswer())) {
			userStatistics.setCreateObjectiveCount(userStatistics
					.getCreateObjectiveCount() + 1);
		}
		userStatisticsService.update(userStatistics);

		String categories = question.getCategories();
		if (StringUtils.isNotBlank(categories)) {
			List<CategoryVO> categoryList = JSON.parseArray(categories,
					CategoryVO.class);
			for (CategoryVO c : categoryList) {
				// CategoryQuestion
				CategoryQuestion categoryQuestion = new CategoryQuestion();
				categoryQuestion.setCategoryId(c.getId());
				categoryQuestion.setQuestionId(id);
				questionCategoryService.save(categoryQuestion);

				// Category
				Category category = categoryService.get(c.getId());
				category.setQuestionCount(category.getQuestionCount() + 1);
				categoryService.update(category);
			}
		}

		// QuestionStatistics
		QuestionStatistics questionStatistics = new QuestionStatistics();
		questionStatistics.setQuestionId(id);
		questionStatistics.setIsObjective(StringUtils.isBlank(question
				.getAnswer()) ? false : true);

		if (question.getIsPublished()) {
			if (!question.getIsPublic()) {
				if (userIds != null) {
					for (String uId : userIds) {
						// QuestionShare
						QuestionShare questionShare = new QuestionShare();
						questionShare.setQuestionId(id);
						questionShare.setUserId(question.getUserId());
						questionShare.set_userId(uId);
						questionShare.setDate(question.getDate());
						questionShareService.save(questionShare);

						// send message to friends
						Message message = new Message();
						message.setType(MessageType.QUESTION_SHARE);
						message.setSenderId(question.getUserId());
						message.setSenderNickname(nickname);
						message.setSenderAvatar(avatar);
						message.setReceiverId(uId);
						message.setRelatedObject(id);
						message.setDate(question.getDate());
						messageService.save(message);
					}
					questionStatistics.setShareCount(1);
					questionStatistics.setShareUserCount(userIds.size());
				}
			}
		}

		questionStatisticsService.save(questionStatistics);

		return id;
	}

	public void share(String userId, String nickname,String avatar,
			String questionId, List<String> userIds) {
		
		for (String uId : userIds) {
			Date date = new Date();
			// QuestionShare
			QuestionShare questionShare = new QuestionShare();
			questionShare.setQuestionId(questionId);
			questionShare.setUserId(userId);
			questionShare.set_userId(uId);
			questionShare.setDate(date);
			questionShareService.save(questionShare);

			// send message to friends
			Message message = new Message();
			message.setType(MessageType.QUESTION_SHARE);
			message.setSenderId(userId);
			message.setSenderNickname(nickname);
			message.setSenderAvatar(avatar);
			message.setReceiverId(uId);
			message.setRelatedObject(questionId);
			message.setDate(date);
			messageService.save(message);
		}

		// update QuestionStatistics
		QuestionStatistics questionStatistics = questionStatisticsService
				.get(questionId);
		questionStatistics.setShareCount(questionStatistics.getShareCount()
				+ 1);
		questionStatistics.setShareUserCount(questionStatistics.getShareUserCount()
				+ userIds.size());
		questionStatisticsService.update(questionStatistics);

	}

	public void publish(String nickname,String avatar, 
			String questionId, boolean isPublic, List<String> userIds) {
		Question question = questionDao.get(questionId);
		question.setIsPublished(true);
		question.setIsPublic(isPublic);
		questionDao.update(question);
		
		if (!isPublic) {
			if (userIds != null) {
				Date date = new Date();
				for (String uId : userIds) {
					// QuestionShare
					QuestionShare questionShare = new QuestionShare();
					questionShare.setQuestionId(questionId);
					questionShare.setUserId(question.getUserId());
					questionShare.set_userId(uId);
					questionShare.setDate(date);
					questionShareService.save(questionShare);

					// send message to friends
					Message message = new Message();
					message.setType(MessageType.QUESTION_SHARE);
					message.setSenderId(question.getUserId());
					message.setSenderNickname(nickname);
					message.setSenderAvatar(avatar);
					message.setReceiverId(uId);
					message.setRelatedObject(questionId);
					message.setDate(date);
					messageService.save(message);
				}
				QuestionStatistics questionStatistics = questionStatisticsService.get(questionId);
				questionStatistics.setShareCount(1);
				questionStatistics.setShareUserCount(userIds.size());
				questionStatisticsService.update(questionStatistics);
			}
		}
		
	}
}
