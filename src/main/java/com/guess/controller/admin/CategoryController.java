package com.guess.controller.admin;

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

import com.guess.model.Category;
import com.guess.service.CategoryService;
import com.guess.vo.Result;

@Controller
@RequestMapping(value = "/admin/category", produces = "application/json;charset=utf-8")
public class CategoryController{
	
	private static Logger logger = LogManager.getLogger(CategoryController.class);
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/add")
	@ResponseBody
	public String add(@RequestParam()String name, HttpServletResponse response){
		Result result = new Result();
		Category category = categoryService.getByName(name);
		if(category != null){
			if(!category.getIsDeleted()){
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				result.setError("问题分类已存在");
				logger.error("the category already exists: " + name);
			}else {
				category.setIsDeleted(false);
				categoryService.update(category);
				result.set("id", category.getId());
				logger.info("add category: " + name);
			}
		}else {
			Category category2 = new Category();
			category2.setName(name);
			String id = categoryService.save(category2);
			result.set("id", id);
			logger.info("add category: " + name);
		}
		return result.toJson();
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(@RequestParam("ids") String[] ids, HttpServletResponse response){
		Result result = new Result();
		categoryService.delete(ids);
		StringBuilder string = new StringBuilder("delete category: ");
		for(String s : ids){
			string.append(s + " "); 
		}
		logger.info(string.toString());
		return result.toJson();
	}
	/*
	//Not allowed to update
	@RequestMapping("/update")
	@ResponseBody
	public String update(@RequestParam("id") String id, @RequestParam("name") String name,
			HttpServletResponse response){
		Result result = new Result();
		if(StringUtils.isBlank(id)){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setError("id不允许为空");
			logger.error("id is null");
		}else {
			Category category = categoryService.get(id);
			if(category == null){
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				result.setError("找不到具有此id的问题分类： " + id);
				logger.error("can not get category with id " + id);
			}else {
				category.setName(name);
				categoryService.update(category);
				logger.info("update category " + id + " to new name " + name);
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
		}else {
			Category category = categoryService.get(id);
			if(category == null){
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				result.setError("找不到具有此id的问题分类： " + id);
				logger.error("can not get category: " + id);
			}else {
				result.set("category", category);
				logger.info("get category: " + category.getName());
			}
		}
		return result.toJson();
	}
	
	@RequestMapping("/get_all")
	@ResponseBody
	public String getAll(HttpServletResponse response){
		Result result = new Result();
		List<Category> categories = categoryService.getAllList();
		result.set("categorys", categories);
		logger.info("get all categories");
		return result.toJson();
	}
}
