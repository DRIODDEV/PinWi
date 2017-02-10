package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetHolidaysByChildID implements Serializable {

	private int ChildID;
	private String HolidayDate;
	private int Flag;
	
	public int getChildID() {
		return ChildID;
	}
	public void setChildID(int childID) {
		ChildID = childID;
	}
	public String getHolidayDate() {
		return HolidayDate;
	}
	public void setHolidayDate(String holidayDate) {
		HolidayDate = holidayDate;
	}
	public int getFlag() {
		return Flag;
	}
	public void setFlag(int flag) {
		Flag = flag;
	}
	
}
