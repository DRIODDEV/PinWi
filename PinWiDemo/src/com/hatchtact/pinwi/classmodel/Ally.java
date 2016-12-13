package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Ally implements Serializable
{
	private int AllyTypeID;
	private String AllyTypeName="";
	
	public int getAllyTypeID() {
		return AllyTypeID;
	}
	public void setAllyTypeID(int allyTypeID) {
		AllyTypeID = allyTypeID;
	}
	public String getAllyTypeName() {
		return AllyTypeName;
	}
	public void setAllyTypeName(String allyTypeName) {
		AllyTypeName = allyTypeName;
	}
}
