package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetChildSubjectActiviesByDaySubjectModelList implements Serializable {


	private ArrayList<GetChildSubjectActiviesByDaySubjectModel> listOfSubjects=new ArrayList<GetChildSubjectActiviesByDaySubjectModel>();

	public ArrayList<GetChildSubjectActiviesByDaySubjectModel> getListOfSchoolSubjects() {
		return listOfSubjects;
	}

	public void setListOfSubjects(ArrayList<GetChildSubjectActiviesByDaySubjectModel> listOfAfterSchoolSubjects) {
		this.listOfSubjects = listOfAfterSchoolSubjects;
	}	

}
