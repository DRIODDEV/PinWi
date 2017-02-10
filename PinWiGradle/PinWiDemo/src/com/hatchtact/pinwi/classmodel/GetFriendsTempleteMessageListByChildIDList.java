package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetFriendsTempleteMessageListByChildIDList implements Serializable
{
	private ArrayList<GetFriendsTempleteMessageListByChildID> getFriendsTempleteMessageListByChildIDList=new ArrayList<GetFriendsTempleteMessageListByChildID>();

	public ArrayList<GetFriendsTempleteMessageListByChildID> getFriendsTempleteMessageListByChildID() {
		return getFriendsTempleteMessageListByChildIDList;
	}

	public void setFriendsTempleteMessageListByChildIDList(
			ArrayList<GetFriendsTempleteMessageListByChildID> getFriendsTempleteMessageListByChildIDList) 
	{
		this.getFriendsTempleteMessageListByChildIDList = getFriendsTempleteMessageListByChildIDList;
	}

	
}
