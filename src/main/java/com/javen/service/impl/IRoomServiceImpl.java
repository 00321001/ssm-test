package com.javen.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.javen.dao.RoomDao;
import com.javen.model.Room;
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
}
