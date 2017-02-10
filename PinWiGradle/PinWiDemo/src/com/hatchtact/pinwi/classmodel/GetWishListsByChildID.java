package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetWishListsByChildID implements Serializable

{
	public int getActivityID() {
		return ActivityID;
	}
	public void setActivityID(int activityID) {
		ActivityID = activityID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getChildrenDoingThis() {
		return ChildrenDoingThis;
	}
	public void setChildrenDoingThis(int childrenDoingThis) {
		ChildrenDoingThis = childrenDoingThis;
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
	private String Name="";
	private int ChildrenDoingThis;
	private int RowNumbers;
	private int IsScheduled;
	
}
