package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetListOfClustersOnRecommendedByChildID implements Serializable

{
	

	private int  ClusterID;
	private String ClusterName="";
	private int ActivityCount;
	public int getClusterID() {
		return ClusterID;
	}
	public void setClusterID(int clusterID) {
		ClusterID = clusterID;
	}
	public int getActCount() {
		return ActivityCount;
	}
	public void setActCount(int actCount) {
		ActivityCount = actCount;
	}
	public String getName() {
		return ClusterName;
	}
	public void setName(String name) {
		ClusterName = name;
	}

}
