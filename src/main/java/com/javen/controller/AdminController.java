package com.javen.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javen.model.Admin;
import com.javen.service.IAdminService;

@Controller // 返回指定页面 ajax 不能接受到页面的返回 ，所以说
@RequestMapping("/admin")
public class AdminController {
	@Resource
	private IAdminService adminService;

	// 管理员登录
	@ResponseBody
	@RequestMapping(value = "/loginAdmin", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String loginAdmin(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String adminName = request.getParameter("adminName");
		String password = request.getParameter("password");
		if (adminName == null || adminName.length() == 0 || password == null || password.length() == 0) {
			return "{\"code\":\"9999\",\"data\":\"参数为空\"}";
		} else {
			adminName = adminName.trim();
			password = password.trim();
			if (adminName == null || adminName.length() == 0 || password == null || password.length() == 0) {
				return "{\"code\":\"9999\",\"data\":\"参数为空\"}";
			}
		}
		Admin admin = new Admin();
		admin.setPassword(password);
		System.out.println(admin.getPassword());
		admin.setAdminName(adminName);
		System.out.println(admin.getAdminName());
		Admin adminlogin = adminService.loginAdmin(admin);
		session.setAttribute("adminName", adminlogin.getAdminName());
		session.setAttribute("adminId", adminlogin.getId().toString());
		String data = "{\"code\":\"9999\",\"data\":\"返回成功\"}";
		return data;
	}

	// 检查登录
	@ResponseBody
	@RequestMapping(value = "/checkAdmin", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String checkAdmin(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String adminName = (String) session.getAttribute("adminName");
		String adminId = (String) session.getAttribute("adminId");

		System.out.println(adminName);
		String data;
		if (adminId == null || adminId.length() == 0) {
			data = "{\"code\":\"7777\"}";
		} else {
			data = "{\"code\":\"0000\",\"data\":{\"adminName\":\"" + adminName + "\"}}";
		}
		System.out.println(data);
		return data;
	}
}
