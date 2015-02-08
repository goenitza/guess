package com.guess.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class QuestionStatistics extends BaseModelAssignedId{
	// how many time does the question be answered
	private int answerCount = 0;
	// how many time does the question be answered correctly, only for objective
	// question
	private int answerCorrectCount = 0;
	// how many users does the question be shared to
	private int shareCount = 0;
	// true if the question is objective question
	private boolean isObjective;

	@Column(nullable = false)
	public int getAnswerCount() {
		return answerCount;
	}
	
	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}
	
	@Column(nullable = false)
	public int getAnswerCorrectCount() {
		return answerCorrectCount;
	}
	public void setAnswerCorrectCount(int answerCorrectCount) {
		this.answerCorrectCount = answerCorrectCount;
	}

	@Column(nullable = false)
	public int getShareCount() {
		return shareCount;
	}

	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}

	@Column(nullable = false)
	public boolean getIsObjective() {
		return isObjective;
	}

	public void setIsObjective(boolean isObjective) {
		this.isObjective = isObjective;
	}
}
