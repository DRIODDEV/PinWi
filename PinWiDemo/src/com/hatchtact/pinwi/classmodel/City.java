package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class City implements Serializable
{
	private int CityID;
	private String CityName="";
	
	public int getCityID() {
		return CityID;
	}
	public void setCityID(int cityID) {
		CityID = cityID;
	}
	public String getCityName() {
		return CityName;
	}
	public void setCityName(String cityName) {
		CityName = cityName;
	}
}
