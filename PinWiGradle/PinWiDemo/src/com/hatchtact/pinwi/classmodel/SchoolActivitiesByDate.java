package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SchoolActivitiesByDate implements Serializable
{	
	private String ActivityID=null;
	private String Name=null;
	private String DayID=null;
	private String IsVerified="False";
	
	public String getIsVerified() {
		return IsVerified;
	}
	public void setIsVerified(String isVerified) {
		IsVerified = isVerified;
	}
	public String getActivityID() {
		return ActivityID;
	}
	public void setActivityID(String activityID) {
		ActivityID = activityID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDayID() {
		return DayID;
	}
	public void setDayID(String dayID) {
		DayID = dayID;
	}
}
