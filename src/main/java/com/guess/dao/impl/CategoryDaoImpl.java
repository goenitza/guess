package com.guess.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.guess.dao.CategoryDao;
import com.guess.model.Category;

@Component
public class CategoryDaoImpl extends BaseDaoImpl<Category, String> implements CategoryDao{

	public Category getByName(String name) {
		String query = "from Category g where g.name = ?";
		List<Category> categories = hibernateTemplate.find(query, name);
		if(categories.size() == 0){
			return null;
		}else {
			return categories.get(0);
		}
	}
	
	@Override
	public Category get(String id){
		Category category = super.get(id);
		if(category == null || category.getIsDeleted())
			return null;
		else
			return category;
	}
	
	@Override
	public List<Category> getAllList(){
		String query = "from Category g where g.isDeleted = false";
		List<Category> categories = hibernateTemplate.find(query);
		return categories;
	}
	
	@Override
	public Category load(String id){
		Category category = super.load(id);
		if(category == null || category.getIsDeleted())
			return null;
		else
			return category;
	}
	
	@Override
	public void delete(String id){
		Category category = get(id);
		if(category != null){
			category.setIsDeleted(true);
			update(category);
		}
	}
	
	@Override
	public void delete(String [] ids){
		for(String id : ids){
			delete(id);
		}
	}
}
