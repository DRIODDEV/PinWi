package com.hatchtact.pinwi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.hatchtact.pinwi.gcm.GCMNotificationIntentService;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.CustomLoader;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.SocialConstants;
import com.hatchtact.pinwi.utility.StaticVariables;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends Activity
{
	private SharePreferenceClass sharePreferenceClass=null;
	private ShowMessages showMessage=null;
	private CheckNetwork checkNetwork=null;
	private ServiceMethod serviceMethod=null;

	private String deviceId;

	//private boolean deviceAlreadyRegistered=false;

	private String getResult="";
	public static int ScreenWidth;
	public static int ScreenHeight;

	private GoogleCloudMessaging gcm;
	private String registrationId;
	//private FirebaseAnalytics mFirebaseAnalytics;
	//CleverTapAPI cleverTap;
	private CustomLoader customProgressLoader;
	private SocialConstants socialConstants;
	private void getDisplayWidth(Activity a)
	{

		Display display = a.getWindowManager().getDefaultDisplay();

		// creating an empty Point so that the compiler
		// does not complain about null reference
		Point displaySize = new Point();
		display.getSize(displaySize);
		ScreenWidth =  displaySize.x;
		ScreenHeight =  displaySize.y;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.splash_activity);
		customProgressLoader=new CustomLoader(SplashActivity.this);
		sharePreferenceClass=new SharePreferenceClass(SplashActivity.this);
		socialConstants=new SocialConstants(SplashActivity.this);
		downloadAnalyticsLog();
		sharePreferenceClass.setAppDownloaded(true);

		StaticVariables.isSignUpClicked=false;
		serviceMethod=new ServiceMethod();
		checkNetwork=new CheckNetwork();
		showMessage = new ShowMessages(SplashActivity.this);
		getDisplayWidth(SplashActivity.this);

		try {
			TelephonyManager tManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

			deviceId = tManager.getDeviceId();
			sharePreferenceClass.setDeviceId(deviceId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sharePreferenceClass.setDeviceId("");
		}

		registerGCM();
	}

	//private ProgressDialog progressDialog=null;

	private class CheckDeviceIdExist extends AsyncTask<Void, Void, Integer>
	{
		private String deviceId;

		public CheckDeviceIdExist(String deviceId)
		{
			// TODO Auto-generated constructor stub 
			this.deviceId = deviceId;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(customProgressLoader!=null)
			{
				customProgressLoader.showProgressBar();
			}
/*
			progressDialog = ProgressDialog.show(SplashActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(SplashActivity.this))
			{
				serviceMethod.appLauncherByDeviceID(registrationId, 1);
				ErrorCode=serviceMethod.checkDeviceIDExist(deviceId);
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
				customProgressLoader.dismissProgressBar();
				/*if (progressDialog.isShowing())
					progressDialog.cancel();*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				if(!checkNetwork.checkNetworkConnection(SplashActivity.this))
				{
					showMessage.showToastMessage("Please check your network connection");
				}
				else
					new CheckDeviceIdExist(deviceId).execute();
			}
			else
			{
				getResult=serviceMethod.getErrorMessage();

				/*sharePreferenceClass.setParentIsRegistered(true);	
				sharePreferenceClass.setCurrentScreen(1);*/

				if(sharePreferenceClass.getParentIsRegistered() && !sharePreferenceClass.getIsLogout())
				{
					switch (sharePreferenceClass.getCurrentScreen())
					{
						case 1:
							finish();
							Intent intent1 = new Intent(SplashActivity.this, ChildRegistrationActivity.class);
							startActivity(intent1);
							break;

						case 2:
							finish();
						/*Intent intent2 = new Intent(SplashActivity.this, AllyRegistrationActivity.class);
						startActivity(intent2);*/
						/*Intent intent2 = new Intent(SplashActivity.this, GetStartedActivity.class);
						startActivity(intent2);*/
							Intent intent2 = new Intent(SplashActivity.this, AccessProfileActivity.class);
							startActivity(intent2);
							break;

						case 3:
							finish();
						/*Intent intent3 = new Intent(SplashActivity.this, GetStartedActivity.class);
						startActivity(intent3);*/
							Intent intent3 = new Intent(SplashActivity.this, GetStartedActivity.class);
							startActivity(intent3);
							break;

						case 4:
							finish();
							Intent intent = new Intent(SplashActivity.this, AccessProfileActivity.class);
							startActivity(intent);
							break;
					}
				}
				else if(!sharePreferenceClass.getParentIsRegistered() && getResult.contains("New Device ID."))
				{
					finish();
					Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
					startActivity(intent);
				}

				else if(getResult.contains("Device ID Already Exists.") ||
						(sharePreferenceClass.getParentIsRegistered() && !sharePreferenceClass.getIsLogin() && sharePreferenceClass.getIsLogout()))
				{
					finish();
					Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
					startActivity(intent);
				}
			}
		}
	}

	private void registerGCM(){
		gcm = GoogleCloudMessaging.getInstance(this);
		registrationId = sharePreferenceClass.getGCMDeviceId();
		new GetGcmDeviceID().execute();
	}

	private class GetGcmDeviceID extends AsyncTask<Void, Void, String>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params){
			String msg = "";
			try {
				if (gcm == null) {
					gcm = GoogleCloudMessaging.getInstance(SplashActivity.this);
				}
				registrationId = gcm.register(GCMNotificationIntentService.GOOGLE_PROJECT_ID);
				Log.d("RegisterActivity", "registerInBackground - regId: "
						+ registrationId);
				msg = "Device registered, registration ID=" + registrationId;
				sharePreferenceClass.setGCMDeviceId(registrationId);
			} catch (IOException ex) {
				msg = "Error :" + ex.getMessage();
				Log.d("RegisterActivity", "Error: " + msg);
			}
			Log.d("RegisterActivity", "AsyncTask completed: " + msg);
			return msg;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			//send registrationId to your Server
			new CheckDeviceIdExist(deviceId).execute();
		}
	}

	/*private void getError()
	{
		Error err = serviceMethod.getError();	
		showMessage.showAlert("Confirmation", err.getErrorDesc());
	}*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onStart() {
		super.onStart();

		EasyTracker.getInstance(this).activityStart(this);  // Add this method.
	}

	@Override
	public void onStop() {
		super.onStop();

		EasyTracker.getInstance(this).activityStop(this);  // Add this method.
	}
	/**
	 *
	 */
	private void downloadAnalyticsLog() {

		if(!sharePreferenceClass.isAppDownloaded())
		{
			socialConstants.download_StatusAnalyticsLog();
		}
	}



	private  void showHashKey(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo("com.hatchtact.pinwi",
					PackageManager.GET_SIGNATURES);
			for (android.content.pm.Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());

				String sign= Base64.encodeToString(md.digest(), Base64.DEFAULT);
				Log.e("KeyHash:", sign);
				Toast.makeText(getApplicationContext(),sign,     Toast.LENGTH_LONG).show();
			}
			Log.d("KeyHash:", "****------------***");
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
