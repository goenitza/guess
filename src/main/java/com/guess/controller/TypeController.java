package com.guess.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guess.model.Type;
import com.guess.service.TypeService;

@Controller
@RequestMapping("/admin/type")
public class TypeController {

	private static Logger logger = LogManager.getLogger(TypeController.class);
	
	@Autowired
	private TypeService typeService;
	
	@RequestMapping("/add")
	@ResponseBody
	public String add(@RequestParam("name") String name, 
			@RequestParam("isContainsQuestion") boolean isContainsQuestion, 
			@RequestParam("isContainsOption") boolean isContainsOption, 
			@RequestParam("isContainsAnswer") boolean isContainsAnswer,
			@RequestParam("isOptionSingle") boolean isOptionSingle,
			@RequestParam("isOptionAll") boolean isOptionAll,
			HttpServletResponse response){
		Result result = new Result();
		Type type = typeService.getByName(name);
		if(type != null && !type.getIsDeleted()){
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			result.setError("题型已存在");
			logger.info(name + " exists");
		}else {
			type = new Type();
			type.setName(name);
			type.setIsContainsAnswer(isContainsAnswer);
			type.setIsContainsOption(isContainsOption);
			type.setIsContainsQuestion(isContainsQuestion);
			type.setIsOptionSingle(isOptionSingle);
			type.setIsOptionAll(isOptionAll);
			String id = typeService.save(type);
			result.set("id", id);
			logger.info("add type " + name);
		}
		return result.toJson();
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(@RequestParam("ids") String [] ids, HttpServletResponse response){
		Result result = new Result();
		typeService.delete(ids);
		StringBuilder string = new StringBuilder("delete type ");
		for(String s : ids){
			string.append(s); 
		}
		logger.info(string.toString());
		return result.toJson();
	}
	/*
	//Not allowed to update
	@RequestMapping("/update")
	@ResponseBody
	public String update(@RequestParam("id") String id, 
			@RequestParam("name") String name, 
			@RequestParam("isContainsQuestion") boolean isContainsQuestion, 
			@RequestParam("isContainsOption") boolean isContainsOption, 
			@RequestParam("isContainsAnswer") boolean isContainsAnswer,
			HttpServletResponse response){
		Result result = new Result();
		if(StringUtils.isBlank(id)){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("id不允许为空");
			logger.error("id is null");
		}else {
			Type type = typeService.get(id);
			if(type == null){
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				result.setError("找不到具有此id的题型");
				logger.info("can not get type with id " + id);
			}else {
				type.setName(name);
				type.setIsContainsAnswer(isContainsAnswer);
				type.setIsContainsOption(isContainsOption);
				type.setIsContainsQuestion(isContainsQuestion);
				typeService.update(type);
				logger.info("update type " + name);
			}
		}
		
		return result.toJson();
	}
	*/
	@RequestMapping("/get")
	@ResponseBody
	public String get(@RequestParam("id") String id, HttpServletResponse response){
		Result result = new Result();
		if(StringUtils.isBlank(id)){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("id不允许为空");
			logger.error("id is null");
		}else{
			Type type = typeService.get(id);
			if(type == null){
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				result.setError("找不到具有此id的题型： " + id);
				logger.error("can not get type with id " + id);
			}else {
				result.set("type", type);
				logger.info("get type " + id);
			}
		}
		return result.toJson();
	}
	
	@RequestMapping("/get_all")
	@ResponseBody
	public String getAll(HttpServletResponse response){
		Result result = new Result();
		List<Type> types = typeService.getAllList();
		result.set("types", types);
		logger.info("get all types");
		return result.toJson();
	}
}
