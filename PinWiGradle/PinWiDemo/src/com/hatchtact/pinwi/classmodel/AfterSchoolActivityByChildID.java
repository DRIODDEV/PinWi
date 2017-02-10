package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AfterSchoolActivityByChildID implements Serializable
{	
	private int ActivityID;
	private String ActivityName=null;
	private String Remarks=null;
	private String ExamDate=null;
	private String StartDate=null;
	private String StartTime=null;
	private String EndTime=null;
	private String Enddate=null;
	private int DayID;
	private boolean IsSpecial=false;
	private boolean IsPrivate=false;
	private String SpecialDate=null;
	private boolean IsVerified=false;
	public boolean isIsVerified() {
		return IsVerified;
	}
	public void setIsVerified(boolean isVerified) {
		IsVerified = isVerified;
	}
	private String daysValue=null;
	
	public String getDaysValue() {
		return daysValue;
	}
	public void setDaysValue(String daysValue) {
		this.daysValue = daysValue;
	}
	public int getActivityID() {
		return ActivityID;
	}
	public void setActivityID(int activityID) {
		ActivityID = activityID;
	}
	public String getActivityName() {
		return ActivityName;
	}
	public void setActivityName(String activityName) {
		ActivityName = activityName;
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
	public String getStartDate() {
		return StartDate;
	}
	public void setStartDate(String startDate) {
		StartDate = startDate;
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
	public String getEnddate() {
		return Enddate;
	}
	public void setEnddate(String enddate) {
		Enddate = enddate;
	}
	public int getDayID() {
		return DayID;
	}
	public void setDayID(int dayID) {
		DayID = dayID;
	}
	public boolean isIsSpecial() {
		return IsSpecial;
	}
	public void setIsSpecial(boolean isSpecial) {
		IsSpecial = isSpecial;
	}
	public boolean isIsPrivate() {
		return IsPrivate;
	}
	public void setIsPrivate(boolean isPrivate) {
		IsPrivate = isPrivate;
	}
	public String getSpecialDate() {
		return SpecialDate;
	}
	public void setSpecialDate(String specialDate) {
		SpecialDate = specialDate;
	}
}
