package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetFriendsListByLoggedIDOnCIList implements Serializable 
{
	private ArrayList<GetFriendsListByLoggedIDOnCI> getFriendsListByLoggedID=new ArrayList<GetFriendsListByLoggedIDOnCI>();

	public ArrayList<GetFriendsListByLoggedIDOnCI> getFriendsListByLoggedID() 
	{
		return getFriendsListByLoggedID;
	}

	public void setFriendsListByLoggedIDOnCI(ArrayList<GetFriendsListByLoggedIDOnCI> getFriendsListByLoggedID) 
	{
		this.getFriendsListByLoggedID = getFriendsListByLoggedID;
	}

	
}