package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SubjectActivitiesByChildID implements Serializable
{
	private String dayid;
	private int ActivityID;
	private String Name=null;
	private boolean IsVerified=false;
	
	public boolean isIsVerified() {
		return IsVerified;
	}
	public void setIsVerified(boolean isVerified) {
		IsVerified = isVerified;
	}
	public String getDayid() {
		return dayid;
	}
	public void setDayid(String dayid) {
		this.dayid = dayid;
	}
	public int getActivityID() {
		return ActivityID;
	}
	public void setActivityID(int activityID) {
		ActivityID = activityID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
}
