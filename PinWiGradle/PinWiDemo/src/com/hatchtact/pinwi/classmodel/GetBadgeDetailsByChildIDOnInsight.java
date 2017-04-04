package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetBadgeDetailsByChildIDOnInsight implements Serializable 
{
	private String CreatedOn;
	private int Quality_Badge;
	private int ChildId;
	private String ChildName;
	
	public String getCreatedOn() {
		return CreatedOn;
	}
	public void setCreatedOn(String createdOn) {
		CreatedOn = createdOn;
	}
	public int getQuality_Badge() {
		return Quality_Badge;
	}
	public void setQuality_Badge(int quality_Badge) {
		Quality_Badge = quality_Badge;
	}
	public int getChildId() {
		return ChildId;
	}
	public void setChildId(int childId) {
		ChildId = childId;
	}
	public String getChildName() {
		return ChildName;
	}
	public void setChildName(String childName) {
		ChildName = childName;
	}
	
}
