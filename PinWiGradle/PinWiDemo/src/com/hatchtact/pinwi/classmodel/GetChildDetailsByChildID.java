package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

public class GetChildDetailsByChildID implements Serializable 
{
	private String ChildName;
	private String ChildProfileImage;
	private String ParentName;
	private String Siblings;
	private int Age;
	private String DateOfBirth;
	private String LoggedUserName;
	private String FamilyConnection;
	private String InterestID;
	private String InterestName;
	private String ActivityID;
	private String ActivityName;
	public String getChildName() {
		return ChildName;
	}
	public void setChildName(String childName) {
		ChildName = childName;
	}
	public String getParentName() {
		return ParentName;
	}
	public void setParentName(String parentName) {
		ParentName = parentName;
	}
	public String getSiblings() {
		return Siblings;
	}
	public void setSiblings(String siblings) {
		Siblings = siblings;
	}
	public int getAge() {
		return Age;
	}
	public void setAge(int age) {
		Age = age;
	}
	public String getDateOfBirth() {
		return DateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}
	public String getLoggedUserName() {
		return LoggedUserName;
	}
	public void setLoggedUserName(String loggedUserName) {
		LoggedUserName = loggedUserName;
	}
	public String getFamilyConnection() {
		return FamilyConnection;
	}
	public void setFamilyConnection(String familyConnection) {
		FamilyConnection = familyConnection;
	}
	public String getInterestID() {
		return InterestID;
	}
	public void setInterestID(String interestID) {
		InterestID = interestID;
	}
	public String getInterestName() {
		return InterestName;
	}
	public void setInterestName(String interestName) {
		InterestName = interestName;
	}
	public String getActivityID() {
		return ActivityID;
	}
	public void setActivityID(String activityID) {
		ActivityID = activityID;
	}
	public String getActivityName() {
		return ActivityName;
	}
	public void setActivityName(String activityName) {
		ActivityName = activityName;
	}
	public String getChildProfileImage() {
		return ChildProfileImage;
	}
	public void setChildProfileImage(String childProfileImage) {
		ChildProfileImage = childProfileImage;
	}

}
