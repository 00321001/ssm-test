package com.javen.model;

public class Room {
	private Integer id;
	private Integer roomNum;
	private String roomState;
	private String userName;
	private String phoneNumber;
	private String idCard;
	private String roomTime;
	private String endTime;
	private String haveTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}
	public String getRoomState() {
		return roomState;
	}
	public void setRoomState(String roomState) {
		this.roomState = roomState;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getRoomTime() {
		return roomTime;
	}
	public void setRoomTime(String roomTime) {
		this.roomTime = roomTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getHaveTime() {
		return haveTime;
	}
	public void setHaveTime(String haveTime) {
		this.haveTime = haveTime;
	}
	@Override
	public String toString() {
		return "Room [id=" + id + ", roomNum=" + roomNum + ", roomState=" + roomState + ", userName=" + userName
				+ ", phoneNumber=" + phoneNumber + ", idCard=" + idCard + ", roomTime=" + roomTime + ", endTime="
				+ endTime + ", haveTime=" + haveTime + "]";
	}
}
