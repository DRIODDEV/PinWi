package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class SearchWishListByChildIDList implements Serializable 
{
	private ArrayList<SearchWishListByChildID> searchWishListByChildIDList=new ArrayList<SearchWishListByChildID>();

	public ArrayList<SearchWishListByChildID> getsearchWishListByChildIDList() 
	{
		return searchWishListByChildIDList;
	}

	public void setsearchActivitiesByChildID(ArrayList<SearchWishListByChildID> searchWishListByChildIDList) 
	{
		this.searchWishListByChildIDList = searchWishListByChildIDList;
	}

	
}