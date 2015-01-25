package com.guess.dao;

import com.guess.model.Type;

public interface TypeDao extends BaseDao<Type, String>{
	Type getByName(String name);
}
