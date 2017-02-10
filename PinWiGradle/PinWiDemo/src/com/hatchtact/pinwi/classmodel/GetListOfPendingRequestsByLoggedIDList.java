package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetListOfPendingRequestsByLoggedIDList implements Serializable 
{
	private ArrayList<GetListOfPendingRequestsByLoggedID> getListOfPendingRequestsByLoggedID=new ArrayList<GetListOfPendingRequestsByLoggedID>();

	public ArrayList<GetListOfPendingRequestsByLoggedID> getListOfPendingRequestsByLoggedID() 
	{
		return getListOfPendingRequestsByLoggedID;
	}

	public void setGetInterestTraitsByChildIDOnInsight(ArrayList<GetListOfPendingRequestsByLoggedID> getPendingRequestsByLoggedID) 
	{
		this.getListOfPendingRequestsByLoggedID = getPendingRequestsByLoggedID;
	}

	
}