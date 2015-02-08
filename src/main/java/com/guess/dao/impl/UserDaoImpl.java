package com.guess.dao.impl;

import org.springframework.stereotype.Component;

import com.guess.dao.UserDao;
import com.guess.model.User;

@Component
public class UserDaoImpl extends BaseDaoImpl<User, String> implements UserDao{

}
