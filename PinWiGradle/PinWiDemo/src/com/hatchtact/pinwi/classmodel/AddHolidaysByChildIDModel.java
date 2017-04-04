package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AddHolidaysByChildIDModel implements Serializable {



	private int ChildID;
	private String HolidayDescription;
	private String StartDate;
	private String EndDate;
	public int getChildID() {
		return ChildID;
	}
	public void setChildID(int childID) {
		ChildID = childID;
	}
	public String getHolidayDescription() {
		return HolidayDescription;
	}
	public void setHolidayDescription(String holidayDescription) {
		HolidayDescription = holidayDescription;
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

}
