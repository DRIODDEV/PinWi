package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetDetailByChildIDList implements Serializable
{
	private ArrayList<GetDetailByChildID> getDetailByChildIDList=new ArrayList<GetDetailByChildID>();

	public ArrayList<GetDetailByChildID> getDetailByChildID() {
		return getDetailByChildIDList;
	}

	public void setDetailByChildID(
			ArrayList<GetDetailByChildID> getDetailByChildID) 
	{
		this.getDetailByChildIDList = getDetailByChildID;
	}

	
}
