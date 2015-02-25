package com.guess.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.guess.dao.OrganizationDao;
import com.guess.model.Organization;
import com.guess.model.UserStatistics;
import com.guess.service.ImageService;
import com.guess.service.OrganizationService;
import com.guess.service.UserStatisticsService;

@Component
@Transactional
public class OrganizationServiceImpl extends BaseServiceImpl<Organization, String> implements OrganizationService{
	private OrganizationDao organizationDao;
	@Autowired
	private UserStatisticsService userStatisticsService;
	@Autowired
	private ImageService imageService;

	public OrganizationDao getOrganizationDao() {
		return organizationDao;
	}
	@Autowired
	public void setOrganizationDao(OrganizationDao organizationDao) {
		super.setBaseDao(organizationDao);
		this.organizationDao = organizationDao;
	}
	public Organization getByUsername(String username) {
		return organizationDao.getByUsername(username);
	}
	
	public String save(Organization organization, String contextPath) throws IOException{
		String id = organizationDao.save(organization);
		
		String avatarPath = imageService.setDefaultAvatar(contextPath, id);
		organization.setAvatar(avatarPath);
		organizationDao.update(organization);
		
		UserStatistics userStatistics = new UserStatistics();
		userStatistics.setUserId(id);
		userStatisticsService.save(userStatistics);
		
		return id;
	}
}
