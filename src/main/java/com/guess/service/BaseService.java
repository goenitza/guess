package com.guess.service;

import java.io.Serializable;
import java.util.List;

public interface BaseService <T, PK extends Serializable>{
	
	T get(PK id);
	
	T load(PK id);
	
	List<T> getAllList();
	
	long getTotalCount();
	
	PK save(T entity);
	
	void update(T entity);
	
	void delete(T entity);
	
	void delete(PK id);
	
	void delete(PK[] ids);

	public void flush();

	public void evict(Object object);
	
	public void clear();
}
