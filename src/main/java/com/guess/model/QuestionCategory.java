package com.guess.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class QuestionCategory extends BaseModel{
	private String questionId;
	private String categoryId;
	@Column(nullable = false)
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	@Column(nullable = false)
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
}
