package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DisplayAllyByChildAndActivityId implements Serializable
{
	private String AllyName="";
	private int AllyID;
	private String Date="";
	private String Time="";
	private String PickUp="";
	private String Drop="";
	private String SpeicalInstructions="";
	
	public String getAllyName() {
		return AllyName;
	}
	public void setAllyName(String allyName) {
		AllyName = allyName;
	}
	public int getAllyID() {
		return AllyID;
	}
	public void setAllyID(int allyID) {
		AllyID = allyID;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getPickUp() {
		return PickUp;
	}
	public void setPickUp(String pickUp) {
		PickUp = pickUp;
	}
	public String getDrop() {
		return Drop;
	}
	public void setDrop(String drop) {
		Drop = drop;
	}
	public String getSpeicalInstructions() {
		return SpeicalInstructions;
	}
	public void setSpeicalInstructions(String speicalInstructions) {
		SpeicalInstructions = speicalInstructions;
	}
}
