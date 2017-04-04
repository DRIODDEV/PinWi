package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetNeighbourhoodRadius implements Serializable
{
	private int NeighbourhoodID;
	private String RadiusValue=null;

	public int getNeighbourhoodID() {
		return NeighbourhoodID;
	}
	public void setNeighbourhoodID(int neighbourhoodID) {
		NeighbourhoodID = neighbourhoodID;
	}
	public String getRadiusValue() {
		return RadiusValue;
	}
	public void setRadiusValue(String radiusValue) {
		RadiusValue = radiusValue;
	}
}
