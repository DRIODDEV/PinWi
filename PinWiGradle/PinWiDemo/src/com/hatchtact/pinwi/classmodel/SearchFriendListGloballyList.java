package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class SearchFriendListGloballyList implements Serializable {
	private ArrayList<SearchFriendListGlobally> searchFriendListGloballyList=new ArrayList<SearchFriendListGlobally>();

	public ArrayList<SearchFriendListGlobally> getsearchFriendListGloballyList() {
		return searchFriendListGloballyList;
	}

	public void setGetPointsInfoByChildIDOnInsights(
			ArrayList<SearchFriendListGlobally> getsearchFriendListGloballyList) {
		this.searchFriendListGloballyList = getsearchFriendListGloballyList;
	}

}
