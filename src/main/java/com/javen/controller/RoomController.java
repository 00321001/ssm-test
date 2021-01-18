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
	@RequestMapping(value="/addRoom", method=RequestMethod.GET,produces = "text/plain;charset=utf-8")
	public String addRoom(HttpServletRequest request) throws Exception{
		request.setCharacterEncoding("utf-8");
		Integer roomNum = Integer.parseInt(request.getParameter("roomNum"));
		String data = "{\"data\":\"";
		try {
			roomService.addRoom(roomNum);
			data += "新增成功\"}";
		} catch (Exception e) {
			data += "新增失败\"}";
		}
		return data;
	}
	
	
}
