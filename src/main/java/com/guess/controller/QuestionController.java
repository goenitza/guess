package com.guess.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.guess.enums.QuestionType;
import com.guess.service.ImageService;
import com.guess.service.QuestionCategoryService;
import com.guess.service.QuestionService;
import com.guess.service.UserCreateQuestionService;
import com.guess.util.ImageUtil;
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
	
	/*options:
	{
	    "num": "2",
	    "options": [
	        {
	            "index": "1",
	            "content": "星期一",
	            "containsImage": "true"
	        },
	        {
	            "index": "2",
	            "content": "星期二",
	            "containsImage": "true"
	        }
	    ]
	}
	categories:
	[
	    {
	        "id": "402881e54b63a022014b63aa6d34000e",
	        "name": "时事"
	    },
	    {
	        "id": "402881e54b63a022014b63aa7d6d000f",
	        "name": "汽车"
	    }
	]
	 * 
	 */
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
			@RequestParam("isPublished") boolean isPublished, 
			@RequestParam(value = "isPublic", required = false) boolean isPublic, 
			@RequestParam(value = "userIds", required = false) List<String> userIds, 
			HttpServletRequest request, HttpServletResponse response
			) throws IOException{
		Result result = new Result();
		//to do
		
		
		
		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
		Question question = new Question();
		question.setType(type);
		question.setContent(content);
		String contextPath = request.getSession().getServletContext().getRealPath("/");
		if(contentImage != null){
			if(!ImageUtil.check(contentImage)){
				response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				result.setError("图片格式须为jpg、jpeg、png或者gif并且大小不能超过1M");
				logger.info("image format is not supported or size > 1M");
				return result.toJson();
			}
//			String fileName = question.getId() + "_content"; 
//			String contentUrl = imageService.saveQuestionContentImage(contextPath, contentImage, fileName);
//			question.setContentUrl(contentUrl);
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
					if(!ImageUtil.check(optionsImages[i])){
						response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
						result.setError("图片格式须为jpg、jpeg、png或者gif并且大小不能超过1M");
						logger.info("image format is not supported or size > 1M");
						return result.toJson();
					}
//					String fileName = question.getId() + "_option_" + (i + 1);
//					String imageUrl = imageService.saveQuestionOptionImage(contextPath, optionsImages[i], fileName);
//					option.setImageUrl(imageUrl);
				}
			}
//			question.setOptions(JSON.toJSONString(questionOptions));
		}
		if(StringUtils.isNotBlank(answer)){
			question.setAnswer(answer);
		}
		if(StringUtils.isNotBlank(categories)){
			question.setCategories(categories);
		}
		question.setIsPublic(isPublic);
		question.setIsPublished(isPublished);
		question.setUserId(userInSession.id);
		question.setDate(new Date());
		
		String id = questionService.save( userInSession.nickname, userInSession.avatar, 
				question, contentImage, options, optionsImages, contextPath, userIds);
		result.set("id", id);
		logger.info("add question: " + id);
		return result.toJson();
	}
	
	@RequestMapping("/publish")
	@ResponseBody
	public String publish(@RequestParam("questionId") String questionId, 
			@RequestParam("isPublic") boolean isPublic, 
			@RequestParam(value = "userIds", required = false) List<String> userIds, 
			HttpServletRequest request, HttpServletResponse response){
		Result result = new Result();
		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
		questionService.publish( userInSession.nickname, userInSession.avatar, 
				questionId, isPublic, userIds);
		logger.info("publish question: " + questionId);
		return result.toJson();
	}
	
	@RequestMapping("/share")
	@ResponseBody
	public String share(@RequestParam("questionId") String questionId, @RequestParam("userIds") List<String> userIds, 
			HttpServletRequest request){
		Result result = new Result();
		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
		questionService.share(userInSession.id, userInSession.nickname, userInSession.avatar,
				questionId, userIds);
		logger.info("share question: " + questionId);
		return result.toJson();
	}
}
