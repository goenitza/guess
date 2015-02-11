package com.guess.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.guess.enums.CircleUserType;

@Entity
public class CircleUser{
	private String circleId;
	private String userId;
	private String _userId;
	private CircleUserType type;
	
	@Id
	@GenericGenerator(name = "assigned", strategy = "assigned")
	@GeneratedValue(generator = "assigned")
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	@Column(nullable = false)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(nullable = false)
	public String get_userId() {
		return _userId;
	}
	public void set_userId(String _userId) {
		this._userId = _userId;
	}
	@Column()
	public CircleUserType getType() {
		return type;
	}
	public void setType(CircleUserType type) {
		this.type = type;
	}
}
