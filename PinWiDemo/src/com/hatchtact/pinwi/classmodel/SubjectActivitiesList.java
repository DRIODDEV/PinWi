package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class SubjectActivitiesList implements Serializable
{
	private ArrayList<SubjectActivities> subjectActivitiesList=new ArrayList<SubjectActivities>();

	public ArrayList<SubjectActivities> getSubjectActivitiesList() {
		return subjectActivitiesList;
	}

	public void setSubjectActivitiesList(
			ArrayList<SubjectActivities> subjectActivitiesList) {
		this.subjectActivitiesList = subjectActivitiesList;
	}
}
