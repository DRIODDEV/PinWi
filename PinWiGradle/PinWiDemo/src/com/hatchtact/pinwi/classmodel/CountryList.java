package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class CountryList implements Serializable
{
	private ArrayList<Country> country=new ArrayList<Country>();

	public ArrayList<Country> getCountry() {
		return country;
	}

	public void setCountry(ArrayList<Country> country) {
		this.country = country;
	}
}
