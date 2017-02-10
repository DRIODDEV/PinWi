package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ResultIsSubscribed implements Serializable 
{

	private String Status;
	 private String PatternStatus;
	 
	private String SubscriptionType;

	private String SubscriptionCost;

	private String SubscriptionText;

	public String getStatus ()
	{
		return Status;
	}

	public void setStatus (String Status)
	{
		this.Status = Status;
	}

	public String getSubscriptionType ()
	{
		return SubscriptionType;
	}

	public void setSubscriptionType (String SubscriptionType)
	{
		this.SubscriptionType = SubscriptionType;
	}

	public String getSubscriptionCost ()
	{
		return SubscriptionCost;
	}

	public void setSubscriptionCost (String SubscriptionCost)
	{
		this.SubscriptionCost = SubscriptionCost;
	}

	public String getSubscriptionText ()
	{
		return SubscriptionText;
	}

	public void setSubscriptionText (String SubscriptionText)
	{
		this.SubscriptionText = SubscriptionText;
	}

	@Override
	public String toString()
	{
		return "ClassPojo [Status = "+Status+", SubscriptionType = "+SubscriptionType+", SubscriptionCost = "+SubscriptionCost+", SubscriptionText = "+SubscriptionText+"]";
	}

	public String getPatternStatus() {
		return PatternStatus;
	}

	public void setPatternStatus(String patternStatus) {
		PatternStatus = patternStatus;
	}


}
