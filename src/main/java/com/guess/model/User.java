package com.guess.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "user")
public class User extends BaseModel{
	
	private String name;
	
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
