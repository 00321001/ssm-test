package com.javen.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.javen.dao.AdminDao;
import com.javen.model.Admin;
import com.javen.service.IAdminService;

@Service
public class IAdminServiceImpl implements IAdminService{


	@Resource
	private AdminDao adminDao;
	
	public Admin loginAdmin(Admin admin) {
		// TODO Auto-generated method stub
		return this.adminDao.loginAdmin(admin);
	}

}
