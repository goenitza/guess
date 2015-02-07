package com.guess.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class QuestionStatistics extends BaseModel{
	//how many time does the question be answered
	private int userAnswerCount = 0;
	//how many time does the question be answered correctly, only for objective question
	private int userAnswerCorrectCount = 0;
	//how many users does the question be shared to
	private int shareCount = 0;
	//true if the question is objective question
	private boolean isObjective;
	@Column(nullable =false)
	public int getUserAnswerCount() {
		return userAnswerCount;
	}
	public void setUserAnswerCount(int userAnswerCount) {
		this.userAnswerCount = userAnswerCount;
	}
	@Column(nullable =false)
	public int getUserAnswerCorrectCount() {
		return userAnswerCorrectCount;
	}
	public void setUserAnswerCorrectCount(int userAnswerCorrectCount) {
		this.userAnswerCorrectCount = userAnswerCorrectCount;
	}
	@Column(nullable =false)
	public int getShareCount() {
		return shareCount;
	}
	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}
	@Column(nullable =false)
	public boolean getIsObjective() {
		return isObjective;
	}
	public void setIsObjective(boolean isObjective) {
		this.isObjective = isObjective;
	}
}
