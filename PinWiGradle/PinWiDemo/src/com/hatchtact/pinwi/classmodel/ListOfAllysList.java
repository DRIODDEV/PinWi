package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ListOfAllysList implements Serializable
{
	private ArrayList<ListOfAllys> listOfAllys=new ArrayList<ListOfAllys>();

	public ArrayList<ListOfAllys> getListOfAllys() {
		return listOfAllys;
	}

	public void setListOfAllys(ArrayList<ListOfAllys> listOfAllys) {
		this.listOfAllys = listOfAllys;
	}	
}
