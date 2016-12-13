package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CalenderDate implements Serializable
{
	private String Day=null;

	public String getDay() {
		return Day;
	}

	public void setDay(String day) {
		Day = day;
	}	
}
