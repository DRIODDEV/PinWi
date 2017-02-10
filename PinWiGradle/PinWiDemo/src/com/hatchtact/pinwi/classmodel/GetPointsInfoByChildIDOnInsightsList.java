package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetPointsInfoByChildIDOnInsightsList implements Serializable {
	private ArrayList<GetPointsInfoByChildIDOnInsights> getPointsInfoByChildIDOnInsights=new ArrayList<GetPointsInfoByChildIDOnInsights>();

	public ArrayList<GetPointsInfoByChildIDOnInsights> getGetPointsInfoByChildIDOnInsights() {
		return getPointsInfoByChildIDOnInsights;
	}

	public void setGetPointsInfoByChildIDOnInsights(
			ArrayList<GetPointsInfoByChildIDOnInsights> getPointsInfoByChildIDOnInsights) {
		this.getPointsInfoByChildIDOnInsights = getPointsInfoByChildIDOnInsights;
	}

}
