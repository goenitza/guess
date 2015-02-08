package com.guess.model;

import javax.persistence.Entity;

//who does the question been shared to
@Entity
public class QuestionShare extends BaseModelAssignedId{
	private String friends;//json
	public String getFriends() {
		return friends;
	}
	public void setFriends(String friends) {
		this.friends = friends;
	}
}
