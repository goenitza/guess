package com.guess.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.Assert;

import com.guess.dao.BaseDao;


public class BaseDaoImpl<T , PK extends Serializable> implements BaseDao<T, PK>{
	
	private Class<T> entityClass;
	
	@Autowired
	HibernateTemplate hibernateTemplate;
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
        Class c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
        }
	}

	public void delete(T entity) {
		Assert.notNull(entity, "entity is required");
		hibernateTemplate.delete(entity);
	}

	public void delete(PK ID) {
		Assert.notNull(ID, "ID is required");
		T entity = hibernateTemplate.load(entityClass, ID);
		hibernateTemplate.delete(entity);
	}

	public void delete(PK[] IDs) {
		Assert.notEmpty(IDs, "IDs must not be empty");
		for(PK ID : IDs){
			T entity = hibernateTemplate.load(entityClass, ID);
			hibernateTemplate.delete(entity);
		}
	}

	public T get(PK ID) {
		Assert.notNull(ID, "ID is required");
		return hibernateTemplate.get(entityClass, ID);
	}

	@SuppressWarnings("unchecked")
	public List<T> getAllList() {
		String query = "from " + entityClass.getName();
		return hibernateTemplate.find(query);
	}

	public long getTotalCount() {
		String query = "select count(*) from " + entityClass.getName();
		return ((Long)hibernateTemplate.iterate(query).next()).longValue();
	}

	public T load(PK ID) {
		Assert.notNull(ID, "ID is required");
		return hibernateTemplate.load(entityClass, ID);
	}

	@SuppressWarnings("unchecked")
	public PK save(T entity) {
		Assert.notNull(entity, "entity is required");
		return (PK) hibernateTemplate.save(entity);
	}

	public void update(T entity) {
		Assert.notNull(entity, "entity is required");
		hibernateTemplate.update(entity);
	}

	public void clear() {
		hibernateTemplate.clear();
	}

	public void evict(Object object) {
		Assert.notNull(object, "object is required");
		hibernateTemplate.evict(object);
	}

	public void flush() {
		hibernateTemplate.flush();
	}
}
