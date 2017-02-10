package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

public class GetProfileDetailsByLoggedIDList implements Serializable 
{
	private ArrayList<GetProfileDetailsByLoggedID> getProfileDetailsByLoggedIDList=new ArrayList<GetProfileDetailsByLoggedID>();

	public ArrayList<GetProfileDetailsByLoggedID> getGetProfileDetailsByLoggedIDList() {
		return getProfileDetailsByLoggedIDList;
	}

	public void setGetProfileDetailsByLoggedIDList(
			ArrayList<GetProfileDetailsByLoggedID> getProfileDetailsByLoggedIDList) {
		this.getProfileDetailsByLoggedIDList = getProfileDetailsByLoggedIDList;
	}

}
