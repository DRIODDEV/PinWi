package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GetLocationDetails implements Serializable
{
	private String FlatNoBuilding="";
	private String StreetLocality="";
	private int City;
	private String CityName="";
	private int Country;
	private String CountryName="";
	private String GoogleMapAddress="";
	private String Longitude="";
	private String Latitude="";
	private int NeighbourhoodRadius;
	private String NeighbourhoodRadiusValue="";
	private int Locality=0;

	
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
	public int getCity() {
		return City;
	}
	public void setCity(int city) {
		City = city;
	}
	public String getCityName() {
		return CityName;
	}
	public void setCityName(String cityName) {
		CityName = cityName;
	}
	public int getCountry() {
		return Country;
	}
	public void setCountry(int country) {
		Country = country;
	}
	public String getCountryName() {
		return CountryName;
	}
	public void setCountryName(String countryName) {
		CountryName = countryName;
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
	public int getNeighbourhoodRadius() {
		return NeighbourhoodRadius;
	}
	public void setNeighbourhoodRadius(int neighbourhoodRadius) {
		NeighbourhoodRadius = neighbourhoodRadius;
	}
	public String getNeighbourhoodRadiusValue() {
		return NeighbourhoodRadiusValue;
	}
	public void setNeighbourhoodRadiusValue(String neighbourhoodRadiusValue) {
		NeighbourhoodRadiusValue = neighbourhoodRadiusValue;
	}
	public int getLocality() {
		return Locality;
	}
	public void setLocality(int locality) {
		Locality = locality;
	}
}