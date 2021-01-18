package com.javen.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;

import org.springframework.stereotype.Service;

import com.javen.dao.UserDao;
import com.javen.model.User;
import com.javen.service.IUserService;

@Service
public class IUserServiceImpl implements IUserService{

	@Resource
	private UserDao userDao;

	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return this.userDao.deleteById(id);
	}

	public int signUp(User user) {
		// TODO Auto-generated method stub
		return this.userDao.signUp(user);
	}

	public int updateById(User user) {
		// TODO Auto-generated method stub
		return this.userDao.updateById(user);
	}

	public List<User> selectAll(int page, int limit) throws Exception{
		// TODO Auto-generated method stub
		Date dateTime = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	    String nowTime = df.format(dateTime);// new Date()为获取当前系统时间
		int pageIndex = (page - 1) * limit;
		int pageSize = limit;
		this.userDao.updateOut(nowTime);//超时重置
		List<User> users = this.userDao.selectAll(pageIndex,pageSize);
		for (User user : users) {
			String endTimeStr = user.getEndTime();
			if(endTimeStr != null && endTimeStr != "") {
				Date endTime = df.parse(endTimeStr);
				long second = (endTime.getTime() - dateTime.getTime()) / 1000;
				String haveTime = second / 3600 + ":" + (second % 3600) / 60;
				user.setHavaTime(haveTime);
			}
		}
		return users;
	}

	public int selectCount() {
		// TODO Auto-generated method stub
		return this.userDao.selectCount();
	}

}
