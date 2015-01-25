package com.guess.dao;

import com.guess.model.Admin;

public interface AdminDao extends BaseDao<Admin, String>{
	Admin getByUsername(String username);
}
