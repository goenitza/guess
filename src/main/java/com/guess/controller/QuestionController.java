package com.guess.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.guess.service.QuestionCategoryService;
import com.guess.service.QuestionService;
import com.guess.service.UserCreateQuestionService;
import com.guess.vo.Result;
import com.guess.vo.UserInSession;

@Controller
@RequestMapping(value = "/question", produces = "application/json;charset=utf-8")
public class QuestionController {
	
	private static Logger logger = LogManager.getLogger(QuestionController.class);
	
	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuestionCategoryService questionCategoryService;
	@Autowired
	private UserCreateQuestionService userCreateQuestionService;
	
	
	public String add_(String content, MultipartFile contentImage, String option, MultipartFile[] optionImages){
		Result result = new Result();
		
		return result.toJson();
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public String add(@RequestParam("question") String question, 
			@RequestParam(value = "options", required = false) String options, 
			@RequestParam("answer") String answer, 
			@RequestParam("typeId") String typeId, 
			@RequestParam("categories") String categories, 
			HttpServletRequest request, 
			HttpServletResponse response){
		Result result = new Result();
		
		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
		String userId = null;
		if(userInSession == null || (userId = userInSession.id) == null){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("请先登录 ");
			logger.info("the user has not logged in");
			return result.toJson();
		}
		
//		Type type = typeService.get(typeId);
//		if(type == null){
//			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//			result.setError("找不到具有此id的题型 " + typeId);
//			logger.info("cannot find type with the id " + typeId);
//			return result.toJson();
//		}
		
//		if(type.getIsContainsOption() && options == null){
//			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//			result.setError("参数option不允许为空 ");
//			logger.info("option is null");
//			return result.toJson();
//		}
		
		//check if the param categories can be parsed to map
		try {
			Map<String, String> map = (Map<String, String>) JSON.parse(categories);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("参数categories不能被正确解析 ");
			logger.info("param categores cannot be parsed to map");
			return result.toJson();
		}
		
		Date date = new Date();
		Question ques = new Question();
//		ques.setQuestion(question);
		if(options != null){
			ques.setOptions(options);
		}
		ques.setAnswer(answer);
		ques.setDate(date);
		ques.setUserId(userId);
		ques.setTypeId(typeId);
		ques.setCategories(categories);
		
		String id = questionService.save(ques);
		result.set("id", id);
		logger.info("add question " + id);
		
		return result.toJson();
	}
}
