package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;


public class SearchActivitiesByActivityName implements Serializable

{
	private String InterestedNumberOfChildrens;

	private String CategoryName;

	private String SubCategoryID;

	private String ActivityID;

	private String AverageRating;

	private String Status;

	private String AgeAppropriateness;

	private String CategoryID;

	private String ClusterName;

	private String SubCategoryName;

	private String CreatedOn;

	private String ClusterID;

	private String LastModified;

	private String ActivityName;

	public String getInterestedNumberOfChildrens ()
	{
		return InterestedNumberOfChildrens;
	}

	public void setInterestedNumberOfChildrens (String InterestedNumberOfChildrens)
	{
		this.InterestedNumberOfChildrens = InterestedNumberOfChildrens;
	}

	public String getCategoryName ()
	{
		return CategoryName;
	}

	public void setCategoryName (String CategoryName)
	{
		this.CategoryName = CategoryName;
	}

	public String getSubCategoryID ()
	{
		return SubCategoryID;
	}

	public void setSubCategoryID (String SubCategoryID)
	{
		this.SubCategoryID = SubCategoryID;
	}

	public String getActivityID ()
	{
		return ActivityID;
	}

	public void setActivityID (String ActivityID)
	{
		this.ActivityID = ActivityID;
	}

	public String getAverageRating ()
	{
		return AverageRating;
	}

	public void setAverageRating (String AverageRating)
	{
		this.AverageRating = AverageRating;
	}

	public String getStatus ()
	{
		return Status;
	}

	public void setStatus (String Status)
	{
		this.Status = Status;
	}

	public String getAgeAppropriateness ()
	{
		return AgeAppropriateness;
	}

	public void setAgeAppropriateness (String AgeAppropriateness)
	{
		this.AgeAppropriateness = AgeAppropriateness;
	}

	public String getCategoryID ()
	{
		return CategoryID;
	}

	public void setCategoryID (String CategoryID)
	{
		this.CategoryID = CategoryID;
	}

	public String getClusterName ()
	{
		return ClusterName;
	}

	public void setClusterName (String ClusterName)
	{
		this.ClusterName = ClusterName;
	}

	public String getSubCategoryName ()
	{
		return SubCategoryName;
	}

	public void setSubCategoryName (String SubCategoryName)
	{
		this.SubCategoryName = SubCategoryName;
	}

	public String getCreatedOn ()
	{
		return CreatedOn;
	}

	public void setCreatedOn (String CreatedOn)
	{
		this.CreatedOn = CreatedOn;
	}

	public String getClusterID ()
	{
		return ClusterID;
	}

	public void setClusterID (String ClusterID)
	{
		this.ClusterID = ClusterID;
	}

	public String getLastModified ()
	{
		return LastModified;
	}

	public void setLastModified (String LastModified)
	{
		this.LastModified = LastModified;
	}

	public String getActivityName ()
	{
		return ActivityName;
	}

	public void setActivityName (String ActivityName)
	{
		this.ActivityName = ActivityName;
	}

	@Override
	public String toString()
	{
		return "ClassPojo [InterestedNumberOfChildrens = "+InterestedNumberOfChildrens+", CategoryName = "+CategoryName+", SubCategoryID = "+SubCategoryID+", ActivityID = "+ActivityID+", AverageRating = "+AverageRating+", Status = "+Status+", AgeAppropriateness = "+AgeAppropriateness+", CategoryID = "+CategoryID+", ClusterName = "+ClusterName+", SubCategoryName = "+SubCategoryName+", CreatedOn = "+CreatedOn+", ClusterID = "+ClusterID+", LastModified = "+LastModified+", ActivityName = "+ActivityName+"]";
	}

}
