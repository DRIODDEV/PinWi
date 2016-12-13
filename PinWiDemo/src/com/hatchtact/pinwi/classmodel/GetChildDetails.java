package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetChildDetails implements Serializable
{
	private String ProfileImage = "";
	private String FirstName = "";
	private String LastName = "";
	private String NickName = "";
	private String DateOfBirth = "";
	private String Gender = "";
	private String  SchoolID= "";    
	private String SchoolName = "";
	private int AutolockTime = 0;
	private String TimeValue = "";
	private String Passcode="";
	
	public String getPasscode() {
		return Passcode;
	}
	public void setPasscode(String passcode) {
		Passcode = passcode;
	}
	public String getProfileImage() {
		return ProfileImage;
	}
	public void setProfileImage(String profileImage) {
		ProfileImage = profileImage;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getNickName() {
		return NickName;
	}
	public void setNickName(String nickName) {
		NickName = nickName;
	}
	public String getDateOfBirth() {
		return DateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	public String getSchoolID() {
		return SchoolID;
	}
	public void setSchoolID(String schoolID) {
		SchoolID = schoolID;
	}
	public String getSchoolName() {
		return SchoolName;
	}
	public void setSchoolName(String schoolName) {
		SchoolName = schoolName;
	}
	public int getAutolockTime() {
		return AutolockTime;
	}
	public void setAutolockTime(int autolockTime) {
		AutolockTime = autolockTime;
	}
	public String getTimeValue() {
		return TimeValue;
	}
	public void setTimeValue(String timeValue) {
		TimeValue = timeValue;
	}
	
}
