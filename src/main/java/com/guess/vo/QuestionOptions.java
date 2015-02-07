package com.guess.vo;

import java.util.List;

public class QuestionOptions {
	int num;
	List<QuestionOption> options;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public List<QuestionOption> getOptions() {
		return options;
	}
	public void setOptions(List<QuestionOption> options) {
		this.options = options;
	}
}