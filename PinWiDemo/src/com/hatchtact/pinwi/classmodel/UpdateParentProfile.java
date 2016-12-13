package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UpdateParentProfile implements Serializable
{
	private String wsid="";
	private String wspwd="";
	private int ParentID;
	private String ProfileImage="";
	private String FirstName="";
	private String LastName="";
	private String Password="";
	private String Relation="";
	private String Contact="";
	private String Passcode="";
	private String AutolockTime="";
	private String DateOfBirth="";
	public String getWsid() {
		return wsid;
	}
	public void setWsid(String wsid) {
		this.wsid = wsid;
	}
	public String getWspwd() {
		return wspwd;
	}
	public void setWspwd(String wspwd) {
		this.wspwd = wspwd;
	}
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
	public String getRelation() {
		return Relation;
	}
	public void setRelation(String relation) {
		Relation = relation;
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
	public String getAutolockTime() {
		return AutolockTime;
	}
	public void setAutolockTime(String autolockTime) {
		AutolockTime = autolockTime;
	}
	public String getDateOfBirth() {
		return DateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}
}
