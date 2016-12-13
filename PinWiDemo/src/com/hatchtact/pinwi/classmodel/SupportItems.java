package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SupportItems implements Serializable
{
	private String supportText=null;
	private String supportDescription=null;
	
	public String getSupportText() {
		return supportText;
	}
	public void setSupportText(String supportText) {
		this.supportText = supportText;
	}
	public String getSupportDescription() {
		return supportDescription;
	}
	public void setSupportDescription(String supportDescription) {
		this.supportDescription = supportDescription;
	}
}
