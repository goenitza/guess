package com.guess.service;

import java.io.IOException;

import com.guess.model.Organization;

public interface OrganizationService extends BaseService<Organization, String>{
	Organization getByUsername(String username);
	String save(Organization org, String contextPath) throws IOException;
}
