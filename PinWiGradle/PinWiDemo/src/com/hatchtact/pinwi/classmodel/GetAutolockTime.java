package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetAutolockTime implements Serializable
{
	private int AutolockID;
	private String TimeValue="";
	
	public int getAutolockID() {
		return AutolockID;
	}
	public void setAutolockID(int autolockID) {
		AutolockID = autolockID;
	}
	public String getTimeValue() {
		return TimeValue;
	}
	public void setTimeValue(String timeValue) {
		TimeValue = timeValue;
	}
}
