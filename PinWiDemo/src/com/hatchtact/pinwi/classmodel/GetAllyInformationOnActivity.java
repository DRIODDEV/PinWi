package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetAllyInformationOnActivity implements Serializable
{
	private String ProfileImage=null;
	private String FirstName=null;
	private String Date=null;
	private String Time=null;
	private String PickUp=null;
	private String Drop=null;
	private String SpeicalInstructions=null;
	
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
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getPickUp() {
		return PickUp;
	}
	public void setPickUp(String pickUp) {
		PickUp = pickUp;
	}
	public String getDrop() {
		return Drop;
	}
	public void setDrop(String drop) {
		Drop = drop;
	}
	public String getSpeicalInstructions() {
		return SpeicalInstructions;
	}
	public void setSpeicalInstructions(String speicalInstructions) {
		SpeicalInstructions = speicalInstructions;
	}
}
