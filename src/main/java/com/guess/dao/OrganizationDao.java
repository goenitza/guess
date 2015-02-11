package com.guess.dao;

import com.guess.model.Organization;

public interface OrganizationDao extends BaseDao<Organization, String>{
	Organization getByUsername(String username);
}
