package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ListOfAllys implements Serializable
{	
	private String AllyName=null;
	private String AllyProfileImage=null;
	private String Relationship=null;
	private int AllyProfileID;
	
	public String getAllyName() {
		return AllyName;
	}
	public void setAllyName(String allyName) {
		AllyName = allyName;
	}
	public String getAllyProfileImage() {
		return AllyProfileImage;
	}
	public void setAllyProfileImage(String allyProfileImage) {
		AllyProfileImage = allyProfileImage;
	}
	public String getRelationship() {
		return Relationship;
	}
	public void setRelationship(String relationship) {
		Relationship = relationship;
	}
	public int getAllyProfileID() {
		return AllyProfileID;
	}
	public void setAllyProfileID(int allyProfileID) {
		AllyProfileID = allyProfileID;
	}
}
