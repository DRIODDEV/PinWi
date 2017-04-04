package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetNotificationListByParentID implements Serializable
{
	private int NotificationID;
	private String Description;
	private String Time;
	private int read=0;
	private int Status=0;
	private String Date;
	public int getNotificationID() {
		return NotificationID;
	}
	public void setNotificationID(int notificationID) {
		NotificationID = notificationID;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}


}
