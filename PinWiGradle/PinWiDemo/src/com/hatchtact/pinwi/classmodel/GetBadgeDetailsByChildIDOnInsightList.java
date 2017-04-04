package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetBadgeDetailsByChildIDOnInsightList implements Serializable
{
	private ArrayList<GetBadgeDetailsByChildIDOnInsight> getBadgeDetailsByChildIDOnInsight=new ArrayList<GetBadgeDetailsByChildIDOnInsight>();

	public ArrayList<GetBadgeDetailsByChildIDOnInsight> getGetBadgeDetailsByChildIDOnInsight() {
		return getBadgeDetailsByChildIDOnInsight;
	}

	public void setGetBadgeDetailsByChildIDOnInsight(
			ArrayList<GetBadgeDetailsByChildIDOnInsight> getBadgeDetailsByChildIDOnInsight) {
		this.getBadgeDetailsByChildIDOnInsight = getBadgeDetailsByChildIDOnInsight;
	}

	
}
