package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetListOfActivitiesOnRecommendedByClusterIDList implements Serializable 
{
	private ArrayList<GetListOfActivitiesOnRecommendedByClusterID> getListOfActivitiesOnRecommendedByClusterID=new ArrayList<GetListOfActivitiesOnRecommendedByClusterID>();

	public ArrayList<GetListOfActivitiesOnRecommendedByClusterID> getListOfActivitiesOnRecommendedByClusterID() 
	{
		return getListOfActivitiesOnRecommendedByClusterID;
	}

	public void setGetInterestTraitsByChildIDOnInsight(ArrayList<GetListOfActivitiesOnRecommendedByClusterID> getListOfActivitiesOnRecommendedByClusterID) 
	{
		this.getListOfActivitiesOnRecommendedByClusterID = getListOfActivitiesOnRecommendedByClusterID;
	}

	
}