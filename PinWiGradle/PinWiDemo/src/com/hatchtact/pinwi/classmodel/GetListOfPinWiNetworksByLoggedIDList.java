package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

public class GetListOfPinWiNetworksByLoggedIDList implements Serializable 
{
	private ArrayList<GetListOfPinWiNetworksByLoggedID> getListOfPinWiNetworksByLoggedIDList=new ArrayList<GetListOfPinWiNetworksByLoggedID>();

	public ArrayList<GetListOfPinWiNetworksByLoggedID> getGetListOfPinWiNetworksByLoggedIDList() {
		return getListOfPinWiNetworksByLoggedIDList;
	}

	public void setGetListOfPinWiNetworksByLoggedIDList(
			ArrayList<GetListOfPinWiNetworksByLoggedID> getListOfPinWiNetworksByLoggedIDList) {
		this.getListOfPinWiNetworksByLoggedIDList = getListOfPinWiNetworksByLoggedIDList;
	}
	

}
