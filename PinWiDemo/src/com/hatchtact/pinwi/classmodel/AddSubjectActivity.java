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
}
