package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class AllyList implements Serializable
{
	private ArrayList<Ally> allyList=new ArrayList<Ally>();

	public ArrayList<Ally> getAllyList() {
		return allyList;
	}

	public void setAllyList(ArrayList<Ally> allyList) {
		this.allyList = allyList;
	}
}
