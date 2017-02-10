package com.hatchtact.pinwi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.classmodel.ResetPassword;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;
import com.hatchtact.pinwi.utility.Validation;

public class UpdatePasswordActivity extends Activity
{
	private TextView text_forgotupdate=null;
	private EditText text_newPassword=null;
	private EditText text_confirmNewPassword=null;
	private Button button_continueforgotEmail=null;

	private TypeFace typeface=null;

	private ResetPassword resetPassword=null;

	private Validation checkValidation=null;
	private ShowMessages showMessage=null;
	private SharePreferenceClass sharePref=null;
	private Gson gsonRegistration=null;
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;
	ParentProfile parentCompleteInformation;

	private String emailId="";
	private String password="";
	
	private TextView aboutUs_textView=null;
	private TextView help_textView=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_forgotpasswordsetpassword);	

		checkValidation = new Validation();
		showMessage = new ShowMessages(UpdatePasswordActivity.this);
		sharePref=new SharePreferenceClass(UpdatePasswordActivity.this);
		gsonRegistration=new GsonBuilder().create();
		serviceMethod=new ServiceMethod();
		checkNetwork=new CheckNetwork();
		typeface=new TypeFace(UpdatePasswordActivity.this);

		parentCompleteInformation = gsonRegistration.fromJson(sharePref.getParentProfile(), ParentProfile.class);
		emailId=parentCompleteInformation.getEmailAddress();
		resetPassword=new ResetPassword();
		
		text_forgotupdate=(TextView) findViewById(R.id.text_forgotupdate);
		text_newPassword=(EditText) findViewById(R.id.text_newPassword);
		text_confirmNewPassword=(EditText) findViewById(R.id.text_confirmNewPassword);
		button_continueforgotEmail=(Button) findViewById(R.id.button_continueforgotEmail);
		
		aboutUs_textView=(TextView) findViewById(R.id.text_about);
		help_textView=(TextView) findViewById(R.id.text_help);
		
		typeface.setTypefaceLight(aboutUs_textView);
		typeface.setTypefaceLight(help_textView);

		typeface.setTypefaceLight(text_forgotupdate);
		typeface.setTypefaceLight(text_newPassword);
		typeface.setTypefaceLight(text_confirmNewPassword);
		typeface.setTypefaceLight(button_continueforgotEmail);
		
		aboutUs_textView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentAboutUs =new Intent(UpdatePasswordActivity.this, ActivityAboutUS.class);
				startActivity(intentAboutUs);
				StaticVariables.webUrl=" http://pinwi.in/aboutus.html";
			}
		});

		help_textView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentAboutUs =new Intent(UpdatePasswordActivity.this, ActivityAboutUS.class);
				startActivity(intentAboutUs);
				StaticVariables.webUrl="http://pinwi.in/contactus.aspx?4";
			}
		});

		

		button_continueforgotEmail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!checkValidation.isNotNullOrBlank(text_newPassword.getText().toString()))
					showMessage.showAlert("Incomplete Data", "Please enter new password");	
				else if(!checkValidation.isNotNullOrBlank(text_confirmNewPassword.getText().toString()))
					showMessage.showAlert("Incomplete Data", "Please enter and confirm the password.");	
				else if(!text_newPassword.getText().toString().equalsIgnoreCase(text_confirmNewPassword.getText().toString()))
					showMessage.showAlert("Invalid Data", "The two passwords entered are not same.Please check.");	
				else 
				{
					password=text_newPassword.getText().toString();
					
					resetPassword.setNewPassword(text_newPassword.getText().toString());
					resetPassword.setConfirmPassword(text_confirmNewPassword.getText().toString());
					resetPassword.setEmailAddress(emailId);

					new UpdatePassword().execute();
				}	
			}
		});
	}

	private ProgressDialog progressDialog;
	private class UpdatePassword extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = ProgressDialog.show(UpdatePasswordActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(UpdatePasswordActivity.this))
			{
				ErrorCode=serviceMethod.ResetPasswordCode(resetPassword);
			}
			else
			{
				ErrorCode=-1;
			}
			return ErrorCode;
		}

		@Override
		protected void onPostExecute(Integer result) 
		{
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

				if(checkNetwork.checkNetworkConnection(UpdatePasswordActivity.this))
					new UpdatePassword().execute();
			}
			else
			{
				getError();
				parentCompleteInformation = gsonRegistration.fromJson(sharePref.getParentProfile(), ParentProfile.class);

				if(parentCompleteInformation == null)
				{
					parentCompleteInformation = new ParentProfile();
				}

				parentCompleteInformation.setPassword(password);

				String parentInformation = gsonRegistration.toJson(parentCompleteInformation);
				sharePref.setParentProfile(parentInformation);

				//serviceMethod.getError();

				Intent intent=new Intent(UpdatePasswordActivity.this, LoginActivity.class);
				startActivity(intent);
				UpdatePasswordActivity.this.finish();
			}
		}
	}
	
	private void getError()
	{
		Error err = serviceMethod.getError();	
		Toast.makeText(UpdatePasswordActivity.this, err.getErrorDesc(), Toast.LENGTH_LONG).show();
		//showAlert("Warning", err);
	}

}


