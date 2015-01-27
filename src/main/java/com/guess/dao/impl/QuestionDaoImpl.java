package com.guess.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.guess.dao.QuestionDao;
import com.guess.model.Question;
@Component
public class QuestionDaoImpl extends BaseDaoImpl<Question, String> implements QuestionDao{
	@Override
	public Question get(String id){
		Question question = super.get(id);
		if(question == null || question.getIsDeleted())
			return null;
		else
			return question;
	}
	
	@Override
	public List<Question> getAllList(){
		String query = "from Question q where q.isDeleted = false";
		List<Question> questions = hibernateTemplate.find(query);
		return questions;
	}
	
	@Override
	public Question load(String id){
		Question question = super.load(id);
		if(question == null || question.getIsDeleted())
			return null;
		else
			return question;
	}
	
	@Override
	public void delete(String id){
		Question question = get(id);
		if(question != null){
			question.setIsDeleted(true);
			update(question);
		}
	}
	
	@Override
	public void delete(String [] ids){
		for(String id : ids){
			delete(id);
		}
	}
}
