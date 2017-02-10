package com.hatchtact.pinwi.fragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.AddAfterSchoolActivities;
import com.hatchtact.pinwi.classmodel.AfterSchoolActivityDetails;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.Validation;

public class AddAfterSchoolFragment extends ParentFragment
{
	private View view;

	private String comesfromWhichScreen="";

	private TextView subCategory_text=null;
	private TextView subcategoryByCatid_text=null;

	private LinearLayout layout_startDate=null;
	private LinearLayout layout_startTime=null;
	private LinearLayout layout_endTime=null;
	private LinearLayout layout_endDate=null;
	private LinearLayout layout_daysofWeek=null; 
	private LinearLayout layout_informAlly1=null;
	private LinearLayout layout_informAlly2=null;

	private TextView start_date_text=null;

	private TextView start_time_text=null;
	private TextView end_time_text=null;
	private TextView end_date_text=null;
	private TextView daysOfWeek_text=null;
	private TextView reminderInfo_text=null;
	private TextView mark_private_text=null;
	private TextView mark_special_text=null;
	private TextView ally1_text=null;
	private TextView ally2_text=null;
	private TextView noteAfterSchool_text=null;

	private TextView start_dateValue_text=null;
	private TextView start_timeValue_text=null;
	private TextView end_timeValue_text=null;
	private TextView end_dateValue_text=null;
	private TextView daysOfWeekValue_text=null;
	private TextView ally1Value_text=null;
	private TextView ally2Value_text=null;
	private EditText text_typeNoteAfterSchool=null;
	private Button button_deleteAfterSchool=null;

	private Switch mark_special_switch=null;
	private Switch mark_private_switch=null;

	private Button button_pickSellerAfterSchool=null;

	private LinearLayout layout_specialDays=null;
	private TextView specialDayValueValue_text=null;

	private DatePickerDialog datePickerDialog=null;
	private SimpleDateFormat dateFormatter=null;

	protected Dialog builder_time_picker = null;

	private Activity mActivity;

	private int selectedStartYear,selectedStartMonth,selectedStartDay,selectedStartHour,selectedStartMinute;
	private int selectedEndYear,selectedEndMonth,selectedEndDay,selectedEndHour,selectedEndMinute;
	private int specialselectDay,specialselectMonth,specialselectYear;

	private boolean onTouchStartDate=false;
	private boolean onTouchEndDate=false;
	private boolean OnTouchStartTime=false;
	private boolean OnTouchEndTime=false;

	private boolean OnTouchSpecialDate=false;

	/** To make sure which ally we have chosen.*/
	//public static boolean OnTouchAlly1=false;

	/** To make sure which ally we have chosen.*/
	//public static boolean OnTouchAlly2=false;

	private String days="";

	private ArrayList<String> daySelected=new ArrayList<String>();

	/*public static AddAllyInformationOnActivity addAlly1InformationOnActivity=null;
	public static AddAllyInformationOnActivity addAlly2InformationOnActivity=null;
	 */
	private ShowMessages showMessage=null;
	private View line_belowspecialDays=null;
	private Validation checkValidation=null;
	private CheckNetwork checkNetwork=null;
	private ServiceMethod serviceMethod=null;

	private AddAfterSchoolActivities addAfterSchoolActivities=null;

	private boolean onTouchPickSellerButton=false;
	private ImageView activity_doneImage=null;

	private String activityName;
	private int activityId;

	private TextView specialDay_text;

	public static int updatedDataFromAfterSchool = 0;
	private SharePreferenceClass sharePref;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view=inflater.inflate(R.layout.addafterschool_activity, container, false);
		setHasOptionsMenu(true);
		StaticVariables.fragmentIndexFrequencyPage=0;
		mListener.onFragmentAttached(false,"  Scheduler");
		sharePref=new SharePreferenceClass(getActivity());
		mActivity=getActivity();
		StaticVariables.isFrequencySaveClicked=false;
		init();

		reFillValueIfAny();

		clickEvent();

		setDateField();
		setTimeField();
		//initDialog();

		settingDefaultValueDaysDialog();



		return view;		
	}

	/**
	 * 
	 */
	private void settingDefaultValueDaysDialog() {
		/*boolean flag=true;
		for(int i=0;i<StaticVariables.daysSelectedInAfterSchool.length;i++)
		{
			if(StaticVariables.daysSelectedInAfterSchool[i])
			{
				flag=false;
				break;
			}
		}

		if(flag)
		{
			for(int i=0;i<StaticVariables.daysSelectedInAfterSchool.length;i++)
			{
				StaticVariables.daysSelectedInAfterSchool[i]= true;
			}

			StaticVariables.daysSelectedInAfterSchool[7]= false;
			StaticVariables.daysSelectedInAfterSchool[8]= false;
		}

		if(StaticVariables.daysSelectedInAfterSchool[9]==true)
		{
			daySelected.clear();

			daySelected.add("1");
			daySelected.add("2");
			daySelected.add("3");
			daySelected.add("4");
			daySelected.add("5");
			daySelected.add("6");
			daySelected.add("7");

			daysOfWeekValue_text.setText("All days");
		}*/
		/**Frequency mode implementation*/
		if(StaticVariables.addAfterSchoolActivities!=null)
		{
			if(StaticVariables.addAfterSchoolActivities.getfMode()==0)
			{
				daySelected.clear();
				daySelected.add("1");
				daySelected.add("2");
				daySelected.add("3");
				daySelected.add("4");
				daySelected.add("5");
				daySelected.add("6");
				daySelected.add("7");
				daysOfWeekValue_text.setText("All days");
			}
			else if(StaticVariables.addAfterSchoolActivities.getfMode()==1)
			{
				daysOfWeekValue_text.setText("Weekly");
			}
			else if(StaticVariables.addAfterSchoolActivities.getfMode()==2)
			{
				daysOfWeekValue_text.setText("Bi-Weekly");
			}
			else
			{
				/**Used for older activities case**/
				daysOfWeekValue_text.setText(StaticVariables.dayMode);
				/**Used for older activities case**/

			}
		}
		else
		{
			if(updatedDataFromAfterSchool!=1)
			{
				daySelected.clear();
				daySelected.add("1");
				daySelected.add("2");
				daySelected.add("3");
				daySelected.add("4");
				daySelected.add("5");
				daySelected.add("6");
				daySelected.add("7");
				daysOfWeekValue_text.setText("All days");
			}
		}
	}

	private void clickEvent()
	{
		layout_informAlly1.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {

				StaticVariables.addAfterSchoolActivities = addAfterSchoolActivities;

				if(StaticVariables.fragmentIndexCurrentTabSchedular==35)
				{
					StaticVariables.fragmentIndexCurrentTabSchedular=28;
				}
				else if(StaticVariables.fragmentIndexCurrentTabSchedular==42)
				{

					StaticVariables.fragmentIndexCurrentTabSchedular=43;

				}
				else if(StaticVariables.fragmentIndexCurrentTabSchedular==50)
				{

					StaticVariables.fragmentIndexCurrentTabSchedular=51;

				}

				else if(StaticVariables.fragmentIndexCurrentTabSchedular==24)
				{

					StaticVariables.fragmentIndexCurrentTabSchedular=58;

				}

				else if(StaticVariables.fragmentIndexCurrentTabSchedular==67)
				{
					StaticVariables.fragmentIndexCurrentTabSchedular=68;

				}

				else if(StaticVariables.fragmentIndexCurrentTabSchedular==20)
				{
					StaticVariables.fragmentIndexCurrentTabSchedular=76;

				}

				StaticVariables.ActivityIdScheduler=activityId;

				if(text_typeNoteAfterSchool.getText().toString().trim()!=null)
					StaticVariables.addAfterSchoolActivities.setRemarks(text_typeNoteAfterSchool.getText().toString().trim());

				switchingFragments(new DisplayAllyInformationFragment());

				/*
				// TODO Auto-generated method stub

				StaticVariables.addAfterSchoolActivities = addAfterSchoolActivities;

				if(updatedDataFromAfterSchool == 1comesfromWhichScreen!= null && comesfromWhichScreen.equalsIgnoreCase("updateAfterSchoolActivity"))
				{
					if(ally1Value_text.getText().toString().equalsIgnoreCase(""))
					{
						OnTouchAlly1 = true;
						OnTouchAlly2 = false;

						if(StaticVariables.fragmentIndexCurrentTabSchedular==35)
						{
							StaticVariables.fragmentIndexCurrentTabSchedular=28;
						}
						else if(StaticVariables.fragmentIndexCurrentTabSchedular==42)
						{

							StaticVariables.fragmentIndexCurrentTabSchedular=43;

						}
						else if(StaticVariables.fragmentIndexCurrentTabSchedular==50)
						{

							StaticVariables.fragmentIndexCurrentTabSchedular=51;

						}

						else if(StaticVariables.fragmentIndexCurrentTabSchedular==24)
						{

							StaticVariables.fragmentIndexCurrentTabSchedular=58;

						}

						else if(StaticVariables.fragmentIndexCurrentTabSchedular==67)
						{
							StaticVariables.fragmentIndexCurrentTabSchedular=68;

						}

						switchingFragments(new InformAllyFragment());
							getFragmentManager().beginTransaction().add(R.id.realtabcontent,
								new InformAllyFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();
					}
					else
					{
						OnTouchAlly1 = true;
						OnTouchAlly2 = false;

						if(StaticVariables.fragmentIndexCurrentTabSchedular==35)
						{
							StaticVariables.fragmentIndexCurrentTabSchedular=29;
						}
						else if(StaticVariables.fragmentIndexCurrentTabSchedular==42)
						{

							StaticVariables.fragmentIndexCurrentTabSchedular=44;

						}
						else if(StaticVariables.fragmentIndexCurrentTabSchedular==50)
						{

							StaticVariables.fragmentIndexCurrentTabSchedular=52;

						}

						else if(StaticVariables.fragmentIndexCurrentTabSchedular==24)
						{

							StaticVariables.fragmentIndexCurrentTabSchedular=59;

						}

						else if(StaticVariables.fragmentIndexCurrentTabSchedular==67)
						{
							StaticVariables.fragmentIndexCurrentTabSchedular=69;

						}

						switchingFragments(new AllyDropPickFragment());
						getFragmentManager().beginTransaction().add(R.id.realtabcontent,
								new AllyDropPickFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();
					}
				}
				else
				{

					OnTouchAlly1 = true;
					OnTouchAlly2 = false;

					if(StaticVariables.fragmentIndexCurrentTabSchedular==35)
					{
						StaticVariables.fragmentIndexCurrentTabSchedular=28;
					}
					else if(StaticVariables.fragmentIndexCurrentTabSchedular==42)
					{

						StaticVariables.fragmentIndexCurrentTabSchedular=43;

					}

					else if(StaticVariables.fragmentIndexCurrentTabSchedular==50)
					{

						StaticVariables.fragmentIndexCurrentTabSchedular=51;

					}

					else if(StaticVariables.fragmentIndexCurrentTabSchedular==24)
					{

						StaticVariables.fragmentIndexCurrentTabSchedular=58;

					}
					else if(StaticVariables.fragmentIndexCurrentTabSchedular==67)
					{
						StaticVariables.fragmentIndexCurrentTabSchedular=68;

					}


					switchingFragments(new InformAllyFragment());
					getFragmentManager().beginTransaction().add(R.id.realtabcontent,
							new InformAllyFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();
				}
				 */}
		});

		layout_informAlly2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {/*
				// TODO Auto-generated method stub

				StaticVariables.addAfterSchoolActivities = addAfterSchoolActivities;

				if(updatedDataFromAfterSchool == 1comesfromWhichScreen!= null && comesfromWhichScreen.equalsIgnoreCase("updateAfterSchoolActivity"))
				{
					if(ally2Value_text.getText().toString().equalsIgnoreCase(""))
					{
						OnTouchAlly1 = false;
						OnTouchAlly2 = true;

						if(StaticVariables.fragmentIndexCurrentTabSchedular==35)
						{
							StaticVariables.fragmentIndexCurrentTabSchedular=28;
						}
						else if(StaticVariables.fragmentIndexCurrentTabSchedular==42)
						{

							StaticVariables.fragmentIndexCurrentTabSchedular=43;

						}
						else if(StaticVariables.fragmentIndexCurrentTabSchedular==50)
						{

							StaticVariables.fragmentIndexCurrentTabSchedular=51;

						}
						else if(StaticVariables.fragmentIndexCurrentTabSchedular==24)
						{

							StaticVariables.fragmentIndexCurrentTabSchedular=58;

						}

						else if(StaticVariables.fragmentIndexCurrentTabSchedular==67)
						{
							StaticVariables.fragmentIndexCurrentTabSchedular=68;

						}

						switchingFragments(new InformAllyFragment());
						getFragmentManager().beginTransaction().add(R.id.realtabcontent,
								new InformAllyFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();
					}
					else
					{
						OnTouchAlly1 = false;
						OnTouchAlly2 = true;
						if(StaticVariables.fragmentIndexCurrentTabSchedular==35)
						{
							StaticVariables.fragmentIndexCurrentTabSchedular=29;
						}
						else if(StaticVariables.fragmentIndexCurrentTabSchedular==42)
						{

							StaticVariables.fragmentIndexCurrentTabSchedular=44;

						}

						else if(StaticVariables.fragmentIndexCurrentTabSchedular==50)
						{

							StaticVariables.fragmentIndexCurrentTabSchedular=52;

						}

						else if(StaticVariables.fragmentIndexCurrentTabSchedular==24)
						{

							StaticVariables.fragmentIndexCurrentTabSchedular=59;

						}

						else if(StaticVariables.fragmentIndexCurrentTabSchedular==67)
						{
							StaticVariables.fragmentIndexCurrentTabSchedular=69;

						}


						switchingFragments(new AllyDropPickFragment());

						getFragmentManager().beginTransaction().add(R.id.realtabcontent,
								new AllyDropPickFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();
					}
				}
				else
				{
					OnTouchAlly1 = false;
					OnTouchAlly2 = true;

					if(StaticVariables.showAllyName1InAfterSchool)
					{

						if(StaticVariables.fragmentIndexCurrentTabSchedular==35)
						{
							StaticVariables.fragmentIndexCurrentTabSchedular=28;
						}
						else if(StaticVariables.fragmentIndexCurrentTabSchedular==42)
						{

							StaticVariables.fragmentIndexCurrentTabSchedular=43;

						}

						else if(StaticVariables.fragmentIndexCurrentTabSchedular==24)
						{

							StaticVariables.fragmentIndexCurrentTabSchedular=58;

						}

						else if(StaticVariables.fragmentIndexCurrentTabSchedular==67)
						{
							StaticVariables.fragmentIndexCurrentTabSchedular=68;

						}

						switchingFragments(new InformAllyFragment());

						getFragmentManager().beginTransaction().add(R.id.realtabcontent,
								new InformAllyFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();
						 }
					else
					{
						showMessage.showAlert("WARNING", "Please fill details for ally 1.");
					}
				}
			 */}
		});

		layout_daysofWeek.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				showChoiceForDays();
			}
		});

		layout_startDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onTouchStartDate=true;
				datePickerDialog.show();
			}
		});

		layout_endDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onTouchEndDate=true;
				datePickerDialog.show();
			}
		});

		layout_startTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OnTouchStartTime=true;
				showDialog(true);
				//builder_time_picker.show();
			}
		});

		layout_endTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OnTouchEndTime=true;
				showDialog(false);
				//builder_time_picker.show();


			}
		});

		mark_special_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if(mark_special_switch.isChecked())
				{
					//layout_specialDays.setVisibility(View.VISIBLE);
					line_belowspecialDays.setVisibility(View.VISIBLE);
					addAfterSchoolActivities.setIsSpecial("1");
				}
				else
				{
					layout_specialDays.setVisibility(View.GONE);
					line_belowspecialDays.setVisibility(View.GONE);
					addAfterSchoolActivities.setIsSpecial("0");
				}
			}
		});

		mark_private_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if(mark_private_switch.isChecked())
				{
					addAfterSchoolActivities.setIsPrivate("1");
				}
				else
				{
					addAfterSchoolActivities.setIsPrivate("0");
				}
			}
		});

		layout_specialDays.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OnTouchSpecialDate=true;
				datePickerDialog.show();
			}
		});

		button_pickSellerAfterSchool.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Calendar calender1=Calendar.getInstance();
				calender1.set(selectedStartYear, selectedStartMonth, selectedStartDay, selectedStartHour, selectedStartMinute);

				long startDateValue=calender1.getTime().getTime();

				//	Calendar calender2=Calendar.getInstance();
				calender1.set(selectedEndYear, selectedEndMonth, selectedEndDay, selectedEndHour, selectedEndMinute);
				long endDateValue=calender1.getTime().getTime();

				long totalValue=(endDateValue - startDateValue);

				Calendar calenderCurrentYear=Calendar.getInstance();

				int year = calenderCurrentYear.get(Calendar.YEAR);
				int month = calenderCurrentYear.get(Calendar.MONTH);
				int day = calenderCurrentYear.get(Calendar.DAY_OF_MONTH);

				if(mark_special_switch.isChecked() && !checkValidation.isNotNullOrBlank(specialDayValueValue_text.getText().toString()))
				{
					//if you select date in past
					showMessage.showAlert("Incomplete Data", "This activity is marked special. You must set a special date - like the date for stage performance, a finale or a field trip. You can always come back and change this.");
				}
				/*else if(mark_special_switch.isChecked() && specialselectYear < year)
					showMessage.showAlert("Warning", "Please select valid year");*/

				/*else if(mark_special_switch.isChecked() && specialselectYear <= year && (specialselectMonth < month))
					showMessage.showAlert("Warning", "Please select valid month");	
				else if(mark_special_switch.isChecked() && specialselectMonth <= month && specialselectDay < day)
					showMessage.showAlert("Warning", "Please select valid day");*/
				else if(!checkValidation.isNotNullOrBlank(start_dateValue_text.getText().toString()))
					showMessage.showAlert("Warning", "Oops! You left a few important fields blank.");
				else if(selectedStartYear < year)
					showMessage.showAlert("Invalid Data", "Activities cannot be scheduled for a date or  time in the past.");

				else if(selectedStartYear <= year && selectedStartMonth < month)
					showMessage.showAlert("Invalid Data", "Activities cannot be scheduled for a date or  time in the past.");

				else if(selectedStartMonth <= month && selectedStartDay < day)
					showMessage.showAlert("Invalid Data", "Activities cannot be scheduled for a date or  time in the past.");

				else if(!checkValidation.isNotNullOrBlank(end_dateValue_text.getText().toString()))
					showMessage.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");

				else if(selectedEndYear < selectedStartYear)
					showMessage.showAlert("Invalid Data", "Activities cannot be scheduled for a date or  time in the past.");

				else if(selectedEndYear <= selectedStartYear && selectedEndMonth < selectedStartMonth)
					showMessage.showAlert("Invalid Data", "Activities cannot be scheduled for a date or  time in the past.");

				else if(selectedEndMonth <= selectedStartMonth && selectedEndDay<selectedStartDay)
					showMessage.showAlert("Invalid Data", "Activities cannot be scheduled for a date or  time in the past.");

				else if(!checkValidation.isNotNullOrBlank(start_timeValue_text.getText().toString()))
					showMessage.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");

				else if(!checkValidation.isNotNullOrBlank(end_timeValue_text.getText().toString()))
					showMessage.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");

				else if(!checkValidation.isNotNullOrBlank(daysOfWeekValue_text.getText().toString()))
					showMessage.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");

				else if(startDateValue < System.currentTimeMillis() -(30 * 1000))
					showMessage.showAlert("Invalid Data", "Activities cannot be scheduled for a date or  time in the past.");

				else if(totalValue < 3600000 )
					showMessage.showAlert("Warning", "End time should be greater than one hour from Start time");

				else if(daySelected.size()==0)
					showMessage.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");
				else
				{
					onTouchPickSellerButton=true;

					//	if(StaticVariables.addAfterSchoolActivities!=null  )
					{
						days = "";

						daySelected=removeDupliciacy(daySelected);//changed for duplicacy
						for(int i=0;i<daySelected.size();i++)
						{
							if(i==daySelected.size()-1)
							{
								days+=daySelected.get(i)+"";
							}
							else
							{
								days+=daySelected.get(i)+",";
							}
						}
					}

					if(mark_private_switch.isChecked())
					{
						addAfterSchoolActivities.setIsPrivate("1");
					}
					else
					{
						addAfterSchoolActivities.setIsPrivate("0");
					}

					if(mark_special_switch.isChecked())
					{
						addAfterSchoolActivities.setIsSpecial("1");
					}
					else
					{
						addAfterSchoolActivities.setIsSpecial("0");
					}

					if(StaticVariables.addAfterSchoolActivities!=null)
					{
						addAfterSchoolActivities.setActivityDays(StaticVariables.addAfterSchoolActivities.getActivityDays());
						addAfterSchoolActivities.setfMode(StaticVariables.addAfterSchoolActivities.getfMode());
						addAfterSchoolActivities.setBWFMode(StaticVariables.addAfterSchoolActivities.getBWFMode());
					}
					else
						addAfterSchoolActivities.setActivityDays(days);
					addAfterSchoolActivities.setEnddate(end_dateValue_text.getText().toString());
					addAfterSchoolActivities.setStartDate(start_dateValue_text.getText().toString());
					addAfterSchoolActivities.setStartTime(start_timeValue_text.getText().toString());
					addAfterSchoolActivities.setEndTime(end_timeValue_text.getText().toString());
					addAfterSchoolActivities.setRemarks(text_typeNoteAfterSchool.getText().toString());
					addAfterSchoolActivities.setExamDate(start_dateValue_text.getText().toString());
					addAfterSchoolActivities.setSpecialDate(specialDayValueValue_text.getText().toString());
					/*addAfterSchoolActivities.setAllyName1(ally1Value_text.getText().toString());
					addAfterSchoolActivities.setAllyName2(ally2Value_text.getText().toString());
					 */
					new AddAfterSchoolTask().execute();

				}
			}
		});
	}

	private void init()
	{
		onTouchPickSellerButton=false;

		StaticVariables.fragmentIndex=8;

		/*OnTouchAlly1=false;
		OnTouchAlly2=false;
		 */
		OnTouchSpecialDate=false;

		addAfterSchoolActivities=new AddAfterSchoolActivities();

		if(getArguments()!=null) 
		{
			activityName = getArguments().getString("ActivityName");  
			activityId=getArguments().getInt("ActivityId");
			comesfromWhichScreen=getArguments().getString("ComingFromWhichScreen");

			if(comesfromWhichScreen!=null && comesfromWhichScreen.equalsIgnoreCase("updateAfterSchoolActivity"))
			{
				updatedDataFromAfterSchool = 1;
				addAfterSchoolActivities.setIsUpdate(1);
			}
		}


		checkValidation=new Validation();
		checkNetwork=new CheckNetwork();
		serviceMethod=new ServiceMethod();

		showMessage=new ShowMessages(mActivity);

		subCategory_text=(TextView) view.findViewById(R.id.subcategoryName_text);
		subcategoryByCatid_text=(TextView) view.findViewById(R.id.subcategoryNamebyCatid_text);
		ally1Value_text=(TextView) view.findViewById(R.id.ally1Value_text);
		ally2Value_text=(TextView) view.findViewById(R.id.ally2Value_text);

		dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		activity_doneImage=(ImageView) view.findViewById(R.id.image_activityAfterSchoolDone);
		layout_startDate=(LinearLayout) view.findViewById(R.id.layout_startDate);
		layout_startTime=(LinearLayout) view.findViewById(R.id.layout_startTime);
		layout_endDate=(LinearLayout) view.findViewById(R.id.layout_endDate);
		layout_endTime=(LinearLayout) view.findViewById(R.id.layout_endTime);
		layout_daysofWeek=(LinearLayout) view.findViewById(R.id.layout_daysofWeek);
		layout_informAlly1=(LinearLayout) view.findViewById(R.id.layout_informAlly1);
		layout_informAlly2=(LinearLayout) view.findViewById(R.id.layout_informAlly2);
		layout_specialDays=(LinearLayout) view.findViewById(R.id.layout_specialDays);
		start_dateValue_text=(TextView) view.findViewById(R.id.start_dateValue_text);
		start_timeValue_text=(TextView) view.findViewById(R.id.start_timeValue_text);
		end_timeValue_text=(TextView) view.findViewById(R.id.end_timeValue_text);
		end_dateValue_text=(TextView) view.findViewById(R.id.end_dateValue_text);
		daysOfWeekValue_text=(TextView) view.findViewById(R.id.daysOfWeekValue_text);
		mark_private_switch=(Switch) view.findViewById(R.id.mark_private_switch);
		mark_special_switch=(Switch) view.findViewById(R.id.mark_special_switch);
		specialDayValueValue_text=(TextView) view.findViewById(R.id.specialDayValueValue_text);
		button_pickSellerAfterSchool=(Button) view.findViewById(R.id.button_pickSellerAfterSchool);
		text_typeNoteAfterSchool=(EditText) view.findViewById(R.id.text_typeNoteAfterSchool);
		line_belowspecialDays=view.findViewById(R.id.line_belowspecialDays);
		button_deleteAfterSchool=(Button) view.findViewById(R.id.button_deleteAfterSchool);

		start_date_text=(TextView) view.findViewById(R.id.start_date_text);
		start_time_text=(TextView) view.findViewById(R.id.start_time_text);
		end_time_text=(TextView) view.findViewById(R.id.end_time_text);
		end_date_text=(TextView) view.findViewById(R.id.end_date_text);
		daysOfWeek_text=(TextView) view.findViewById(R.id.daysOfWeek_text);
		reminderInfo_text=(TextView) view.findViewById(R.id.reminderInfo_text);;
		mark_private_text=(TextView) view.findViewById(R.id.mark_private_text);;
		mark_special_text=(TextView) view.findViewById(R.id.mark_special_text);;
		ally1_text=(TextView) view.findViewById(R.id.ally1_text);;
		ally2_text=(TextView) view.findViewById(R.id.ally2_text);;
		noteAfterSchool_text=(TextView) view.findViewById(R.id.noteAfterSchool_text);;
		specialDay_text =(TextView) view.findViewById(R.id.specialDay_text);;

		typeFace.setTypefaceRegular(subCategory_text);
		typeFace.setTypefaceRegular(subcategoryByCatid_text);
		typeFace.setTypefaceRegular(ally1Value_text);
		typeFace.setTypefaceRegular(ally2Value_text);
		typeFace.setTypefaceRegular(start_date_text);
		typeFace.setTypefaceRegular(start_time_text);
		typeFace.setTypefaceRegular(end_time_text);
		typeFace.setTypefaceRegular(end_date_text);
		typeFace.setTypefaceRegular(daysOfWeek_text);
		typeFace.setTypefaceRegular(reminderInfo_text);
		typeFace.setTypefaceRegular(mark_private_text);
		typeFace.setTypefaceRegular(mark_special_text);
		typeFace.setTypefaceRegular(ally1_text);
		typeFace.setTypefaceRegular(ally2_text);
		typeFace.setTypefaceRegular(noteAfterSchool_text);

		typeFace.setTypefaceRegular(start_dateValue_text);
		typeFace.setTypefaceRegular(start_timeValue_text);
		typeFace.setTypefaceRegular(end_timeValue_text);
		typeFace.setTypefaceRegular(end_dateValue_text);
		typeFace.setTypefaceRegular(specialDay_text);
		typeFace.setTypefaceRegular(specialDayValueValue_text);
		typeFace.setTypefaceRegular(text_typeNoteAfterSchool);
		typeFace.setTypefaceRegular(daysOfWeekValue_text);

		typeFace.setTypefaceRegular(button_pickSellerAfterSchool);
		typeFace.setTypefaceRegular(button_deleteAfterSchool);

		subCategory_text.setText(StaticVariables.subCategoryName);
		subcategoryByCatid_text.setText(StaticVariables.subSubCategoryName);

		if(updatedDataFromAfterSchool == 1/*comesfromWhichScreen!= null && comesfromWhichScreen.equalsIgnoreCase("updateAfterSchoolActivity")*/)
		{
			subCategory_text.setText("After School ");
			activityName=StaticVariables.subSubCategoryName;
			subcategoryByCatid_text.setText(activityName);
			//subcategoryByCatid_text.setText(StaticVariables.subSubCategoryName);
			activity_doneImage.setVisibility(View.VISIBLE);
			button_deleteAfterSchool.setVisibility(View.VISIBLE);
			button_deleteAfterSchool.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					new DeleteScheduledActivityAsync().execute();

				}
			});
			new GetAfterSchoolActivityDetail(StaticVariables.currentChild.getChildID(),String.valueOf(activityId)).execute();
		}

		/*if(getArguments()!=null)
		{
			if(StaticVariables.showAllyName1InAfterSchool)
			{
				ally1Value_text.setText(StaticVariables.allySetFor1.getAllyName());
				addAlly1InformationOnActivity=(AddAllyInformationOnActivity) getArguments().get("addAllyInformationOnActivity");
			}

			if(StaticVariables.showAllyName2InAfterSchool)
			{
				ally2Value_text.setText(StaticVariables.allySetFor2.getAllyName());
				addAlly2InformationOnActivity=(AddAllyInformationOnActivity) getArguments().get("addAllyInformationOnActivity");
			}
		}*/

		/*if(addAlly1InformationOnActivity!=null)
		{
			if(StaticVariables.allySetFor1!=null)
			ally1Value_text.setText(StaticVariables.allySetFor1.getAllyName());
		}

		if(addAlly2InformationOnActivity!=null)
		{
			if(StaticVariables.allySetFor2!=null)
			ally2Value_text.setText(StaticVariables.allySetFor2.getAllyName());
		}
		 */
		addAfterSchoolActivities.setChildID(StaticVariables.currentChild.getChildID());
		addAfterSchoolActivities.setActivityID(StaticVariables.subSubCategoryId);
	}

	private void reFillValueIfAny() 
	{
		// TODO Auto-generated method stub
		if(StaticVariables.addAfterSchoolActivities!=null)
		{
			addAfterSchoolActivities = StaticVariables.addAfterSchoolActivities;
			if(StaticVariables.addAfterSchoolActivities.getStartDate()!=null && !StaticVariables.addAfterSchoolActivities.getStartDate().trim().equalsIgnoreCase(""))
			{
				start_dateValue_text.setText(StaticVariables.addAfterSchoolActivities.getStartDate());
				String[] startDate = StaticVariables.addAfterSchoolActivities.getStartDate().split("/");

				for(int i=0;i<startDate.length;i++)
				{
					selectedStartDay=Integer.parseInt(startDate[0]);
					selectedStartMonth=(Integer.parseInt(startDate[1]))-1;
					selectedStartYear=Integer.parseInt(startDate[2]);
				}
			}

			if(StaticVariables.addAfterSchoolActivities.getEnddate()!=null && !StaticVariables.addAfterSchoolActivities.getEnddate().trim().equalsIgnoreCase(""))
			{
				end_dateValue_text.setText(StaticVariables.addAfterSchoolActivities.getEnddate());

				String[] endDate = StaticVariables.addAfterSchoolActivities.getEnddate().split("/");

				for(int i=0;i<endDate.length;i++)
				{
					selectedEndDay=Integer.parseInt(endDate[0]);
					selectedEndMonth=(Integer.parseInt(endDate[1]))-1;
					selectedEndYear=Integer.parseInt(endDate[2]);
				}
			}

			if(StaticVariables.addAfterSchoolActivities.getStartTime()!=null && !StaticVariables.addAfterSchoolActivities.getStartTime().trim().equalsIgnoreCase(""))
			{
				start_timeValue_text.setText(StaticVariables.addAfterSchoolActivities.getStartTime());
				//convert this value to 24 hr format
				//11:01 AM
				selectedStartHour = Integer.parseInt(StaticVariables.addAfterSchoolActivities.getStartTime().split(":")[0]);
				selectedStartMinute = Integer.parseInt(StaticVariables.addAfterSchoolActivities.getStartTime().split(":")[1].substring(0, 1).trim());

				//convert this value to 24 hr format
				//11:01 AM
				/*selectedStartHour = Integer.parseInt(StaticVariables.addAfterSchoolActivities.getStartTime().split(":")[0]);
				selectedStartMinute = Integer.parseInt(StaticVariables.addAfterSchoolActivities.getStartTime().split(":")[1].split(" ")[0]);
				String AMOrPM = StaticVariables.addAfterSchoolActivities.getStartTime().split(":")[1].split(" ")[1];
				if(AMOrPM.equalsIgnoreCase("PM"))
				{
					selectedStartHour+=12;
				}*/

			}

			if(StaticVariables.addAfterSchoolActivities.getEndTime()!=null && !StaticVariables.addAfterSchoolActivities.getEndTime().trim().equalsIgnoreCase(""))
			{

				end_timeValue_text.setText(StaticVariables.addAfterSchoolActivities.getEndTime());

				selectedEndHour = Integer.parseInt(StaticVariables.addAfterSchoolActivities.getEndTime().split(":")[0]);
				selectedEndMinute = Integer.parseInt(StaticVariables.addAfterSchoolActivities.getEndTime().split(":")[1].substring(0, 1).trim());


				/*selectedEndHour = Integer.parseInt(StaticVariables.addAfterSchoolActivities.getEndTime().split(":")[0]);
				selectedEndMinute = Integer.parseInt(StaticVariables.addAfterSchoolActivities.getEndTime().split(":")[1].split(" ")[0]);
				String AMOrPMEnd = StaticVariables.addAfterSchoolActivities.getEndTime().split(":")[1].split(" ")[1];
				if(AMOrPMEnd.equalsIgnoreCase("PM"))
				{
					selectedEndHour+=12;
				}*/
			}

			if(StaticVariables.addAfterSchoolActivities.getActivityDays()!=null && !StaticVariables.addAfterSchoolActivities.getActivityDays().trim().equalsIgnoreCase(""))
			{
				daysOfWeekValue_text.setText(StaticVariables.addAfterSchoolActivities.getActivityDays());
				String dayValue=StaticVariables.addAfterSchoolActivities.getActivityDays();

				String[] daySelectedValue=dayValue.split(",");

				for(int i=0;i<daySelectedValue.length;i++)
				{
					daySelected.add(daySelectedValue[i]);
				}
			}

			if(StaticVariables.addAfterSchoolActivities.getIsPrivate().equalsIgnoreCase("1"))
			{
				mark_private_switch.setChecked(true);
				addAfterSchoolActivities.setIsPrivate("1");
			}
			else if(StaticVariables.addAfterSchoolActivities.getIsPrivate().equalsIgnoreCase("0"))
			{
				mark_private_switch.setChecked(false);
				addAfterSchoolActivities.setIsPrivate("0");
			}

			if(StaticVariables.addAfterSchoolActivities.getIsSpecial().equalsIgnoreCase("1"))
			{
				mark_special_switch.setChecked(true);
				addAfterSchoolActivities.setIsSpecial("1");
			}
			else if(StaticVariables.addAfterSchoolActivities.getIsSpecial().equalsIgnoreCase("0"))
			{
				mark_special_switch.setChecked(false);
				addAfterSchoolActivities.setIsSpecial("0");
			}

			if(mark_special_switch.isChecked())
			{
				//layout_specialDays.setVisibility(View.VISIBLE);
				line_belowspecialDays.setVisibility(View.VISIBLE);

				if(StaticVariables.addAfterSchoolActivities.getSpecialDate()!=null && !StaticVariables.addAfterSchoolActivities.getSpecialDate().trim().equalsIgnoreCase(""))
				{
					specialDayValueValue_text.setText(StaticVariables.addAfterSchoolActivities.getSpecialDate());

					String[] specialDate = StaticVariables.addAfterSchoolActivities.getSpecialDate().split("/");

					for(int i=0;i<specialDate.length;i++)
					{
						specialselectDay=Integer.parseInt(specialDate[0]);
						specialselectMonth=(Integer.parseInt(specialDate[1]))-1;
						specialselectYear=Integer.parseInt(specialDate[2]);
					}
				}
			}

			/*if(StaticVariables.daysSelectedInAfterSchool[7]==true)
			{
				daysOfWeekValue_text.setText("Weekdays");
			}
			else if(StaticVariables.daysSelectedInAfterSchool[8]==true)
			{
				daysOfWeekValue_text.setText("Weekend");
			}
			else if(StaticVariables.daysSelectedInAfterSchool[9]==true)
			{
				daysOfWeekValue_text.setText("All days");
			}
			else if(StaticVariables.daysSelectedInAfterSchool[0]==true || StaticVariables.daysSelectedInAfterSchool[1]==true || StaticVariables.daysSelectedInAfterSchool[2]==true
					|| StaticVariables.daysSelectedInAfterSchool[3]==true || StaticVariables.daysSelectedInAfterSchool[4]==true || StaticVariables.daysSelectedInAfterSchool[5]==true
					|| StaticVariables.daysSelectedInAfterSchool[6]==true)
			{
				daysOfWeekValue_text.setText("Specific Day");
			}

			else if(StaticVariables.daysSelectedInAfterSchool[0]==true || StaticVariables.daysSelectedInAfterSchool[1]==true || StaticVariables.daysSelectedInAfterSchool[2]==true
					|| StaticVariables.daysSelectedInAfterSchool[3]==true || StaticVariables.daysSelectedInAfterSchool[4]==true || StaticVariables.daysSelectedInAfterSchool[5]==true
					|| StaticVariables.daysSelectedInAfterSchool[6]==true)
			{
				daysOfWeekValue_text.setText("Specific Day");
			}
			 */
			/**Frequency mode implementation*/
			if(StaticVariables.addAfterSchoolActivities.getfMode()==0)
			{
				daysOfWeekValue_text.setText("All days");
			}
			else if(StaticVariables.addAfterSchoolActivities.getfMode()==1)
			{
				daysOfWeekValue_text.setText("Weekly");
			}
			else if(StaticVariables.addAfterSchoolActivities.getfMode()==2)
			{
				daysOfWeekValue_text.setText("Bi-Weekly");
			}

			/**Frequency mode implementation*/

			addAfterSchoolActivities.setActivityDays(StaticVariables.addAfterSchoolActivities.getActivityDays());

			text_typeNoteAfterSchool.setText(StaticVariables.addAfterSchoolActivities.getRemarks());	
		}
		else
		{
			addAfterSchoolActivities.setIsPrivate("0");
			addAfterSchoolActivities.setIsSpecial("0");
		}
	}

	private void showDialog(boolean isStartTime)
	{
		// TODO Auto-generated method stub

		if(builder_time_picker != null){
			builder_time_picker.dismiss();
			builder_time_picker = null;
		}

		builder_time_picker = new Dialog(mActivity);
		builder_time_picker.setTitle("Select Time:");
		LayoutInflater li = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = li.inflate(R.layout.dialog_time_picker, null);

		final TimePicker mTimePicker = (TimePicker) view.findViewById(R.id.time_picker);

		Calendar c = Calendar.getInstance();

		mTimePicker.setIs24HourView(true);
		mTimePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
		mTimePicker.setCurrentMinute(c.get(Calendar.MINUTE));

		if(isStartTime)
		{
			if(start_timeValue_text.getText().toString().trim().length() > 0){
				try{
					mTimePicker.setCurrentHour(Integer.parseInt(start_timeValue_text.getText().toString().split(":")[0]));
					mTimePicker.setCurrentMinute(Integer.parseInt(start_timeValue_text.getText().toString().split(":")[1]));
				}catch(Exception e){

				}
			}
		}else{
			if(end_timeValue_text.getText().toString().trim().length() > 0){
				try{
					mTimePicker.setCurrentHour(Integer.parseInt(end_timeValue_text.getText().toString().split(":")[0]));
					mTimePicker.setCurrentMinute(Integer.parseInt(end_timeValue_text.getText().toString().split(":")[1]));
				}catch(Exception e){

				}
			}
		}


		Button cancel_time = (Button) view.findViewById(R.id.cancel_time);
		cancel_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(builder_time_picker != null){
					builder_time_picker.dismiss();
					builder_time_picker = null;
				}
			}
		});

		Button setTime = (Button) view.findViewById(R.id.set_time);
		setTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(builder_time_picker != null){
					builder_time_picker.dismiss();
					builder_time_picker = null;
				}
				DateFormat dateFormat = new SimpleDateFormat("HH:mm");
				Date date = null;
				try {
					date = dateFormat.parse(mTimePicker.getCurrentHour()+":"+mTimePicker.getCurrentMinute());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//String dateToSet = new SimpleDateFormat("hh:mm aa").format(date);
				String dateToSet = dateFormat.format(date);

				// Adding time for end time


				if(OnTouchStartTime)
				{
					start_timeValue_text.setText(dateToSet);
					OnTouchStartTime=false;
					selectedStartHour = mTimePicker.getCurrentHour();
					selectedStartMinute = mTimePicker.getCurrentMinute();

					addAfterSchoolActivities.setStartTime(start_timeValue_text.getText().toString());

					if(end_timeValue_text.getText().toString().trim().length()==0)
					{
						try {
							date = dateFormat.parse((mTimePicker.getCurrentHour()+1)+":"+mTimePicker.getCurrentMinute());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						//String dateToSetEndTime = new SimpleDateFormat("hh:mm aa").format(date);
						String dateToSetEndTime = dateFormat.format(date);
						end_timeValue_text.setText(dateToSetEndTime);
						OnTouchEndTime=false;
						selectedEndHour = mTimePicker.getCurrentHour()+1;
						selectedEndMinute = mTimePicker.getCurrentMinute();
						addAfterSchoolActivities.setEndTime(end_timeValue_text.getText().toString());
					}
				}

				if(OnTouchEndTime)
				{
					end_timeValue_text.setText(dateToSet);
					OnTouchEndTime=false;
					selectedEndHour = mTimePicker.getCurrentHour();
					selectedEndMinute = mTimePicker.getCurrentMinute();

					addAfterSchoolActivities.setEndTime(end_timeValue_text.getText().toString());
				}
			}
		});

		builder_time_picker.setContentView(view);
		builder_time_picker.show();

	}

	private void setTimeField()
	{    
		layout_startTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OnTouchStartTime=true;
				showDialog(true);
				//builder_time_picker.show();
			}
		});

		layout_endTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OnTouchEndTime=true;
				showDialog(false);
				//builder_time_picker.show();
			}
		});
	}

	private void showChoiceForDays() {
		/**Added for frequency page after school new changes */
		StaticVariables.addAfterSchoolActivities = addAfterSchoolActivities;
		StaticVariables.ActivityIdScheduler=activityId;
		StaticVariables.fragmentIndexFrequencyPage=1000;
		if(text_typeNoteAfterSchool.getText().toString().trim()!=null)
			StaticVariables.addAfterSchoolActivities.setRemarks(text_typeNoteAfterSchool.getText().toString().trim());

		switchingFragments(new FrequencyAfterSchoolFragment());	
		/**Added for frquency page after school new changes */



		/*AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);

		builder.setTitle("Select Days For Activity");

		builder.setMultiChoiceItems(StaticVariables.daysInAfterSchool, StaticVariables.daysSelectedInAfterSchool, new OnMultiChoiceClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				// TODO Auto-generated method stub

				System.out.println("value of switch case"+which);

				StaticVariables.daysSelectedInAfterSchool[which]= isChecked;

				switch (which) 
				{
				case 0:
					//daysValue.add(1);
					rebuildDialog(dialog);




					break;

				case 1:
					//daysValue.add(2);
					rebuildDialog(dialog);



					break;

				case 2:
					//daysValue.add(3);   
					rebuildDialog(dialog);

					break;

				case 3:
					//daysValue.add(4);
					rebuildDialog(dialog);

					break;

				case 4:
					//daysValue.add(5);
					rebuildDialog(dialog);

					break;

				case 5:
					//daysValue.add(6);
					rebuildDialog(dialog);

					break;

				case 6:
					//	daysValue.add(7);
					rebuildDialog(dialog);


					break;

				case 7:

					for(int i=0;i<6;i++)
					{
						StaticVariables.daysSelectedInAfterSchool[1]= isChecked;
						StaticVariables.daysSelectedInAfterSchool[2]= isChecked;
						StaticVariables.daysSelectedInAfterSchool[3]= isChecked;
						StaticVariables.daysSelectedInAfterSchool[4]= isChecked;
						StaticVariables.daysSelectedInAfterSchool[5]= isChecked;
					}


					StaticVariables.daysSelectedInAfterSchool[0]= false;
					StaticVariables.daysSelectedInAfterSchool[6]= false;
					StaticVariables.daysSelectedInAfterSchool[8]= false;
					StaticVariables.daysSelectedInAfterSchool[9]= false;

					dialog.dismiss();

					showChoiceForDays();

					break;

				case 8:

					for(int i=0;i<2;i++)
					{
						StaticVariables.daysSelectedInAfterSchool[0]= isChecked;
						StaticVariables.daysSelectedInAfterSchool[6]= isChecked;
					}

					StaticVariables.daysSelectedInAfterSchool[1]= false;
					StaticVariables.daysSelectedInAfterSchool[2]= false;
					StaticVariables.daysSelectedInAfterSchool[3]= false;
					StaticVariables.daysSelectedInAfterSchool[4]= false;
					StaticVariables.daysSelectedInAfterSchool[5]= false;
					StaticVariables.daysSelectedInAfterSchool[7]= false;
					StaticVariables.daysSelectedInAfterSchool[9]= false;

					dialog.dismiss();

					showChoiceForDays();
					break;

				case 9:

					for(int i=0;i<7;i++)
					{
						StaticVariables.daysSelectedInAfterSchool[i]= isChecked;
					}

					StaticVariables.daysSelectedInAfterSchool[7]= false;
					StaticVariables.daysSelectedInAfterSchool[8]= false;

					dialog.dismiss();

					showChoiceForDays();

					break;
				} 

			}
		});

		builder.setPositiveButton(" Ok ", new DialogInterface.OnClickListener() 
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();

				//weekdays
				if(StaticVariables.daysSelectedInAfterSchool[7]==true)
				{
					daySelected.clear();

					daySelected.add("2");
					daySelected.add("3");
					daySelected.add("4");
					daySelected.add("5");
					daySelected.add("6");

					daysOfWeekValue_text.setText("Weekdays");
				}

				//weekend
				else if(StaticVariables.daysSelectedInAfterSchool[8]==true)
				{
					daySelected.clear();

					daySelected.add("1");
					daySelected.add("7");

					daysOfWeekValue_text.setText("Weekend");
				}

				//all days
				else if(StaticVariables.daysSelectedInAfterSchool[9]==true)
				{
					daySelected.clear();

					daySelected.add("1");
					daySelected.add("2");
					daySelected.add("3");
					daySelected.add("4");
					daySelected.add("5");
					daySelected.add("6");
					daySelected.add("7");

					daysOfWeekValue_text.setText("All days");	
				}
				//normal days
				else
				{
					daySelected.clear();

					if(StaticVariables.daysSelectedInAfterSchool[0]==true)
					{
						daySelected.add("1");
						daysOfWeekValue_text.setText("Specific Day");
					}
					if(StaticVariables.daysSelectedInAfterSchool[1]==true)
					{
						daySelected.add("2");
						daysOfWeekValue_text.setText("Specific Day");
					}
					if(StaticVariables.daysSelectedInAfterSchool[2]==true)
					{
						daySelected.add("3");
						daysOfWeekValue_text.setText("Specific Day");
					}
					if(StaticVariables.daysSelectedInAfterSchool[3]==true)
					{
						daySelected.add("4");
						daysOfWeekValue_text.setText("Specific Day");
					}
					if(StaticVariables.daysSelectedInAfterSchool[4]==true)
					{
						daySelected.add("5");
						daysOfWeekValue_text.setText("Specific Day");
					}
					if(StaticVariables.daysSelectedInAfterSchool[5]==true)
					{
						daySelected.add("6");
						daysOfWeekValue_text.setText("Specific Day");
					}
					if(StaticVariables.daysSelectedInAfterSchool[6]==true)
					{
						daySelected.add("7");
						daysOfWeekValue_text.setText("Specific Day");
					}
					if(StaticVariables.daysSelectedInAfterSchool[0]==false && StaticVariables.daysSelectedInAfterSchool[1]==false&& StaticVariables.daysSelectedInAfterSchool[2]==false && StaticVariables.daysSelectedInAfterSchool[3]==false && StaticVariables.daysSelectedInAfterSchool[4]==false && StaticVariables.daysSelectedInAfterSchool[5]==false && StaticVariables.daysSelectedInAfterSchool[6]==false)
					{

						daysOfWeekValue_text.setText("");
					}
				}

				for(int i=0;i<daySelected.size();i++)
				{
					if(i==daySelected.size()-1)
					{
						days+=daySelected.get(i)+"";
					}
					else
					{
						days+=daySelected.get(i)+",";
					}
				}
				addAfterSchoolActivities.setActivityDays(days);
			}
		});	

		builder.setNegativeButton(" Cancel ", new DialogInterface.OnClickListener() 
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});	

		AlertDialog alert = builder.create();
		alert.show();*/
	}



	private void setDateField() 
	{
		layout_startDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onTouchStartDate=true;
				datePickerDialog.show();
			}
		});

		layout_endDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onTouchEndDate=true;
				datePickerDialog.show();
			}
		});

		layout_specialDays.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OnTouchSpecialDate=true;
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

				if(onTouchStartDate)
				{
					start_dateValue_text.setText(dateFormatter.format(newDate.getTime()));
					onTouchStartDate=false;
					selectedStartYear=year;
					selectedStartMonth=monthOfYear;
					selectedStartDay=dayOfMonth;

					addAfterSchoolActivities.setStartDate(start_dateValue_text.getText().toString());
				}

				if(onTouchEndDate)
				{
					end_dateValue_text.setText(dateFormatter.format(newDate.getTime()));
					onTouchEndDate=false;
					selectedEndYear=year;
					selectedEndMonth=monthOfYear;
					selectedEndDay=dayOfMonth;

					addAfterSchoolActivities.setEnddate(end_dateValue_text.getText().toString());
				}

				if(OnTouchSpecialDate)
				{
					specialDayValueValue_text.setText(dateFormatter.format(newDate.getTime()));
					OnTouchSpecialDate=false;
					specialselectYear=year;
					specialselectMonth=monthOfYear;
					specialselectDay=dayOfMonth;

					addAfterSchoolActivities.setSpecialDate(specialDayValueValue_text.getText().toString());
				}				
			}
		}, newCalendar
		.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

		datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 10000);

	}

	private ProgressDialog progressDialog=null;	

	private class AddAfterSchoolTask extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		int Errorcode=0;
		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub



			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				Errorcode=serviceMethod.AddAfterSchoolActivity(addAfterSchoolActivities);
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

			onTouchPickSellerButton=false;

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(getActivity()))
					new AddAfterSchoolTask().execute();
			}
			else
			{
				if(result!=-1)
				{
					/*if(StaticVariables.showAllyName1InAfterSchool)
					{
						new AddAllyInformationTask(1).execute();
					}
					else
					{*/
					if(Errorcode==0)
					{
						resetStaticValues();
						//getError();
						StaticVariables.fragmentIndexCurrentTabSchedular = 12;
						if(sharePref.isFirstTimeActivityScheduled(StaticVariables.currentChild.getChildID()+"")==0)
						{
							sharePref.setFirstTimeActivitySchedule(1, StaticVariables.currentChild.getChildID()+"");
							social.scheduleFirstActivityFacebookLog();
							social.scheduleFirstActivityGoogleAnalyticsLog();
						}
						else
						{
							social.scheduleAfterSchoolActivityFacebookLog();
							social.scheduleAfterSchoolActivityGoogleAnalyticsLog();
						}

						switchingFragments(new AfterSchoolActivityByChildIdFragment());
					}
					else
					{
						getError();
					}
					//}

				}
			}
		}
	}

	private void getError()
	{
		Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());
	} 
	/*private void getError()
	{
		Error err = serviceMethod.getError();	
		//showMessage.showAlert("Warning", err.getErrorDesc());
		showMessage.showAlertAndMove("Confirmtion", err.getErrorDesc(),"AfterSchoolActivityAdded",getFragmentManager());
	} */
	/*
	private class AddAllyInformationTask extends AsyncTask<Void, Void, Integer>
	{
		private int ally=0;

		public AddAllyInformationTask(int i) {
			// TODO Auto-generated constructor stub
			ally = i;
		}

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
				if(ally==1)
				{
					//Errorcode=serviceMethod.AddAllyInformation(addAlly1InformationOnActivity);
				}
				else if(ally==2)
				{
					//Errorcode=serviceMethod.AddAllyInformation(addAlly2InformationOnActivity);
				}
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
					new AddAllyInformationTask(ally).execute();
			}
			else
			{
				if(result!=-1)
				{
					//second ally ka result
					if(ally==2)
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
						{
							new AddAllyInformationTask(2).execute();
						}
					}


					if(StaticVariables.showAllyName2InAfterSchool)
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
					}

					if(StaticVariables.showAllyName1InAfterSchool)
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
					}
				}
			}
		}


	}*/
	private void resetStaticValues()
	{
		StaticVariables.showAllyName1InAfterSchool=false;
		StaticVariables.showAllyName2InAfterSchool=false;

		if(StaticVariables.addAfterSchoolActivities!=null)
			StaticVariables.addAfterSchoolActivities=null;

		updatedDataFromAfterSchool = 0;
		/*OnTouchAlly1=false;
		OnTouchAlly2=false;
		 */
		/*	addAlly1InformationOnActivity = null;
		addAlly2InformationOnActivity = null;
		 */}

	private class GetAfterSchoolActivityDetail extends AsyncTask<Void, Void, Integer>
	{
		AfterSchoolActivityDetails afterSchoolActivityDetails;

		int childId;
		String activityId;

		public GetAfterSchoolActivityDetail(int childID,String activityID)
		{
			// TODO Auto-generated constructor stub
			childId=childID;
			activityId=activityID;
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
				afterSchoolActivityDetails =serviceMethod.getAfterSchoolActivityDetail(childId, activityId);
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
					new GetAfterSchoolActivityDetail(childId,activityId).execute();
			}
			else
			{
				if(result!=-1)
				{	
					if(afterSchoolActivityDetails!=null)
					{
						
						addAfterSchoolActivities.setfMode(afterSchoolActivityDetails.getfMode());
						addAfterSchoolActivities.setBWFMode(afterSchoolActivityDetails.getBWFMode());
						if(afterSchoolActivityDetails.getfMode()==0)
						{
							daysOfWeekValue_text.setText("All days");
						}
						else if(afterSchoolActivityDetails.getfMode()==1)
						{
							daysOfWeekValue_text.setText("Weekly");
						}
						else if(afterSchoolActivityDetails.getfMode()==2)
						{
							daysOfWeekValue_text.setText("Bi-Weekly");
						}
						else
						{
							//daysOfWeekValue_text.setText("All days");
							/**Used for older activities case**/
							daysOfWeekValue_text.setText(afterSchoolActivityDetails.getDayMode());
							StaticVariables.dayMode=afterSchoolActivityDetails.getDayMode();
							/**Used for older activities case**/
						}
						start_dateValue_text.setText(afterSchoolActivityDetails.getStartDate());
						end_dateValue_text.setText(afterSchoolActivityDetails.getEndDate());
						start_timeValue_text.setText(afterSchoolActivityDetails.getStartTime());
						end_timeValue_text.setText(afterSchoolActivityDetails.getEndTime());
						//daysOfWeekValue_text.setText(afterSchoolActivityDetails.getDayMode());
						start_timeValue_text.setText(afterSchoolActivityDetails.getStartTime());
						/*ally1Value_text.setText(afterSchoolActivityDetails.getAlly1FirstName());
						ally2Value_text.setText(afterSchoolActivityDetails.getAlly2FirstName());
						 */
						addAfterSchoolActivities.setStartDate(afterSchoolActivityDetails.getStartDate());
						addAfterSchoolActivities.setEnddate(afterSchoolActivityDetails.getEndDate());
						addAfterSchoolActivities.setStartTime(afterSchoolActivityDetails.getStartTime());
						addAfterSchoolActivities.setEndTime(afterSchoolActivityDetails.getEndTime());
						addAfterSchoolActivities.setActivityDays(afterSchoolActivityDetails.getDayID());
						addAfterSchoolActivities.setActivityID(AddAfterSchoolFragment.this.activityId);
						addAfterSchoolActivities.setRemarks(afterSchoolActivityDetails.getRemarks());

						try {
							if(addAfterSchoolActivities.getRemarks().trim().length()>0)
								text_typeNoteAfterSchool.setText(addAfterSchoolActivities.getRemarks());
							else
							{
								text_typeNoteAfterSchool.setText("");
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	

						/*addAfterSchoolActivities.setAllyId1(afterSchoolActivityDetails.getAllyID1());
						addAfterSchoolActivities.setAllyId2(afterSchoolActivityDetails.getAllyID2());
						 */
						/*addAfterSchoolActivities.setAllyName1(afterSchoolActivityDetails.getAlly1FirstName());
						addAfterSchoolActivities.setAllyName2(afterSchoolActivityDetails.getAlly2FirstName());

						addAfterSchoolActivities.setAllyIndex1(afterSchoolActivityDetails.getAlly1Index());
						addAfterSchoolActivities.setAllyIndex2(afterSchoolActivityDetails.getAlly2Index());
						 */
						//afterSchoolActivityDetails.get
						//	afterSchoolActivityDetails.se

						if(afterSchoolActivityDetails.getIsPrivate().equalsIgnoreCase("true"))
						{
							mark_private_switch.setChecked(true);
							addAfterSchoolActivities.setIsPrivate("1");
						}
						else if(afterSchoolActivityDetails.getIsPrivate().equalsIgnoreCase("false"))
						{
							mark_private_switch.setChecked(false);
							addAfterSchoolActivities.setIsPrivate("0");
						}

						if(afterSchoolActivityDetails.getIsSpecial().equalsIgnoreCase("true"))
						{
							mark_special_switch.setChecked(true);
							addAfterSchoolActivities.setIsSpecial("1");
							//layout_specialDays.setVisibility(View.VISIBLE);
							line_belowspecialDays.setVisibility(View.VISIBLE);

						}
						else if(afterSchoolActivityDetails.getIsSpecial().equalsIgnoreCase("false"))
						{
							mark_special_switch.setChecked(false);
							addAfterSchoolActivities.setIsSpecial("0");
							layout_specialDays.setVisibility(View.GONE);
							line_belowspecialDays.setVisibility(View.GONE);
						}

						if(mark_special_switch.isChecked())
						{
							specialDayValueValue_text.setText(afterSchoolActivityDetails.getSpecialDate());
							addAfterSchoolActivities.setSpecialDate(afterSchoolActivityDetails.getSpecialDate());
						}

						String[] specialDate = afterSchoolActivityDetails.getSpecialDate().split("/");

						for(int i=0;i<specialDate.length;i++)
						{
							specialselectDay=Integer.parseInt(specialDate[0]);
							specialselectMonth=(Integer.parseInt(specialDate[1]))-1;
							specialselectYear=Integer.parseInt(specialDate[2]);
						}

						String[] startDate = afterSchoolActivityDetails.getStartDate().split("/");

						for(int i=0;i<startDate.length;i++)
						{
							selectedStartDay=Integer.parseInt(startDate[0]);
							selectedStartMonth=(Integer.parseInt(startDate[1]))-1;
							selectedStartYear=Integer.parseInt(startDate[2]);
						}

						String[] endDate = afterSchoolActivityDetails.getEndDate().split("/");

						for(int i=0;i<endDate.length;i++)
						{
							selectedEndDay=Integer.parseInt(endDate[0]);
							selectedEndMonth=(Integer.parseInt(endDate[1]))-1;
							selectedEndYear=Integer.parseInt(endDate[2]);
						}
						selectedStartHour = Integer.parseInt(afterSchoolActivityDetails.getStartTime().split(":")[0]);
						selectedStartMinute = Integer.parseInt(afterSchoolActivityDetails.getStartTime().split(":")[1].substring(0, 1).trim());

						//convert this value to 24 hr format
						//11:01 AM
						/*selectedStartHour = Integer.parseInt(afterSchoolActivityDetails.getStartTime().split(":")[0]);
						selectedStartMinute = Integer.parseInt(afterSchoolActivityDetails.getStartTime().split(":")[1].split(" ")[0]);
						String AMOrPM = afterSchoolActivityDetails.getStartTime().split(":")[1].split(" ")[1];
						if(AMOrPM.equalsIgnoreCase("PM"))
						{
							selectedStartHour+=12;
						}*/

						selectedEndHour = Integer.parseInt(afterSchoolActivityDetails.getEndTime().split(":")[0]);
						selectedEndMinute = Integer.parseInt(afterSchoolActivityDetails.getEndTime().split(":")[1].substring(0, 1).trim());


						/*selectedEndHour = Integer.parseInt(afterSchoolActivityDetails.getEndTime().split(":")[0]);
						selectedEndMinute = Integer.parseInt(afterSchoolActivityDetails.getEndTime().split(":")[1].split(" ")[0]);
						String AMOrPMEnd = afterSchoolActivityDetails.getEndTime().split(":")[1].split(" ")[1];
						if(AMOrPMEnd.equalsIgnoreCase("PM"))
						{
							selectedEndHour+=12;
						}
						 */
						// A

						String dayValue=afterSchoolActivityDetails.getDayID();

						String[] daySelectedValue=dayValue.split(",");

						for(int i=0;i<daySelectedValue.length;i++)
						{
							daySelected.add(daySelectedValue[i]);
						}

						if(afterSchoolActivityDetails.getDayMode().trim().equalsIgnoreCase("All Days"))
						{
							for(int i=0;i<StaticVariables.daysInAfterSchool.length;i++)
							{
								if(i<7)
								{
									StaticVariables.daysSelectedInAfterSchool[i] = true;
								}
								else if(i==9)
								{
									StaticVariables.daysSelectedInAfterSchool[i] = true;
								}
								else
								{
									StaticVariables.daysSelectedInAfterSchool[i] = false;
								}
							}
						}
						else if(afterSchoolActivityDetails.getDayMode().trim().equalsIgnoreCase("WeekDays"))
						{
							for(int i=0;i<StaticVariables.daysInAfterSchool.length;i++)
							{
								if(i>0 &&  i<6)
								{
									StaticVariables.daysSelectedInAfterSchool[i] = true;
								}
								else if(i==7)
								{
									StaticVariables.daysSelectedInAfterSchool[i] = true;
								}
								else
								{
									StaticVariables.daysSelectedInAfterSchool[i] = false;
								}
							}
						}
						else if(afterSchoolActivityDetails.getDayMode().trim().equalsIgnoreCase("Specific Days"))
						{
							for(int i=0;i<StaticVariables.daysInAfterSchool.length;i++)
							{
								for(int j=0;j<daySelected.size();j++)
								{

									int dayId = Integer.parseInt(daySelected.get(j))-1;

									if(i==dayId)
									{
										StaticVariables.daysSelectedInAfterSchool[i] = true;
										break;
									}
								}
							}
						}
						else {

							for(int i=0;i<StaticVariables.daysInAfterSchool.length;i++)
							{
								if(i==0 || i==6 || i==8)
								{
									StaticVariables.daysSelectedInAfterSchool[i] = true;
								}
								else
								{
									StaticVariables.daysSelectedInAfterSchool[i] = false;
								}
							}
						}

						/*		ally1Value_text.setText(afterSchoolActivityDetails.getAlly1FirstName());
						ally2Value_text.setText(afterSchoolActivityDetails.getAlly2FirstName());*/
					}	
				}
				else
				{
					StaticVariables.fragmentIndexCurrentTabSchedular = 12;
					if(sharePref.isFirstTimeActivityScheduled(StaticVariables.currentChild.getChildID()+"")==0)
					{
						sharePref.setFirstTimeActivitySchedule(1, StaticVariables.currentChild.getChildID()+"");
						social.scheduleFirstActivityFacebookLog();
						social.scheduleFirstActivityGoogleAnalyticsLog();
					}
					else
					{
						social.scheduleAfterSchoolActivityFacebookLog();
						social.scheduleAfterSchoolActivityGoogleAnalyticsLog();
					}
					switchingFragments(new AfterSchoolActivityByChildIdFragment());
				}
			}
		}	
	}

	/*	private void getError(String toMove)
	{
		Error err = serviceMethod.getError();	
		showMessage.showAlertAndMove("Warning", err.getErrorDesc(),toMove,getFragmentManager());
	} */


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
			//updatedDataFromAfterSchool=0;
		}
		return super.onOptionsItemSelected(item);
	}



	/**
	 * @param dialog
	 */
	private void rebuildDialog(DialogInterface dialog) {
		if(StaticVariables.daysSelectedInAfterSchool[7] || StaticVariables.daysSelectedInAfterSchool[8]|| StaticVariables.daysSelectedInAfterSchool[9])
		{
			StaticVariables.daysSelectedInAfterSchool[7]=false;
			StaticVariables.daysSelectedInAfterSchool[8]=false;
			StaticVariables.daysSelectedInAfterSchool[9]=false;
			dialog.dismiss();

			showChoiceForDays();
		}
		else
		{
			StaticVariables.daysSelectedInAfterSchool[7]=false;
			StaticVariables.daysSelectedInAfterSchool[8]=false;
			StaticVariables.daysSelectedInAfterSchool[9]=false;
		}
	}

	public class DeleteScheduledActivityAsync extends AsyncTask<Void, Void, Integer>
	{

		public DeleteScheduledActivityAsync()
		{

		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		String status;
		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				status=serviceMethod.deleteScheduledActivityByActChildID(StaticVariables.currentChild.getChildID(), activityId);


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
					new DeleteScheduledActivityAsync().execute();

			}
			else
			{
				if(status.equalsIgnoreCase("0"))
				{
					//Toast.makeText(getActivity(), "Scheduled Activity Deleted Successfully.", Toast.LENGTH_LONG).show();

					if(StaticVariables.deleteAfterSchoolFromAfterSchool)
					{
						resetStaticValues();
						//getError();
						StaticVariables.fragmentIndexCurrentTabSchedular = 12;
						if(sharePref.isFirstTimeActivityScheduled(StaticVariables.currentChild.getChildID()+"")==0)
						{
							sharePref.setFirstTimeActivitySchedule(1, StaticVariables.currentChild.getChildID()+"");
							social.scheduleFirstActivityFacebookLog();
							social.scheduleFirstActivityGoogleAnalyticsLog();
						}
						else
						{
							social.scheduleAfterSchoolActivityFacebookLog();
							social.scheduleAfterSchoolActivityGoogleAnalyticsLog();
						}
						switchingFragments(new AfterSchoolActivityByChildIdFragment());
					}
					else
					{

						StaticVariables.fragmentIndexCurrentTabSchedular = 15;
						switchingFragments(new GetDataByCalendarDateFragment());

					}

				}

			}	
		}	
	}

	private ArrayList<String> removeDupliciacy(
			ArrayList<String> daysOfWeek) {
		// TODO Auto-generated method stub
		ArrayList<String> uniqueList = new ArrayList<String>();
		if (daysOfWeek.size() > 0) {
			String[] array = new String[daysOfWeek.size()];
			for (int i = 0; i < daysOfWeek.size(); i++)
				array[i] = daysOfWeek.get(i);
			uniqueList.add(array[0]);
			for (int i = 0; i < array.length; i++) {
				String items = array[i];
				boolean add = false;
				for (int j = 0; j < uniqueList.size(); j++) {
					if (items.equalsIgnoreCase(uniqueList.get(j)))
						add = true;
				}
				if (!add)
					uniqueList.add(items);
			}
		}
		return uniqueList;
	}


}
