package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

public class SearchFriendListGlobally implements Serializable 
{

     private int ParentID;
     private String ProfileImage="";
     private String ParentName="";
     private String FStatus="";
	public int getParentID() {
		return ParentID;
	}
	public void setParentID(int parentID) {
		ParentID = parentID;
	}
	public String getProfileImage() {
		return ProfileImage;
	}
	public void setProfileImage(String profileImage) {
		ProfileImage = profileImage;
	}
	public String getParentName() {
		return ParentName;
	}
	public void setParentName(String parentName) {
		ParentName = parentName;
	}
	public String getFStatus() {
		return FStatus;
	}
	public void setFStatus(String fStatus) {
		FStatus = fStatus;
	}
 
}
