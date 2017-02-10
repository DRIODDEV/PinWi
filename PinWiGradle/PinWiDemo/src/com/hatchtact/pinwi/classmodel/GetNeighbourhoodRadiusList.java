package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GetNeighbourhoodRadiusList implements Serializable
{
	private ArrayList<GetNeighbourhoodRadius> getNeighbourhoodRadius=new ArrayList<GetNeighbourhoodRadius>();

	public ArrayList<GetNeighbourhoodRadius> getGetNeighbourhoodRadius() {
		return getNeighbourhoodRadius;
	}

	public void setGetNeighbourhoodRadius(
			ArrayList<GetNeighbourhoodRadius> getNeighbourhoodRadius) {
		this.getNeighbourhoodRadius = getNeighbourhoodRadius;
	}

}
