package com.guess.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.guess.enums.CircleUserType;

@Entity
public class CircleUser extends BaseModel{
	private String circleId;
	private String userId;
	private String _userId;
	private CircleUserType type;
	
	@Column()
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
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	public CircleUserType getType() {
		return type;
	}
	public void setType(CircleUserType type) {
		this.type = type;
	}
}
