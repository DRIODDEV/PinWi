package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ResultIsSubscribeList implements Serializable 
{
	private ArrayList<ResultIsSubscribed> isSubscribeList=new ArrayList<ResultIsSubscribed>();

	public ArrayList<ResultIsSubscribed> getIsSubscribed() {
		return isSubscribeList;
	}

	public void setSubscribed(ArrayList<ResultIsSubscribed> list) {
		this.isSubscribeList = list;
	}
}
