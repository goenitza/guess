package com.guess.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Category extends BaseModel{
	private String name;
	private int questionCount = 0;//how many questions does the category contain
	private boolean isDeleted = false;

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(nullable = false)
	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	@Column(nullable = false)
	public int getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}
}
