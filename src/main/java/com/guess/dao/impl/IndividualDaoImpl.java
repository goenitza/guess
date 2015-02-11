package com.guess.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.guess.dao.IndividualDao;
import com.guess.model.Individual;

@Component
public class IndividualDaoImpl extends BaseDaoImpl<Individual, String> implements IndividualDao{

	public Individual getByUsername(String username) {
		String query = "from Individual i where i.username = ?";
		List<Individual>individuals = hibernateTemplate.find(query, username);
		return individuals.size() == 0 ? null : individuals.get(0);
	}

}
