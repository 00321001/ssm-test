package com.javen.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.javen.dao.RoomDao;
import com.javen.model.Room;
import com.javen.model.User;
import com.javen.service.IRoomService;

@Service
public class IRoomServiceImpl implements IRoomService{
	@Resource
	private RoomDao roomDao;

	public List<Room> selectAll(Integer pageIndex, Integer pageSize) throws Exception {
		// TODO Auto-generated method stub
		Date dateTime = new Date();
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//以下为重置超时房间部分
		String dataTimeStr = simpleFormat.format(dateTime);
		System.out.println(this.roomDao.resetTimeOut(dataTimeStr));
		
		//以下为查询部分
		Integer startIndex = (pageIndex - 1) * pageSize;
		List<Room> re = this.roomDao.selectAll(startIndex, pageSize);
		for (Room room : re) {
			String endTimeStr = room.getEndTime();
			if(endTimeStr != null && endTimeStr != "") {
				Date endTime = simpleFormat.parse(endTimeStr);
				long second = (endTime.getTime() - dateTime.getTime())/1000;
				String haveTime = second/3600 + ":" + (second%3600)/60;
				room.setHaveTime(haveTime);
			}
		}
		return re;
	}

	public int selectCount() {
		// TODO Auto-generated method stub
		return this.roomDao.selectCount();
	}

	public int deleteRoomById(Integer id) {
		// TODO Auto-generated method stub
		return this.roomDao.deleteRoomById(id);
	}

	public int addRoom(Integer roomNum) {
		// TODO Auto-generated method stub
		return this.roomDao.addRoom(roomNum);
	}

	public int bookRoom(Integer userId, Integer roomNum, String roomTime) {
		// TODO Auto-generated method stub
		
		Room room = this.roomDao.selectRoomByRoomNum(roomNum);
		User user = this.roomDao.selectUserById(userId);
		if(user == null) {
			return 3;
		}
		if(roomTime == "" || roomTime == null) {
			return 4;
		}
		if(user.getBookedRoom() == null || user.getBookedRoom() == "") {
			if(room.getRoomState().equals("0")) {
				Date date = new Date();
				long endTime = date.getTime();
				endTime += 1000*3600*3;
				SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date.setTime(endTime);
				String endTimeStr = simpleFormat.format(date);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("userId", userId.toString());
				map.put("roomNum", roomNum.toString());
				map.put("roomTime", roomTime);
				map.put("userName", user.getUserName());
				map.put("phoneNumber", user.getPhoneNumber());
				map.put("idcard", user.getUserName());
				map.put("endTime", endTimeStr);
				System.out.println(this.roomDao.bookRoom(map));
			}else {
				return 2;
			}
		}else {
			return 1;
		}
		return 0;
	}

	public int checkIn(String phoneNumber, Integer roomNum, String roomTime) {
		// TODO Auto-generated method stub
		Date date = new Date();
		long endTime = date.getTime();
		endTime += 1000*3600*24*Integer.valueOf(roomTime);
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date.setTime(endTime);
		String endTimeStr = simpleFormat.format(date);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("phoneNumber", phoneNumber);
		map.put("roomNum", roomNum.toString());
		map.put("endTime", endTimeStr);
		return this.roomDao.checkIn(map);
	}

	public List<Room> selectFreeRoom(Integer pageIndex, Integer pageSize) {
		// TODO Auto-generated method stub
		Date dateTime = new Date();
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dataTimeStr = simpleFormat.format(dateTime);
		System.out.println(this.roomDao.resetTimeOut(dataTimeStr));
		
		Integer startIndex = (pageIndex - 1) * pageSize;
		return this.roomDao.selectFreeRoom(startIndex, pageSize);
	}

	public int selectFreeCount() {
		// TODO Auto-generated method stub
		return this.roomDao.selectFreeCount();
	}

	public int cancelBook(String userid) {
		// TODO Auto-generated method stub
		User user = roomDao.selectUserById(Integer.parseInt(userid));
		if(user.getBookedRoom() == null || user.getBookedRoom() == "") {
			return 1;
		}
		try {
			this.checkIn(user.getPhoneNumber(), Integer.parseInt(user.getBookedRoom()), "-1");
			return 0;
		}catch (Exception e) {
			e.printStackTrace();
			return 2;
		}
		
	}
	
	
}
