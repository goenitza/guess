package com.guess.service;

import com.guess.model.Individual;

public interface IndividualService extends BaseService<Individual, String>{
	Individual getByUsername(String username);
}
