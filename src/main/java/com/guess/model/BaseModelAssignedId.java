package com.guess.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class BaseModelAssignedId {
	
	protected String id;
	
	@Id
	@GenericGenerator(name = "assigned", strategy = "assigned")
	@GeneratedValue(generator = "assigned")
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
		if(object instanceof BaseModelAssignedId){
			BaseModelAssignedId baseModel = (BaseModelAssignedId) object;
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
