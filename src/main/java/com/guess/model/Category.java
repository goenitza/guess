package com.guess.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Category extends BaseModel{
	String name;
	boolean isDeleted = false;

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(nullable = false)
	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
