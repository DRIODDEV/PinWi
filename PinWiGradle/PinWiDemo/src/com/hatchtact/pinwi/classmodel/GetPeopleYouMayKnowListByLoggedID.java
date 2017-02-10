package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

public class GetPeopleYouMayKnowListByLoggedID implements Serializable

{
	
	
	private int ParentID;
	private String ProfileImage="";
	private String ParentName="";
	private String ChildName="";
	private String FStatus;
	private String LoggedUserName="";
	public int getFriendID() {
		return ParentID;
	}
	public void setFriendID(int friendID) {
		ParentID = friendID;
	}
	public String getProfileImage() {
		return ProfileImage;
	}
	public void setProfileImage(String profileImage) {
		ProfileImage = profileImage;
	}
	public String getFriendName() {
		return ParentName;
	}
	public void setFriendName(String friendName) {
		ParentName = friendName;
	}
	public String getChildName() {
		return ChildName;
	}
	public void setChildName(String childName) {
		ChildName = childName;
	}
	public String getFStatus() {
		return FStatus;
	}
	public void setFStatus(String fStatus) {
		FStatus = fStatus;
	}
	public String getLoggedUserName() {
		return LoggedUserName;
	}
	public void setLoggedUserName(String loggedUserName) {
		LoggedUserName = loggedUserName;
	}
}
