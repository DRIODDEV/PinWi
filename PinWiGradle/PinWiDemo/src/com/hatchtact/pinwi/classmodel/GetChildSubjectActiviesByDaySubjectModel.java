package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetChildSubjectActiviesByDaySubjectModel implements Serializable
{
	private int ActivityID;
	private String Name="";
	private int DayID;
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	public int getActivityID() {
		return ActivityID;
	}
	public void setActivityID(int activityID) {
		ActivityID = activityID;
	}
	public int getDayID() {
		return DayID;
	}
	public void setDayID(int dayID) {
		DayID = dayID;
	}



}
