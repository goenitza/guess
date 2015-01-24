package com.guess.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guess.dao.CategoryDao;
import com.guess.model.Category;
import com.guess.service.CategoryService;

@Component
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
	
	@Override
	public Category get(String id){
		Category category = categoryDao.get(id);
		if(category == null || category.getIsDeleted())
			return null;
		else
			return category;
	}
	
	@Override
	public List<Category> getAllList(){
		List<Category> categories = categoryDao.getAllList();
		Category category = null;
		for(Iterator<Category> iterator = categories.iterator(); iterator.hasNext();){
			category = iterator.next();
			if(category.getIsDeleted())
				iterator.remove();
		}
		return categories;
	}
	
	@Override
	public Category load(String id){
		Category category = categoryDao.load(id);
		if(category == null || category.getIsDeleted())
			return null;
		else
			return category;
	}
	
	@Override
	public void delete(String id){
		Category category = categoryDao.get(id);
		if(category != null && category.getIsDeleted() == false){
			category.setIsDeleted(true);
			categoryDao.update(category);
		}
	}
	
	@Override
	public void delete(String [] ids){
		for(String id : ids){
			delete(id);
		}
	}
	
	public boolean isUnique(String name) {
		boolean isUnique = true;
		List<Category> categories = categoryDao.getAllList();
		if(categories != null){
			for(Category category : categories){
				if(category.getName().equals(name)){
					isUnique = false;
					break;
				}
			}
		}
		return isUnique;
	}
}
