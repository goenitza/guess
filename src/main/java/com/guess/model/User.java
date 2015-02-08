package com.guess.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.guess.enums.UserRole;

@Entity
public class User{
	
	private String username;
	private String password;
	private UserRole role;
	private String nickname;
	private String email;
	private String avatar;
	private boolean isVerified = false;
	private boolean isFrozen = false;
	private String realname;
	private String interestedCategories;
	private String friends;// friends are followers for org
	private String orgs;// orgs those the users pay attention to 
	private String circles;
	
	@Id
	@GenericGenerator(name = "assigned", strategy = "assigned")
	@GeneratedValue(generator = "assigned")
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
	public boolean getIsFrozen() {
		return isFrozen;
	}
	public void setIsFrozen(boolean isFrozen) {
		this.isFrozen = isFrozen;
	}
	@Column
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	@Column(length = 3000)
	public String getInterestedCategories() {
		return interestedCategories;
	}
	public void setInterestedCategories(String interestedCategories) {
		this.interestedCategories = interestedCategories;
	}
	@Column
	public String getFriends() {
		return friends;
	}
	public void setFriends(String friends) {
		this.friends = friends;
	}
	@Column
	public String getOrgs() {
		return orgs;
	}
	public void setOrgs(String orgs) {
		this.orgs = orgs;
	}
	@Column
	public String getCircles() {
		return circles;
	}
	public void setCircles(String circles) {
		this.circles = circles;
	}

	@Override
	public boolean equals(Object object){
		if(object == null){
			return false;
		}
		if(object instanceof User){
			User user = (User) object;
			if(this.getUsername() == null || user.getUsername() == null){
				return false;
			}
			return (this.getUsername().equals(user.getUsername()));
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return username == null ? System.identityHashCode(this) 
				: (this.getClass().getName() + this.getUsername()).hashCode();
	}
}
