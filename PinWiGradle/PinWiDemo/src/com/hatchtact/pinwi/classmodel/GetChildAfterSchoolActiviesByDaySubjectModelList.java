package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetChildAfterSchoolActiviesByDaySubjectModelList implements Serializable {


	private ArrayList<GetChildAfterSchoolActiviesByDaySubjectModel> listOfAfterSubjects=new ArrayList<GetChildAfterSchoolActiviesByDaySubjectModel>();

	public ArrayList<GetChildAfterSchoolActiviesByDaySubjectModel> getListOfAfterSchoolSubjects() {
		return listOfAfterSubjects;
	}

	public void setListOfAllys(ArrayList<GetChildAfterSchoolActiviesByDaySubjectModel> listOfAfterSchoolSubjects) {
		this.listOfAfterSubjects = listOfAfterSchoolSubjects;
	}	

}
