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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hatchtact.pinwi.adapter.AccessProfileListAdapter;
import com.hatchtact.pinwi.adapter.NavDrawerListAdapterMenu;
import com.hatchtact.pinwi.adapter.ScreenSlidePagerAdapter;
import com.hatchtact.pinwi.child.ChildTutorialActivity;
import com.hatchtact.pinwi.child.SoundEffect;
import com.hatchtact.pinwi.classmodel.AccessProfile;
import com.hatchtact.pinwi.classmodel.AccessProfileList;
import com.hatchtact.pinwi.classmodel.ChildProfile;
import com.hatchtact.pinwi.classmodel.GetChildAfterSchoolActiviesByDaySubjectModelList;
import com.hatchtact.pinwi.classmodel.GetChildSubjectActiviesByDaySubjectModelList;
import com.hatchtact.pinwi.classmodel.NavigationDrawerItem;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.classmodel.PassCode;
import com.hatchtact.pinwi.classmodel.PassCodeList;
import com.hatchtact.pinwi.classmodel.RequestAddOnVersionModel;
import com.hatchtact.pinwi.classmodel.SubjectActivities;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

public class AccessProfileActivity extends Activity implements OnItemClickListener
{
	private SharePreferenceClass sharePreferenceClass=null;
	private ListView listView=null; 
	private AccessProfileListAdapter accessProfileListAdapter=null;
	private int parentId=0;
	private ServiceMethod serviceMethod=null;
	private Gson gsonRegistration=null;
	private ShowMessages showMessage=null;
	private CheckNetwork checkNetwork=null;
	private AccessProfileList accessProfileList=null;
	private ParentProfile parentCompleteInformation;

	public static GetChildAfterSchoolActiviesByDaySubjectModelList subjectsFetchedAfterSchool = null;
	public static GetChildSubjectActiviesByDaySubjectModelList subjectsFetched = null;
	public static ArrayList<SubjectActivities> subjectsScheduled = new ArrayList<SubjectActivities>();
	public static SoundEffect soundEffectTransition=null;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavigationDrawerItem> navDrawerItems;
	private NavDrawerListAdapterMenu adapter;
	private ListView mDrawerList;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private RequestAddOnVersionModel requestaddonVersion=null;
	private static AccessProfileActivity instance=null;
	private float earnedPointsForChildSelected = 0;
	private Bitmap bitmapHeader;

	private ImageView imgUser,imgLock;
	private TextView txtParentName,txtChildrenName,childAccessheading;
	private RelativeLayout layoutaccessprofile,layoutimgLock;
	private TypeFace typeface;
	private AccessProfile model;
	private String txtDummyData="Access your profile to schedule activities, view insights, see what to do and network. ";
	private View layout_parentcard;






	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub

		//screenName="Access your Profile";

		super.onCreate(savedInstanceState);

		setContentView(R.layout.accessprofile_activity);
		StaticVariables.statusChild=0;
		setInstance(this);

		sharePreferenceClass=new SharePreferenceClass(AccessProfileActivity.this);
		listView=(ListView) findViewById(R.id.containall_list);
		imgUser=(ImageView) findViewById(R.id.imgUser);
		imgLock=(ImageView) findViewById(R.id.imgLock);
		txtParentName=(TextView) findViewById(R.id.txtParentName);;
		txtChildrenName=(TextView) findViewById(R.id.txtChildrenName);
		childAccessheading=(TextView) findViewById(R.id.childAccessheading);
		layoutaccessprofile=(RelativeLayout) findViewById(R.id.layoutaccessprofile);
		layoutimgLock=(RelativeLayout) findViewById(R.id.layoutimgLock);
		typeface=new TypeFace(this);
		typeface.setTypefaceRegular(txtParentName);
		typeface.setTypefaceLight(txtChildrenName);
		typeface.setTypefaceLight(childAccessheading);
		layout_parentcard=(View) findViewById(R.id.layout_parentcard);
		listView.setEnabled(false);
		listView.setFocusable(false);
		listView.setClickable(false);
		layoutimgLock.setFocusable(true);
		layoutimgLock.setClickable(true);
		layout_parentcard.setVisibility(View.GONE);
		childAccessheading.setVisibility(View.GONE);
		layoutaccessprofile.setVisibility(View.GONE);
		layoutimgLock.setVisibility(View.GONE);


		imgLock.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				layoutaccessprofile.setAlpha(1);
				imgLock.setVisibility(View.GONE);
				layoutimgLock.setVisibility(View.GONE);
				if(accessProfileListAdapter!=null)
					accessProfileListAdapter.notifyDataSetChanged();

				listView.setEnabled(true);
				listView.setFocusable(true);
				listView.setClickable(true);
				listView.invalidate();
				layoutimgLock.setFocusable(false);
				layoutimgLock.setClickable(false);
			}
		});

		/*sharePreferenceClass.setCalenderTutorial(false);
		sharePreferenceClass.setAtSchoolTutorial(false);
		sharePreferenceClass.setafterschoolTutorial(false);
		sharePreferenceClass.setHowPinWiWorks(false);
		sharePreferenceClass.setInsightsTutorial(false);*/


		if(!sharePreferenceClass.gethowPinWiWorks())
		{/*
			sharePreferenceClass.setHowPinWiWorks(true);
			ScreenSlidePagerAdapter.NUM_PAGES=4;

			Intent tutorial=new Intent(AccessProfileActivity.this, TutorialPageActivity.class);
			startActivity(tutorial);
			StaticVariables.currentTutorialValue=StaticVariables.howPiNWiWorksTutorial;
		 */}


		bitmapHeader = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.header_bg), SplashActivity.ScreenWidth, BitmapFactory.decodeResource(getResources(), R.drawable.header_bg).getHeight(), false);

		accessProfileList=new AccessProfileList();
		checkNetwork=new CheckNetwork();
		showMessage=new ShowMessages(AccessProfileActivity.this);
		serviceMethod=new ServiceMethod();
		gsonRegistration=new GsonBuilder().create();

		parentCompleteInformation = gsonRegistration.fromJson(sharePreferenceClass.getParentProfile(), ParentProfile.class);
		parentId=parentCompleteInformation.getParentID();

		try 
		{
			StaticVariables.currentParentName=parentCompleteInformation.getFirstName();
			StaticVariables.currentParentId=parentCompleteInformation.getParentID();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(parentCompleteInformation.getAutolockTime()!=null && parentCompleteInformation.getAutolockTime().trim().length()>0)
		{
			StaticVariables.autolockTimeValue=Integer.parseInt(parentCompleteInformation.getAutolockTime().split(" ")[0]);
			StaticVariables.timeforPasscode=StaticVariables.autolockTimeValue *(60*1000);
		}
		else
		{
			StaticVariables.forPasscode=true;
		}

		if(soundEffectTransition==null)
		{
			soundEffectTransition = new SoundEffect(AccessProfileActivity.this, R.raw.pageflip);
		}

		requestaddonVersion=new RequestAddOnVersionModel();
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




		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapterMenu(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter); 

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());


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

		String string="Welcome To PiNWi";
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
		new GetAccessProfile(parentId).execute();

	}



	private ProgressDialog progressDialogdeAccessProfile=null;

	private class GetAccessProfile extends AsyncTask<Void, Void, Integer>
	{
		private int parentId;

		public GetAccessProfile(int parentId)
		{
			// TODO Auto-generated constructor stub 
			this.parentId = parentId;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialogdeAccessProfile = ProgressDialog.show(AccessProfileActivity.this, "", StaticVariables.progressBarText, false);
			progressDialogdeAccessProfile.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(AccessProfileActivity.this))
			{
				accessProfileList = serviceMethod.getaccessProfile(parentId);	
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
				if (progressDialogdeAccessProfile.isShowing())
					progressDialogdeAccessProfile.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(AccessProfileActivity.this))
					new GetAccessProfile(parentId).execute();
			}
			else
			{
				StaticVariables.childInfo.clear();
				if(accessProfileList!=null && accessProfileList.getAccessProfileList().size()>0)
				{


					parentCompleteInformation.setProfileImage(accessProfileList.getAccessProfileList().get(0).getProfileImage());

					parentCompleteInformation.setFirstName(accessProfileList.getAccessProfileList().get(0).getFirstName());
					parentCompleteInformation.setPasscode(accessProfileList.getAccessProfileList().get(0).getPasscode());
					String parentInformation = gsonRegistration.toJson(parentCompleteInformation);
					sharePreferenceClass.setParentProfile(parentInformation);  

					try 
					{
						StaticVariables.currentParentName=parentCompleteInformation.getFirstName();
						StaticVariables.parentPasscodeModel.setPassCode(accessProfileList.getAccessProfileList().get(0).getPasscode());
						StaticVariables.parentPasscodeModel.setPassCodeType(1);						
						StaticVariables.parentPasscodeModel.setProfileId(accessProfileList.getAccessProfileList().get(0).getProfileID());

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					setParentData(accessProfileList.getAccessProfileList().get(0));
					accessProfileList.getAccessProfileList().remove(0);
					accessProfileListAdapter=new AccessProfileListAdapter(AccessProfileActivity.this, accessProfileList.getAccessProfileList());

					listView.setAdapter(accessProfileListAdapter);
					listView.setOnItemClickListener(AccessProfileActivity.this);
					ArrayList<PassCode> listPasscode=new ArrayList<PassCode>();

					for(int i=0;i<accessProfileList.getAccessProfileList().size();i++)
					{
						if(accessProfileList.getAccessProfileList().get(i).getProfileType()==2)
						{
							ChildProfile child = new ChildProfile();
							child.setChildID(accessProfileList.getAccessProfileList().get(i).getProfileID());
							child.setFirstName(accessProfileList.getAccessProfileList().get(i).getFirstName());
							child.setProfileImage(accessProfileList.getAccessProfileList().get(i).getProfileImage());

							PassCode pcChild = new  PassCode();

							pcChild.setPassCodeType(2);
							pcChild.setProfileId(child.getChildID());
							pcChild.setPassCode(accessProfileList.getAccessProfileList().get(i).getPasscode());
							listPasscode.add(pcChild);
							StaticVariables.childInfo.add(child);
						}
					}	

					if(StaticVariables.childPasscodeList!=null)
						StaticVariables.childPasscodeList.setPasscodeList(listPasscode);
					else
					{
						StaticVariables.childPasscodeList=new PassCodeList();
						StaticVariables.childPasscodeList.setPasscodeList(listPasscode);
					}
					/** Crash when moving to settings in navigation drawer*/
					StaticVariables.currentChild=StaticVariables.childInfo.get(0);
				}
				else
				{	
					getError();
				}	
			}	
		}

		private void setParentData(AccessProfile accessProfile) 
		{
			// TODO Auto-generated method stub
			model=accessProfile;
			layout_parentcard.setVisibility(View.VISIBLE);
			childAccessheading.setVisibility(View.VISIBLE);
			layoutaccessprofile.setVisibility(View.VISIBLE);
			layoutimgLock.setVisibility(View.VISIBLE);

			txtParentName.setText(model.getFirstName());
			txtChildrenName.setMaxLines(4);
			txtChildrenName.setText(txtDummyData);
			if(model.getProfileImage()!=null && model.getProfileImage().trim().length()>100)
			{
				byte[] imageByteParent=Base64.decode(model.getProfileImage(), 0);
				if(imageByteParent!=null)
				{
					imgUser.setBackgroundResource(0);	

					imgUser.setImageBitmap(getRoundedRectBitmap(BitmapFactory.decodeByteArray(imageByteParent, 0, imageByteParent.length)));	
				}
			}
			else
			{
				imgUser.setBackgroundResource(0);
				imgUser.setImageBitmap(getRoundedRectBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.parent_image)));			
			}

			layout_parentcard.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(parentCompleteInformation.getPasscode().toString()!=null && parentCompleteInformation.getPasscode().toString().equals(""))
					{
						/*Intent intent = new Intent(AccessProfileActivity.this, ParentProfileInformationActivity.class);
						StaticVariables.currentChild=StaticVariables.childInfo.get(0);
						startActivity(intent);*/
						Intent intent=new Intent(AccessProfileActivity.this, TabChildActivities.class);
						StaticVariables.currentChild=StaticVariables.childInfo.get(0);
						intent.putExtra("Type", 1);
						startActivity(intent);
						/** Fading Transition Effect */
						/*AccessProfileActivity.this.overridePendingTransition(R.anim.grow_from_bottom, R.anim.shrink_from_top);
						 */AccessProfileActivity.this.finish();
					}
					else  
					{ 
						StaticVariables.currentChild=StaticVariables.childInfo.get(0);

						Intent intent = new Intent(AccessProfileActivity.this, PasswordUnLockActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt("ProfileId", parentId);
						bundle.putBoolean("ToLoadNextScreen", true);
						intent.putExtras(bundle);
						startActivity(intent);
						//AccessProfileActivity.this.finish();
					}
				}
			});

		}	

	}


	private Bitmap getRoundedRectBitmap(Bitmap bitmap) 
	{

		int pixels;

		if(SplashActivity.ScreenWidth >= 2000)
		{
			pixels = 280;

		}

		else if(SplashActivity.ScreenWidth >= 1000)
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
	private void getError()
	{/*
		com.demo.pinwi.classmodel.Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());
	 */} 

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub	
		/*	if(position==0)
		{
			if(parentCompleteInformation.getPasscode().toString()!=null && parentCompleteInformation.getPasscode().toString().equals(""))
			{
				Intent intent = new Intent(AccessProfileActivity.this, ParentProfileInformationActivity.class);
				StaticVariables.currentChild=StaticVariables.childInfo.get(0);
				startActivity(intent);
				AccessProfileActivity.this.finish();
			}
			else  
			{ 
				StaticVariables.currentChild=StaticVariables.childInfo.get(0);

				Intent intent = new Intent(AccessProfileActivity.this, PasswordUnLockActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("ProfileId", parentId);
				bundle.putBoolean("ToLoadNextScreen", true);
				intent.putExtras(bundle);
				startActivity(intent);
				//AccessProfileActivity.this.finish();
			}
		}  
		else*/
		{		
			earnedPointsForChildSelected = accessProfileList.getAccessProfileList().get(position).getEarnedPoints();
			StaticVariables.currentChild=StaticVariables.childInfo.get(position);	
			StaticVariables.isTutorialSoundEnabled = true;
			callingWebServiceForChildData();		
		}
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
					Intent intent=new Intent(AccessProfileActivity.this, PasswordUnLockActivity.class);			
					Bundle bundle = new Bundle();
					bundle.putInt("ProfileId", parentId);
					bundle.putBoolean("ToLoadNextScreen", true);
					intent.putExtras(bundle);
					startActivity(intent);
					//AccessProfileActivity.this.finish();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		StaticVariables.forPasscode=false;
		StaticVariables.lastTimeValue=System.currentTimeMillis();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		StaticVariables.forPasscode=true;
		StaticVariables.lastTimeValue=0;
	}




	/**  ---------------------Child Module Methods-----------------------   */
	private void callingWebServiceForChildData()
	{
		// TODO Auto-generated method stub
		if(subjectsFetched!=null)
			subjectsFetched.getListOfSchoolSubjects().clear();

		if(subjectsFetchedAfterSchool!=null)
			subjectsFetchedAfterSchool.getListOfAfterSchoolSubjects().clear();

		subjectsScheduled.clear();

		StaticVariables.daysAgo = "0";

		new GetStatusForChildModule(StaticVariables.currentChild.getChildID()).execute();

	}

	private class GetStatusForChildModule extends AsyncTask<Void, Void, Integer>
	{
		private int childID;

		public GetStatusForChildModule(int childID)
		{
			// TODO Auto-generated constructor stub 
			this.childID = childID;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = ProgressDialog.show(AccessProfileActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);


		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(AccessProfileActivity.this))
			{
				ErrorCode = serviceMethod.getCurrentDayRatingStatusForChildModule(childID);
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

			/*try {
				if (progressDialog.isShowing())
					progressDialog.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");
				try {
					if (progressDialog.isShowing())
						progressDialog.cancel();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(checkNetwork.checkNetworkConnection(AccessProfileActivity.this))
					new GetStatusForChildModule(childID).execute();
			}
			/*else if(result == 0)//Directly Move to DashBoard Activity of Child Module 
			{
				Intent intent = new Intent(AccessProfileActivity.this, ChildDashboardActivity.class);

				//bundle.putInt("ParentId", parentId);
				if(earnedPointsForChildSelected != 0)
				{
					Bundle bundle = new Bundle();
					bundle.putBoolean("PlaySound", true);
					intent.putExtras(bundle);
				}
				startActivity(intent);

				AccessProfileActivity.this.finish();
			}*/
			else /*if(result == 1)*/// Fetch Subjects by Day for that child one by one
			{
				StaticVariables.statusChild=result;//holding status of child 
				new GetChildAfterSchoolActiviesByDayForChildModule(childID).execute();
			}
		}	
	}

	private class GetChildAfterSchoolActiviesByDayForChildModule extends AsyncTask<Void, Void, Integer>
	{
		private int childID;

		public GetChildAfterSchoolActiviesByDayForChildModule(int childID)
		{
			// TODO Auto-generated constructor stub 
			this.childID = childID;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			/*progressDialog = ProgressDialog.show(AccessProfileActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(AccessProfileActivity.this))
			{
				ErrorCode = serviceMethod.getChildAfterSchoolActiviesByDayForChildModule(childID,"0");
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

			/*try {
				if (progressDialog.isShowing())
					progressDialog.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(AccessProfileActivity.this))
					new GetChildAfterSchoolActiviesByDayForChildModule(childID).execute();
			}
			else if(result == 0)//Call another webservice
			{
				new GetChildSubjectActiviesByDayForChildModule(childID).execute();

				//				if(subjectsFetched.getListOfAfterSchoolSubjects().size()>0)
				//				{
				//					//then toh fine h ji
				//				}
				//				else
				//					getError();
			}

		}	
	}

	private class GetChildSubjectActiviesByDayForChildModule extends AsyncTask<Void, Void, Integer>
	{
		private int childID;

		public GetChildSubjectActiviesByDayForChildModule(int childID)
		{
			// TODO Auto-generated constructor stub 
			this.childID = childID;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			/*progressDialog = ProgressDialog.show(AccessProfileActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(AccessProfileActivity.this))
			{
				ErrorCode = serviceMethod.getChildSubjectActiviesByDayForChildModule(childID,"0");
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
				if (progressDialog.isShowing())
					progressDialog.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");



				if(checkNetwork.checkNetworkConnection(AccessProfileActivity.this))
					new GetChildSubjectActiviesByDayForChildModule(childID).execute();
			}
			else if(result == 0)//Move to Star Rating if any subjects are scheduled
			{
				if(subjectsFetchedAfterSchool!=null && subjectsFetchedAfterSchool.getListOfAfterSchoolSubjects().size()>0)
				{
					for(int i=0;i<subjectsFetchedAfterSchool.getListOfAfterSchoolSubjects().size();i++)
					{
						SubjectActivities subjectActivities = new SubjectActivities();
						subjectActivities.setSubjectID(subjectsFetchedAfterSchool.getListOfAfterSchoolSubjects().get(i).getActivityID());
						subjectActivities.setSubjectName(subjectsFetchedAfterSchool.getListOfAfterSchoolSubjects().get(i).getName());
						subjectsScheduled.add(subjectActivities);
					}

				}

				if(subjectsFetched!=null && subjectsFetched.getListOfSchoolSubjects().size()>0)
				{
					for(int i=0;i<subjectsFetched.getListOfSchoolSubjects().size();i++)
					{
						SubjectActivities subjectActivities = new SubjectActivities();
						subjectActivities.setSubjectID(subjectsFetched.getListOfSchoolSubjects().get(i).getActivityID());
						subjectActivities.setSubjectName(subjectsFetched.getListOfSchoolSubjects().get(i).getName());
						subjectsScheduled.add(subjectActivities);
					}

				}

				//if(subjectsScheduled.size()>0)
				{

					/*Intent intent = new Intent(AccessProfileActivity.this, ChildMainDashboardActivity.class);
					startActivity(intent);
					 *//** Fading Transition Effect *//*
					AccessProfileActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

					AccessProfileActivity.this.finish();*/
					/*	Intent intent = new Intent(AccessProfileActivity.this, ChildTutorialActivity.class);
					startActivity(intent);
					AccessProfileActivity.this.finish();*/

					/*if(parentCompleteInformation.getPasscode().toString()!=null && parentCompleteInformation.getPasscode().toString().equals(""))
					{
						Intent intent = new Intent(AccessProfileActivity.this, ChildTutorialActivity.class);
						startActivity(intent);
						AccessProfileActivity.this.finish();
					}
					else  
					{ */
					//StaticVariables.currentChild=StaticVariables.childInfo.get(0);


					if(StaticVariables.childPasscodeList!=null)
					{
						for(int i=0;i<StaticVariables.childPasscodeList.getPasscodeList().size();i++)
						{
							if(StaticVariables.childPasscodeList.getPasscodeList().get(i).getProfileId()==StaticVariables.currentChild.getChildID())
							{
								if(StaticVariables.childPasscodeList.getPasscodeList().get(i).getPassCode()!=null&& !StaticVariables.childPasscodeList.getPasscodeList().get(i).getPassCode().trim().equalsIgnoreCase("") && StaticVariables.childPasscodeList.getPasscodeList().get(i).getPassCode().trim().length()==4)
								{
									Intent intent = new Intent(AccessProfileActivity.this, PasswordUnLockActivityChild.class);
									Bundle bundle = new Bundle();
									bundle.putInt("ProfileId", StaticVariables.childPasscodeList.getPasscodeList().get(i).getProfileId());
									bundle.putBoolean("ToLoadNextScreen", true);
									intent.putExtras(bundle);
									startActivity(intent);
									AccessProfileActivity.this.finish();
								}
								else
								{
									Intent intent = new Intent(AccessProfileActivity.this, ChildTutorialActivity.class);
									startActivity(intent);
									AccessProfileActivity.this.finish();
								}
								break;
							}

						}

					}
					else
					{
						Intent intent = new Intent(AccessProfileActivity.this, ChildTutorialActivity.class);
						startActivity(intent);
						AccessProfileActivity.this.finish();
					}

					//AccessProfileActivity.this.finish();
					//}


					//then toh fine h ji
					//					Intent intent = new Intent(AccessProfileActivity.this, ChildSetStarRatingActivity.class);
					//					Bundle bundle = new Bundle();
					//					bundle.putInt("ParentId", parentId);
					//					intent.putExtras(bundle);
					//					startActivity(intent);
					//					AccessProfileActivity.this.finish();

				}
				/*else
					getError();*/
			}

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

	private void displayView(int position) {
		// update the main content by replacing fragments
		switch (position) {
		case 0:
			mDrawerLayout.closeDrawers();

			break;
		case 1:
			Intent intentAboutUs =new Intent(AccessProfileActivity.this, ActivityAboutUS.class);
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
		case 2:
			Intent intentTutorial =new Intent(AccessProfileActivity.this, ActivityTutorial.class);
			startActivity(intentTutorial);
			break;
			/*case 3:
			Intent intentInvite =new Intent(AccessProfileActivity.this, ActivityInvite.class);
			startActivity(intentInvite);
			break;*/
		case 3:
			Intent intentInvite =new Intent(AccessProfileActivity.this, ActivityInvite.class);
			startActivity(intentInvite);
			break;
		case 4:
			Intent intentContactus =new Intent(AccessProfileActivity.this, ActivityAboutUS.class);
			startActivity(intentContactus);
			StaticVariables.webUrl="http://pinwi.in/contactus.aspx?4";

			break;
		case 5:
			mDrawerLayout.closeDrawers();
			finish();
			StaticVariables.isSettingsFromAccessProfile=true;
			Intent intentSettings =new Intent(AccessProfileActivity.this, SettingsActivity.class);
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
			Intent intent = new Intent(AccessProfileActivity.this, LoginActivity.class);
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
			progressDialog = ProgressDialog.show(AccessProfileActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(AccessProfileActivity.this))
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


	public static AccessProfileActivity getInstance() {
		return instance;
	}

	public static void setInstance(AccessProfileActivity instance) {
		AccessProfileActivity.instance = instance;
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


	/*@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//android.os.Process.killProcess(android.os.Process.myPid());
		super.onBackPressed();
			stopService(new Intent(getBaseContext(), OnClearFromService.class));

	}*/

	/*@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();	  
		AppEventsLogger.deactivateApp(this);

	}*/

}
