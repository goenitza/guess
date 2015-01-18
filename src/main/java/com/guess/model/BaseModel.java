package com.guess.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class BaseModel {
	
	protected String id;
	
	@Id
	@Column(length = 32, nullable = false)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object object){
		if(object == null){
			return false;
		}
		if(object instanceof BaseModel){
			BaseModel baseModel = (BaseModel) object;
			if(this.getId() == null || baseModel.getId() == null){
				return false;
			}
			return (this.getId().equals(baseModel.getId()));
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return id == null ? System.identityHashCode(this) : (this.getClass().getName() + this.getId()).hashCode();
	}
}
