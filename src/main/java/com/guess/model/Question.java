package com.guess.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Question extends BaseModel{
	private String content;//question content
	private String options;//question options
	private String answer;//question answer
	private int answerCount;//the count that the question has been answered
	private int answerCorrectCount;// the count that the question has been answered correctly
	private int shareCount;//the count that the question has been shared
	private boolean isDeleted = false;
	private boolean isReported = false;//whether the question had been reported
	private Date date;
	private String userId;
	private String typeId;
	private String categories;//json format, includes multiple items, every item contains category name and id
	
	@Column(nullable = false)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
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
	public String getCategories() {
		return categories;
	}
	public void setCategories(String categories) {
		this.categories = categories;
	}
}
