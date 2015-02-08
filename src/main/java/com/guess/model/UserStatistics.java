package com.guess.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class UserStatistics extends BaseModelAssignedId{
	private int answerCount = 0;
	private int answerObjectiveCount = 0;
	private int answerCorrectCount = 0;
	private int createCount = 0;
	private int createObjectiveCount = 0;
	@Column(nullable = false)
	public int getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}
	@Column(nullable = false)
	public int getAnswerObjectiveCount() {
		return answerObjectiveCount;
	}
	public void setAnswerObjectiveCount(int answerObjectiveCount) {
		this.answerObjectiveCount = answerObjectiveCount;
	}
	@Column(nullable = false)
	public int getAnswerCorrectCount() {
		return answerCorrectCount;
	}
	public void setAnswerCorrectCount(int answerCorrectCount) {
		this.answerCorrectCount = answerCorrectCount;
	}
	@Column(nullable = false)
	public int getCreateCount() {
		return createCount;
	}
	public void setCreateCount(int createCount) {
		this.createCount = createCount;
	}
	@Column(nullable = false)
	public int getCreateObjectiveCount() {
		return createObjectiveCount;
	}
	public void setCreateObjectiveCount(int createObjectiveCount) {
		this.createObjectiveCount = createObjectiveCount;
	}
}
