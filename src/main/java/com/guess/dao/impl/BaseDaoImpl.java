package com.guess.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.guess.dao.BaseDao;

public class BaseDaoImpl<T , PK extends Serializable> implements BaseDao<T, PK>{
	
	private Class<T> entityClass;
	
	@Autowired
	SessionFactory sessionFactory;
	
	public Session currentSession(){
		return sessionFactory.getCurrentSession();
	}
	
	
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
		currentSession().delete(entity);
	}

	public void delete(PK ID) {
		Assert.notNull(ID, "ID is required");
		T entity = (T) currentSession().load(entityClass, ID);
		currentSession().delete(entity);
	}

	public void delete(PK[] IDs) {
		Assert.notEmpty(IDs, "IDs must not be empty");
		for(PK ID : IDs){
			T entity = (T) currentSession().load(entityClass, ID);
			currentSession().delete(entity);
		}
	}

	public T get(PK ID) {
		Assert.notNull(ID, "ID is required");
		return (T) currentSession().get(entityClass, ID);
	}

	@SuppressWarnings("unchecked")
	public List<T> getAllList() {
		String query = "from " + entityClass.getName();
		return currentSession().createQuery(query).list();
	}

	public T load(PK ID) {
		Assert.notNull(ID, "ID is required");
		return (T) currentSession().load(entityClass, ID);
	}

	@SuppressWarnings("unchecked")
	public PK save(T entity) {
		Assert.notNull(entity, "entity is required");
		return (PK) currentSession().save(entity);
	}

	public void update(T entity) {
		Assert.notNull(entity, "entity is required");
		currentSession().update(entity);
	}
}
