package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetListOfClustersOnRecommendedByChildIDList implements Serializable 
{
	private ArrayList<GetListOfClustersOnRecommendedByChildID> getListOfClustersOnRecommendedByChildID=new ArrayList<GetListOfClustersOnRecommendedByChildID>();

	public ArrayList<GetListOfClustersOnRecommendedByChildID> getListOfClustersOnRecommendedByChildID() 
	{
		return getListOfClustersOnRecommendedByChildID;
	}

	public void setListOfClustersOnRecommendedByChildID(ArrayList<GetListOfClustersOnRecommendedByChildID> getListOfClustersOnRecommendedByChildID) 
	{
		this.getListOfClustersOnRecommendedByChildID = getListOfClustersOnRecommendedByChildID;
	}

	
}