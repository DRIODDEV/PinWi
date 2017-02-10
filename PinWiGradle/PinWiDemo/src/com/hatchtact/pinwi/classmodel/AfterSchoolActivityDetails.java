package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AfterSchoolActivityDetails implements Serializable
{	
	private String ActivityAllyID;
	private String ActivityID=null;
	private String Name=null;
	private String StartDate=null;
	private String EndDate=null;
	private String StartTime=null;
	private String EndTime=null;
	private String DayMode=null;
	private String DayID=null;
	private String IsSpecial=null;
	private String IsPrivate=null;
	private String SpecialDate=null;
	private String AllyID1=null;
	private String Ally1FirstName=null;
	private String AllyID2=null;
	private String Ally2FirstName=null;
	
	private int Ally1Index;
	private int Ally2Index;
	private String Remarks;
	private int FMode=0;/* 0 == all days 1 == Weekly 2 == Bi Weekly */
	private int BWFMode=0;/* 1 == this week 2 == next week */

	public String getActivityAllyID() {
		return ActivityAllyID;
	}
	public void setActivityAllyID(String activityAllyID) {
		ActivityAllyID = activityAllyID;
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
	public String getDayMode() {
		return DayMode;
	}
	public void setDayMode(String dayMode) {
		DayMode = dayMode;
	}
	public String getDayID() {
		return DayID;
	}
	public void setDayID(String dayID) {
		DayID = dayID;
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
	public String getAllyID1() {
		return AllyID1;
	}
	public void setAllyID1(String allyID1) {
		AllyID1 = allyID1;
	}
	public String getAlly1FirstName() {
		return Ally1FirstName;
	}
	public void setAlly1FirstName(String ally1FirstName) {
		Ally1FirstName = ally1FirstName;
	}
	public String getAllyID2() {
		return AllyID2;
	}
	public void setAllyID2(String allyID2) {
		AllyID2 = allyID2;
	}
	public String getAlly2FirstName() {
		return Ally2FirstName;
	}
	public void setAlly2FirstName(String ally2FirstName) {
		Ally2FirstName = ally2FirstName;
	}
	public int getAlly1Index() {
		return Ally1Index;
	}
	public void setAlly1Index(int ally1Index) {
		Ally1Index = ally1Index;
	}
	public int getAlly2Index() {
		return Ally2Index;
	}
	public void setAlly2Index(int ally2Index) {
		Ally2Index = ally2Index;
	}
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	public int getfMode() {
		return FMode;
	}
	public void setfMode(int fMode) {
		this.FMode = fMode;
	}
	public int getBWFMode() {
		return BWFMode;
	}
	public void setBWFMode(int bWFMode) {
		BWFMode = bWFMode;
	}
}
