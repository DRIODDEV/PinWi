package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SchedularHolidayModel implements Serializable
{
	private int ChildID;
	private String HolidayDescription="";
	
	
	
	public int getChildId() {
		return ChildID;
	}
	public void setChildId(int ChildID) {
		this.ChildID = ChildID;
	}
	public String getHolidayDescription() {
		return HolidayDescription;
	}
	public void setHolidayDescription(String HolidayDescription) {
		this.HolidayDescription = HolidayDescription;
	}
}
