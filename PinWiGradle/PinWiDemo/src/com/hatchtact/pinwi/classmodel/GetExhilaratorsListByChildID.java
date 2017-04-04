package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

public class GetExhilaratorsListByChildID implements Serializable 
{
	private int InterestTraitID;
	private String Name;
	private String Description="";
	private String ChildName="";
	private String LogedUserName="";
	
	
	public int getInterestTraitID() {
		return InterestTraitID;
	}
	public void setInterestTraitID(int interestTraitID) {
		InterestTraitID = interestTraitID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getChildName() {
		return ChildName;
	}
	public void setChildName(String childName) {
		ChildName = childName;
	}
	public String getLogedUserName() {
		return LogedUserName;
	}
	public void setLogedUserName(String logedUserName) {
		LogedUserName = logedUserName;
	}
}
