package com.javen.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javan.util.JsonUtil;
import com.javen.model.User;
import com.javen.service.IUserService;

@Controller // 返回指定页面 ajax 不能接受到页面的返回 ，所以说
@RequestMapping("/user")
public class UserController {
	@Resource
	private IUserService userService;

	// /user/test?id=1
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(HttpServletRequest request, Model model) {
		return "back";
	}

	// 删除用户信息
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String delete(HttpServletRequest request) {
		String idString = request.getParameter("id");
		Integer id = Integer.valueOf(idString);
		int flag = userService.deleteById(id);
		String data;
		if (flag == 1) {
			data = "{\"data\":\"删除成功\"}";
		} else {
			data = "{\"data\":\"删除失败\"}";
		}
		return data;
	}

	// 用户注册
	@ResponseBody
	@RequestMapping(value = "/signUp", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String signUp(HttpServletRequest request) {
		// 插入数据库
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String phoneNumber = request.getParameter("phoneNumber");
		String idCard = request.getParameter("idCard");
		String data = "";
		if (userName == "" || idCard == "" || password == "" || phoneNumber == "") {
			data = "{\"data\":\"9999\"}";
		} else {
			User user = new User();
			user.setUserName(userName);
			user.setPassword(password);
			user.setPhoneNumber(phoneNumber);
			user.setIdCard(idCard);
			int flag = userService.signUp(user);
			if (flag == 1) {
				data = "{\"data\":\"0000\"}";
			} else {
				data = "{\"data\":\"9999\"}";
			}
		}
		// 给前台返回的东西
		return data;
	}

	// 修改信息
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String update(HttpServletRequest request) {
		// 插入数据库
		String idString = request.getParameter("id");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String phoneNumber = request.getParameter("phoneNumber");
		String idCard = request.getParameter("idCard");
		Integer id = Integer.valueOf(idString);
		String data = "";
		if (userName == "" || idCard == "" || password == "" || phoneNumber == "") {
			data = "{\"data\":\"9999\"}";
		} else {
			User user = new User();
			user.setId(id);
			user.setUserName(userName);
			user.setPassword(password);
			user.setPhoneNumber(phoneNumber);
			user.setIdCard(idCard);
			int flag = userService.updateById(user);
			if (flag == 1) {
				data = "{\"data\":\"0000\"}";
			} else {
				data = "{\"data\":\"9999\"}";
			}
		}
		// 给前台返回的东西
		return data;
	}

	// 查询全部
	@ResponseBody
	@RequestMapping(value = "/selectAll", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String selectAll(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String pageString = request.getParameter("page");
		String limitString = request.getParameter("limit");
		Integer page = Integer.valueOf(pageString);
		Integer limit = Integer.valueOf(limitString);
		List<User> users = userService.selectAll(page, limit);
		String[] colums = { "id", "userName", "password", "phoneNumber", "idCard", "bookedRoom", "havaTime", "roomTime",
				"endTime" };
		String data = JsonUtil.listToLayJson(colums, users);
		return data;
	}

	// 查询有多少条数据
	@ResponseBody
	@RequestMapping(value = "/selectCount", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String selectCount(HttpServletRequest request) throws Exception {
		int count = userService.selectCount();
		String data = String.valueOf(count);
		String json = "{" + "\"count\":" + data + "}";
		return json;
	}

	// 用户登录
	@ResponseBody
	@RequestMapping(value = "/loginUser", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String loginUser(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String phoneNumber = request.getParameter("phoneNumber");
		String password = request.getParameter("password");
		User user = new User();
		user.setPassword(password);
		user.setPhoneNumber(phoneNumber);
		User userlogin = userService.loginUser(user);
		session.setAttribute("userid", userlogin.getId().toString());
		session.setAttribute("userName", userlogin.getUserName());
		String data = "{\"data\":\"返回成功\"}";
		return data;
	}

	// 检查登录
	@ResponseBody
	@RequestMapping(value = "/checkUser", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String checkUser(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		String userName = (String) session.getAttribute("userName");
		String data;
		if (userid == null || userid == "") {
			data = "{\"code\":\"7777\"}";
		} else {
			data = "{\"code\":\"0000\",\"data\":{\"userid\":\"" + userid + "\",\"userName\":\"" + userName + "\"}}";
		}
		System.out.println(data);
		return data;
	}
}
