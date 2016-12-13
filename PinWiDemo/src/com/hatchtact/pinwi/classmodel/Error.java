package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Error implements Serializable
{
	private String ErrorDesc="";
	private String ErrorCode="";
	public String getErrorDesc() {
		return ErrorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		ErrorDesc = errorDesc;
	}
	public String getErrorCode() {
		return ErrorCode;
	}
	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}
}
