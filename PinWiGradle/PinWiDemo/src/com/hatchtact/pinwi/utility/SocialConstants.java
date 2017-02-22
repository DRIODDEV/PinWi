package com.hatchtact.pinwi.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.facebook.appevents.AppEventsLogger;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;


public class SocialConstants
{
	private Context mContext;
	private AppEventsLogger logger;
	private Tracker tracker;

	public static final String DeviceType="Device_Type";
	public static final String OSType="OS_Type";
	public static final String  OSVersion="OS_Version";
	public static final String  Downloaded_App="Downloaded_App";
	public static final String  Registration_Method="Registration_Method";
	//public static final String  Registration_Completed="Registration_Completed";
	public static final String  Create_Parent_Profile="Create_Parent_Profile";
	public static final String  Create_Child_Profile="Create_Child_Profile";
	public static final String  Time_of_the_day="Time_of_the_day";
	public static final String  Number_of_times_a_Day="Number_of_times_a_Day";
	public static final String  App_Launch	="App_Launch";
	public static final String  CompletedTutorial	="Completed_Tutorial";
	//public static final String  ViewContent	="ViewContent";	
	public static final String  Access_Parent_Profile="Access_Parent_Profile";
	public static final String  Access_Child_Profile="Access_Child_Profile";
	public static final String  Schedule_First_Activity="Schedule_First_Activity";
	public static final String  Schedule_SchoolActivity="Schedule_SchoolActivity";
	public static final String  Schedule_AfterSchoolActivity="Schedule_AfterSchoolActivity";
	public static final String  Date="Date";
	public static final String  Reacted_to_Post="Reacted_to_Post";	//30
	public static final String  Add_Buddy="Add_Buddy";	//29
	public static final String  Buddy_Requests_Sent="Buddy_Requests_Sent";//28	
	public static final String  Create_Postcard_Photo="Create_Postcard_Photo";//27	
	public static final String  Create_Postcard_VO="Create_Postcard_VO";//26	
	public static final String  Create_Postcard_Text="Create_Postcard_Text";//25	
	public static final String  Add_to_Wishlist="Add_to_Wishlist";//24	
	public static final String  Child_Ratings="Child_Ratings";//23	
	public static final String  Quality_Badge_Level="Quality_Badge_Level";//16	
	public static final String  View_What_to_Do="View_What_to_Do";//17	
	public static final String  Schedule_Activity_from_WTD="Schedule_Activity_from_WTD";//18	
	public static final String  Network_Connection_Added="Network_Connection_Added";//19
	public static final String  Network_Connection_Accepted="Network_Connection_Accepted";//20	
	public static final String  LEVEL="Level";
	public static final String  Insights_Activated="Insights_Activated";
	public static final String  Insights_Subscribed="Insights_Subscribed";
	public static final String  Subscribe_Button_Clicked="Subscribe_Button_Clicked";
	public static final String  CATEGORY_MOBILE="Mobile";
	//public static final String  CATEGORY_MOBILE="MobileProduction";

	private FirebaseAnalytics mFirebaseAnalytics;

	public SocialConstants(Context context)
	{
		mContext=context;
		tracker = GoogleAnalytics.getInstance(mContext).getTracker("UA-86307141-1");
		logger = AppEventsLogger.newLogger(mContext);
		mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);

	}
	/**
	 *
	 */
	public void parentRegistrationGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */

		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(SocialConstants.Registration_Method,"Email");
		tracker.send(hitParameters);*/
		String label="Email";
		/*tracker.send(new HitBuilders.EventBuilder()
	    .setCategory("Mobile")
	    .setAction(SocialConstants.Registration_Method)
	    .setLabel(SocialConstants.Registration_Method).
	    .build());*/
		label=SocialConstants.Registration_Method+":"+label;
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Create_Parent_Profile,  // Event action (required)
						label,
						null)            // Event value
				.build()
		);
		/*tracker.send(MapBuilder
				.createEvent(SocialConstants.Create_Parent_Profile,// Event category (required)
						SocialConstants.Registration_Method,  // Event action (required)
						label,
						null)            // Event value
						.build()
				);*/
	}

	/**
	 *
	 */
	public void childRegistrationGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */

		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(SocialConstants.Registration_Method,"Email");
		tracker.send(hitParameters);*/
		//String label="Email";
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Create_Child_Profile,  // Event action (required)
						null,
						null)            // Event value
				.build()
		);
		/*tracker.send(MapBuilder
				.createEvent(SocialConstants.Create_Child_Profile,// Event category (required)
						SocialConstants.Registration_Method,  // Event action (required)
						label,
						null)            // Event value
						.build()
				);*/
	}
	/**
	 *
	 */
	public void childRegistrationFacebookLog() {
		//Bundle parameters = new Bundle();
		//parameters.putString(SocialConstants.Registration_Method,"Email");

		logger.logEvent(SocialConstants.Create_Child_Profile);
		mFirebaseAnalytics.logEvent(SocialConstants.Create_Child_Profile,null);
	}

	/**
	 *
	 */
	public void parentRegistrationFacebookLog() {
		Bundle parameters = new Bundle();
		parameters.putString(SocialConstants.Registration_Method,"Email");

		logger.logEvent(SocialConstants.Create_Parent_Profile,parameters);

		Bundle bundle = new Bundle();
		bundle.putString(SocialConstants.Registration_Method,"Email");
		mFirebaseAnalytics.logEvent(SocialConstants.Create_Parent_Profile, bundle);
	}


	/**
	 *
	 */
	public void CompletedTutorialGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */

//		HashMap<String, String> hitParameters = new HashMap<String, String>();
//		hitParameters.put(SocialConstants.CompletedTutorial,SocialConstants.CompletedTutorial);
//		tracker.send(hitParameters);
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.CompletedTutorial,  // Event action (required)
						null,
						null)            // Event value
				.build()
		);
	}
	/**
	 *
	 */
	public void CompletedTutorialFacebookLog() {
		//Bundle parameters = new Bundle();
		//parameters.putString(SocialConstants.CompletedTutorial,SocialConstants.CompletedTutorial);

		logger.logEvent(SocialConstants.CompletedTutorial);
		mFirebaseAnalytics.logEvent(SocialConstants.CompletedTutorial,null);
	}

	/**
	 *
	 */
	public void Access_Parent_ProfileGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */

		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String strTime = sdf.format(c.getTime());
		SimpleDateFormat sdDate = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdDate.format(c.getTime());
		hitParameters.put(SocialConstants.Time_of_the_day,strTime);
		hitParameters.put(SocialConstants.Number_of_times_a_Day,strDate);

		tracker.send(hitParameters);*/
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String strTime = sdf.format(c.getTime());
		SimpleDateFormat sdDate = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdDate.format(c.getTime());
		/*hitParameters.put(SocialConstants.Time_of_the_day,strTime);
		hitParameters.put(SocialConstants.Number_of_times_a_Day,strDate);

		tracker.send(hitParameters);*/
		String label=SocialConstants.Time_of_the_day+":"+strTime+","
				+SocialConstants.Number_of_times_a_Day+":"+strDate;
		/*tracker.send(new HitBuilders.EventBuilder()
	    .setCategory("Mobile")
	    .setAction(SocialConstants.Registration_Method)
	    .setLabel(SocialConstants.Registration_Method).
	    .build());*/
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Access_Parent_Profile,  // Event action (required)
						null,
						null)            // Event value
				.build()
		);
	}
	/**
	 *
	 */
	public void Access_Parent_ProfileFacebookLog() {

		Bundle parameters = new Bundle();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String strTime = sdf.format(c.getTime());
		SimpleDateFormat sdDate = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdDate.format(c.getTime());

		parameters.putString(SocialConstants.Time_of_the_day,strTime);
		parameters.putString(SocialConstants.Number_of_times_a_Day,strDate);
		logger.logEvent(SocialConstants.Access_Parent_Profile/*,null*/);

		Bundle bundle = new Bundle();
		bundle.putString(SocialConstants.Time_of_the_day,strTime);
		bundle.putString(SocialConstants.Number_of_times_a_Day,strDate);
		mFirebaseAnalytics.logEvent(SocialConstants.Access_Parent_Profile, null);
	}

	/**
	 *
	 */
	public void Access_Child_ProfileGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */

		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String strTime = sdf.format(c.getTime());
		SimpleDateFormat sdDate = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdDate.format(c.getTime());
		hitParameters.put(SocialConstants.Time_of_the_day,strTime);
		hitParameters.put(SocialConstants.Number_of_times_a_Day,strDate);

		tracker.send(hitParameters);*/
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String strTime = sdf.format(c.getTime());
		SimpleDateFormat sdDate = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdDate.format(c.getTime());
		/*hitParameters.put(SocialConstants.Time_of_the_day,strTime);
		hitParameters.put(SocialConstants.Number_of_times_a_Day,strDate);

		tracker.send(hitParameters);*/
		String label=SocialConstants.Time_of_the_day+":"+strTime+","
				+SocialConstants.Number_of_times_a_Day+":"+strDate;
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Access_Child_Profile,  // Event action (required)
						null,
						null)            // Event value
				.build()
		);
	}
	/**
	 *
	 */
	public void Access_Child_ProfileFacebookLog() {

		Bundle parameters = new Bundle();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String strTime = sdf.format(c.getTime());
		SimpleDateFormat sdDate = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdDate.format(c.getTime());

		parameters.putString(SocialConstants.Time_of_the_day,strTime);
		parameters.putString(SocialConstants.Number_of_times_a_Day,strDate);
		logger.logEvent(SocialConstants.Access_Child_Profile/*,parameters*/);

		Bundle bundle = new Bundle();
		bundle.putString(SocialConstants.Time_of_the_day,strTime);
		bundle.putString(SocialConstants.Number_of_times_a_Day,strDate);
		mFirebaseAnalytics.logEvent(SocialConstants.Access_Child_Profile, null);
	}
	/**
	 *
	 */
	public void scheduleFirstActivityGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */
		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String strTime = sdf.format(c.getTime());
		SimpleDateFormat sdDate = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdDate.format(c.getTime());
		hitParameters.put(SocialConstants.Time_of_the_day,strTime);
		hitParameters.put(SocialConstants.Date,strDate);
		tracker.send(hitParameters);*/
		/*Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String strTime = sdf.format(c.getTime());
		SimpleDateFormat sdDate = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdDate.format(c.getTime());

		String label=SocialConstants.Time_of_the_day+":"+strTime+","
				+SocialConstants.Date+":"+strDate;
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Schedule_First_Activity,  // Event action (required)
						label,
						null)            // Event value
				.build()
		);*/
	}
	/**
	 *
	 */
	public void scheduleFirstActivityFacebookLog()
	{
		/*Bundle parameters = new Bundle();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String strTime = sdf.format(c.getTime());
		SimpleDateFormat sdDate = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdDate.format(c.getTime());

		parameters.putString(SocialConstants.Time_of_the_day,strTime);
		parameters.putString(SocialConstants.Number_of_times_a_Day,strDate);

		logger.logEvent(SocialConstants.Schedule_First_Activity,parameters);
		Bundle bundle = new Bundle();
		bundle.putString(SocialConstants.Time_of_the_day,strTime);
		bundle.putString(SocialConstants.Number_of_times_a_Day,strDate);
		mFirebaseAnalytics.logEvent(SocialConstants.Schedule_First_Activity, bundle);*/
	}



	/**
	 *
	 */
	public void scheduleSchoolActivityGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */
		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(SocialConstants.Frequency_of_adding_SchoolActivity,SocialConstants.Frequency_of_adding_SchoolActivity);
		tracker.send(hitParameters);*/

		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Schedule_SchoolActivity,  // Event action (required)
						null,
						null)            // Event value
				.build()
		);
	}
	/**
	 *
	 */
	public void scheduleSchoolActivityFacebookLog()
	{
		logger.logEvent(SocialConstants.Schedule_SchoolActivity);
		mFirebaseAnalytics.logEvent(SocialConstants.Schedule_SchoolActivity,null);
	}


	/**
	 *
	 */
	public void scheduleAfterSchoolActivityGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */
	/*	HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(SocialConstants.Frequency_of_adding_AfterSchoolActivity,SocialConstants.Frequency_of_adding_AfterSchoolActivity);
		tracker.send(hitParameters);*/
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Schedule_AfterSchoolActivity,  // Event action (required)
						null,
						null)            // Event value
				.build()
		);
	}
	/**
	 *
	 */
	public void scheduleAfterSchoolActivityFacebookLog()
	{
		logger.logEvent(SocialConstants.Schedule_AfterSchoolActivity);
		mFirebaseAnalytics.logEvent(SocialConstants.Schedule_AfterSchoolActivity,null);
	}


	/**
	 *
	 */
	public void reactedToPostGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */
		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(SocialConstants.Reacted_to_Post,SocialConstants.Frequency_of_adding_SchoolActivity);
		tracker.send(hitParameters);*/
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Reacted_to_Post,  // Event action (required)
						null,
						null)            // Event value
				.build()
		);
	}
	/**
	 *
	 */
	public void reactedToPostFacebookLog()
	{
		logger.logEvent(SocialConstants.Reacted_to_Post);
		mFirebaseAnalytics.logEvent(SocialConstants.Reacted_to_Post, null);
	}

	/**
	 *
	 */
	public void addBuddyGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */
		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(SocialConstants.Reacted_to_Post,SocialConstants.Frequency_of_adding_SchoolActivity);
		tracker.send(hitParameters);*/
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Add_Buddy,  // Event action (required)
						null,
						null)            // Event value
				.build()
		);
	}
	/**
	 *
	 */
	public void addBuddyFacebookLog()
	{
		logger.logEvent(SocialConstants.Add_Buddy);
		mFirebaseAnalytics.logEvent(SocialConstants.Add_Buddy, null);
	}


	/**
	 *
	 */
	public void buddyRequestSentGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */
		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(SocialConstants.Reacted_to_Post,SocialConstants.Frequency_of_adding_SchoolActivity);
		tracker.send(hitParameters);*/
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Buddy_Requests_Sent,  // Event action (required)
						null,
						null)            // Event value
				.build()
		);
	}
	/**
	 *
	 */
	public void buddyRequestSentFacebookLog()
	{
		logger.logEvent(SocialConstants.Buddy_Requests_Sent);
		mFirebaseAnalytics.logEvent(SocialConstants.Buddy_Requests_Sent, null);
	}

	/**
	 *
	 */
	public void Create_Postcard_PhotoGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */
		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(SocialConstants.Reacted_to_Post,SocialConstants.Frequency_of_adding_SchoolActivity);
		tracker.send(hitParameters);*/
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Create_Postcard_Photo,  // Event action (required)
						null,
						null)            // Event value
				.build()
		);
	}
	/**
	 *
	 */
	public void Create_Postcard_PhotoSentFacebookLog()
	{
		logger.logEvent(SocialConstants.Create_Postcard_Photo);
		mFirebaseAnalytics.logEvent(SocialConstants.Create_Postcard_Photo, null);
	}

	/**
	 *
	 */
	public void Create_Postcard_VOGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */
		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(SocialConstants.Reacted_to_Post,SocialConstants.Frequency_of_adding_SchoolActivity);
		tracker.send(hitParameters);*/
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Create_Postcard_VO,  // Event action (required)
						null,
						null)            // Event value
				.build()
		);
	}
	/**
	 *
	 */
	public void Create_Postcard_VOSentFacebookLog()
	{
		logger.logEvent(SocialConstants.Create_Postcard_VO);
		mFirebaseAnalytics.logEvent(SocialConstants.Create_Postcard_VO, null);
	}



	/**
	 *
	 */
	public void Create_Postcard_TextGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */
		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(SocialConstants.Reacted_to_Post,SocialConstants.Frequency_of_adding_SchoolActivity);
		tracker.send(hitParameters);*/
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Create_Postcard_Text,  // Event action (required)
						null,
						null)            // Event value
				.build()
		);
	}
	/**
	 *
	 */
	public void Create_Postcard_TextSentFacebookLog()
	{
		logger.logEvent(SocialConstants.Create_Postcard_Text);
		mFirebaseAnalytics.logEvent(SocialConstants.Create_Postcard_Text, null);
	}



	/**
	 *
	 */
	public void Add_to_WishlistGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */
		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(SocialConstants.Reacted_to_Post,SocialConstants.Frequency_of_adding_SchoolActivity);
		tracker.send(hitParameters);*/
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Add_to_Wishlist,  // Event action (required)
						null,
						null)            // Event value
				.build()
		);
	}
	/**
	 *
	 */
	public void Add_to_WishlistFacebookLog()
	{
		logger.logEvent(SocialConstants.Add_to_Wishlist);
		mFirebaseAnalytics.logEvent(SocialConstants.Add_to_Wishlist, null);
	}

	/**
	 *
	 */
	public void Child_RatingsGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */
		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(SocialConstants.Reacted_to_Post,SocialConstants.Frequency_of_adding_SchoolActivity);
		tracker.send(hitParameters);*/
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Child_Ratings,  // Event action (required)
						null,
						null)            // Event value
				.build()
		);
	}
	/**
	 *
	 */
	public void Child_RatingsFacebookLog()
	{
		logger.logEvent(SocialConstants.Child_Ratings);
		mFirebaseAnalytics.logEvent(SocialConstants.Child_Ratings, null);
	}




	/**
	 *
	 */
	public void Network_Connection_AcceptedGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */
		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(SocialConstants.Reacted_to_Post,SocialConstants.Frequency_of_adding_SchoolActivity);
		tracker.send(hitParameters);*/
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Network_Connection_Accepted,  // Event action (required)
						null,
						null)            // Event value
				.build()
		);
	}
	/**
	 *
	 */
	public void Network_Connection_AcceptedFacebookLog()
	{
		logger.logEvent(SocialConstants.Network_Connection_Accepted);
		mFirebaseAnalytics.logEvent(SocialConstants.Network_Connection_Accepted, null);
	}

	/**
	 *
	 */
	public void Network_Connection_AddedGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */
		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(SocialConstants.Reacted_to_Post,SocialConstants.Frequency_of_adding_SchoolActivity);
		tracker.send(hitParameters);*/
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Network_Connection_Added,  // Event action (required)
						null,
						null)            // Event value
				.build()
		);
	}
	/**
	 *
	 */
	public void Network_Connection_AddedFacebookLog()
	{
		logger.logEvent(SocialConstants.Network_Connection_Added);
		mFirebaseAnalytics.logEvent(SocialConstants.Network_Connection_Added, null);

	}

	/**
	 *
	 */
	public void Schedule_Activity_from_WTDGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */
		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(SocialConstants.Reacted_to_Post,SocialConstants.Frequency_of_adding_SchoolActivity);
		tracker.send(hitParameters);*/
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Schedule_Activity_from_WTD,  // Event action (required)
						null,
						null)            // Event value
				.build()
		);
	}
	/**
	 *
	 */
	public void Schedule_Activity_from_WTDFacebookLog()
	{
		logger.logEvent(SocialConstants.Schedule_Activity_from_WTD);
		mFirebaseAnalytics.logEvent(SocialConstants.Schedule_Activity_from_WTD, null);

	}



	/**
	 *
	 */
	public void View_What_to_DoGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */
		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(SocialConstants.Reacted_to_Post,SocialConstants.Frequency_of_adding_SchoolActivity);
		tracker.send(hitParameters);*/
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.View_What_to_Do,  // Event action (required)
						null,
						null)            // Event value
				.build()
		);
	}
	/**
	 *
	 */
	public void View_What_to_DoFacebookLog()
	{
		logger.logEvent(SocialConstants.View_What_to_Do);
		mFirebaseAnalytics.logEvent(SocialConstants.View_What_to_Do, null);

	}




	/**
	 */
	public void Quality_Badge_LevelGoogleAnalyticsLog(String levelValue) {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */
		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(SocialConstants.Reacted_to_Post,SocialConstants.Frequency_of_adding_SchoolActivity);
		tracker.send(hitParameters);*/
		String label=SocialConstants.LEVEL+":"+levelValue;
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Quality_Badge_Level,  // Event action (required)
						label,
						null)            // Event value
				.build()
		);
	}
	/**
	 *
	 */
	public void 	Quality_Badge_LevelFacebookLog(String levelValue)
	{
		Bundle parameters = new Bundle();
		parameters.putString(SocialConstants.LEVEL,levelValue);

		logger.logEvent(SocialConstants.Quality_Badge_Level,parameters);

		Bundle bundle = new Bundle();
		bundle.putString(SocialConstants.LEVEL,levelValue);
		mFirebaseAnalytics.logEvent(SocialConstants.Quality_Badge_Level, bundle);

	}


	/**
	 *
	 */
	public void Insights_ActivatedGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */
		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(SocialConstants.Reacted_to_Post,SocialConstants.Frequency_of_adding_SchoolActivity);
		tracker.send(hitParameters);*/
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Insights_Activated,  // Event action (required)
						null,
						null)            // Event value
				.build()
		);

	}
	/**
	 *
	 */
	public void Insights_ActivatedFacebookLog()
	{
		logger.logEvent(SocialConstants.Insights_Activated);
		mFirebaseAnalytics.logEvent(SocialConstants.Insights_Activated, null);
	}

	/**
	 * @param value
	 */
	public void Insights_SubscribedGoogleAnalyticsLog(String value) {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */
		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(SocialConstants.Reacted_to_Post,SocialConstants.Frequency_of_adding_SchoolActivity);
		tracker.send(hitParameters);*/
		tracker.send(MapBuilder
				.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
						SocialConstants.Insights_Subscribed,  // Event action (required)
						value,
						null)            // Event value
				.build()
		);
	}
	/**
	 *
	 */
	public void Insights_SubscribedFacebookLog(String value)
	{
		Bundle parameters = new Bundle();
		parameters.putString(SocialConstants.Insights_Subscribed,value);

		logger.logEvent(SocialConstants.Insights_Subscribed,parameters);
		Bundle bundle = new Bundle();
		bundle.putString(SocialConstants.Insights_Subscribed,value);
		mFirebaseAnalytics.logEvent(SocialConstants.Insights_Subscribed, bundle);
	}

	/**
	 */
	public void Subscribe_Button_ClickedGoogleAnalyticsLog() {
		/*
		 * Send a screen view to Google Analytics by setting a map of parameter
		 * values on the tracker and calling send.
		 */
		/*HashMap<String, String> hitParameters = new HashMap<String, String>();
		hitParameters.put(SocialConstants.Reacted_to_Post,SocialConstants.Frequency_of_adding_SchoolActivity);
		tracker.send(hitParameters);*/
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// Do something after 7minutes = 420000ms
				tracker.send(MapBuilder
						.createEvent(SocialConstants.CATEGORY_MOBILE,// Event category (required)
								SocialConstants.Subscribe_Button_Clicked,  // Event action (required)
								"Did_Not_Complete",
								null)            // Event value
						.build()
				);	    }
		}, 420000);

	}
	/**
	 *
	 */
	public void 	Subscribe_Button_ClickedFacebookLog()
	{
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// Do something after 7minutes = 420000ms
				Bundle parameters = new Bundle();
				parameters.putString(SocialConstants.Subscribe_Button_Clicked,"Did_Not_Complete");
				logger.logEvent(SocialConstants.Subscribe_Button_Clicked,parameters);
				Bundle bundle = new Bundle();
				bundle.putString(SocialConstants.Subscribe_Button_Clicked,"Did_Not_Complete");
				mFirebaseAnalytics.logEvent(SocialConstants.Subscribe_Button_Clicked, bundle);
			}
		}, 420000);

	}

}

