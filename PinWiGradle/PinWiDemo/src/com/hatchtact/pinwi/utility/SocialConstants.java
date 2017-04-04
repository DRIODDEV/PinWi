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
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
		prodViewedAction.put(SocialConstants.Device_Type, "Android");
		cleverTap.event.push(SocialConstants.Download_Status, prodViewedAction);
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

	public void parent_ProfileAnalyticsLog() {
		//Facebook Log
		logger.logEvent(SocialConstants.Parent_Profile);
		//Clevertap Log
		cleverTap.event.push(SocialConstants.Parent_Profile);
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
		cleverTap.event.push(SocialConstants.Child_Profile);
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
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
		prodViewedAction.put(SocialConstants.Button_Type, parameterValue);
		cleverTap.event.push(SocialConstants.Instant_Demo, prodViewedAction);

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
		HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
		prodViewedAction.put(SocialConstants.Button_Type, parameterValue);
		cleverTap.event.push(SocialConstants.Full_Version, prodViewedAction);

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

	public void insights_ActivatedAnalyticsLog() {

		//Facebook Log
		logger.logEvent(SocialConstants.Insights_Activated);
		//Clevertap Log
		cleverTap.event.push(SocialConstants.Insights_Activated);
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
		cleverTap.event.push(SocialConstants.Rating_Done, prodViewedAction);

		String label = SocialConstants.Rating_Done + "_" + parameterValue;
		String labelGA = SocialConstants.Points_Earned + ":" + parameterValue;

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
	/***************************************************New Events*************************************************/

}
