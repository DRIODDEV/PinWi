package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetPastDaysRatingStatusModelList implements Serializable {


	private ArrayList<GetPastDaysRatingStatusModel> listOfPastDays=new ArrayList<GetPastDaysRatingStatusModel>();

	public ArrayList<GetPastDaysRatingStatusModel> getListOfSchoolSubjects() {
		return listOfPastDays;
	}

	public void setListOfSubjects(ArrayList<GetPastDaysRatingStatusModel> listOfPastDays) {
		this.listOfPastDays = listOfPastDays;
	}	

}
