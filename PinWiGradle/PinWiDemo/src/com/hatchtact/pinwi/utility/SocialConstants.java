package com.hatchtact.pinwi.utility;

import android.content.Context;
import android.os.Bundle;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.exceptions.CleverTapMetaDataNotFoundException;
import com.clevertap.android.sdk.exceptions.CleverTapPermissionsNotSatisfied;
import com.facebook.appevents.AppEventsLogger;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hatchtact.pinwi.classmodel.ChildProfile;
import com.hatchtact.pinwi.classmodel.ParentProfile;

import java.util.HashMap;


public class SocialConstants {
	private Context mContext;
	private AppEventsLogger logger;
	private Tracker tracker;
	/**
	 * New Constants Events
	 */
	public static final String Device_Type = "Device_Type";
	public static final String Download_Status = "Download_Status";
	public static final String Parent_Profile = "Parent_Profile";
	public static final String Child_Profile = "Child_Profile";
	public static final String Instant_Demo = "Instant_Demo";
	public static final String Button_Type = "Button_Type";
	public static final String Full_Version = "Full_Version";
	public static final String Parent_Access = "Parent_Access";
	public static final String Tab_Type = "Tab_Type";
	public static final String Child_Access = "Child_Access";
	public static final String Parent_Tutorial = "Parent_Tutorial";
	public static final String Tutorial_Status = "Tutorial_Status";
	public static final String Insights_Activated = "Insights_Activated";
	public static final String Access_InviteFriend = "Access_InviteFriend";
	public static final String Invite_Type = "Invite_Type";
	public static final String Child_Tutorial = "Child_Tutorial";
	public static final String Rating_Done = "Rating_Done";
	public static final String Points_Earned = "Points_Earned";
	/**
	 * New Constants Events
	 */


	/**Category for GA****/
	//public static final String CATEGORY_MOBILE = "StagingApp";
	public static final String  CATEGORY_MOBILE="LiveApp";
	/**Category for GA****/


	private FirebaseAnalytics mFirebaseAnalytics;
	private CleverTapAPI cleverTap;

	public SocialConstants(Context context) {
		mContext = context;
		tracker = GoogleAnalytics.getInstance(mContext).getTracker("UA-86307141-1");
		logger = AppEventsLogger.newLogger(mContext);
		mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
		try {
			cleverTap = CleverTapAPI.getInstance(context);
		} catch (CleverTapMetaDataNotFoundException e) {
			// thrown if you haven't specified your CleverTap Account ID or Token in your AndroidManifest.xml
		} catch (CleverTapPermissionsNotSatisfied e) {
			// thrown if you haven't requested the required permissions in your AndroidManifest.xml
			System.out.print("" + e);
		}

	}



	/***************************************************
	 * New Events
	 *************************************************/
	public void download_StatusAnalyticsLog() {

		//Facebook Log
		Bundle parameters = new Bundle();
		parameters.putString(SocialConstants.Device_Type, "Android");
		logger.logEvent(SocialConstants.Download_Status, parameters);
		//Clevertap Log
		/*HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
		prodViewedAction.put(SocialConstants.Device_Type, "Android");
		cleverTap.event.push(SocialConstants.Download_Status, prodViewedAction);*/
		//Firebase Log
		String labelFirebase=SocialConstants.Download_Status + "_Android";
		mFirebaseAnalytics.logEvent(labelFirebase, null);

		//GA Log
		String label = SocialConstants.Device_Type + ":" + "Android";
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Download_Status,  // Event action (required)
						label,
						null)            // Event value
				.build()
		);
	}

	public void parent_ProfileAnalyticsLog() //New clevertap log added
	{
		//Facebook Log
		logger.logEvent(SocialConstants.Parent_Profile);
		//Clevertap Log
		//cleverTap.event.push(SocialConstants.Parent_Profile);
		cleverTap.event.push("Registered");
		String label = SocialConstants.Parent_Profile + ":Complete";
		String labelFirebase = SocialConstants.Parent_Profile + "_Complete";

		//Firebase Log
		mFirebaseAnalytics.logEvent(labelFirebase, null);
		//GA Log
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Parent_Profile,  // Event action (required)
						label,
						null)            // Event value
				.build()
		);
	}

	public void child_ProfileAnalyticsLog() {
		//Facebook Log
		logger.logEvent(SocialConstants.Child_Profile);
		//Clevertap Log
		//cleverTap.event.push(SocialConstants.Child_Profile);
		String label = SocialConstants.Child_Profile + ":Complete";
		String labelFirebase = SocialConstants.Child_Profile + "_Complete";

		//Firebase Log
		mFirebaseAnalytics.logEvent(labelFirebase, null);
		//GA Log
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Child_Profile,  // Event action (required)
						label,
						null)            // Event value
				.build()
		);
	}

	public void instant_DemoAnalyticsLog(String parameterValue) {

		//Facebook Log
		Bundle parameters = new Bundle();
		parameters.putString(SocialConstants.Button_Type, parameterValue);
		logger.logEvent(SocialConstants.Instant_Demo, parameters);
		//Clevertap Log
		/*HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
		prodViewedAction.put(SocialConstants.Button_Type, parameterValue);
		cleverTap.event.push(SocialConstants.Instant_Demo, prodViewedAction);*/

		String label = SocialConstants.Instant_Demo + "_" + parameterValue;
		String labelGA = SocialConstants.Button_Type + ":" + parameterValue;

		//Firebase Log
		mFirebaseAnalytics.logEvent(label, null);
		//GA Log
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Instant_Demo,  // Event action (required)
						labelGA,
						null)            // Event value
				.build()
		);
	}

	public void full_VersionAnalyticsLog(String parameterValue) {

		//Facebook Log
		Bundle parameters = new Bundle();
		parameters.putString(SocialConstants.Button_Type, parameterValue);
		logger.logEvent(SocialConstants.Full_Version, parameters);
		//Clevertap Log
		/*HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
		prodViewedAction.put(SocialConstants.Button_Type, parameterValue);
		cleverTap.event.push(SocialConstants.Full_Version, prodViewedAction);*/

		String label = SocialConstants.Full_Version + "_" + parameterValue;
		String labelGA = SocialConstants.Button_Type + ":" + parameterValue;

		//Firebase Log
		mFirebaseAnalytics.logEvent(label, null);
		//GA Log
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Full_Version,  // Event action (required)
						labelGA,
						null)            // Event value
				.build()
		);
	}

	public void parent_AccessAnalyticsLog(String parameterValue) {

		//Facebook Log
		Bundle parameters = new Bundle();
		parameters.putString(SocialConstants.Tab_Type, parameterValue);
		logger.logEvent(SocialConstants.Parent_Access, parameters);
		//Clevertap Log
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
		prodViewedAction.put(SocialConstants.Tab_Type, parameterValue);
		cleverTap.event.push(SocialConstants.Parent_Access, prodViewedAction);

		String label = SocialConstants.Parent_Access + "_" + parameterValue;
		String labelGA = SocialConstants.Tab_Type + ":" + parameterValue;

		//Firebase Log
		mFirebaseAnalytics.logEvent(label, null);
		//GA Log
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Parent_Access,  // Event action (required)
						labelGA,
						null)            // Event value
				.build()
		);
	}


	public void child_AccessAnalyticsLog(String parameterValue) {

		//Facebook Log
		Bundle parameters = new Bundle();
		parameters.putString(SocialConstants.Tab_Type, parameterValue);
		logger.logEvent(SocialConstants.Child_Access, parameters);
		//Clevertap Log
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
		prodViewedAction.put(SocialConstants.Tab_Type, parameterValue);
		cleverTap.event.push(SocialConstants.Child_Access, prodViewedAction);

		String label = SocialConstants.Child_Access + "_" + parameterValue;
		String labelGA = SocialConstants.Tab_Type + ":" + parameterValue;

		//Firebase Log
		mFirebaseAnalytics.logEvent(label, null);
		//GA Log
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Child_Access,  // Event action (required)
						labelGA,
						null)            // Event value
				.build()
		);
	}

	public void parent_TutorialAnalyticsLog(String parameterValue) {

		//Facebook Log
		Bundle parameters = new Bundle();
		parameters.putString(SocialConstants.Tutorial_Status, parameterValue);
		logger.logEvent(SocialConstants.Parent_Tutorial, parameters);
		//Clevertap Log
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
		prodViewedAction.put(SocialConstants.Tutorial_Status, parameterValue);
		prodViewedAction.put("NameofParent", StaticVariables.currentParentName);

		cleverTap.event.push(SocialConstants.Parent_Tutorial, prodViewedAction);

		String label = SocialConstants.Parent_Tutorial + ":" + parameterValue;
		String labelFirebase = SocialConstants.Parent_Tutorial + "_" + parameterValue;

		//Firebase Log
		mFirebaseAnalytics.logEvent(labelFirebase, null);
		//GA Log
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Parent_Tutorial,  // Event action (required)
						label,
						null)            // Event value
				.build()
		);

	}

	public void insights_ActivatedAnalyticsLog() //new clevertap log
	{

		//Facebook Log
		logger.logEvent(SocialConstants.Insights_Activated);
		//Clevertap Log
		//cleverTap.event.push(SocialConstants.Insights_Activated);
		/*HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();

		prodViewedAction.put("NameOfChild",StaticVariables.currentChild.getFirstName());
		cleverTap.event.push("Insights_Viewed",prodViewedAction);*/

		String label = SocialConstants.Insights_Activated + ":Yes";
		String labelFirebase = SocialConstants.Insights_Activated + "_Yes";

		//Firebase Log
		mFirebaseAnalytics.logEvent(labelFirebase, null);
		//GA Log
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Insights_Activated,  // Event action (required)
						label,
						null)            // Event value
				.build()
		);
	}
	public void insights_ViewedLog() //new clevertap log
	{
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
		prodViewedAction.put("NameOfChild",StaticVariables.currentChild.getFirstName());
		cleverTap.event.push("Insights_Viewed",prodViewedAction);

	}
	public void access_InviteFriendAnalyticsLog(String parameterValue) {

		//Facebook Log
		Bundle parameters = new Bundle();
		parameters.putString(SocialConstants.Invite_Type, parameterValue);
		logger.logEvent(SocialConstants.Access_InviteFriend, parameters);
		//Clevertap Log
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
		prodViewedAction.put(SocialConstants.Invite_Type, parameterValue);
		cleverTap.event.push(SocialConstants.Access_InviteFriend, prodViewedAction);

		String label = SocialConstants.Access_InviteFriend + "_" + parameterValue;
		String labelGA = SocialConstants.Invite_Type + ":" + parameterValue;

		//Firebase Log
		mFirebaseAnalytics.logEvent(label, null);
		//GA Log
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Access_InviteFriend,  // Event action (required)
						labelGA,
						null)            // Event value
				.build()
		);
	}

	public void childTutorialFriendAnalyticsLog(String parameterValue) {

		//Facebook Log
		Bundle parameters = new Bundle();
		parameters.putString(SocialConstants.Tutorial_Status, parameterValue);
		logger.logEvent(SocialConstants.Child_Tutorial, parameters);
		//Clevertap Log
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
		prodViewedAction.put(SocialConstants.Tutorial_Status, parameterValue);
		prodViewedAction.put("NameOfChild", StaticVariables.currentChild.getFirstName());

		cleverTap.event.push(SocialConstants.Child_Tutorial, prodViewedAction);

		String label = SocialConstants.Child_Tutorial + ":" + parameterValue;
		String labelFirebase = SocialConstants.Child_Tutorial + "_" + parameterValue;

		//Firebase Log
		mFirebaseAnalytics.logEvent(labelFirebase, null);
		//GA Log
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Child_Tutorial,  // Event action (required)
						label,
						null)            // Event value
				.build()
		);
	}

	public void rating_DoneAnalyticsLog(String parameterValue) {

		//Facebook Log
		Bundle parameters = new Bundle();
		parameters.putString(SocialConstants.Points_Earned, parameterValue);
		logger.logEvent(SocialConstants.Rating_Done, parameters);
		//Clevertap Log
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
		prodViewedAction.put(SocialConstants.Points_Earned, parameterValue);
		prodViewedAction.put("Nameofchild", StaticVariables.currentChild.getFirstName());

		cleverTap.event.push("Child_Ratings_Provided", prodViewedAction);

		String label = SocialConstants.Rating_Done + "_" + parameterValue;
		String labelGA = SocialConstants.Points_Earned + ":" + parameterValue;

		//Firebase Log
		mFirebaseAnalytics.logEvent(label, null);
		//GA Log
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Rating_Done,  // Event action (required)
						labelGA,
						null)            // Event value
				.build()
		);
	}
	/***************************************************New Events*************************************************/

	public void userProfileClevertap(String key, String parameterValue, int type, ParentProfile parentProfile, ChildProfile childProfile, int parentId, int currentChild) {


		//Clevertap UserProfile
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();


		switch (type)
		{
			case 1://for single value
				prodViewedAction.put("Identity",StaticVariables.currentParentId);  // String or number
				prodViewedAction.put("Name",StaticVariables.currentParentName);
				prodViewedAction.put(key, parameterValue);
				break;
			case 2://for parent updation
				prodViewedAction.put("Name",parentProfile.getFirstName());
				prodViewedAction.put("Email",parentProfile.getEmailAddress());
				prodViewedAction.put("Identity", parentProfile.getParentID());  // String or number

				if(parentProfile.getContact()!=null)
				{
					if(parentProfile.getContact().trim().length()==0)
					{
						parentProfile.setContact("");
					}
				}
				if(parentProfile.getContact().trim().startsWith("91"))
				{
					prodViewedAction.put("Phone","+"+parentProfile.getContact());
				}
				else if(parentProfile.getContact().trim().startsWith("+91"))
				{
					prodViewedAction.put("Phone",parentProfile.getContact());
				}
				else
				{
					prodViewedAction.put("Phone","+91"+parentProfile.getContact());
				}

				prodViewedAction.put("Type", parentProfile.getRelation());
				if(parentProfile.getDateOfBirth()!=null)
				{
					if(parentProfile.getDateOfBirth().trim().length()==0)
					{
						parentProfile.setDateOfBirth("01/01/1900");
					}
				}
				prodViewedAction.put("Parent_DOB", parentProfile.getDateOfBirth());
				break;
			case 3://for child updation
				String baseKey="Child"+currentChild;
				prodViewedAction.put(baseKey+"_Name",childProfile.getFirstName());
				prodViewedAction.put("Identity",parentId);  // String or number//need to check this
				prodViewedAction.put(baseKey+"_Nickname",childProfile.getNickName());
				if(childProfile.getDateOfBirth()!=null)
				{
					if(childProfile.getDateOfBirth().trim().length()==0)
					{
						childProfile.setDateOfBirth("01/01/1900");
					}
				}
				prodViewedAction.put(baseKey+"_DOB",childProfile.getDateOfBirth());
				prodViewedAction.put(baseKey+"_Gender",childProfile.getGender());
				prodViewedAction.put(baseKey+"_School",childProfile.getSchoolName());

				break;
		}
		 cleverTap.onUserLogin(prodViewedAction);
	    // cleverTap.profile.push(prodViewedAction);


	}

	public void loginLogClevertap()//New clevertap log added
	{
		cleverTap.event.push("Login");
	}

	public void demoReportRequestedClevertap(String value)//New clevertap log added
	{
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
		prodViewedAction.put("Child_Chosen",value);//FIRST NAME IS SENT
		cleverTap.event.push("Demo_Report_Requested", prodViewedAction);
	}


	public void SchedulerSubjectAddedClevertap(String nickname,String subjectname)//New clevertap log added
	{
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();

		prodViewedAction.put("NameOfChild",nickname);
		prodViewedAction.put("Subject_Name",subjectname);

		cleverTap.event.push("Scheduler_Subject_Added", prodViewedAction);
	}

	public void SchedulerActivityAddedClevertap(String nickname,String activitytname/*,String category*/)//New clevertap log added
	{
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();

		//prodViewedAction.put("Category",category);
		prodViewedAction.put("Activity",activitytname);
		prodViewedAction.put("NameOfChild",nickname);

		cleverTap.event.push("Scheduler_Activity_Added", prodViewedAction);
	}

	public void WhattoDoActivityAddedClevertap(String nickname,String activitytname)//New clevertap log added
	{
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();

		//prodViewedAction.put("Category",category);
		prodViewedAction.put("Activity",activitytname);
		prodViewedAction.put("NameOfChild",nickname);

		cleverTap.event.push("WhatToDo_Activity_Scheduled", prodViewedAction);
	}

	public void childProfileViewedClevertap(String childName,String parentName)//New clevertap log added
	{
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();

		prodViewedAction.put("NameofChild",childName);
		prodViewedAction.put("NameofParent",parentName);

		cleverTap.event.push("Child_Profile_Viewed", prodViewedAction);
	}
	public void parentProfileViewedClevertap(String parentName)//New clevertap log added
	{
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();

		prodViewedAction.put("NameofParent",parentName);

		cleverTap.event.push("Parent_Profile_Viewed", prodViewedAction);
	}
	public void connectedRemovedClevertap(String parentName)//New clevertap log added
	{
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();

		prodViewedAction.put("NameofParent",parentName);

		cleverTap.event.push("Connection_Removed", prodViewedAction);
	}
	public void connectedAcceptedClevertap(String parentName)//New clevertap log added
	{
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();

		prodViewedAction.put("NameofParent",parentName);

		cleverTap.event.push("Connection_Accepted", prodViewedAction);
	}
	public void connectedAddedClevertap(String parentName)//New clevertap log added
	{
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();

		prodViewedAction.put("NameofParent",parentName);

		cleverTap.event.push("Connection_Added", prodViewedAction);
	}

	public void childActivityWishlistedClevertap(String Activity,String childName)//New clevertap log added
	{
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();

		prodViewedAction.put("Activity",Activity);
		prodViewedAction.put("NameofChild",childName);

		cleverTap.event.push("Child_Activity_Wishlisted", prodViewedAction);
	}

	public void buddyRemovedClevertap(String childName)//New clevertap log added
	{
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();

		prodViewedAction.put("NameofChild",childName);

		cleverTap.event.push("Buddy_Removed", prodViewedAction);
	}
	public void buddyAcceptedClevertap(String childName)//New clevertap log added
	{
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();

		prodViewedAction.put("NameofChild",childName);

		cleverTap.event.push("Buddy_Accepted", prodViewedAction);
	}
	public void buddyAddedClevertap(String childName)//New clevertap log added
	{
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();

		prodViewedAction.put("NameofChild",childName);

		cleverTap.event.push("Buddy_Added", prodViewedAction);
	}

	public void postcardAddedClevertap(String postcardName,String type)//New clevertap log added
	{
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();

		prodViewedAction.put("NameOfPostcard",postcardName);
		prodViewedAction.put("Type",type);

		cleverTap.event.push("Postcard_Added", prodViewedAction);
	}

	public void playwllReactionAddedClevertap(String childName)//New clevertap log added
	{
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();

		prodViewedAction.put("NameofChild",childName);

		cleverTap.event.push("Playwall_Reaction_Added", prodViewedAction);
	}
	public void logoutClevertap()//New clevertap log added
	{


		cleverTap.event.push("Logout");
	}

	public void whattodoviewedClevertap(String childName)//New clevertap log added
	{
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();

		prodViewedAction.put("NameofChild",childName);

		cleverTap.event.push("WhattodoViewed", prodViewedAction);
	}


	public void pushfire(String regid)//New clevertap log added
	{
		cleverTap.data.pushGcmRegistrationId(regid, true);

	}


}
