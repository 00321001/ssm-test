package com.javen.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javan.util.JsonUtil;
import com.javan.util.ObjtoLayJson;
import com.javen.model.User;
import com.javen.service.IUserService;

@Controller // 返回指定页面 ajax 不能接受到页面的返回 ，所以说
@RequestMapping("/user")
public class UserController {
	private static Logger log = LoggerFactory.getLogger(UserController.class);

	@Resource
	private IUserService userService;

	// /user/test?id=1
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(HttpServletRequest request, Model model) {
		return "back";
	}

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

	@ResponseBody
	@RequestMapping(value = "/signUp", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String signUp(HttpServletRequest request) {
		// 插入数据库
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String phoneNumber = request.getParameter("phoneNumber");
		String idCard = request.getParameter("idCard");
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setPhoneNumber(phoneNumber);
		user.setIdCard(idCard);
		int flag = userService.signUp(user);
		// 给前台返回的东西
		String data;
		if (flag == 1) {
			data = "{\"data\":\"注册成功\"}";
		} else {
			data = "{\"data\":\"注册失败\"}";
		}
		return data;
	}

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
		User user = new User();
		user.setId(id);
		user.setUserName(userName);
		user.setPassword(password);
		user.setPhoneNumber(phoneNumber);
		user.setIdCard(idCard);
		int flag = userService.updateById(user);
		// 给前台返回的东西
		String data;
		if (flag == 1) {
			data = "{\"data\":\"修改成功\"}";
		} else {
			data = "{\"data\":\"修改失败\"}";
		}
		return data;
	}

	// 返回字符串
	@ResponseBody
	@RequestMapping(value = "/selectAll", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String selectAll(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String pageString = request.getParameter("page");
		String limitString = request.getParameter("limit");
		Integer page = Integer.valueOf(pageString);
		Integer limit = Integer.valueOf(limitString);
		List<User> users = userService.selectAll(page, limit);
		String[] colums = { "id", "userName", "password", "phoneNumber", "idCard", "bookedRoom", "havaTime", "roomTime", "endTime"};
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
}
