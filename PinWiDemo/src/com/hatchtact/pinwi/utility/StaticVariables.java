package com.hatchtact.pinwi.utility;

import java.util.ArrayList;

import com.hatchtact.pinwi.classmodel.AddAfterSchoolActivities;
import com.hatchtact.pinwi.classmodel.ChildModel;
import com.hatchtact.pinwi.classmodel.ChildProfile;
import com.hatchtact.pinwi.classmodel.FacebookParentProfileModel;
import com.hatchtact.pinwi.classmodel.ListOfAllys;
import com.hatchtact.pinwi.classmodel.PassCode;
import com.hatchtact.pinwi.classmodel.PassCodeList;
import com.hatchtact.pinwi.classmodel.SearchActivitiesByActivityName;

public class StaticVariables 
{
	public static int autolockTimeValue=0;
	public static long timeforPasscode=0;
	public static long lastTimeValue=0;	
	public static boolean forPasscode=false;

	public static ArrayList<ChildProfile> childInfo=new ArrayList<ChildProfile>();
	public static ChildProfile currentChild;
	public static int ActivityIdInsight;
	public static int BucketIdInsight;

	public static int fragmentIndex=0;

	public static int categoryId;
	public static String categoryName;

	public static int subCategoryId;
	public static String subCategoryName;

	public static int subSubCategoryId;
	public static String subSubCategoryName;

	//public static ArrayList<ListOfAllys> allyInformation=new ArrayList<ListOfAllys>();
	public static String[] daysInAfterSchool = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Weekdays","Weekend","All days"};

	public static boolean[] daysSelectedInAfterSchool = {false,false,false,false,false,false,false,false,false,false};

	public static ListOfAllys allySetFor1;
	public static ListOfAllys allySetFor2;

	public static boolean showAllyName1InAfterSchool=false;
	public static boolean showAllyName2InAfterSchool=false;

	public static AddAfterSchoolActivities addAfterSchoolActivities;

	public static boolean isHolidayCalenderFromScheduler=false;

	public static int fragmentIndexCurrentTabInsight=0;
	public static int fragmentIndexCurrentTabSchedular=0;
	public static int fragmentIndexCurrentTabNetwork=0;
	public static int fragmentIndexCurrentTabWhatToDo=0;
	public static int currentFragmentRunningOnce=0;

	public static int positionNotificationSelected;

	public static int fragmentIndexCurrentTabNotification=0;
	public static String currentParentName;
	public static int currentParentId;
	public static int notificationCount;
	public static String selectedDate;

	public static FacebookParentProfileModel modelFacebook=new FacebookParentProfileModel();

	public static boolean isChildUpdated=false;
	public static final String progressBarText="Hold On...";
	public static boolean isSettingsFromAccessProfile=false;
	public static ArrayList<ChildModel> childArrayList=new ArrayList<ChildModel>();
	public static int currentTutorialValue=0;//this is for in app tutorials

	//public static int currentTutorialFromList=0;//this is for tutorial screen
	public static boolean  deleteAfterSchoolFromAfterSchool=false;

	public static int ActivityIdScheduler;
	public static boolean fromInformAlly=false;
	public static String webUrl="";
	public static boolean isTutorialSoundEnabled=false;
	public static String daysAgo="";

	public static final int howPiNWiWorksTutorial=0;
	public static final int schedularTutorial=1;
	public static final int atSchoolTutorial=2;
	public static final int afterSchoolTutorial=3;
	public static final int insightsTutorial=4;
	public static final int childTutorial=5;


	public static String AllyName="";
	public static int AllyId;
	public static int statusChild=0;
	public static String earnedPts="";
	public static boolean isFromSettingsScreen=false; 
	
	public static int fragmentIndexCurrentTabSettings=0;
	public static boolean notificationCountInvisible=false;
	public static String byteArray="";
	public static String FriendId="";
	public static String NetworkChildId="";
	public static String NetworkExhilaratorChildName="";
	public static int ClusterId=0;
	public static String ClusterName="";
	
	public static String WhatToDoActivityName="";
	public static int WhatToDoActivityId;
	
	public static String webserviceName="";
	public static boolean isSignUpClicked=false;
	
	public static String NetworkChildIdProfileConnections="";
	public static String NetworkExhilaratorChildNameProfileConnections="";
	
	public static String childIdBuddiesDetail="";
	public static String childWishlistName="";
	public static int emooticionNo=0;
	
	public static int screenIndex=0;

	public static int selectedPostcardIndex=0;

	public static String[] subscriptionTypeArray;
	public static String[] subscriptionTextArray;
	public static String[] subscriptionCostArray;
	
	public static PassCode parentPasscodeModel=new PassCode();
	public static PassCodeList childPasscodeList=new PassCodeList();;
	public static boolean isInterestDriver=false;
	public static final int  ACCESSPROFILESETTINGSPASSCODE=1;
	public static final int  TABCHILDACTIVITIESSETTINGSPASSCODE=2;



}
