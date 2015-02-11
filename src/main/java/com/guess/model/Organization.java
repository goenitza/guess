package com.guess.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Organization extends User{
	
	private boolean isVerified = false;
	private String realname;
	
	@Column
	public boolean getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}
	@Column
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
}
