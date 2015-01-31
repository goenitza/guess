package com.guess.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Type extends BaseModel{
	private String name;
	private boolean isContainsOption;//whether contains options, false for blank filling question and true for true-false question and multiple-choice question
	private boolean isOptionSingle;//true: single select
	private boolean isOptionAll;//true: order question
	private boolean isDeleted = false;
	
	@Column(nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(nullable = false)
	public boolean getIsContainsOption() {
		return isContainsOption;
	}
	public void setIsContainsOption(boolean isContainsOption) {
		this.isContainsOption = isContainsOption;
	}
	@Column(nullable = false)
	public boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	@Column(nullable = false)
	public boolean getIsOptionSingle() {
		return isOptionSingle;
	}
	public void setIsOptionSingle(boolean isOptionSingle) {
		this.isOptionSingle = isOptionSingle;
	}
	@Column(nullable = false)
	public boolean getIsOptionAll() {
		return isOptionAll;
	}
	public void setIsOptionAll(boolean isOptionAll) {
		this.isOptionAll = isOptionAll;
	}
}
