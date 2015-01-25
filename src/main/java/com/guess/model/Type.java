package com.guess.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Type extends BaseModel{
	private String name;
	private boolean isContainsQuestion;
	private boolean isContainsOption;
	private boolean isContainsAnswer;
	private boolean isDeleted = false;
	
	@Column(nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(nullable = false)
	public boolean getIsContainsQuestion() {
		return isContainsQuestion;
	}
	public void setIsContainsQuestion(boolean isContainsQuestion) {
		this.isContainsQuestion = isContainsQuestion;
	}
	@Column(nullable = false)
	public boolean getIsContainsOption() {
		return isContainsOption;
	}
	public void setIsContainsOption(boolean isContainsOption) {
		this.isContainsOption = isContainsOption;
	}
	@Column(nullable = false)
	public boolean getIsContainsAnswer() {
		return isContainsAnswer;
	}
	public void setIsContainsAnswer(boolean isContainsAnswer) {
		this.isContainsAnswer = isContainsAnswer;
	}
	@Column(nullable = false)
	public boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
