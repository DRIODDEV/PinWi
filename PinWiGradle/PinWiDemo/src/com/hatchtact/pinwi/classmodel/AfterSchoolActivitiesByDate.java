package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AfterSchoolActivitiesByDate implements Serializable
{	
	private String ActivityID=null;
	private String Name=null;
	private String StartTime=null;
	private String EndTime=null;
	private String DayID=null;
	
	private String IsPrivate="False";
	private String IsSpecial="False";
	private String SpecialDate="";
	private String IsVerified="False";

	public String getIsPrivate() {
		return IsPrivate;
	}
	public void setIsPrivate(String isPrivate) {
		IsPrivate = isPrivate;
	}
	public String getIsSpecial() {
		return IsSpecial;
	}
	public void setIsSpecial(String isSpecial) {
		IsSpecial = isSpecial;
	}
	public String getSpecialDate() {
		return SpecialDate;
	}
	public void setSpecialDate(String specialDate) {
		SpecialDate = specialDate;
	}
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
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	public String getDayID() {
		return DayID;
	}
	public void setDayID(String dayID) {
		DayID = dayID;
	}
}
