package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetPointsInfoByChildIDOnInsights implements Serializable {
	
	private int EarnedPoints;
	private int PendingPoints;

	private int LostPoints;

	public int getEarnedPoints() {
		return EarnedPoints;
	}

	public void setEarnedPoints(int earnedPoints) {
		EarnedPoints = earnedPoints;
	}

	public int getPendingPoints() {
		return PendingPoints;
	}

	public void setPendingPoints(int pendingPoints) {
		PendingPoints = pendingPoints;
	}

	public int getLostPoints() {
		return LostPoints;
	}

	public void setLostPoints(int lostPoints) {
		LostPoints = lostPoints;
	}

}
