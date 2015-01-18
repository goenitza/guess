package com.guess.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao <T ,PK extends Serializable>{
	
	T get(PK ID);
	
	T load(PK ID);
	
	List<T> getAllList();
	
	long getTotalCount();
	
	PK save(T entity);
	
	void update(T entity);
	
	void delete(T entity);
	
	void delete(PK ID);
	
	void delete(PK[] IDs);
	
	public void flush();
	
	public void evict(Object object);
	
	public void clear();
}
