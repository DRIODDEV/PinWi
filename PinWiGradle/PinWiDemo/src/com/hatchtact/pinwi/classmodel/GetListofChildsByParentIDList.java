package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetListofChildsByParentIDList implements Serializable
{
ArrayList<GetListofChildsByParentID> getListofChildsByParentID=new ArrayList<GetListofChildsByParentID>();

public ArrayList<GetListofChildsByParentID> getGetListofChildsByParentID() {
	return getListofChildsByParentID;
}

public void setGetListofChildsByParentID(
		ArrayList<GetListofChildsByParentID> getListofChildsByParentID) {
	this.getListofChildsByParentID = getListofChildsByParentID;
}
}
