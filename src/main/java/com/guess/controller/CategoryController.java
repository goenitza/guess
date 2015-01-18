package com.guess.controller;

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
		}else {
			Category category = new Category();
			category.setName(name);
			categoryService.save(category);
			response.setStatus(HttpServletResponse.SC_OK);
			logger.info("add category " + name);
		}
		return result.toJson();
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
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
	
	
}
