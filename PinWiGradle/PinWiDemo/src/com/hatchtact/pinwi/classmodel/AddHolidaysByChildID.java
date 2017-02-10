package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AddHolidaysByChildID implements Serializable {

	
	private String HolidayDate;
	private String HolidayDescription;
	public String getHolidayDate() {
		return HolidayDate;
	}
	public void setHolidayDate(String holidayDate) {
		HolidayDate = holidayDate;
	}
	public String getHolidayDescription() {
		return HolidayDescription;
	}
	public void setHolidayDescription(String holidayDescription) {
		HolidayDescription = holidayDescription;
	}
}
