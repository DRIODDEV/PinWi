package com.hatchtact.pinwi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;
import com.hatchtact.pinwi.utility.Validation;

public class ForgotPasswordActivity extends Activity
{
	private ShowMessages showMessage=null;
	private CheckNetwork checkNetwork=null;
	private SharePreferenceClass sharePref=null;
	private Gson gsonRegistration=null;
	private ServiceMethod serviceMethod=null;
	ParentProfile parentCompleteInformation;
	private TypeFace typeFace=null;
	private Validation checkValidation=null;
	private String emailId="";

	private TextView text_forgot=null;
	private TextView text_enterEmail=null;
	private EditText text_emailforgot=null;
	private Button button_continueforgotEmail=null;
	
	private TextView aboutUs_textView=null;
	private TextView help_textView=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_forgotpasswordemail);

		text_forgot=(TextView) findViewById(R.id.text_forgot);
		text_enterEmail=(TextView) findViewById(R.id.text_enterEmail);
		text_emailforgot=(EditText) findViewById(R.id.text_emailforgot);
		button_continueforgotEmail=(Button) findViewById(R.id.button_continueforgotEmail);

		checkValidation = new Validation();
		showMessage = new ShowMessages(ForgotPasswordActivity.this);
		typeFace=new TypeFace(ForgotPasswordActivity.this);
		sharePref=new SharePreferenceClass(ForgotPasswordActivity.this);
		gsonRegistration=new GsonBuilder().create();
		serviceMethod=new ServiceMethod();
		checkNetwork=new CheckNetwork();

		/*parentCompleteInformation = gsonRegistration.fromJson(sharePref.getParentProfile(), ParentProfile.class);

		if(parentCompleteInformation!=null)
		{
			emailId=parentCompleteInformation.getEmailAddress();
			text_emailforgot.setText(emailId);
			text_emailforgot.setSelection(emailId.length());
		}*/

		
		aboutUs_textView=(TextView) findViewById(R.id.text_about);
		help_textView=(TextView) findViewById(R.id.text_help);
		
		typeFace.setTypefaceLight(aboutUs_textView);
		typeFace.setTypefaceLight(help_textView);

		typeFace.setTypefaceLight(text_forgot);
		typeFace.setTypefaceLight(text_enterEmail);
		typeFace.setTypefaceLight(text_emailforgot);
		typeFace.setTypefaceRegular(button_continueforgotEmail);
		
		
		aboutUs_textView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentAboutUs =new Intent(ForgotPasswordActivity.this, ActivityAboutUS.class);
				startActivity(intentAboutUs);
				StaticVariables.webUrl=" http://pinwi.in/aboutus.html";
			}
		});

		help_textView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentAboutUs =new Intent(ForgotPasswordActivity.this, ActivityAboutUS.class);
				startActivity(intentAboutUs);
				StaticVariables.webUrl="http://pinwi.in/contactus.aspx?4";
			}
		});


		button_continueforgotEmail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if(!checkValidation.isNotNullOrBlank(text_emailforgot.getText().toString()))
				{
					showMessage.showAlert("Incomplete Data", "Please enter your Email Id");
				}
				else if(!checkValidation.isValidEmail(text_emailforgot.getText().toString()))
				{
					showMessage.showAlert("Invalid Email ID", "Your email ID may not be correct. Please check.");
				}
				else
				{
					emailId = text_emailforgot.getText().toString();
					new SendPasswordToMail(emailId).execute();
				}
			}
		});
	}

	ProgressDialog progressDialog;

	private class SendPasswordToMail extends AsyncTask<Void, Void, Integer>
	{
		private String emailId;

		public SendPasswordToMail(String emailId)
		{
			// TODO Auto-generated constructor stub 
			this.emailId = emailId;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = ProgressDialog.show(ForgotPasswordActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ForgotPasswordActivity.this))
			{
				ErrorCode=serviceMethod.forgetPassword(emailId);
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
				new SendPasswordToMail(emailId).execute();
			}
			else
			{
				getError();
			}	
		}
	}

	private void getError()
	{
		Error err = serviceMethod.getError();	
		showAlert("Warning", err);
	}

	public void showAlert(String title, final Error err)
	{
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ForgotPasswordActivity.this);

		alertBuilder.setTitle(title);
		alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
		alertBuilder.setMessage(err.getErrorDesc());
		alertBuilder.setPositiveButton(" OK ", new DialogInterface.OnClickListener() 
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				parentCompleteInformation = gsonRegistration.fromJson(sharePref.getParentProfile(), ParentProfile.class);

				if(parentCompleteInformation == null)
				{
					parentCompleteInformation = new ParentProfile();
				}

				parentCompleteInformation.setEmailAddress(emailId);

				String parentInformation = gsonRegistration.toJson(parentCompleteInformation);
				sharePref.setParentProfile(parentInformation);

				if(err.getErrorCode().equalsIgnoreCase("0"))
				{
					Intent intent=new Intent(ForgotPasswordActivity.this, ForgotPasswordActivityForCode.class);
					startActivity(intent);
					finish();
				}
			}
		});	
		alertBuilder.show();
	}

}
