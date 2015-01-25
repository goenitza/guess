package com.guess.model;

import javax.persistence.Entity;

public class Question extends BaseModel{
	private boolean isContainsQuestion;
	private boolean isContainsOption;
	private boolean isContainsAnswer;
	private String question;
	private String option;
	private String answer;
	private String type;
	private String categories;
}
