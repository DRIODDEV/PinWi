package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class AfterSchoolActivityByChildIDList implements Serializable
{
	private ArrayList<AfterSchoolActivityByChildID> afterSchoolActivityByChildID=new ArrayList<AfterSchoolActivityByChildID>();

	public ArrayList<AfterSchoolActivityByChildID> getAfterSchoolActivityByChildID() {
		return afterSchoolActivityByChildID;
	}

	public void setAfterSchoolActivityByChildID(
			ArrayList<AfterSchoolActivityByChildID> afterSchoolActivityByChildID) {
		this.afterSchoolActivityByChildID = afterSchoolActivityByChildID;
	}
}
