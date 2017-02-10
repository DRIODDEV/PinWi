package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetListOfActivitiesOnRecommendedByClusterID implements Serializable

{
	
	public int getClusterID() {
		return ClusterID;
	}
	public void setClusterID(int clusterID) {
		ClusterID = clusterID;
	}
	public String getClusterName() {
		return ClusterName;
	}
	public void setClusterName(String clusterName) {
		ClusterName = clusterName;
	}
	public int getActivityCount() {
		return ActivityCount;
	}
	public void setActivityCount(int activityCount) {
		ActivityCount = activityCount;
	}
	public int getActivityID() {
		return ActivityID;
	}
	public void setActivityID(int activityID) {
		ActivityID = activityID;
	}
	public String getActivityName() {
		return ActivityName;
	}
	public void setActivityName(String activityName) {
		ActivityName = activityName;
	}
	public int getChildrenDoingThis() {
		return ChildrenDoingThis;
	}
	public void setChildrenDoingThis(int childrenDoingThis) {
		ChildrenDoingThis = childrenDoingThis;
	}
	public int getIsWished() {
		return IsWished;
	}
	public void setIsWished(int isWished) {
		IsWished = isWished;
	}
	public int getIsScheduled() {
		return IsScheduled;
	}
	public void setIsScheduled(int isScheduled) {
		IsScheduled = isScheduled;
	}
	private int ClusterID;
	private String ClusterName;
	private int ActivityCount;
	private int ActivityID;
	private String ActivityName="";
	private int ChildrenDoingThis;
	private int IsWished;
	private int IsScheduled;
	
}
