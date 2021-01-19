package com.javen.service;

import java.util.List;

import com.javen.model.Room;

public interface IRoomService {
	public List<Room> selectAll(Integer pageIndex, Integer pageSize) throws Exception;
	public int selectCount();
	public int deleteRoomById(Integer id);
	public int addRoom(Integer roomNum);
	public int bookRoom(Integer userId, Integer roomNum, String roomTime);
	public int checkIn(String phoneNumber, Integer roomNum, String roomTime);
	public List<Room> selectFreeRoom(Integer pageIndex, Integer pageSize);
	public int selectFreeCount();
	public int cancelBook(String userid);
}
