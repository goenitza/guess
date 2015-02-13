package com.guess.service;

import java.io.IOException;

import com.guess.model.Individual;

public interface IndividualService extends BaseService<Individual, String>{
	Individual getByUsername(String username);
	String save(Individual individual, String contextPath) throws IOException;
}
