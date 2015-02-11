package com.guess.dao;

import com.guess.model.Individual;

public interface IndividualDao extends BaseDao<Individual, String>{
	Individual getByUsername(String username);
}
