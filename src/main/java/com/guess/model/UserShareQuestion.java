package com.guess.model;

import javax.persistence.Entity;

//who does the question been shared to
@Entity
public class UserShareQuestion extends BaseModel{
	private String questionId;
	private String friends;//json
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getFriends() {
		return friends;
	}
	public void setFriends(String friends) {
		this.friends = friends;
	}
}
