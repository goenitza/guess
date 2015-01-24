package com.guess.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guess.model.Category;
import com.guess.service.CategoryService;

@Controller
@Scope("prototype")
@RequestMapping("/category")
public class CategoryController extends BaseController{
	@Autowired
	private CategoryService categoryService;
	private static Logger logger = LogManager.getLogger(CategoryController.class);
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@RequestParam()String name, HttpServletResponse response){
		if(StringUtils.isBlank(name)){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("param name is null");
			logger.error("param name is null");
		}else if(!categoryService.isUnique(name)){
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			result.setError("param name " + name + " is conflict");
			logger.error("param name " + name + " is conflict");
		}else {
			Category category = new Category();
			category.setName(name);
			String id = categoryService.save(category);
			result.set("id", id);
			response.setStatus(HttpServletResponse.SC_OK);
			logger.info("add category " + name);
		}
		return result.toJson();
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam("ids") String[] ids, HttpServletResponse response){
		if(ids == null || ids.length <= 0){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("id is null");
			logger.error("id is null");
		}else {
			categoryService.delete(ids);
			response.setStatus(HttpServletResponse.SC_OK);
			logger.info("delete category " + ids);
		}
		return result.toJson();
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@RequestParam("id") String id, @RequestParam("name") String name,
			HttpServletResponse response){
		if(StringUtils.isBlank(id) || StringUtils.isBlank(name)){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("id or name is null");
			logger.error("id or name is null");
		}else {
			Category category = categoryService.get(id);
			if(category == null){
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				result.setError("can not get category with id " + id);
				logger.error("can not get category with id " + id);
			}else {
				category.setName(name);
				categoryService.update(category);
				response.setStatus(HttpServletResponse.SC_OK);
				logger.info("update category " + id + " to new name " + name);
			}
		}
		return result.toJson();
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public String get(@RequestParam("id") String id, HttpServletResponse response){
		if(StringUtils.isBlank(id)){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("id is null");
			logger.error("id is null");
		}else {
			Category category = categoryService.get(id);
			if(category == null){
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				result.setError("can not get category with id " + id);
				logger.error("can not get category with id " + id);
			}else {
				result.set("category", category);
				response.setStatus(HttpServletResponse.SC_OK);
				logger.info("get category " + id);
			}
		}
		return result.toJson();
	}
	
	@RequestMapping(value = "/get_all", method = RequestMethod.POST)
	@ResponseBody
	public String getAll(HttpServletResponse response){
		List<Category> categories = categoryService.getAllList();
		result.set("categorys", categories);
		logger.info("get all categorys");
		response.setStatus(HttpServletResponse.SC_OK);
		return result.toJson();
	}
}
