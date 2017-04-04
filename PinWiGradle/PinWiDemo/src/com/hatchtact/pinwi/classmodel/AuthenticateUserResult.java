package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AuthenticateUserResult implements Serializable
{
	private int ProfileID;
	private String FirstName;
	private String LastName;
	
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	private int ProfileStatus;
	private int PaymentStatus;

	public int getProfileStatus() {
		return ProfileStatus;
	}
	public void setProfileStatus(int profileStatus) {
		ProfileStatus = profileStatus;
	}
	public int getProfileID() {
		return ProfileID;
	}
	public void setProfileID(int profileID) {
		ProfileID = profileID;
	}
	
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public int getPaymentStatus() {
		return PaymentStatus;
	}
}
