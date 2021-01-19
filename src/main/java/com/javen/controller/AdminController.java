package com.javen.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javan.util.JsonUtil;
import com.javen.model.Admin;
import com.javen.service.IAdminService;

@Controller // 返回指定页面 ajax 不能接受到页面的返回 ，所以说
@RequestMapping("/admin")
public class AdminController {
	private static Logger log = LoggerFactory.getLogger(AdminController.class);

	@Resource
	private IAdminService adminService;
	
	//管理员登录
	@ResponseBody
	@RequestMapping(value = "/loginAdmin", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String loginAdmin(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String adminName = request.getParameter("adminName");
		String password = request.getParameter("password");
		Admin admin = new Admin();
		admin.setPassword(password);
		admin.setAdminName(adminName);
		Admin adminlogin = adminService.loginAdmin(admin);
		session.setAttribute("adminName",adminlogin.getAdminName());
		String data = "{\"data\":\"返回成功\"}";
		return data;
	}
	
	//检查登录
	@ResponseBody
	@RequestMapping(value = "/checkAdmin", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String checkAdmin(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String adminName = (String)session.getAttribute("adminName");
		System.out.println(adminName);
		String data;
		if(!adminName.equals("admin")) {
			data = "{\"code\":\"7777\"}";
		}else {
			data = "{\"code\":\"0000\",\"data\":{\"adminName\":\""+adminName+"\"}}";
		}
		System.out.println(data);
		return data;
	}
}
