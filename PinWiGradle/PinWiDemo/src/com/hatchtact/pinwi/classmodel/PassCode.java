package com.hatchtact.pinwi.classmodel;

public class PassCode 
{
	private String passCode=null;
	private int passCodeType;
	private int profileId;
	
	public String getPassCode() {
		return passCode;
	}
	public void setPassCode(String passCode) {
		this.passCode = passCode;
	}
	public int getPassCodeType() {
		return passCodeType;
	}
	public void setPassCodeType(int passCodeType) {
		this.passCodeType = passCodeType;
	}
	public int getProfileId() {
		return profileId;
	}
	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}
}
