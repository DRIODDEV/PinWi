package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetDelightTraitsByActivityList implements Serializable {
	private ArrayList<GetDelightTraitsByActivity> getDelightTraitsByActivity=new ArrayList<GetDelightTraitsByActivity>();

	public ArrayList<GetDelightTraitsByActivity> getGetDelightTraitsByActivity() {
		return getDelightTraitsByActivity;
	}

	public void setGetDelightTraitsByActivity(
			ArrayList<GetDelightTraitsByActivity> getDelightTraitsByActivity) {
		this.getDelightTraitsByActivity = getDelightTraitsByActivity;
	}

}
