package com.javen.dao;

import java.util.List;

import com.javen.model.User;

public interface UserDao {

	int deleteById(int id);

	int signUp(User user);

	int updateById(User user);

	List<User> selectAll(int page, int limit) throws Exception;

	int selectCount();

	void updateOut(String nowTime);
	
	User loginUser(User user);

}
