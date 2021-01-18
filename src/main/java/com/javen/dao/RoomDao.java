package com.javen.dao;

import java.util.List;

import com.javen.model.Room;

public interface RoomDao {
	List<Room> selectAll(Integer startIndex, Integer pageSize);
	int resetTimeOut(String dateTime);
}
