package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class LocalityList implements Serializable
{
		private ArrayList<Locality> locality=new ArrayList<Locality>();

		public ArrayList<Locality> getLocality() {
			return locality;
		}

		public void setLocality(ArrayList<Locality> locality) {
			this.locality = locality;
		}

	
}
