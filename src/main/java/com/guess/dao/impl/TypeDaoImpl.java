package com.guess.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.guess.dao.TypeDao;
import com.guess.model.Type;

@Component
public class TypeDaoImpl extends BaseDaoImpl<Type, String> implements TypeDao {

	@Override
	public Type get(String id) {
		Type type = super.get(id);
		if (type == null || type.getIsDeleted())
			return null;
		else
			return type;
	}

	@Override
	public List<Type> getAllList() {
		String query = "from Type t where t.isDeleted = false";
		List<Type> types = hibernateTemplate.find(query);
		return types;
	}

	@Override
	public Type load(String id) {
		Type type = super.load(id);
		if (type == null || type.getIsDeleted())
			return null;
		else
			return type;
	}

	@Override
	public void delete(String id) {
		Type type = get(id);
		if (type != null) {
			type.setIsDeleted(true);
			update(type);
		}
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			delete(id);
		}
	}

	public Type getByName(String name) {
		String query = "from Type t where t.name = ?";
		List<Type> types = hibernateTemplate.find(query, name);
		if (types.size() == 0) {
			return null;
		} else {
			return types.get(0);
		}
	}
}
