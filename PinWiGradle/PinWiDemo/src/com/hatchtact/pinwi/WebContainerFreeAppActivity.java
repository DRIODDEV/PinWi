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
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.NavDrawerListAdapterMenu;
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

public class WebContainerFreeAppActivity extends MainActionBarActivity
{
	private Button getStarted_button=null;
	private Button button_fullver=null;
	private TypeFace typeFace=null;
	private SharePreferenceClass sharePreferenceclass=null;
	WebView webView;

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
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub


		super.onCreate(savedInstanceState);

		setContentView(R.layout.webcontainer_activity);
		social=new SocialConstants(this);
		//Declaring the WebView
		webView = (WebView) findViewById(R.id.webView1);
		webView.setWebChromeClient(new WebChromeClient(){});
		webView.setWebViewClient(new myWebViewClient());
		typeFace= new TypeFace(this);
		customProgressLoader=new CustomLoader(WebContainerFreeAppActivity.this);
		serviceMethod=new ServiceMethod();
		gsonRegistration=new GsonBuilder().create();


		sharePreferenceclass=new SharePreferenceClass(WebContainerFreeAppActivity.this);
		try {
			parentCompleteInformation = gsonRegistration.fromJson(sharePreferenceclass.getParentProfile(), ParentProfile.class);
			parentId=parentCompleteInformation.getParentID();
			parentEmailId=parentCompleteInformation.getEmailAddress();
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
		getStarted_button=(Button) findViewById(R.id.button_getStarted);
		button_fullver=(Button) findViewById(R.id.button_fullver);

		typeFace.setTypefaceRegular(getStarted_button);
		typeFace.setTypefaceRegular(button_fullver);

		//free version click
		getStarted_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(WebContainerFreeAppActivity.this, ChildListFreeActivity.class);
				startActivity(intent);
				WebContainerFreeAppActivity.this.finish();

			}
		});

		//buy full version click
		button_fullver.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//in app purchase
				social.fullVersion2GoogleAnalyticsLog();
				social.fullVersion2FacebookLog();
				new GetPaymentStatusCheckAsyncTask().execute();

			/*	Intent intent=new Intent(WebContainerFreeAppActivity.this, AccessProfileActivity.class);
				startActivity(intent);
				sharePreferenceclass.setCurrentScreen(4);
				WebContainerFreeAppActivity.this.finish();*/
			}
		});
		// load slide menu items
		initializeNavigationDrawerItems();//initialize navigation drawer items
		initializeActionBar();//initialization of action bar and drawer items

		webView.getSettings().setJavaScriptEnabled(true);
		// webView.getSettings().setPluginState(PluginState.ON);
		//Setting the settings for the webview
		webView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				return true;
			}
		});
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);
		/* webView.setOnKeyListener(new View.OnKeyListener()
	        {
	            @Override
	            public boolean onKey(View v, int keyCode, KeyEvent event)
	            {
	                if(event.getAction() == KeyEvent.ACTION_DOWN)
	                {
	                    WebView webView = (WebView) v;

	                    switch(keyCode)
	                    {
	                        case KeyEvent.KEYCODE_BACK:
	                            if(webView.canGoBack())
	                            {
	                                webView.goBack();
	                                return true;
	                            }
	                            break;
	                    }
	                }

	                return false;
	            }
	        });*/


		webView.loadUrl(StaticVariables.freeAppWebUrl);
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

	@Override
	public void onBackPressed() {
		finish();
		Intent childIntent=new Intent(WebContainerFreeAppActivity.this,ChildListFreeActivity.class);
		startActivity(childIntent);
	}


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

		String string=" Instant Demo";
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
				Intent intentAboutUs =new Intent(WebContainerFreeAppActivity.this, ActivityAboutUS.class);
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
				Intent intentTutorial =new Intent(WebContainerFreeAppActivity.this, ActivityTutorial.class);
				startActivity(intentTutorial);
				break;
			/*case 3:
			Intent intentInvite =new Intent(AccessProfileActivity.this, ActivityInvite.class);
			startActivity(intentInvite);
			break;*/
			case 3:
				Intent intentInvite =new Intent(WebContainerFreeAppActivity.this, ActivityInvite.class);
				startActivity(intentInvite);
				break;
			case 5:
				Intent intentContactus =new Intent(WebContainerFreeAppActivity.this, ActivityAboutUS.class);
				startActivity(intentContactus);
				StaticVariables.webUrl="http://pinwi.in/contactus.aspx?4";

				break;
			case 1:
				mDrawerLayout.closeDrawers();
				Intent childIntent=new Intent(WebContainerFreeAppActivity.this,ChildListActivity.class);
				startActivity(childIntent);
				break;
			case 2:
				mDrawerLayout.closeDrawers();
				Intent parentIntent=new Intent(WebContainerFreeAppActivity.this,ParentRegistrationActivity.class);
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
				Intent intent = new Intent(WebContainerFreeAppActivity.this, LoginActivity.class);
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

	class myWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return super.shouldOverrideUrlLoading(view, url);    //To change body of overridden methods use File | Settings | File Templates.
		}
		boolean error;
		@Override
		public void onReceivedError( WebView view, int errorCode, String description, String failingUrl)  {

			error = true;
			System.out.println("description error" + description);
			view.setVisibility( View.GONE );
			Toast.makeText(WebContainerFreeAppActivity.this, "This feature is unavailable at the moment, please try again later.", Toast.LENGTH_LONG).show();
			finish();
		}

	}

	private com.hatchtact.pinwi.classmodel.GetPaymentStatusCheck modelStatus;
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

			if(new CheckNetwork().checkNetworkConnection(WebContainerFreeAppActivity.this))
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
						Intent intent=new Intent(WebContainerFreeAppActivity.this, AccessProfileActivity.class);
						startActivity(intent);
						sharePreferenceclass.setCurrentScreen(4);
						WebContainerFreeAppActivity.this.finish();
					}
					else
					{
						//Toast.makeText(GetStartedActivity.this,"Not Purchased ",Toast.LENGTH_LONG).show();
						StaticVariables.screenForPurchase=2;
						finish();
						Intent intent=new Intent(WebContainerFreeAppActivity.this, InAppPurchaseActivity.class);
						startActivity(intent);
					}
				}
				else
				{
					//Toast.makeText(GetStartedActivity.this,"Not Purchased ",Toast.LENGTH_LONG).show();
					StaticVariables.screenForPurchase=2;
					finish();
					Intent intent=new Intent(WebContainerFreeAppActivity.this, InAppPurchaseActivity.class);
					startActivity(intent);
				}
			}

		}
	}
}
