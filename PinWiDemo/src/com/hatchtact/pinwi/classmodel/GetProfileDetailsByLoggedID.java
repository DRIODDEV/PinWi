package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

public class GetProfileDetailsByLoggedID implements Serializable 
{
			private String ProfileID;
			private String ProfileImage="";
			private String LoggedUserName="";
			private String CityName="";
			private String ParentAddress="";
			private String PinWiConnection="";
			
			private String ChildID="";
			private String ChildName="";
			private String Age="";
			private String DateOfBirth="";
			private int SchoolName;
			private String SchoolName1="";
			
			public String getProfileImage() {
				return ProfileImage;
			}
			public void setProfileImage(String profileImage) {
				ProfileImage = profileImage;
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
			public String getProfileID() {
				return ProfileID;
			}
			public void setProfileID(String profileID) {
				ProfileID = profileID;
			}
			public String getParentAddress() {
				return ParentAddress;
			}
			public void setParentAddress(String parentAddress) {
				ParentAddress = parentAddress;
			}
			public String getPinWiConnection() {
				return PinWiConnection;
			}
			public void setPinWiConnection(String pinWiConnection) {
				PinWiConnection = pinWiConnection;
			}
			public String getDateOfBirth() {
				return DateOfBirth;
			}
			public void setDateOfBirth(String dateOfBirth) {
				DateOfBirth = dateOfBirth;
			}
			public int getSchoolName() {
				return SchoolName;
			}
			public void setSchoolName(int schoolName) {
				SchoolName = schoolName;
			}
			public String getSchoolName1() {
				return SchoolName1;
			}
			public void setSchoolName1(String schoolName1) {
				SchoolName1 = schoolName1;
			}
			
			

}
