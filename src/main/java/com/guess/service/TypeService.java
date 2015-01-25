package com.guess.service;

import com.guess.model.Type;

public interface TypeService extends BaseService<Type, String>{
	Type getByName(String name);
}
