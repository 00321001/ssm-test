package com.javen.service;

import java.util.List;

import com.javen.model.User;

public interface IUserService {

	int deleteById(int id);

	int signUp(User user);

	int updateById(User user);

	List<User> selectAll(int page,int limit) throws Exception;

	int selectCount();
	
	
}
