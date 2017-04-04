package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class AfterSchoolCategoriesByOwnerIDList implements Serializable
{
	private ArrayList<AfterSchoolCategoriesByOwnerID> afterSchoolCategoriesByOwnerID=new ArrayList<AfterSchoolCategoriesByOwnerID>();

	public ArrayList<AfterSchoolCategoriesByOwnerID> getAfterSchoolCategoriesByOwnerID() {
		return afterSchoolCategoriesByOwnerID;
	}

	public void setAfterSchoolCategoriesByOwnerID(
			ArrayList<AfterSchoolCategoriesByOwnerID> afterSchoolCategoriesByOwnerID) {
		this.afterSchoolCategoriesByOwnerID = afterSchoolCategoriesByOwnerID;
	}
}
