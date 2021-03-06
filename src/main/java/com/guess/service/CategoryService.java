package com.guess.service;

import com.guess.model.Category;

public interface CategoryService extends BaseService<Category, String>{
	Category getByName(String name);
}
