package com.guess.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.guess.dao.CategoryDao;
import com.guess.model.Category;
import com.guess.service.CategoryService;

@Component
@Transactional
public class CategoryServiceImpl extends BaseServiceImpl<Category, String> implements CategoryService{
	
	private CategoryDao categoryDao;

	public CategoryDao getCategoryDao() {
		return categoryDao;
	}
	
	@Autowired
	public void setCategoryDao(CategoryDao categoryDao) {
		super.setBaseDao(categoryDao);
		this.categoryDao = categoryDao;
	}
	
	public Category getByName(String name) {
		return categoryDao.getByName(name);
	}
}
