package com.javen.dao;

import java.util.HashMap;
import java.util.List;

import com.javen.model.Room;
import com.javen.model.User;

public interface RoomDao {
	List<Room> selectAll(Integer startIndex, Integer pageSize);
	int resetTimeOut(String dateTime);
	int selectCount();
	int deleteRoomById(Integer id);
	int addRoom(Integer roomNum);
	User selectUserById(Integer id);
	Room selectRoomByRoomNum(Integer roomNum);
	int bookRoom(HashMap<String, String> map);
	int checkIn(HashMap<String, String> map);
	List<Room> selectFreeRoom(Integer startIndex, Integer pageSize);
	int selectFreeCount();
}
