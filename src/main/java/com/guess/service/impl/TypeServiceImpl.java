package com.guess.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guess.dao.TypeDao;
import com.guess.model.Type;
import com.guess.service.TypeService;

@Component
public class TypeServiceImpl extends BaseServiceImpl<Type, String> implements TypeService{
	private TypeDao typeDao;

	public TypeDao getTypeDao() {
		return typeDao;
	}
	@Autowired
	public void setTypeDao(TypeDao typeDao) {
		super.setBaseDao(typeDao);
		this.typeDao = typeDao;
	}
	
	public Type getByName(String name) {
		return typeDao.getByName(name);
	}
}
