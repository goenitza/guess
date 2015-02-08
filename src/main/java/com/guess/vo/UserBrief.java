package com.guess.vo;

import com.guess.enums.UserRole;


public class UserBrief {
	String username;
	String nickName;
	String avatar;
	UserRole role;
	boolean isVerified;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	public boolean getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
