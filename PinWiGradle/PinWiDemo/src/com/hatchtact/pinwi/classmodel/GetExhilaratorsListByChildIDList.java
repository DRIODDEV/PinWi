package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetExhilaratorsListByChildIDList implements Serializable
{
private ArrayList<GetExhilaratorsListByChildID> getExhilaratorsListByChildID=new ArrayList<GetExhilaratorsListByChildID>();

	public ArrayList<GetExhilaratorsListByChildID> getExhilaratorsList() {
		return getExhilaratorsListByChildID;
	}

	public void setExhilaratorsList(ArrayList<GetExhilaratorsListByChildID> list) {
		this.getExhilaratorsListByChildID = list;
	}
}
