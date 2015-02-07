package com.guess.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Question extends BaseModel{
	private QuestionType type;
	private String content;//question content
	private String contentUrl;
	private String linkName;
	private String linkUrl;
	private String options;//question options
	private String answer;//question answer
	private String username;
	private String categories;//json format, includes multiple items, every item contains category name and id
	private boolean isPublic;//true-all users can find the question
	private boolean isPublished;
	private boolean isReported = false;//whether the question had been reported
	private boolean isDeleted = false;
	private Date date;
	
	@Column(nullable = false)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(length = 3000)
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
	public String getCategories() {
		return categories;
	}
	public void setCategories(String categories) {
		this.categories = categories;
	}
	@Column(nullable = false)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Column(nullable = false)
	public QuestionType getType() {
		return type;
	}
	public void setType(QuestionType type) {
		this.type = type;
	}
	@Column(nullable = false)
	public boolean getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	@Column(nullable = false)
	public boolean getIsPublished() {
		return isPublished;
	}
	public void setIsPublished(boolean isPublished) {
		this.isPublished = isPublished;
	}
	@Column
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	@Column
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	@Column
	public String getContentUrl() {
		return contentUrl;
	}
	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
}
