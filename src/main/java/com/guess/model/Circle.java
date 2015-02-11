package com.guess.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.guess.enums.CircleType;

@Entity
public class Circle extends BaseModel{
	private String name;
	private CircleType type;
	
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(nullable = false)
	public CircleType getType() {
		return type;
	}

	public void setType(CircleType type) {
		this.type = type;
	}
}
