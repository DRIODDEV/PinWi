package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetDetailByMapEmoticIDList implements Serializable
{
	private ArrayList<GetDetailByMapEmoticID> getDetailByMapEmoticIDList=new ArrayList<GetDetailByMapEmoticID>();

	public ArrayList<GetDetailByMapEmoticID> getDetailByMapEmoticID() {
		return getDetailByMapEmoticIDList;
	}

	public void setDetailByMapEmoticID(
			ArrayList<GetDetailByMapEmoticID> getDetailByMapEmoticID) {
		this.getDetailByMapEmoticIDList = getDetailByMapEmoticID;
	}

	
}
