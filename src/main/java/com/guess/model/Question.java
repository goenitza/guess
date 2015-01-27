package com.guess.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Question extends BaseModel{
	private String question;
	private String option;
	private String answer;
	private int answerCount;
	private int answerCorrectCount;
	private int shareCount;
	private boolean isDeleted = false;
	private boolean isReported = false;
	private Date date;
	private String userId;
	private String typeId;
	private String categoryIds;
	@Column(nullable = false)
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	@Column
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	@Column(nullable = false)
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	@Column
	public int getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}
	@Column
	public int getAnswerCorrectCount() {
		return answerCorrectCount;
	}
	public void setAnswerCorrectCount(int answerCorrectCount) {
		this.answerCorrectCount = answerCorrectCount;
	}
	@Column
	public int getShareCount() {
		return shareCount;
	}
	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}
	@Column(nullable = false)
	public boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	@Column(nullable = false)
	public boolean getIsReported() {
		return isReported;
	}
	public void setIsReported(boolean isReported) {
		this.isReported = isReported;
	}
	@Column
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Column
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(nullable = false)
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	@Column
	public String getCategoryIds() {
		return categoryIds;
	}
	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}
}
