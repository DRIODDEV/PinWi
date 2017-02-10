package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetInterestPatternByChildIDOnInsightList implements Serializable 
{
	private ArrayList<GetInterestPatternByChildIDOnInsight> getInterestPatternByChildIDOnInsight=new ArrayList<GetInterestPatternByChildIDOnInsight>();

	public ArrayList<GetInterestPatternByChildIDOnInsight> getInterestPatternByChildIDList() {
		return getInterestPatternByChildIDOnInsight;
	}

	public void setInterestPatternByChildIDOnInsight(ArrayList<GetInterestPatternByChildIDOnInsight> list) 
	{
		this.getInterestPatternByChildIDOnInsight = list;
	}

}
