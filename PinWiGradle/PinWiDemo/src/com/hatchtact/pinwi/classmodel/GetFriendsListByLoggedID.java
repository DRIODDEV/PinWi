package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

public class GetFriendsListByLoggedID implements Serializable

{
	
	private String FriendID;
	private String ProfileImage="";
	private String FriendName="";
	private String ChildName="";
	private String FStatus;
	private String LoggedUserName="";
	public String getFriendID() {
		return FriendID;
	}
	public void setFriendID(String friendID) {
		FriendID = friendID;
	}
	public String getProfileImage() {
		return ProfileImage;
	}
	public void setProfileImage(String profileImage) {
		ProfileImage = profileImage;
	}
	public String getFriendName() {
		return FriendName;
	}
	public void setFriendName(String friendName) {
		FriendName = friendName;
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
