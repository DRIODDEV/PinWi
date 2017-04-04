package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetFriendsListByLoggedIDList implements Serializable 
{
	private ArrayList<GetFriendsListByLoggedID> getFriendsListByLoggedID=new ArrayList<GetFriendsListByLoggedID>();

	public ArrayList<GetFriendsListByLoggedID> getGetFriendsListByLoggedID() 
	{
		return getFriendsListByLoggedID;
	}

	public void setGetInterestTraitsByChildIDOnInsight(ArrayList<GetFriendsListByLoggedID> getFriendsListByLoggedID) 
	{
		this.getFriendsListByLoggedID = getFriendsListByLoggedID;
	}

	
}