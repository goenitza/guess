package com.guess.dao.impl;

import org.springframework.stereotype.Component;

import com.guess.dao.OrganizationDao;
import com.guess.model.Organization;

@Component
public class OrganizationDaoImpl extends BaseDaoImpl<Organization, String> implements OrganizationDao{

	public Organization getByUsername(String username) {
		String query = "from Organization o where o.username = :username";
		return (Organization) currentSession().createQuery(query).setString("username", username).uniqueResult();
	}
}
