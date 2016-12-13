package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetPeopleYouMayKnowListByLoggedIDList implements Serializable 
{
	private ArrayList<GetPeopleYouMayKnowListByLoggedID> getPeopleYouMayKnowListByLoggedID=new ArrayList<GetPeopleYouMayKnowListByLoggedID>();

	public ArrayList<GetPeopleYouMayKnowListByLoggedID> getPeopleYouMayKnowListByLoggedID() 
	{
		return getPeopleYouMayKnowListByLoggedID;
	}

	public void setGetInterestTraitsByChildIDOnInsight(ArrayList<GetPeopleYouMayKnowListByLoggedID> getPeopleYouMayKnowByLoggedID) 
	{
		this.getPeopleYouMayKnowListByLoggedID = getPeopleYouMayKnowByLoggedID;
	}

	
}