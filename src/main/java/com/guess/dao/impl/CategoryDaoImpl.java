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
}
