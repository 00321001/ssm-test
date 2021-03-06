package com.javen.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javan.util.JsonUtil;
import com.javen.model.Room;
import com.javen.service.IRoomService;

@Controller // 返回指定页面 ajax 不能接受到页面的返回 ，所以说
@RequestMapping("/room")

public class RoomController {

	@Resource
	private IRoomService roomService;

	// 后台获取所有数据接口，用于展示数据
	@ResponseBody
	@RequestMapping(value = "/selectAll", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String selectAll(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String pageString = request.getParameter("page");
		String limitString = request.getParameter("limit");
		if (pageString == null || limitString == null) {
			return "{\"code\":\"9999\",\"data\":\"参数为空\"}";
		} else {
			pageString = pageString.trim();
			limitString = limitString.trim();
			if (pageString.length() == 0 || limitString.length() == 0) {
				return "{\"code\":\"9999\",\"data\":\"参数为空\"}";
			}
		}
		HttpSession se = request.getSession();
		String adminId = (String) se.getAttribute("adminId");
		if (adminId == null || adminId.length() == 0) {
			return "{\"code\":\"7777\",\"data\":\"未登录\"}";
		}
		System.out.println(pageString + " " + limitString);
		List<Room> rooms = roomService.selectAll(Integer.parseInt(pageString), Integer.parseInt(limitString));
		String[] colums = { "id", "roomNum", "roomState", "userName", "phoneNumber", "idCard", "roomTime", "endTime",
				"haveTime" };
		String data = JsonUtil.listToLayJson(colums, rooms);
		System.out.println(data);
		return data;
	}

	// 后台获取数据总数接口，用于分页查询
	@ResponseBody
	@RequestMapping(value = "/selectCount", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String selectCount(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession se = request.getSession();
		String adminId = (String) se.getAttribute("adminId");
		if (adminId == null || adminId.length() == 0) {
			return "{\"code\":\"7777\",\"data\":\"未登录\"}";
		}
		int count = roomService.selectCount();
		String data = "{\"code\":\"0000\",\"count\":" + count + "}";
		System.out.println(data);
		return data;
	}

	// 后台删除房间接口
	@ResponseBody
	@RequestMapping(value = "/deleteById", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	public String deleteById(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String idStr = request.getParameter("id");
		// 判空
		if (idStr == null) {
			return "{\"code\":\"9999\",\"data\":\"参数为空\"}";
		} else {
			idStr = idStr.trim();
			if (idStr.length() == 0) {
				return "{\"code\":\"9999\",\"data\":\"参数为空\"}";
			}
		}
		// 登录验证
		HttpSession se = request.getSession();
		String adminId = (String) se.getAttribute("adminId");
		if (adminId == null || adminId.length() == 0) {
			return "{\"code\":\"7777\",\"data\":\"未登录\"}";
		}
		// 功能实现
		Integer id = Integer.valueOf(idStr);
		int flag = roomService.deleteRoomById(id);
		String data = "{\"code\":\"0000\",\"data\":\"";
		if (flag == 0) {
			data += "删除失败\"}";
		} else if (flag == 1) {
			data += "删除成功\"}";
		} else {
			data += "未知错误\"}";
		}
		return data;
	}

	// 后台添加房间接口
	@ResponseBody
	@RequestMapping(value = "/addRoom", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	public String addRoom(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String roomNumStr = request.getParameter("roomNum");
		// 判空
		if (roomNumStr == null) {
			return "{\"code\":\"9999\",\"data\":\"参数为空\"}";
		} else {
			roomNumStr = roomNumStr.trim();
			if (roomNumStr.length() == 0) {
				return "{\"code\":\"9999\",\"data\":\"参数为空\"}";
			}
		}
		// 登录验证
		HttpSession se = request.getSession();
		String adminId = (String) se.getAttribute("adminId");
		if (adminId == null || adminId.length() == 0) {
			return "{\"code\":\"7777\",\"data\":\"未登录\"}";
		}
		// 功能实现
		Integer roomNum = Integer.valueOf(roomNumStr);
		String data = "{\"code\":\"0000\",\"data\":\"";
		try {
			roomService.addRoom(roomNum);
			data += "新增成功\"}";
		} catch (Exception e) {
			e.printStackTrace();
			data += "新增失败\"}";
		}
		return data;
	}

	// 预定房间接口（前后台共用）
	@ResponseBody
	@RequestMapping(value = "/bookRoom", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	public String bookRoom(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String roomNumStr = request.getParameter("roomNum");
		String userIdStr = request.getParameter("userId");
		String roomTime = request.getParameter("roomTime");
		// 判空
		if (roomNumStr == null || userIdStr == null || roomTime == null) {
			return "{\"code\":\"9999\",\"data\":\"参数为空\"}";
		} else {
			roomNumStr = roomNumStr.trim();
			userIdStr = userIdStr.trim();
			roomTime = roomTime.trim();
			if (roomNumStr.length() == 0 || userIdStr.length() == 0 || roomTime.length() == 0) {
				return "{\"code\":\"9999\",\"data\":\"参数为空\"}";
			}
		}
		// 登录验证
		HttpSession se = request.getSession();
		String seUserId = (String) se.getAttribute("userid");
		System.out.println(seUserId);
		String seAdminId = (String) se.getAttribute("adminId");
		System.out.println(seAdminId);
		if ((seUserId == null || seUserId.length() == 0) && (seAdminId == null || seAdminId.length() == 0)) {
			return "{\"code\":\"7777\",\"data\":\"未登录\"}";
		}
		// 功能实现
		Integer roomNum = Integer.valueOf(roomNumStr);
		Integer userId = Integer.parseInt(userIdStr);
		int flag = roomService.bookRoom(userId, roomNum, roomTime);
		String data = "{\"code\":\"0000\",\"data\":\"";
		if (flag == 0) {
			data += "预定成功\"}";
		} else if (flag == 1) {
			data += "用户已有预定或入住房间\"}";
		} else if (flag == 2) {
			data += "该房间已被预定或入住\"}";
		} else if (flag == 3) {
			data += "无此用户\"}";
		} else if (flag == 4) {
			data += "请填写所有字段\"}";
		}
		return data;
	}

	// 后台入住（退房）接口
	@ResponseBody
	@RequestMapping(value = "/checkIn", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	public String checkIn(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String phoneNumber = request.getParameter("phoneNumber");
		String roomNumStr = request.getParameter("roomNum");
		String roomTime = request.getParameter("roomTime");
		// 判空
		if (phoneNumber == null || roomNumStr == null || roomTime == null) {
			return "{\"code\":\"9999\",\"data\":\"参数为空\"}";
		} else {
			phoneNumber = phoneNumber.trim();
			roomNumStr = roomNumStr.trim();
			roomTime = roomTime.trim();
			if (phoneNumber.length() == 0 || roomNumStr.length() == 0 || roomTime.length() == 0) {
				return "{\"code\":\"9999\",\"data\":\"参数为空\"}";
			}
		}
		// 登录验证
		HttpSession se = request.getSession();
		String adminId = (String) se.getAttribute("adminId");
		if (adminId == null || adminId.length() == 0) {
			return "{\"code\":\"7777\",\"data\":\"未登录\"}";
		}
		// 功能实现
		String data = "{\"code\":\"0000\",\"data\":\"";
		try {
			Integer roomNum = Integer.valueOf(roomNumStr);
			roomService.checkIn(phoneNumber, roomNum, roomTime);
			data += "操作成功\"}";
		} catch (Exception e) {
			e.printStackTrace();
			data += "操作失败\"}";
		}
		return data;
	}

	// 前台获取所有空闲房间接口
	@ResponseBody
	@RequestMapping(value = "/seeRoom", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String seeRoom(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String pageString = request.getParameter("page");
		String limitString = request.getParameter("limit");
		// 判空
		if (pageString == null || limitString == null) {
			return "{\"code\":\"9999\",\"data\":\"参数为空\"}";
		} else {
			pageString = pageString.trim();
			limitString = limitString.trim();
			if (pageString.length() == 0 || limitString.length() == 0) {
				return "{\"code\":\"9999\",\"data\":\"参数为空\"}";
			}
		}
		// 登录验证
		HttpSession se = request.getSession();
		String userid = (String) se.getAttribute("userid");
		if (userid == null || userid.length() == 0) {
			return "{\"code\":\"7777\",\"data\":\"未登录\"}";
		}
		List<Room> rooms = roomService.selectFreeRoom(Integer.parseInt(pageString), Integer.parseInt(limitString));
		String[] colums = { "id", "roomNum" };
		String data = JsonUtil.listToLayJson(colums, rooms);
		System.out.println(data);
		return data;
	}

	// 前台获取空闲房间总数接口
	@ResponseBody
	@RequestMapping(value = "/selectFreeCount", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String selectFreeCount(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession se = request.getSession();
		String userid = (String) se.getAttribute("userid");
		if (userid == null || userid.length() == 0) {
			return "{\"code\":\"7777\",\"data\":\"未登录\"}";
		}
		int count = roomService.selectFreeCount();
		String data = "{\"code\":\"0000\",\"count\":" + count + "}";
		System.out.println(data);
		return data;
	}

	// 前台登录检查接口
	@ResponseBody
	@RequestMapping(value = "/checkUserLogin", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String checkUserLogin(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession se = request.getSession();
//    	测试用的假数据
//    	se.setAttribute("userid", "2");
//    	se.setAttribute("userName", "yty");
		String userid = (String) se.getAttribute("userid");
		String userName = (String) se.getAttribute("userName");
		String data;
		if (userid == null || userid == "") {
			data = "{\"code\":\"7777\",\"data\":\"未登录\"}";
			System.out.println(11);
		} else {
			data = "{\"code\":\"0000\",\"data\":{\"userid\":\"" + userid + "\",\"userName\":\"" + userName + "\"}}";
		}
		System.out.println(data);
		return data;
	}

	// 前台登出接口
	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String logout(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession se = request.getSession();
		se.invalidate();
		return "{\"code\":\"0000\",\"data\":\"操作成功\"}";
	}

	// 前台取消预约接口
	@ResponseBody
	@RequestMapping(value = "/cancelBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	public String cancelBook(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String userId = request.getParameter("userid");
		// 判空
		if (userId == null) {
			return "{\"code\":\"9999\",\"data\":\"参数为空\"}";
		} else {
			userId = userId.trim();
			if (userId.length() == 0) {
				return "{\"code\":\"9999\",\"data\":\"参数为空\"}";
			}
		}
		// 登录验证
		HttpSession se = request.getSession();
		String userid = (String) se.getAttribute("userid");
		if (userid == null || userid.length() == 0) {
			return "{\"code\":\"7777\",\"data\":\"未登录\"}";
		}
		String data = "{\"data\":\"";
		int flag = roomService.cancelBook(userId);
		if (flag == 0) {
			data += "操作成功\"}";
		} else if (flag == 1) {
			data += "用户没有预定房间\"}";
		} else if (flag == 2) {
			data += "操作失败\"}";
		}

		return data;
	}

}
