package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AddAfterSchoolActivities implements Serializable
{
	private String wsid="";
	private String wspwd="";
	private int ActivityID;
	private int ChildID;
	private String Remarks="";
	private String ExamDate="";
	private String StartDate="";
	private String StartTime="";
	private String EndTime="";
	private String Enddate="";
	private String ActivityDays="";   
	private int fMode=0;/* 0 == all days 1 == Weekly 2 == Bi Weekly */
	private int BWFMode=0;/* 1 == this week 2 == next week */
	private String IsSpecial="";
	private String IsPrivate="";
	private String SpecialDate="";
	private int isUpdate;
	private String allyId1;
	private String allyId2;
	private String allyName1;
	private String allyName2;
	private int allyIndex1;
	private int allyIndex2;

	public int getAllyIndex1() {
		return allyIndex1;
	}
	public void setAllyIndex1(int allyIndex1) {
		this.allyIndex1 = allyIndex1;
	}
	public int getAllyIndex2() {
		return allyIndex2;
	}
	public void setAllyIndex2(int allyIndex2) {
		this.allyIndex2 = allyIndex2;
	}

	public String getAllyName1() {
		return allyName1;
	}
	public void setAllyName1(String allyName1) {
		this.allyName1 = allyName1;
	}
	public String getAllyName2() {
		return allyName2;
	}
	public void setAllyName2(String allyName2) {
		this.allyName2 = allyName2;
	}
	public String getAllyId1() {
		return allyId1;
	}
	public void setAllyId1(String allyId1) {
		this.allyId1 = allyId1;
	}
	public String getAllyId2() {
		return allyId2;
	}
	public void setAllyId2(String allyId2) {
		this.allyId2 = allyId2;
	}
	public int getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(int isUpdate) {
		this.isUpdate = isUpdate;
	}
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
	public String getActivityDays() {
		return ActivityDays;
	}
	public void setActivityDays(String activityDays) {
		ActivityDays = activityDays;
	}
	public String getIsSpecial() {
		return IsSpecial;
	}
	public void setIsSpecial(String isSpecial) {
		IsSpecial = isSpecial;
	}
	public String getIsPrivate() {
		return IsPrivate;
	}
	public void setIsPrivate(String isPrivate) {
		IsPrivate = isPrivate;
	}
	public String getSpecialDate() {
		return SpecialDate;
	}
	public void setSpecialDate(String specialDate) {
		SpecialDate = specialDate;
	}
	public int getfMode() {
		return fMode;
	}
	public void setfMode(int fMode) {
		this.fMode = fMode;
	}
	public int getBWFMode() {
		return BWFMode;
	}
	public void setBWFMode(int bWFMode) {
		BWFMode = bWFMode;
	}
}
