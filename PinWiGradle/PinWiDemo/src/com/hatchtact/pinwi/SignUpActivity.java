package com.hatchtact.pinwi;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.FacebookParentProfileModel;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

public class SignUpActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener
{
	private TextView manageMap_textView=null;	
	private Button sighUp_button=null;
	private TextView signUp_textView=null;
	private TextView facebook_textView=null;
	private TextView googlePlus_textView=null;
	private TextView aboutUs_textView=null;
	private TextView help_textView=null;

	private LinearLayout layout_facebook;
	private LinearLayout layout_googleplus;

	private TypeFace typeFace=null;

	/*Facebook and Google Plus Integration*/
	//private CallbackManager callbackManager;
	private static final List<String> PERMISSIONS = Arrays.asList("public_profile","email","user_birthday");
	//private AccessTokenTracker accessTokenTracker;

	private static final int RC_SIGN_IN = 0;


	// Profile pic image size in pixels
	private static final int PROFILE_PIC_SIZE = 400;

	// Google client to interact with Google API
	//private GoogleApiClient mGoogleApiClient;

	//365125606347-uf6is4vj7poged6fgmcs0rh7locr06fh.apps.googleusercontent.com

	/**
	 * A flag indicating that a PendingIntent is in progress and prevents us
	 * from starting further intents.
	 */
	private boolean mIntentInProgress;

	private boolean mSignInClicked;

	private ConnectionResult mConnectionResult;
	/*Facebook and Google Plus Integration*/
	private ProgressDialog progressDialog=null;	

	@Override
	protected void onActivityResult(int reqCode, int resCode, Intent data) {

		/*if(callbackManager!=null)
		{
			callbackManager.onActivityResult(reqCode, resCode, data);

			*//*Intent intent=new Intent(SignUpActivity.this, ParentRegistrationActivity.class);
				startActivity(intent);
				SignUpActivity.this.finish();*//*

		}
*/

		if (reqCode == RC_SIGN_IN) {
			//mGoogleApiClient.connect();
            //hitCount=0;
			progress = ProgressDialog.show(SignUpActivity.this, "", StaticVariables.progressBarText, false);
			//hitCount++;
			progress.setCancelable(false);
			//new getGoogleProfileInformation(resCode).execute();


			/*	progressDialog = ProgressDialog.show(SignUpActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
			/*try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

			/*try {
				if (progressDialog.isShowing())
					progressDialog.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

			// getProfileInformation();
			/*Intent intent=new Intent(SignUpActivity.this, ParentRegistrationActivity.class);
				startActivity(intent);
				SignUpActivity.this.finish();

			if (resCode != RESULT_OK) {
				mSignInClicked = false;
			}

			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
			 */		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub   
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);


		setContentView(R.layout.signup_activity);
		//hitCount=0;

		/*Google Plus Init*/
		/*mGoogleApiClient = new GoogleApiClient.Builder(SignUpActivity.this)
		.addConnectionCallbacks(SignUpActivity.this)
		.addOnConnectionFailedListener(SignUpActivity.this).addApi(Plus.API)
		.addScope(Plus.SCOPE_PLUS_LOGIN).build();*/
		/*Google Plus Init*/

		typeFace=new TypeFace(SignUpActivity.this);

		manageMap_textView=(TextView) findViewById(R.id.text_manageChild);
		facebook_textView=(TextView) findViewById(R.id.text_facebook);
		googlePlus_textView=(TextView) findViewById(R.id.text_googlePlus);
		aboutUs_textView=(TextView) findViewById(R.id.text_about);
		help_textView=(TextView) findViewById(R.id.text_help);
		signUp_textView=(TextView) findViewById(R.id.text_signup);
		sighUp_button=(Button) findViewById(R.id.button_signup);

		typeFace.setTypefaceRegular(manageMap_textView);
		typeFace.setTypefaceRegular(facebook_textView);
		typeFace.setTypefaceRegular(googlePlus_textView);
		typeFace.setTypefaceLight(aboutUs_textView);
		typeFace.setTypefaceLight(help_textView);
		typeFace.setTypefaceRegular(signUp_textView);
		typeFace.setTypefaceRegular(sighUp_button);



		layout_facebook=(LinearLayout) findViewById(R.id.layout_facebook);
		layout_googleplus=(LinearLayout) findViewById(R.id.layout_googleplus);

		aboutUs_textView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentAboutUs =new Intent(SignUpActivity.this, ActivityAboutUS.class);
				startActivity(intentAboutUs);
				StaticVariables.webUrl="http://pinwi.in/aboutus.html";
			}
		});

		help_textView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentAboutUs =new Intent(SignUpActivity.this, ActivityAboutUS.class);
				startActivity(intentAboutUs);
				StaticVariables.webUrl="http://pinwi.in/contactus.aspx?4";
			}
		});


		layout_facebook.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				//showHashKey(SignUpActivity.this);
				boolean isFacebookDataFilled=false;
				if(StaticVariables.modelFacebook==null)
				{
					StaticVariables.modelFacebook=new FacebookParentProfileModel();
				}
				if(StaticVariables.modelFacebook.getSocialLoginName()==null)
				{
					isFacebookDataFilled=false;
				}
				else if(StaticVariables.modelFacebook.getSocialLoginName()!=null)
				{
					if(StaticVariables.modelFacebook.getSocialLoginName().equalsIgnoreCase("GooglePlus"))
						isFacebookDataFilled=false;

				}
				else
				{
					isFacebookDataFilled=true;

				}

				if(!isFacebookDataFilled)
				{
					/*callbackManager = CallbackManager.Factory.create();
					FacebookSdk.sdkInitialize(getApplicationContext());

					LoginManager.getInstance().logInWithReadPermissions(SignUpActivity.this, PERMISSIONS);
					LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
						@Override
						public void onSuccess(LoginResult loginResult) {
							*//*Toast.makeText(SignUpActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
						System.out.println("permissionens" +loginResult.getAccessToken().getDeclinedPermissions()  +AccessToken.getCurrentAccessToken());
						System.out.println("Token" +loginResult.getAccessToken().getCurrentAccessToken());*//*




							GraphRequest request = GraphRequest.newMeRequest(
									loginResult.getAccessToken(),
									new GraphRequest.GraphJSONObjectCallback() {

										@Override
										public void onCompleted(JSONObject object,
												GraphResponse response) {
											// TODO Auto-generated method stub
											try {
												//JSONArray arrayJson = new JSONArray();
												JSONObject  onj = new JSONObject(response.getJSONObject().toString());

												try {
													StaticVariables.modelFacebook.setId(onj.getInt("id"));
												} catch (Exception e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
												try {
													//StaticVariables.modelFacebook.setBirthday(onj.getString("birthday"));
												} catch (Exception e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												StaticVariables.modelFacebook.setEmail(onj.getString("email"));
												try {
													StaticVariables.modelFacebook.setFirst_name(onj.getString("first_name"));
												} catch (Exception e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												try {
													StaticVariables.modelFacebook.setGender(onj.getString("gender"));
												} catch (Exception e1) {
													// TODO Auto-generated catch block
													e1.printStackTrace();
												}
												try {
													StaticVariables.modelFacebook.setLast_name(onj.getString("last_name"));
												} catch (Exception e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}

												try {
													StaticVariables.modelFacebook.setUrl((onj.getJSONObject("picture").getJSONObject("data").getString("url")));
												} catch (Exception e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												StaticVariables.modelFacebook.setSocialLoginName("Facebook");


												Intent intent=new Intent(SignUpActivity.this, ParentRegistrationActivity.class);
												startActivity(intent);
												SignUpActivity.this.finish();

												LoginManager.getInstance().logOut();


											} catch (JSONException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											catch (Exception e) {
												// TODO: handle exception
												e.printStackTrace();
											}

										}
									});
							Bundle parameters = new Bundle();
							parameters.putString("fields", "id,email,gender,birthday,first_name,last_name,picture");
							request.setParameters(parameters);
							request.executeAsync();


						}

						@Override
						public void onCancel() {
							Toast.makeText(SignUpActivity.this, "Login cancelled by user!", Toast.LENGTH_LONG).show();
							System.out.println("Facebook Login failed!!");

						}

						@Override
						public void onError(FacebookException e) {
							Toast.makeText(SignUpActivity.this, "Login unsuccessful!", Toast.LENGTH_LONG).show();
							System.out.println("Facebook Login failed!!");
						}
					});
*/
				}

				else
				{
					Intent intent=new Intent(SignUpActivity.this, ParentRegistrationActivity.class);
					startActivity(intent);
					SignUpActivity.this.finish();
				}
			}
		});



		layout_googleplus.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				boolean isGooglePlusDataFilled=false;


				if(StaticVariables.modelFacebook==null)
				{
					StaticVariables.modelFacebook=new FacebookParentProfileModel();
				}

				if(StaticVariables.modelFacebook.getSocialLoginName()==null)
				{
					isGooglePlusDataFilled=false;
				}
				else if(StaticVariables.modelFacebook.getSocialLoginName()!=null)
				{
					if(StaticVariables.modelFacebook.getSocialLoginName().equalsIgnoreCase("Facebook"))
						isGooglePlusDataFilled=false;
					else
						isGooglePlusDataFilled=true;


				}
				else
				{
					isGooglePlusDataFilled=true;

				}

				if(!isGooglePlusDataFilled)
				{
					signInWithGplus();
				}
				else
				{
					Intent intent=new Intent(SignUpActivity.this, ParentRegistrationActivity.class);
					startActivity(intent);
					SignUpActivity.this.finish();
				}


			}
		});

		sighUp_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(SignUpActivity.this, ParentRegistrationActivity.class);
				startActivity(intent);
				SignUpActivity.this.finish();
				StaticVariables.modelFacebook=null;
			}
		});
	} 

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}


	private  void showHashKey(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo("com.demo.pinwi",
					PackageManager.GET_SIGNATURES);
			for (android.content.pm.Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());

				String sign=Base64.encodeToString(md.digest(), Base64.DEFAULT);
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


	protected void onStart() {
		super.onStart();
		try {
			//mGoogleApiClient.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void onStop() {
		super.onStop();
		try {
//			if (mGoogleApiClient.isConnected()) {
//				mGoogleApiClient.disconnect();
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




	/**
	 * Method to resolve any signin errors
	 * */
	private void resolveSignInError() {
		try {
			if (mConnectionResult.hasResolution()) {
				try {
					mIntentInProgress = true;
					mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
				} catch (SendIntentException e) {
					mIntentInProgress = false;
					//mGoogleApiClient.connect();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
					0).show();
			return;
		}

		if (!mIntentInProgress) {
			// Store the ConnectionResult for later usage
			mConnectionResult = result;

			if (mSignInClicked) {
				// The user has already clicked 'sign-in' so we attempt to
				// resolve all
				// errors until the user is signed in, or they cancel.
				resolveSignInError();
			}
		}

	}

	@Override
	public void onConnected(Bundle arg0) {
		mSignInClicked = false;
		//Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

		// Get user's information
		//getProfileInformation();

		// Update the UI after signin
		//updateUI(true);

	}

	/**
	 * Updating the UI, showing/hiding buttons and profile layout
	 * */
	private void updateUI(boolean isSignedIn) {

	}

	/**
	 * Fetching user's information name, email, profile pic
	 * */
	/*private int getProfileInformation() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				Person currentPerson = Plus.PeopleApi
						.getCurrentPerson(mGoogleApiClient);
				if(StaticVariables.modelFacebook==null)
				{
					StaticVariables.modelFacebook=new FacebookParentProfileModel();
				}

				String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
				StaticVariables.modelFacebook.setEmail(email);


				String personName = currentPerson.getDisplayName();
				String[] name=personName.split(" ");
				StaticVariables.modelFacebook.setFirst_name(name[0]);
				StaticVariables.modelFacebook.setLast_name(name[1]);


				String personPhotoUrl = currentPerson.getImage().getUrl();
				StaticVariables.modelFacebook.setUrl(personPhotoUrl);

				String personBirthday=currentPerson.getBirthday();
				//StaticVariables.modelFacebook.setBirthday(personBirthday);

				int currentgender=currentPerson.getGender();
				if(currentgender==0)
				{
					StaticVariables.modelFacebook.setGender("Male");
				}
				else
				{
					StaticVariables.modelFacebook.setGender("Female");
				}


				StaticVariables.modelFacebook.setSocialLoginName("GooglePlus");



				// by default the profile url gives 50x50 px image only
				// we can replace the value with whatever dimension we want by
				// replacing sz=X
				personPhotoUrl = personPhotoUrl.substring(0,
						personPhotoUrl.length() - 2)
						+ PROFILE_PIC_SIZE;

				//new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);
				return 1;

			} else {
				Toast.makeText(getApplicationContext(),
						"Person information is null", Toast.LENGTH_LONG).show();
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}*/

	@Override
	public void onConnectionSuspended(int arg0) {
		//mGoogleApiClient.connect();
		//updateUI(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	/**
	 * Sign-in into google
	 * */
	private void signInWithGplus() {
		/*if (!mGoogleApiClient.isConnecting()) {
			mSignInClicked = true;
			resolveSignInError();
		}*/
	}

	/**
	 * Sign-out from google
	 * */
	/*private void signOutFromGplus() {
		if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			mGoogleApiClient.disconnect();
			mGoogleApiClient.connect();
			//updateUI(false);
		}
	}
*/
	/**
	 * Revoking access from google
	 * */
	/*private void revokeGplusAccess() {
		if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
			.setResultCallback(new ResultCallback<Status>() {
				@Override
				public void onResult(Status arg0) {
					Log.e("SignUpActivity", "User access revoked!");
					mGoogleApiClient.connect();
					//updateUI(false);
				}

			});
		}
	}*/





	private ProgressDialog progress=null;
	//private int hitCount=0;
	/**
	 * Background Async task to load user profile data from google plus
	 * */
/*	private class getGoogleProfileInformation extends AsyncTask<String, Void, Integer> {


		private int resultcode;
		public getGoogleProfileInformation(int resCode) {
			resultcode=resCode; 
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			*//*progress = ProgressDialog.show(SignUpActivity.this, "", StaticVariables.progressBarText, false);
			//hitCount++;
			progress.setCancelable(false);*//*
		}

		protected Integer doInBackground(String... urls) {

			int result=getProfileInformation();
			return result;
		}

		protected void onPostExecute(Integer result) {


			*//*try {
				if (progress.isShowing())
					progress.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*//*
			if(result==0)
			{
				//if(hitCount<=3)
				new getGoogleProfileInformation(resultcode).execute();
				
			}
			else
			{
				try {
					if (progress.isShowing())
						progress.cancel();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Intent intent=new Intent(SignUpActivity.this, ParentRegistrationActivity.class);
				startActivity(intent);
				SignUpActivity.this.finish();

				if (resultcode != RESULT_OK) {
					mSignInClicked = false;
				}

				mIntentInProgress = false;

				if (!mGoogleApiClient.isConnecting()) {
					mGoogleApiClient.connect();
				}

			}
		}



	}
	*/
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}



}
