package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetInterestPatternByChildIDOnInsight implements Serializable
{
	private String Name;

	private String Description;

	private String InterestTraitID;

	private String PatternID;

	public String getName ()
	{
		return Name;
	}

	public void setName (String Name)
	{
		this.Name = Name;
	}

	public String getDescription ()
	{
		return Description;
	}

	public void setDescription (String Description)
	{
		this.Description = Description;
	}

	public String getInterestTraitID ()
	{
		return InterestTraitID;
	}

	public void setInterestTraitID (String InterestTraitID)
	{
		this.InterestTraitID = InterestTraitID;
	}

	public String getPatternID ()
	{
		return PatternID;
	}

	public void setPatternID (String PatternID)
	{
		this.PatternID = PatternID;
	}

	@Override
	public String toString()
	{
		return "ClassPojo [Name = "+Name+", Description = "+Description+", InterestTraitID = "+InterestTraitID+", PatternID = "+PatternID+"]";
	}
}
