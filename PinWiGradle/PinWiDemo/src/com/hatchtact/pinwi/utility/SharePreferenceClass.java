package com.hatchtact.pinwi.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePreferenceClass
{
	private String PREFS_NAME = "SettingsFile";

	private SharedPreferences sharedPreferences;
	private Editor editor;

	private int currentScreen=0;

	public SharePreferenceClass(Context ctx)
	{
		if(sharedPreferences==null)
			sharedPreferences = ctx.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
	}

	public void setParentIsRegistered(boolean reg_state)
	{
		editor = sharedPreferences.edit();
		editor.putBoolean("IS_REGISTERED", reg_state);
		editor.commit();
	}

	public boolean getParentIsRegistered()
	{
		return sharedPreferences.getBoolean("IS_REGISTERED", false);
	}

	public void setParentProfile(String parentProfile)
	{
		editor = sharedPreferences.edit();

		editor.putString("ParentProfile", parentProfile);
		editor.commit();

	}
	public String getParentProfile()
	{
		return sharedPreferences.getString("ParentProfile","");
	}

	/*//child Information
	public void setChildProfile(String childProfile)
	{
		editor = sharedPreferences.edit();

		editor.putString("ChildProfile", childProfile);
		editor.commit();

	}
	public String getChildProfile()
	{
		return sharedPreferences.getString("ChildProfile","");
	}  
	 */
	//PassCodeList
	public void setPassCodeList(String passcodeList)
	{
		editor = sharedPreferences.edit();

		editor.putString("PassCodeList", passcodeList);
		editor.commit();

	}
	public String getPassCodeList()
	{
		return sharedPreferences.getString("PassCodeList","");
	}

	public int getCurrentScreen() {
		return sharedPreferences.getInt("CurrentScreen", 0);
	}

	public void setCurrentScreen(int currentScreen) {
		editor = sharedPreferences.edit();
		editor.putInt("CurrentScreen", currentScreen);
		editor.commit();
	}

	public void setIsLogin(boolean login_state)
	{
		editor = sharedPreferences.edit();
		editor.putBoolean("IS_LOGIN", login_state);
		editor.commit();
	}

	public boolean getIsLogin()
	{
		return sharedPreferences.getBoolean("IS_LOGIN", false);
	}

	public void setIsLogout(boolean logout_state)
	{
		editor = sharedPreferences.edit();
		editor.putBoolean("IS_LOGOUT", logout_state);
		editor.commit();
	}

	public boolean getIsLogout()
	{
		return sharedPreferences.getBoolean("IS_LOGOUT", false);
	}

	//For AllyDropPick

	public void setAddAllyInformationOnActivity(String addAllyInformationOnActivity)
	{
		editor = sharedPreferences.edit();
		editor.putString("AddAllyInformationOnActivity", addAllyInformationOnActivity);
		editor.commit();
	}
	public String getAddAllyInformationOnActivity()
	{
		return sharedPreferences.getString("AddAllyInformationOnActivity","");
	}



	public void setIsPasscodeParentSet(boolean passcode_state)
	{
		editor = sharedPreferences.edit();
		editor.putBoolean("IS_PASSCODESET", passcode_state);
		editor.commit();
	}

	public boolean getIsPasscodeParentSet()
	{
		return sharedPreferences.getBoolean("IS_PASSCODESET", false);
	}

	public void setIsPasscodeChildSet(boolean passcodeChild_state)
	{
		editor = sharedPreferences.edit();
		editor.putBoolean("IS_PASSCODECHILD", passcodeChild_state);
		editor.commit();
	}

	public boolean getIsPasscodeChildSet()
	{
		return sharedPreferences.getBoolean("IS_PASSCODECHILD", false);
	}


	public void setActivitySetting_Notification(boolean isActivitySetting_Notification)
	{
		editor = sharedPreferences.edit();
		editor.putBoolean("ActivitySetting_Notification", isActivitySetting_Notification);
		editor.commit();
	}
	public boolean getActivitySetting_Notification()
	{
		return sharedPreferences.getBoolean("ActivitySetting_Notification",true);
	}


	public void setReportSetting_Notification(boolean isReportSetting_Notification)
	{
		editor = sharedPreferences.edit();
		editor.putBoolean("ReportSetting_Notification", isReportSetting_Notification);
		editor.commit();
	}
	public boolean getReportSetting_Notification()
	{
		return sharedPreferences.getBoolean("ReportSetting_Notification",true);
	}


	public void setNotification3Setting_Notification(boolean isNotification3Setting_Notification)
	{
		editor = sharedPreferences.edit();
		editor.putBoolean("Notification3Setting_Notification", isNotification3Setting_Notification);
		editor.commit();
	}
	public boolean getNotification3Setting_Notification()
	{
		return sharedPreferences.getBoolean("Notification3Setting_Notification",true);
	}


	public void setNotification4Setting_Notification(boolean isNotification4Setting_Notification)
	{
		editor = sharedPreferences.edit();
		editor.putBoolean("Notification4Setting_Notification", isNotification4Setting_Notification);
		editor.commit();
	}
	public boolean getNotification4Setting_Notificationn()
	{
		return sharedPreferences.getBoolean("Notification4Setting_Notification",true);
	}


	public void setFrequency(String frequency)
	{
		editor = sharedPreferences.edit();
		editor.putString("Frequency", frequency);
		editor.commit();
	}
	public String getFrequency()
	{
		return sharedPreferences.getString("Frequency","Daily");
	}

	public void setTime(String time)
	{
		editor = sharedPreferences.edit();
		editor.putString("Time", time);
		editor.commit();
	}
	public String getTime()
	{
		return sharedPreferences.getString("Time","7:30 PM");
	}


	/*public void setSound(boolean isSound)
	{
		editor = sharedPreferences.edit();
		editor.putBoolean("isSound", isSound);
		editor.commit();
	}
	public boolean isSound()
	{
		return sharedPreferences.getBoolean("isSound",false);
	}*/

	/*For Sounds Child Module*/
	public void setSound(boolean isSound,String key) 
	{
		// TODO Auto-generated method stub
		editor = sharedPreferences.edit();
		editor.putBoolean(key+"Sound", isSound);
		editor.commit();
	}

	public boolean isSound(String key) 
	{		
		return sharedPreferences.getBoolean(key+"Sound",false);
	}

	/*For VoiceOvers Child Module*/
	public void setVoiceOvers(boolean isVoiceOver,String key) 
	{
		// TODO Auto-generated method stub
		editor = sharedPreferences.edit();
		editor.putBoolean(key+"VoiceOver", isVoiceOver);
		editor.commit();
	}

	public boolean isVoiceOver(String key) 
	{		
		return sharedPreferences.getBoolean(key+"VoiceOver",false);
	}


	/***************Tutorial*********************************************/


	public void setHowPinWiWorks(boolean howPinWiWorks)
	{
		editor = sharedPreferences.edit();
		editor.putBoolean("howPinWiWorks", howPinWiWorks);
		editor.commit();
	}
	public boolean gethowPinWiWorks()
	{
		return sharedPreferences.getBoolean("howPinWiWorks",false);
	}


	public void setCalenderTutorial(boolean calenderTutorial)
	{
		editor = sharedPreferences.edit();
		editor.putBoolean("calenderTutorial", calenderTutorial);
		editor.commit();
	}
	public boolean iscalenderTutorial()
	{
		return sharedPreferences.getBoolean("calenderTutorial",false);
	}


	public void setAtSchoolTutorial(boolean atSchoolTutorial)
	{
		editor = sharedPreferences.edit();
		editor.putBoolean("atSchoolTutorial", atSchoolTutorial);
		editor.commit();
	}
	public boolean isatSchoolTutorial()
	{
		return sharedPreferences.getBoolean("atSchoolTutorial",false);
	}

	public void setafterschoolTutorial(boolean afterschoolTutorial)
	{
		editor = sharedPreferences.edit();
		editor.putBoolean("afterschoolTutorial", afterschoolTutorial);
		editor.commit();
	}
	public boolean isafterschoolTutorial()
	{
		return sharedPreferences.getBoolean("afterschoolTutorial",false);
	}

	public void setInsightsTutorial(boolean insightsTutorial)
	{
		editor = sharedPreferences.edit();
		editor.putBoolean("insightsTutorial", insightsTutorial);
		editor.commit();
	}
	public boolean isinsightsTutorial()
	{
		return sharedPreferences.getBoolean("insightsTutorial",false);
	}

	public void setholidaycalendertutorial(boolean holidaycalendertutorial)
	{
		editor = sharedPreferences.edit();
		editor.putBoolean("holidaycalendertutorial", holidaycalendertutorial);
		editor.commit();
	}
	public boolean isholidaycalendertutorial()
	{
		return sharedPreferences.getBoolean("holidaycalendertutorial",false);
	}

	public void setIsChildTutorialDone(int childID)
	{
		editor = sharedPreferences.edit();
		editor.putBoolean("IS_TUTORIAL_DONE"+childID, true);
		editor.commit();
	}

	public boolean getIsChildTutorialDone(int childID)
	{
		return sharedPreferences.getBoolean("IS_TUTORIAL_DONE"+childID, false);
	}

	public void setDeviceId(String deviceId)
	{
		editor = sharedPreferences.edit();
		editor.putString("deviceId", deviceId);
		editor.commit();
	}
	public String getDeviceId()
	{
		return sharedPreferences.getString("deviceId","");
	}



	public void setGCMDeviceId(String deviceId)
	{
		editor = sharedPreferences.edit();
		editor.putString("gcmdeviceId", deviceId);
		editor.commit();
	}
	public String getGCMDeviceId()
	{
		return sharedPreferences.getString("gcmdeviceId","");
	}


	public void setTime24Hour(String time)
	{
		editor = sharedPreferences.edit();
		editor.putString("Time24Hour", time);
		editor.commit();
	}
	public String getTime24Hour()
	{
		return sharedPreferences.getString("Time24Hour","19:30");
	}



	public void setFirstTimeActivitySchedule(int isSchedule,String key) 
	{
		// TODO Auto-generated method stub
		editor = sharedPreferences.edit();
		editor.putInt(key+"ActivitySchedule", isSchedule);
		editor.commit();
	}


	public int isFirstTimeActivityScheduled(String key) 
	{		
		return sharedPreferences.getInt(key+"ActivitySchedule",0);
	}

	public boolean isAppDownloaded()
	{
		return sharedPreferences.getBoolean("isAppDownloaded",false);
	}

	public void setAppDownloaded(boolean isAppDownloaded)
	{
		editor = sharedPreferences.edit();
		editor.putBoolean("isAppDownloaded", isAppDownloaded);
		editor.commit();
	}


	public boolean isReminderOn()
	{
		return sharedPreferences.getBoolean("isReminderOn",true);
	}

	public void setReminder(boolean isReminderOn)
	{
		editor = sharedPreferences.edit();
		editor.putBoolean("isReminderOn", isReminderOn);
		editor.commit();
	}

	public void setInsightsActivated(int isInsightsActivated,String key) 
	{
		// TODO Auto-generated method stub
		editor = sharedPreferences.edit();
		editor.putInt(key+"InsightsActivated",isInsightsActivated);
		editor.commit();
	}

	public int isInsightsActivared(String key) 
	{		
		return sharedPreferences.getInt(key+"InsightsActivated",0);
	}


	public void setBadgeScore(String score) 
	{
		// TODO Auto-generated method stub
		editor = sharedPreferences.edit();
		editor.putString("BadgeScore",score);
		editor.commit();
	}

	public String getBadgeScore() 
	{		
		return sharedPreferences.getString("BadgeScore","0");
	}

	public void setNetworkTableCreated(boolean networkTableCreated)
	{
		editor = sharedPreferences.edit();
		editor.putBoolean("networkTableCreated", networkTableCreated);
		editor.commit();
	}
	public boolean isNetworkTableCreated()
	{
		return sharedPreferences.getBoolean("networkTableCreated",false);
	}

	public void setChildAdded(boolean childAdded)
	{
		editor = sharedPreferences.edit();
		editor.putBoolean("childAdded", childAdded);
		editor.commit();
	}
	public boolean isChildAdded()
	{
		return sharedPreferences.getBoolean("childAdded",false);
	}
}
