package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RecoverPasscode implements Serializable
{
	private String wsid="";
	private String wspwd="";
	private int ProfileID;

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

	public int getProfileID() {
		return ProfileID;
	}

	public void setProfileID(int profileID) {
		ProfileID = profileID;
	}
}
