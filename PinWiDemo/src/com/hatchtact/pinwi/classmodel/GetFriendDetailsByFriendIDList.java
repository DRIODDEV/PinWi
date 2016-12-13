package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetFriendDetailsByFriendIDList implements Serializable
{
private ArrayList<GetFriendDetailsByFriendID> getFriendDetailsByFriendIDList=new ArrayList<GetFriendDetailsByFriendID>();

public ArrayList<GetFriendDetailsByFriendID> getGetFriendDetailsByFriendIDList() {
	return getFriendDetailsByFriendIDList;
}

public void setGetFriendDetailsByFriendIDList(
		ArrayList<GetFriendDetailsByFriendID> getFriendDetailsByFriendIDList) {
	this.getFriendDetailsByFriendIDList = getFriendDetailsByFriendIDList;
}
}
