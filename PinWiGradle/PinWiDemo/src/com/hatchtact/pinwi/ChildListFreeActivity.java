package com.hatchtact.pinwi;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hatchtact.pinwi.adapter.ChildFreeListAdapter;
import com.hatchtact.pinwi.adapter.NavDrawerListAdapterMenu;
import com.hatchtact.pinwi.classmodel.AccessProfile;
import com.hatchtact.pinwi.classmodel.AccessProfileList;
import com.hatchtact.pinwi.classmodel.ChildModel;
import com.hatchtact.pinwi.classmodel.ChildProfile;
import com.hatchtact.pinwi.classmodel.GetListofChildsByParentIDList;
import com.hatchtact.pinwi.classmodel.NavigationDrawerItem;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.AppUtils;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.CustomLoader;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

import java.util.ArrayList;

public class ChildListFreeActivity extends MainActionBarActivity implements AdapterView.OnItemClickListener
{
	private TextView text_started=null;
	private TypeFace typeFace=null;
	private SharePreferenceClass sharePreferenceclass=null;
	private ListView listView;
	private ShowMessages showMessage=null;
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;

	//private GetListofChildsByParentIDList getListofChildsByParentIDList;
	private AccessProfileList accessProfileList=null;

	private ChildFreeListAdapter childListAdapter=null;
	int parentId=0;

	private ParentProfile parentCompleteInformation;
	private Gson gsonRegistration=null;
	private CustomLoader customProgressLoader;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavigationDrawerItem> navDrawerItems;
	private NavDrawerListAdapterMenu adapter;
	private ListView mDrawerList;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private Bitmap bitmapHeader;
	private String parentName;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub


		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_childlist_free);
		customProgressLoader=new CustomLoader(ChildListFreeActivity.this);

		typeFace= new TypeFace(this);
		//getListofChildsByParentIDList=new GetListofChildsByParentIDList();
		accessProfileList=new AccessProfileList();

		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(ChildListFreeActivity.this);
		checkNetwork=new CheckNetwork();
		gsonRegistration=new GsonBuilder().create();
		sharePreferenceclass=new SharePreferenceClass(ChildListFreeActivity.this);
		try {
			bitmapHeader = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.header_bg), SplashActivity.ScreenWidth, BitmapFactory.decodeResource(getResources(), R.drawable.header_bg).getHeight(), false);
		}
		catch (Exception e)
		{

		}
		text_started=(TextView) findViewById(R.id.text_started);
		listView= (ListView) findViewById(R.id.containallchildname_list);
		typeFace.setTypefaceBold(text_started);

		parentCompleteInformation = gsonRegistration.fromJson(sharePreferenceclass.getParentProfile(), ParentProfile.class);
		parentId=parentCompleteInformation.getParentID();
		parentName=parentCompleteInformation.getFirstName();
		//new GetChildName().execute();
		new GetAccessProfile(parentId).execute();

		// load slide menu items
		initializeNavigationDrawerItems();//initialize navigation drawer items
		initializeActionBar();//initialization of action bar and drawer items
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

		navDrawerItems.add(new NavigationDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));

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

		String string=" Select Child";
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
				Intent intentAboutUs =new Intent(ChildListFreeActivity.this, ActivityAboutUS.class);
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
			case 4:
				Intent intentTutorial =new Intent(ChildListFreeActivity.this, ActivityTutorial.class);
				startActivity(intentTutorial);
				break;
			/*case 3:
			Intent intentInvite =new Intent(AccessProfileActivity.this, ActivityInvite.class);
			startActivity(intentInvite);
			break;*/
			case 3:
				Intent intentInvite =new Intent(ChildListFreeActivity.this, ActivityInvite.class);
				startActivity(intentInvite);
				break;
			case 5:
				Intent intentContactus =new Intent(ChildListFreeActivity.this, ActivityAboutUS.class);
				startActivity(intentContactus);
				StaticVariables.webUrl="http://pinwi.in/contactus.aspx?4";

				break;
			case 1:
				mDrawerLayout.closeDrawers();
				Intent childIntent=new Intent(ChildListFreeActivity.this,ChildListActivity.class);
				startActivityForResult(childIntent,1000);
				break;
			case 2:
				mDrawerLayout.closeDrawers();
				Intent parentIntent=new Intent(ChildListFreeActivity.this,ParentRegistrationActivity.class);
				Bundle bundle=new Bundle();
				bundle.putBoolean("ToParentScreen", true);
				parentIntent.putExtras(bundle);
				startActivity(parentIntent);
				break;
			case 6:
				sharePreferenceclass.setIsLogin(false);
				sharePreferenceclass.setIsLogout(true);
				sharePreferenceclass.setParentProfile("");
				finish();
				Intent intent = new Intent(ChildListFreeActivity.this, LoginActivity.class);
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

/*	private class GetChildName extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			if(customProgressLoader!=null)
			{
				customProgressLoader.startHandler();
			}
			*//*progressDialog = ProgressDialog.show(ChildListActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*//*
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ChildListFreeActivity.this))
			{
				getListofChildsByParentIDList =serviceMethod.getchildListByParentId(parentId);
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
				*//*if (progressDialog.isShowing())
					progressDialog.cancel();*//*
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(ChildListFreeActivity.this))
					new GetChildName().execute();

			}
			else
			{
				if(getListofChildsByParentIDList!=null && getListofChildsByParentIDList.getGetListofChildsByParentID().size()>0)
				{
					StaticVariables.childInfo.clear();
					StaticVariables.childArrayList.clear();
					StaticVariables.isChildUpdated=true;
					for(int i=0;i<getListofChildsByParentIDList.getGetListofChildsByParentID().size();i++)
					{

						ChildProfile child = new ChildProfile();
						child.setChildID(getListofChildsByParentIDList.getGetListofChildsByParentID().get(i).getChildID());
						child.setFirstName(getListofChildsByParentIDList.getGetListofChildsByParentID().get(i).getNickName());
						//child.setNickName(getListofChildsByParentIDList.getGetListofChildsByParentID().get(i).getNickName());
						//child.setProfileImage(accessProfileList.getAccessProfileList().get(i).getProfileImage());

						StaticVariables.childInfo.add(child);
						ChildModel model=new ChildModel();
						model.setChildID(getListofChildsByParentIDList.getGetListofChildsByParentID().get(i).getChildID());
						model.setFirstName(getListofChildsByParentIDList.getGetListofChildsByParentID().get(i).getFirstName());
						model.setNickName(getListofChildsByParentIDList.getGetListofChildsByParentID().get(i).getNickName());
						StaticVariables.childArrayList.add(model);

					}

					StaticVariables.currentChild=StaticVariables.childInfo.get(0);
					childListAdapter=new ChildFreeListAdapter(ChildListFreeActivity.this, getListofChildsByParentIDList.getGetListofChildsByParentID(),ChildListFreeActivity.this);
					listView.setAdapter(childListAdapter);
					childListAdapter.notifyDataSetChanged();
					listView.setOnItemClickListener(ChildListFreeActivity.this);
				}
				else
				{
					getError();
				}
			}
		}
	}*/

	private void getError()
	{/*
		Error err = serviceMethod.getError();
		showMessage.showAlert("Warning", err.getErrorDesc());
	 */}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		//intent free app
		StaticVariables.freeAppWebUrl="http://demo.pinwi.in/interst-driver.aspx";
		//StaticVariables.freeAppWebUrl="http://pinwidemo.staging4.nz-technologies.com/interst-driver.aspx";
		if(accessProfileList!=null&& accessProfileList.getAccessProfileList().size()>0)
		{
			StaticVariables.freeAppWebUrl=StaticVariables.freeAppWebUrl+"?ChildID="+
					accessProfileList.getAccessProfileList().get(position).getProfileID()+"&&ParentID="+parentId+"&&ParentName="+parentName;
		}
		Intent intent=new Intent(ChildListFreeActivity.this, WebContainerFreeAppActivity.class);
		startActivity(intent);
		ChildListFreeActivity.this.finish();
	}

	@Override
	public void onBackPressed() {
		finish();
		Intent childIntent=new Intent(ChildListFreeActivity.this,GetStartedActivity.class);
		startActivity(childIntent);
	}


	private class GetAccessProfile extends AsyncTask<Void, Void, Integer>
	{
		private int parentId;
		private boolean isNeedToShowProgress=false;

		public GetAccessProfile(int parentId)
		{
			// TODO Auto-generated constructor stub
			this.parentId = parentId;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			customProgressLoader.startHandler();
			/*	progressDialogdeAccessProfile = ProgressDialog.show(AccessProfileActivity.this, "", StaticVariables.progressBarText, false);
				progressDialogdeAccessProfile.setCancelable(false);*/
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ChildListFreeActivity.this))
			{
				accessProfileList = serviceMethod.getaccessProfile(parentId);
				if(accessProfileList!=null&& accessProfileList.getAccessProfileList().size()>0)
				{
					accessProfileList.getAccessProfileList().remove(0);
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

				customProgressLoader.removeCallbacksHandler();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(ChildListFreeActivity.this))
					new GetAccessProfile(parentId).execute();
			}
			else
			{

				StaticVariables.childInfo.clear();
				if(accessProfileList!=null && accessProfileList.getAccessProfileList().size()>0)
				{

					StaticVariables.childInfo.clear();
					StaticVariables.childArrayList.clear();
					StaticVariables.isChildUpdated=true;
					/*for(int i=0;i<accessProfileList.getGetListofChildsByParentID().size();i++)
					{

						ChildProfile child = new ChildProfile();
						child.setChildID(getListofChildsByParentIDList.getGetListofChildsByParentID().get(i).getChildID());
						child.setFirstName(getListofChildsByParentIDList.getGetListofChildsByParentID().get(i).getNickName());
						//child.setNickName(getListofChildsByParentIDList.getGetListofChildsByParentID().get(i).getNickName());
						//child.setProfileImage(accessProfileList.getAccessProfileList().get(i).getProfileImage());

						StaticVariables.childInfo.add(child);
						ChildModel model=new ChildModel();
						model.setChildID(getListofChildsByParentIDList.getGetListofChildsByParentID().get(i).getChildID());
						model.setFirstName(getListofChildsByParentIDList.getGetListofChildsByParentID().get(i).getFirstName());
						model.setNickName(getListofChildsByParentIDList.getGetListofChildsByParentID().get(i).getNickName());
						StaticVariables.childArrayList.add(model);

					}

					StaticVariables.currentChild=StaticVariables.childInfo.get(0);*/
					childListAdapter=new ChildFreeListAdapter(ChildListFreeActivity.this, accessProfileList.getAccessProfileList(),ChildListFreeActivity.this);
					listView.setAdapter(childListAdapter);
					childListAdapter.notifyDataSetChanged();
					listView.setOnItemClickListener(ChildListFreeActivity.this);
				}
				else
				{
					getError();
				}
			}
		}



	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode)
		{
			case 1000:
				new GetAccessProfile(parentId).execute();
				break;
			default:
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
