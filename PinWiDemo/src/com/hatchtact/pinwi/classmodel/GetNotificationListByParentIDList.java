package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetNotificationListByParentIDList implements Serializable {

	
	private ArrayList<GetNotificationListByParentID>  getNotificationListByParentID=new ArrayList<GetNotificationListByParentID>();

	public ArrayList<GetNotificationListByParentID> getGetNotificationListByParentID() {
		return getNotificationListByParentID;
	}

	public void setGetNotificationListByParentID(
			ArrayList<GetNotificationListByParentID> getNotificationListByParentID) {
		this.getNotificationListByParentID = getNotificationListByParentID;
	}
}
