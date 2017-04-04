package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetDelightTraitsByChildIDOnInsightList implements Serializable {

	private ArrayList<GetDelightTraitsByChildIDOnInsight> getDelightTraitsByChildIDOnInsight=new ArrayList<GetDelightTraitsByChildIDOnInsight>();

	public ArrayList<GetDelightTraitsByChildIDOnInsight> getGetDelightTraitsByChildIDOnInsight() {
		return getDelightTraitsByChildIDOnInsight;
	}

	public void setGetDelightTraitsByChildIDOnInsight(
			ArrayList<GetDelightTraitsByChildIDOnInsight> getDelightTraitsByChildIDOnInsight) {
		this.getDelightTraitsByChildIDOnInsight = getDelightTraitsByChildIDOnInsight;
	}
	
}
