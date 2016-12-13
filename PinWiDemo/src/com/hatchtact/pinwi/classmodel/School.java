package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class School implements Serializable
{
	private int SchoolID;
	private String SchoolName="";
	
	public int getSchoolID() {
		return SchoolID;
	}
	public void setSchoolID(int schoolID) {
		SchoolID = schoolID;
	}
	public String getSchoolName() {
		return SchoolName;
	}
	public void setSchoolName(String schoolName) {
		SchoolName = schoolName;
	}
}
