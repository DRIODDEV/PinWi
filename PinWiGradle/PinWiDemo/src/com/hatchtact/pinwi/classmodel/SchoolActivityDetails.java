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
	private int FMode;
	private String EndDate;
	private int SemsterID;
	private String StartDate;
	private int BWFMode;
	private String SemesterMode;

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

	public int getFMode() {
		return FMode;
	}

	public void setFMode(int FMode) {
		this.FMode = FMode;
	}

	public String getEndDate() {
		return EndDate;
	}

	public void setEndDate(String endDate) {
		EndDate = endDate;
	}

	public int getSemsterID() {
		return SemsterID;
	}

	public void setSemsterID(int semsterID) {
		SemsterID = semsterID;
	}

	public String getStartDate() {
		return StartDate;
	}

	public void setStartDate(String startDate) {
		StartDate = startDate;
	}

	public int getBWFMode() {
		return BWFMode;
	}

	public void setBWFMode(int BWFMode) {
		this.BWFMode = BWFMode;
	}

	public String getSemesterMode() {
		return SemesterMode;
	}

	public void setSemesterMode(String semesterMode) {
		SemesterMode = semesterMode;
	}
}
