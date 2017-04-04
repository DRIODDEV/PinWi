package com.hatchtact.pinwi;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.GetStartedScreenSlidePagerAdapter;
import com.hatchtact.pinwi.adapter.NavDrawerListAdapterMenu;
import com.hatchtact.pinwi.classmodel.GetPaymentStatusCheck;
import com.hatchtact.pinwi.classmodel.NavigationDrawerItem;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.CustomLoader;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.SocialConstants;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GetStartedActivity extends MainActionBarActivity
{
	private TextView welcome_textView=null;
	private TextView getStartedText_textView=null;
	private Button getStarted_button=null;
	private Button button_fullver=null;
	private TypeFace typeFace=null;
	private SharePreferenceClass sharePreferenceclass=null;
	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavigationDrawerItem> navDrawerItems;
	private NavDrawerListAdapterMenu adapter;
	private ListView mDrawerList;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private Bitmap bitmapHeader;
	protected CustomLoader customProgressLoader;
	private ServiceMethod serviceMethod;
	private Gson gsonRegistration=null;
	private ParentProfile parentCompleteInformation;
	private int parentId;
	private String parentEmailId;
	private SocialConstants social;

	ViewPager viewpager;
	private GetStartedScreenSlidePagerAdapter mPagerAdapter;
	private ImageView dotone,dottwo,dotthree,dotfour,dotfive,dotsix;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub

		screenName="Welcome to PiNWi";

		super.onCreate(savedInstanceState);

		setContentView(R.layout.getstarted_activity);
		social=new SocialConstants(this);
		typeFace= new TypeFace(this);
		customProgressLoader=new CustomLoader(GetStartedActivity.this);
		serviceMethod=new ServiceMethod();
		gsonRegistration=new GsonBuilder().create();


		sharePreferenceclass=new SharePreferenceClass(GetStartedActivity.this);
		try {
			parentCompleteInformation = gsonRegistration.fromJson(sharePreferenceclass.getParentProfile(), ParentProfile.class);
			parentId=parentCompleteInformation.getParentID();
			parentEmailId=parentCompleteInformation.getEmailAddress();
			StaticVariables.currentParentId=parentId;
		}
		catch (Exception e)
		{

		}
		try {
			bitmapHeader = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.header_bg), SplashActivity.ScreenWidth, BitmapFactory.decodeResource(getResources(), R.drawable.header_bg).getHeight(), false);
		}
		catch (Exception e)
		{

		}
		welcome_textView=(TextView) findViewById(R.id.text_welcome);
		getStarted_button=(Button) findViewById(R.id.button_getStarted);
		getStartedText_textView=(TextView) findViewById(R.id.text_started);
		button_fullver=(Button) findViewById(R.id.button_fullver);

		typeFace.setTypefaceRegular(welcome_textView);
		typeFace.setTypefaceLight(getStartedText_textView);
		typeFace.setTypefaceRegular(getStarted_button);
		typeFace.setTypefaceRegular(button_fullver);
		try {
			new UpdateAppVersionAsyncTask().execute();
		}
		catch (Exception e)
		{

		}

		//free version click
		getStarted_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*sharePreferenceclass.setIsLogin(true);
				System.out.println("value of is registered in parentprofile"+sharePreferenceclass.getParentIsRegistered());
				System.out.println("value of is login in parentprofile"+sharePreferenceclass.getIsLogin());
				
				Intent intent=new Intent(GetStartedActivity.this, AccessProfileActivity.class);
				startActivity(intent);
				sharePreferenceclass.setCurrentScreen(4);
				GetStartedActivity.this.finish();*/
				social.instant_DemoAnalyticsLog("Free_Button");
				sharePreferenceclass.setIsLogin(true);
				Intent intent=new Intent(GetStartedActivity.this, ChildListFreeActivity.class);
				startActivity(intent);
				GetStartedActivity.this.finish();
			}
		});

		//buy full version click
		button_fullver.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				social.full_VersionAnalyticsLog("Main_Button");
				new GetPaymentStatusCheckAsyncTask().execute();
				/*sharePreferenceclass.setIsLogin(true);
				Intent intent=new Intent(GetStartedActivity.this, AccessProfileActivity.class);
				startActivity(intent);
				sharePreferenceclass.setCurrentScreen(4);
				GetStartedActivity.this.finish();*/
			}
		});
		// load slide menu items
		initializeNavigationDrawerItems();//initialize navigation drawer items
		initializeActionBar();//initialization of action bar and drawer items


		dotone=(ImageView)findViewById(R.id.imagedot);
		dottwo=(ImageView)findViewById(R.id.imagedottwo);
		dotthree=(ImageView)findViewById(R.id.imagedotthree);
		dotfour=(ImageView)findViewById(R.id.imagedotfour);
		dotfive=(ImageView)findViewById(R.id.imagedotfive);
		dotsix=(ImageView)findViewById(R.id.imagedotsix);
		viewpager=(ViewPager) findViewById(R.id.pager);
		viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {


				switch (position) {
					case 0:
						pageSwitcher(5);
						page=0;
						dotone.setImageResource(R.drawable.dot_darkblue);
						dottwo.setImageResource(R.drawable.dot_gray);
						dotthree.setImageResource(R.drawable.dot_gray);
						dotfour.setImageResource(R.drawable.dot_gray);
						dotfive.setImageResource(R.drawable.dot_gray);
						dotsix.setImageResource(R.drawable.dot_gray);
						break;

					case 1:
						pageSwitcher(5);
						page=1;
						dottwo.setImageResource(R.drawable.dot_darkblue);
						dotone.setImageResource(R.drawable.dot_gray);
						dotthree.setImageResource(R.drawable.dot_gray);
						dotfour.setImageResource(R.drawable.dot_gray);
						dotfive.setImageResource(R.drawable.dot_gray);
						dotsix.setImageResource(R.drawable.dot_gray);
						break;

					case 2:
						pageSwitcher(5);
						page=2;
						dotthree.setImageResource(R.drawable.dot_darkblue);
						dotone.setImageResource(R.drawable.dot_gray);
						dottwo.setImageResource(R.drawable.dot_gray);
						dotfour.setImageResource(R.drawable.dot_gray);
						dotfive.setImageResource(R.drawable.dot_gray);
						dotsix.setImageResource(R.drawable.dot_gray);
						break;

					case 3:
						pageSwitcher(5);
						page=3;
						dotfour.setImageResource(R.drawable.dot_darkblue);
						dotthree.setImageResource(R.drawable.dot_gray);
						dotone.setImageResource(R.drawable.dot_gray);
						dottwo.setImageResource(R.drawable.dot_gray);
						dotfive.setImageResource(R.drawable.dot_gray);
						dotsix.setImageResource(R.drawable.dot_gray);
						break;
					case 4:
						pageSwitcher(5);
						page=4;
						dotfour.setImageResource(R.drawable.dot_gray);
						dotthree.setImageResource(R.drawable.dot_gray);
						dotone.setImageResource(R.drawable.dot_gray);
						dottwo.setImageResource(R.drawable.dot_gray);
						dotfive.setImageResource(R.drawable.dot_darkblue);
						dotsix.setImageResource(R.drawable.dot_gray);
						break;
					case 5:
						pageSwitcher(5);
						page=5;
						dotfour.setImageResource(R.drawable.dot_gray);
						dotthree.setImageResource(R.drawable.dot_gray);
						dotone.setImageResource(R.drawable.dot_gray);
						dottwo.setImageResource(R.drawable.dot_gray);
						dotfive.setImageResource(R.drawable.dot_gray);
						dotsix.setImageResource(R.drawable.dot_darkblue);
						break;
					default:
						break;
				}


			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});




		mPagerAdapter = new GetStartedScreenSlidePagerAdapter(getSupportFragmentManager());
		viewpager.setAdapter(mPagerAdapter);
		pageSwitcher(5);
		page=0;

	}

	/**
	 *
	 */
	private void initializeNavigationDrawerItems() {
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items_free);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons_free);

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

		//navDrawerItems.add(new NavigationDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();




		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapterMenu(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
	}
	/*@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	}*/

	/**
	 *
	 */
	private void initializeActionBar() {
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

		//getActionBar().setTitle("Hi "+ parentCompleteInformation.getFirstName());

		String string=" Welcome To PiNWi";
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
	private void displayView(int position) {
		// update the main content by replacing fragments
		switch (position) {
			/*case 0:
				mDrawerLayout.closeDrawers();
				break;*/
			case 0:
				Intent intentAboutUs =new Intent(GetStartedActivity.this, ActivityAboutUS.class);
				startActivity(intentAboutUs);
				StaticVariables.webUrl="http://pinwi.in/aboutus.html";
				break;
			/*case 2:
			Intent intentSupport =new Intent(AccessProfileActivity.this, ActivityAboutUS.class);
			startActivity(intentSupport);
			StaticVariables.webUrl="http://designer.convergenttec.com/pinwi/html/faq.html";
			Intent intentSupport =new Intent(TabChildActivities.this, ActivitySupport.class);
			startActivity(intentSupport);
			break;*/
		/*	case 4:
				Intent intentTutorial =new Intent(GetStartedActivity.this, ActivityTutorial.class);
				startActivity(intentTutorial);
				break;*/
			/*case 3:
			Intent intentInvite =new Intent(AccessProfileActivity.this, ActivityInvite.class);
			startActivity(intentInvite);
			break;*/
			case 3:
				Intent intentInvite =new Intent(GetStartedActivity.this, ActivityInvite.class);
				startActivity(intentInvite);
				break;
			case 4:
				Intent intentContactus =new Intent(GetStartedActivity.this, ActivityAboutUS.class);
				startActivity(intentContactus);
				StaticVariables.webUrl="http://pinwi.in/contactus.aspx?4";

				break;
			case 1:
				mDrawerLayout.closeDrawers();
				Intent childIntent=new Intent(GetStartedActivity.this,ChildListActivity.class);
				startActivity(childIntent);
				break;
			case 2:
				mDrawerLayout.closeDrawers();
				Intent parentIntent=new Intent(GetStartedActivity.this,ParentRegistrationActivity.class);
				Bundle bundle=new Bundle();
				bundle.putBoolean("ToParentScreen", true);
				parentIntent.putExtras(bundle);
				startActivity(parentIntent);
				break;
			case 5:
				sharePreferenceclass.setIsLogin(false);
				sharePreferenceclass.setIsLogout(true);
				sharePreferenceclass.setParentProfile("");
				finish();
				Intent intent = new Intent(GetStartedActivity.this, LoginActivity.class);
				startActivity(intent);
				android.os.Process.killProcess(android.os.Process.myPid());

				break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);


	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		//	menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
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

	private GetPaymentStatusCheck modelStatus;
	private class GetPaymentStatusCheckAsyncTask extends AsyncTask<Void, Void, Integer>
	{


		public GetPaymentStatusCheckAsyncTask()
		{
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(customProgressLoader!=null)
			{
				customProgressLoader.startHandler();
			}
			/*progressDialog = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(new CheckNetwork().checkNetworkConnection(GetStartedActivity.this))
			{
				modelStatus =serviceMethod.getPaymentStatusCheck(parentId,parentEmailId);
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
				customProgressLoader.removeCallbacksHandler();
			/*	if (progressDialog.isShowing())
					progressDialog.cancel();*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{


			}
			else
			{
				if(modelStatus!=null)
				{
					if(modelStatus.getPaymentStatus()==1)
					{
						sharePreferenceclass.setIsLogin(true);
						Intent intent=new Intent(GetStartedActivity.this, AccessProfileActivity.class);
						startActivity(intent);
						sharePreferenceclass.setCurrentScreen(4);
						GetStartedActivity.this.finish();
					}
					else
					{
						//Toast.makeText(GetStartedActivity.this,"Not Purchased ",Toast.LENGTH_LONG).show();
						StaticVariables.screenForPurchase=1;
						finish();
						Intent intent=new Intent(GetStartedActivity.this, InAppPurchaseActivity.class);
						startActivity(intent);
					}
				}
				else
				{
					//Toast.makeText(GetStartedActivity.this,"Not Purchased ",Toast.LENGTH_LONG).show();
					StaticVariables.screenForPurchase=1;
					finish();
					Intent intent=new Intent(GetStartedActivity.this, InAppPurchaseActivity.class);
					startActivity(intent);
				}
			}

		}
	}
	private String versionName="";

	private class UpdateAppVersionAsyncTask extends AsyncTask<Void, Void, Integer>
	{


		public UpdateAppVersionAsyncTask()
		{
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(customProgressLoader!=null)
			{
				customProgressLoader.startHandler();
			}
			PackageManager manager = getPackageManager();
			PackageInfo info = null;
			try {
				info = manager.getPackageInfo( getPackageName(), 0);
				//"Version 3.0"
				versionName=info.versionName;
			} catch (PackageManager.NameNotFoundException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(new CheckNetwork().checkNetworkConnection(GetStartedActivity.this))
			{
				ErrorCode =serviceMethod.updateAppVersion(sharePreferenceclass.getDeviceId(),parentId,versionName);
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
				customProgressLoader.removeCallbacksHandler();
			/*	if (progressDialog.isShowing())
					progressDialog.cancel();*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{


			}
			else
			{

			}

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(timer!=null)
		{
			timer.cancel();
			timer=null;
		}
	}

	Timer timer;
	int page = 0;

	public void pageSwitcher(int seconds) {
		seconds=9;
		if(timer!=null)
		{
			timer.cancel();
			timer=null;
		}
		timer = new Timer(); // At this line a new Thread will be created
		timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
		// in
		// milliseconds
	}

	// this is an inner class...
	class RemindTask extends TimerTask {

		@Override
		public void run() {

			// As the TimerTask run on a seprate thread from UI thread we have
			// to call runOnUiThread to do work on UI thread.
			runOnUiThread(new Runnable() {
				public void run() {
					if (page > 5) { // In my case the number of pages are 5
						//timer.cancel();
						page=0;
						viewpager.setCurrentItem(page++);
					} else {
						viewpager.setCurrentItem(page++);
					}
				}
			});
		}
	}

}
