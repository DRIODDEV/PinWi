package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

public class GetFriendDetailsByFriendID implements Serializable 
{
	private String FriendID;
	private String ProfileImage="";
	private String FriendName="";
	private String CityName="";
	private String FStatus="";
	private String LoggedUserName="";
	private String ChildID="";
	private String ChildName="";
	private String Age="";
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
	public String getCityName() {
		return CityName;
	}
	public void setCityName(String cityName) {
		CityName = cityName;
	}
	public String getLoggedUserName() {
		return LoggedUserName;
	}
	public void setLoggedUserName(String loggedUserName) {
		LoggedUserName = loggedUserName;
	}
	public String getChildID() {
		return ChildID;
	}
	public void setChildID(String childID) {
		ChildID = childID;
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
	public String getFStatus() {
		return FStatus;
	}
	public void setFStatus(String fStatus) {
		FStatus = fStatus;
	}
	
}
