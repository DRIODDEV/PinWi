package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetAutolockTimeList implements Serializable
{
	private ArrayList<GetAutolockTime> getAutolockTime=new ArrayList<GetAutolockTime>();

	public ArrayList<GetAutolockTime> getGetAutolockTime() {
		return getAutolockTime;
	}

	public void setGetAutolockTime(ArrayList<GetAutolockTime> getAutolockTime) {
		this.getAutolockTime = getAutolockTime;
	}
}
