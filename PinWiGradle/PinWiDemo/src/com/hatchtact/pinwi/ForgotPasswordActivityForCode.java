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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.CheckPasswordCode;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;
import com.hatchtact.pinwi.utility.Validation;

public class ForgotPasswordActivityForCode extends Activity 
{
	private TextView text_forgotcode=null;
	private TextView text_codeData=null;
	private EditText text_codeValue=null;
	private Button button_continueforgotCode=null;

	private TypeFace typeface=null;

	private Validation checkValidation=null;
	private ShowMessages showMessage=null;
	private SharePreferenceClass sharePref=null;
	private Gson gsonRegistration=null;
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;
	ParentProfile parentCompleteInformation;

	private String emailId="";
	//private String password="";

	private CheckPasswordCode checkCode=null;
	
	private TextView aboutUs_textView=null;
	private TextView help_textView=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_forgotpasswordcode);

		typeface=new TypeFace(ForgotPasswordActivityForCode.this);
		checkValidation = new Validation();
		showMessage = new ShowMessages(ForgotPasswordActivityForCode.this);
		sharePref=new SharePreferenceClass(ForgotPasswordActivityForCode.this);
		gsonRegistration=new GsonBuilder().create();
		serviceMethod=new ServiceMethod();
		checkNetwork=new CheckNetwork();
		checkCode=new CheckPasswordCode();
		
		parentCompleteInformation = gsonRegistration.fromJson(sharePref.getParentProfile(), ParentProfile.class);
		emailId=parentCompleteInformation.getEmailAddress();

		text_forgotcode=(TextView) findViewById(R.id.text_forgotcode);
		text_codeData=(TextView) findViewById(R.id.text_codeData);
		text_codeValue=(EditText) findViewById(R.id.text_codeValue);
		button_continueforgotCode=(Button) findViewById(R.id.button_continueforgotCode);
		
		aboutUs_textView=(TextView) findViewById(R.id.text_about);
		help_textView=(TextView) findViewById(R.id.text_help);
		
		typeface.setTypefaceLight(aboutUs_textView);
		typeface.setTypefaceLight(help_textView);

		typeface.setTypefaceRegular(text_forgotcode);
		typeface.setTypefaceRegular(text_codeData);
		typeface.setTypefaceRegular(text_codeValue);
		typeface.setTypefaceRegular(button_continueforgotCode);
		
		
		aboutUs_textView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentAboutUs =new Intent(ForgotPasswordActivityForCode.this, ActivityAboutUS.class);
				startActivity(intentAboutUs);
				StaticVariables.webUrl=" http://pinwi.in/aboutus.html";
			}
		});

		help_textView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentAboutUs =new Intent(ForgotPasswordActivityForCode.this, ActivityAboutUS.class);
				startActivity(intentAboutUs);
				StaticVariables.webUrl="http://pinwi.in/contactus.aspx?4";
			}
		});


		
		

		button_continueforgotCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!checkValidation.isNotNullOrBlank(text_codeValue.getText().toString()))
					showMessage.showAlert("Incomplete Data", "Please enter verification code sent to you.");	
				else 
				{
					checkCode.setCode(text_codeValue.getText().toString());
					checkCode.setEmailAddress(emailId);

					new ResetPasswordCheck().execute();
				}	
			}
		});
	}

	ProgressDialog progressDialog1=null;

	private class ResetPasswordCheck extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog1 = ProgressDialog.show(ForgotPasswordActivityForCode.this, "", StaticVariables.progressBarText, false);
			progressDialog1.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ForgotPasswordActivityForCode.this))
			{
				ErrorCode=serviceMethod.checkPasswordCode(checkCode);
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
				if(progressDialog1.isShowing())
					progressDialog1.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(ForgotPasswordActivityForCode.this))
					new ResetPasswordCheck().execute();
			}
			else
			{
				Intent intent=new Intent(ForgotPasswordActivityForCode.this, UpdatePasswordActivity.class);
				startActivity(intent);
				ForgotPasswordActivityForCode.this.finish();
			}
		}
	}
}
