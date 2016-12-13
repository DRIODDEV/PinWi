package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SubjectActivities implements Serializable
{
	private int SubjectID;
	private String SubjectName="";

	public int getSubjectID() {
		return SubjectID;
	}
	public void setSubjectID(int subjectID) {
		SubjectID = subjectID;
	}
	public String getSubjectName() {
		return SubjectName;
	}
	public void setSubjectName(String subjectName) {
		SubjectName = subjectName;
	}
}
