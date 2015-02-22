package com.guess.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class QuestionStatistics{
	private String questionId;
	// how many time does the question be answered
	private int answerCount = 0;
	// how many time does the question be answered correctly, only for objective
	// question
	private int answerCorrectCount = 0;
	// how many times does the question be shared to
	private int shareCount = 0;
	// how many users does the question be shared to
	private int shareUserCount = 0;
	// true if the question is objective question
	private boolean isObjective;
	
	@Id
	@GenericGenerator(name = "assigned", strategy = "assigned")
	@GeneratedValue(generator = "assigned")
	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

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
	@Column(nullable = false)
	public int getShareUserCount() {
		return shareUserCount;
	}

	public void setShareUserCount(int shareUserCount) {
		this.shareUserCount = shareUserCount;
	}
	
	@Override
	public boolean equals(Object object){
		if(object == null){
			return false;
		}
		if(object instanceof QuestionStatistics){
			QuestionStatistics questionStatistics = (QuestionStatistics) object;
			if(this.getQuestionId() == null || questionStatistics.getQuestionId() == null){
				return false;
			}
			return (this.getQuestionId().equals(questionStatistics.getQuestionId()));
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return questionId == null ? System.identityHashCode(this) : (this.getClass().getName() + this.getQuestionId()).hashCode();
	}
}
