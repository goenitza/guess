package com.guess.vo;

import java.util.List;

public class QuestionOption {
	int num;
	List<QuestionOptionItem> questionOptionItems;
}

class QuestionOptionItem {
	int index;
	String content;
	String imageUrl;
}