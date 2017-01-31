package com.hatchtact.pinwi;

import java.lang.reflect.Field;
import java.util.ArrayList;

import leolin.shortcutbadger.ShortcutBadger;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hatchtact.pinwi.adapter.NavDrawerListAdapterMenu;
import com.hatchtact.pinwi.classmodel.GetNewNotificationCount;
import com.hatchtact.pinwi.classmodel.NavigationDrawerItem;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.classmodel.RequestAddOnVersionModel;
import com.hatchtact.pinwi.fragment.AddActivityFragment;
import com.hatchtact.pinwi.fragment.AddAfterSchoolByCatIDFragment;
import com.hatchtact.pinwi.fragment.AddAfterSchoolCategoriesAndSubCategoriesFragment;
import com.hatchtact.pinwi.fragment.AddAfterSchoolCategoriesFragment;
import com.hatchtact.pinwi.fragment.AddAfterSchoolFragment;
import com.hatchtact.pinwi.fragment.AddSubjectFragment;
import com.hatchtact.pinwi.fragment.AfterSchoolActivityByChildIdFragment;
import com.hatchtact.pinwi.fragment.AllyDropPickFragment;
import com.hatchtact.pinwi.fragment.CalenderFragment;
import com.hatchtact.pinwi.fragment.DisplayAllyInformationFragment;
import com.hatchtact.pinwi.fragment.GetDataByCalendarDateFragment;
import com.hatchtact.pinwi.fragment.HolidayListFragment;
import com.hatchtact.pinwi.fragment.NotificationFragment;
import com.hatchtact.pinwi.fragment.SubjectActivityByChildIDFragment;
import com.hatchtact.pinwi.fragment.insights.DelightTrendsFragment;
import com.hatchtact.pinwi.fragment.insights.InsightsErrorFragment;
import com.hatchtact.pinwi.fragment.insights.OnFragmentAttachedListener;
import com.hatchtact.pinwi.fragment.insights.SubscribeFragment;
import com.hatchtact.pinwi.fragment.insights.TypesInsightsFragment;
import com.hatchtact.pinwi.fragment.network.ChildDetailFragment;
import com.hatchtact.pinwi.fragment.network.FriendDetailFragment;
import com.hatchtact.pinwi.fragment.network.MyProfileScreenFragment;
import com.hatchtact.pinwi.fragment.network.NetworkConnectionsFragment;
import com.hatchtact.pinwi.fragment.network.NetworkDiscoverFragment;
import com.hatchtact.pinwi.fragment.network.NetworkRequestFragment;
import com.hatchtact.pinwi.fragment.network.OnEventListener;
import com.hatchtact.pinwi.fragment.network.ProfileConnectionsFragment;
import com.hatchtact.pinwi.fragment.whattodo.ActivityListFragment;
import com.hatchtact.pinwi.fragment.whattodo.WhatToDoExploreFragment;
import com.hatchtact.pinwi.fragment.whattodo.WhatToDoNetworkFragment;
import com.hatchtact.pinwi.fragment.whattodo.WhatToDoRecommendedFragment;
import com.hatchtact.pinwi.fragment.whattodo.WhoDoingThisFragment;
import com.hatchtact.pinwi.fragment.whattodo.WishListFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

public class TabChildActivities extends FragmentActivity implements OnFragmentAttachedListener, OnClickListener
{ 
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;

	private ActionBarDrawerToggle mDrawerToggle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavigationDrawerItem> navDrawerItems;
	private NavDrawerListAdapterMenu adapter;

	//private FragmentTabHost mTabHost;

	private ParentProfile parentCompleteInformation;

	private SharePreferenceClass sharePref;
	private Gson gsonRegistration=null;

	private int parentId=0;

	private ImageView tab_notification;
	private ImageView tab_scheduler;
	private ImageView tab_insights;
	private ImageView tab_activity;
	private ImageView tab_network;


	//BackStack handling Variables
	private int currentTab=-1;
	//Insights
	private final int typesInsightFragment=1;
	private final int delightTrendsFragment=2;
	private final int insightDriversFragment=3;
	private final int insightsFragment=4;
	private final int delightTrendsByActivityFragmentFromTypesInsight=5;
	private int delightTrendsByActivityFragmentFromDelightTrendsFragment=6;
	private final int insightErrorFragment=7;

	//Scheduler
	private final int calenderFragmentScheduler=11;
	private final int afterSchoolFragmentScheduler=12;
	private final int schoolFragmentScheduler=13;
	private final int addActivityFragment=14;
	private final int addActivityFragmentByCalenderdateFragment=18;
	private final int getDataByCalenderdateFragment=15;
	private final int addSubjectFragmentByCalenderdateFragment=16;
	private final int addAfterSchoolCategoriesFragmentByCalenderdateFragment=17;
	private final int addSchoolFragmentByCalenderdateFragment=19;
	private final int addAfterSchoolFragmentByCalenderdateFragment=20;
	private final int addSubjectFragmentByActivityFragmentFromCalenderFragment=21;
	private final int addAfterSchoolCategoriesFragmentByActivityFragmentFromCalenderFragment=22;
	private final int addAfterSchoolCategoriesFragmentByAfterSchool=23;
	private final int addAfterSchoolFragmentByAfterSchool=24;
	private final int addSubjectFragmentBySchoolFragment=25;
	private final int addSchoolFragmentBySchoolFragment=26;
	private final int addAfterSchoolCategoriesAndSubCategoriesFragmentByCalenderDateFragment =27;
	private final int informAllyFragmentByAddAfterSchoolFragmentByCalenderDateFragment=28;
	private final int allyDropPickFragmentByAddAfterSchoolFragmentByCalenderDateFragment=29;
	private final int addSchoolFragmentByAddSubjectFragmentFromCalenderFragment=30;
	private final int addAfterSchoolByCatIDFragmentByCalenderDateFragment=31;
	private final int allyDropPickFragmentByInformAllyFragmentByCalenderDateFragment=32;
	private final int addAfterSchoolFragmentByAllyDropPickFragmentByCalenderDateFragment=33;
	private final int addafterSchoolallyDropPickFragmentByAddAfterSchoolFragmentByCalenderDateFragment=57;
	private final int addCustomFragmentByAddAfterSchoolByCatIDFragmentByCalenderDateFragment=34;
	private final int addAfterSchoolFragmentByAddAfterSchoolByCatIDFragmentByCalenderDateFragment=35;
	private final int addSubjectFragmentByActivityFragmentFromGetDataByCalenderdateFragment=36;
	private final int addAfterSchoolCategoriesFragmentByActivityFragmentFromGetDataByCalenderdateFragment=37;
	private final int addSchoolFragmentByAddSubjectFragmentFromGetDataByCalenderdateFragment=38;
	private final int addAfterSchoolCategoriesAndSubCategoriesFragmentFromGetDataByCalenderdateFragment =39;
	private final int addAfterSchoolByCatIDFragmentFromGetDataByCalenderdateFragment=40;
	private final int addCustomFragmentByAddAfterSchoolByCatIDFragmentFromGetDataByCalenderdateFragment=41;
	private final int addAfterSchoolFragmentByAddAfterSchoolByCatIDFragmentFromGetDataByCalenderdateFragment=42;
	private final int informAllyFragmentByAddAfterSchoolFragmentFromGetDataByCalenderdateFragment=43;
	private final int allyDropPickFragmentByAddAfterSchoolFragmentFromGetDataByCalenderdateFragment=44;
	private final int addafterSchoolallyDropPickFragmentByAddAfterSchoolFragmentFromGetDataByCalenderdateFragment=56;
	private final int allyDropPickFragmentByInformAllyFragmentFromGetDataByCalenderdateFragment=45;
	private final int addAfterSchoolFragmentByAllyDropPickFragmentFromGetDataByCalenderdateFragment=46;
	private final int addAfterSchoolCategoriesAndSubCategoriesFragmentByAfterSchool =47;
	private final int addAfterSchoolByCatIDFragmentFromGetDataByAfterSchool=48;
	private final int addCustomFragmentByAddAfterSchoolByCatIDFragmentFromAfterSchool=49;
	private final int addAfterSchoolByAddAfterSchoolByCatIDFragmentFromAfterSchool=50;
	private final int informAllyFragmentByAddAfterSchoolFragmentFromAfterSchool=51;
	private final int allyDropPickFragmentByAddAfterSchoolFragmentFromAfterSchool=52;
	private final int allyDropPickFragmentFromInformAllyByAddAfterSchoolFragmentFromAfterSchool=53;
	private final int addAfterSchoolFragmentFromAllyDropPickByInformAllyByAddAfterSchoolFragmentFromAfterSchool=54;
	private final int addAfterSchoolFragmentByAllydropPickByAddAfterSchoolFragmentFromAfterSchool=55;
	private final int informAllyByAddAfterSchoolFromAfterSchool=58;
	private final int allyDropPickByAddAfterSchoolFromAfterSchool=59;
	private final int allyDropPickByInformAllyByAddAfterSchoolFromAfterSchool=60;
	private final int addAfterSchoolByallyDropPickByInformAllyByAddAfterSchoolFromAfterSchool=61;
	private final int addAfterSchoolByallyDropPickByAddAfterSchoolFromAfterSchool=62;
	private final int addSchoolFragmentByAddSubjectFromSchool=63;
	private final int addAfterSchoolSubcategoryaByAfterSchoolCategoriesFragmentFromCalenderdateFragment=64;
	private final int addAfterSchoolByCatIdByaddAfterSchoolSubcategoryaByAfterSchoolCategoriesFragmentFromCalenderdateFragment=65;
	private final int addCustomByaddAfterSchoolByCatIdByaddAfterSchoolSubcategoryaByAfterSchoolCategoriesFragmentFromCalenderdateFragment=66;
	private final int addAfterSchoolByaddAfterSchoolByCatIdByaddAfterSchoolSubcategoryaByAfterSchoolCategoriesFragmentFromCalenderdateFragment=67;
	private final int informAllyByAddAfterSchoolByCatIdBySubcategory=68;
	private final int allyDropPickByAddAfterSchoolByCatIdBySubcategory=69;
	private final int allyDropPickByinformAllyByAddAfterSchoolByCatIdBySubcategory=70;
	private final int informAllyByAllyDropPick1=71;
	private final int informAllyByAllyDropPick2=72;
	private final int informAllyByAllyDropPick3=73;
	private final int informAllyByAllyDropPick4=74;
	private final int informAllyByAllyDropPick5=75;
	private final int holidaylistfragmentbyCalenderFragment=80;
	private final int holidayDetailFragmentbyHolidayListFragment=81;	
	private final int searchActivityByAfterSchoolFragmentFromAfterSchoolCategoriesFragmentFromAfterSchoolByChildIdFragment=82;
	private final int searchActivityByAfterSchoolFragmentFromAfterSchoolCategoriesFragmentFromCalenderFragment=83;
	private final int searchActivityByAfterSchoolFragmentFromAfterSchoolCategoriesFragmentFromGetByCalenderFragment=84;
	private final int searchActivityByAfterSchoolFragmentFromAfterSchoolCategoriesFragmentByAddActivityFromGetByCalenderFragment=85;




	/*Notification Tags*/
	private final int notificationFragmentOne=101;
	private final int notificationFragmentTwo=102;
	/*Notification Tags*/

	/*Network from 200*/
	private final int networkFragmentConnections=201;
	private final int networkFragmentRequest=202;
	private final int networkFragmentDiscover=203;
	private final int friendDetailsByNetworkConnectionList=204;
	private final int childdetailByFriendDetail=205;
	private final int listOfExhilaratorsByChildIdBychilddetail=206;
	private final int profileScreenFragment=207;
	private final int profileConnectionFragmentByProfileFragment=208;
	private final int childFragmentByProfileFragment=209;
	private final int listOfExhilaratorsByChildIdBychilddetaiByProfileFragment=210;
	private final int childFragmentByProfileFragmentByNetworkConnections=211;
	private final int listOfExhilaratorsByChildIdBychilddetaiByListOfConnectionsByProfileFragment=212;

	/*What To Do*/
	private final int whattodoFragmentRecommended=301;
	private final int whattodoFragmentActivityDetailByClusterId=302;
	private final int whattodoFragmentWhoisdoingthisfromactivityList=305;
	private final int whattodoFragmentchilddetailfromWhoisdoingthisbyactivityList=308;
	private final int whattodoFragmentexhilaratorfromchilddetailfromWhoisdoingthisbyactivityList=309;
	private final int whattodoFragmentAddAfterSchoolfromactivityList=314;
	private final int whattodoFragmentDispalyAllyfromAddAfterSchoolfromactivityList=315;
	private final int whattodoFragmentAllyDropPickfromDisplayAllyfromAddAfterSchoolfromactivityList=316;
	private final int whattodoFragmentInformAllyFromAllyDropPickAddAfterSchoolfromactivityList=317;
	private final int whattodoFragmentWislistScreen=303;
	private final int whattodoFragmentWhoisdoingthisfromWishList=306;
	private final int whattodoFragmentchilddetailfromWhoisdoingthisbywishList=310;
	private final int whattodoFragmentexhilaratorfromchilddetailfromWhoisdoingthisbywishList=311;
	private final int whattodoFragmentAddAfterSchoolfromwishList=318;
	private final int whattodoFragmentDispalyAllyfromAddAfterSchoolfromwishList=319;
	private final int whattodoFragmentAllyDropPickfromDisplayAllyfromAddAfterSchoolfromwishList=320;
	private final int whattodoFragmentInformAllyFromAllyDropPickAddAfterSchoolfromwishList=321;
	private final int whattodoFragmentWislistScreenbyActivityList=304;
	private final int whattodoFragmentWhoisdoingthisfromWishListByActivityList=307;
	private final int whattodoFragmentchilddetailfromWhoisdoingthisfromWishListByActivityList=312;
	private final int whattodoFragmentexhilaratorfromchilddetailfromWhoisdoingthisfromWishListByActivityList=313;
	private final int whattodoFragmentAddAfterSchoolfromWishListByActivityList=322;
	private final int whattodoFragmentDispalyAllyfromAddAfterSchoolfromWishListByActivityList=323;
	private final int whattodoFragmentAllyDropPickfromDisplayAllyfromAddAfterSchoolfromWishListByActivityList=324;
	private final int whattodoFragmentInformAllyFromAllyDropPickAddAfterSchoolfromWishListByActivityList=325;
	/*****/




	private TextView textViewtab_notification;

	private ShowMessages showMessage=null;
	private CheckNetwork checkNetwork=null;
	private ServiceMethod serviceMethod=null;
	private RequestAddOnVersionModel requestaddonVersion=null;
	private TypeFace typeFace;

	private final int frequencyPageAfterSchool=1000;
	private final int frequencyPageAfterSchoolWhattodo=1001;

	private final int errorDetailPageInsights=2000;

	private Bitmap bitmapHeader;


	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tab);
		/* boolean success = ShortcutBadger.removeCount(TabChildActivities.this);

         Toast.makeText(getApplicationContext(), "success=" + success, Toast.LENGTH_SHORT).show();*/
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if(menuKeyField != null) 
			{
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception ex) {
			// Ignore
		}

		typeFace = new TypeFace(TabChildActivities.this);

		showMessage=new ShowMessages(TabChildActivities.this);
		checkNetwork=new CheckNetwork();
		serviceMethod=new ServiceMethod();
		requestaddonVersion=new RequestAddOnVersionModel();
		new AsyncNotificationCountByParentID().execute();

		tab_notification = (ImageView) findViewById(R.id.tab_notification);
		tab_scheduler = (ImageView) findViewById(R.id.tab_scheduler);
		tab_insights = (ImageView) findViewById(R.id.tab_insight);
		tab_activity = (ImageView) findViewById(R.id.tab_activity);
		tab_network = (ImageView) findViewById(R.id.tab_network);
		textViewtab_notification=(TextView) findViewById(R.id.textViewtab_notification);
		typeFace.setTypefaceRegular(textViewtab_notification);
		textViewtab_notification.setVisibility(View.GONE);
		/*textViewtab_notification.setVisibility(View.GONE);
		textViewtab_notification.setText(StaticVariables.notificationCount + "");*/

		try {
			bitmapHeader = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.header_bg), SplashActivity.ScreenWidth, BitmapFactory.decodeResource(getResources(), R.drawable.header_bg).getHeight(), false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bitmapHeader = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.header_bg), SplashActivity.ScreenWidth,100,false);

		}

		int whichisSelected=getIntent().getIntExtra("Type", 0);

		sharePref=new SharePreferenceClass(TabChildActivities.this);

		gsonRegistration=new GsonBuilder().create();


		parentCompleteInformation = gsonRegistration.fromJson(sharePref.getParentProfile(), ParentProfile.class);
		parentId=parentCompleteInformation.getParentID();
		StaticVariables.currentParentName=parentCompleteInformation.getFirstName();
		StaticVariables.currentParentId=parentCompleteInformation.getParentID();


		currentTab = whichisSelected;
		//StaticVariables.fragmentIndexCurrentTabSchedular=calenderFragmentScheduler;
		StaticVariables.fragmentIndexCurrentTabSchedular=schoolFragmentScheduler;
		StaticVariables.fragmentIndexCurrentTabInsight=typesInsightFragment;
		StaticVariables.fragmentIndexCurrentTabNotification=notificationFragmentOne;
		StaticVariables.fragmentIndexCurrentTabNetwork=networkFragmentConnections;
		StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentRecommended;

		switch (currentTab)
		{
		case 0:
			setActionBarTitle("  Notifications");
			StaticVariables.fragmentIndexCurrentTabNotification=notificationFragmentOne;
			tab_notification.setSelected(true);
			tab_scheduler.setSelected(false);
			tab_insights.setSelected(false);
			tab_network.setSelected(false);
			tab_activity.setSelected(false);
			textViewtab_notification.setVisibility(View.GONE);
			sharePref.setBadgeScore("0");
			/*boolean success =*/ ShortcutBadger.removeCount(TabChildActivities.this);
			//Toast.makeText(getApplicationContext(), "success=" + success, Toast.LENGTH_SHORT).show();
			switchingFragments(new NotificationFragment());	
			break;

		case 1:
			setActionBarTitle("  Scheduler");
			tab_notification.setSelected(false);
			tab_scheduler.setSelected(true);
			tab_insights.setSelected(false);
			tab_network.setSelected(false);
			tab_activity.setSelected(false);
			StaticVariables.fragmentIndexCurrentTabSchedular=schoolFragmentScheduler;
			switchingFragments(SubjectActivityByChildIDFragment.getInstance());

			//StaticVariables.fragmentIndexCurrentTabSchedular=calenderFragmentScheduler;
			//switchingFragments(CalenderFragment.getInstance());
			break;

		case 2:
			setActionBarTitle("  Insights");
			tab_notification.setSelected(false);
			tab_scheduler.setSelected(false);
			tab_insights.setSelected(true);
			tab_network.setSelected(false);
			tab_activity.setSelected(false);
			StaticVariables.fragmentIndexCurrentTabInsight=typesInsightFragment;
			switchingFragments(TypesInsightsFragment.getInstance());
			break;
		case 3:
			setActionBarTitle("  WhatToDo");
			tab_notification.setSelected(false);
			tab_scheduler.setSelected(false);
			tab_insights.setSelected(false);
			tab_network.setSelected(false);
			tab_activity.setSelected(true);
			//switchingFragments(new WhatToDoFragment());
			switchingFragments(new WhatToDoRecommendedFragment());
			break;
		case 4:
			setActionBarTitle("  Network");
			tab_notification.setSelected(false);
			tab_scheduler.setSelected(false);
			tab_insights.setSelected(false);
			tab_network.setSelected(true);
			tab_activity.setSelected(false);
			//switchingFragments(new NetworkFragment());
			switchingFragments(new NetworkConnectionsFragment());
			break;
		}


		tab_notification.setOnClickListener(this);
		tab_scheduler.setOnClickListener(this);
		tab_insights.setOnClickListener(this);
		tab_activity.setOnClickListener(this);
		tab_network.setOnClickListener(this);


		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()  
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavigationDrawerItem>();

		// adding nav drawer items to array

		navDrawerItems.add(new NavigationDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));

		navDrawerItems.add(new NavigationDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));

		//navDrawerItems.add(new NavigationDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));

		navDrawerItems.add(new NavigationDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));

		//navDrawerItems.add(new NavigationDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));

		navDrawerItems.add(new NavigationDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));

		navDrawerItems.add(new NavigationDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));

		//navDrawerItems.add(new NavigationDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));

		navDrawerItems.add(new NavigationDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));

		//navDrawerItems.add(new NavigationDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));

		navDrawerItems.add(new NavigationDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();


		StaticVariables.fragmentIndex=1;

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapterMenu(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter); 

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setIcon(null);
		getActionBar().setDisplayUseLogoEnabled(false);
		getActionBar().setBackgroundDrawable(new Drawable() {

			@Override
			public void setColorFilter(ColorFilter cf) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setAlpha(int alpha) {
				// TODO Auto-generated method stub

			}

			@Override
			public int getOpacity() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public void draw(Canvas canvas) {
				// TODO Auto-generated method stub
				//canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.header_bg), 0, 0, null);
				canvas.drawBitmap(bitmapHeader, 0, 0, null);

			}
		});



		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.menu, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
				) {
			public void onDrawerClosed(View view) {
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				// TODO Auto-generated method stub
				super.onDrawerSlide(drawerView, 0);
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		/*if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}*/
	}



	private void setActionBarTitle(String string) {
		// TODO Auto-generated method stub

		SpannableString s = new SpannableString(string);
		s.setSpan(new TypefaceSpan("Roboto-Bold.ttf"), 0, s.length(),
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		//getActionBar().setTitle(s);
		int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		if (actionBarTitleId > 0) {
			TextView title = (TextView) findViewById(actionBarTitleId);
			if (title != null) {
				title.setTextColor(Color.WHITE); 
				title.setText(s);
			}
		}

		getActionBar().setTitle(s);

	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
	ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item 
			System.out.println("value of item click"+position);
			displayView(position);
		}
	}   

	@SuppressLint("DefaultLocale")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.main, menu);



		for(int i=0;i<StaticVariables.childInfo.size();i++)
		{
			menu.add(0, i, i, StaticVariables.childInfo.get(i).getFirstName().toUpperCase()).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

		}




		for(int i = 0; i < menu.size(); i++) {
			MenuItem item = menu.getItem(i);

			if(item.getItemId()==R.id.action_childName)
			{
				//	menu.findItem(R.id.action_childName).setTitle(StaticVariables.currentChild.getFirstName());

				SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
				spanString.setSpan(new TypefaceSpan("gotham.ttf"), 0, spanString.length(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				spanString.setSpan(new ForegroundColorSpan(R.color.font_white_color), 0, spanString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); //fix the color to white

				item.setTitle(spanString);

			}
			else
			{
				SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
				spanString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spanString.length(), 0); //fix the color to white
				item.setTitle(spanString);
			}

		}

		//typeFace.setTypefaceGotham(menu.findItem(R.id.action_childName));

		for(int i=0;i<menu.size();i++)
		{
			menu.getItem(i).setVisible(false);
		}

		return true;
	}


	@Override
	public final boolean onMenuItemSelected(int featureId, android.view.MenuItem item) {
		// fix android formatted title bug
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2
				&& item.getTitleCondensed() != null) {
			item.setTitleCondensed(item.getTitleCondensed().toString());
		}
		return super.onMenuItemSelected(featureId, item);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		/*if (getFragmentManager().getBackStackEntryCount() > 0) {
			   getFragmentManager().popBackStack();
			} else {
			   finish();
			}*/
		return super.onOptionsItemSelected(item);

		// Handle action bar actions click
		/*switch (item.getItemId()) {


		case android.R.id.home:
	       this.finish();
	        return true;
		default:
			return super.onOptionsItemSelected(item);
		}*/
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		//	menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	private void displayView(int position) {
		// update the main content by replacing fragments
		switch (position) {
		case 0:

			/*if(ParentProfileInformationActivity.getInstance()!=null)
			{
				ParentProfileInformationActivity.getInstance().finish();
			}*/
			finish();
			Intent intentAccessProfile =new Intent(TabChildActivities.this, AccessProfileActivity.class);
			// set the new task and clear flags
			intentAccessProfile.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intentAccessProfile);
			break;
		case 1:
			Intent intentAboutUs =new Intent(TabChildActivities.this, ActivityAboutUS.class);
			startActivity(intentAboutUs);
			StaticVariables.webUrl="http://pinwi.in/aboutus.html";
			break;
			/*case 2:
			Intent intentSupport =new Intent(TabChildActivities.this, ActivityAboutUS.class);
			startActivity(intentSupport);
			StaticVariables.webUrl="http://designer.convergenttec.com/pinwi/html/faq.html";
			Intent intentSupport =new Intent(TabChildActivities.this, ActivitySupport.class);
			startActivity(intentSupport);
			break;*/
		case 2:
			Intent intentTutorial =new Intent(TabChildActivities.this, ActivityTutorial.class);
			startActivity(intentTutorial);
			break;
			/*case 3:
			Intent intentInvite =new Intent(TabChildActivities.this, ActivityInvite.class);
			startActivity(intentInvite);
			break;*/
		case 3:
			Intent intentInvite =new Intent(TabChildActivities.this, ActivityInvite.class);
			startActivity(intentInvite);
			break;
		case 4:
			Intent intentContactus =new Intent(TabChildActivities.this, ActivityAboutUS.class);
			startActivity(intentContactus);
			StaticVariables.webUrl=" http://pinwi.in/contactus.aspx?4";

			break;
		case 5:
			mDrawerLayout.closeDrawers();

			//if(parentCompleteInformation.getPasscode().toString()!=null && parentCompleteInformation.getPasscode().toString().equals(""))
			{
				Intent intentSettings =new Intent(TabChildActivities.this, SettingsActivity.class);
				startActivity(intentSettings);
			}
			/*else  
			{ 
				Intent intent = new Intent(TabChildActivities.this, PasswordUnLockActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("ProfileId", parentId);
				bundle.putBoolean("ToLoadNextScreen", true);
				bundle.putInt("isSettings", StaticVariables.TABCHILDACTIVITIESSETTINGSPASSCODE);
				intent.putExtras(bundle);
				startActivity(intent);
				//AccessProfileActivity.this.finish();
			}*/
			break;

			/*case 7:
			new RequestAddOnVersionTask(parentId).execute();
			break;*/
		case 6:
			sharePref.setIsLogin(false);
			sharePref.setIsLogout(true);
			sharePref.setParentProfile("");  

			/*if(ParentProfileInformationActivity.getInstance()!=null)
			{
				ParentProfileInformationActivity.getInstance().finish();
			}*/
			finish();
			Intent intent = new Intent(TabChildActivities.this, LoginActivity.class);
			startActivity(intent);
			android.os.Process.killProcess(android.os.Process.myPid());

			break;
		}
	}
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		StaticVariables.forPasscode=false;
		StaticVariables.lastTimeValue=System.currentTimeMillis();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

		/*try {
			if(!StaticVariables.forPasscode)
			{ 
				if (parentCompleteInformation!=null && parentCompleteInformation.getPasscode()!=null && parentCompleteInformation.getPasscode().trim().length()>0 && System.currentTimeMillis() - StaticVariables.lastTimeValue > StaticVariables.timeforPasscode) 
				{
					Intent intent=new Intent(TabChildActivities.this, PasswordUnLockActivity.class);			
					Bundle bundle = new Bundle();
					bundle.putInt("ProfileId", parentId);
					bundle.putBoolean("ToLoadNextScreen", false);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		StaticVariables.forPasscode=true;
		StaticVariables.lastTimeValue=0;	
	}

	@Override
	public void onFragmentAttached(boolean navigationDrawer,String moduleName) 
	{
		// TODO Auto-generated method stub

		setDrawerState(navigationDrawer,moduleName);

	}


	public void setDrawerState(boolean isEnabled,String moduleName) {
		setActionBarTitle(moduleName);
		if ( isEnabled ) {
			mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
			mDrawerToggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_UNLOCKED);
			mDrawerToggle.setDrawerIndicatorEnabled(true);
			mDrawerToggle.syncState();

		}
		else {
			mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
			mDrawerToggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
			mDrawerToggle.setDrawerIndicatorEnabled(false);
			mDrawerToggle.syncState();

		}

	}

	private ProgressDialog progressDialog=null;	

	private class RequestAddOnVersionTask extends AsyncTask<Void, Void, Integer>
	{
		private int parentId ;

		public RequestAddOnVersionTask(int parentId)
		{
			// TODO Auto-generated constructor stub 
			this.parentId = parentId;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = ProgressDialog.show(TabChildActivities.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(TabChildActivities.this))
			{
				requestaddonVersion = serviceMethod.getRequestAddOnVersion(parentId);		
			}
			else 
			{
				ErrorCode=-1;
			}
			return ErrorCode;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {
				if (progressDialog.isShowing())
					progressDialog.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");
				new RequestAddOnVersionTask(parentId).execute();
			}
			else
			{
				showAlert("Add On Request", "Your request for AddOn services has been confirmed!");
			}	
		}	
	}

	public void showAlert(String title, String message)
	{
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

		alertBuilder.setTitle(title);
		alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
		alertBuilder.setMessage(message);
		alertBuilder.setPositiveButton(" OK ", new DialogInterface.OnClickListener() 
		{	
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});	
		alertBuilder.show();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub


		switch (currentTab) 
		{
		case 0:
			if(StaticVariables.fragmentIndexCurrentTabNotification==notificationFragmentOne)
			{
				//finish();
				finish();
				Intent intentAccessProfile =new Intent(TabChildActivities.this, AccessProfileActivity.class);
				// set the new task and clear flags
				intentAccessProfile.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intentAccessProfile);
			}
			else if(StaticVariables.fragmentIndexCurrentTabNotification==notificationFragmentTwo)

			{
				StaticVariables.fragmentIndexCurrentTabNotification=notificationFragmentOne;
				switchingFragments(new NotificationFragment());
			}
			break;

		case 1:
			if(StaticVariables.fragmentIndexFrequencyPage==frequencyPageAfterSchool)
			{
				if(!StaticVariables.isFrequencySaveClicked)
				{
					showMessage.showAlertFrequency(TabChildActivities.this, new OnEventListener<String>() {

						@Override
						public void onSuccess(String object) {
							// TODO Auto-generated method stub
							StaticVariables.fragmentIndexFrequencyPage=0;
							switchingFragments(new AddAfterSchoolFragment());
							StaticVariables.isFrequencySaveClicked=false;
						}

						@Override
						public void onFailure(String object) {
							// TODO Auto-generated method stub

						}
					});
				}
				else
				{
					StaticVariables.fragmentIndexFrequencyPage=0;
					switchingFragments(new AddAfterSchoolFragment());
					StaticVariables.isFrequencySaveClicked=false;
				}

			}
			else if(StaticVariables.fragmentIndexCurrentTabSchedular==calenderFragmentScheduler)
			{
				//finish();
				finish();
				Intent intentAccessProfile =new Intent(TabChildActivities.this, AccessProfileActivity.class);
				// set the new task and clear flags
				intentAccessProfile.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intentAccessProfile);
			}
			else if(StaticVariables.fragmentIndexCurrentTabSchedular==afterSchoolFragmentScheduler)
			{
				//finish();
				finish();
				Intent intentAccessProfile =new Intent(TabChildActivities.this, AccessProfileActivity.class);
				// set the new task and clear flags
				intentAccessProfile.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intentAccessProfile);
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==schoolFragmentScheduler)
			{
				//finish();
				finish();
				Intent intentAccessProfile =new Intent(TabChildActivities.this, AccessProfileActivity.class);
				// set the new task and clear flags
				intentAccessProfile.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intentAccessProfile);
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addActivityFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=11;
				switchingFragments(new CalenderFragment());

			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==getDataByCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=11;
				switchingFragments(new CalenderFragment());

			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addSubjectFragmentByCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=15;
				switchingFragments(new GetDataByCalendarDateFragment());
			}

			/*else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolCategoriesFragmentByCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=15;
				switchingFragments(new GetDataByCalendarDateFragment());
			}*/

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addActivityFragmentByCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=15;
				switchingFragments(new GetDataByCalendarDateFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addSchoolFragmentByCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=15;
				switchingFragments(new GetDataByCalendarDateFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolFragmentByCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=15;
				switchingFragments(new GetDataByCalendarDateFragment());
			}


			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addSubjectFragmentByActivityFragmentFromCalenderFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=14;
				switchingFragments(new AddActivityFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolCategoriesFragmentByActivityFragmentFromCalenderFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=14;
				switchingFragments(new AddActivityFragment());
			}



			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolCategoriesFragmentByAfterSchool)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=12;
				switchingFragments(new AfterSchoolActivityByChildIdFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolFragmentByAfterSchool)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=12;
				switchingFragments(new AfterSchoolActivityByChildIdFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addSubjectFragmentBySchoolFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=13;
				switchingFragments(new SubjectActivityByChildIDFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addSchoolFragmentBySchoolFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=13;
				switchingFragments(new SubjectActivityByChildIDFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolCategoriesAndSubCategoriesFragmentByCalenderDateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=22;
				switchingFragments(new AddAfterSchoolCategoriesFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==informAllyFragmentByAddAfterSchoolFragmentByCalenderDateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=35;
				switchingFragments(new AddAfterSchoolFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabSchedular==allyDropPickFragmentByAddAfterSchoolFragmentByCalenderDateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=35;
				switchingFragments(new AddAfterSchoolFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addSchoolFragmentByAddSubjectFragmentFromCalenderFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=21;
				switchingFragments(new AddSubjectFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolByCatIDFragmentByCalenderDateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=27;
				switchingFragments(new AddAfterSchoolCategoriesAndSubCategoriesFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==allyDropPickFragmentByInformAllyFragmentByCalenderDateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=28;
				switchingFragments(new DisplayAllyInformationFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolFragmentByAllyDropPickFragmentByCalenderDateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=32;
				switchingFragments(new AllyDropPickFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addCustomFragmentByAddAfterSchoolByCatIDFragmentByCalenderDateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=31;
				switchingFragments(new AddAfterSchoolByCatIDFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolFragmentByAddAfterSchoolByCatIDFragmentByCalenderDateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=31;
				switchingFragments(new AddAfterSchoolByCatIDFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addSubjectFragmentByActivityFragmentFromGetDataByCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=18;
				switchingFragments(new AddActivityFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolCategoriesFragmentByActivityFragmentFromGetDataByCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=18;
				switchingFragments(new AddActivityFragment());
			}


			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addSchoolFragmentByAddSubjectFragmentFromGetDataByCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=36;
				switchingFragments(new AddSubjectFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolCategoriesAndSubCategoriesFragmentFromGetDataByCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=37;
				switchingFragments(new AddAfterSchoolCategoriesFragment());
			}


			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolByCatIDFragmentFromGetDataByCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=39;
				switchingFragments(new AddAfterSchoolCategoriesAndSubCategoriesFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addCustomFragmentByAddAfterSchoolByCatIDFragmentFromGetDataByCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=40;
				switchingFragments(new AddAfterSchoolByCatIDFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolFragmentByAddAfterSchoolByCatIDFragmentFromGetDataByCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=40;
				switchingFragments(new AddAfterSchoolByCatIDFragment());
			}


			else if(StaticVariables.fragmentIndexCurrentTabSchedular==informAllyFragmentByAddAfterSchoolFragmentFromGetDataByCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=42;
				switchingFragments(new AddAfterSchoolFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabSchedular==allyDropPickFragmentByAddAfterSchoolFragmentFromGetDataByCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=42;
				switchingFragments(new AddAfterSchoolFragment());
			}


			else if(StaticVariables.fragmentIndexCurrentTabSchedular==allyDropPickFragmentByInformAllyFragmentFromGetDataByCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=43;
				switchingFragments(new DisplayAllyInformationFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolFragmentByAllyDropPickFragmentFromGetDataByCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=45;
				switchingFragments(new AllyDropPickFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolCategoriesAndSubCategoriesFragmentByAfterSchool)
			{

				StaticVariables.fragmentIndexCurrentTabSchedular=23;
				switchingFragments(new AddAfterSchoolCategoriesFragment());

			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolByCatIDFragmentFromGetDataByAfterSchool)
			{

				StaticVariables.fragmentIndexCurrentTabSchedular=47;
				switchingFragments(new AddAfterSchoolCategoriesAndSubCategoriesFragment());

			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addCustomFragmentByAddAfterSchoolByCatIDFragmentFromAfterSchool)
			{


				StaticVariables.fragmentIndexCurrentTabSchedular=48;
				switchingFragments(new AddAfterSchoolByCatIDFragment());


			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolByAddAfterSchoolByCatIDFragmentFromAfterSchool)
			{


				StaticVariables.fragmentIndexCurrentTabSchedular=48;
				switchingFragments(new AddAfterSchoolByCatIDFragment());


			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==informAllyFragmentByAddAfterSchoolFragmentFromAfterSchool)
			{


				StaticVariables.fragmentIndexCurrentTabSchedular=50;
				switchingFragments(new AddAfterSchoolFragment());


			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==allyDropPickFragmentByAddAfterSchoolFragmentFromAfterSchool)
			{


				StaticVariables.fragmentIndexCurrentTabSchedular=50;
				switchingFragments(new AddAfterSchoolFragment());


			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==allyDropPickFragmentFromInformAllyByAddAfterSchoolFragmentFromAfterSchool)
			{


				StaticVariables.fragmentIndexCurrentTabSchedular=51;
				switchingFragments(new DisplayAllyInformationFragment());


			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolFragmentFromAllyDropPickByInformAllyByAddAfterSchoolFragmentFromAfterSchool)
			{


				StaticVariables.fragmentIndexCurrentTabSchedular=53;
				switchingFragments(new AllyDropPickFragment());


			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolFragmentByAllydropPickByAddAfterSchoolFragmentFromAfterSchool)
			{


				StaticVariables.fragmentIndexCurrentTabSchedular=52;
				switchingFragments(new AllyDropPickFragment());


			}


			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addafterSchoolallyDropPickFragmentByAddAfterSchoolFragmentFromGetDataByCalenderdateFragment)
			{


				StaticVariables.fragmentIndexCurrentTabSchedular=44;
				switchingFragments(new AllyDropPickFragment());


			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addafterSchoolallyDropPickFragmentByAddAfterSchoolFragmentByCalenderDateFragment)
			{


				StaticVariables.fragmentIndexCurrentTabSchedular=29;
				switchingFragments(new AllyDropPickFragment());


			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==informAllyByAddAfterSchoolFromAfterSchool)
			{


				StaticVariables.fragmentIndexCurrentTabSchedular=24;
				switchingFragments(new AddAfterSchoolFragment());


			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==allyDropPickByAddAfterSchoolFromAfterSchool)
			{


				StaticVariables.fragmentIndexCurrentTabSchedular=24;
				switchingFragments(new AddAfterSchoolFragment());


			}


			else if(StaticVariables.fragmentIndexCurrentTabSchedular==allyDropPickByInformAllyByAddAfterSchoolFromAfterSchool)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=58;
				switchingFragments(new DisplayAllyInformationFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolByallyDropPickByInformAllyByAddAfterSchoolFromAfterSchool)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=60;
				switchingFragments(new AllyDropPickFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolByallyDropPickByAddAfterSchoolFromAfterSchool)
			{

				StaticVariables.fragmentIndexCurrentTabSchedular=59;
				switchingFragments(new AllyDropPickFragment());



			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addSchoolFragmentByAddSubjectFromSchool)
			{



				StaticVariables.fragmentIndexCurrentTabSchedular=25;
				switchingFragments(new AddSubjectFragment());



			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolCategoriesFragmentByCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=15;
				switchingFragments(new GetDataByCalendarDateFragment());
			}


			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolSubcategoryaByAfterSchoolCategoriesFragmentFromCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=17;
				switchingFragments(new AddAfterSchoolCategoriesFragment());

			}
			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolByCatIdByaddAfterSchoolSubcategoryaByAfterSchoolCategoriesFragmentFromCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=64;
				switchingFragments(new AddAfterSchoolCategoriesAndSubCategoriesFragment());

			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addCustomByaddAfterSchoolByCatIdByaddAfterSchoolSubcategoryaByAfterSchoolCategoriesFragmentFromCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=65;
				switchingFragments(new AddAfterSchoolByCatIDFragment());

			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolByaddAfterSchoolByCatIdByaddAfterSchoolSubcategoryaByAfterSchoolCategoriesFragmentFromCalenderdateFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=65;
				switchingFragments(new AddAfterSchoolByCatIDFragment());

			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==informAllyByAddAfterSchoolByCatIdBySubcategory)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=67;
				switchingFragments(new AddAfterSchoolFragment());

			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==allyDropPickByinformAllyByAddAfterSchoolByCatIdBySubcategory)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=68;
				switchingFragments(new DisplayAllyInformationFragment());

			}
			/*else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolByallyDropPickByinformAllyByAddAfterSchoolByCatIdBySubcategory)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=70;
				switchingFragments(new AllyDropPickFragment());

			}*/
			else if(StaticVariables.fragmentIndexCurrentTabSchedular==allyDropPickByAddAfterSchoolByCatIdBySubcategory)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=67;
				switchingFragments(new AddAfterSchoolFragment());

			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==informAllyByAllyDropPick1)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=32;
				switchingFragments(new AllyDropPickFragment());

			}
			else if(StaticVariables.fragmentIndexCurrentTabSchedular==informAllyByAllyDropPick2)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=45;
				switchingFragments(new AllyDropPickFragment());

			}
			else if(StaticVariables.fragmentIndexCurrentTabSchedular==informAllyByAllyDropPick3)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=53;
				switchingFragments(new AllyDropPickFragment());

			}
			else if(StaticVariables.fragmentIndexCurrentTabSchedular==informAllyByAllyDropPick4)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=60;
				switchingFragments(new AllyDropPickFragment());

			}
			else if(StaticVariables.fragmentIndexCurrentTabSchedular==informAllyByAllyDropPick5)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=70;
				switchingFragments(new AllyDropPickFragment());

			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==76)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=20;
				switchingFragments(new AddAfterSchoolFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==77)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=76;
				switchingFragments(new DisplayAllyInformationFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==78)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=77;
				switchingFragments(new AllyDropPickFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==holidaylistfragmentbyCalenderFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=11;
				switchingFragments(new CalenderFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==holidayDetailFragmentbyHolidayListFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=80;
				switchingFragments(new 	HolidayListFragment());
			}

			//New cases for search
			else if(StaticVariables.fragmentIndexCurrentTabSchedular==searchActivityByAfterSchoolFragmentFromAfterSchoolCategoriesFragmentFromAfterSchoolByChildIdFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=addAfterSchoolCategoriesFragmentByAfterSchool;//23
				switchingFragments(new 	AddAfterSchoolCategoriesFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==searchActivityByAfterSchoolFragmentFromAfterSchoolCategoriesFragmentFromCalenderFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=
						addAfterSchoolCategoriesFragmentByActivityFragmentFromCalenderFragment;//22
				switchingFragments(new 	AddAfterSchoolCategoriesFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==searchActivityByAfterSchoolFragmentFromAfterSchoolCategoriesFragmentFromGetByCalenderFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=addAfterSchoolCategoriesFragmentByCalenderdateFragment;//17;
				switchingFragments(new 	AddAfterSchoolCategoriesFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabSchedular==searchActivityByAfterSchoolFragmentFromAfterSchoolCategoriesFragmentByAddActivityFromGetByCalenderFragment)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=
						addAfterSchoolCategoriesFragmentByActivityFragmentFromGetDataByCalenderdateFragment;//37
				switchingFragments(new 	AddAfterSchoolCategoriesFragment());
			}

			/*else if(StaticVariables.fragmentIndexCurrentTabSchedular==addAfterSchoolByallyDropPickByCatIdBySubcategory)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=69;
				switchingFragments(new AllyDropPickFragment());

			}*/


			break;


		case 2:

			if(StaticVariables.fragmentIndexErrorDetailPage==errorDetailPageInsights)
			{
				StaticVariables.fragmentIndexErrorDetailPage=0;
				switchingFragments(new InsightsErrorFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabInsight==typesInsightFragment)
			{
				//finish();
				finish();
				Intent intentAccessProfile =new Intent(TabChildActivities.this, AccessProfileActivity.class);
				// set the new task and clear flags
				intentAccessProfile.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intentAccessProfile);
			}
			else if(StaticVariables.fragmentIndexCurrentTabInsight==delightTrendsFragment)
			{
				switchingFragments(new TypesInsightsFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabInsight==insightDriversFragment)
			{
				switchingFragments(new TypesInsightsFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabInsight==insightsFragment)
			{
				switchingFragments(new TypesInsightsFragment());

			}

			else if(StaticVariables.fragmentIndexCurrentTabInsight==delightTrendsByActivityFragmentFromTypesInsight)
			{
				switchingFragments(new TypesInsightsFragment());

			}

			else if(StaticVariables.fragmentIndexCurrentTabInsight==delightTrendsByActivityFragmentFromDelightTrendsFragment)
			{
				switchingFragments(new DelightTrendsFragment());

			}

			else if(StaticVariables.fragmentIndexCurrentTabInsight==insightErrorFragment)
			{
				//finish();
				finish();
				Intent intentAccessProfile =new Intent(TabChildActivities.this, AccessProfileActivity.class);
				// set the new task and clear flags
				intentAccessProfile.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intentAccessProfile);

			}
			break;

		case 3:
			if(StaticVariables.fragmentIndexFrequencyPage==frequencyPageAfterSchoolWhattodo)
			{
				if(!StaticVariables.isFrequencySaveClicked)
				{
					showMessage.showAlertFrequency(TabChildActivities.this, new OnEventListener<String>() {

						@Override
						public void onSuccess(String object) {
							// TODO Auto-generated method stub
							StaticVariables.fragmentIndexFrequencyPage=0;
							switchingFragments(new com.hatchtact.pinwi.fragment.whattodo.AddAfterSchoolFragment());
							StaticVariables.isFrequencySaveClicked=false;
						}

						@Override
						public void onFailure(String object) {
							// TODO Auto-generated method stub

						}
					});
				}
				else
				{
					StaticVariables.fragmentIndexFrequencyPage=0;
					switchingFragments(new com.hatchtact.pinwi.fragment.whattodo.AddAfterSchoolFragment());
					StaticVariables.isFrequencySaveClicked=false;
				}

			}

			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentActivityDetailByClusterId ||StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentWislistScreen)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentRecommended;
				switch (StaticVariables.screenIndex)
				{
				case 0:
					switchingFragments(new WhatToDoRecommendedFragment());
					break;
				case 1:
					switchingFragments(new WhatToDoNetworkFragment());

					break;
				case 2:
					switchingFragments(new WhatToDoExploreFragment());

					break;

				default:
					break;
				}
			}
			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentWislistScreenbyActivityList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentActivityDetailByClusterId;
				switchingFragments(new ActivityListFragment());
			}

			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentWhoisdoingthisfromactivityList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentActivityDetailByClusterId;
				switchingFragments(new ActivityListFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentWhoisdoingthisfromWishList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentWislistScreen;
				switchingFragments(new WishListFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentWhoisdoingthisfromWishListByActivityList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentWislistScreenbyActivityList;
				switchingFragments(new WishListFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentchilddetailfromWhoisdoingthisbyactivityList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentWhoisdoingthisfromactivityList;
				switchingFragments(new WhoDoingThisFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentexhilaratorfromchilddetailfromWhoisdoingthisbyactivityList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentchilddetailfromWhoisdoingthisbyactivityList;
				switchingFragments(new com.hatchtact.pinwi.fragment.whattodo.ChildDetailFragment() );
			}
			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentchilddetailfromWhoisdoingthisbywishList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentWhoisdoingthisfromWishList;
				switchingFragments(new WhoDoingThisFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentexhilaratorfromchilddetailfromWhoisdoingthisbywishList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentchilddetailfromWhoisdoingthisbywishList;
				switchingFragments(new com.hatchtact.pinwi.fragment.whattodo.ChildDetailFragment() );
			}
			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentchilddetailfromWhoisdoingthisfromWishListByActivityList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentWhoisdoingthisfromWishListByActivityList;
				switchingFragments(new WhoDoingThisFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentexhilaratorfromchilddetailfromWhoisdoingthisfromWishListByActivityList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentchilddetailfromWhoisdoingthisfromWishListByActivityList;
				switchingFragments(new com.hatchtact.pinwi.fragment.whattodo.ChildDetailFragment() );
			}

			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentAddAfterSchoolfromactivityList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentActivityDetailByClusterId;
				switchingFragments(new ActivityListFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentDispalyAllyfromAddAfterSchoolfromactivityList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentAddAfterSchoolfromactivityList;
				switchingFragments(new com.hatchtact.pinwi.fragment.whattodo.AddAfterSchoolFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentAllyDropPickfromDisplayAllyfromAddAfterSchoolfromactivityList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentDispalyAllyfromAddAfterSchoolfromactivityList;
				switchingFragments(new com.hatchtact.pinwi.fragment.whattodo.DisplayAllyInformationFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentInformAllyFromAllyDropPickAddAfterSchoolfromactivityList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentAllyDropPickfromDisplayAllyfromAddAfterSchoolfromactivityList;
				switchingFragments(new com.hatchtact.pinwi.fragment.whattodo.AllyDropPickFragment());
			}


			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentAddAfterSchoolfromwishList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentWislistScreen;
				switchingFragments(new WishListFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentDispalyAllyfromAddAfterSchoolfromwishList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentAddAfterSchoolfromwishList;
				switchingFragments(new com.hatchtact.pinwi.fragment.whattodo.AddAfterSchoolFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentAllyDropPickfromDisplayAllyfromAddAfterSchoolfromwishList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentDispalyAllyfromAddAfterSchoolfromwishList;
				switchingFragments(new com.hatchtact.pinwi.fragment.whattodo.DisplayAllyInformationFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentInformAllyFromAllyDropPickAddAfterSchoolfromwishList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentAllyDropPickfromDisplayAllyfromAddAfterSchoolfromwishList;
				switchingFragments(new com.hatchtact.pinwi.fragment.whattodo.AllyDropPickFragment());
			}


			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentAddAfterSchoolfromWishListByActivityList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentWislistScreenbyActivityList;
				switchingFragments(new WishListFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentDispalyAllyfromAddAfterSchoolfromWishListByActivityList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentAddAfterSchoolfromWishListByActivityList;
				switchingFragments(new com.hatchtact.pinwi.fragment.whattodo.AddAfterSchoolFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentAllyDropPickfromDisplayAllyfromAddAfterSchoolfromWishListByActivityList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentDispalyAllyfromAddAfterSchoolfromWishListByActivityList;
				switchingFragments(new com.hatchtact.pinwi.fragment.whattodo.DisplayAllyInformationFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==whattodoFragmentInformAllyFromAllyDropPickAddAfterSchoolfromWishListByActivityList)
			{
				StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentAllyDropPickfromDisplayAllyfromAddAfterSchoolfromWishListByActivityList;
				switchingFragments(new com.hatchtact.pinwi.fragment.whattodo.AllyDropPickFragment());
			}

			else
			{
				//finish();
				finish();
				Intent intentAccessProfile =new Intent(TabChildActivities.this, AccessProfileActivity.class);
				// set the new task and clear flags
				intentAccessProfile.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intentAccessProfile);
			}
			//finish();
			break;

		case 4:

			if(StaticVariables.fragmentIndexCurrentTabNetwork==networkFragmentConnections ||StaticVariables.fragmentIndexCurrentTabNetwork==networkFragmentRequest ||StaticVariables.fragmentIndexCurrentTabNetwork==networkFragmentDiscover)
			{
				//finish();
				finish();
				Intent intentAccessProfile =new Intent(TabChildActivities.this, AccessProfileActivity.class);
				// set the new task and clear flags
				intentAccessProfile.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intentAccessProfile);
			}
			else if(StaticVariables.fragmentIndexCurrentTabNetwork==friendDetailsByNetworkConnectionList || StaticVariables.fragmentIndexCurrentTabNetwork==profileScreenFragment)
			{
				StaticVariables.fragmentIndexCurrentTabNetwork=networkFragmentConnections;				
				switch (StaticVariables.screenIndex)
				{
				case 0:
					switchingFragments(new NetworkConnectionsFragment());
					break;
				case 1:
					switchingFragments(new NetworkRequestFragment());

					break;
				case 2:
					switchingFragments(new NetworkDiscoverFragment());

					break;
				default:
					break;
				}
			}
			else if(StaticVariables.fragmentIndexCurrentTabNetwork==childdetailByFriendDetail)
			{
				StaticVariables.fragmentIndexCurrentTabNetwork=friendDetailsByNetworkConnectionList;
				switchingFragments(new FriendDetailFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabNetwork==listOfExhilaratorsByChildIdBychilddetail)
			{
				StaticVariables.fragmentIndexCurrentTabNetwork=childdetailByFriendDetail;
				switchingFragments(new ChildDetailFragment());
			}
			/*else if(StaticVariables.fragmentIndexCurrentTabNetwork==profileScreenFragment)
			{
				StaticVariables.fragmentIndexCurrentTabNetwork=networkFragmentConnections;
				switchingFragments(new NetworkConnectionsFragment());
			}*/
			else if(StaticVariables.fragmentIndexCurrentTabNetwork==profileConnectionFragmentByProfileFragment)
			{
				StaticVariables.fragmentIndexCurrentTabNetwork=profileScreenFragment;
				switchingFragments(new MyProfileScreenFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabNetwork==childFragmentByProfileFragment)
			{
				StaticVariables.fragmentIndexCurrentTabNetwork=profileScreenFragment;
				switchingFragments(new MyProfileScreenFragment());

			}
			else if(StaticVariables.fragmentIndexCurrentTabNetwork==listOfExhilaratorsByChildIdBychilddetaiByProfileFragment)
			{
				StaticVariables.fragmentIndexCurrentTabNetwork=childFragmentByProfileFragment;
				switchingFragments(new ChildDetailFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabNetwork==listOfExhilaratorsByChildIdBychilddetaiByListOfConnectionsByProfileFragment)
			{
				StaticVariables.fragmentIndexCurrentTabNetwork=childFragmentByProfileFragmentByNetworkConnections;
				switchingFragments(new ChildDetailFragment());
			}
			else if(StaticVariables.fragmentIndexCurrentTabNetwork==childFragmentByProfileFragmentByNetworkConnections)
			{
				StaticVariables.fragmentIndexCurrentTabNetwork=profileConnectionFragmentByProfileFragment;
				switchingFragments(new ProfileConnectionsFragment());
			}


			//finish();
			break;



		default:
			super.onBackPressed();
			break;
		}
	}



	private void actionBarTitle()
	{



		switch (currentTab) 
		{
		case 0:
			setDrawerState(true,"  Notifications");
			break;

		case 1:
			if(StaticVariables.fragmentIndexCurrentTabSchedular==calenderFragmentScheduler || StaticVariables.fragmentIndexCurrentTabSchedular==afterSchoolFragmentScheduler ||StaticVariables.fragmentIndexCurrentTabSchedular==schoolFragmentScheduler)
			{
				setDrawerState(true,"  Scheduler");
			}

			else 
			{
				setDrawerState(false,"  Scheduler");

			}

			break;


		case 2:

			if(StaticVariables.fragmentIndexCurrentTabInsight==typesInsightFragment)
			{
				setDrawerState(true,"  Insights");
			}
			else
			{
				setDrawerState(false,"  Insights");
			}
			break;

		case 3:

			setDrawerState(true,"  WhatToDo");
			break;

		case 4:
			if(StaticVariables.fragmentIndexCurrentTabNetwork==networkFragmentConnections ||StaticVariables.fragmentIndexCurrentTabNetwork==networkFragmentRequest ||StaticVariables.fragmentIndexCurrentTabNetwork==networkFragmentDiscover)
			{
				setDrawerState(true,"  Network");
			}
			else
			{
				setDrawerState(false,"  Network");
			}
			break;

		default:
			break;
		}
	}

	protected void switchingFragments(android.support.v4.app.Fragment toFragment)
	{	
		/*if (getFragmentManager().getBackStackEntryCount() > 0) {
			   getFragmentManager().popBackStack();
			}*/
		android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.tabcontent_frame_layout, toFragment, "");
		fragmentTransaction.commit();	
		//  getFragmentManager().executePendingTransactions();      // <----- This is the key 
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(getFragmentManager().getBackStackEntryCount()>0)
			getFragmentManager().popBackStack();

		actionBarTitle();


		switch (v.getId()) 
		{
		case R.id.tab_notification:
			currentTab = 0;
			StaticVariables.fragmentIndexCurrentTabNotification=notificationFragmentOne;
			tab_notification.setSelected(true);
			tab_scheduler.setSelected(false);
			tab_insights.setSelected(false);
			tab_network.setSelected(false);
			tab_activity.setSelected(false);
			textViewtab_notification.setVisibility(View.GONE);
			switchingFragments(new NotificationFragment());	
			break;
		case R.id.tab_scheduler:
			currentTab = 1;
			tab_notification.setSelected(false);
			tab_scheduler.setSelected(true);
			tab_insights.setSelected(false);
			tab_network.setSelected(false);
			tab_activity.setSelected(false);
			StaticVariables.fragmentIndexCurrentTabSchedular=schoolFragmentScheduler;
			switchingFragments(SubjectActivityByChildIDFragment.getInstance());

			//StaticVariables.fragmentIndexCurrentTabSchedular=calenderFragmentScheduler;
			//switchingFragments(CalenderFragment.getInstance());
			break;

		case R.id.tab_insight:
			currentTab = 2;
			tab_notification.setSelected(false);
			tab_scheduler.setSelected(false);
			tab_insights.setSelected(true);
			tab_network.setSelected(false);
			tab_activity.setSelected(false);
			StaticVariables.fragmentIndexCurrentTabInsight=typesInsightFragment;
			switchingFragments(TypesInsightsFragment.getInstance());
			break;
		case R.id.tab_activity:
			currentTab = 3;
			tab_notification.setSelected(false);
			tab_scheduler.setSelected(false);
			tab_insights.setSelected(false);
			tab_network.setSelected(false);
			tab_activity.setSelected(true);
			//switchingFragments(new WhatToDoFragment());
			StaticVariables.fragmentIndexCurrentTabWhatToDo=whattodoFragmentRecommended;
			switchingFragments(new WhatToDoRecommendedFragment());
			break;
		case R.id.tab_network:
			currentTab = 4;
			tab_notification.setSelected(false);
			tab_scheduler.setSelected(false);
			tab_insights.setSelected(false);
			tab_network.setSelected(true);
			tab_activity.setSelected(false);
			//switchingFragments(new NetworkFragment());
			StaticVariables.fragmentIndexCurrentTabNetwork=networkFragmentConnections;
			switchingFragments(new NetworkConnectionsFragment());
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (SubscribeFragment.mHelper == null) return;

		// Pass on the activity result to the helper for handling
		if (!SubscribeFragment.mHelper.handleActivityResult(requestCode, resultCode, data)) {
			// not handled, so handle it ourselves (here's where you'd
			// perform any handling of activity results not related to in-app
			// billing...
			super.onActivityResult(requestCode, resultCode, data);
		}
		else {
			//Toast.makeText(MainActivity.this, "Purchase Activity Result:", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		try
		{
			if(sharePref!=null && gsonRegistration!=null)
			{
				parentCompleteInformation = gsonRegistration.fromJson(sharePref.getParentProfile(), ParentProfile.class);
				parentId=parentCompleteInformation.getParentID();
				StaticVariables.currentParentName=parentCompleteInformation.getFirstName();
				StaticVariables.currentParentId=parentCompleteInformation.getParentID();
			}
		}
		catch(Exception e)
		{

		}
	}
	GetNewNotificationCount model;

	private class AsyncNotificationCountByParentID extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(TabChildActivities.this))
			{
				model=serviceMethod.getNewNotificationCount(parentId);
				if(model!=null)
				{
					StaticVariables.notificationCount=model.getCount();			
				}
				else
				{
					StaticVariables.notificationCount=0;
				}
			}
			else 
			{
				ErrorCode=-1;
			}
			return ErrorCode;
		}

		@Override
		protected void onPostExecute(Integer  result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(TabChildActivities.this))
					new AsyncNotificationCountByParentID().execute();

			}
			else
			{
				if(model!=null)
				{
					if(StaticVariables.notificationCount>0)
					{
						textViewtab_notification.setVisibility(View.VISIBLE);
						textViewtab_notification.setText(StaticVariables.notificationCount + "");
					}
					else
					{
						textViewtab_notification.setVisibility(View.GONE);
					}
				}
				else
				{	
					//getError();
				}	
			}	
		}	
	}

}
