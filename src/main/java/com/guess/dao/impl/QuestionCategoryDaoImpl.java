package com.guess.dao.impl;

import org.springframework.stereotype.Component;

import com.guess.dao.QuestionCategoryDao;
import com.guess.model.CategoryQuestion;

@Component
public class QuestionCategoryDaoImpl extends BaseDaoImpl<CategoryQuestion, String> implements QuestionCategoryDao{

}
