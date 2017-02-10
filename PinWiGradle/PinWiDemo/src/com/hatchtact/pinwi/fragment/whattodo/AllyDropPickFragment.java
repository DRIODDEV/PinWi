package com.hatchtact.pinwi.fragment.whattodo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.AddAllyInformationOnActivity;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.GetAllyInformationOnActivity;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.Validation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AllyDropPickFragment extends ParentFragment
{
	private View view;

	private TextView text_allyInformName=null;
	private LinearLayout layout_dateInformAlly=null;
	private LinearLayout layout_timeInformAlly=null;
	private TextView settimeValueAlly_text=null;
	private TextView setdateValueAlly_text=null;
	private Switch setpickValue_text=null;
	private Switch setdropValue_text=null;
	private TextView text_specialInstrutionAlly=null;
	private Button button_continueInformAlly=null;
	private ImageView image_informAllyProfile=null;

	private TextView setdate_text=null;
	private TextView settime_text=null;
	private TextView setpick_text=null;
	private TextView setdrop_text=null;
	private TextView layout_specialInstrutionInformAlly=null;

	private DatePickerDialog datePickerDialog=null;
	private SimpleDateFormat dateFormatter=null;

	private int selectedYear,selectedMonth,selectedDay;

	private Activity mActivity;

	protected Dialog builder_time_picker = null;

	private AddAllyInformationOnActivity addAllyInformationOnActivity=null;

	private SharePreferenceClass sharePreferneceClass=null;
	private Gson gsonRegistration=null;
	private Validation checkValidation=null;
	private ShowMessages showMessage=null; 
	private CheckNetwork checkNetwork=null;
	private ServiceMethod serviceMethod=null;

	private boolean onTouchContinueButton=false;

	AddAfterSchoolFragment addAfterSchoolFragment=new AddAfterSchoolFragment();

	int childId,activityId;

	private GetAllyInformationOnActivity getAllyInformationOnActivity=null;

	private TextView text_SMS;

	private TextView text_EMAIL;

	private TextView selectSubjectName;
	private TextView informAllyLabel;
	private TextView setAllyName_label;
	private TextView SelectOneLabel;
	private LinearLayout layout_allyName;

	private Integer selectedStartHour;

	private Integer selectedStartMinute;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.activity_inform_ally, container, false);

		setHasOptionsMenu(true);
		mListener.onFragmentAttached(false,"  Scheduler");

		mActivity=getActivity();
		onTouchContinueButton=false;

		addAllyInformationOnActivity=new AddAllyInformationOnActivity();
		getAllyInformationOnActivity=new GetAllyInformationOnActivity();
		sharePreferneceClass=new SharePreferenceClass(mActivity);
		gsonRegistration = new GsonBuilder().create();
		checkNetwork=new CheckNetwork();
		checkValidation = new Validation();
		showMessage=new ShowMessages(mActivity);
		serviceMethod=new ServiceMethod();
		dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

		text_allyInformName=(TextView) view.findViewById(R.id.text_allyInformName);
		layout_dateInformAlly=(LinearLayout) view.findViewById(R.id.layout_dateInformAlly);
		layout_timeInformAlly=(LinearLayout) view.findViewById(R.id.layout_timeInformAlly);
		settimeValueAlly_text=(TextView) view.findViewById(R.id.settimeValueAlly_text);
		setdateValueAlly_text=(TextView) view.findViewById(R.id.setdateValueAlly_text);
		setpickValue_text=(Switch) view.findViewById(R.id.setpickValue_text);
		setdropValue_text=(Switch) view.findViewById(R.id.setdropValue_text);
		text_specialInstrutionAlly=(TextView) view.findViewById(R.id.text_specialInstrutionAlly);
		button_continueInformAlly = (Button) view.findViewById(R.id.button_continueInformAlly);
		//image_informAllyProfile=(ImageView) view.findViewById(R.id.image_informAllyProfile);

		setdate_text=(TextView) view.findViewById(R.id.setdate_text);
		settime_text=(TextView) view.findViewById(R.id.settime_text);
		setpick_text=(TextView) view.findViewById(R.id.setpick_text);
		setdrop_text=(TextView) view.findViewById(R.id.setdrop_text);
		layout_specialInstrutionInformAlly=(TextView) view.findViewById(R.id.layout_specialInstrutionInformAlly);

		text_SMS =(TextView) view.findViewById(R.id.text_SMS);
		text_EMAIL =(TextView) view.findViewById(R.id.text_EMAIL);
		selectSubjectName=(TextView) view.findViewById(R.id.SelectSubjectName);
		informAllyLabel=(TextView) view.findViewById(R.id.informAllyLabel);
		setAllyName_label=(TextView) view.findViewById(R.id.setAllyName_label);
		SelectOneLabel=(TextView) view.findViewById(R.id.SelectOneLabel);

		layout_allyName=(LinearLayout) view.findViewById(R.id.layout_allyName);
	
		layout_allyName.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(!DisplayAllyInformationFragment.updatedDataFromDisplayAlly)
				{

					if(StaticVariables.fragmentIndexCurrentTabWhatToDo==316)
					{
					StaticVariables.fragmentIndexCurrentTabWhatToDo = 317;
				
					}
					else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==320)
					{
						StaticVariables.fragmentIndexCurrentTabWhatToDo = 321;
					
					}
					else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==324)
					{
						StaticVariables.fragmentIndexCurrentTabWhatToDo = 325;
						
					}

					switchingFragments(new InformAllyFragment());
				}
			}
		});


		typeFace.setTypefaceRegular(text_allyInformName);
		typeFace.setTypefaceRegular(settimeValueAlly_text);
		typeFace.setTypefaceRegular(text_SMS);
		typeFace.setTypefaceRegular(text_EMAIL);
		typeFace.setTypefaceRegular(setdateValueAlly_text);
		typeFace.setTypefaceRegular(text_specialInstrutionAlly);
		typeFace.setTypefaceRegular(button_continueInformAlly);

		typeFace.setTypefaceRegular(setdate_text);
		typeFace.setTypefaceRegular(settime_text);
		typeFace.setTypefaceRegular(setpick_text);
		typeFace.setTypefaceRegular(setdrop_text);
		typeFace.setTypefaceRegular(layout_specialInstrutionInformAlly);
		typeFace.setTypefaceRegular(selectSubjectName);
		typeFace.setTypefaceRegular(informAllyLabel);
		typeFace.setTypefaceRegular(setAllyName_label);
		typeFace.setTypefaceRegular(SelectOneLabel);

		selectSubjectName.setText(StaticVariables.WhatToDoActivityName);

		if(DisplayAllyInformationFragment.updatedDataFromDisplayAlly) 
		{
			if(StaticVariables.addAfterSchoolActivities!=null)
				activityId=StaticVariables.addAfterSchoolActivities.getActivityID();
			
			

			new GetAllyDataFromServer(StaticVariables.currentChild.getChildID(),activityId,StaticVariables.AllyId,0).execute();
		}
		else
		{
			if(!StaticVariables.AllyName.equalsIgnoreCase(""))
				text_allyInformName.setText(StaticVariables.AllyName);

			}




		addAllyInformationOnActivity.setPickUp("0");
		addAllyInformationOnActivity.setDrop("0");

		layout_dateInformAlly.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				datePickerDialog.show();
			}
		});

		layout_timeInformAlly.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				builder_time_picker.show();
			}
		});

		//attach a listener to check for changes in state
		setpickValue_text.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				//if(!setdropValue_text.isChecked())
				{
					if(isChecked)
					{
						addAllyInformationOnActivity.setPickUp("1");
						setdropValue_text.setChecked(false);
						addAllyInformationOnActivity.setDrop("0");
					}
					else
					{
						addAllyInformationOnActivity.setPickUp("0");
					}
				}
				/*else
				{
					setpickValue_text.setChecked(false);
				}*/
			}
		});

		setdropValue_text.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked)
			{
				//if(!setpickValue_text.isChecked())
				{
					if(isChecked)
					{
						addAllyInformationOnActivity.setDrop("1");
						setpickValue_text.setChecked(false);
						addAllyInformationOnActivity.setPickUp("0");
					}
					else
					{
						addAllyInformationOnActivity.setDrop("0");
					}
				}
				/*else
				{
					setdropValue_text.setChecked(false);
				}*/
			}
		});

		button_continueInformAlly.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar c = Calendar.getInstance(); 
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DAY_OF_MONTH);
				
				Calendar calender1=Calendar.getInstance();
				
				calender1.set(selectedYear, selectedMonth, selectedDay, selectedStartHour, selectedStartMinute);

				long startDateValue=calender1.getTime().getTime();

				
				if(!checkValidation.isNotNullOrBlank(setdateValueAlly_text.getText().toString()))
					showMessage.showAlert("Warning", "Please select any date");

				else if(selectedYear < year)
					showMessage.showAlert("Warning", "Please select valid year");

				else if((selectedYear == year && selectedMonth < month))
					showMessage.showAlert("Warning", "Please select valid month");	

				else if(selectedMonth == month && selectedDay < day)
					showMessage.showAlert("Warning", "Please select valid day");

				else if(!checkValidation.isNotNullOrBlank(settimeValueAlly_text.getText().toString()))
					showMessage.showAlert("Warning", "Please select time");
				
				else if(startDateValue < System.currentTimeMillis() -(30 * 1000))
					showMessage.showAlert("Invalid Data", "Activities cannot be scheduled for a date or  time in the past.");

				else
				{
					onTouchContinueButton=true;

					addAllyInformationOnActivity.setActivityID(StaticVariables.WhatToDoActivityId);
					addAllyInformationOnActivity.setChildID(StaticVariables.currentChild.getChildID());

					/*if(AddAfterSchoolFragment.OnTouchAlly1)
					{
						addAllyInformationOnActivity.setAllyID(StaticVariables.allySetFor1.getAllyProfileID());
						addAllyInformationOnActivity.setAllyIndex(1);
					}
					else if(AddAfterSchoolFragment.OnTouchAlly2)
					{
						addAllyInformationOnActivity.setAllyID(StaticVariables.allySetFor2.getAllyProfileID());
						addAllyInformationOnActivity.setAllyIndex(2);
					}
					 */
					addAllyInformationOnActivity.setSpeicalInstructions(text_specialInstrutionAlly.getText().toString());
					addAllyInformationOnActivity.setNotificationMode("Email");
					addAllyInformationOnActivity.setDate(setdateValueAlly_text.getText().toString());
					addAllyInformationOnActivity.setTime(settimeValueAlly_text.getText().toString());
					addAllyInformationOnActivity.setAllyID(StaticVariables.AllyId);
					addAllyInformationOnActivity.setAllyIndex(0);

					/*if(AddAfterSchoolFragment.OnTouchAlly1)
					{
						StaticVariables.showAllyName1InAfterSchool=true;
						AddAfterSchoolFragment.addAlly1InformationOnActivity = addAllyInformationOnActivity;
					}
					else if(AddAfterSchoolFragment.OnTouchAlly2)
					{
						StaticVariables.showAllyName2InAfterSchool=true;
						AddAfterSchoolFragment.addAlly2InformationOnActivity = addAllyInformationOnActivity;

					}	*/		

					/*Bundle bundle = new Bundle();
					bundle.putSerializable("addAllyInformationOnActivity", addAllyInformationOnActivity);
					addAfterSchoolFragment.setArguments(bundle);
					 */
					/*String allyInformation = gsonRegistration.toJson(addAllyInformationOnActivity);
					sharePreferneceClass.setAddAllyInformationOnActivity(allyInformation);
					 */
					/*if(StaticVariables.fragmentIndexCurrentTabSchedular==32)
					{
						StaticVariables.fragmentIndexCurrentTabSchedular=35;
					}
					else if(StaticVariables.fragmentIndexCurrentTabSchedular==45)

					{
						StaticVariables.fragmentIndexCurrentTabSchedular=42;
					}

					else if(StaticVariables.fragmentIndexCurrentTabSchedular==53)

					{
						StaticVariables.fragmentIndexCurrentTabSchedular=50;
					}

					else if(StaticVariables.fragmentIndexCurrentTabSchedular==52)

					{
						StaticVariables.fragmentIndexCurrentTabSchedular=50;
					}

					else if(StaticVariables.fragmentIndexCurrentTabSchedular==44)

					{
						StaticVariables.fragmentIndexCurrentTabSchedular=42;
					}

					else if(StaticVariables.fragmentIndexCurrentTabSchedular==29)

					{
						StaticVariables.fragmentIndexCurrentTabSchedular=35;
					}

					else if(StaticVariables.fragmentIndexCurrentTabSchedular==60)

					{
						StaticVariables.fragmentIndexCurrentTabSchedular=24;
					}

					else if(StaticVariables.fragmentIndexCurrentTabSchedular==59)

					{
						StaticVariables.fragmentIndexCurrentTabSchedular=24;
					}

					else if(StaticVariables.fragmentIndexCurrentTabSchedular==70)
					{
						StaticVariables.fragmentIndexCurrentTabSchedular=67;

					}

					else if(StaticVariables.fragmentIndexCurrentTabSchedular==69)
					{
						StaticVariables.fragmentIndexCurrentTabSchedular=67;

					}
					 */

					new AddAllyInformationTask().execute();

					/*getFragmentManager().beginTransaction().add(R.id.realtabcontent,
							addAfterSchoolFragment).commit();*/
				}
			}
		});

		setDateField();
		setTimeField();
		initDialog();

		return view;
	}

	private void initDialog()
	{
		// TODO Auto-generated method stub
		builder_time_picker = new Dialog(mActivity);
		builder_time_picker.setTitle("Select Time:");
		LayoutInflater li = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = li.inflate(R.layout.dialog_time_picker, null);

		final TimePicker mTimePicker = (TimePicker) view.findViewById(R.id.time_picker);

		Calendar c = Calendar.getInstance();

		//mTimePicker.setIs24HourView(false);
		mTimePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
		mTimePicker.setCurrentMinute(c.get(Calendar.MINUTE));
		Button cancel_time = (Button) view.findViewById(R.id.cancel_time);
		cancel_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				builder_time_picker.dismiss();
			}
		});

		Button setTime = (Button) view.findViewById(R.id.set_time);
		setTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				builder_time_picker.dismiss();
				DateFormat dateFormat = new SimpleDateFormat("HH:mm");
				Date date = null;
				try {
					date = dateFormat.parse(mTimePicker.getCurrentHour()+":"+mTimePicker.getCurrentMinute());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String dateToSet = new SimpleDateFormat("hh:mm aa").format(date);

				settimeValueAlly_text.setText(dateToSet);
				
				selectedStartHour = mTimePicker.getCurrentHour();
				selectedStartMinute = mTimePicker.getCurrentMinute();

			}
		});
		builder_time_picker.setContentView(view);
	}

	private void setTimeField()
	{    
		layout_timeInformAlly.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				builder_time_picker.show();
			}
		});
	}

	private void setDateField() 
	{
		layout_dateInformAlly.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				datePickerDialog.show();
			}
		});

		Calendar newCalendar = Calendar.getInstance();

		datePickerDialog=new DatePickerDialog(mActivity, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				Calendar newDate=Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);

				setdateValueAlly_text.setText(dateFormatter.format(newDate.getTime()));

				selectedYear=year;
				selectedMonth=monthOfYear;
				selectedDay=dayOfMonth;
			}
		}, newCalendar
		.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
	}

	private ProgressDialog progressDialog=null;	

	private class GetAllyDataFromServer extends AsyncTask<Void, Void, Integer>
	{
		int childId;
		int activityId;
		int allyId;
		int allyIndex;

		public GetAllyDataFromServer(int childID,int activityID,int allyID,int allyIndex)
		{
			// TODO Auto-generated constructor stub
			childId=childID;
			activityId=activityID;
			allyId=allyID;
			this.allyIndex=allyIndex;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				getAllyInformationOnActivity =serviceMethod.getAllyInformationOnActivity(childId, activityId, allyId,0);
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
				if (progressDialog.isShowing())
					progressDialog.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(getActivity()))
					new GetAllyDataFromServer(childId,activityId,allyId,0).execute();
			}
			else
			{
				if(result!=-1)
				{	
					//DisplayAllyInformationFragment.updatedDataFromDisplayAlly=false;
					
					if(getAllyInformationOnActivity!=null)
					{
						text_allyInformName.setText(getAllyInformationOnActivity.getFirstName());
						setdateValueAlly_text.setText(getAllyInformationOnActivity.getDate());
						
						if(getAllyInformationOnActivity.getTime()!=null && !getAllyInformationOnActivity.getTime().trim().equalsIgnoreCase(""))
						{

							settimeValueAlly_text.setText(getAllyInformationOnActivity.getTime());
							
							selectedStartHour = Integer.parseInt(getAllyInformationOnActivity.getTime().split(":")[0]);
							selectedStartMinute = Integer.parseInt(getAllyInformationOnActivity.getTime().split(":")[1].split(" ")[0]);
							String AMOrPMEnd = getAllyInformationOnActivity.getTime().split(":")[1].split(" ")[1];
							if(AMOrPMEnd.equalsIgnoreCase("PM"))
							{
								selectedStartHour+=12;
							}
						}
						text_specialInstrutionAlly.setText(getAllyInformationOnActivity.getSpeicalInstructions());

						if(getAllyInformationOnActivity.getPickUp().equalsIgnoreCase("true"))
						{
							setpickValue_text.setChecked(true);
							setdropValue_text.setChecked(false);
							getAllyInformationOnActivity.setPickUp("1");
						}
						else if(getAllyInformationOnActivity.getPickUp().equalsIgnoreCase("false"))
						{
							setpickValue_text.setChecked(false);
							getAllyInformationOnActivity.setPickUp("0");
						}

						if(getAllyInformationOnActivity.getDrop().equalsIgnoreCase("true"))
						{
							setdropValue_text.setChecked(true);
							setpickValue_text.setChecked(false);
							getAllyInformationOnActivity.setDrop("1");
						}
						else if(getAllyInformationOnActivity.getDrop().equalsIgnoreCase("false"))
						{
							setdropValue_text.setChecked(false);
							getAllyInformationOnActivity.setDrop("0");
						}

						String[] pickDropDate = getAllyInformationOnActivity.getDate().split("/");

						for(int i=0;i<pickDropDate.length;i++)
						{
							selectedDay=Integer.parseInt(pickDropDate[0]);
							selectedMonth=Integer.parseInt(pickDropDate[1])-1;
							selectedYear=Integer.parseInt(pickDropDate[2]);
						}
					}	
				}
				else
				{
					//getError();
				}
			}
		}	
	}

	public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
		int targetWidth = 100;
		int targetHeight = 100;
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

		/* Paint p = new Paint();
		p.setAntiAlias(true);
		//p.setFilterBitmap(true);
		 */		
		Bitmap sourceBitmap = scaleBitmapImage;
		canvas.drawBitmap(sourceBitmap, 
				new Rect(0, 0, sourceBitmap.getWidth(),
						sourceBitmap.getHeight()), 
						new Rect(0, 0, targetWidth, targetHeight), null);
		return targetBitmap;
	}

	private void getError()
	{
		Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());
	} 

	// the create options menu with a MenuInflater to have the menu from your fragment
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		for(int i=0;i<menu.size();i++)
		{
			if(menu.getItem(i).getItemId()!=R.id.action_childName)
				menu.getItem(i).setVisible(false);
			else
			{
				menu.findItem(R.id.action_childName).setTitle(StaticVariables.currentChild.getFirstName());
				menu.getItem(i).setVisible(true);

			}
		}

		super.onCreateOptionsMenu(menu, inflater);
	} 



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==android.R.id.home)
		{
			getActivity().onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}


	private class AddAllyInformationTask extends AsyncTask<Void, Void, Integer>
	{
		private int ally=0;

		/*public AddAllyInformationTask(int i) {
			// TODO Auto-generated constructor stub
			ally = i;
		}*/

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub

			int Errorcode=0;

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				/*if(ally==1)
				{
					//Errorcode=serviceMethod.AddAllyInformation(addAlly1InformationOnActivity);
				}
				else if(ally==2)
				{
					//Errorcode=serviceMethod.AddAllyInformation(addAlly2InformationOnActivity);
				}*/
				Errorcode=serviceMethod.AddAllyInformation(addAllyInformationOnActivity);
			}
			else
			{
				Errorcode=-1;
			}
			return Errorcode;
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

				if(checkNetwork.checkNetworkConnection(getActivity()))
					new AddAllyInformationTask().execute();
			}
			else
			{
				if(result!=-1)
				{
					//second ally ka result
					/*if(ally==2)
					{
						resetStaticValues();

						showMessage.showAlertAndMove("Confirmation", "Activity Added","AfterSchoolActivityAdded",getFragmentManager());

					}
					else
					{
						if(ally2Value_text.getText().toString().equalsIgnoreCase(""))
						{
							resetStaticValues();

							showMessage.showAlertAndMove("Confirmation", "Activity Added","AfterSchoolActivityAdded",getFragmentManager());

						}
						else
						{*/


					StaticVariables.AllyName="";

					if(StaticVariables.fragmentIndexCurrentTabWhatToDo==316)
					{
					StaticVariables.fragmentIndexCurrentTabWhatToDo = 315;
				
					}
					else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==320)
					{
						StaticVariables.fragmentIndexCurrentTabWhatToDo = 319;
					
					}
					else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==324)
					{
						StaticVariables.fragmentIndexCurrentTabWhatToDo = 323;
						
					}
  
					 hideKeyBoard();

					//switchingFragments(addAfterSchoolFragment);
					switchingFragments(new DisplayAllyInformationFragment());
					//}
					//}


					/*if(StaticVariables.showAllyName2InAfterSchool)
					{
						StaticVariables.showAllyName1InAfterSchool=false;
						StaticVariables.showAllyName2InAfterSchool=false;
						StaticVariables.addAfterSchoolActivities=null;
						OnTouchAlly1=false;
						OnTouchAlly2=false;
					}
					else
					{
						//getError();
						StaticVariables.showAllyName1InAfterSchool=false;
						//StaticVariables.showAllyName2InAfterSchool=false;
						StaticVariables.addAfterSchoolActivities=null;
						OnTouchAlly1=false;
						//OnTouchAlly2=false;
						new AddAllyInformationTask().execute();
					}*/

					/*if(StaticVariables.showAllyName1InAfterSchool)
					{
						StaticVariables.showAllyName1InAfterSchool=false;
						StaticVariables.addAfterSchoolActivities=null;
						OnTouchAlly1=false;
						new AddAllyInformationTask().execute();
					}
					else if(StaticVariables.showAllyName2InAfterSchool)
					{
						StaticVariables.showAllyName1InAfterSchool=false;
						StaticVariables.showAllyName2InAfterSchool=false;
						StaticVariables.addAfterSchoolActivities=null;
						OnTouchAlly1=false;
						OnTouchAlly2=false;
					}*/
				}
			}
		}
	}
	
	
	private void hideKeyBoard() {
		
		
		
		try {
			getActivity().getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			InputMethodManager inputManager = (InputMethodManager) getActivity()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(getActivity()
					.getCurrentFocus().getWindowToken(), 0);
			getActivity().getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		} catch (Exception e) {
		}
		
	}
}
