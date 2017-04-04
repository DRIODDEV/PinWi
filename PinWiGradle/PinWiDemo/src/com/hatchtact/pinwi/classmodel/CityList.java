package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class CityList implements Serializable
{
		private ArrayList<City> city=new ArrayList<City>();

		public ArrayList<City> getCity() {
			return city;
		}

		public void setCity(ArrayList<City> city) {
			this.city = city;
		}
}
