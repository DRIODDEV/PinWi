package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetInterestTraitsByChildIDOnInsightList implements Serializable {
	private ArrayList<GetInterestTraitsByChildIDOnInsight> getInterestTraitsByChildIDOnInsight=new ArrayList<GetInterestTraitsByChildIDOnInsight>();

	public ArrayList<GetInterestTraitsByChildIDOnInsight> getGetInterestTraitsByChildIDOnInsight() {
		return getInterestTraitsByChildIDOnInsight;
	}

	public void setGetInterestTraitsByChildIDOnInsight(
			ArrayList<GetInterestTraitsByChildIDOnInsight> getInterestTraitsByChildIDOnInsight) {
		this.getInterestTraitsByChildIDOnInsight = getInterestTraitsByChildIDOnInsight;
	}

}
