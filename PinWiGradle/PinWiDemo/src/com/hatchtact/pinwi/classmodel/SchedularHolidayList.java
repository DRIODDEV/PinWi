package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class SchedularHolidayList implements Serializable
{
	private ArrayList<SchedularHolidayModel> holidayList=new ArrayList<SchedularHolidayModel>();

	public ArrayList<SchedularHolidayModel> getHolidaylist() {
		return holidayList;
	}

	public void setHolidayList(ArrayList<SchedularHolidayModel> holidayList) {
		this.holidayList = holidayList;
	}
}
