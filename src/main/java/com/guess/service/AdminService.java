package com.guess.service;

import com.guess.model.Admin;

public interface AdminService extends BaseService<Admin, String>{
	Admin getByUsername(String username);
}
