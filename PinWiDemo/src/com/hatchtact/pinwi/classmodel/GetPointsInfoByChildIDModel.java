package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetPointsInfoByChildIDModel implements Serializable
{
	private String EarnedPoints="";
	private String PendingPoints="";
	private String QualityBadge="";
	
	public String getEarnedPoints() {
		return EarnedPoints;
	}
	public void setEarnedPoints(String earnedPoints) {
		EarnedPoints = earnedPoints;
	}
	public String getPendingPoints() {
		return PendingPoints;
	}
	public void setPendingPoints(String pendingPoints) {
		PendingPoints = pendingPoints;
	}
	public String getQualityBadge() {
		return QualityBadge;
	}
	public void setQualityBadge(String qualityBadge) {
		QualityBadge = qualityBadge;
	}
	


}
