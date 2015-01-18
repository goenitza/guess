package com.guess.service.impl;

import java.io.Serializable;
import java.util.List;

import com.guess.dao.BaseDao;
import com.guess.service.BaseService;

public class BaseServiceImpl <T,PK extends Serializable> implements BaseService<T, PK>{
	private BaseDao<T, PK> baseDao;
	
	public BaseDao<T, PK> getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}

	public void delete(T entity) {
		baseDao.delete(entity);
	}

	public void delete(PK id) {
		baseDao.delete(id);
	}

	public void delete(PK[] ids) {
		baseDao.delete(ids);
	}

	public T get(PK id) {
		return baseDao.get(id);
	}

	public List<T> getAllList() {
		return baseDao.getAllList();
	}

	public long getTotalCount() {
		return baseDao.getTotalCount();
	}

	public T load(PK id) {
		return baseDao.load(id);
	}

	public PK save(T entity) {
		return (PK) baseDao.save(entity);
	}

	public void update(T entity) {
		baseDao.update(entity);
	}
	public void clear() {
		baseDao.clear();
	}
	public void evict(Object object) {
		baseDao.evict(object);
	}
	public void flush() {
		baseDao.flush();
	}
}
