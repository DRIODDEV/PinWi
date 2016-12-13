package com.hatchtact.pinwi.classmodel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AddAllyInformationOnActivity implements Serializable
{
	private String wsid=null;
	private String wspwd=null;
	private int ChildID;
	private int ActivityID;
	private int AllyID;
	private String Date=null;
	private String Time=null;
	private String PickUp=null;
	private String Drop=null;
	private String SpeicalInstructions=null;
	private String NotificationMode=null;
	private int AllyIndex;
	
	public int getAllyIndex() {
		return AllyIndex;
	}
	public void setAllyIndex(int allyIndex) {
		AllyIndex = allyIndex;
	}
	private int ActivityAllyID;

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
	public int getChildID() {
		return ChildID;
	}
	public void setChildID(int childID) {
		ChildID = childID;
	}
	public int getActivityID() {
		return ActivityID;
	}
	public void setActivityID(int activityID) {
		ActivityID = activityID;
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
	public int getActivityAllyID() {
		return ActivityAllyID;
	}
	public void setActivityAllyID(int activityAllyID) {
		ActivityAllyID = activityAllyID;
	}
	
	public String getSpeicalInstructions() {
		return SpeicalInstructions;
	}
	public void setSpeicalInstructions(String speicalInstructions) {
		SpeicalInstructions = speicalInstructions;
	}
	public String getNotificationMode() {
		return NotificationMode;
	}
	public void setNotificationMode(String notificationMode) {
		NotificationMode = notificationMode;
	}
	
	/*public boolean isSpeicalInstructions() {
		return SpeicalInstructions;
	}
	public void setSpeicalInstructions(boolean speicalInstructions) {
		SpeicalInstructions = speicalInstructions;
	}
	public boolean isNotificationMode() {
		return NotificationMode;
	}
	public void setNotificationMode(boolean notificationMode) {
		NotificationMode = notificationMode;
	}*/
}
