package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SchoolActivityDetails implements Serializable
{
	private String ActivityID=null;
	private String Name=null;
	private String Remarks=null;
	private String ExamDate=null;
	private String DayID=null;
	
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
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	public String getExamDate() {
		return ExamDate;
	}
	public void setExamDate(String examDate) {
		ExamDate = examDate;
	}
	public String getDayID() {
		return DayID;
	}
	public void setDayID(String dayID) {
		DayID = dayID;
	}
}
