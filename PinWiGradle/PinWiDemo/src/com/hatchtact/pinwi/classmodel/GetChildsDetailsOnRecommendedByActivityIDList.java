package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetChildsDetailsOnRecommendedByActivityIDList implements Serializable 
{
	private ArrayList<GetChildsDetailsOnRecommendedByActivityID> getChildsDetailsOnRecommendedByActivityID=new ArrayList<GetChildsDetailsOnRecommendedByActivityID>();

	public ArrayList<GetChildsDetailsOnRecommendedByActivityID> getChildsDetailsOnRecommendedByActivityID() 
	{
		return getChildsDetailsOnRecommendedByActivityID;
	}

	public void setGetChildsDetailsOnRecommendedByActivityID(ArrayList<GetChildsDetailsOnRecommendedByActivityID> getChildsDetailsOnRecommendedByActivityID) 
	{
		this.getChildsDetailsOnRecommendedByActivityID = getChildsDetailsOnRecommendedByActivityID;
	}

	
}