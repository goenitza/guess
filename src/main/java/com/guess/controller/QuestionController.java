package com.guess.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.guess.model.Question;
import com.guess.model.QuestionType;
import com.guess.service.ImageService;
import com.guess.service.QuestionCategoryService;
import com.guess.service.QuestionService;
import com.guess.service.UserCreateQuestionService;
import com.guess.vo.QuestionOption;
import com.guess.vo.QuestionOptions;
import com.guess.vo.Result;
import com.guess.vo.UserInSession;

@Controller
@RequestMapping(value = "/user/question", produces = "application/json;charset=utf-8")
public class QuestionController {
	
	private static Logger logger = LogManager.getLogger(QuestionController.class);
	
	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuestionCategoryService questionCategoryService;
	@Autowired
	private UserCreateQuestionService userCreateQuestionService;
	@Autowired
	private ImageService imageService;
	
	//options-json
	//categories-json
	//friends-json:["friend1Id","friend2Id",...]
	@RequestMapping("/add")
	@ResponseBody
	public String add_(@RequestParam("type") QuestionType type,
			@RequestParam("content") String content, 
			@RequestParam(value = "contentImage", required = false) MultipartFile contentImage, 
			@RequestParam(value = "linkName", required = false) String linkName,
			@RequestParam(value = "linkUrl", required = false) String linkUrl, 
			@RequestParam(value = "options", required = false) String options, 
			@RequestParam(value = "optionsImages", required = false) MultipartFile[] optionsImages, 
			@RequestParam(value = "answer", required = false) String answer, 
			@RequestParam(value = "categories", required = false) String categories, 
			@RequestParam("isPublic") boolean isPublic, 
			@RequestParam("isPublished") boolean isPublished, 
			@RequestParam(value = "friends", required = false) String friends, 
			HttpServletRequest request
			) throws IOException{
		Result result = new Result();
		
		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
		Question question = new Question();
		question.setType(type);
		question.setContent(content);
		String contextPath = request.getSession().getServletContext().getRealPath("/");
		if(contentImage != null){
			String fileName = userInSession.username + "_content"; 
			String contentUrl = imageService.saveQuestionContentImage(contextPath, contentImage, fileName);
			question.setContentUrl(contentUrl);
		}
		if(StringUtils.isNotBlank(linkName)){
			question.setLinkName(linkName);
		}
		if(StringUtils.isNotBlank(linkUrl)){
			question.setLinkUrl(linkUrl);
		}
		if(StringUtils.isNotBlank(options)){
			QuestionOptions questionOptions = JSON.parseObject(options, QuestionOptions.class);
			List<QuestionOption> optionList = questionOptions.getOptions();
			for(int i = 0; i < optionList.size(); i++){
				QuestionOption option = optionList.get(i);
				if(option.getContainsImage()){
					String fileName = userInSession.username + "_option_" + (i + 1);
					String imageUrl = imageService.saveQuestionOptionImage(contextPath, optionsImages[i], fileName);
					option.setImageUrl(imageUrl);
				}
			}
			question.setOptions(JSON.toJSONString(questionOptions));
		}
		if(StringUtils.isNotBlank(answer)){
			question.setAnswer(answer);
		}
		if(StringUtils.isNotBlank(categories)){
			question.setCategories(categories);
		}
		question.setIsPublic(isPublic);
		question.setIsPublished(isPublished);
		question.setUsername(userInSession.username);
		question.setDate(new Date());
		
		String id = questionService.save(question, friends);
		result.set("id", id);
		logger.info("add question: " + id);
		return result.toJson();
	}
	
	//friends-json
	@RequestMapping("/share")
	@ResponseBody
	public String share(@RequestParam("id") String id, @RequestParam("friends") String friends, 
			HttpServletRequest request){
		Result result = new Result();
		questionService.share(id, friends);
		logger.info("share question: " + id);
		return result.toJson();
	}
}
