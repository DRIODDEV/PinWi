package com.hatchtact.pinwi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.classmodel.RecoverPasscode;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

public  class PasswordUnLockActivity extends MainActionBarActivity 
{
	protected EditText pinCodeField1 = null;
	protected EditText pinCodeField2 = null;
	protected EditText pinCodeField3 = null;
	protected EditText pinCodeField4 = null;
	protected InputFilter[] filters = null;
	protected TextView topMessage = null;

	private int profileId=0; 
	private SharePreferenceClass sharePrefclass;
	private Gson gsonRegistration=null;
	private ShowMessages showMessage=null;

	//private PassCodeList passcode;
	private boolean loadNextScreen;

	private CheckNetwork checkNetwork=null;
	private ServiceMethod serviceMethod=null;

	private int parentId=0;

	private RecoverPasscode recoverPasscode;
	private TypeFace typeFace=null;
	private TextView button_cancel;

	private Button button0;
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private Button button6;
	private Button button7;
	private Button button8;
	private Button button9;
	private int  isSettings=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		screenName="Unlock Profile";

		super.onCreate(savedInstanceState);

		setContentView(R.layout.app_passcode_keyboard);
		isSettings=0;
		try {
			Bundle bundle = getIntent().getExtras();	
			profileId = bundle.getInt("ProfileId");
			loadNextScreen = bundle.getBoolean("ToLoadNextScreen");
			isSettings = bundle.getInt("isSettings");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isSettings=0;
		}

		typeFace= new TypeFace(PasswordUnLockActivity.this); 

		sharePrefclass=new SharePreferenceClass(PasswordUnLockActivity.this);
		showMessage = new ShowMessages(PasswordUnLockActivity.this);
		gsonRegistration=new GsonBuilder().create();
		checkNetwork=new CheckNetwork();
		serviceMethod = new ServiceMethod();
		recoverPasscode=new RecoverPasscode();

		topMessage = (TextView) findViewById(R.id.top_message);	

		ParentProfile parentCompleteInformation = gsonRegistration.fromJson(sharePrefclass.getParentProfile(), ParentProfile.class);
		parentId=parentCompleteInformation.getParentID();

		filters = new InputFilter[2];
		filters[0]= new InputFilter.LengthFilter(1);
		filters[1] = onlyNumber;

		//passcode = gsonRegistration.fromJson(sharePrefclass.getPassCodeList(), PassCodeList.class);

		//Setup the pin fields row
		pinCodeField1 = (EditText) findViewById(R.id.pincode_1);
		setupPinItem(pinCodeField1);

		pinCodeField2 = (EditText) findViewById(R.id.pincode_2);
		setupPinItem(pinCodeField2);

		pinCodeField3 = (EditText) findViewById(R.id.pincode_3);
		setupPinItem(pinCodeField3);

		pinCodeField4 = (EditText) findViewById(R.id.pincode_4);
		setupPinItem(pinCodeField4);

		//setup the keyboard
		button0 = (Button) findViewById(R.id.button0);
		button0.setOnClickListener(defaultButtonListener);

		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(defaultButtonListener);

		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(defaultButtonListener);

		button3 = (Button) findViewById(R.id.button3);
		button3.setOnClickListener(defaultButtonListener);

		button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(defaultButtonListener);

		button5 = (Button) findViewById(R.id.button5);
		button5.setOnClickListener(defaultButtonListener);

		button6 = (Button) findViewById(R.id.button6);
		button6.setOnClickListener(defaultButtonListener);

		button7 = (Button) findViewById(R.id.button7);
		button7.setOnClickListener(defaultButtonListener);

		button8 = (Button) findViewById(R.id.button8);
		button8.setOnClickListener(defaultButtonListener);

		button9 = (Button) findViewById(R.id.button9);
		button9.setOnClickListener(defaultButtonListener);

		button_cancel = (TextView) findViewById(R.id.button_cancel);
		button_cancel.setOnClickListener(defaultButtonListener);

		typeFace.setTypefaceRegular(topMessage);
		typeFace.setTypefaceRegular(button_cancel);
		typeFace.setTypefaceRegular(button0);
		typeFace.setTypefaceRegular(button1);
		typeFace.setTypefaceRegular(button2);
		typeFace.setTypefaceRegular(button3);
		typeFace.setTypefaceRegular(button4);
		typeFace.setTypefaceRegular(button5);
		typeFace.setTypefaceRegular(button6);
		typeFace.setTypefaceRegular(button7);
		typeFace.setTypefaceRegular(button8);
		typeFace.setTypefaceRegular(button9);


		//((ImageView) findViewById(R.id.button_delete)).setOnClickListener(defaultButtonListener);

		((ImageView) findViewById(R.id.button_delete)).setOnClickListener(
				new OnClickListener() {
					@SuppressWarnings("deprecation")
					@Override
					public void onClick(View arg0) {
						if( pinCodeField1.isFocused() )
						{
							//pinCodeField1.setBackgroundColor(Color.BLACK);
						}
						else if( pinCodeField2.isFocused() ) {
							pinCodeField1.requestFocus();
							pinCodeField1.setText("");
							pinCodeField1.setBackgroundResource(0);
							pinCodeField1.setBackgroundResource(R.drawable.rounded_button);
						}
						else if( pinCodeField3.isFocused() ) {
							pinCodeField2.requestFocus();
							pinCodeField2.setText("");
							pinCodeField2.setBackgroundResource(0);
							pinCodeField2.setBackgroundResource(R.drawable.rounded_button);
						}
						else if( pinCodeField4.isFocused() ) {
							pinCodeField3.requestFocus();
							pinCodeField3.setText("");
							pinCodeField3.setBackgroundResource(0);
							pinCodeField3.setBackgroundResource(R.drawable.rounded_button);
						}
					}
				});



		button_cancel.setOnClickListener(
				new OnClickListener() {
					@SuppressWarnings("deprecation")
					@Override
					public void onClick(View arg0)
					{

						if(loadNextScreen)
						{
							PasswordUnLockActivity.this.finish();
							switch (isSettings) {
							/*case StaticVariables.ACCESSPROFILESETTINGSPASSCODE:
								Intent intent=new Intent(PasswordUnLockActivity.this, AccessProfileActivity.class);
								startActivity(intent);
								break;*/
							case StaticVariables.TABCHILDACTIVITIESSETTINGSPASSCODE:
								finish();
								break;
							default:
								/*Intent intent=new Intent(PasswordUnLockActivity.this, AccessProfileActivity.class);
								startActivity(intent);*/
								finish();
								break;
							}
							
						}
						else
						{
							PasswordUnLockActivity.this.finish();
						}
					}
				});

		TextView forgetPassword=(TextView) findViewById(R.id.txt_forgetPassword_unlock);
		typeFace.setTypefaceLight(forgetPassword);

		forgetPassword.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				new SendPasscodeOnEmail(parentId).execute();
			}
		});	
	}

	protected void setupPinItem(EditText item){
		item.setInputType(InputType.TYPE_NULL); 
		item.setFilters(filters); 
		item.setOnTouchListener(otl);
		item.setTransformationMethod(PasswordTransformationMethod.getInstance());
	}

	private OnClickListener defaultButtonListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			int currentValue = -1;
			int id = arg0.getId();
			if (id == R.id.button0) {
				currentValue = 0;
			} else if (id == R.id.button1) {
				currentValue = 1;
			} else if (id == R.id.button2) {
				currentValue = 2;
			} else if (id == R.id.button3) {
				currentValue = 3;
			} else if (id == R.id.button4) {
				currentValue = 4;
			} else if (id == R.id.button5) {
				currentValue = 5;
			} else if (id == R.id.button6) {
				currentValue = 6;
			} else if (id == R.id.button7) {
				currentValue = 7;
			} else if (id == R.id.button8) {
				currentValue = 8;
			} else if (id == R.id.button9) {
				currentValue = 9;
			} else if(id==R.id.button_cancel){
				currentValue=10;
			}else if(id==R.id.button_delete){
				currentValue=11;
			}else{

			}

			//set the value and move the focus
			String currentValueString = String.valueOf(currentValue);
			if( pinCodeField1.isFocused() ) {
				pinCodeField1.setBackgroundResource(R.drawable.solid_rounded_button);
				pinCodeField1.setText(currentValueString);
				pinCodeField2.requestFocus();
				pinCodeField2.setText("");
			}
			else if( pinCodeField2.isFocused() ) {
				pinCodeField2.setBackgroundResource(R.drawable.solid_rounded_button);
				pinCodeField2.setText(currentValueString);
				pinCodeField3.requestFocus();
				pinCodeField3.setText("");
			}
			else if( pinCodeField3.isFocused() ) {
				pinCodeField3.setBackgroundResource(R.drawable.solid_rounded_button);
				pinCodeField3.setText(currentValueString);
				pinCodeField4.requestFocus();
				pinCodeField4.setText("");
			}
			else if( pinCodeField4.isFocused() ) {
				pinCodeField4.setBackgroundResource(R.drawable.solid_rounded_button);
				pinCodeField4.setText(currentValueString);
			}

			if(pinCodeField4.getText().toString().length() > 0 &&
					pinCodeField3.getText().toString().length() > 0 &&
					pinCodeField2.getText().toString().length() > 0 &&
					pinCodeField1.getText().toString().length() > 0
					) {
				onPinLockInserted();
			}
		}
	};

	protected void onPinLockInserted() {
		String passLock = pinCodeField1.getText().toString() + pinCodeField2.getText().toString() +
				pinCodeField3.getText().toString() + pinCodeField4.getText();


		boolean passCodeMatched = false ;

		int ProfileType = -1;

		/*if(passcode!=null && passcode.getPasscodeList().size()>0)
		{
			for(int i=0;i<passcode.getPasscodeList().size();i++)
			{
				if(passcode.getPasscodeList().get(i).getProfileId()==profileId && 
						passLock.trim().equalsIgnoreCase(passcode.getPasscodeList().get(i).getPassCode().trim()))
				{
					passCodeMatched = true;
					ProfileType = passcode.getPasscodeList().get(i).getPassCodeType();
					break;
				}
			}
		}*/
		if(StaticVariables.parentPasscodeModel!=null)
		{
			if(StaticVariables.parentPasscodeModel.getProfileId()==profileId && 
					passLock.trim().equalsIgnoreCase(StaticVariables.parentPasscodeModel.getPassCode().trim()))
			{
				passCodeMatched = true;
				ProfileType = StaticVariables.parentPasscodeModel.getPassCodeType();
			}

		}

		if(passCodeMatched)
		{
			//new CheckPassCode().execute();
			// Send for Parent 

			if(ProfileType==1)   
			{	
				if(loadNextScreen)
				{
					PasswordUnLockActivity.this.finish();
					switch (isSettings) {
					case StaticVariables.ACCESSPROFILESETTINGSPASSCODE:
						StaticVariables.isSettingsFromAccessProfile=true;
						Intent intentSettings =new Intent(PasswordUnLockActivity.this, SettingsActivity.class);
						startActivity(intentSettings);
						break;
					case StaticVariables.TABCHILDACTIVITIESSETTINGSPASSCODE:
						Intent intentSettingsTab =new Intent(PasswordUnLockActivity.this, SettingsActivity.class);
						startActivity(intentSettingsTab);
						break;
					default:
						AccessProfileActivity.getInstance().finish();
						Intent intent=new Intent(PasswordUnLockActivity.this, TabChildActivities.class);
						intent.putExtra("Type", 1);
						startActivity(intent);
						break;
					}

					/*Intent intent=new Intent(PasswordUnLockActivity.this, ParentProfileInformationActivity.class)	;
					startActivity(intent);*/
				}
				else
				{
					PasswordUnLockActivity.this.finish();
				}	
			}
			else
			{

				// Screen Next to child
				if(loadNextScreen)
				{
					switch (isSettings) {
					case StaticVariables.ACCESSPROFILESETTINGSPASSCODE:
						StaticVariables.isSettingsFromAccessProfile=true;
						Intent intentSettings =new Intent(PasswordUnLockActivity.this, SettingsActivity.class);
						startActivity(intentSettings);
						break;
					case StaticVariables.TABCHILDACTIVITIESSETTINGSPASSCODE:
						Intent intentSettingsTab =new Intent(PasswordUnLockActivity.this, SettingsActivity.class);
						startActivity(intentSettingsTab);
						break;
					default:
						AccessProfileActivity.getInstance().finish();
						Intent intent=new Intent(PasswordUnLockActivity.this, TabChildActivities.class);
						intent.putExtra("Type", 1);
						startActivity(intent);
						break;
					}
					/*Intent intent=new Intent(PasswordUnLockActivity.this, ParentProfileInformationActivity.class)	;
					startActivity(intent);*/
				}

				finish();
			}
		}
		else
		{
			pinCodeField1.setText("");
			pinCodeField2.setText("");
			pinCodeField3.setText("");
			pinCodeField4.setText("");
			pinCodeField1.requestFocus();

			pinCodeField1.setBackgroundResource(0);
			pinCodeField1.setBackgroundResource(R.drawable.rounded_button);
			pinCodeField2.setBackgroundResource(0);
			pinCodeField2.setBackgroundResource(R.drawable.rounded_button);
			pinCodeField3.setBackgroundResource(0);
			pinCodeField3.setBackgroundResource(R.drawable.rounded_button);
			pinCodeField4.setBackgroundResource(0);
			pinCodeField4.setBackgroundResource(R.drawable.rounded_button);


			showMessage.showAlert("Warning", "Wrong code.Please try again");

			//	Toast.makeText(PasswordUnLockActivity.this,"Wrong passcode.Please try again",Toast.LENGTH_SHORT).show();
		}
	}

	private InputFilter onlyNumber = new InputFilter() {
		@Override
		public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

			if( source.length() > 1 )
				return "";

			if( source.length() == 0 ) //erase
				return null;

			try {
				int number = Integer.parseInt(source.toString());
				if( ( number >= 0 ) && ( number <= 9 ) )
					return String.valueOf(number);
				else
					return "";
			} catch (NumberFormatException e) {
				return "";
			}
		}
	};

	private OnTouchListener otl = new OnTouchListener() {
		@Override
		public boolean onTouch (View v, MotionEvent event) {
			if( v instanceof EditText ) {
				((EditText)v).setText("");
			}
			return false;
		}
	};


	private ProgressDialog progressDialog=null;	

	private class SendPasscodeOnEmail extends AsyncTask<Void, Void, Integer>
	{
		private int parentId;

		public SendPasscodeOnEmail(int parentId)
		{
			// TODO Auto-generated constructor stub 
			this.parentId = parentId;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = ProgressDialog.show(PasswordUnLockActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(PasswordUnLockActivity.this))
			{
				ErrorCode=serviceMethod.recoverPasscode(parentId);
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
		showMessage.showAlert("Recover Code", err.getErrorDesc());
	}

	/*private class CheckPassCode extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub

			int profileId=0;

			if(checkNetwork.checkNetworkConnection(PasswordUnLockActivity.this))
			{
				profileId=-1;
				profileId=serviceMethod.checkpassCode(checkPasscode);

				if(profileId!=0)
				{
					checkPasscode.setProfileID(profileId);
				}
				else
				{

				}
			}
			return profileId;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result); 
			if(result==0)
			{
				showMessage.showToastMessage("Please check your network connection");
			}
			else
			{
				if(result!=-1)
				{
					Intent intent=new Intent(PasswordUnLockActivity.this, ParentRegistrationActivity.class);
					startActivity(intent);
				}
				else
				{
					getError();
				}
			}
		}
	}

	private void getError()
	{
		Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());
	}*/
}
