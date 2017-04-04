package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetParentDetails implements Serializable
{
	private String ProfileImage="";
	private String FirstName="";
	private String LastName="";
	private String Password="";
	private String EmailAddress="";
	private String DateOfBirth="";
	private String Relation="";
	private String Gender="";
	private String Contact="";
	private String Passcode="";
	private int AutolockTime;
	private String TimeValue="";
	
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
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getEmailAddress() {
		return EmailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		EmailAddress = emailAddress;
	}
	public String getDateOfBirth() {
		return DateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}
	public String getRelation() {
		return Relation;
	}
	public void setRelation(String relation) {
		Relation = relation;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	public String getContact() {
		return Contact;
	}
	public void setContact(String contact) {
		Contact = contact;
	}
	public String getPasscode() {
		return Passcode;
	}
	public void setPasscode(String passcode) {
		Passcode = passcode;
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

