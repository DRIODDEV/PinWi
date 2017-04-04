package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class SearchActivitiesByChildIDList implements Serializable 
{
	private ArrayList<SearchActivitiesByChildID> searchActivitiesByChildID=new ArrayList<SearchActivitiesByChildID>();

	public ArrayList<SearchActivitiesByChildID> getsearchActivitiesByChildID() 
	{
		return searchActivitiesByChildID;
	}

	public void setsearchActivitiesByChildID(ArrayList<SearchActivitiesByChildID> searchActivitiesByChildID) 
	{
		this.searchActivitiesByChildID = searchActivitiesByChildID;
	}

	
}