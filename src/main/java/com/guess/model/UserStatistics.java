package com.guess.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class UserStatistics{
	private String userId;
	private int answerCount = 0;
	private int answerObjectiveCount = 0;
	private int answerCorrectCount = 0;
	private int createCount = 0;
	private int createObjectiveCount = 0;
	
	@Id
	@GenericGenerator(name = "assigned", strategy = "assigned")
	@GeneratedValue(generator = "assigned")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
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
	
	@Override
	public boolean equals(Object object){
		if(object == null){
			return false;
		}
		if(object instanceof UserStatistics){
			UserStatistics userStatistics = (UserStatistics) object;
			if(this.getUserId() == null || userStatistics.getUserId() == null){
				return false;
			}
			return (this.getUserId().equals(userStatistics.getUserId()));
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return userId == null ? System.identityHashCode(this) : (this.getClass().getName() + this.getUserId()).hashCode();
	}
}
