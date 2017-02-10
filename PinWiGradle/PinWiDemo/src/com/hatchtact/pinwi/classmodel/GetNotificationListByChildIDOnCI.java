package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetNotificationListByChildIDOnCI implements Serializable 
{
	public String getNotificationID() {
		return NotificationID;
	}
	public void setNotificationID(String notificationID) {
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
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public String getChildName() {
		return ChildName;
	}
	public void setChildName(String childName) {
		ChildName = childName;
	}
	public String getProfileImage() {
		return ProfileImage;
	}
	public void setProfileImage(String profileImage) {
		ProfileImage = profileImage;
	}
	private String NotificationID;
	private String Description;
	private String Time;
	private int Status;
	private String ChildName;
	private String ProfileImage;


}
