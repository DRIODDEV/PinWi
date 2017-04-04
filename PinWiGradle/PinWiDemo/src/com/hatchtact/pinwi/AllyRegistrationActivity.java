package com.hatchtact.pinwi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.util.Base64;
import android.util.Log;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.AllyList;
import com.hatchtact.pinwi.classmodel.AllyProfile;
import com.hatchtact.pinwi.classmodel.GetAllyDetails;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.classmodel.UpdateAllyProfile;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;
import com.hatchtact.pinwi.utility.Validation;
import com.hatchtact.pinwi.view.AutoCompleteAdapter;

public class AllyRegistrationActivity extends MainActionBarActivity implements OnTouchListener
{
	private EditText allyFname_editText=null;
	private EditText allyLname_editText=null;
	private AutoCompleteTextView relationShip_autoCompleteTextView=null;
	private EditText allyEmail_editText=null;
	private EditText allyPhone_editText=null;
	private ImageView allyProfile_image=null;

	private Button allyContinue_textView=null;
	private Button registerLater_TextView=null;

	private TextView addanotherally_textView=null;
	private ImageView addanotherally_imageView=null;
	
	private Button button_saveAlly=null;

	private static final int SELECT_PICTURE = 100;

	private String imageByte=null;

	private Bitmap	bitmapLength=null;
	private TypeFace typeFace=null;
	private Validation checkValidation=null;
	private ShowMessages showMessage=null;
	private AllyProfile allyProfile=null;
	private	ServiceMethod serviceMethod=null;

	private ArrayList<String> relationShipList=new ArrayList<String>();

	private CheckNetwork checkNetwork=null;

	private AllyList allyList=null;

	private int parentId=0;

	private SharePreferenceClass sharePref=null;
	private Gson gsonRegistration=null;

	private boolean onTouchAllyAddButton=false;
	private boolean onTouchContinueButton=false;
	
	private boolean allyUpdate=false;
	private boolean addNewAlly=false;

	private int allyID;
	
	private UpdateAllyProfile updateAllyProfile=null;
	private TextView text_ProfileText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		screenName="Add Ally";	

		super.onCreate(savedInstanceState);

		setContentView(R.layout.ally_registration_activity);
		
		Bundle bundle = getIntent().getExtras();

		try {	
			allyUpdate = bundle.getBoolean("ToAllyScreen");
			allyID=bundle.getInt("allyId");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {	
			addNewAlly=bundle.getBoolean("ToAllyScreenFromAdd");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		checkValidation = new Validation();
		allyProfile=new AllyProfile();
		serviceMethod = new ServiceMethod();
		checkNetwork=new CheckNetwork();
		showMessage = new ShowMessages(AllyRegistrationActivity.this);
		typeFace=new TypeFace(AllyRegistrationActivity.this);
		sharePref=new SharePreferenceClass(this);
		gsonRegistration = new GsonBuilder().create();

		onTouchContinueButton=false;

		ParentProfile parentCompleteInformation = gsonRegistration.fromJson(sharePref.getParentProfile(), ParentProfile.class);
		parentId=parentCompleteInformation.getParentID();

		allyList=new AllyList();

		allyFname_editText=(EditText) findViewById(R.id.ally_Firstname);
		allyLname_editText=(EditText) findViewById(R.id.ally_Lastname);
		allyEmail_editText=(EditText) findViewById(R.id.text_allyemailId);
		allyPhone_editText=(EditText) findViewById(R.id.text_allyPhone);
		relationShip_autoCompleteTextView=(AutoCompleteTextView) findViewById(R.id.text_relationship);
		allyProfile_image=(ImageView) findViewById(R.id.image_camera_ally);
		addanotherally_textView=(TextView) findViewById(R.id.text_addally);
		addanotherally_imageView=(ImageView) findViewById(R.id.image_addAlly);
		button_saveAlly= (Button) findViewById(R.id.button_saveAlly);
		allyContinue_textView=(Button) findViewById(R.id.button_continueAlly);
		registerLater_TextView=(Button) findViewById(R.id.button_later);
		text_ProfileText = (TextView) findViewById(R.id.text_ProfileText);
		
		typeFace.setTypefaceLight(allyFname_editText);
		typeFace.setTypefaceLight(allyLname_editText);
		typeFace.setTypefaceLight(allyEmail_editText);
		typeFace.setTypefaceLight(allyPhone_editText);
		typeFace.setTypefaceLight(text_ProfileText);
		typeFace.setTypefaceLight(relationShip_autoCompleteTextView);
		typeFace.setTypefaceRegular(allyContinue_textView);
		typeFace.setTypefaceRegular(addanotherally_textView);
		typeFace.setTypefaceRegular(registerLater_TextView);
		typeFace.setTypefaceRegular(button_saveAlly);

		registerLater_TextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(AllyRegistrationActivity.this, GetStartedActivity.class);
				startActivity(intent);
				sharePref.setCurrentScreen(3);
				AllyRegistrationActivity.this.finish();
			}
		});	

		allyContinue_textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!onTouchAllyAddButton)
				{
					onTouchAllyAddButton=true;
					addAlly(false);
				}
			}
		});

		allyProfile_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cameraClickDialog();
			}
		});

		relationShip_autoCompleteTextView.setOnTouchListener(this);

		relationShip_autoCompleteTextView.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

			} 
		});

		addanotherally_imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!onTouchAllyAddButton)
				{
					onTouchAllyAddButton=true;
					addAlly(true);
				}
			}
		});
		
		addanotherally_textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!onTouchAllyAddButton)
				{
					onTouchAllyAddButton=true;
					addAlly(true);
				}
			}
		});
		
		button_saveAlly.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				addAlly(false);
			}
		});	
		
		
		
		if(addNewAlly)
		{
			button_saveAlly.setVisibility(View.VISIBLE);
			allyContinue_textView.setVisibility(View.GONE);
			registerLater_TextView.setVisibility(View.GONE);
			addanotherally_textView.setVisibility(View.GONE);
			addanotherally_imageView.setVisibility(View.GONE);
		}
		if(allyUpdate || addNewAlly)
		{
			if(allyUpdate)
			{
				setDataFromServer();
			}
			new GetListOfAllyRelationShip().execute();
		}
		else
		{
			showAlertAllyRegistration("PiNWi Help", "An ally is someone whose help you need to manage all the activities in your child's hectic life. For eg, your driver who picks/drops your child, a neighbour whom you car pool with and sometimes even your spouse who is otherwise off child duty.\nAdd as many Ally profiles as you have to easily communicate and connect with them.");
	
		}
		
	}
	
	private void setDataFromServer()
	{
		// TODO Auto-generated method stub
		button_saveAlly.setVisibility(View.VISIBLE);
		allyContinue_textView.setVisibility(View.GONE);
		registerLater_TextView.setVisibility(View.GONE);
		addanotherally_textView.setVisibility(View.GONE);
		addanotherally_imageView.setVisibility(View.GONE);

		new GetAllyDetailFromServer(allyID).execute();
	}

	private GetAllyDetails getAllyDetail=null;

	private ProgressDialog progressDialog2=null;

	private class GetAllyDetailFromServer extends AsyncTask<Void, Void, Integer>
	{
		private int allyId;


		public GetAllyDetailFromServer(int allyId)
		{
			// TODO Auto-generated constructor stub 
			this.allyId = allyId;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog2 = ProgressDialog.show(AllyRegistrationActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog2.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(AllyRegistrationActivity.this))
			{
				getAllyDetail = serviceMethod.getAllyDetails(allyId);
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

				if(checkNetwork.checkNetworkConnection(AllyRegistrationActivity.this))
					new GetAllyDetailFromServer(parentId).execute();
			}
			else
			{
				if(getAllyDetail!=null)
				{
					if(getAllyDetail.getFirstName()!=null && getAllyDetail.getFirstName().trim().length()>0)
						allyFname_editText.setText(getAllyDetail.getFirstName());

					if(getAllyDetail.getLastName()!=null && getAllyDetail.getLastName().trim().length()>0)
						allyLname_editText.setText(getAllyDetail.getLastName());

					if(getAllyDetail.getContact()!=null && getAllyDetail.getContact().trim().length()>0)
					{
						allyPhone_editText.setText(getAllyDetail.getContact());
					}

					if(getAllyDetail.getEmailAddress()!=null && getAllyDetail.getEmailAddress().trim().length()>0)
					{
						allyEmail_editText.setText(getAllyDetail.getEmailAddress());
					}
					
					if(getAllyDetail.getProfileImage()!=null && getAllyDetail.getProfileImage().length()>0)
					{
						byte[] imageByteRefill=Base64.decode(getAllyDetail.getProfileImage(), 0);
						if(imageByteRefill!=null)
						{
							imageByte = getAllyDetail.getProfileImage();

							allyProfile_image.setImageBitmap(getRoundedShape(BitmapFactory.decodeByteArray(imageByteRefill, 0, imageByteRefill.length)));	
						}
					}	

					if(getAllyDetail.getRelationshipName()!=null && getAllyDetail.getRelationshipName().trim().length()>0)
					{
						relationShip_autoCompleteTextView.setText(getAllyDetail.getRelationshipName());
						allyProfile.setAllyID(Integer.parseInt(getAllyDetail.getRelationship()));
					}
				}
				else
				{	
					getError();
				}
			}	
		}	
	}
	
	private void addAlly(boolean hasToaddMore)
	{
		if(!checkValidation.isNotNullOrBlank(allyFname_editText.getText().toString()) && !checkValidation.isNotNullOrBlank(allyLname_editText.getText().toString()) && !checkValidation.isNotNullOrBlank(relationShip_autoCompleteTextView.getText().toString())
				&& !checkValidation.isNotNullOrBlank(allyEmail_editText.getText().toString()) && !checkValidation.isNotNullOrBlank(allyPhone_editText.getText().toString()))
		{
			showMessage.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");
			onTouchAllyAddButton=false;
		}
		else
		{
			if(!checkValidation.isNotNullOrBlank(allyFname_editText.getText().toString()))
			{
				showMessage.showAlert("Invalid Data", "Did you type the name right?\nNote: You cant use smileys or special characters.");
				onTouchAllyAddButton=false;
			}

			/*else if(!checkValidation.validateFirstName(allyFname_editText.getText().toString()))
			{
				showMessage.showAlert("Invalid Data", "Did you type the name right?\nNote: You cant use smileys or special characters.");
				onTouchAllyAddButton=false;
			}*/

			else if(!checkValidation.isNotNullOrBlank(allyLname_editText.getText().toString()))
			{
				showMessage.showAlert("Invalid Data", "Did you type the name right?\nNote: You cant use smileys or special characters.");
				onTouchAllyAddButton=false;
			}

			/*else if(!checkValidation.validateLastName(allyLname_editText.getText().toString()))
			{
				showMessage.showAlert("Invalid Data", "Did you type the name right?\nNote: You cant use smileys or special characters.");
				onTouchAllyAddButton=false;
			}*/

			else if(!checkValidation.isNotNullOrBlank(relationShip_autoCompleteTextView.getText().toString()))
			{
				showMessage.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");
				onTouchAllyAddButton=false;
			}

			else if(!checkValidation.isNotNullOrBlank(allyEmail_editText.getText().toString()))
			{
				showMessage.showAlert("Invalid Email ID", "The email ID you entered may not be correct. Please Check");
				onTouchAllyAddButton=false;
			}

			else if(!checkValidation.isValidEmail(allyEmail_editText.getText().toString()))
			{
				showMessage.showAlert("Invalid Email ID", "The email ID you entered may not be correct. Please Check.");
				onTouchAllyAddButton=false;
			}

			/*else if(!checkValidation.isNotNullOrBlank(allyPhone_editText.getText().toString()))
			{
				showMessage.showAlert("Incorrect Number", "Your phone number should have 10 digits. Please check.");
				onTouchAllyAddButton=false;
			}		
			else if(!checkValidation.isValidPhoneNo(allyPhone_editText.getText().toString()))
			{
				showMessage.showAlert("Incorrect Number", "Your phone number should have 10 digits. Please check.");
				onTouchAllyAddButton=false;
			}*/

			else
			{
				String textSelectRelationShip = relationShip_autoCompleteTextView.getText().toString();	

				for(int i=0;i<allyList.getAllyList().size();i++)
				{
					if(textSelectRelationShip.trim().equalsIgnoreCase(allyList.getAllyList().get(i).getAllyTypeName().trim()))
					{
						allyProfile.setAllyID(allyList.getAllyList().get(i).getAllyTypeID());	

						allyProfile.setRelationship(textSelectRelationShip);
					}
				}

				allyProfile.setFirstName(allyFname_editText.getText().toString());
				allyProfile.setLastName(allyLname_editText.getText().toString());
				allyProfile.setRelationship(relationShip_autoCompleteTextView.getText().toString());
				allyProfile.setEmailAddress(allyEmail_editText.getText().toString());
				allyProfile.setContact(allyPhone_editText.getText().toString());
				allyProfile.setProfileImage(imageByte);
				allyProfile.setParentID(parentId);

				if(allyUpdate)
				{
					if(bitmapLength==null)
					{
						allyProfile.setProfileImage(imageByte);
					}
					else
					{
						allyProfile.setProfileImage(bitmapTobyte(getImageforCitation(imageByte)));
					}

					updateAllyProfile  = new UpdateAllyProfile();

					updateAllyProfile.setParentID(parentId);
					updateAllyProfile.setAllyID(allyID);
					updateAllyProfile.setProfileImage(allyProfile.getProfileImage());
					updateAllyProfile.setFirstName(allyProfile.getFirstName());
					updateAllyProfile.setLastName(allyProfile.getLastName());
					updateAllyProfile.setRelationship(allyProfile.getAllyID()+"");
					updateAllyProfile.setEmailAddress(allyProfile.getEmailAddress());
					updateAllyProfile.setContact(allyProfile.getContact());

					new UpdateAllyInformationOnServer().execute();	
				}
				else
				{
					new RegisterAllyTask(hasToaddMore).execute();
				}	
			}	
		}
	}

	private ProgressDialog progressDialog3=null;	

	private class UpdateAllyInformationOnServer extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog3 = ProgressDialog.show(AllyRegistrationActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog3.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(AllyRegistrationActivity.this))
			{
				ErrorCode = serviceMethod.updateAllyProfile(updateAllyProfile);
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

				if(checkNetwork.checkNetworkConnection(AllyRegistrationActivity.this))
					new UpdateAllyInformationOnServer().execute();
			}
			else
			{
				if(result==0)
				{
					Toast.makeText(AllyRegistrationActivity.this, "Ally Profile is updated.", Toast.LENGTH_SHORT).show(); 
					finishActivity();
				}
				else
				{
					getError();
				}
			}	
		}	
	}

	private ProgressDialog progressDialog=null;	

	private class GetListOfAllyRelationShip extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = ProgressDialog.show(AllyRegistrationActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(AllyRegistrationActivity.this))
			{
				allyList = serviceMethod.getAlly();

				if(allyList!=null)
				{
					relationShipList = getAllyStringList();
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

		private ArrayList<String> getAllyStringList() {
			// TODO Auto-gen																																																																																																														erated method stub

			relationShipList=new ArrayList<String>();

			for(int i=0;i<allyList.getAllyList().size();i++)
			{
				relationShipList.add(allyList.getAllyList().get(i).getAllyTypeName()); 
			}

			return relationShipList;
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

				if(checkNetwork.checkNetworkConnection(AllyRegistrationActivity.this))
					new GetListOfAllyRelationShip().execute();
			}
			else
			{
				if(relationShipList!=null && relationShipList.size()>0)
				{
					AutoCompleteAdapter checkUpAdapter = new AutoCompleteAdapter(AllyRegistrationActivity.this, R.layout.list_item, R.id.item,relationShipList);
					relationShip_autoCompleteTextView.setAdapter(checkUpAdapter);
					relationShip_autoCompleteTextView.setValidator(new ValidateText(relationShipList,1));
				}
				else
				{	
					getError();
				}	
			}	
		}	
	}

	private class RegisterAllyTask extends AsyncTask<Void, Void, Integer>
	{

		private boolean hasToaddMore;

		public RegisterAllyTask(boolean hasToaddMore) {
			// TODO Auto-generated constructor stub

			this.hasToaddMore = hasToaddMore;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = ProgressDialog.show(AllyRegistrationActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}


		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub

			int allyId=0;

			if(checkNetwork.checkNetworkConnection(AllyRegistrationActivity.this))
			{
				allyId=-1;
				try {
					if(allyProfile_image!=null)
					{
						String path=allyProfile.getProfileImage();

						if(path!=null)
							allyProfile.setProfileImage(bitmapTobyte(getImageforCitation(path)));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				allyId=serviceMethod.createAllyProfile(allyProfile);

				if(allyId!=0)
				{
					allyProfile.setAllyID(allyId);
				}
				else
				{

				}
			}
			return allyId;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result); 

			onTouchAllyAddButton=false;
			onTouchContinueButton=false;
			try {
				if (progressDialog.isShowing())
					progressDialog.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==0)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(AllyRegistrationActivity.this))
					new RegisterAllyTask(hasToaddMore).execute();
			}
			else
			{
				if(result!=-1)
				{
					//sharePref.setCurrentScreen(3);
					if(hasToaddMore)
					{
						try {
							showMessage.showAlert("Confirmation", "Way to go! You just added a new Ally to your list.");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						allyFname_editText.setText("");
						allyLname_editText.setText("");
						allyEmail_editText.setText("");
						relationShip_autoCompleteTextView.setText("");
						allyPhone_editText.setText("");
						allyProfile_image.setImageResource(R.drawable.camera_icon);

						allyProfile=new AllyProfile();
					}
					else
					{
						if(addNewAlly)
						{
							Toast.makeText(AllyRegistrationActivity.this, "Way to go! You just added a new Ally to your list.", Toast.LENGTH_SHORT).show(); 
							//showMessage.showAlert("Confirmation", "Way to go! You just added a new Ally to your list.");
							finishActivity();
						}
						else
						{
							Intent intent=new Intent(AllyRegistrationActivity.this, GetStartedActivity.class);
							startActivity(intent);
							sharePref.setCurrentScreen(3);
							AllyRegistrationActivity.this.finish();
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

	private void getError()
	{/*
		Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());
	*/}

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
			AllyRegistrationActivity.this.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

			InputMethodManager inputManager = (InputMethodManager) AllyRegistrationActivity.this
					.getSystemService(Context.INPUT_METHOD_SERVICE);

			inputManager.hideSoftInputFromWindow(AllyRegistrationActivity.this
					.getCurrentFocus().getWindowToken(), 0);

			AllyRegistrationActivity.this.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		} 
		catch (Exception e) 
		{

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
					/*Intent i=new Intent(AllyRegistrationActivity.this, TakePicture.class);
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

	@SuppressLint("NewApi")
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
				imageByte = getPath(selectedImageUri);

				bitmapTobyte();
			}
			else
			{
				Toast.makeText(AllyRegistrationActivity.this, "Could not use this image. Please pick another one.", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(AllyRegistrationActivity.this, "Could not use this image. Please pick another one.", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(AllyRegistrationActivity.this, "Could not use this image. Please pick another one.", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(AllyRegistrationActivity.this, "Could not use this image. Please pick another one.", Toast.LENGTH_SHORT).show();
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
					imageByte=filePath;

					bitmapTobyte();
				}
			else
			{
				Toast.makeText(AllyRegistrationActivity.this, "Could not use this image. Please pick another one.", Toast.LENGTH_SHORT).show();
			}


				break;
			}
		}
	}

	private void bitmapTobyte()
	{
		int bytes = bitmapLength.getByteCount();

		ByteBuffer buffer = ByteBuffer.allocate(bytes); //Create a new buffer
		bitmapLength.copyPixelsToBuffer(buffer); //Move the byte data to the buffer

		/*byte[] array = buffer.array();

		imageByte = Base64.encodeToString(array,
				Base64.DEFAULT);*/

		//allyProfile_image.setImageBitmap(bitmapLength);

		allyProfile_image.setImageBitmap(getRoundedShape(bitmapLength));
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
				relationShip_autoCompleteTextView.setFocusable(true);
			}
			else
			{
				allyEmail_editText.setFocusable(true);
			}
		}

		public CharSequence fixText(CharSequence invalidText) {
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

	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(allyUpdate || addNewAlly)
		{
			finishActivity();
		}
		else
			super.onBackPressed();
	}

	private void finishActivity() {
		
		if(!StaticVariables.fromInformAlly)
		{
		Intent allyIntent=new Intent(AllyRegistrationActivity.this,AllyListActivity.class);
		startActivity(allyIntent);
		AllyRegistrationActivity.this.finish();
		}
		else
		{
			AllyRegistrationActivity.this.finish();
			setResult(-1, null);
			StaticVariables.fromInformAlly=false;
		}
	}
	
	
	
	public void showAlertAllyRegistration(String title, String message)
	{
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AllyRegistrationActivity.this);

		alertBuilder.setTitle(title);
		alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
		alertBuilder.setMessage(message);
		alertBuilder.setPositiveButton(" OK ", new DialogInterface.OnClickListener() 
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				new GetListOfAllyRelationShip().execute();

			}
		});	
		alertBuilder.show();
	}
}
