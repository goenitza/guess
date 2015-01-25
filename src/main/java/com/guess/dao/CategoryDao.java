package com.guess.dao;

import com.guess.dao.BaseDao;
import com.guess.model.Category;

public interface CategoryDao extends BaseDao<Category, String>{
	Category getByName(String name);
}
