package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetDelightTraitsByActivity implements Serializable {
	
	public int getRating() {
		return Rating;
	}
	public void setRating(int rating) {
		Rating = rating;
	}
	public String getActivityDate() {
		return ActivityDate;
	}
	public void setActivityDate(String activityDate) {
		ActivityDate = activityDate;
	}
	private int Rating;
	private String ActivityDate;
	
}
