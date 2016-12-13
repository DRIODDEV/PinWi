package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AccessProfile implements Serializable
{
	private int ProfileID;
	private int ProfileType;
	private String ProfileImage="";
	private String FirstName="";
	private int EarnedPoints;
	private int PendingPoints;
	private String Passcode;
	public int getProfileID() {
		return ProfileID;
	}
	public void setProfileID(int profileID) {
		ProfileID = profileID;
	}
	public int getProfileType() {
		return ProfileType;
	}
	public void setProfileType(int profileType) {
		ProfileType = profileType;
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
	
	public int getEarnedPoints() {
		return EarnedPoints;
	}
	public void setEarnedPoints(int earnedPoints) {
		EarnedPoints = earnedPoints;
	}
	public int getPendingPoints() {
		return PendingPoints;
	}
	public void setPendingPoints(int pendingPoints) {
		PendingPoints = pendingPoints;
	}
	public String getPasscode() {
		return Passcode;
	}
	public void setPasscode(String passcode) {
		Passcode = passcode;
	}
}

