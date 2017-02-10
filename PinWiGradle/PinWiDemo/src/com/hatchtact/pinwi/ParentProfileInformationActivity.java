package com.hatchtact.pinwi;

import java.util.ArrayList;

import android.app.Activity;
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
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
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
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.NavDrawerListAdapterMenu;
import com.hatchtact.pinwi.classmodel.GetNewNotificationCount;
import com.hatchtact.pinwi.classmodel.NavigationDrawerItem;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.classmodel.RequestAddOnVersionModel;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

public class ParentProfileInformationActivity extends Activity implements AnimationListener
{
	private ImageView parentProfile_image=null;
	private ImageView notification_image=null;
	private ImageView whattodo_image=null;
	private ImageView network_image=null;
	private ImageView insights_image=null;
	private ImageView schedule_image=null;

	private TextView notification_textView=null;
	private TextView whattodo_textView=null;
	private TextView network_textView=null;
	private TextView insights_textView=null;
	private TextView schedule_textView=null;

	private TypeFace typeFace=null;

	Animation animFadein;

	private SharePreferenceClass sharePreferenceClass=null;

	private ShowMessages showMessage=null;
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;

	private Gson gsonRegistration=null;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private int parentId=0;

	private ParentProfile parentCompleteInformation;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavigationDrawerItem> navDrawerItems;
	private NavDrawerListAdapterMenu adapter;

	private RequestAddOnVersionModel requestaddonVersion=null;

	private TextView textViewnotification_image;
	private TextView whatyoulike_text;
	private static ParentProfileInformationActivity instance=null;
	private Bitmap bitmapHeader;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);

		setContentView(R.layout.access_parent_child);
		setInstance(this);

		typeFace=new TypeFace(ParentProfileInformationActivity.this);
		sharePreferenceClass=new SharePreferenceClass(ParentProfileInformationActivity.this);

		/*sharePreferenceClass.setHowPinWiWorks(false);
		sharePreferenceClass.setAtSchoolTutorial(false);
		sharePreferenceClass.setafterschoolTutorial(false);
		sharePreferenceClass.setCalenderTutorial(false);
		sharePreferenceClass.setInsightsTutorial(false);*/
		gsonRegistration=new GsonBuilder().create();
		parentCompleteInformation = gsonRegistration.fromJson(sharePreferenceClass.getParentProfile(), ParentProfile.class);
		if(parentCompleteInformation==null)
		{
			finish();
		}
		else
		{
			parentId=parentCompleteInformation.getParentID();

			showMessage=new ShowMessages(this);
			serviceMethod=new ServiceMethod();
			checkNetwork=new CheckNetwork();
			requestaddonVersion=new RequestAddOnVersionModel();

			parentProfile_image=(ImageView) findViewById(R.id.parentprofile_image);
			notification_image=(ImageView) findViewById(R.id.notification_image);
			network_image=(ImageView) findViewById(R.id.network_image);
			schedule_image=(ImageView) findViewById(R.id.schedule_image);
			whattodo_image=(ImageView) findViewById(R.id.activity_image);
			insights_image=(ImageView) findViewById(R.id.insights_image);
			textViewnotification_image=(TextView) findViewById(R.id.textViewnotification_image);
			whatyoulike_text = (TextView) findViewById(R.id.whatyoulike_text);
			textViewnotification_image.setVisibility(View.GONE);

			bitmapHeader = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.header_bg), SplashActivity.ScreenWidth, BitmapFactory.decodeResource(getResources(), R.drawable.header_bg).getHeight(), false);

			//if(!StaticVariables.notificationCountInvisible)
			new AsyncNotificationCountByParentID().execute();
			/*else
			textViewnotification_image.setVisibility(View.GONE);*/

			schedule_textView=(TextView) findViewById(R.id.schedule_text);
			insights_textView=(TextView) findViewById(R.id.insights_text);
			network_textView=(TextView) findViewById(R.id.network_text);
			whattodo_textView=(TextView) findViewById(R.id.activity_text);
			notification_textView=(TextView) findViewById(R.id.notify_text);

			typeFace.setTypefaceRegular(whatyoulike_text);
			typeFace.setTypefaceRegular(textViewnotification_image);

			typeFace.setTypefaceLight(notification_textView);
			typeFace.setTypefaceLight(network_textView);
			typeFace.setTypefaceLight(insights_textView);
			typeFace.setTypefaceLight(schedule_textView);
			typeFace.setTypefaceLight(whattodo_textView);

			if(parentCompleteInformation.getProfileImage()!=null && parentCompleteInformation.getProfileImage().trim().length()>100)
			{
				byte[] imageByte=Base64.decode(parentCompleteInformation.getProfileImage(), 0);
				if(imageByte!=null)
				{
					parentProfile_image.setImageBitmap(getRoundedRectBitmap(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length)));	
				}
			}

			// load the animation
			animFadein = AnimationUtils.loadAnimation(ParentProfileInformationActivity.this, R.anim.fade_in);

			// set animation listener
			animFadein.setAnimationListener(this);


			notification_image.startAnimation(animFadein);
			network_image.startAnimation(animFadein);
			schedule_image.startAnimation(animFadein);     
			whattodo_image.startAnimation(animFadein);
			insights_image.startAnimation(animFadein);

			notification_textView.startAnimation(animFadein);
			network_textView.startAnimation(animFadein);
			schedule_textView.startAnimation(animFadein);
			whattodo_textView.startAnimation(animFadein);
			insights_textView.startAnimation(animFadein);

			notification_image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(ParentProfileInformationActivity.this, TabChildActivities.class);
					intent.putExtra("Type", 0);
					startActivity(intent);


				}
			});

			schedule_image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(ParentProfileInformationActivity.this, TabChildActivities.class);
					intent.putExtra("Type", 1);
					startActivity(intent);
				}
			});

			insights_image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(ParentProfileInformationActivity.this, TabChildActivities.class);
					intent.putExtra("Type", 2);
					startActivity(intent);
				}
			});

			whattodo_image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(ParentProfileInformationActivity.this, TabChildActivities.class);
					intent.putExtra("Type", 3);
					startActivity(intent);
				}
			});

			network_image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(ParentProfileInformationActivity.this, TabChildActivities.class);
					intent.putExtra("Type", 4);
					startActivity(intent);
				}
			});

			// load slide menu items
			navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

			// nav drawer icons from resources
			navMenuIcons = getResources()
					.obtainTypedArray(R.array.nav_drawer_icons);

			mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
			mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

			navDrawerItems = new ArrayList<NavigationDrawerItem>();

			// adding nav drawer items to array
			// Home
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

			//	mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

			// setting the nav drawer list adapter
			adapter = new NavDrawerListAdapterMenu(getApplicationContext(),
					navDrawerItems);
			mDrawerList.setAdapter(adapter);

			//mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

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

			//getActionBar().setTitle("Hi "+ parentCompleteInformation.getFirstName());

			setTitleActionBar();

			mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
					R.drawable.menu, //nav menu toggle icon
					R.string.app_name, // nav drawer open - description for accessibility
					R.string.app_name // nav drawer close - description for accessibility
					) {
				public void onDrawerClosed(View view) {
					//getActionBar().setTitle(mTitle);
					// calling onPrepareOptionsMenu() to show action bar icons
					invalidateOptionsMenu();
				}

				public void onDrawerOpened(View drawerView) {
					//	getActionBar().setTitle(mDrawerTitle);
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


			mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					// TODO Auto-generated method stub
					displayView(position);
				}
			});
		}
	}

	/**
	 * 
	 */
	private void setTitleActionBar() {
		try {
			String string="Hi "+ parentCompleteInformation.getFirstName();
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class SlideMenuClickListener implements
	ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item

			System.out.println("in last"+position);
			displayView(position);

			//	if()

		}
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		if (animation == animFadein) {
			System.out.println("animation finish");
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}   

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}	

	/*public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {


		int targetWidth;
		if(SplashActivity.ScreenWidth >= 700)
		{
			 targetWidth = 170;

		}
		else
		{
			targetWidth = 100;
		}

		int targetHeight = targetWidth;
		Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, 
				targetHeight,Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(targetBitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true);

		Path path = new Path();
		path.addCircle(((float) targetWidth - 1) / 2,
				((float) targetHeight - 1) / 2,
				(Math.min(((float) targetWidth), 
						((float) targetHeight)) / 2),
						Path.Direction.CCW);

		canvas.clipPath(path);
		Bitmap sourceBitmap = scaleBitmapImage;
		canvas.drawBitmap(sourceBitmap, 
				new Rect(0, 0, sourceBitmap.getWidth(),
						sourceBitmap.getHeight()), 
						new Rect(0, 0, targetWidth, targetHeight),paint);
		return targetBitmap;
	}*/

	private Bitmap getRoundedRectBitmap(Bitmap bitmap) {

		int pixels;
		if(SplashActivity.ScreenWidth >= 1000)
		{
			pixels = 227;

		}
		else if(SplashActivity.ScreenWidth >= 700)
		{
			pixels = 170;

		}
		else if(SplashActivity.ScreenWidth >= 590)
		{
			pixels = 128;
		}
		else
		{
			pixels = 100;
		}

		Bitmap result = null;
		try {
			bitmap = Bitmap.createScaledBitmap(bitmap, pixels, pixels, false);

			result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
					Bitmap.Config.ARGB_8888);

			Canvas canvas = new Canvas(result);

			int color = 0xFF424242;
			Paint paint = new Paint();

			Rect rect = new Rect(0, 0, pixels, pixels);
			RectF rectF = new RectF(rect);
			int roundPx = pixels;

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
		} catch (NullPointerException e) {
			// return bitmap;
		} catch (OutOfMemoryError o) {
		}
		return result;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		/*switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}*/
		return super.onOptionsItemSelected(item);
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		//boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		//	menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	private void displayView(int position) 
	{	
		switch (position) 
		{
		case 0:
			finish();
			Intent intentAccessProfile =new Intent(ParentProfileInformationActivity.this, AccessProfileActivity.class);
			startActivity(intentAccessProfile);
			break;
		case 1:
			Intent intentAboutUs =new Intent(ParentProfileInformationActivity.this, ActivityAboutUS.class);
			startActivity(intentAboutUs);
			StaticVariables.webUrl=" http://pinwi.in/aboutus.html";
			break;
			/*case 2:
			Intent intentSupport =new Intent(ParentProfileInformationActivity.this, ActivityAboutUS.class);
			startActivity(intentSupport);
			StaticVariables.webUrl="http://designer.convergenttec.com/pinwi/html/faq.html";
			Intent intentSupport =new Intent(TabChildActivities.this, ActivitySupport.class);
			startActivity(intentSupport);
			break;*/
		case 2:
			Intent intentTutorial =new Intent(ParentProfileInformationActivity.this, ActivityTutorial.class);
			startActivity(intentTutorial);
			break;
			/*case 3:
			Intent intentInvite =new Intent(ParentProfileInformationActivity.this, ActivityInvite.class);
			startActivity(intentInvite);
			break;*/
		case 3:
			Intent intentInvite =new Intent(ParentProfileInformationActivity.this, ActivityInvite.class);
			startActivity(intentInvite);
			break;
		case 4:
			Intent intentContactus =new Intent(ParentProfileInformationActivity.this, ActivityAboutUS.class);
			startActivity(intentContactus);
			StaticVariables.webUrl=" http://pinwi.in/contactus.aspx?4";

			break;
		case 5:
			mDrawerLayout.closeDrawers();
			Intent intentSettings =new Intent(ParentProfileInformationActivity.this, SettingsActivity.class);
			startActivity(intentSettings);

			break;

			/*case 7:
			new RequestAddOnVersionTask(parentId).execute();
			break;*/
		case 6:
			sharePreferenceClass.setIsLogin(false);
			sharePreferenceClass.setIsLogout(true);
			sharePreferenceClass.setParentProfile("");  

			finish();
			Intent intent = new Intent(ParentProfileInformationActivity.this, LoginActivity.class);
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
		StaticVariables.lastTimeValue= System.currentTimeMillis();
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
					Intent intent=new Intent(ParentProfileInformationActivity.this, PasswordUnLockActivity.class);			
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
		try 
		{
			if(StaticVariables.notificationCountInvisible)
			textViewnotification_image.setVisibility(View.GONE);
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		parentCompleteInformation = gsonRegistration.fromJson(sharePreferenceClass.getParentProfile(), ParentProfile.class);
		
		if(parentCompleteInformation!=null)
		{
			setTitleActionBar();
		}
	
		if(parentCompleteInformation.getProfileImage()!=null && parentCompleteInformation.getProfileImage().trim().length()>100)
		{

			byte[] imageByte=Base64.decode(parentCompleteInformation.getProfileImage(), 0);
			if(imageByte!=null)
			{
				parentProfile_image.setImageBitmap(getRoundedRectBitmap(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length)));	
			}
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
			progressDialog = ProgressDialog.show(ParentProfileInformationActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ParentProfileInformationActivity.this))
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
				//getError();
				showAlert("Add On Request", "Your request for AddOn services has been confirmed!");
			}	
		}	
	}

	/*private void getError()
	{
		Error err = serviceMethod.getError();	
		showAlert("Warning", err.getErrorDesc());
	}*/

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

			if(checkNetwork.checkNetworkConnection(ParentProfileInformationActivity.this))
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

				if(checkNetwork.checkNetworkConnection(ParentProfileInformationActivity.this))
					new AsyncNotificationCountByParentID().execute();

			}
			else
			{
				if(model!=null)
				{
					if(StaticVariables.notificationCount>0)
					{
						textViewnotification_image.setVisibility(View.VISIBLE);

						textViewnotification_image.setText(StaticVariables.notificationCount+"");
					}
					else
					{
						textViewnotification_image.setVisibility(View.GONE);

					}
				}
				else
				{	
					//getError();
				}	
			}	
		}	
	}


	private void getError()
	{
		com.hatchtact.pinwi.classmodel.Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(ParentProfileInformationActivity.this, AccessProfileActivity.class);
		startActivity(intent);
		ParentProfileInformationActivity.this.finish();
	}

	public static ParentProfileInformationActivity getInstance() {
		return instance;
	}

	public static void setInstance(ParentProfileInformationActivity instance) {
		if(ParentProfileInformationActivity.instance==null)
		{
			ParentProfileInformationActivity.instance = instance;
		}
	}
}
