package com.hatchtact.pinwi;

import java.io.File;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.AuthenticateUser;
import com.hatchtact.pinwi.classmodel.AuthenticateUserResult;
import com.hatchtact.pinwi.classmodel.CheckPasswordCode;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.database.DataSource;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.AppUtils;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.CustomLoader;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;
import com.hatchtact.pinwi.utility.Validation;

public class LoginActivity extends Activity
{
	private TextView manageMap_textView=null;
	private EditText userName_editText=null;
	private EditText password_editText=null;
	private Button login_button=null;
	private Button sighUp_button=null;
	private TextView facebook_textView=null;
	private TextView googlePlus_textView=null;
	private TextView forgotPassword_textView=null;
	private TextView aboutUs_textView=null;
	private TextView help_textView=null;
	private CheckBox keepLogin_checkbox=null;

	private TypeFace typeFace=null;
	private Validation checkValidation=null;
	private ShowMessages showMessage=null;
	private AuthenticateUser authenticateUser=null;
	private CheckNetwork checkNetwork=null;
	private SharePreferenceClass sharePref=null;

	private String emailId="";
	private String password=null;

	private Gson gsonRegistration=null;
	private ServiceMethod serviceMethod=null;

	private CheckPasswordCode resetPasswordCode=null;
	private TextView text_keeplog;
	private TextView text_logIn;
	private SharePreferenceClass sharePreferenceClass=null;

	ParentProfile parentCompleteInformation;
	private CustomLoader customProgressLoader;


	private void getDisplayWidth(Activity a)
	{

		Display display = a.getWindowManager().getDefaultDisplay();

		// creating an empty Point so that the compiler
		// does not complain about null reference
		Point displaySize = new Point();
		display.getSize(displaySize);
		SplashActivity.ScreenWidth =  displaySize.x;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.login_activity);
		customProgressLoader=new CustomLoader(this);
		try
		{
			deleteData();//delete db data and images data from sd card
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sharePreferenceClass=new SharePreferenceClass(LoginActivity.this);
		sharePreferenceClass.setChildAdded(false);
		StaticVariables.isSignUpClicked=false;
		getDisplayWidth(LoginActivity.this);
		checkValidation = new Validation();
		showMessage = new ShowMessages(LoginActivity.this);
		typeFace=new TypeFace(LoginActivity.this);
		sharePref=new SharePreferenceClass(LoginActivity.this);
		gsonRegistration=new GsonBuilder().create();
		serviceMethod=new ServiceMethod();
		checkNetwork=new CheckNetwork();
		resetPasswordCode=new CheckPasswordCode();

		parentCompleteInformation = gsonRegistration.fromJson(sharePref.getParentProfile(), ParentProfile.class);

		if(parentCompleteInformation!=null)
			emailId=parentCompleteInformation.getEmailAddress();

		manageMap_textView=(TextView) findViewById(R.id.text_manageChild);
		userName_editText=(EditText) findViewById(R.id.text_username);
		password_editText=(EditText) findViewById(R.id.text_password);
		login_button=(Button) findViewById(R.id.button_login);
		sighUp_button=(Button) findViewById(R.id.button_signup);
		facebook_textView=(TextView) findViewById(R.id.text_facebook);
		googlePlus_textView=(TextView) findViewById(R.id.text_googlePlus);
		forgotPassword_textView=(TextView) findViewById(R.id.text_forgot_Password);
		aboutUs_textView=(TextView) findViewById(R.id.text_about);
		help_textView=(TextView) findViewById(R.id.text_help);
		keepLogin_checkbox=(CheckBox) findViewById(R.id.image_checkBox);
		text_keeplog =(TextView) findViewById(R.id.text_keeplog);
		text_logIn = (TextView) findViewById(R.id.text_logIn);

		typeFace.setTypefaceRegular(manageMap_textView);
		typeFace.setTypefaceRegular(userName_editText);
		typeFace.setTypefaceRegular(password_editText);
		typeFace.setTypefaceRegular(login_button);
		typeFace.setTypefaceRegular(sighUp_button);
		typeFace.setTypefaceRegular(facebook_textView);
		typeFace.setTypefaceRegular(googlePlus_textView);
		typeFace.setTypefaceRegular(forgotPassword_textView);
		typeFace.setTypefaceRegular(text_keeplog);
		typeFace.setTypefaceLight(aboutUs_textView);
		typeFace.setTypefaceLight(help_textView);
		typeFace.setTypefaceLight(text_logIn);


		aboutUs_textView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentAboutUs =new Intent(LoginActivity.this, ActivityAboutUS.class);
				startActivity(intentAboutUs);
				StaticVariables.webUrl=" http://pinwi.in/aboutus.html";
			}
		});

		help_textView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentAboutUs =new Intent(LoginActivity.this, ActivityAboutUS.class);
				startActivity(intentAboutUs);
				StaticVariables.webUrl="http://pinwi.in/contactus.aspx?4";
			}
		});

		forgotPassword_textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LoginActivity.this, ForgotPasswordActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
			}
		});


		login_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!checkValidation.isNotNullOrBlank(userName_editText.getText().toString()))
					showMessage.showAlert("Incomplete Data", "Please enter your Email Id");
				else if(!checkValidation.isValidEmail(userName_editText.getText().toString()))
					showMessage.showAlert("Invalid Email ID", "Your email ID may not be correct. Please check.");
				else if(!checkValidation.isNotNullOrBlank(password_editText.getText().toString()))
					showMessage.showAlert("Incomplete Data", "Please enter the password");
				/*else if(!keepLogin_checkbox.isChecked())
					showMessage.showAlert("Incomplete Data", "Please select keep me logged in");*/
				else /*if(keepLogin_checkbox.isChecked())*/
				{
					authenticateUser=new AuthenticateUser();

					authenticateUser.setEmailAddress(userName_editText.getText().toString());
					authenticateUser.setPassword(password_editText.getText().toString());

					new CheckWebservice().execute();
				}
			}
		});

		sighUp_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StaticVariables.isSignUpClicked=true;
				sharePreferenceClass.setIsLogout(false);
				sharePreferenceClass.setParentIsRegistered(false);
				LoginActivity.this.finish();
				Intent intent=new Intent(LoginActivity.this, ParentRegistrationActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 *
	 */
	private void deleteData() {
		File newfile=new File(Environment.getExternalStorageDirectory()+"/PinWi");
		new AppUtils(LoginActivity.this).deleteDirectory(newfile);
		DataSource datasource=new DataSource(LoginActivity.this);
		datasource.open();
		datasource.deleteAll();
		datasource.close();
		sharePref.setNetworkTableCreated(false);
	}

	//private ProgressDialog progressDialog=null;

	private class CheckWebservice extends AsyncTask<Void, Void, Integer>
	{
		AuthenticateUserResult userres;

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(LoginActivity.this))
			{
				//userres =serviceMethod.authenticateUser(authenticateUser);
				userres =serviceMethod.authenticateUserWithDeviceID(authenticateUser,sharePreferenceClass.getGCMDeviceId());

			}
			else
			{
				ErrorCode=-1;
			}

			return ErrorCode;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(customProgressLoader!=null)
			{
				customProgressLoader.showProgressBar();
			}
			/*progressDialog = ProgressDialog.show(LoginActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
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
				showMessage.showToastMessage("Please check your network connection");

			}
			else
			{
				if(userres!=null)
				{
					if(parentCompleteInformation == null)
					{
						parentCompleteInformation = new ParentProfile();
					}

					parentCompleteInformation.setParentID(userres.getProfileID());
					parentCompleteInformation.setFirstName(userres.getFirstName());
					parentCompleteInformation.setLastName(userres.getLastName());
					parentCompleteInformation.setEmailAddress(userName_editText.getText().toString());

					sharePref.setIsLogin(true);
					sharePref.setIsLogout(false);
					sharePref.setParentIsRegistered(true);


					String parentInformation = gsonRegistration.toJson(parentCompleteInformation);
					sharePref.setParentProfile(parentInformation);

					if(userres.getProfileStatus()==0)
					{
						LoginActivity.this.finish();
						sharePref.setCurrentScreen(1);
						Intent intent=new Intent(LoginActivity.this, ChildRegistrationActivity.class);
						startActivity(intent);
					}
					else if(userres.getProfileStatus()==1)
					{
						LoginActivity.this.finish();
						Intent intent=null;
						if(userres.getPaymentStatus()==1)
						{
							sharePref.setCurrentScreen(4);
							intent = new Intent(LoginActivity.this, AccessProfileActivity.class);
						}
						else
						{
							sharePref.setCurrentScreen(3);
							intent = new Intent(LoginActivity.this, GetStartedActivity.class);
						}

						startActivity(intent);
					}
					else if(userres.getProfileStatus()==2)
					{
						LoginActivity.this.finish();
						Intent intent=null;
						if(userres.getPaymentStatus()==1)
						{
							sharePref.setCurrentScreen(4);
							intent = new Intent(LoginActivity.this, AccessProfileActivity.class);
						}
						else
						{
							sharePref.setCurrentScreen(3);
							intent = new Intent(LoginActivity.this, GetStartedActivity.class);
						}
						startActivity(intent);
					}
				}
				else
				{
					Error err = serviceMethod.getError();
					showMessage.showAlert("Alert", err.getErrorDesc());
				}
			}
		}
	}
}
