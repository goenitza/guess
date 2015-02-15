package com.guess.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Circle extends BaseModel{
	private String name;
	
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
