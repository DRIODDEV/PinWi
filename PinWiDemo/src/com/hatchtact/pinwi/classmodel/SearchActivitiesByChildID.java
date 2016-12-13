package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

public class SearchActivitiesByChildID implements Serializable

{
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
	public int getChildrenDoingThis() {
		return ChildrenDoingThis;
	}
	public void setChildrenDoingThis(int childrenDoingThis) {
		ChildrenDoingThis = childrenDoingThis;
	}
	public int getIsWished() {
		return IsWished;
	}
	public void setIsWished(int isWished) {
		IsWished = isWished;
	}
	public int getRowNumbers() {
		return RowNumbers;
	}
	public void setRowNumbers(int rowNumbers) {
		RowNumbers = rowNumbers;
	}
	public int getIsScheduled() {
		return IsScheduled;
	}
	public void setIsScheduled(int isScheduled) {
		IsScheduled = isScheduled;
	}
	private int ActivityID;
	private String ActivityName="";
	private int ChildrenDoingThis;
	private int IsWished;
	private int RowNumbers;
	private int IsScheduled;
	
}
