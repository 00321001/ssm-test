package com.javen.model;

public class User {
	private Integer id;
	private String userName;
	private String password;
	private String phoneNumber;
	private String idCard;
	private String bookedRoom;
	private String havaTime;
	private String roomTime;
	private String endTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getBookedRoom() {
		return bookedRoom;
	}
	public void setBookedRoom(String bookedRoom) {
		this.bookedRoom = bookedRoom;
	}
	public String getHavaTime() {
		return havaTime;
	}
	public void setHavaTime(String havaTime) {
		this.havaTime = havaTime;
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
	
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", phoneNumber=" + phoneNumber
				+ ", idCard=" + idCard + ", bookedRoom=" + bookedRoom + ", havaTime=" + havaTime + ", roomTime="
				+ roomTime + ", endTime=" + endTime + "]";
	}
	
}
