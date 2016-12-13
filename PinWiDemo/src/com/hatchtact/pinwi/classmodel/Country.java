package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Country implements Serializable
{
	private int CountryID;
	private String CountryName="";
	
	public int getCountryID() {
		return CountryID;
	}
	public void setCountryID(int countryID) {
		CountryID = countryID;
	}
	public String getCountryName() {
		return CountryName;
	}
	public void setCountryName(String countryName) {
		CountryName = countryName;
	}
}
