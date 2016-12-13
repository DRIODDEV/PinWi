package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetInterestTraitsByChildIDOnInsight implements Serializable 
{
	private int BucketID;
	private int InterestTraitID;
	private String Name;
	private int Status;
	private String Description;
	
	public int getBucketID() {
		return BucketID;
	}
	public void setBucketID(int bucketID) {
		BucketID = bucketID;
	}
	public int getInterestTraitID() {
		return InterestTraitID;
	}
	public void setInterestTraitID(int interestTraitID) {
		InterestTraitID = interestTraitID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	
	
}
