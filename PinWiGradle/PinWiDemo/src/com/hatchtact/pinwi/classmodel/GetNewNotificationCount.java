package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetNewNotificationCount implements Serializable 
{

	private int Count;

	public int getCount() {
		return Count;
	}

	public void setCount(int count) {
		this.Count = count;
	}
}
