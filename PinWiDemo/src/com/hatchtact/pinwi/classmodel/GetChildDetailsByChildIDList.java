package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetChildDetailsByChildIDList implements Serializable 
{
	private ArrayList<GetChildDetailsByChildID> getChildDetailsByChildIDList=new ArrayList<GetChildDetailsByChildID>();

	public ArrayList<GetChildDetailsByChildID> getChildDetailsByChildIDList() {
		return getChildDetailsByChildIDList;
	}

	public void setGetDelightTraitsByActivity(
			ArrayList<GetChildDetailsByChildID> getChildDetailsByChildID) {
		this.getChildDetailsByChildIDList = getChildDetailsByChildID;
	}

}
