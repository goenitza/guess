package com.guess.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.guess.dao.OrganizationDao;
import com.guess.model.Organization;

@Component
public class OrganizationDaoImpl extends BaseDaoImpl<Organization, String> implements OrganizationDao{

	public Organization getByUsername(String username) {
		String query = "from Organization o where o.username = ?";
		List<Organization> organizations = hibernateTemplate.find(query, username);
		return organizations.size() == 0 ? null : organizations.get(0);
	}

}
