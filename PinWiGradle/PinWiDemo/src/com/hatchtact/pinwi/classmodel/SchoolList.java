package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class SchoolList implements Serializable
{
	private ArrayList<School> schoolList=new ArrayList<School>();

	public ArrayList<School> getSchoolList() {
		return schoolList;
	}

	public void setSchoolList(ArrayList<School> schoolList) {
		this.schoolList = schoolList;
	}

	
}
