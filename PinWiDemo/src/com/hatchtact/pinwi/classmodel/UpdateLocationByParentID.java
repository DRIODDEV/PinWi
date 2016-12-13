package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")

public class UpdateLocationByParentID implements Serializable
{
	private String wsid="";
	private String wspwd="";
	private int ParentID;
	private String FlatNoBuilding="";
	private String StreetLocality="";
	private String City="";
	private String Country="";
	private String GoogleMapAddress="";
	private String Longitude="";
	private String Latitude="";
	private String NeighbourhoodRadius="";
	
	public String getWsid() {
		return wsid;
	}
	public void setWsid(String wsid) {
		this.wsid = wsid;
	}
	public String getWspwd() {
		return wspwd;
	}
	public void setWspwd(String wspwd) {
		this.wspwd = wspwd;
	}
	public int getParentID() {
		return ParentID;
	}
	public void setParentID(int parentID) {
		ParentID = parentID;
	}
	public String getFlatNoBuilding() {
		return FlatNoBuilding;
	}
	public void setFlatNoBuilding(String flatNoBuilding) {
		FlatNoBuilding = flatNoBuilding;
	}
	public String getStreetLocality() {
		return StreetLocality;
	}
	public void setStreetLocality(String streetLocality) {
		StreetLocality = streetLocality;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	public String getGoogleMapAddress() {
		return GoogleMapAddress;
	}
	public void setGoogleMapAddress(String googleMapAddress) {
		GoogleMapAddress = googleMapAddress;
	}
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getNeighbourhoodRadius() {
		return NeighbourhoodRadius;
	}
	public void setNeighbourhoodRadius(String neighbourhoodRadius) {
		NeighbourhoodRadius = neighbourhoodRadius;
	}
}