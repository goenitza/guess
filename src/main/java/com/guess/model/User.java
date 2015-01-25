package com.guess.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class User extends BaseModel{
	
	private String username;
	private String password;
	private String sickname;
	private String email;
	private String avatar = "/default_avatar.jpg";
	private boolean isVerified = false;
	private String realname;
	
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
	@Column
	public String getSickname() {
		return sickname;
	}
	public void setSickname(String sickname) {
		this.sickname = sickname;
	}
	@Column(nullable = false)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(nullable = false)
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
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
