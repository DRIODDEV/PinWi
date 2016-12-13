package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetListofAllysByParentIDList implements Serializable
{
	private ArrayList<GetListofAllysByParentID> getListofAllysByParentID=new ArrayList<GetListofAllysByParentID>();

	public ArrayList<GetListofAllysByParentID> getGetListofAllysByParentID() {
		return getListofAllysByParentID;
	}

	public void setGetListofAllysByParentID(
			ArrayList<GetListofAllysByParentID> getListofAllysByParentID) {
		this.getListofAllysByParentID = getListofAllysByParentID;
	}
	
}
