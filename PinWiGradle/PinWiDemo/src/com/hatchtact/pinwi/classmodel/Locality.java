package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Locality implements Serializable
{
	private int LocalityID;
	private String LocalityName="";
	private int CityID;
	public int getLocalityID() {
		return LocalityID;
	}
	public void setLocalityID(int localityID) {
		LocalityID = localityID;
	}
	public String getLocalityName() {
		return LocalityName;
	}
	public void setLocalityName(String localityName) {
		LocalityName = localityName;
	}
	public int getCityID() {
		return CityID;
	}
	public void setCityID(int cityID) {
		CityID = cityID;
	}
	
	
}
