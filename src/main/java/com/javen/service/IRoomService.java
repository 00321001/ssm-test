package com.javen.service;

import java.util.List;

import com.javen.model.Room;

public interface IRoomService {
	public List<Room> selectAll(Integer pageIndex, Integer pageSize) throws Exception;
	public int selectCount();
	public int deleteRoomById(Integer id);
}
