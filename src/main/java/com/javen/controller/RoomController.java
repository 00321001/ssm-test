package com.javen.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javan.util.ObjtoLayJson;
import com.javen.model.Room;
import com.javen.service.IRoomService;

@Controller  //返回指定页面  ajax 不能接受到页面的返回 ，所以说
@RequestMapping("/room") 

public class RoomController {
	
	@Resource
	private IRoomService roomService;
	
	@ResponseBody
    @RequestMapping(value="/selectAll", method=RequestMethod.GET,produces = "text/plain;charset=utf-8")  
    public String selectAll(HttpServletRequest request) throws Exception{  
    	request.setCharacterEncoding("utf-8");  
    	String pageString = request.getParameter("page");
    	String limitString = request.getParameter("limit");
    	System.out.println(pageString + " "+limitString);
    	List<Room> logins = roomService.selectAll(Integer.parseInt(pageString), Integer.parseInt(limitString));
      	String[] colums = {"id","roomNum","roomState","userName", "phoneNumber", "idCard", "roomTime", "endTime", "haveTime" };
    	String data = ObjtoLayJson.ListtoJson(logins, colums);
    	System.out.println(data);
        return data; 
    }
	
	@ResponseBody
    @RequestMapping(value="/selectCount", method=RequestMethod.GET,produces = "text/plain;charset=utf-8")  
    public String selectCount(HttpServletRequest request) throws Exception{  
    	request.setCharacterEncoding("utf-8");
    	int count = roomService.selectCount();
    	String data = "{\"count\":"+count+"}";
    	System.out.println(data);
        return data; 
    }
	
	@ResponseBody
	@RequestMapping(value="/deleteById", method=RequestMethod.POST,produces = "text/plain;charset=utf-8")
	public String deleteById(HttpServletRequest request) throws Exception{
		request.setCharacterEncoding("utf-8");
		Integer id = Integer.parseInt(request.getParameter("id"));
		int flag = roomService.deleteRoomById(id);
		String data = "{\"data\":\"";
		if(flag == 0) {
			data += "删除失败\"}";
		}else if(flag == 1){
			data += "删除成功\"}";
		}else {
			data += "未知错误\"}";
		}
		return data;
	}
	
	@ResponseBody
	@RequestMapping(value="/addRoom", method=RequestMethod.POST,produces = "text/plain;charset=utf-8")
	public String addRoom(HttpServletRequest request) throws Exception{
		request.setCharacterEncoding("utf-8");
		Integer roomNum = Integer.parseInt(request.getParameter("roomNum"));
		String data = "{\"data\":\"";
		try {
			roomService.addRoom(roomNum);
			data += "新增成功\"}";
		} catch (Exception e) {
			e.printStackTrace();
			data += "新增失败\"}";
		}
		return data;
	}
	
	@ResponseBody
	@RequestMapping(value="/bookRoom", method=RequestMethod.GET,produces = "text/plain;charset=utf-8")
	public String bookRoom(HttpServletRequest request) throws Exception{
		request.setCharacterEncoding("utf-8");
		Integer roomNum = Integer.parseInt(request.getParameter("roomNum"));
		Integer userId = Integer.parseInt(request.getParameter("userId"));
		String roomTime = request.getParameter("roomTime");
		int flag = roomService.bookRoom(userId, roomNum, roomTime);
		String data = "{\"data\":\"";
		if(flag == 0) {
			data += "预定成功\"}";
		}else if(flag == 1){
			data += "用户已有预定或入住房间\"}";
		}else if(flag == 2) {
			data += "该房间已被预定或入住\"}";
		}
		return data;
	}
	
	@ResponseBody
	@RequestMapping(value="/checkIn", method=RequestMethod.GET,produces = "text/plain;charset=utf-8")
	public String checkIn(HttpServletRequest request) throws Exception{
		request.setCharacterEncoding("utf-8");
		String phoneNumber = request.getParameter("phoneNumber");
		Integer roomNum = Integer.parseInt(request.getParameter("roomNum"));
		String roomTime = request.getParameter("roomTime");
		String data = "{\"data\":\"";
		try {
			roomService.checkIn(phoneNumber, roomNum, roomTime);
			data += "操作成功\"}";
		} catch (Exception e) {
			e.printStackTrace();
			data += "操作失败\"}";
		}
		return data;
	}
}
