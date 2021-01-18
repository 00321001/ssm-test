package com.javen.dao;

import java.util.List;

import com.javen.model.Login;

public interface LoginDao {

	Login selectByPrimaryKey(int id);

	int deleteByPrimaryKey(int id);

	int insert(Login login);

	int updateByPrimaryKey(Login login);

	List<Login> selectAll(int page,int limit);

	int selectCount();
}
