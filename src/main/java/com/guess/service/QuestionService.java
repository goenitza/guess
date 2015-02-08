package com.guess.service;

import com.guess.model.Question;

public interface QuestionService extends BaseService<Question, String>{
	String save(Question question, String friends);
	void share(String username, String id, String friends);
}
