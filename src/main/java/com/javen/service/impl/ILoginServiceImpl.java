package com.javen.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.javen.dao.LoginDao;
import com.javen.model.Login;
import com.javen.service.ILoginService;

@Service
public class ILoginServiceImpl implements ILoginService {

	@Resource
	private LoginDao loginDao;

	public Login selectByPrimaryKey(int id) {
		// TODO Auto-generated method stub
		return this.loginDao.selectByPrimaryKey(id);
	}

	public int deleteByPrimaryKey(int id) {
		// TODO Auto-generated method stub
		return this.loginDao.deleteByPrimaryKey(id);
	}

	public int selectCount() {
		// TODO Auto-generated method stub
		return this.loginDao.selectCount();
	}

	public int insert(Login login) {
		// TODO Auto-generated method stub
		return this.loginDao.insert(login);
	}

	public int updateByPrimaryKey(Login login) {
		// TODO Auto-generated method stub
		return this.loginDao.updateByPrimaryKey(login);
	}

	/*
	 * page代表我们在第几页
	 * limit代表我们每一页的数据量
	 * (non-Javadoc)
	 * @see com.javen.service.ILoginService#selectAll(int, int)
	 */
	public List<Login> selectAll(int page,int limit) {
		// TODO Auto-generated method stub
		int pageIndex = (page - 1) * limit;
		int pageSize = limit;
		return this.loginDao.selectAll(pageIndex,pageSize);
	}

}
