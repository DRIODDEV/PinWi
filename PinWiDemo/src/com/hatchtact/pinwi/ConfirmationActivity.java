package com.hatchtact.pinwi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hatchtact.pinwi.classmodel.CheckConfirmationCode;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.classmodel.PassCode;
import com.hatchtact.pinwi.classmodel.PassCodeList;
import com.hatchtact.pinwi.classmodel.SendConfirmationCodeToMail;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;
import com.hatchtact.pinwi.utility.Validation;

public class ConfirmationActivity extends MainActionBarActivity implements OnClickListener
{
	private EditText code_editText=null;
	private CheckBox check_imageView=null;
	private Button submit_button=null;
	private Button send_button=null;

	private TextView text_confirmation;
	private TextView text_receiveConfirmation;
	private TextView text_condition;

	private RadioButton sms_text=null;
	private RadioButton email_text=null;

	private TypeFace typeFace=null;

	private SendConfirmationCodeToMail sendConfirmationCodeToMail=null;
	private ServiceMethod serviceMethod=null;

	private Validation checkValidation=null;
	private ShowMessages showMessage=null;

	private CheckConfirmationCode checkConfirmationCode=null;
	private SharePreferenceClass sharePref=null;
	private CheckNetwork checkNetwork=null;

	int parentID;
	String EmailAddress;

	private Gson gsonRegistration=null; 
	private boolean  onTouchSendButton=false;

	private RadioGroup group_sms_email=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		screenName="Confirm Profile";

		super.onCreate(savedInstanceState);

		setContentView(R.layout.confirm_profile_activity);

		onTouchSendButton=false;

		// Get bundle and pass it to webservice
		parentID=getIntent().getIntExtra("ParentID", -1);
		EmailAddress=getIntent().getStringExtra("EmailAddress");

		typeFace= new TypeFace(ConfirmationActivity.this);
		sendConfirmationCodeToMail=new SendConfirmationCodeToMail();
		serviceMethod = new ServiceMethod();
		showMessage=new ShowMessages(ConfirmationActivity.this);
		sharePref = new SharePreferenceClass(ConfirmationActivity.this);
		checkNetwork=new CheckNetwork();
		checkValidation=new Validation();
		gsonRegistration = new GsonBuilder().create();

		code_editText=(EditText) findViewById(R.id.text_code);
		check_imageView=(CheckBox) findViewById(R.id.image_checkBox);
		submit_button=(Button) findViewById(R.id.button_submitConfirm);
		send_button=(Button) findViewById(R.id.button_send);

		sms_text=(RadioButton) findViewById(R.id.text_SMS);
		email_text=(RadioButton) findViewById(R.id.text_email);
		//email_text.setSelected(true);

		group_sms_email=(RadioGroup) findViewById(R.id.layout_sms_email);
		//group_sms_email.getChildAt(1).setSelected(true);
		group_sms_email.check(R.id.text_email);

		sendConfirmationCodeToMail.setEmailAddress(EmailAddress);
		sendConfirmationCodeToMail.setParentID(parentID);

		text_confirmation = (TextView) findViewById(R.id.text_confirmation);
		text_receiveConfirmation = (TextView) findViewById(R.id.text_receiveConfirmation);
		text_condition  = (TextView) findViewById(R.id.text_condition);

		typeFace.setTypefaceLight(code_editText);
		typeFace.setTypefaceLight(text_confirmation);
		typeFace.setTypefaceRegular(text_receiveConfirmation);
		typeFace.setTypefaceRegular(text_condition);

		typeFace.setTypefaceRegular(check_imageView);

		typeFace.setTypefaceRegular(send_button);
		typeFace.setTypefaceRegular(submit_button);

		typeFace.setTypefaceRegular(sms_text);
		typeFace.setTypefaceRegular(email_text);


		send_button.setOnClickListener(this);
		submit_button.setOnClickListener(this);

		text_condition.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentAboutUs =new Intent(ConfirmationActivity.this, ActivityAboutUS.class);
				startActivity(intentAboutUs);
				StaticVariables.webUrl="http://pinwi.in/terms.html";

			}
		});

		group_sms_email.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId==R.id.text_SMS)
				{
					sms_text.setSelected(true);
					send_button.setText("Send");	
				}
				if(checkedId==R.id.text_email)
				{
					email_text.setSelected(true);
					send_button.setText("Send");	
				}
			}
		});

	}

	private class ConfirmationTask extends AsyncTask<Void, Void, Integer>
	{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			int confirmationId=0;

			if(checkNetwork.checkNetworkConnection(ConfirmationActivity.this))
			{
				if(email_text.isChecked())
				{
					/*sms_text.setEnabled(false);
					sms_text.setClickable(false);*/
					confirmationId=serviceMethod.sendCodeToMail(sendConfirmationCodeToMail,0);
				}
				else
				{
					/*email_text.setEnabled(false);
					email_text.setClickable(false);*/
					confirmationId=serviceMethod.sendCodeToMail(sendConfirmationCodeToMail,1);
				}
			}
			else
			{
				confirmationId=-1;
			}

			return confirmationId;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			onTouchSendButton=false;

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(ConfirmationActivity.this))
					new ConfirmationTask().execute();
			}
			else
			{
				if(email_text.isChecked())
				{
					showMessage.showAlert("Confirmation", "We just sent a verification code to your registered email ID.");
				}
				else
				{
					showMessage.showAlert("Confirmation", "We just sent a verification code to your registered conatct no.");

				}
				if(result!=0)
				{
					//getError();
				}
				/*else
				{
					getError();
				}*/
			}	
		}
	}

	private ProgressDialog progressDialog=null;	

	private class checkConfirmationTask extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = ProgressDialog.show(ConfirmationActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			int parentId=0;

			if(checkNetwork.checkNetworkConnection(ConfirmationActivity.this))
			{
				parentId=serviceMethod.checkConfirmationCodeById(checkConfirmationCode);
			}
			else
			{
				parentId=-1;
			}
			return parentId;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			onTouchSendButton=false;

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

				if(checkNetwork.checkNetworkConnection(ConfirmationActivity.this))
					new checkConfirmationTask().execute();
			}
			else
			{
				if(result!=0)
				{
					ParentProfile parentCompleteInformation = gsonRegistration.fromJson(sharePref.getParentProfile(), ParentProfile.class);
					parentCompleteInformation.setParentID(parentID);
					parentCompleteInformation.setEmailAddress(EmailAddress);

					String parentInformation = gsonRegistration.toJson(parentCompleteInformation);
					sharePref.setParentProfile(parentInformation);

					sharePref.setParentIsRegistered(true);	

					System.out.println("value of is registered in confirm"+sharePref.getParentIsRegistered());

					PassCode pcParent = new  PassCode();

					pcParent.setPassCodeType(0);
					pcParent.setProfileId(parentID);
					pcParent.setPassCode(parentCompleteInformation.getPasscode());
					StaticVariables.parentPasscodeModel.setPassCode(parentCompleteInformation.getPasscode());
					StaticVariables.parentPasscodeModel.setPassCodeType(1);						
					StaticVariables.parentPasscodeModel.setProfileId(parentID);


					PassCodeList pcList = new PassCodeList();
					pcList.getPasscodeList().add(pcParent);

					String passcodeListString = gsonRegistration.toJson(pcList);				
					sharePref.setPassCodeList(passcodeListString);

					generateCongrateDialog();
				}
				else
				{
					//getError();
					showMessage.showAlert("Confirmation", "Something's not right. Please confirm the code again.");
				}
			}
		}
	}

	private void getError()
	{
		Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId())
		{
		case R.id.button_send:
			onTouchSendButton=true;

			if(onTouchSendButton /*&& email_text.isChecked()*/)
			{
				if(send_button.getText().toString().equalsIgnoreCase("Resend"))
					showAlertResend("Confirmation", "Are you sure you want the verification code sent again?");
				else
				{
					new ConfirmationTask().execute();
					//showMessage.showAlert("Confirmation", "We just sent a verification code to your registered email ID.");
				}
				send_button.setText("Resend");	
			}
			else
			{
				System.out.println("please select Email");
			}
			break;

		case R.id.button_submitConfirm:

			if(!checkValidation.isNotNullOrBlank(code_editText.getText().toString()) && !check_imageView.isChecked())
				showMessage.showAlert("Confirmation", "Please enter verification code sent to you and accept Terms & Conditions");	
			else if(!checkValidation.isNotNullOrBlank(code_editText.getText().toString()))
				showMessage.showAlert("Confirmation", "Please enter verification code sent to you.");	
			else if(!check_imageView.isChecked())
				showMessage.showAlert("Confirmation", "Please accept Terms & Conditions.");
			else if(check_imageView.isChecked() && code_editText.getText().length()>0)
			{
				checkConfirmationCode= new CheckConfirmationCode();   

				onTouchSendButton=true;
				checkConfirmationCode.setConfirmationCode(code_editText.getText().toString());
				checkConfirmationCode.setEmailAddress(EmailAddress);
				checkConfirmationCode.setParentID(parentID);

				new checkConfirmationTask().execute();
			}
			break;
		}
	}

	AlertDialog.Builder  congratulationDialog=null;

	public void generateCongrateDialog() 
	{
		congratulationDialog = new AlertDialog.Builder(ConfirmationActivity.this);
		LayoutInflater layoutInflater = (LayoutInflater)this.getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.confirm_alert, null);
		congratulationDialog.setView(view);

		TextView continue_confirmbutton=null;
		TextView text_congrates = null;
		TextView text_detailcongrates = null;

		text_congrates=(TextView) view.findViewById(R.id.text_congrates);
		text_detailcongrates =(TextView) view.findViewById(R.id.text_detailcongrates);
		continue_confirmbutton=(TextView) view.findViewById(R.id.button_continueConfirmation);
		typeFace.setTypefaceLight(text_detailcongrates);
		typeFace.setTypefaceRegular(text_congrates);
		typeFace.setTypefaceRegular(continue_confirmbutton);

		continue_confirmbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub

				//generatePendingIntent();

				Intent intent= new Intent(ConfirmationActivity.this, ChildRegistrationActivity.class);
				startActivity(intent);
				sharePref.setCurrentScreen(1);
				ConfirmationActivity.this.finish();
			}
		});	 

		congratulationDialog.create();
		congratulationDialog.show();
	}

	/*private void generatePendingIntent() 
	{
		Intent alarmIntent = new Intent(ConfirmationActivity.this, FutureNotificationAlarmForMe.class);
		alarmIntent.putExtra("RepeatingType", sharePref.getFrequency());
		alarmIntent.setAction(Long.toString(System.currentTimeMillis()));

		//----Convert time for next day time scheduling------
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 9);
		c.add(Calendar.DAY_OF_MONTH, 1);


		PendingIntent pendingIntent = PendingIntent.getBroadcast(ConfirmationActivity.this,0, alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);                        
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(), 24*60*60*1000, pendingIntent);
	}*/

	public void showAlertResend(String title, String message)
	{
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

		alertBuilder.setTitle(title);
		alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
		alertBuilder.setMessage(message);
		alertBuilder.setPositiveButton(" Yes ", new DialogInterface.OnClickListener() 
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				new ConfirmationTask().execute();
			}
		});	

		alertBuilder.setNegativeButton(" No ", new DialogInterface.OnClickListener() 
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();

			}
		});	
		alertBuilder.show();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		sharePref.setIsLogout(false);
		sharePref.setParentIsRegistered(false);
		Intent intent=new Intent(ConfirmationActivity.this, ParentRegistrationActivity.class);
		startActivity(intent);
		ConfirmationActivity.this.finish();
	}
}
