package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ParentProfile implements Serializable
{
	private String wsid="";
	private String wspwd="";
	private String DeviceID="";
	private String DeviceToken="";
	private String ProfileImage="";
	private String FirstName="";
	private String LastName="";
	private String EmailAddress="";
	private String Password="";
	private String DateOfBirth="";
	private String Relation="";
	private String Gender="";
	private String Contact="";
	private String Passcode="";
	private String AutolockTime="";
	private String FlatNoBuilding="";
	private String StreetLocality="";
	private String City="";
	private String Country="";
	private String GoogleMapAddress="";
	private String Longitude="";
	private String Latitude="";
	private String NeighbourhoodRadius="";	
	private int ParentID=0;
	private int CityID;
	private int CountryID;
	private int LocalityID;

	private int AutolockID;
	private int NeighbourhoodID;
	
	public int getNeighbourhoodID() {
		return NeighbourhoodID;
	}
	public void setNeighbourhoodID(int neighbourhoodID) {
		NeighbourhoodID = neighbourhoodID;
	}
	public int getAutolockID() {
		return AutolockID;
	}
	public void setAutolockID(int autolockID) {
		AutolockID = autolockID;
	}
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
	public String getDeviceID() {
		return DeviceID;
	}
	public void setDeviceID(String deviceID) {
		DeviceID = deviceID;
	}
	public String getDeviceToken() {
		return DeviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		DeviceToken = deviceToken;
	}
	public String getProfileImage() {
		return ProfileImage;
	}
	public void setProfileImage(String profileImage) {
		ProfileImage = profileImage;
	}
	
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getEmailAddress() {
		return EmailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		EmailAddress = emailAddress;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getDateOfBirth() {
		return DateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}
	public String getRelation() {
		return Relation;
	}
	public void setRelation(String relation) {
		Relation = relation;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	public String getContact() {
		return Contact;
	}
	public void setContact(String contact) {
		Contact = contact;
	}
	public String getPasscode() {
		return Passcode;
	}
	public void setPasscode(String passcode) {
		Passcode = passcode;
	}
	public String getAutolockTime() {
		return AutolockTime;
	}
	public void setAutolockTime(String autolockTime) {
		AutolockTime = autolockTime;
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
	public int getParentID() {
		return ParentID;
	}
	public void setParentID(int parentID) {
		ParentID = parentID;
	}
	public int getCityID() {
		return CityID;
	}
	public void setCityID(int cityID) {
		CityID = cityID;
	}
	public int getCountryID() {
		return CountryID;
	}
	public void setCountryID(int countryID) {
		CountryID = countryID;
	}
	public int getLocalityID() {
		return LocalityID;
	}
	public void setLocalityID(int localityID) {
		LocalityID = localityID;
	}
}
