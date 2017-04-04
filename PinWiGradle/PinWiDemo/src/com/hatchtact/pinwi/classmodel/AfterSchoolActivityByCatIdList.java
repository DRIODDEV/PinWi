package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class AfterSchoolActivityByCatIdList implements Serializable
{
	private ArrayList<AfterSchoolActivityByCatId> afterschoolActivityByCatId = new ArrayList<AfterSchoolActivityByCatId>();

	public ArrayList<AfterSchoolActivityByCatId> getAfterschoolActivityByCatId() {
		return afterschoolActivityByCatId;
	}

	public void setAfterschoolActivityByCatId(
			ArrayList<AfterSchoolActivityByCatId> afterschoolActivityByCatId) {
		this.afterschoolActivityByCatId = afterschoolActivityByCatId;
	}
}
