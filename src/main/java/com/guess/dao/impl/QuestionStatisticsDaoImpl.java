package com.guess.dao.impl;

import org.springframework.stereotype.Component;

import com.guess.dao.QuestionStatisticsDao;
import com.guess.model.QuestionStatistics;

@Component
public class QuestionStatisticsDaoImpl extends BaseDaoImpl<QuestionStatistics, String>
	implements QuestionStatisticsDao{

}
