package com.hatchtact.pinwi.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import com.facebook.appevents.AppEventsLogger;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;


public class SocialConstants
{
	private Context mContext;
	private AppEventsLogger logger;
	private Tracker tracker;

	public static final String DeviceType=" DeviceType";
	public static final String OSType=" OSType";
	public static final String  OSVersion="  OSVersion";
	public static final String  Downloaded_App="Downloaded_App";
	public static final String  Registration_Method="Registration_Method";
	//public static final String  Registration_Completed="Registration_Completed";
	public static final String  Create_Parent_Profile="Create_Parent_Profile";
	public static final String  Create_Child_Profile="Create_Child_Profile";
	public static final String  Time_of_the_day="Time_of_the_day";
	public static final String  Number_of_times_a_Day="Number_of_times_a_Day";	
	public static final String  App_Launch	="App_Launch";	
	public static final String  CompletedTutorial	="CompletedTutorial";	
	//public static final String  ViewContent	="ViewContent";	
	public static final String  Access_Parent_Profile="Access_Parent_Profile";	
	public static final String  Access_Child_Profile="Access_Child_Profile";	
	public static final String  Schedule_First_Activity="Schedule_First_Activity";	
	public static final String  Schedule_SchoolActivity="Schedule_SchoolActivity";	
	public static final String  Schedule_AfterSchoolActivity="Schedule_AfterSchoolActivity";	
	public static final String  Date="Date";	
	public static final String  Frequency_of_adding_SchoolActivity="Frequency_of_adding_SchoolActivity";	
	public static final String  Frequency_of_adding_AfterSchoolActivity="Frequency_of_adding_AfterSchoolActivity";	
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
	public static final String  LEVEL="LEVEL";
	public static final String  Insights_Activated="Insights_Activated";
	public static final String  Insights_Subscribed="Insights_Subscribed";	
	public static final String  Subscribe_Button_Clicked="Subscribe_Button_Clicked";
	public static final String  STATUS="STATUS";


	public SocialConstants(Context context)
	{
		mContext=context;
		tracker = GoogleAnalytics.getInstance(mContext).getTracker("UA-86307141-1");
		logger = AppEventsLogger.newLogger(mContext);
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
		tracker.send(MapBuilder
				.createEvent(SocialConstants.Create_Parent_Profile,// Event category (required)
						SocialConstants.Registration_Method,  // Event action (required)
						label,
						null)            // Event value
						.build()
				);
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
		String label="Email";
		tracker.send(MapBuilder
				.createEvent(SocialConstants.Create_Child_Profile,// Event category (required)
						SocialConstants.Registration_Method,  // Event action (required)
						label,
						null)            // Event value
						.build()
				);
	}
	/**
	 * 
	 */
	public void childRegistrationFacebookLog() {
		Bundle parameters = new Bundle();
		parameters.putString(SocialConstants.Registration_Method,"Email");

		logger.logEvent(SocialConstants.Create_Child_Profile,parameters);
	}

	/**
	 * 
	 */
	public void parentRegistrationFacebookLog() {
		Bundle parameters = new Bundle();
		parameters.putString(SocialConstants.Registration_Method,"Email");

		logger.logEvent(SocialConstants.Create_Parent_Profile,parameters);
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
				.createEvent(SocialConstants.CompletedTutorial,// Event category (required)
						null,  // Event action (required)
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
		tracker.send(MapBuilder
				.createEvent(SocialConstants.Access_Parent_Profile,// Event category (required)
						SocialConstants.Access_Parent_Profile,  // Event action (required)
						label,
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
		logger.logEvent(SocialConstants.Access_Parent_Profile,parameters);
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
				.createEvent(SocialConstants.Access_Child_Profile,// Event category (required)
						SocialConstants.Access_Child_Profile,  // Event action (required)
						label,
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
		logger.logEvent(SocialConstants.Access_Child_Profile,parameters);
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
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String strTime = sdf.format(c.getTime());
		SimpleDateFormat sdDate = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdDate.format(c.getTime());
		/*hitParameters.put(SocialConstants.Time_of_the_day,strTime);
		hitParameters.put(SocialConstants.Number_of_times_a_Day,strDate);

		tracker.send(hitParameters);*/
		String label=SocialConstants.Time_of_the_day+":"+strTime+","
				+SocialConstants.Date+":"+strDate;
		tracker.send(MapBuilder
				.createEvent(SocialConstants.Schedule_First_Activity,// Event category (required)
						SocialConstants.Schedule_First_Activity,  // Event action (required)
						label,
						null)            // Event value
						.build()
				);
	}
	/**
	 * 
	 */
	public void scheduleFirstActivityFacebookLog()
	{
		Bundle parameters = new Bundle();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String strTime = sdf.format(c.getTime());
		SimpleDateFormat sdDate = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdDate.format(c.getTime());

		parameters.putString(SocialConstants.Time_of_the_day,strTime);
		parameters.putString(SocialConstants.Number_of_times_a_Day,strDate);

		logger.logEvent(SocialConstants.Schedule_First_Activity,parameters);
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
				.createEvent(SocialConstants.Schedule_SchoolActivity,// Event category (required)
						null,  // Event action (required)
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
				.createEvent(SocialConstants.Schedule_AfterSchoolActivity,// Event category (required)
						null,  // Event action (required)
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
				.createEvent(SocialConstants.Reacted_to_Post,// Event category (required)
						null,  // Event action (required)
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
				.createEvent(SocialConstants.Add_Buddy,// Event category (required)
						null,  // Event action (required)
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
				.createEvent(SocialConstants.Buddy_Requests_Sent,// Event category (required)
						null,  // Event action (required)
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
				.createEvent(SocialConstants.Create_Postcard_Photo,// Event category (required)
						null,  // Event action (required)
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
				.createEvent(SocialConstants.Create_Postcard_VO,// Event category (required)
						null,  // Event action (required)
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
				.createEvent(SocialConstants.Create_Postcard_Text,// Event category (required)
						null,  // Event action (required)
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
				.createEvent(SocialConstants.Add_to_Wishlist,// Event category (required)
						null,  // Event action (required)
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
				.createEvent(SocialConstants.Child_Ratings,// Event category (required)
						null,  // Event action (required)
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
				.createEvent(SocialConstants.Network_Connection_Accepted,// Event category (required)
						null,  // Event action (required)
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
				.createEvent(SocialConstants.Network_Connection_Added,// Event category (required)
						null,  // Event action (required)
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
				.createEvent(SocialConstants.Schedule_Activity_from_WTD,// Event category (required)
						null,  // Event action (required)
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
				.createEvent(SocialConstants.View_What_to_Do,// Event category (required)
						null,  // Event action (required)
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
		tracker.send(MapBuilder
				.createEvent(SocialConstants.Quality_Badge_Level,// Event category (required)
						SocialConstants.LEVEL,  // Event action (required)
						levelValue,
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

		logger.logEvent(SocialConstants.Quality_Badge_Level);
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
				.createEvent(SocialConstants.Insights_Activated,// Event category (required)
						null,  // Event action (required)
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
				.createEvent(SocialConstants.Insights_Subscribed,// Event category (required)
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

		logger.logEvent(SocialConstants.Insights_Subscribed);
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
						.createEvent(SocialConstants.Subscribe_Button_Clicked,// Event category (required)
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
				parameters.putString(SocialConstants.STATUS,"Did_Not_Complete");
				logger.logEvent(SocialConstants.Subscribe_Button_Clicked);		    }
		}, 420000); 

	}

}

