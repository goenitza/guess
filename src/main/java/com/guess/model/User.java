package com.guess.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import com.guess.enums.UserRole;

@MappedSuperclass
public class User extends BaseModel{
	
	private String username;//email
	private String password;
	private UserRole role;
	private String nickname;
	private String avatar;
	private boolean isFrozen = false;
	private String interestedCategories;
	private Set<Circle> circles;
	
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
	@Column(nullable = false)
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	@Column
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	@Column()
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	@Column
	public boolean getIsFrozen() {
		return isFrozen;
	}
	public void setIsFrozen(boolean isFrozen) {
		this.isFrozen = isFrozen;
	}
	@Column(length = 3000)
	public String getInterestedCategories() {
		return interestedCategories;
	}
	public void setInterestedCategories(String interestedCategories) {
		this.interestedCategories = interestedCategories;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<Circle> getCircles() {
		return circles;
	}
	public void setCircles(Set<Circle> circles) {
		this.circles = circles;
	}
}
