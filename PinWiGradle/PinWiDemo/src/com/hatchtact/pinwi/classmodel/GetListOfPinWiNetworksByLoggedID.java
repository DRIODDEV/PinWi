package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

public class GetListOfPinWiNetworksByLoggedID implements Serializable 
{

	public String getChildID() {
		return ChildID;
	}
	public void setChildID(String childID) {
		ChildID = childID;
	}
	public String getProfileImage() {
		return ProfileImage;
	}
	public void setProfileImage(String profileImage) {
		ProfileImage = profileImage;
	}
	public String getChildName() {
		return ChildName;
	}
	public void setChildName(String childName) {
		ChildName = childName;
	}
	public String getAge() {
		return Age;
	}
	public void setAge(String age) {
		Age = age;
	}
	public String getDateOfBirth() {
		return DateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}
	public String getParentName() {
		return ParentName;
	}
	public void setParentName(String parentName) {
		ParentName = parentName;
	}
	public String getLoggedUserName() {
		return LoggedUserName;
	}
	public void setLoggedUserName(String loggedUserName) {
		LoggedUserName = loggedUserName;
	}
	private String ChildID="";
	private String ProfileImage="";
	private String ChildName="";
	private String Age="";
	private String DateOfBirth="";
	private String ParentName="";
	private String LoggedUserName="";

}
