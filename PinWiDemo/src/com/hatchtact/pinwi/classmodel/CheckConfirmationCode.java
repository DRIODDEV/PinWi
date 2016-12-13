package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CheckConfirmationCode implements Serializable 
{
	private String wsid="";
	private String wspwd="";
	private int ParentID;
	private String ConfirmationCode="";
	private String EmailAddress="";
	
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
	
	public String getConfirmationCode() {
		return ConfirmationCode;
	}
	public void setConfirmationCode(String confirmationCode) {
		ConfirmationCode = confirmationCode;
	}
	public String getEmailAddress() {
		return EmailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		EmailAddress = emailAddress;
	}
}
