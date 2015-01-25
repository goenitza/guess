package com.guess.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Admin extends BaseModel{
	private String username;
	private String password;
	
	@Column(nullable = false)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Column(nullable = false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
