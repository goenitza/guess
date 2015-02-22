package com.guess.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class QuestionShare extends BaseModel{
	private String questionId;
	private String userId;
	private String _userId;
	private Date date;
	@Column(nullable = false)
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
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
	@Column(nullable = false)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
