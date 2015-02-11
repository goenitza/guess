package com.guess.service;

import com.guess.model.Organization;

public interface OrganizationService extends BaseService<Organization, String>{
	Organization getByUsername(String username);
}
