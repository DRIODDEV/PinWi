package com.hatchtact.pinwi;

import java.lang.reflect.Field;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.fragment.HolidayListFragment;
import com.hatchtact.pinwi.fragment.SettingsFragment;
import com.hatchtact.pinwi.fragment.insights.OnFragmentAttachedListener;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.StaticVariables;

public class SettingsActivity extends FragmentActivity implements OnFragmentAttachedListener
{

	private ParentProfile parentCompleteInformation;
	private Gson gsonRegistration=null;

	private int parentId=0;
	private SharePreferenceClass sharePref;
	private Bitmap bitmapHeader;

	private final int settingsFragmentFromSettings=199;
	private final int notificationFragmentFromSettings=200;
	private final int holidayListFragmentFromSettings=201;
	//private final int holidayCalenderFragmentFromSettings=201;
	//private final int holidayListFragmentFromHolidayCalender=202;
	private final int holidayDetailFragmentFromHolidayListFragment=202;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frame_layout_setting);
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if(menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception ex) {
			// Ignore
		}

		sharePref=new SharePreferenceClass(SettingsActivity.this);

		gsonRegistration=new GsonBuilder().create();

		parentCompleteInformation = gsonRegistration.fromJson(sharePref.getParentProfile(), ParentProfile.class);
		parentId=parentCompleteInformation.getParentID();
		bitmapHeader = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.header_bg), SplashActivity.ScreenWidth, BitmapFactory.decodeResource(getResources(), R.drawable.header_bg).getHeight(), false);


		android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		StaticVariables.fragmentIndexCurrentTabSettings=settingsFragmentFromSettings;


		if(!StaticVariables.isHolidayCalenderFromScheduler)
		{
			StaticVariables.fragmentIndexCurrentTabSettings=settingsFragmentFromSettings;
			switchingFragments(new SettingsFragment());
		}
		else
		{
			//switchingFragments(new HolidayCalenderFragment());
			switchingFragments(new HolidayListFragment());
		}
		fragmentTransaction.commit();			

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

		//getActionBar().setTitle("  Settings");

		SpannableString s = new SpannableString("  Settings");
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


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);

		for(int i=0;i<StaticVariables.childInfo.size();i++)
		{
			menu.add(0, i, i, StaticVariables.childInfo.get(i).getFirstName()).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

		}


		for(int i = 0; i < menu.size(); i++) {
			MenuItem item = menu.getItem(i);
			SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
			spanString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spanString.length(), 0); //fix the color to white
			item.setTitle(spanString);
		}


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
	public void onBackPressed() {
		// TODO Auto-generated method stub

		onBackSettings();
		//new SetReminderAsync().execute();

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
					Intent intent=new Intent(SettingsActivity.this, PasswordUnLockActivity.class);			
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
	public void onFragmentAttached(boolean navigationDrawer, String moduleName) {
		// TODO Auto-generated method stub
		setActionBarTitle(moduleName);
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

	protected void switchingFragments(android.support.v4.app.Fragment toFragment)
	{	
		/*if (getFragmentManager().getBackStackEntryCount() > 0) {
			   getFragmentManager().popBackStack();
			}*/
		android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.framelayout1, toFragment, "");
		fragmentTransaction.commit();	
		//  getFragmentManager().executePendingTransactions();      // <----- This is the key 


	}

	/*private void getError()
	{

		com.hatchtact.pinwi.classmodel.Error err = new ServiceMethod().getError();	
		//new ShowMessages(SettingsActivity.this).showAlert("Alert", err.getErrorDesc());
		new ShowMessages(SettingsActivity.this).showAlert("Alert", err.getErrorDesc());


	}*/


	public void onBackSettings() 
	{
		// TODO Auto-generated method stub
		//super.onBackPressed();
		/*if(StaticVariables.isHolidayCalenderFromScheduler)
				{
					StaticVariables.isHolidayCalenderFromScheduler=false;
					finish();
				}*/


		if(StaticVariables.fragmentIndexCurrentTabSettings==notificationFragmentFromSettings)//200
		{
			StaticVariables.fragmentIndexCurrentTabSettings=settingsFragmentFromSettings;//199
			switchingFragments(new SettingsFragment());	
		}
		else if(StaticVariables.fragmentIndexCurrentTabSettings==settingsFragmentFromSettings)//199

		{
			StaticVariables.fragmentIndexCurrentTabSettings=settingsFragmentFromSettings;//199
			if(StaticVariables.isSettingsFromAccessProfile)
			{
				StaticVariables.isSettingsFromAccessProfile=false;
				Intent intentAccess =new Intent(SettingsActivity.this, AccessProfileActivity.class);
				startActivity(intentAccess);
			}
			finish();

		}
		/*else if(StaticVariables.fragmentIndexCurrentTabSettings==holidayCalenderFragmentFromSettings)//201
				{
					StaticVariables.fragmentIndexCurrentTabSettings=settingsFragmentFromSettings;//199
					switchingFragments(new SettingsFragment());	
				}
				else if(StaticVariables.fragmentIndexCurrentTabSettings==holidayListFragmentFromHolidayCalender)//202
				{
					StaticVariables.fragmentIndexCurrentTabSettings=holidayCalenderFragmentFromSettings;//201
					switchingFragments(new HolidayCalenderFragment());	
				}

				else if(StaticVariables.fragmentIndexCurrentTabSettings==holidayDetailFragmentFromHolidayListFragmentr)//203
				{
					StaticVariables.fragmentIndexCurrentTabSettings=holidayListFragmentFromHolidayCalender;//202
					switchingFragments(new HolidayListFragment());	
				}*/
		else if(StaticVariables.fragmentIndexCurrentTabSettings==holidayListFragmentFromSettings)//201
		{
			StaticVariables.fragmentIndexCurrentTabSettings=settingsFragmentFromSettings;//199
			switchingFragments(new SettingsFragment());	
		}


		else if(StaticVariables.fragmentIndexCurrentTabSettings==holidayDetailFragmentFromHolidayListFragment)//202
		{
			StaticVariables.fragmentIndexCurrentTabSettings=holidayListFragmentFromSettings;//201
			switchingFragments(new HolidayListFragment());	
		}

	} 

}
