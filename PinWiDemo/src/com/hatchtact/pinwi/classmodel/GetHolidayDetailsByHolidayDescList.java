package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

public class GetHolidayDetailsByHolidayDescList implements Serializable 
{

	ArrayList<GetHolidayDetailsByHolidayDescList> getHolidayDetailsByHolidayDescList=new ArrayList<GetHolidayDetailsByHolidayDescList>();

	public ArrayList<GetHolidayDetailsByHolidayDescList> getHolidayDescList() {
		return getHolidayDetailsByHolidayDescList;
	}

	public void setgetHolidayDescList(ArrayList<GetHolidayDetailsByHolidayDescList> getHolidayDescList) {
		this.getHolidayDetailsByHolidayDescList = getHolidayDescList;
	}
}
