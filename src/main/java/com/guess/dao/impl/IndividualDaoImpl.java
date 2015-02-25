package com.guess.dao.impl;

import org.springframework.stereotype.Repository;

import com.guess.dao.IndividualDao;
import com.guess.model.Individual;

@Repository
public class IndividualDaoImpl extends BaseDaoImpl<Individual, String> implements IndividualDao{

	public Individual getByUsername(String username) {
		String query = "from Individual i where i.username = :username";
		return (Individual) currentSession().createQuery(query)
				.setString("username", username).uniqueResult();
	}

}
