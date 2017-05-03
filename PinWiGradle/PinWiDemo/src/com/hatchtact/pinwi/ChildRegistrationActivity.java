package com.hatchtact.pinwi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
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
import com.hatchtact.pinwi.child.postcard.CompressImage;
import com.hatchtact.pinwi.classmodel.ChildModel;
import com.hatchtact.pinwi.classmodel.ChildProfile;
import com.hatchtact.pinwi.classmodel.GetAutolockTimeList;
import com.hatchtact.pinwi.classmodel.GetChildDetails;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.classmodel.PassCode;
import com.hatchtact.pinwi.classmodel.PassCodeList;
import com.hatchtact.pinwi.classmodel.SchoolList;
import com.hatchtact.pinwi.classmodel.UpdateChildProfile;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.CustomLoader;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;
import com.hatchtact.pinwi.utility.Validation;
import com.hatchtact.pinwi.view.AutoCompleteAdapter;
import com.hatchtact.pinwi.view.NamesAdapter;

@SuppressLint("NewApi")
public class ChildRegistrationActivity extends MainActionBarActivity implements OnTouchListener
{
	private EditText childFname_editText=null;
	private EditText childLname_editText=null;
	private EditText childnickname_editText=null;
	private EditText childdob_editText=null;
	private EditText childpasscode_editText=null;
	private AutoCompleteTextView childautolocktime_autoCompleteTextView=null;
	private Button continue_button=null;
	private Button button_new_continue_Child;
	private ImageView childprofilePic_imageView=null;
	private ImageView childdob_button=null;
	private TextView addanotherchild_textView=null;
	private ImageView addanotherchild_imageView=null;
	private Switch passcodechild_switch=null;
	private LinearLayout layout_Pass_AutoLockChild=null;

	private AutoCompleteTextView school_autoCompleteTextView=null;

	private DatePickerDialog datePickerDialog=null;

	private SimpleDateFormat dateFormatter=null;

	private int yearDOB,monthDOB,dayDOB;

	private static final int SELECT_PICTURE = 100;

	private String imageByte=null;

	private Bitmap	bitmapLength=null;
	private Gson gsonRegistration=null;

	private TypeFace typeFace=null;
	private Validation checkValidation=null;
	private ShowMessages showMessage=null;
	private ChildProfile childProfile=null;

	private SchoolList schoolList=null;

	private boolean onTouchChildAddButton=false;

	private	ServiceMethod serviceMethod=null;

	private int parentId=0;

	private ArrayList<String> schoolStringList=new ArrayList<String>();

	private CheckNetwork checkNetwork=null;

	private GetAutolockTimeList getAutolockTimeList=null;
	private ArrayList<String> getAutolockTimeStringList=new ArrayList<String>();

	private boolean onTouchContinueButton=false;

	private RadioGroup radioGroup_child=null;
	private RadioButton boy_textView=null;
	private RadioButton girl_textView=null;
	private SharePreferenceClass sharePref=null;

	private boolean childUpdate=false;
	private boolean addNewChild=false;

	private int childID;
	private TextView text_ProfileText;
	private TextView textchild_passcode;
	private boolean isPasscodeTouched=false;//need to use this flag
	private TextView header_text,header_help;
	private CustomLoader customProgressLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		screenName="Child Profile Setup";

		super.onCreate(savedInstanceState);

		setContentView(R.layout.child_registration_activity);
		customProgressLoader=new CustomLoader(ChildRegistrationActivity.this);

		isLockDialogClicked=false;
		Bundle bundle = getIntent().getExtras();

		try {
			childUpdate = bundle.getBoolean("ToChildScreen");
			childID=bundle.getInt("childId");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			addNewChild=bundle.getBoolean("ToChildScreenFromAdd");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		header_text=(TextView) findViewById(R.id.header_text);
		header_help=(TextView) findViewById(R.id.header_help);
		header_text.setText(screenName);


		header_help.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentAboutUs =new Intent(ChildRegistrationActivity.this, ActivityAboutUS.class);
				startActivity(intentAboutUs);
				StaticVariables.webUrl="http://pinwi.in/contactus.aspx?4";
			}
		});
		checkValidation = new Validation();
		childProfile=new ChildProfile();
		serviceMethod = new ServiceMethod();
		checkNetwork=new CheckNetwork();
		showMessage = new ShowMessages(ChildRegistrationActivity.this);
		typeFace=new TypeFace(ChildRegistrationActivity.this);
		dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

		onTouchContinueButton=false;

		schoolList=new SchoolList();
		sharePref=new SharePreferenceClass(this);
		gsonRegistration = new GsonBuilder().create();

		getAutolockTimeList=new GetAutolockTimeList();

		ParentProfile parentCompleteInformation = gsonRegistration.fromJson(sharePref.getParentProfile(), ParentProfile.class);
		parentId=parentCompleteInformation.getParentID();

		childFname_editText=(EditText) findViewById(R.id.child_Firstname);
		childLname_editText=(EditText) findViewById(R.id.child_Lastname);
		childnickname_editText=(EditText) findViewById(R.id.text_nick_name);
		childdob_editText=(EditText) findViewById(R.id.text_DOB_child);
		childdob_button=(ImageView) findViewById(R.id.image_calender_child);
		childpasscode_editText=(EditText) findViewById(R.id.text_passcodeChild);
		childautolocktime_autoCompleteTextView=(AutoCompleteTextView) findViewById(R.id.text_AutoLockTimeChild);
		continue_button=(Button) findViewById(R.id.button_continueChild);
		button_new_continue_Child=(Button) findViewById(R.id.button_new_continue_Child);
		if(sharePref.getIsLogin()) {
			button_new_continue_Child.setVisibility(View.GONE);
		}
		else
		{
			if(sharePref.isChildAdded())
				button_new_continue_Child.setVisibility(View.VISIBLE);
			else
			{
				button_new_continue_Child.setVisibility(View.GONE);
			}
		}
		childprofilePic_imageView=(ImageView) findViewById(R.id.image_camera_child);
		addanotherchild_imageView=(ImageView) findViewById(R.id.image_addChild);
		addanotherchild_textView=(TextView) findViewById(R.id.text_addchild);
		radioGroup_child=(RadioGroup) findViewById(R.id.layout_boy_girl);
		passcodechild_switch=(Switch) findViewById(R.id.passcodechild_switch);
		passcodechild_switch.setChecked(false);
		layout_Pass_AutoLockChild=(LinearLayout) findViewById(R.id.layout_Pass_AutoLockChild);
		text_ProfileText = (TextView) findViewById(R.id.text_ProfileText);
		textchild_passcode = (TextView) findViewById(R.id.textchild_passcode);


		boy_textView=(RadioButton) findViewById(R.id.text_boy);
		girl_textView=(RadioButton) findViewById(R.id.text_girl);
		school_autoCompleteTextView=(AutoCompleteTextView) findViewById(R.id.text_schoolname);
		school_autoCompleteTextView.setFocusable(true);
		school_autoCompleteTextView.setFocusableInTouchMode(true);
		/*school_autoCompleteTextView.setSingleLine(false);
		school_autoCompleteTextView.setMaxLines(2);*/
		boy_textView.setChecked(true);

		typeFace.setTypefaceLight(childFname_editText);
		typeFace.setTypefaceLight(childLname_editText);
		typeFace.setTypefaceLight(childnickname_editText);
		typeFace.setTypefaceLight(childdob_editText);
		typeFace.setTypefaceLight(childpasscode_editText);
		typeFace.setTypefaceLight(childautolocktime_autoCompleteTextView);
		typeFace.setTypefaceRegular(addanotherchild_textView);
		typeFace.setTypefaceLight(school_autoCompleteTextView);
		typeFace.setTypefaceRegular(continue_button);
		typeFace.setTypefaceRegular(button_new_continue_Child);
		typeFace.setTypefaceLight(text_ProfileText);
		typeFace.setTypefaceLight(passcodechild_switch);
		typeFace.setTypefaceLight(textchild_passcode);
		typeFace.setTypefaceLight(boy_textView);
		typeFace.setTypefaceLight(girl_textView);


		setDateTimeField();

		passcodechild_switch.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(passcodechild_switch.isChecked())
				{
					if(!isLockDialogClicked)
					{

						sharePref.setIsPasscodeChildSet(isChecked);
						//layout_Pass_AutoLockChild.setVisibility(View.VISIBLE);
						if(!isPasscodeTouched)
						{
							Intent intent=new Intent(ChildRegistrationActivity.this, AddPasscordActivity.class);
							Bundle bundle =new Bundle();
							//textchild_passcode.setText("Passcode");
							textchild_passcode.setText("Lock Child Profile");

							bundle.putString("passCode","");
							intent.putExtras(bundle);
							startActivityForResult(intent, 200);
						}
						layout_Pass_AutoLockChild.setVisibility(View.GONE);

						isPasscodeTouched=false;
					}
					else
					{
						isLockDialogClicked=false;
					}
				}
				else
				{
					sharePref.setIsPasscodeChildSet(isChecked);
					layout_Pass_AutoLockChild.setVisibility(View.GONE);
					childpasscode_editText.setText("");
					textchild_passcode.setInputType(InputType.TYPE_CLASS_TEXT);
					textchild_passcode.setAlpha(.7f);

					//textchild_passcode.setText("Passcode");
					textchild_passcode.setText("Lock Child Profile");

					childautolocktime_autoCompleteTextView.setText("");
				}
			}
		});

		continue_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if(!onTouchChildAddButton)
				{
					onTouchChildAddButton=true;
					addChild(false);
				}
			}
		});
		button_new_continue_Child.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showAlertChildContinueNext("Confirmation","Are you sure you want to continue without adding new child profiles?");

			}
		});

		childdob_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				datePickerDialog.show();
			}
		});

		childautolocktime_autoCompleteTextView.setOnTouchListener(this);

		childautolocktime_autoCompleteTextView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub

			}
		});

		childprofilePic_imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cameraClickDialog();
			}
		});

		radioGroup_child.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId==R.id.text_boy)
				{
					boy_textView.setChecked(true);
				}

				if(checkedId==R.id.text_girl)
				{
					girl_textView.setChecked(true);
				}
			}
		});

		/*school_autoCompleteTextView.setOnTouchListener(this);

		school_autoCompleteTextView.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override 
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

			}
		});*/

		school_autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					hideKeyBoard();

					return true;
				}
				else
				{
					hideKeyBoard();
					return true;
				}
			}
		});


		school_autoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					// parkingAutocomplete.requestFocus();
					//  parkingAutocomplete.setFocusable(true);




					school_autoCompleteTextView.showDropDown();

				} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
					school_autoCompleteTextView.setText("");
					school_autoCompleteTextView.requestFocus();
					((InputMethodManager) ChildRegistrationActivity.this
							.getSystemService(ChildRegistrationActivity.INPUT_METHOD_SERVICE))
							.toggleSoftInput(
									InputMethodManager.SHOW_FORCED,
									InputMethodManager.HIDE_IMPLICIT_ONLY);
				}
				return true;
			}
		});
		//school_autoCompleteTextView.setOnTouchListener(this);

		school_autoCompleteTextView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub

				//school_autoCompleteTextView.setText(schoolList.get);
				school_autoCompleteTextView.setSelection(school_autoCompleteTextView.getText().toString().trim().length());

				hideKeyBoard();
			}
		});


		addanotherchild_imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!onTouchChildAddButton)
				{
					onTouchChildAddButton=true;
					addChild(true);
				}
			}
		});

		addanotherchild_textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!onTouchChildAddButton)
				{
					onTouchChildAddButton=true;
					addChild(true);
				}
			}
		});

		textchild_passcode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(passcodechild_switch.isChecked())
				{
					Intent intent=new Intent(ChildRegistrationActivity.this, AddPasscordActivity.class);
					Bundle bundle =new Bundle();
					bundle.putString("passCode", textchild_passcode.getText().toString());
					intent.putExtras(bundle);
					startActivityForResult(intent, 200);
				}
			}
		});


		if(addNewChild)
		{
			continue_button.setText("Save");
			addanotherchild_imageView.setVisibility(View.GONE);
			addanotherchild_textView.setVisibility(View.GONE);
		}

		if(childUpdate ||addNewChild )
		{
			if(childUpdate)
			{
				setDataFromServer();
			}


			new GetListOfSchool(parentId).execute();

			new GetListOfAutoLockTimeField().execute();
		}
		else
		{
			/*sharePref.setParentIsRegistered(true);	
			sharePref.setCurrentScreen(1);*/
			showAlertChildRegistration("Setting up Child Profiles", "Quickly set up profiles for your children in the next section. This is important to ensure you get individual views and reports for each child. ");

		}
	}


	private void setDataFromServer()
	{
		// TODO Auto-generated method stub
		continue_button.setText("Save");
		button_new_continue_Child.setVisibility(View.GONE);
		childdob_button.setEnabled(false);
		//childdob_editText.setEnabled(false);
		childdob_editText.setEnabled(true);
		radioGroup_child.setEnabled(false);
		boy_textView.setEnabled(false);
		girl_textView.setEnabled(false);

		addanotherchild_imageView.setVisibility(View.GONE);
		addanotherchild_textView.setVisibility(View.GONE);

		new GetChildDetailFromServer(childID).execute();
	}

	private UpdateChildProfile updateChildProfile;

	private GetChildDetails getChildDetail=null;

	//	private ProgressDialog progressDialog2=null;
	private int countDialog=0;

	private class GetChildDetailFromServer extends AsyncTask<Void, Void, Integer>
	{
		private int childId;


		public GetChildDetailFromServer(int childId)
		{
			// TODO Auto-generated constructor stub 
			this.childId = childId;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			if(customProgressLoader!=null)
			{
				customProgressLoader.showProgressBar();
			}
			/*progressDialog2 = ProgressDialog.show(ChildRegistrationActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog2.setCancelable(false);*/
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ChildRegistrationActivity.this))
			{
				getChildDetail = serviceMethod.getChildDetails(childId);
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
				customProgressLoader.dismissProgressBar();
			/*	if (progressDialog2.isShowing())
					progressDialog2.cancel();*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				//showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(ChildRegistrationActivity.this))
					new GetChildDetailFromServer(parentId).execute();
			}
			else
			{
				if(getChildDetail!=null)
				{
					if(getChildDetail.getFirstName()!=null && getChildDetail.getFirstName().trim().length()>0)
						childFname_editText.setText(getChildDetail.getFirstName());

					if(getChildDetail.getLastName()!=null && getChildDetail.getLastName().trim().length()>0)
						childLname_editText.setText(getChildDetail.getLastName());

					if(getChildDetail.getNickName()!=null && getChildDetail.getNickName().trim().length()>0)
						childnickname_editText.setText(getChildDetail.getNickName());

					if(getChildDetail.getDateOfBirth()!=null && getChildDetail.getDateOfBirth().trim().length()>0)
					{
						if(!getChildDetail.getDateOfBirth().trim().equalsIgnoreCase("01/01/1900"))
							childdob_editText.setText(getChildDetail.getDateOfBirth());
						childProfile.setDateOfBirth(getChildDetail.getDateOfBirth());
					}

					if(getChildDetail.getGender()!=null && getChildDetail.getGender().trim().length()>0)
					{
						if(getChildDetail.getGender().toString().equalsIgnoreCase("Male"))
						{
							childProfile.setGender("Male");
							boy_textView.setChecked(true);
						}
						else if(getChildDetail.getGender().toString().equalsIgnoreCase("Female"))
						{
							childProfile.setGender("Female");
							girl_textView.setChecked(true);
						}
					}

					if(getChildDetail.getProfileImage()!=null && getChildDetail.getProfileImage().length()>0)
					{

						imageByte = getChildDetail.getProfileImage();

						if(getChildDetail.getProfileImage()!=null && getChildDetail.getProfileImage().trim().length()>100)
						{
							byte[] imageByteRefill=Base64.decode(getChildDetail.getProfileImage(), 0);
							if(imageByteRefill!=null)
							{
								childprofilePic_imageView.setImageBitmap(getRoundedShape(BitmapFactory.decodeByteArray(imageByteRefill, 0, imageByteRefill.length)));

							}
						}


					}	

					/*if(getChildDetail.getSchoolID()!=null && getChildDetail.getSchoolID().trim().length()>0)
					{

					}*/
					if(getChildDetail.getSchoolName()!=null && getChildDetail.getSchoolName().trim().length()>0)
					{
						school_autoCompleteTextView.setText(getChildDetail.getSchoolName());
						childProfile.setSchoolID(Integer.parseInt(getChildDetail.getSchoolID()));
					}

					boolean isPasscodeSet=false;
					if(StaticVariables.childPasscodeList!=null)
					{
						for(int i=0;i<StaticVariables.childPasscodeList.getPasscodeList().size();i++)
						{
							if(StaticVariables.childPasscodeList.getPasscodeList().get(i).getProfileId()==childID)
							{
								if(StaticVariables.childPasscodeList.getPasscodeList().get(i).getPassCode()!=null&& !StaticVariables.childPasscodeList.getPasscodeList().get(i).getPassCode().trim().equalsIgnoreCase("") && StaticVariables.childPasscodeList.getPasscodeList().get(i).getPassCode().trim().length()==4)
								{
									isPasscodeSet=true;
								}
								break;
							}

						}

					}
					if(/*sharePref.getIsPasscodeParentSet()==true*/isPasscodeSet)
					{
						isPasscodeTouched=true;
						passcodechild_switch.setChecked(true);

						if(getChildDetail.getPasscode()!=null && getChildDetail.getPasscode().trim().length()>3)
						{
							//childpasscode_editText.setText(getChildDetail.getPasscode());
							textchild_passcode.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
							//textchild_passcode.setTransformationMethod(PasswordTransformationMethod.getInstance());
							textchild_passcode.setAlpha(1f);

							textchild_passcode.setText(getChildDetail.getPasscode());

						}

						if(getChildDetail.getAutolockTime()!=0)
						{
							childautolocktime_autoCompleteTextView.setText(getChildDetail.getTimeValue());
							childautolocktime_autoCompleteTextView.setSelection(getChildDetail.getTimeValue().length());
							childProfile.setAutolockID(getChildDetail.getAutolockTime());

							//childProfile.setAutolockID(Integer.parseInt(getChildDetail.getAutolockTime()));
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

	private void setDateTimeField() {
		childdob_editText.setOnClickListener(new OnClickListener() {

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
				childdob_editText.setText(dateFormatter.format(newDate.getTime()));
				yearDOB=year;
				monthDOB=monthOfYear;
				dayDOB=dayOfMonth;
			}
		},newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

		newCalendar.add(Calendar.YEAR, -15);

		datePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());

		Calendar maxDate = Calendar.getInstance();
		maxDate.add(Calendar.YEAR, -3);
		maxDate.set(Calendar.MONTH, Calendar.DECEMBER);
		maxDate.set(Calendar.DAY_OF_MONTH, 31);

		datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
	}

	private void addChild(boolean hasToaddMore)
	{
		if(!checkValidation.isNotNullOrBlank(childFname_editText.getText().toString()) && !checkValidation.isNotNullOrBlank(childLname_editText.getText().toString()) && !checkValidation.isNotNullOrBlank(childnickname_editText.getText().toString())
				&& !checkValidation.isNotNullOrBlank(school_autoCompleteTextView.getText().toString()) /*&& !checkValidation.isNotNullOrBlank(childdob_editText.getText().toString())*/)
		{
			showMessage.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");
			onTouchChildAddButton=false;
		}
		else
		{
			if(!checkValidation.isNotNullOrBlank(childFname_editText.getText().toString()))
			{
				showMessage.showAlert("Invalid Data", "Did you type the name right?\nNote: You cant use smileys or special characters.");
				onTouchChildAddButton=false;
			}

			/*	else if(!checkValidation.validateFirstName(childFname_editText.getText().toString()))
			{
				showMessage.showAlert("Invalid Data", "Did you type the name right?\nNote: You cant use smileys or special characters.");
				onTouchChildAddButton=false;
			}*/

			else if(!checkValidation.isNotNullOrBlank(childLname_editText.getText().toString()))
			{
				showMessage.showAlert("Invalid Data", "Did you type the name right?\nNote: You cant use smileys or special characters.");
				onTouchChildAddButton=false;
			}

			/*	else if(!checkValidation.validateLastName(childLname_editText.getText().toString()))
			{
				showMessage.showAlert("Invalid Data", "Did you type the name right?\nNote: You cant use smileys or special characters.");
				onTouchChildAddButton=false;
			}
			 */
			else if(!checkValidation.isNotNullOrBlank(childnickname_editText.getText().toString()))
			{
				showMessage.showAlert("Invalid Data", "Did you type the nick name right?\nNote: You cant use smileys or special characters.");
				onTouchChildAddButton=false;
			}
			else if(!checkValidation.isNotNullOrBlank(school_autoCompleteTextView.getText().toString()))
			{
				showMessage.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");
				onTouchChildAddButton=false;
			}

			/*else if(!checkValidation.isNotNullOrBlank(childdob_editText.getText().toString()))
			{
				showMessage.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");
				onTouchChildAddButton=false;
			}

			else if(!checkValidation.isAgeValid(yearDOB, monthDOB, dayDOB, 3))
			{
				showMessage.showAlert("Alert", "Please check the date of birth entered. This app is ideal for children between the age of 3 to 15 years.");
				onTouchChildAddButton=false;
			}*/
			else if(!checkFirstName(childFname_editText.getText().toString()))
			{
				showMessage.showAlert("Profile Mix Up", "A child profile already exists with this name. Every child profile should have a unique name. ");
				onTouchChildAddButton=false;
			}
			else if(!checkNickName(childnickname_editText.getText().toString()))
			{
				showMessage.showAlert("Profile Mix Up", "A child profile already exists with this name. Every child profile should have a unique name. ");
				onTouchChildAddButton=false;
			}

			else if(checkValidation.isNotNullOrBlank(childdob_editText.getText().toString()))
			{
				if(!checkValidation.isAgeValid(yearDOB, monthDOB, dayDOB, 3))
				{
					showMessage.showAlert("Alert", "Please check the date of birth entered. This app is ideal for children between the age of 3 to 15 years.");
					onTouchChildAddButton=false;
				}
				else
				{

					//if(layout_Pass_AutoLockChild.getVisibility()==View.VISIBLE)
					if(passcodechild_switch.isChecked())
					{

						if(!checkValidation.isNotNullOrBlank(textchild_passcode.getText().toString()))
						{
							showMessage.showAlert("Alert", "Please enter a passcode before you proceed.");
							onTouchChildAddButton=false;
						}
						/*else if(!checkValidation.isNotNullOrBlank(childautolocktime_autoCompleteTextView.getText().toString()))
						{
							onTouchChildAddButton=false;
							showMessage.showAlert("Alert", "Please enter autolocktime before you proceed.");
						}*/
						else
						{
							childSaving(hasToaddMore);
						}
					}
					else
					{
						onTouchChildAddButton=false;
						showAlertPasscode("Alert", hasToaddMore, "Locking your child's profile helps secure access to the their personal dashboard. Are you sure you don't want to set up a child profile lock?");
					}

				}
			}
			else if(!checkValidation.isNotNullOrBlank(childdob_editText.getText().toString())&& countDialog==0)
			{

				AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ChildRegistrationActivity.this);
				alertBuilder.setCancelable(false);

				alertBuilder.setTitle("Alert");
				alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
				alertBuilder.setMessage("PiNWi is ideal for children aged 6 to 13 yrs.By choosing to share your child's DOB, you can help us stay relevant with our information.");
				alertBuilder.setPositiveButton(" GOT IT ", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(final DialogInterface dialog, int which) {
						dialog.dismiss();
						countDialog=1;
						addChild(false);
						onTouchChildAddButton=false;
					}
				});
				alertBuilder.setNegativeButton(" SET ", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
						datePickerDialog.show();
						onTouchChildAddButton=false;

					}
				});
				alertBuilder.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss(DialogInterface dialog) {
						// TODO Auto-generated method stub
						onTouchChildAddButton=false;
					}
				});

				alertBuilder.show();

			}


			else
			{
				//if(layout_Pass_AutoLockChild.getVisibility()==View.VISIBLE)
				if(passcodechild_switch.isChecked())
				{
					if(!checkValidation.isNotNullOrBlank(textchild_passcode.getText().toString()))
					{
						showMessage.showAlert("Alert", "Please enter a passcode before you proceed.");
						onTouchChildAddButton=false;
					}
					/*else if(!checkValidation.isNotNullOrBlank(childautolocktime_autoCompleteTextView.getText().toString()))
					{
						onTouchChildAddButton=false;
						showMessage.showAlert("Alert", "Please enter autolocktime before you proceed.");
					}*/
					else
					{
						childSaving(hasToaddMore);
					}
				}
				else
				{
					onTouchChildAddButton=false;
					showAlertPasscode("Alert", hasToaddMore,"Locking your child's profile helps secure access to the their personal dashboard. Are you sure you don't want to set up a child profile lock?");
				}
			}
		}
	}





	/**
	 * @param hasToaddMore
	 */
	private void childSaving(boolean hasToaddMore) {
		String textSelectSchool = school_autoCompleteTextView.getText().toString();	

		/*for(int i=0;i<schoolList.getSchoolList().size();i++)
		{
			if(textSelectSchool.trim().equalsIgnoreCase(schoolList.getSchoolList().get(i).getSchoolName().trim()))
			{
				childProfile.setSchoolID(schoolList.getSchoolList().get(i).getSchoolID());	
				childProfile.setSchoolName(textSelectSchool);
			}
		}*/
		childProfile.setSchoolName(textSelectSchool);

		String textSelectedAutolockTime = childautolocktime_autoCompleteTextView.getText().toString();

		for(int i=0;i<getAutolockTimeList.getGetAutolockTime().size();i++)
		{
			if(textSelectedAutolockTime.trim().equalsIgnoreCase(getAutolockTimeList.getGetAutolockTime().get(i).getTimeValue().trim()))
			{
				childProfile.setAutolockID(getAutolockTimeList.getGetAutolockTime().get(i).getAutolockID());
				childProfile.setAutolockTime(textSelectedAutolockTime);
			}
		}

		childProfile.setFirstName(childFname_editText.getText().toString());
		childProfile.setLastName(childLname_editText.getText().toString());
		childProfile.setNickName(childnickname_editText.getText().toString());
		try {
			childProfile.setDateOfBirth(childdob_editText.getText().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			childProfile.setDateOfBirth("");

			e.printStackTrace();
		}
		childProfile.setSchoolName(school_autoCompleteTextView.getText().toString());
		//if(textchild_passcode.getText().toString().equalsIgnoreCase("")||textchild_passcode.getText().toString().equalsIgnoreCase("Passcode"))
		if(textchild_passcode.getText().toString().equalsIgnoreCase("")||textchild_passcode.getText().toString().equalsIgnoreCase("Lock Child Profile"))
		{
			childProfile.setPasscode("");
		}
		else
		{
			childProfile.setPasscode(textchild_passcode.getText().toString());
		}
		childProfile.setProfileImage(imageByte);
		childProfile.setParentID(parentId);

		if(boy_textView.isChecked())
		{
			childProfile.setGender("Male");
		}
		else if(girl_textView.isChecked())
		{
			childProfile.setGender("Female");
		}

		if(childUpdate)
		{
			if(bitmapLength==null)
			{
				childProfile.setProfileImage(imageByte);
			}
			else
			{
				childProfile.setProfileImage(bitmapTobyte(getImageforCitation(imageByte)));
			}

			updateChildProfile  = new UpdateChildProfile();

			updateChildProfile.setParentID(parentId);
			updateChildProfile.setChildID(childID);
			updateChildProfile.setProfileImage(childProfile.getProfileImage());
			updateChildProfile.setFirstName(childProfile.getFirstName());
			updateChildProfile.setLastName(childProfile.getLastName());
			updateChildProfile.setDateOfBirth(childProfile.getDateOfBirth());
			updateChildProfile.setNickName(childProfile.getNickName());
			updateChildProfile.setGender(childProfile.getGender());
			//updateChildProfile.setSchoolName(childProfile.getSchoolID()+"");
			updateChildProfile.setSchoolName(childProfile.getSchoolName());
			updateChildProfile.setPasscode(childProfile.getPasscode());
			updateChildProfile.setAutolockTime(childProfile.getAutolockID()+"");

			new UpdateChildInformationOnServer().execute();
		}
		else
		{
			new RegisterChildTask(hasToaddMore).execute();
		}
	}

	//private ProgressDialog progressDialog3=null;

	private class UpdateChildInformationOnServer extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			if(customProgressLoader!=null)
			{
				customProgressLoader.showProgressBar();
			}
			/*progressDialog3 = ProgressDialog.show(ChildRegistrationActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog3.setCancelable(false);*/
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ChildRegistrationActivity.this))
			{
				ErrorCode = serviceMethod.updateChildProfile(updateChildProfile);
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
				customProgressLoader.dismissProgressBar();
				/*if (progressDialog3.isShowing())
					progressDialog3.cancel();*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			onTouchContinueButton=false;

			if(result==-1)
			{
				//showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(ChildRegistrationActivity.this))
					new UpdateChildInformationOnServer().execute();
			}
			else
			{
				if(result==0)
				{
					Toast.makeText(ChildRegistrationActivity.this, "Woohoo! You succesfully updated child profile", Toast.LENGTH_SHORT).show();

					ChildProfile modelChild=new ChildProfile();
					modelChild.setChildID(updateChildProfile.getChildID());
					modelChild.setNickName(updateChildProfile.getNickName());
					modelChild.setFirstName(updateChildProfile.getFirstName());
					modelChild.setDateOfBirth(updateChildProfile.getDateOfBirth());
					modelChild.setGender(updateChildProfile.getGender());
					modelChild.setSchoolName(updateChildProfile.getSchoolName());

					try {
						int currentChild=sharePref.getCurrentChildNumber();
						social.userProfileClevertap("","",3,null,modelChild,parentId,currentChild);

					}
					catch(Exception e)
					{

					}

					if(StaticVariables.childArrayList.size()>0)
					{
						for(int i=0;i<StaticVariables.childArrayList.size();i++)
						{
							if(StaticVariables.childArrayList.get(i).getChildID()==updateChildProfile.getChildID())
							{
								StaticVariables.childArrayList.get(i).setFirstName(updateChildProfile.getFirstName());
								StaticVariables.childArrayList.get(i).setNickName(updateChildProfile.getNickName());
							}
						}
					}

					PassCode pcChild = new  PassCode();

					pcChild.setPassCodeType(2);
					pcChild.setProfileId(childID);
					pcChild.setPassCode(childProfile.getPasscode());

					PassCodeList passCodeList = gsonRegistration.fromJson(sharePref.getPassCodeList(), PassCodeList.class);

					if(passCodeList!=null)
					{
						for(int i=0;i<passCodeList.getPasscodeList().size();i++)
						{
							if(passCodeList.getPasscodeList().get(i).getProfileId()==pcChild.getProfileId())
							{
								passCodeList.getPasscodeList().get(i).setPassCode(pcChild.getPassCode());
							}
							else
							{
								passCodeList.getPasscodeList().add(pcChild);
							}
						}

					}

					StaticVariables.childPasscodeList=passCodeList;
					String passcodeListString = gsonRegistration.toJson(passCodeList);
					sharePref.setPassCodeList(passcodeListString);

					finishActivity();
				}
				else
				{
					getError();
				}
			}
		}
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
					startActivityForResult(i, 105);
					/*Intent i=new Intent(ChildRegistrationActivity.this, TakePicture.class);
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
	protected boolean isLockDialogClicked=false;

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
					if(bitmapLength!=null)
					{
						imageByte =getPath(selectedImageUri);

						bitmapTobyte();
					}
					else
					{
						Toast.makeText(ChildRegistrationActivity.this, "Could not use this image. Please pick another one.", Toast.LENGTH_SHORT).show();
					}

					break;

				case 110:

					Uri selectedImageUri1 = data.getData();

					final int takeFlags = data.getFlags()
							& (Intent.FLAG_GRANT_READ_URI_PERMISSION
							| Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
					getContentResolver().takePersistableUriPermission(selectedImageUri1, takeFlags);


					bitmapLength=BitmapFactory.decodeFile(getPath(selectedImageUri1));
					if(bitmapLength!=null)
					{
						imageByte = getPath(selectedImageUri1);

						bitmapTobyte();
					}
					else
					{
						Toast.makeText(ChildRegistrationActivity.this, "Could not use this image. Please pick another one.", Toast.LENGTH_SHORT).show();
					}


					break;

				/*case 105:

				String path = data.getStringExtra("PATH");

				bitmapLength=BitmapFactory.decodeFile(path);

				if(bitmapLength!=null)
				{
				imageByte = path;

				bitmapTobyte();
				}
				else
				{
					Toast.makeText(ChildRegistrationActivity.this, "Could not use this image. Please pick another one.", Toast.LENGTH_SHORT).show();
				}

				break;*/

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
						Toast.makeText(ChildRegistrationActivity.this, "Could not use this image. Please pick another one.", Toast.LENGTH_SHORT).show();
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

					String filePath = "";

					int columnIndex = cursor.getColumnIndex(column[0]);

					if (cursor.moveToFirst()) {
						filePath = cursor.getString(columnIndex);
					}

					cursor.close();

					bitmapLength=BitmapFactory.decodeFile(filePath);
					if(bitmapLength!=null)
					{
						imageByte = filePath;
						bitmapTobyte();
					}
					else
					{
						Toast.makeText(ChildRegistrationActivity.this, "Could not use this image. Please pick another one.", Toast.LENGTH_SHORT).show();
					}

					break;

				case 200:
					String passCodeValue = data.getStringExtra("passCode");
					//childpasscode_editText.setText(passCodeValue);
					textchild_passcode.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
					//textchild_passcode.setTransformationMethod(PasswordTransformationMethod.getInstance());
					textchild_passcode.setAlpha(1f);
					hideKeyBoard();
					textchild_passcode.setText(passCodeValue);

					if(childProfile!=null)
						childProfile.setPasscode(passCodeValue);
					passcodechild_switch.setChecked(true);

					break;
			}
		}
		else
		{
			switch(requestCode)
			{
				case 200:
					passcodechild_switch.setChecked(false);
					break;
			}
		}
	}

	private void bitmapTobyte()
	{
		int bytes = bitmapLength.getByteCount();
		ByteBuffer buffer = ByteBuffer.allocate(bytes); //Create a new buffer
		bitmapLength.copyPixelsToBuffer(buffer); //Move the byte data to the buffer
		//imageByte = getChildDetail.getProfileImage();
		if(bitmapLength!=null)
			childprofilePic_imageView.setImageBitmap(getRoundedShape(bitmapLength));
	}

	@SuppressWarnings("deprecation")
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

	private class GetListOfSchool extends AsyncTask<Void, Void, Integer>
	{
		private int parentId ;

		public GetListOfSchool(int parentId)
		{
			// TODO Auto-generated constructor stub 
			this.parentId = parentId;
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ChildRegistrationActivity.this))
			{
				schoolList = serviceMethod.getSchool(parentId);

				if(schoolList!=null)
				{
					schoolStringList = getSchoolStringList();
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

		private ArrayList<String> getSchoolStringList() {
			// TODO Auto-gen																																																																																																														erated method stub

			schoolStringList=new ArrayList<String>();

			for(int i=0;i<schoolList.getSchoolList().size();i++)
			{
				schoolStringList.add(schoolList.getSchoolList().get(i).getSchoolName());
			}

			return schoolStringList;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if(result==-1)
			{
				//showMessage.showToastMessage("Please check your network connection");
			}
			else
			{
				if(schoolStringList!=null && schoolStringList.size()>0)
				{
					Collections.sort(schoolStringList);
					/*AutoCompleteAdapter checkUpAdapter = new AutoCompleteAdapter(ChildRegistrationActivity.this, R.layout.list_item, R.id.item,schoolStringList);
					school_autoCompleteTextView.setAdapter(checkUpAdapter);*/
					NamesAdapter namesAdapter = new NamesAdapter(
							ChildRegistrationActivity.this, R.layout.list_item, R.id.item,schoolStringList
					);
					//set adapter into listStudent
					school_autoCompleteTextView.setAdapter(namesAdapter);
					school_autoCompleteTextView.setThreshold(0);
					//school_autoCompleteTextView.setValidator(new ValidateText(schoolStringList,1));
				}
				else
				{
					getError();
				}
			}
		}
	}

	private void getError()
	{/*
		Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());
	 */}

	//private ProgressDialog progressDialog=null;

	private class RegisterChildTask extends AsyncTask<Void, Void, Integer>
	{

		private boolean hasToaddMore;

		public RegisterChildTask(boolean hasToaddMore) {
			// TODO Auto-generated constructor stub

			this.hasToaddMore = hasToaddMore;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(customProgressLoader!=null)
			{
				customProgressLoader.showProgressBar();
			}
			/*progressDialog = ProgressDialog.show(ChildRegistrationActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
		}

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub

			int childId=0;

			if(checkNetwork.checkNetworkConnection(ChildRegistrationActivity.this))
			{
				try {
					String path=childProfile.getProfileImage();

					if(path!=null)
						childProfile.setProfileImage(bitmapTobyte(getImageforCitation(path)));

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				childId=-1;

				childId=serviceMethod.createChildProfile(childProfile);

				if(childId!=0)
				{
					childProfile.setChildID(childId);
				}
				else
				{

				}
			}
			return childId;
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

			onTouchChildAddButton=false;
			onTouchContinueButton=false;

			if(result==0)
			{
				//showMessage.showToastMessage("Please check your network connection");

				/*if(checkNetwork.checkNetworkConnection(ChildRegistrationActivity.this))
					new RegisterChildTask(hasToaddMore).execute();*/
			}
			else
			{

				if(result!=-1)
				{
					/*social.childRegistrationFacebookLog();
					social.childRegistrationGoogleAnalyticsLog();*/
					//sharePref.setCurrentScreen(2);	
					PassCode pcChild = new  PassCode();

					pcChild.setPassCodeType(2);
					pcChild.setProfileId(result);
					pcChild.setPassCode(childProfile.getPasscode());
					try {
						int currentChild=sharePref.getCurrentChildNumber();
						currentChild++;
						sharePref.setCurrentChildNo(currentChild);
						social.userProfileClevertap("","",3,null,childProfile,parentId,currentChild);

					}
					catch(Exception e)
					{

					}
					PassCodeList passCodeList = gsonRegistration.fromJson(sharePref.getPassCodeList(), PassCodeList.class);

					if(passCodeList!=null)
					{
						passCodeList.getPasscodeList().add(pcChild);
					}
					else
					{
						passCodeList = new PassCodeList();
						passCodeList.getPasscodeList().add(pcChild);
					}

					StaticVariables.childPasscodeList=passCodeList;
					String passcodeListString = gsonRegistration.toJson(passCodeList);
					sharePref.setPassCodeList(passcodeListString);
					if(sharePref.getIsLogin())
					{

					}
					else
					{
						if(!sharePref.isChildAdded())
						{

							if(social!=null)
							{
								social.child_ProfileAnalyticsLog();
							}
						}

					}
					if(hasToaddMore)
					{
						try {
							//showMessage.showAlert("Confirmation", "Woohoo! You succesfully added a new child profile");
							Toast.makeText(ChildRegistrationActivity.this, "Woohoo! You succesfully added a new child profile", Toast.LENGTH_SHORT).show();

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}



						clearDataForNewChild();
					}
					else
					{
						System.out.println("value of is registered in ally"+sharePref.getParentIsRegistered());

						if(addNewChild)
						{
							//Toast.makeText(ChildRegistrationActivity.this, "Child Profile is added.", Toast.LENGTH_SHORT).show(); 
							//showMessage.showAlert("Confirmation", "Woohoo! You succesfully added a new child profile");
							Toast.makeText(ChildRegistrationActivity.this, "Woohoo! You succesfully added a new child profile", Toast.LENGTH_SHORT).show();

							ChildModel model=new ChildModel();
							model.setChildID(childProfile.getChildID());
							model.setFirstName(childProfile.getFirstName());
							model.setNickName(childProfile.getNickName());
							StaticVariables.childArrayList.add(model);
							finishActivity();
						}
						else
						{
							showAlertChildSave("Confirmation",
									"Woo-hoo! You successfully added a child profile.Add another child profile?");
							/*Intent intent=new Intent(ChildRegistrationActivity.this, AllyRegistrationActivity.class);
							startActivity(intent);
							sharePref.setCurrentScreen(2);*/
							// TODO Auto-generated method stub
							/*Intent intent=new Intent(ChildRegistrationActivity.this, GetStartedActivity.class);
							startActivity(intent);
							sharePref.setCurrentScreen(3);
							ChildRegistrationActivity.this.finish();*/
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
			ChildRegistrationActivity.this.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

			InputMethodManager inputManager = (InputMethodManager) ChildRegistrationActivity.this
					.getSystemService(Context.INPUT_METHOD_SERVICE);

			inputManager.hideSoftInputFromWindow(ChildRegistrationActivity.this
					.getCurrentFocus().getWindowToken(), 0);

			ChildRegistrationActivity.this.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		}
		catch (Exception e)
		{

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
				school_autoCompleteTextView.setFocusable(true);
			}
			else
			{
				childpasscode_editText.setFocusable(true);
			}
		}

		public CharSequence fixText(CharSequence invalidText)
		{
			Log.v("Test", "Returning fixed text");
			System.out.println(invalidText+"  text in change");
			return "";
		}
	}

	private byte[] getImageforCitation(String name) {
		Bitmap bmp = null;
		byte[] byteArray = null;

		File imageFile = new File(name);
		if (imageFile.exists()) {

			//bmp = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
			bmp = new CompressImage(ChildRegistrationActivity.this).compressImage(name);

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

	//For Circular Image

	public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {

		int targetWidth = 100;

		if(SplashActivity.ScreenWidth >= 1000)
		{
			targetWidth = 150;

		}
		int targetHeight = targetWidth;
		Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
				targetHeight,Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(targetBitmap);
		Path path = new Path();
		path.addCircle(((float) targetWidth - 1) / 2,
				((float) targetHeight - 1) / 2,
				(Math.min(((float) targetWidth),
						((float) targetHeight)) / 2),
				Path.Direction.CCW);

		canvas.clipPath(path);
		Bitmap sourceBitmap = scaleBitmapImage;
		canvas.drawBitmap(sourceBitmap,
				new Rect(0, 0, sourceBitmap.getWidth(),
						sourceBitmap.getHeight()),
				new Rect(0, 0, targetWidth, targetHeight), null);
		return targetBitmap;
	}

	private class GetListOfAutoLockTimeField extends AsyncTask<Void, Void, Integer>
	{

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ChildRegistrationActivity.this))
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

			if(result==-1)
			{
				//showMessage.showToastMessage("Please check your network connection");
			}
			else
			{
				if(getAutolockTimeStringList!=null && getAutolockTimeStringList.size()>0)
				{
					AutoCompleteAdapter checkUpAdapter = new AutoCompleteAdapter(ChildRegistrationActivity.this, R.layout.list_item, R.id.item,getAutolockTimeStringList());
					childautolocktime_autoCompleteTextView.setAdapter(checkUpAdapter);
					childautolocktime_autoCompleteTextView.setValidator(new ValidateText(getAutolockTimeStringList,0));
				}
				else
				{
					getError();
				}
			}
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(childUpdate || addNewChild)
		{
			finishActivity();
		}
		else
		{
			showAlertChildContinue("Confirmation","Are you sure you want to exit the app?");
		}
		//	super.onBackPressed();

	}


	private void finishActivity() {
		Intent childIntent=new Intent(ChildRegistrationActivity.this,ChildListActivity.class);
		startActivity(childIntent);
		ChildRegistrationActivity.this.finish();

	}

	public void showAlertPasscode(String title, final boolean hasToaddMore, String message)
	{
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
		alertBuilder.setCancelable(false);

		alertBuilder.setTitle(title);
		alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
		alertBuilder.setMessage(message);
		alertBuilder.setPositiveButton(" Submit ", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				childSaving(hasToaddMore);
			}
		});

		alertBuilder.setNegativeButton(" LOCK ", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				isLockDialogClicked=true;
				Intent intent=new Intent(ChildRegistrationActivity.this, AddPasscordActivity.class);
				Bundle bundle =new Bundle();
				//textchild_passcode.setText("Passcode");
				textchild_passcode.setText("Lock Child Profile");

				bundle.putString("passCode","");
				intent.putExtras(bundle);
				startActivityForResult(intent, 200);
			}
		});
		alertBuilder.show();
	}


	public void showAlertChildRegistration(String title, String message)
	{
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ChildRegistrationActivity.this);

		alertBuilder.setTitle(title);
		alertBuilder.setCancelable(false);
		alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
		alertBuilder.setMessage(message);
		alertBuilder.setPositiveButton(" OK ", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				new GetListOfSchool(parentId).execute();

				new GetListOfAutoLockTimeField().execute();
			}
		});
		alertBuilder.show();
	}


	private boolean checkFirstName(String string)
	{
		// TODO Auto-generated method stub
		boolean valid=true;

		for(int i=0;i<StaticVariables.childArrayList.size();i++)
		{
			if(!childUpdate)
			{
				if(StaticVariables.childArrayList.get(i).getFirstName().equalsIgnoreCase(string))
				{
					valid=false;
				}
			}
			else
			{
				if(childID!=StaticVariables.childArrayList.get(i).getChildID())
				{

					if(StaticVariables.childArrayList.get(i).getFirstName().equalsIgnoreCase(string))
					{
						valid=false;
					}
				}

			}
		}
		return valid;
	}


	private boolean checkNickName(String string)
	{
		// TODO Auto-generated method stub
		boolean valid=true;

		for(int i=0;i<StaticVariables.childArrayList.size();i++)
		{
			if(!childUpdate)
			{
				if(StaticVariables.childArrayList.get(i).getNickName().equalsIgnoreCase(string))
				{
					valid=false;
				}
			}

			else
			{
				if(childID!=StaticVariables.childArrayList.get(i).getChildID())
				{
					if(StaticVariables.childArrayList.get(i).getNickName().equalsIgnoreCase(string))
					{
						valid=false;
					}
				}

			}
		}
		return valid;
	}


	public void showAlertChildSave(String title, String message)
	{
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
		alertBuilder.setCancelable(false);
		alertBuilder.setTitle(title);
		alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
		alertBuilder.setMessage(message);
		alertBuilder.setNegativeButton(" Add ", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				clearDataForNewChild();
			}
		});

		alertBuilder.setPositiveButton(" No, Thanks ", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				Intent intent=new Intent(ChildRegistrationActivity.this, GetStartedActivity.class);
				startActivity(intent);
				sharePref.setCurrentScreen(3);
				ChildRegistrationActivity.this.finish();

			}
		});
		alertBuilder.show();
	}
	/**
	 *
	 */
	private void clearDataForNewChild() {
		ChildModel model=new ChildModel();
		model.setChildID(childProfile.getChildID());
		model.setFirstName(childProfile.getFirstName());
		model.setNickName(childProfile.getNickName());
		StaticVariables.childArrayList.add(model);
		button_new_continue_Child.setVisibility(View.VISIBLE);
		sharePref.setChildAdded(true);
		childFname_editText.setText("");
		childLname_editText.setText("");
		childnickname_editText.setText("");
		childdob_editText.setText("");
		school_autoCompleteTextView.setText("");
		childautolocktime_autoCompleteTextView.setText("");
		childpasscode_editText.setText("");
		childprofilePic_imageView.setImageResource(R.drawable.camera_icon);
		childnickname_editText.requestFocus();
		//textchild_passcode.setText("Passcode");
		textchild_passcode.setText("Lock Child Profile");

		passcodechild_switch.setChecked(false);
		sharePref.setIsPasscodeChildSet(false);
		layout_Pass_AutoLockChild.setVisibility(View.GONE);

		imageByte=null;
		childProfile=new ChildProfile();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_help, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		/*if(item.getItemId()==R.id.action_help)
		{
			Intent intentAboutUs =new Intent(ChildRegistrationActivity.this, ActivityAboutUS.class);
			startActivity(intentAboutUs);
			StaticVariables.webUrl="http://pinwi.in/contactus.aspx?4";	  
			}*/
		return super.onOptionsItemSelected(item);
	}


	public void showAlertChildContinue(String title,String message)
	{
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

		alertBuilder.setTitle(title);
		alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
		alertBuilder.setMessage(message);
		alertBuilder.setPositiveButton(" Cancel ", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});

		alertBuilder.setNegativeButton(" Yes ", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				finish();
			}
		});
		alertBuilder.show();
	}
	public void showAlertChildContinueNext(String title,String message)
	{
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
		alertBuilder.setCancelable(false);
		alertBuilder.setTitle(title);
		alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
		alertBuilder.setMessage(message);
		alertBuilder.setPositiveButton(" Cancel ", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});

		alertBuilder.setNegativeButton(" Yes ", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				Intent intent=new Intent(ChildRegistrationActivity.this, GetStartedActivity.class);
				startActivity(intent);
				sharePref.setCurrentScreen(3);
				ChildRegistrationActivity.this.finish();			}
		});
		alertBuilder.show();
	}
}
