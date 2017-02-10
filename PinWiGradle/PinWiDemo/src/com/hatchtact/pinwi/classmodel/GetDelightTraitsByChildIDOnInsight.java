package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetDelightTraitsByChildIDOnInsight implements Serializable 
{
   private int ActivityID;
   private String Name;
   private int ActivityRank;
   private double Rating;
   private double Change;
public int getActivityID() {
	return ActivityID;
}
public void setActivityID(int activityID) {
	ActivityID = activityID;
}
public String getName() {
	return Name;
}
public void setName(String name) {
	Name = name;
}
public int getActivityRank() {
	return ActivityRank;
}
public void setActivityRank(int activityRank) {
	ActivityRank = activityRank;
}
public double getRating() {
	return Rating;
}
public void setRating(double rating) {
	Rating = rating;
}
public double getChange() {
	return Change;
}
public void setChange(double change) {
	Change = change;
}
	
}
