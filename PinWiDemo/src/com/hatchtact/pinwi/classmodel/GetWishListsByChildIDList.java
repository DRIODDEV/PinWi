package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetWishListsByChildIDList implements Serializable 
{
	private ArrayList<GetWishListsByChildID> getWishListsByChildID=new ArrayList<GetWishListsByChildID>();

	public ArrayList<GetWishListsByChildID> getWishListsByChildIDList() 
	{
		return getWishListsByChildID;
	}

	public void setWishListsByChildID(ArrayList<GetWishListsByChildID> getWishListsByChildID) 
	{
		this.getWishListsByChildID = getWishListsByChildID;
	}

	
}