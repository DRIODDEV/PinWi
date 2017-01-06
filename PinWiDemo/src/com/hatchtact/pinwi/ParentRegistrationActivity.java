package com.hatchtact.pinwi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hatchtact.pinwi.classmodel.CityList;
import com.hatchtact.pinwi.classmodel.CountryList;
import com.hatchtact.pinwi.classmodel.GetAutolockTimeList;
import com.hatchtact.pinwi.classmodel.GetLocationDetails;
import com.hatchtact.pinwi.classmodel.GetParentDetails;
import com.hatchtact.pinwi.classmodel.LocalityList;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.classmodel.PassCode;
import com.hatchtact.pinwi.classmodel.PassCodeList;
import com.hatchtact.pinwi.classmodel.UpdateLocationByParentID;
import com.hatchtact.pinwi.classmodel.UpdateParentProfile;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.GettingLattitude;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;
import com.hatchtact.pinwi.utility.Validation;
import com.hatchtact.pinwi.view.AutoCompleteAdapter;

@SuppressLint("NewApi")
public class ParentRegistrationActivity extends MainActionBarActivity implements OnTouchListener
{
	private EditText firstname_editText=null;
	private EditText lastname_editText=null;
	private EditText email_editText=null;
	private EditText password_editText=null;
	private EditText dob_editText=null;
	private EditText phone_editText=null;
	private EditText phoneCode_editText=null;
	private EditText  passcode_editText=null;
	private AutoCompleteTextView autolocktime_autoCompleteTextView=null;
	private Button continue_button=null;
	private ImageView profilePic_imageView=null;
	private ImageView dob_button=null;
	private TextView text_ProfileText=null;
	private TextView text_passcode=null;

	private RadioButton father_textView=null;
	private RadioButton mother_textView=null;  
	private RadioButton guardian_textView=null;
	private RadioButton male_textView=null;
	private RadioButton female_textView=null;
	private Switch passcode_switchView=null;
	private LinearLayout layout_Pass_AutoLock=null;

	private TypeFace typeFace=null;   
	private Validation checkValidation=null;
	private ShowMessages showMessage=null;  
	private ParentProfile parentProfile=null;
	private UpdateParentProfile updateParentProfile = null; 
	private SharePreferenceClass sharePreferneceClass=null;
	private Gson gsonRegistration=null;

	private RadioGroup radioGroup_maleFemale=null;
	private RadioGroup radioGroup_relationship=null;

	private DatePickerDialog datePickerDialog=null;
	private SimpleDateFormat dateFormatter=null;

	private int yearDOB,monthDOB,dayDOB;

	private static final int SELECT_PICTURE = 100;

	private String imageByte="";

	private Bitmap	bitmapLength=null;
	private	ServiceMethod serviceMethod;

	private GetAutolockTimeList getAutolockTimeList=null;
	private ArrayList<String> getAutolockTimeStringList=new ArrayList<String>();

	private CheckNetwork checkNetwork=null;

	private boolean onTouchContinueButton=false;

	private boolean parentUpdate=false;

	private int parentId;
	private CountryList countryList=null;
	private CityList cityList=null;
	private LocalityList localityList=null;

	private ArrayList<String> countryStringList=new ArrayList<String>();
	private ArrayList<String> cityStringList=new ArrayList<String>();
	private ArrayList<String> localityStringList=new ArrayList<String>();


	private AutoCompleteTextView city_autoCompleteTextView=null;
	private AutoCompleteTextView country_autoCompleteTextView=null;
	private AutoCompleteTextView street_autoCompleteTextView=null;
	//private EditText street_editText=null;
	private int CountryId=0;
	private int CityId=0;

	private UpdateLocationByParentID updateLocationByParentID=null;
	private boolean isPasscodeTouched=false;//need to use this flag
	private TextView header_text,header_help;



	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub

		screenName="Profile Setup";

		super.onCreate(savedInstanceState);

		setContentView(R.layout.parent_registration_activity);


		Bundle bundle = getIntent().getExtras();

		try {	
			parentUpdate = bundle.getBoolean("ToParentScreen");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		countryList=new CountryList();
		cityList=new CityList();
		localityList=new LocalityList();
		parentProfile=new ParentProfile();
		sharePreferneceClass=new SharePreferenceClass(ParentRegistrationActivity.this);
		gsonRegistration = new GsonBuilder().create();
		header_text=(TextView) findViewById(R.id.header_text);
		header_help=(TextView) findViewById(R.id.header_help);
		header_text.setText(screenName);

		header_help.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentAboutUs =new Intent(ParentRegistrationActivity.this, ActivityAboutUS.class);
				startActivity(intentAboutUs);
				StaticVariables.webUrl="http://pinwi.in/contactus.aspx?4";	
			}
		});

		initailization();
	}

	private int countDialog=0;
	protected boolean isLockDialogClicked=false;
	private void initailization()
	{
		isLockDialogClicked=false;
		onTouchContinueButton=false;

		checkValidation = new Validation();
		checkNetwork=new CheckNetwork();
		serviceMethod = new ServiceMethod();
		getAutolockTimeList=new GetAutolockTimeList();
		showMessage=new ShowMessages(ParentRegistrationActivity.this);

		showMessage = new ShowMessages(ParentRegistrationActivity.this);
		typeFace=new TypeFace(ParentRegistrationActivity.this);
		dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

		//street_editText=(EditText) findViewById(R.id.text_street);
		city_autoCompleteTextView=(AutoCompleteTextView) findViewById(R.id.text_city);
		country_autoCompleteTextView=(AutoCompleteTextView) findViewById(R.id.text_country);
		street_autoCompleteTextView=(AutoCompleteTextView) findViewById(R.id.text_street);

		firstname_editText=(EditText) findViewById(R.id.text_Firstname);
		lastname_editText=(EditText) findViewById(R.id.text_Lastname);
		email_editText=(EditText) findViewById(R.id.text_email);
		password_editText=(EditText) findViewById(R.id.text_password);
		dob_editText=(EditText) findViewById(R.id.text_DOB);
		phone_editText=(EditText) findViewById(R.id.text_phoneParent);
		passcode_editText=(EditText) findViewById(R.id.text_passcodeParent);
		autolocktime_autoCompleteTextView= (AutoCompleteTextView) findViewById(R.id.text_AutoLockTime);
		continue_button=(Button) findViewById(R.id.button_continueParent);
		continue_button.setText("Submit");
		profilePic_imageView=(ImageView) findViewById(R.id.image_camera);
		mother_textView=(RadioButton) findViewById(R.id.text_mother);
		father_textView=(RadioButton) findViewById(R.id.text_Father);
		guardian_textView=(RadioButton) findViewById(R.id.text_guardian);
		male_textView=(RadioButton) findViewById(R.id.text_male);
		female_textView=(RadioButton) findViewById(R.id.text_female);
		dob_button=(ImageView) findViewById(R.id.image_calender);
		radioGroup_maleFemale=(RadioGroup) findViewById(R.id.radiogroup_male_female);
		radioGroup_relationship=(RadioGroup) findViewById(R.id.radiogroup_relationShip);
		phoneCode_editText=(EditText) findViewById(R.id.text_phoneCodeParent);
		passcode_switchView=(Switch) findViewById(R.id.passcode_switch);
		layout_Pass_AutoLock=(LinearLayout) findViewById(R.id.layout_Pass_AutoLock);
		text_ProfileText=(TextView) findViewById(R.id.text_ProfileText);
		text_passcode=(TextView) findViewById(R.id.text_passcode);
		father_textView.setChecked(true);

		//typeFace.setTypefaceLight(street_editText);	
		typeFace.setTypefaceLight(street_autoCompleteTextView);	
		typeFace.setTypefaceLight(city_autoCompleteTextView);
		typeFace.setTypefaceLight(country_autoCompleteTextView);
		typeFace.setTypefaceLight(firstname_editText);
		typeFace.setTypefaceLight(lastname_editText);
		typeFace.setTypefaceLight(email_editText);
		typeFace.setTypefaceLight(password_editText);
		typeFace.setTypefaceLight( passcode_editText);
		typeFace.setTypefaceLight(dob_editText);
		typeFace.setTypefaceLight(passcode_switchView);
		typeFace.setTypefaceLight(phone_editText);
		typeFace.setTypefaceLight(autolocktime_autoCompleteTextView);
		typeFace.setTypefaceRegular(continue_button);
		typeFace.setTypefaceLight(father_textView);
		typeFace.setTypefaceLight(mother_textView);
		typeFace.setTypefaceLight(guardian_textView);
		typeFace.setTypefaceLight(male_textView);
		typeFace.setTypefaceLight(female_textView);
		typeFace.setTypefaceLight(phoneCode_editText);
		typeFace.setTypefaceLight(text_ProfileText);
		typeFace.setTypefaceLight(text_passcode);

		country_autoCompleteTextView.setOnTouchListener(this);
		city_autoCompleteTextView.setOnTouchListener(this);
		street_autoCompleteTextView.setOnTouchListener(this);

		country_autoCompleteTextView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				String textSelected = arg0.getItemAtPosition(arg2).toString();	
				int currentCountryId = 0;

				for(int i=0;i<countryList.getCountry().size();i++)
				{
					if(textSelected.trim().equalsIgnoreCase(countryList.getCountry().get(i).getCountryName().trim()))
					{
						currentCountryId=countryList.getCountry().get(i).getCountryID();
					}
				}

				if(currentCountryId==CountryId)
				{
					System.out.println("current CountryId"+currentCountryId+"previous id"+CountryId);
				}
				else
				{

					new GetListOfField("city",currentCountryId).execute();	
					CountryId=currentCountryId;
				}
			}
		});

		/*city_autoCompleteTextView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				String textSelected = arg0.getItemAtPosition(arg2).toString();	
				int currentCityId = 0;

				for(int i=0;i<cityList.getCity().size();i++)
				{
					if(textSelected.trim().equalsIgnoreCase(cityList.getCity().get(i).getCityName().trim()))
					{
						currentCityId=cityList.getCity().get(i).getCityID();
					}
				}

				if(currentCountryId==CountryId)
				{
					System.out.println("current CountryId"+currentCountryId+"previous id"+CountryId);
				}
				else
				{

					new GetListOfField("locality",currentCityId).execute();	
					CityId=currentCityId;
				}
			}
		});*/

		city_autoCompleteTextView.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

			}
		});

		street_autoCompleteTextView.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

			}
		});

		setDateTimeField();

		passcode_switchView.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(passcode_switchView.isChecked())
				{

					if(!isLockDialogClicked)
					{
						sharePreferneceClass.setIsPasscodeParentSet(isChecked);
						//layout_Pass_AutoLock.setVisibility(View.VISIBLE);
						if(!isPasscodeTouched)
						{
							Intent intent=new Intent(ParentRegistrationActivity.this, AddPasscordActivity.class);
							Bundle bundle =new Bundle();
							//text_passcode.setText("Passcode");
							text_passcode.setText("Lock Your Profile");
							bundle.putString("passCode","");
							intent.putExtras(bundle);
							startActivityForResult(intent, 200);
						}
						layout_Pass_AutoLock.setVisibility(View.GONE);

						isPasscodeTouched=false;
					}
					else
					{
						isLockDialogClicked=false;
					}
				}
				else
				{
					sharePreferneceClass.setIsPasscodeParentSet(isChecked);
					layout_Pass_AutoLock.setVisibility(View.GONE);
					passcode_editText.setText("");
					text_passcode.setInputType(InputType.TYPE_CLASS_TEXT);
					text_passcode.setAlpha(.7f);
					//text_passcode.setText("Passcode");
					text_passcode.setText("Lock Your Profile");

					autolocktime_autoCompleteTextView.setText("");
				}
			}
		});

		phoneCode_editText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				phoneCode_editText.setSelection(phoneCode_editText.getText().toString().length());	
			}
		});

		radioGroup_relationship.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId==R.id.text_Father)
				{
					isFirstTime=true;
					father_textView.setChecked(true);
					radioGroup_maleFemale.setVisibility(View.GONE);
				}

				if(checkedId==R.id.text_mother)
				{
					isFirstTime=true;
					mother_textView.setChecked(true);
					radioGroup_maleFemale.setVisibility(View.GONE);
				}
				if(checkedId==R.id.text_guardian)
				{
					guardian_textView.setChecked(true);
					//radioGroup_maleFemale.setVisibility(View.VISIBLE);
					radioGroup_maleFemale.setVisibility(View.GONE);
					if(!parentUpdate)
					{
						if(isFirstTime)
						{
							Dialog dialog = onCreateDialogSingleChoice();
							dialog.show();
						}
					}
				}
			}
		});

		radioGroup_maleFemale.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId==R.id.text_male)
				{
					male_textView.setChecked(true);
				}

				if(checkedId==R.id.text_female)
				{
					female_textView.setChecked(true);
				}
			}
		});
		//yes
		continue_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				validateDataOnSubmit();
			}
		});

		dob_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				datePickerDialog.show();
			}
		});

		profilePic_imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cameraClickDialog();
			}
		});
		text_passcode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(passcode_switchView.isChecked())
				{
					Intent intent=new Intent(ParentRegistrationActivity.this, AddPasscordActivity.class);
					Bundle bundle =new Bundle();
					bundle.putString("passCode", text_passcode.getText().toString());
					intent.putExtras(bundle);
					startActivityForResult(intent, 200);
				}
			}
		});

		autolocktime_autoCompleteTextView.setOnTouchListener(this);

		autolocktime_autoCompleteTextView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

			}
		});

		new GetListOfAutoLockTimeField().execute();
		new GetListOfField("country",0).execute();
		new GetListOfField("city",1).execute();
		// new GetListOfField("locality",1).execute();	
		//	if(!StaticVariables.isSignUpClicked)
		{
			if(!parentUpdate)
			{
				addPreFilledData();
			}
			else
			{
				setDataFromServer();
			}
		}
	}

	private void setDataFromServer()
	{
		// TODO Auto-generated method stub

		ParentProfile parentInformation = gsonRegistration.fromJson(sharePreferneceClass.getParentProfile(), ParentProfile.class);

		parentId=parentInformation.getParentID();

		continue_button.setText("Submit");
		email_editText.setEnabled(false);
		//dob_editText.setEnabled(false);
		dob_editText.setEnabled(true);
		dob_button.setEnabled(false);
		radioGroup_relationship.setEnabled(false);
		radioGroup_maleFemale.setEnabled(false);
		father_textView.setEnabled(false);
		mother_textView.setEnabled(false);
		guardian_textView.setEnabled(false);
		male_textView.setEnabled(false);
		female_textView.setEnabled(false);

		new GetParentDetailFromServer(parentId).execute();
	}

	private ProgressDialog progressDialog2=null;	

	private class GetParentDetailFromServer extends AsyncTask<Void, Void, Integer>
	{
		private int parentId;

		private GetParentDetails parentInformationFromServer=null;
		private GetLocationDetails locationInformationFromServer=null;

		public GetParentDetailFromServer(int parentId)
		{
			// TODO Auto-generated constructor stub 
			this.parentId = parentId;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog2 = ProgressDialog.show(ParentRegistrationActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog2.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ParentRegistrationActivity.this))
			{
				parentInformationFromServer = serviceMethod.getParentinformation(parentId);	
				locationInformationFromServer = serviceMethod.getLocationinformation(parentId);	

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
				if (progressDialog2.isShowing())
					progressDialog2.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(ParentRegistrationActivity.this))
					new GetParentDetailFromServer(parentId).execute();
			}
			else
			{
				if(parentInformationFromServer!=null)
				{
					if(locationInformationFromServer!=null)
					{
						if(locationInformationFromServer.getCountry()!=0)
						{
							country_autoCompleteTextView.setText(locationInformationFromServer.getCountryName());
							parentProfile.setCountryID(locationInformationFromServer.getCountry());
						}

						if(locationInformationFromServer.getCity()!=0)
						{
							city_autoCompleteTextView.setText(locationInformationFromServer.getCityName());
							parentProfile.setCityID(locationInformationFromServer.getCity());
						}

						if(locationInformationFromServer.getStreetLocality()!=null && locationInformationFromServer.getStreetLocality().trim().length()>0)
						{
							street_autoCompleteTextView.setText(locationInformationFromServer.getStreetLocality());
							parentProfile.setLocalityID(locationInformationFromServer.getLocality());
						}

						/*	if(locationInformationFromServer.getStreetLocality()!=null && locationInformationFromServer.getStreetLocality().trim().length()>0)
							street_editText.setText(locationInformationFromServer.getStreetLocality());*/
					}
					if(parentInformationFromServer.getFirstName()!=null && parentInformationFromServer.getFirstName().trim().length()>0)
						firstname_editText.setText(parentInformationFromServer.getFirstName());

					if(parentInformationFromServer.getLastName()!=null && parentInformationFromServer.getLastName().trim().length()>0)
						lastname_editText.setText(parentInformationFromServer.getLastName());

					if(parentInformationFromServer.getEmailAddress()!=null && parentInformationFromServer.getEmailAddress().trim().length()>0)
						email_editText.setText(parentInformationFromServer.getEmailAddress());

					if(parentInformationFromServer.getPassword()!=null && parentInformationFromServer.getPassword().trim().length()>0)
						password_editText.setText(parentInformationFromServer.getPassword());

					if(parentInformationFromServer.getDateOfBirth()!=null && parentInformationFromServer.getDateOfBirth().trim().length()>0)
					{
						if(!parentInformationFromServer.getDateOfBirth().trim().equalsIgnoreCase("01/01/1900"))
							dob_editText.setText(parentInformationFromServer.getDateOfBirth());
					}

					if(parentInformationFromServer.getRelation()!=null && parentInformationFromServer.getRelation().trim().length()>0)
					{
						if(parentInformationFromServer.getRelation().toString().equalsIgnoreCase("Father"))
						{
							parentProfile.setGender("Male");
							parentProfile.setRelation(father_textView.getText().toString());
							father_textView.setChecked(true);
						}

						if(parentInformationFromServer.getRelation().toString().equalsIgnoreCase("Mother"))
						{

							parentProfile.setGender("Female");
							parentProfile.setRelation(mother_textView.getText().toString());
							mother_textView.setChecked(true);
						}

						if(parentInformationFromServer.getRelation().toString().equalsIgnoreCase("Guardian"))
						{

							//radioGroup_maleFemale.setVisibility(View.VISIBLE);//changed for selector guardian
							guardian_textView.setChecked(true);


							if(parentInformationFromServer.getGender().toString().equalsIgnoreCase("Male"))
							{
								parentProfile.setGender(male_textView.getText().toString());
								parentProfile.setRelation(guardian_textView.getText().toString());
								male_textView.setChecked(true);
							}
							if(parentInformationFromServer.getGender().toString().equalsIgnoreCase("Female"))
							{
								parentProfile.setGender(female_textView.getText().toString());
								parentProfile.setRelation(guardian_textView.getText().toString());
								female_textView.setChecked(true);
							}		
						}
					}   

					if(parentInformationFromServer.getProfileImage()!=null && parentInformationFromServer.getProfileImage().length()>0)
					{
						byte[] imageByteRefill=Base64.decode(parentInformationFromServer.getProfileImage(), 0);
						if(imageByteRefill!=null)
						{
							imageByte = parentInformationFromServer.getProfileImage();

							profilePic_imageView.setImageBitmap(getRoundedShape(BitmapFactory.decodeByteArray(imageByteRefill, 0, imageByteRefill.length)));	
						}
					}

					if(parentInformationFromServer.getContact()!=null && parentInformationFromServer.getContact().trim().length()>0)
						phone_editText.setText(parentInformationFromServer.getContact());

					if(parentInformationFromServer.getPasscode()!=null && parentInformationFromServer.getPasscode().trim().length()>0)
					{
						sharePreferneceClass.setIsPasscodeParentSet(true);
					}
					else
					{
						sharePreferneceClass.setIsPasscodeParentSet(false);
					}

					if(sharePreferneceClass.getIsPasscodeParentSet()==true)
					{
						isPasscodeTouched=true;
						passcode_switchView.setChecked(true);

						if(parentInformationFromServer.getPasscode()!=null && parentInformationFromServer.getPasscode().trim().length()>0)
						{
							//passcode_editText.setText(parentInformationFromServer.getPasscode());
							text_passcode.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
							//text_passcode.setTransformationMethod(PasswordTransformationMethod.getInstance());
							text_passcode.setAlpha(1f);

							text_passcode.setText(parentInformationFromServer.getPasscode());
						}


						if(parentInformationFromServer.getAutolockTime()!=0)
						{

							autolocktime_autoCompleteTextView.setText(parentInformationFromServer.getTimeValue());	
							autolocktime_autoCompleteTextView.setSelection(parentInformationFromServer.getTimeValue().length());
						}
					}
				}
				else
				{	
					getError();
				}	
			}	
		}	
	}


	private boolean isFirstTime=false;
	private void addPreFilledData() 
	{
		// TODO Auto-generated method stub
		ParentProfile parentInformation = gsonRegistration.fromJson(sharePreferneceClass.getParentProfile(), ParentProfile.class);

		if(parentInformation!=null)
		{
			isFirstTime=false;
			if(parentInformation.getCountry()!=null && parentInformation.getCountry().trim().length()>0)
				country_autoCompleteTextView.setText(parentInformation.getCountry());

			if(parentInformation.getCity()!=null && parentInformation.getCity().trim().length()>0)
				city_autoCompleteTextView.setText(parentInformation.getCity());

			if(parentInformation.getStreetLocality()!=null && parentInformation.getStreetLocality().trim().length()>0)
			{
				street_autoCompleteTextView.setText(parentInformation.getStreetLocality());
			}
			/*if(parentInformation.getStreetLocality()!=null && parentInformation.getStreetLocality().trim().length()>0)
				street_editText.setText(parentInformation.getStreetLocality());*/

			if(parentInformation.getFirstName()!=null && parentInformation.getFirstName().trim().length()>0)
				firstname_editText.setText(parentInformation.getFirstName());
			else if(StaticVariables.modelFacebook!=null)
			{
				firstname_editText.setText(StaticVariables.modelFacebook.getFirst_name());

			}

			if(parentInformation.getLastName()!=null && parentInformation.getLastName().trim().length()>0)
				lastname_editText.setText(parentInformation.getLastName());
			else if(StaticVariables.modelFacebook!=null)
			{
				lastname_editText.setText(StaticVariables.modelFacebook.getLast_name());

			}


			if(parentInformation.getEmailAddress()!=null && parentInformation.getEmailAddress().trim().length()>0)
				email_editText.setText(parentInformation.getEmailAddress());
			else if(StaticVariables.modelFacebook!=null)
			{
				email_editText.setText(StaticVariables.modelFacebook.getEmail());

			}

			if(parentInformation.getPassword()!=null && parentInformation.getPassword().trim().length()>0)
				password_editText.setText(parentInformation.getPassword());

			if(parentInformation.getDateOfBirth()!=null && parentInformation.getDateOfBirth().trim().length()>0)
			{
				if(!parentInformation.getDateOfBirth().trim().equalsIgnoreCase("01/01/1900"))
					dob_editText.setText(parentInformation.getDateOfBirth());
			}
			else if(StaticVariables.modelFacebook!=null)
			{
				//dob_editText.setText(StaticVariables.modelFacebook.getBirthday());

			}

			if(parentInformation.getRelation()!=null && parentInformation.getRelation().trim().length()>0)
			{
				if(parentInformation.getRelation().toString().equalsIgnoreCase("Father"))
				{
					parentProfile.setGender("Male");
					parentProfile.setRelation(father_textView.getText().toString());
					father_textView.setChecked(true);
				}

				if(parentInformation.getRelation().toString().equalsIgnoreCase("Mother"))
				{

					parentProfile.setGender("Female");
					parentProfile.setRelation(mother_textView.getText().toString());
					mother_textView.setChecked(true);
				}

				if(parentInformation.getRelation().toString().equalsIgnoreCase("Guardian"))
				{

					//radioGroup_maleFemale.setVisibility(View.VISIBLE);
					guardian_textView.setChecked(true);


					if(parentInformation.getGender().toString().equalsIgnoreCase("Male"))
					{
						parentProfile.setGender(male_textView.getText().toString());
						parentProfile.setRelation(guardian_textView.getText().toString());
						male_textView.setChecked(true);
					}
					if(parentInformation.getGender().toString().equalsIgnoreCase("Female"))
					{
						parentProfile.setGender(female_textView.getText().toString());
						parentProfile.setRelation(guardian_textView.getText().toString());
						female_textView.setChecked(true);
					}		
				}
			}   

			if(parentInformation.getProfileImage()!=null && parentInformation.getProfileImage().length()>0)
			{
				try {
					imageByte = parentInformation.getProfileImage();
					profilePic_imageView.setImageBitmap(getRoundedShape(BitmapFactory.decodeFile(parentInformation.getProfileImage())));			
					parentProfile.setProfileImage(parentInformation.getProfileImage());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					imageByte="";
				}
			}
			else if(StaticVariables.modelFacebook!=null)
			{
				new LoadProfileImage().execute();


			}
			if(parentInformation.getContact()!=null && parentInformation.getContact().trim().length()>0)
				phone_editText.setText(parentInformation.getContact());

			if(parentInformation.getPasscode()!=null && parentInformation.getPasscode().trim().length()>0)
			{
				sharePreferneceClass.setIsPasscodeParentSet(true);
			}
			else
			{
				sharePreferneceClass.setIsPasscodeParentSet(false);
			}


			if(sharePreferneceClass.getIsPasscodeParentSet()==true)
			{
				isPasscodeTouched=true;
				passcode_switchView.setChecked(true);

				if(parentInformation.getPasscode()!=null && parentInformation.getPasscode().trim().length()>0)
				{
					//passcode_editText.setText(parentInformation.getPasscode());
					text_passcode.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
					//text_passcode.setTransformationMethod(PasswordTransformationMethod.getInstance());
					text_passcode.setAlpha(1f);

					text_passcode.setText(parentInformation.getPasscode());
				}

				if(parentInformation.getAutolockTime()!=null && parentInformation.getAutolockTime().trim().length()>0)
					autolocktime_autoCompleteTextView.setText(parentInformation.getAutolockTime());	

			}
		}

		else
		{
			isFirstTime=true;
			if(StaticVariables.modelFacebook!=null)
			{
				firstname_editText.setText(StaticVariables.modelFacebook.getFirst_name());

			}

			if(StaticVariables.modelFacebook!=null)
			{
				lastname_editText.setText(StaticVariables.modelFacebook.getLast_name());

			}


			if(StaticVariables.modelFacebook!=null)
			{
				email_editText.setText(StaticVariables.modelFacebook.getEmail());

			}

			if(StaticVariables.modelFacebook!=null)
			{
				//dob_editText.setText(StaticVariables.modelFacebook.getBirthday());

			}



			if(StaticVariables.modelFacebook!=null)
			{
				new LoadProfileImage().execute();


			}


		}
	}

	private ProgressDialog progressDialog1=null;	

	private class GetListOfAutoLockTimeField extends AsyncTask<Void, Void, Integer>
	{   

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog1 = ProgressDialog.show(ParentRegistrationActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog1.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ParentRegistrationActivity.this))
			{
				getAutolockTimeList = serviceMethod.getAutoLockTime();

				if(getAutolockTimeList!=null)
				{
					getAutolockTimeStringList = getAutolockTimeStringList();
				}
				else
				{

				}
			}
			else       
			{
				ErrorCode=-1;
			}
			return ErrorCode;
		}

		private ArrayList<String> getAutolockTimeStringList() {
			// TODO Auto-generated method stub
			getAutolockTimeStringList=new ArrayList<String>();

			for(int i=0;i<getAutolockTimeList.getGetAutolockTime().size();i++)
			{
				getAutolockTimeStringList.add(getAutolockTimeList.getGetAutolockTime().get(i).getTimeValue());
			}

			return getAutolockTimeStringList;
		}


		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {
				if (progressDialog1.isShowing())
					progressDialog1.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(ParentRegistrationActivity.this))
					new GetListOfAutoLockTimeField().execute();
			}
			else
			{
				if(getAutolockTimeStringList!=null && getAutolockTimeStringList.size()>0)
				{
					AutoCompleteAdapter checkUpAdapter = new AutoCompleteAdapter(ParentRegistrationActivity.this, R.layout.list_item, R.id.item,getAutolockTimeStringList());
					autolocktime_autoCompleteTextView.setAdapter(checkUpAdapter);
					autolocktime_autoCompleteTextView.setValidator(new ValidateText(getAutolockTimeStringList,0));
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
		com.hatchtact.pinwi.classmodel.Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());
	}

	private void setDateTimeField() {
		dob_editText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				datePickerDialog.show();
			}
		});

		Calendar newCalendar = Calendar.getInstance();

		datePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				dob_editText.setText(dateFormatter.format(newDate.getTime()));
				yearDOB=year;
				monthDOB=monthOfYear;
				dayDOB=dayOfMonth;
			}
		},newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
	}

	void cameraClickDialog() 
	{
		CharSequence[] items={"Camera","Gallery"};
		AlertDialog.Builder optionDialog = new AlertDialog.Builder(this);


		optionDialog.setTitle("Select Your Option");

		optionDialog.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// The 'which' argument contains the index position
				// of the selected item

				if(id==0)
				{
					Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

					//i.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
					startActivityForResult(i, 105);
					/*Intent i=new Intent(ParentRegistrationActivity.this, TakePicture.class);
					startActivityForResult(i, 105);*/
				}
				else if(id==1)
				{
					selectImage();
				}

			}
		});

		optionDialog.create();
		optionDialog.show();
	}

	private void selectImage() 
	{
		if(Build.VERSION.SDK_INT >20)
		{
			Intent intent2 = new Intent(Intent.ACTION_OPEN_DOCUMENT);
			intent2.addCategory(Intent.CATEGORY_OPENABLE);
			intent2.setType("image/*");
			startActivityForResult(intent2, 1);
		}
		else if (Build.VERSION.SDK_INT <19){
			Intent intent1 = new Intent(); 
			intent1.setType("image/*");
			intent1.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(intent1,
					"Select Picture"), SELECT_PICTURE);

		} else 
		{
			Intent intent2 = new Intent(Intent.ACTION_OPEN_DOCUMENT);
			intent2.addCategory(Intent.CATEGORY_OPENABLE);
			intent2.setType("image/*");
			startActivityForResult(intent2, 110);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("in activity result" +resultCode);

		if (resultCode == RESULT_OK) 
		{
			switch(requestCode) 
			{
			case SELECT_PICTURE:

				Uri selectedImageUri = data.getData();


				bitmapLength=BitmapFactory.decodeFile(getPath(selectedImageUri));
				//bitmapLength=new CompressImage(ParentRegistrationActivity.this).compressImage(picturePath);

				if(bitmapLength!=null)
				{
					imageByte=getPath(selectedImageUri);

					bitmapTobyte();
				}
				else
				{
					imageByte="";
					Toast.makeText(ParentRegistrationActivity.this, "Could not use this image. Please pick another one.", Toast.LENGTH_SHORT).show();
				}

				break;

			case 110:

				Uri selectedImageUri1 = data.getData();

				final int takeFlags = data.getFlags()
						& (Intent.FLAG_GRANT_READ_URI_PERMISSION
								| Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
				getContentResolver().takePersistableUriPermission(selectedImageUri1, takeFlags);



				//bitmapLength=new CompressImage(ParentRegistrationActivity.this).compressImage(picturePath1);
				bitmapLength=BitmapFactory.decodeFile(getPath(selectedImageUri1));
				if(bitmapLength!=null)
				{
					imageByte=getPath(selectedImageUri1);

					bitmapTobyte();
				}
				else
				{
					Toast.makeText(ParentRegistrationActivity.this, "Could not use this image. Please pick another one.", Toast.LENGTH_SHORT).show();
				}


				break;

			case 105:

				bitmapLength = (Bitmap) data.getExtras().get("data");  

				String path = Environment.getExternalStorageDirectory()+ "/profilo_"+".jpeg";
				File file =  new File(path);

				ExifInterface exif = null;
				try {
					exif = new ExifInterface(file.getAbsolutePath());
					int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
					Matrix matrix = new Matrix();
					switch (orientation) {
					case ExifInterface.ORIENTATION_ROTATE_270:

						matrix.postRotate(270);
						bitmapLength = Bitmap.createBitmap(bitmapLength, 0, 0, bitmapLength.getWidth(), bitmapLength.getHeight(), matrix, true);

						break;
					case ExifInterface.ORIENTATION_ROTATE_180:

						matrix.postRotate(180);
						bitmapLength = Bitmap.createBitmap(bitmapLength, 0, 0, bitmapLength.getWidth(), bitmapLength.getHeight(), matrix, true);

						break;
					case ExifInterface.ORIENTATION_ROTATE_90:

						matrix.postRotate(90);
						bitmapLength = Bitmap.createBitmap(bitmapLength, 0, 0, bitmapLength.getWidth(), bitmapLength.getHeight(), matrix, true);

						break;

					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if(bitmapLength.getHeight()>=bitmapLength.getWidth())
				{
					bitmapLength = Bitmap.createBitmap(bitmapLength, 0, bitmapLength.getHeight()/2 - bitmapLength.getWidth()/2, bitmapLength.getWidth(), bitmapLength.getWidth());
				}
				else
				{

					bitmapLength = Bitmap.createBitmap(
							bitmapLength, 
							bitmapLength.getWidth()/2 - bitmapLength.getHeight()/2,
							0,
							bitmapLength.getHeight(), 
							bitmapLength.getHeight()
							);


				}

				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				bitmapLength.compress(Bitmap.CompressFormat.JPEG, 70, bytes);

				try {
					file.createNewFile();
					FileOutputStream fo = new FileOutputStream(file);
					//5
					fo.write(bytes.toByteArray());
					fo.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if(bitmapLength!=null)
				{
					imageByte=path;

					bitmapTobyte();
				}
				else
				{
					Toast.makeText(ParentRegistrationActivity.this, "Could not use this image. Please pick another one.", Toast.LENGTH_SHORT).show();
				}

				/*String path = savingProfilePic((Bitmap)data.getExtras().get("data"));

				bitmapLength=BitmapFactory.decodeFile(path);
				if(bitmapLength!=null)
				{
					imageByte=path;

					bitmapTobyte();
				}
				else
				{
					Toast.makeText(ParentRegistrationActivity.this, "Could not use this image. Please pick another one.", Toast.LENGTH_SHORT).show();
				}
				 */
				break;

			case 1:

				final Uri selectedImage = data.getData();

				String wholeID = DocumentsContract.getDocumentId(selectedImage);

				// Split at colon, use second item in the array
				String id = wholeID.split(":")[1];

				String[] column = { MediaStore.Images.Media.DATA };     

				// where id is equal to             
				String sel = MediaStore.Images.Media._ID + "=?";

				Cursor cursor = getContentResolver().
						query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 
								column, sel, new String[]{ id }, null);

				String filePath2 = "";

				int columnIndex2 = cursor.getColumnIndex(column[0]);

				if (cursor.moveToFirst()) {
					filePath2 = cursor.getString(columnIndex2);
				}   

				cursor.close();


				//bitmapLength=new CompressImage(ParentRegistrationActivity.this).compressImage(filePath2);
				bitmapLength=BitmapFactory.decodeFile(filePath2);
				if(bitmapLength!=null)
				{
					imageByte=filePath2;

					bitmapTobyte();
				}
				else
				{
					Toast.makeText(ParentRegistrationActivity.this, "Could not use this image. Please pick another one.", Toast.LENGTH_SHORT).show();
				}

				break;

			case 200:
				String passCodeValue = data.getStringExtra("passCode");
				//passcode_editText.setText(passCodeValue);
				text_passcode.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
				//text_passcode.setTransformationMethod(PasswordTransformationMethod.getInstance());
				text_passcode.setAlpha(1f);
				hideKeyBoard();
				text_passcode.setText(passCodeValue);
				parentProfile.setPasscode(passCodeValue);
				passcode_switchView.setChecked(true);
				break;
			}
		}
		else
		{
			switch(requestCode) 
			{
			case 200:
				passcode_switchView.setChecked(false);
				break;
			}
		}
	}


	private void bitmapTobyte()
	{
		int bytes = bitmapLength.getByteCount();

		ByteBuffer buffer = ByteBuffer.allocate(bytes); //Create a new buffer
		bitmapLength.copyPixelsToBuffer(buffer); //Move the byte data to the buffer

		//Set image in circle

		if(bitmapLength!=null)
			profilePic_imageView.setImageBitmap(getRoundedShape(bitmapLength));
	}

	private  String getPath(Uri uri) 
	{
		// just some safety built in 
		if( uri == null ) 
		{
			// TODO perform some logging or show user feedback
			return null;
		}
		// try to retrieve the image from the media store first
		// this will only work for images selected from gallery
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if( cursor != null ){ 
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		}
		return uri.getPath();
	}

	//For Circular Image

	public Bitmap getRoundedShape(Bitmap scaleBitmapImage) 
	{


		if(scaleBitmapImage!=null)
		{
			int targetWidth = 100;
			if(SplashActivity.ScreenWidth >= 1000)
			{
				targetWidth = 150;

			}


			int targetHeight = targetWidth;

			Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, 
					targetHeight,Bitmap.Config.ARGB_8888);

			/*Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setAntiAlias(true);
		p.setFilterBitmap(true);*/

			Canvas canvas = new Canvas(targetBitmap);
			Path path = new Path();
			path.addCircle(((float) targetWidth - 1) / 2,
					((float) targetHeight - 1) / 2,
					(Math.min(((float) targetWidth), 
							((float) targetHeight)) / 2),
							Path.Direction.CCW);

			canvas.clipPath(path);
			Bitmap sourceBitmap = scaleBitmapImage;

			System.out.println("value of bitmap"+sourceBitmap);
			canvas.drawBitmap(sourceBitmap, 
					new Rect(0, 0, sourceBitmap.getWidth(),
							sourceBitmap.getHeight()), 
							new Rect(0, 0, targetWidth, targetHeight), null);

			return targetBitmap;
		}
		else
		{
			return BitmapFactory.decodeResource(getResources(), R.drawable.camera_icon);
		}
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		// TODO Auto-generated method stub

		if(event.getAction()==MotionEvent.ACTION_UP)
		{
			if(view instanceof AutoCompleteTextView)

				((AutoCompleteTextView) view).showDropDown();
		}

		else if(event.getAction()==MotionEvent.ACTION_DOWN)
			hideKeyBoard();

		return false;
	}

	private void hideKeyBoard()
	{
		try  
		{
			ParentRegistrationActivity.this.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

			InputMethodManager inputManager = (InputMethodManager) ParentRegistrationActivity.this
					.getSystemService(Context.INPUT_METHOD_SERVICE);

			inputManager.hideSoftInputFromWindow(ParentRegistrationActivity.this
					.getCurrentFocus().getWindowToken(), 0);

			ParentRegistrationActivity.this.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		} 
		catch (Exception e) 
		{

		}
	}

	private byte[] getImageforCitation(String name) {
		Bitmap bmp = null;
		byte[] byteArray = null;

		File imageFile = new File(name);
		if (imageFile.exists()) {

			bmp = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
			ByteArrayOutputStream stream = new ByteArrayOutputStream ();
			bmp.compress (Bitmap.CompressFormat.JPEG, 100, stream);
			byteArray = stream.toByteArray ();
		}
		return byteArray;
	}

	private String bitmapTobyte(byte[] bitmapLength)
	{
		return Base64.encodeToString(bitmapLength,
				Base64.DEFAULT);	
	}


	private void moveToLocationScreen() 
	{
		onTouchContinueButton=true;


		String textSelectedStreet = street_autoCompleteTextView.getText().toString();	

		/*	for(int i=0;i<localityList.getLocality().size();i++)
		{
			if(textSelectedStreet.trim().equalsIgnoreCase(localityList.getLocality().get(i).getLocalityName().trim()))
			{
				parentProfile.setLocalityID(localityList.getLocality().get(i).getLocalityID());	
				parentProfile.setStreetLocality(textSelectedStreet);
			}

		}*/
		parentProfile.setStreetLocality(textSelectedStreet);


		String textSelectedCity = city_autoCompleteTextView.getText().toString();	

		for(int i=0;i<cityList.getCity().size();i++)
		{
			if(textSelectedCity.trim().equalsIgnoreCase(cityList.getCity().get(i).getCityName().trim()))
			{
				parentProfile.setCityID(cityList.getCity().get(i).getCityID());	

				parentProfile.setCity(textSelectedCity);
			}
		}

		String textSelectedCountry = country_autoCompleteTextView.getText().toString();	
		for(int i=0;i<countryList.getCountry().size();i++)
		{
			if(textSelectedCountry.trim().equalsIgnoreCase(countryList.getCountry().get(i).getCountryName().trim()))
			{
				parentProfile.setCountryID(countryList.getCountry().get(i).getCountryID());	
				parentProfile.setCountry(textSelectedCountry);
			}
		}
		parentProfile.setFirstName(firstname_editText.getText().toString());
		parentProfile.setLastName(lastname_editText.getText().toString());
		parentProfile.setEmailAddress(email_editText.getText().toString());
		parentProfile.setPassword(password_editText.getText().toString());
		//parentProfile.setStreetLocality(street_editText.getText().toString());

		try {
			parentProfile.setDateOfBirth(dob_editText.getText().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			parentProfile.setDateOfBirth("");

			e.printStackTrace();
		}
		parentProfile.setContact(phone_editText.getText().toString());
		//if(text_passcode.getText().toString().equalsIgnoreCase("")||text_passcode.getText().toString().equalsIgnoreCase("Passcode"))
		if(text_passcode.getText().toString().equalsIgnoreCase("")||text_passcode.getText().toString().equalsIgnoreCase("Lock Your Profile"))

		{
			parentProfile.setPasscode("");
		}
		else
		{
			parentProfile.setPasscode(text_passcode.getText().toString());

		}
		parentProfile.setProfileImage(imageByte);

		String IMEI = "";
		try {
			TelephonyManager tManager = (TelephonyManager) ParentRegistrationActivity.this.getSystemService(Context.TELEPHONY_SERVICE);

			IMEI = tManager.getDeviceId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parentProfile.setDeviceID(IMEI);
		parentProfile.setDeviceToken(IMEI);
		String textSelectedAutolockTime = autolocktime_autoCompleteTextView.getText().toString();	

		for(int i=0;i<getAutolockTimeList.getGetAutolockTime().size();i++)
		{
			if(textSelectedAutolockTime.trim().equalsIgnoreCase(getAutolockTimeList.getGetAutolockTime().get(i).getTimeValue().trim()))
			{
				parentProfile.setAutolockID(getAutolockTimeList.getGetAutolockTime().get(i).getAutolockID());	
				parentProfile.setAutolockTime(textSelectedAutolockTime);
			}
		}

		if(father_textView.isChecked())
		{
			parentProfile.setGender("Male");
			parentProfile.setRelation(father_textView.getText().toString());
		}

		else if(mother_textView.isChecked())
		{
			parentProfile.setGender("Female");
			parentProfile.setRelation(mother_textView.getText().toString());
		}
		//here we will save selected data
		else if(guardian_textView.isChecked() && selectedIndexGuardian==0)
		{
			parentProfile.setGender("Male");
			parentProfile.setRelation(guardian_textView.getText().toString());
		}

		else if(guardian_textView.isChecked()&& selectedIndexGuardian==1)
		{
			parentProfile.setGender("Female");
			parentProfile.setRelation(guardian_textView.getText().toString());
		}

		/*	//here we will save selected data
		else if(guardian_textView.isChecked() && male_textView.isChecked())
		{
			parentProfile.setGender(male_textView.getText().toString());
			parentProfile.setRelation(guardian_textView.getText().toString());
		}

		else if(guardian_textView.isChecked() && female_textView.isChecked())
		{
			parentProfile.setGender(female_textView.getText().toString());
			parentProfile.setRelation(guardian_textView.getText().toString());
		}*/

		System.out.println("value of update"+parentUpdate);

		if(parentUpdate)
		{
			//Create UpdateParentProfile object...call webservice
			if(bitmapLength==null)
			{
				parentProfile.setProfileImage(imageByte);
			}
			else
			{
				parentProfile.setProfileImage(bitmapTobyte(getImageforCitation(imageByte)));
			}

			updateParentProfile  = new UpdateParentProfile();

			updateParentProfile.setParentID(parentId);
			updateParentProfile.setProfileImage(parentProfile.getProfileImage());
			updateParentProfile.setFirstName(parentProfile.getFirstName());
			updateParentProfile.setLastName(parentProfile.getLastName());
			updateParentProfile.setPassword(parentProfile.getPassword());
			updateParentProfile.setRelation(parentProfile.getRelation());
			updateParentProfile.setContact(parentProfile.getContact());
			updateParentProfile.setPasscode(parentProfile.getPasscode());
			updateParentProfile.setAutolockTime(parentProfile.getAutolockID()+ "");
			updateParentProfile.setDateOfBirth(parentProfile.getDateOfBirth());
			updateLocationByParentID  = new UpdateLocationByParentID();

			updateLocationByParentID.setParentID(parentId);
			updateLocationByParentID.setStreetLocality(parentProfile.getStreetLocality());
			updateLocationByParentID.setCity(parentProfile.getCityID()+"");
			updateLocationByParentID.setCountry(parentProfile.getCountryID()+"");
			new UpdateParentInformationOnServer().execute();	
		}
		else
		{
			/*Intent intent=new Intent(ParentRegistrationActivity.this, LocationActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("parentProfile", parentProfile);
			intent.putExtras(bundle);

			String parentInformation = gsonRegistration.toJson(parentProfile);
			sharePreferneceClass.setParentProfile(parentInformation);  

			startActivity(intent);
			ParentRegistrationActivity.this.finish();*/
			new RegisterParentTask().execute();
		}
	}

	private ProgressDialog progressDialog3=null;	

	private class UpdateParentInformationOnServer extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog3 = ProgressDialog.show(ParentRegistrationActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog3.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ParentRegistrationActivity.this))
			{
				ErrorCode = serviceMethod.getupdateLocation(updateLocationByParentID);
				ErrorCode = serviceMethod.getupdateParentProfile(updateParentProfile);
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
				if (progressDialog3.isShowing())
					progressDialog3.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			onTouchContinueButton=false;

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(ParentRegistrationActivity.this))
					new UpdateParentInformationOnServer().execute();
			}
			else
			{
				if(result==0)
				{
					Toast.makeText(ParentRegistrationActivity.this, "Parent Profile is updated.", Toast.LENGTH_SHORT).show();


					parentProfile = gsonRegistration.fromJson(sharePreferneceClass.getParentProfile(), ParentProfile.class);
					parentProfile.setParentID(updateParentProfile.getParentID());
					parentProfile.setFirstName(updateParentProfile.getFirstName());
					parentProfile.setLastName(updateParentProfile.getLastName());
					parentProfile.setPassword(updateParentProfile.getPassword());
					parentProfile.setContact(updateParentProfile.getContact());
					parentProfile.setPasscode(updateParentProfile.getPasscode());
					parentProfile.setProfileImage(updateParentProfile.getProfileImage());
					parentProfile.setStreetLocality(updateLocationByParentID.getStreetLocality());
					parentProfile.setCity(updateLocationByParentID.getCity());
					parentProfile.setCountry(updateLocationByParentID.getCountry());

					if(autolocktime_autoCompleteTextView!=null && autolocktime_autoCompleteTextView.getText()!=null)
					{
						parentProfile.setAutolockTime(autolocktime_autoCompleteTextView.getText().toString());
						parentProfile.setAutolockID(Integer.parseInt(updateParentProfile.getAutolockTime()));
					}
					else
					{
						parentProfile.setAutolockTime("");
						parentProfile.setAutolockID(0);
					}

					StaticVariables.currentParentName=updateParentProfile.getFirstName();
					if(autolocktime_autoCompleteTextView.getText().toString()!=null && autolocktime_autoCompleteTextView.getText().toString().trim().length()>0)
					{
						StaticVariables.autolockTimeValue=Integer.parseInt(autolocktime_autoCompleteTextView.getText().toString().split(" ")[0]);
						StaticVariables.timeforPasscode=StaticVariables.autolockTimeValue *(60*1000);
					}
					else
					{

						StaticVariables.forPasscode=true;

					}


					PassCode pcParent = new  PassCode();

					pcParent.setPassCodeType(0);
					pcParent.setProfileId(updateParentProfile.getParentID());
					pcParent.setPassCode(updateParentProfile.getPasscode());

					StaticVariables.parentPasscodeModel.setPassCode(updateParentProfile.getPasscode());
					StaticVariables.parentPasscodeModel.setPassCodeType(1);						
					StaticVariables.parentPasscodeModel.setProfileId(updateParentProfile.getParentID());

					PassCodeList passCodeList = gsonRegistration.fromJson(sharePreferneceClass.getPassCodeList(), PassCodeList.class);

					if(passCodeList==null)
					{
						passCodeList = new PassCodeList();
						passCodeList.getPasscodeList().add(pcParent);
					}
					else
					{

						for(int i=0;i<passCodeList.getPasscodeList().size();i++)
						{
							if(passCodeList.getPasscodeList().get(i).getProfileId()==pcParent.getProfileId())
							{
								passCodeList.getPasscodeList().get(i).setPassCode(pcParent.getPassCode());
							}
							else
							{
								passCodeList.getPasscodeList().add(pcParent);
							}
						}

					}

					String passcodeListString = gsonRegistration.toJson(passCodeList);		
					sharePreferneceClass.setPassCodeList(passcodeListString);

					String parentInformation = gsonRegistration.toJson(parentProfile);
					sharePreferneceClass.setParentProfile(parentInformation);  
					ParentRegistrationActivity.this.finish();
				}
				else
				{
					getError();
				}
			}	
		}	
	}

	class ValidateText implements AutoCompleteTextView.Validator
	{
		private ArrayList<String> arrayObj;

		private int autoCompleteTextView;

		public ValidateText(ArrayList<String> arrayOfObjectsToValidate, int autoCompleteTextView) {
			this.arrayObj = arrayOfObjectsToValidate;
			this.autoCompleteTextView=autoCompleteTextView;
		}
		public boolean isValid(CharSequence text) {
			if (arrayObj.contains(text.toString())) {
				setFocuesToNextView();
				System.out.println("in validation caheck");
				return true;
			}
			return false;
		}

		private void setFocuesToNextView() {
			// TODO Auto-generated method stub
			if(autoCompleteTextView==0)
			{
				autolocktime_autoCompleteTextView.setFocusable(true);
			}
			else
			{
				//passcode_editText.setFocusable(true);
			}
		}

		public CharSequence fixText(CharSequence invalidText) {
			Log.v("Test", "Returning fixed text");

			System.out.println(invalidText+"  text in change");
			return "";
		}
	}


	/**
	 * Background Async task to load user profile picture from url
	 * */
	private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {


		public LoadProfileImage() {

		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = StaticVariables.modelFacebook.getUrl();
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				//Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {

			if(result!=null)
			{
				profilePic_imageView.setImageBitmap(getRoundedShape(result));	
				try {
					imageByte = bitmapStoreInSDCard(result, Environment.getExternalStorageDirectory()+ "/profilo_"+".jpeg");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}


		private String bitmapStoreInSDCard(Bitmap bmp,
				String nameOfImageAlongWithPath) throws Exception {
			System.out.println("path" +nameOfImageAlongWithPath);
			File sd = new File(nameOfImageAlongWithPath);

			if (!sd.exists()) {
				sd.getParentFile().mkdirs();

				if (sd.exists()) {
					sd.delete();
				}

			}

			if (sd.exists()) {
				sd.delete();
			}

			FileOutputStream fos = new FileOutputStream(sd);

			bmp.compress(Bitmap.CompressFormat.JPEG, 70 /* ignored for JPEG */,
					fos);

			fos.close();
			fos.flush();
			if(bmp!=null)
			{
				bmp.recycle();
				bmp=null;
			}

			return nameOfImageAlongWithPath;

		}
	}

	public void showAlertPasscode(String title, String message)
	{
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

		alertBuilder.setTitle(title);
		alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
		alertBuilder.setMessage(message);
		alertBuilder.setPositiveButton(" Submit ", new DialogInterface.OnClickListener() 
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				moveToLocationScreen();
			}
		});	

		alertBuilder.setNegativeButton(" LOCK ", new DialogInterface.OnClickListener() 
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				isLockDialogClicked=true;
				Intent intent=new Intent(ParentRegistrationActivity.this, AddPasscordActivity.class);
				Bundle bundle =new Bundle();
				//text_passcode.setText("Passcode");
				text_passcode.setText("Lock Your Profile");
				bundle.putString("passCode","");
				intent.putExtras(bundle);
				startActivityForResult(intent, 200);

			}
		});	
		alertBuilder.show();
	}



	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		if(!parentUpdate)
		{

			Intent intent=new Intent(ParentRegistrationActivity.this, LoginActivity.class);
			startActivity(intent);
			ParentRegistrationActivity.this.finish();

		}
		else
		{
			super.onBackPressed();

		}
	}

	private ProgressDialog progressDialogCity=null;	
	private ProgressDialog progressDialogCountry=null;	
	private ProgressDialog progressDialogLocality=null;	


	private class GetListOfField extends AsyncTask<Void, Void, Integer>
	{
		private String getWebservice;
		private int countryId ;

		public GetListOfField(String getWebservice, int countryId)
		{
			// TODO Auto-generated constructor stub 
			this.getWebservice = getWebservice;
			this.countryId = countryId;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(getWebservice.equalsIgnoreCase("country"))
			{
				progressDialogCountry = ProgressDialog.show(ParentRegistrationActivity.this, "", StaticVariables.progressBarText, false);
				progressDialogCountry.setCancelable(false);
			}
			else if(getWebservice.equalsIgnoreCase("locality"))
			{
				progressDialogLocality = ProgressDialog.show(ParentRegistrationActivity.this, "", StaticVariables.progressBarText, false);
				progressDialogLocality.setCancelable(false);
			}
			else
			{
				progressDialogCity = ProgressDialog.show(ParentRegistrationActivity.this, "", StaticVariables.progressBarText, false);
				progressDialogCity.setCancelable(false);
			}
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ParentRegistrationActivity.this))
			{
				if(getWebservice.equalsIgnoreCase("country"))
				{
					countryList = serviceMethod.getCountry();

					if(countryList!=null)
					{
						countryStringList = getCountryStringList();
					}
					else
					{

					}
				}
				else if(getWebservice.equalsIgnoreCase("locality"))
				{
					localityList = serviceMethod.getLocality(countryId);

					if(localityList!=null)
					{
						localityStringList = getlocalityStringList();
					}
					else
					{

					}
				}

				else
				{
					cityList = serviceMethod.getCity(countryId);

					if(cityList!=null)
					{
						cityStringList = getCityStringList();
					}
					else
					{

					}
				}
			}
			else
			{
				ErrorCode=-1;
			}
			return ErrorCode;
		}

		private ArrayList<String> getCountryStringList() {
			// TODO Auto-generated method stub
			countryStringList=new ArrayList<String>();

			for(int i=0;i<countryList.getCountry().size();i++)
			{
				countryStringList.add(countryList.getCountry().get(i).getCountryName());
			}

			return countryStringList;
		}

		private ArrayList<String> getCityStringList() {
			// TODO Auto-gen																																																																																																														erated method stub

			cityStringList=new ArrayList<String>();

			for(int i=0;i<cityList.getCity().size();i++)
			{
				cityStringList.add(cityList.getCity().get(i).getCityName()); 
			}

			return cityStringList;
		}

		private ArrayList<String> getlocalityStringList() {
			// TODO Auto-gen																																																																																																														erated method stub

			localityStringList=new ArrayList<String>();

			for(int i=0;i<localityList.getLocality().size();i++)
			{
				localityStringList.add(localityList.getLocality().get(i).getLocalityName()); 
			}

			return localityStringList;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {
				if(getWebservice.equalsIgnoreCase("country"))
				{
					if (progressDialogCountry.isShowing())
						progressDialogCountry.cancel();
				}
				else if(getWebservice.equalsIgnoreCase("locality"))
				{
					if (progressDialogLocality.isShowing())
						progressDialogLocality.cancel();

				}
				else
				{

					if (progressDialogCity.isShowing())
						progressDialogCity.cancel();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				new ShowMessages(ParentRegistrationActivity.this).showToastMessage("Please check your network connection");
			}
			else
			{
				if(getWebservice.equalsIgnoreCase("country"))
				{
					if(countryList!=null)
					{
						if(countryStringList!=null && countryStringList.size()>0)
						{
							AutoCompleteAdapter   checkUpAdapter = new AutoCompleteAdapter(ParentRegistrationActivity.this, R.layout.list_item, R.id.item,countryStringList);
							if(!parentUpdate)
								country_autoCompleteTextView.setText(countryStringList.get(0));

							country_autoCompleteTextView.setAdapter(checkUpAdapter);
							country_autoCompleteTextView.setValidator(new ValidateText(countryStringList,0));



						}
						else
						{

						}
					}
					else
					{	
						getError();
						new GetListOfField("country",0).execute();
					}	
				}

				else if(getWebservice.equalsIgnoreCase("locality"))
				{
					if(localityList!=null)
					{
						if(localityStringList!=null && localityStringList.size()>0)
						{
							AutoCompleteAdapter checkUpAdapter = new AutoCompleteAdapter(ParentRegistrationActivity.this, R.layout.list_item, R.id.item,localityStringList);
							street_autoCompleteTextView.setAdapter(checkUpAdapter);
							street_autoCompleteTextView.setValidator(new ValidateText(localityStringList,1));
						}
					}
					else
					{	
						getError();
						new GetListOfField("locality",countryId).execute();	
					}	
				}	
				else
				{
					if(cityList!=null)
					{
						if(cityStringList!=null && cityStringList.size()>0)
						{
							AutoCompleteAdapter checkUpAdapter = new AutoCompleteAdapter(ParentRegistrationActivity.this, R.layout.list_item, R.id.item,cityStringList);
							city_autoCompleteTextView.setAdapter(checkUpAdapter);
							city_autoCompleteTextView.setValidator(new ValidateText(cityStringList,1));
						}
					}
					else
					{	
						getError();
						new GetListOfField("city",countryId).execute();	
					}	
				}	
			}
		}	
	}

	private ProgressDialog progressDialogRegister;

	private class RegisterParentTask extends AsyncTask<Void, Void, Integer>
	{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialogRegister = ProgressDialog.show(ParentRegistrationActivity.this, "", StaticVariables.progressBarText, false);
			progressDialogRegister.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub

			int parentId=0;

			if(checkNetwork.checkNetworkConnection(ParentRegistrationActivity.this))
			{
				parentId=-1;

				try {
					String path=parentProfile.getProfileImage();

					if(path!=null)
					{
						parentProfile.setProfileImage(bitmapTobyte(getImageforCitation(path)));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				parentId=serviceMethod.createParentProfile(parentProfile);

				if(parentId!=0)
				{
					parentProfile.setParentID(parentId);
				}
				else
				{

				}
			}
			return parentId;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result); 

			try {
				if (progressDialogRegister.isShowing())
					progressDialogRegister.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			onTouchContinueButton=false;

			if(result==0)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(ParentRegistrationActivity.this))
					new RegisterParentTask().execute();
			}
			else
			{
				if(result!=-1)
				{
					social.parentRegistrationFacebookLog();
					social.parentRegistrationGoogleAnalyticsLog();
					String parentInformation = gsonRegistration.toJson(parentProfile);
					sharePreferneceClass.setParentProfile(parentInformation);  

					Intent intent=new Intent(ParentRegistrationActivity.this, ConfirmationActivity.class);
					// pass profile email id and profile id
					Bundle bundle = new Bundle();
					bundle.putString("EmailAddress",parentProfile.getEmailAddress());
					bundle.putInt("ParentID", parentProfile.getParentID());
					bundle.putString("Contact",parentProfile.getContact());

					intent.putExtras(bundle);
					startActivity(intent);
					ParentRegistrationActivity.this.finish();
					GettingLattitude.lat=0.0;
					GettingLattitude.lng=0.0;
				}
				else
				{
					getError();
				}
			}
		}
	}


	int selectedIndexGuardian=0;
	public Dialog onCreateDialogSingleChoice()
	{
		//Initialize the Alert Dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		//Source of the data in the DIalog
		CharSequence[] array = {"Male","Female"};

		// Set the dialog title
		builder.setTitle("Select Guardian")
		.setSingleChoiceItems(array,selectedIndexGuardian, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				selectedIndexGuardian=which;
			}
		})

		// Set the action buttons
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// User clicked OK, so save the result somewhere
				// or return them to the component that opened the dialog
				dialog.dismiss();
			}
		})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
				//selectedIndexGuardian=0;
			}
		});
		builder.setCancelable(false);
		return builder.create();
	}



	/**
	 * 
	 */
	private void validateDataOnSubmit() {
		// TODO Auto-generated method stub
		if(!checkValidation.isNotNullOrBlank(firstname_editText.getText().toString()) && !checkValidation.isNotNullOrBlank(lastname_editText.getText().toString()) && !checkValidation.isNotNullOrBlank(email_editText.getText().toString()) 
				&& !checkValidation.isNotNullOrBlank(password_editText.getText().toString()) /*&& !checkValidation.isNotNullOrBlank(dob_editText.getText().toString())*/ && !checkValidation.isNotNullOrBlank(phone_editText.getText().toString()))
		{
			showMessage.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");
		}
		else
		{
			if(!checkValidation.isNotNullOrBlank(firstname_editText.getText().toString()))
				showMessage.showAlert("Invalid Data", "Did you type the name right?\nNote: You cant use smileys or special characters.");
			/*	else if(!checkValidation.validateFirstName(firstname_editText.getText().toString()))
				showMessage.showAlert("Invalid Data", "Did you type the name right?\nNote: You cant use smileys or special characters.");
			 */
			else if(!checkValidation.isNotNullOrBlank(lastname_editText.getText().toString()))
				showMessage.showAlert("Invalid Data", "Did you type the name right?\nNote: You cant use smileys or special characters.");
			/*	else if(!checkValidation.validateLastName(lastname_editText.getText().toString()))
				showMessage.showAlert("Invalid Data", "Did you type the name right?\nNote: You cant use smileys or special characters.");
			 */else if(!checkValidation.isNotNullOrBlank(email_editText.getText().toString()))
				 showMessage.showAlert("Invalid Email ID", "Your email ID may not be correct. Please check.");
			 else if(!checkValidation.isValidEmail(email_editText.getText().toString()))
				 showMessage.showAlert("Invalid Email ID", "Your email ID may not be correct. Please check.");
			 else if(!checkValidation.isNotNullOrBlank(password_editText.getText().toString()))
				 showMessage.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");

			//else if(!checkValidation.isAgeValid(yearDOB, monthDOB, dayDOB, 18))
			//showMessage.showAlert("Alert", "You must be atleast 18 years of age to use this app");
			 else if(!checkValidation.isNotNullOrBlank(phone_editText.getText().toString()))
				 showMessage.showAlert("Incorrect Number", "Your phone number should have 10 digits. Please check.");
			 else if(!checkValidation.isValidPhoneNo(phone_editText.getText().toString()))
				 showMessage.showAlert("Incorrect Number", "Your phone number should have 10 digits. Please check.");
			 else if(!checkValidation.isNotNullOrBlank(country_autoCompleteTextView.getText().toString()))
			 {
				 showMessage.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");
				 country_autoCompleteTextView.requestFocus();
			 }
			/*else if(!checkValidation.isNotNullOrBlank(city_autoCompleteTextView.getText().toString()) && !checkValidation.isNotNullOrBlank(country_autoCompleteTextView.getText().toString())
					&& !validation.isNotNullOrBlank(street_editText.getText().toString()))*/
			 else if(!checkValidation.isNotNullOrBlank(city_autoCompleteTextView.getText().toString()))
			 {
				 showMessage.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");
				 city_autoCompleteTextView.requestFocus();
			 }
			 else if(!checkValidation.isNotNullOrBlank(street_autoCompleteTextView.getText().toString()))
			 {
				 showMessage.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");
				 street_autoCompleteTextView.requestFocus();
			 }


			/* else if(!checkValidation.isNotNullOrBlank(street_editText.getText().toString()))
			 {
				 showMessage.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");
				 street_editText.requestFocus();
			 }*/

			 else if(checkValidation.isNotNullOrBlank(dob_editText.getText().toString()))
			 {
				 //showMessage.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");
				 if(!checkValidation.isAgeValid(yearDOB, monthDOB, dayDOB, 18))
					 showMessage.showAlert("Alert", "You must be atleast 18 years of age to use this app");
				 else
				 {
					 // if(layout_Pass_AutoLock.getVisibility()==View.VISIBLE)
					 if(passcode_switchView.isChecked())
					 {
						 //if(!checkValidation.isNotNullOrBlank(passcode_editText.getText().toString()))
						 if(!checkValidation.isNotNullOrBlank(text_passcode.getText().toString()))
							 showMessage.showAlert("Alert", "Please enter a passcode before you proceed.");
						 /* else if(!checkValidation.isNotNullOrBlank(autolocktime_autoCompleteTextView.getText().toString()))
							 showMessage.showAlert("Alert", "Please enter autolocktime before you proceed.");*/
						 else
						 {
							 moveToLocationScreen();
						 }
					 }
					 else
					 {
						 showAlertPasscode("Alert", "Locking your profile helps secure access to different profiles within the app. Are you sure don't want to set up a profile lock?");
						 //moveToLocationScreen();
					 }
				 }
			 }
			 else if(!checkValidation.isNotNullOrBlank(dob_editText.getText().toString())&& countDialog==0)
			 {

				 AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ParentRegistrationActivity.this);

				 alertBuilder.setTitle("Alert");
				 alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
				 alertBuilder.setMessage("Sharing DOB is optional but to use PiNWi as a parent you need be above legal age.By continuing, you declare you are above 18 yrs. ");

				 alertBuilder.setPositiveButton(" GOT IT ", new DialogInterface.OnClickListener() {

					 @Override
					 public void onClick(final DialogInterface dialog, int which) {
						 dialog.dismiss();
						 countDialog=1;
						 validateDataOnSubmit();
					 }
				 });
				 alertBuilder.setNegativeButton(" SET ", new DialogInterface.OnClickListener() {

					 @Override
					 public void onClick(DialogInterface dialog, int which)
					 {
						 dialog.dismiss();
						 datePickerDialog.show();
					 }
				 });

				 alertBuilder.show();

			 }

			 else
			 {
				 // if(layout_Pass_AutoLock.getVisibility()==View.VISIBLE)
				 if(passcode_switchView.isChecked())

				 {
					 // if(!checkValidation.isNotNullOrBlank(passcode_editText.getText().toString()))
					 if(!checkValidation.isNotNullOrBlank(text_passcode.getText().toString()))
						 showMessage.showAlert("Alert", "Please enter a passcode before you proceed.");
					 /* else if(!checkValidation.isNotNullOrBlank(autolocktime_autoCompleteTextView.getText().toString()))
						 showMessage.showAlert("Alert", "Please enter autolocktime before you proceed.");*/
					 else
					 {
						 moveToLocationScreen();
					 }
				 }
				 else
				 {
					 showAlertPasscode("Alert", "Locking your profile helps secure access to different profiles within the app. Are you sure don't want to set up a profile lock?");
					 //moveToLocationScreen();
				 }
			 }
		}
	}
}
