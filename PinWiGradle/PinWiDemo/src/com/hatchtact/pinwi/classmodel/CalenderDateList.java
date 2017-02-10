package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class CalenderDateList implements Serializable
{
	ArrayList<CalenderDate> calenderDate=new ArrayList<CalenderDate>();

	public ArrayList<CalenderDate> getCalenderDate() {
		return calenderDate;
	}

	public void setCalenderDate(ArrayList<CalenderDate> calenderDate) {
		this.calenderDate = calenderDate;
	}
}
