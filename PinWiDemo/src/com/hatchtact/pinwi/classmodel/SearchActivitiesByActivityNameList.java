package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class SearchActivitiesByActivityNameList implements Serializable 
{
	private ArrayList<SearchActivitiesByActivityName> searchActivitiesByActName=new ArrayList<SearchActivitiesByActivityName>();

	public ArrayList<SearchActivitiesByActivityName> getSearchActivitiesByActName() {
		return searchActivitiesByActName;
	}

	public void setSearchActivitiesByActName(
			ArrayList<SearchActivitiesByActivityName> searchActivitiesByActName) {
		this.searchActivitiesByActName = searchActivitiesByActName;
	}

	

	
}