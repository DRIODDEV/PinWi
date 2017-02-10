package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetListofAllysByParentID implements Serializable
{
	private int AllyID;
	private String FirstName="";

	public int getAllyID() {
		return AllyID;
	}
	public void setAllyID(int allyID) {
		AllyID = allyID;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
}