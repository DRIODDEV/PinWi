package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetPastDaysRatingStatusModel implements Serializable
{
	private String ActivityDate="";
	private String DaysAgo="";
	private String Status="";
	private String PointsValue="";
	private String RemaningTime="";
	
	public String getActivityDate() {
		return ActivityDate;
	}
	public void setActivityDate(String activityDate) {
		ActivityDate = activityDate;
	}
	public String getDaysAgo() {
		return DaysAgo;
	}
	public void setDaysAgo(String daysAgo) {
		DaysAgo = daysAgo;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getPointsValue() {
		return PointsValue;
	}
	public void setPointsValue(String pointsValue) {
		PointsValue = pointsValue;
	}
	public String getRemaningTime() {
		return RemaningTime;
	}
	public void setRemaningTime(String remaningTime) {
		RemaningTime = remaningTime;
	}
	
}
