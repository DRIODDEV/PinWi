package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SendConfirmationCodeToMail implements Serializable
{
	private String wsid="";
	private String wspwd="";
	private String EmailAddress="";
	//private String Contact="";

	private int ParentID;

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
	public String getEmailAddress() {
		return EmailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		EmailAddress = emailAddress;
	}
	public Integer getParentID() {
		return ParentID;
	}
	public void setParentID(Integer parentID) {
		ParentID = parentID;
	}
	/*public String getContact() {
		return Contact;
	}
	public void setContact(String contact) {
		Contact = contact;
	}*/
}
