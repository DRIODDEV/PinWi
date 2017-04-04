package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AddSubjectActivity implements Serializable
{

	private String wsid="";
	private String wspwd="";
	private int ActivityID;
	private int ChildID;
	private String ActivityDays="";
	private String Remarks="";
	private String ExamDate="";
	private String StartDate="";
	private String EndDate="";
	private int FMode=0;/* 0 == all days 1 == Weekly 2 == Bi Weekly */
	private int BWFMode=0;/* 1 == this week 2 == next week */
	private int SemesterMode=0;//0:current sem,1:set new dates,2:only for this activity


	public String getWsid() {
		return wsid;
	}
	public void setWsid(String wsid) {
		this.wsid = wsid;
	}
	public String getWspwd() {
		return wspwd;
	}
	public void setWspwd(String wspwd) {
		this.wspwd = wspwd;
	}
	public int getActivityID() {
		return ActivityID;
	}
	public void setActivityID(int activityID) {
		ActivityID = activityID;
	}
	public int getChildID() {
		return ChildID;
	}
	public void setChildID(int childID) {
		ChildID = childID;
	}
	public String getActivityDays() {
		return ActivityDays;
	}
	public void setActivityDays(String activityDays) {
		ActivityDays = activityDays;
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

	public String getEndDate() {
		return EndDate;
	}

	public void setEndDate(String endDate) {
		EndDate = endDate;
	}

	public int getFMode() {
		return FMode;
	}

	public void setFMode(int FMode) {
		this.FMode = FMode;
	}

	public int getBWFMode() {
		return BWFMode;
	}

	public void setBWFMode(int BWFMode) {
		this.BWFMode = BWFMode;
	}

	public int getSemesterMode() {
		return SemesterMode;
	}

	public void setSemesterMode(int semesterMode) {
		SemesterMode = semesterMode;
	}
}
