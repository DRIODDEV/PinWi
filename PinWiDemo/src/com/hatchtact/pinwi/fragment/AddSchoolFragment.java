package com.hatchtact.pinwi.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.appevents.AppEventsConstants;
import com.google.ads.AdRequest.ErrorCode;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.AddSubjectActivity;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.SchoolActivityDetails;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.SocialConstants;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.Validation;

public class AddSchoolFragment extends ParentFragment  
{
	private View view;

	private String subjectName;
	private int activityId;
	private String comesfromWhichScreen="";

	private DatePickerDialog datePickerDialog=null;
	private SimpleDateFormat dateFormatter=null;

	private int yearCurrent,monthCurrent,dayCurrent;

	private Button pickseller_btn=null;

	private Validation checkValidation=null;
	private ShowMessages showMessage=null;
	private	ServiceMethod serviceMethod=null;

	private AddSubjectActivity addSubjectActivity=null;

	private CheckNetwork checkNetwork=null;

	private TextView day_selected_sunday=null;
	private TextView day_selected_monday=null;
	private TextView day_selected_tuesday=null;
	private TextView day_selected_wednesday=null;
	private TextView day_selected_thursday=null;
	private TextView day_selected_friday=null;
	private TextView day_selected_saturday=null;
	private TextView date_text=null;
	private EditText note_text=null;
	private TextView subjectName_text=null;
	private ImageView activity_doneImage=null;
	private Button button_deleteSchool=null;

	private TextView text_weekday=null;
	private TextView text_weekend=null;
	private TextView text_alldays=null;

	private TextView text_subject_calender=null;
	private TextView text_typeNote=null;

	private boolean onTouchsunday=false;
	private boolean onTouchMonday=false;
	private boolean onTouchTuesday=false;
	private boolean onTouchWednesday=false;
	private boolean onTouchThursday=false;
	private boolean onTouchFriday=false;
	private boolean onTouchSaturday=false;
	private boolean onTouchWeekDay=false;
	private boolean onTouchWeekEnd=false;
	private boolean onTouchAllDay=false;

	private RelativeLayout layout_examdate=null;

	private String days="";
	private ArrayList<String> daySelected=new ArrayList<String>();

	private boolean onTouchPickSellerButton=false;

	private TextView text_examdate;
	private SharePreferenceClass sharePref;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view=inflater.inflate(R.layout.addsubject_activity, container, false);
		setHasOptionsMenu(true);
		mListener.onFragmentAttached(false,"  Scheduler");

		onTouchPickSellerButton=false;

		checkNetwork=new CheckNetwork();
		showMessage=new ShowMessages(getActivity());
		checkValidation=new Validation();
		serviceMethod=new ServiceMethod();
		sharePref=new SharePreferenceClass(getActivity());

		addSubjectActivity=new AddSubjectActivity();

		subjectName = getArguments().getString("SubjectName");  
		activityId=getArguments().getInt("ActivityId");

		dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

		comesfromWhichScreen=getArguments().getString("ComingFromWhichScreen");

		subjectName_text=(TextView) view.findViewById(R.id.text_subjectname);

		day_selected_sunday=(TextView) view.findViewById(R.id.text_sunday);
		day_selected_monday=(TextView) view.findViewById(R.id.text_monday);
		day_selected_tuesday=(TextView) view.findViewById(R.id.text_tuesday);
		day_selected_wednesday=(TextView) view.findViewById(R.id.text_wednesday);
		day_selected_thursday=(TextView) view.findViewById(R.id.text_thursday);
		day_selected_friday=(TextView) view.findViewById(R.id.text_friday);
		day_selected_saturday=(TextView) view.findViewById(R.id.text_saturday);
		activity_doneImage=(ImageView) view.findViewById(R.id.image_activityDone);
		text_weekday=(TextView) view.findViewById(R.id.text_weekday);
		text_weekend=(TextView) view.findViewById(R.id.text_weekend);
		text_alldays=(TextView) view.findViewById(R.id.text_alldays);

		text_examdate = (TextView) view.findViewById(R.id.text_examdate);

		text_subject_calender=(TextView) view.findViewById(R.id.text_subject_calender);
		text_typeNote=(TextView) view.findViewById(R.id.text_note);
		note_text=(EditText) view.findViewById(R.id.text_typeNote);

		pickseller_btn=(Button) view.findViewById(R.id.button_pickSeller);
		button_deleteSchool=(Button) view.findViewById(R.id.button_deleteSchool);

		typeFace.setTypefaceRegular(subjectName_text);
		typeFace.setTypefaceRegular(text_examdate);
		typeFace.setTypefaceRegular(day_selected_sunday);
		typeFace.setTypefaceRegular(day_selected_monday);
		typeFace.setTypefaceRegular(day_selected_tuesday);
		typeFace.setTypefaceRegular(day_selected_wednesday);
		typeFace.setTypefaceRegular(day_selected_thursday);
		typeFace.setTypefaceRegular(day_selected_friday);
		typeFace.setTypefaceRegular(day_selected_saturday);
		typeFace.setTypefaceRegular(text_weekday);
		typeFace.setTypefaceRegular(text_weekend);
		typeFace.setTypefaceRegular(text_alldays);
		typeFace.setTypefaceRegular(text_subject_calender);
		typeFace.setTypefaceRegular(text_typeNote);
		typeFace.setTypefaceRegular(note_text);
		typeFace.setTypefaceRegular(pickseller_btn);
		typeFace.setTypefaceRegular(button_deleteSchool);

		if(comesfromWhichScreen.equalsIgnoreCase("updateSchoolActivity"))
		{
			activity_doneImage.setVisibility(View.VISIBLE);
			button_deleteSchool.setVisibility(View.VISIBLE);
			button_deleteSchool.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					new DeleteScheduledActivityAsync().execute();

				}
			});
			new GetSchoolActivityDetail(StaticVariables.currentChild.getChildID(),String.valueOf(activityId)).execute();
		}

		date_text=(TextView) view.findViewById(R.id.text_date);
		typeFace.setTypefaceRegular(date_text);

		layout_examdate=(RelativeLayout) view.findViewById(R.id.layout_examdate);

		day_selected_sunday.setOnClickListener(weeksClicks);
		day_selected_monday.setOnClickListener(weeksClicks);
		day_selected_tuesday.setOnClickListener(weeksClicks);
		day_selected_wednesday.setOnClickListener(weeksClicks);
		day_selected_thursday.setOnClickListener(weeksClicks);
		day_selected_friday.setOnClickListener(weeksClicks);
		day_selected_saturday.setOnClickListener(weeksClicks);
		text_weekday.setOnClickListener(weeksClicks);
		text_weekend.setOnClickListener(weeksClicks);
		text_alldays.setOnClickListener(weeksClicks);

		subjectName_text.setText(subjectName);

		layout_examdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				datePickerDialog.show();
			}
		});

		pickseller_btn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar c = Calendar.getInstance(); 
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DAY_OF_MONTH);

				/*if(checkValidation.isNotNullOrBlank(date_text.getText().toString()))
				{
					//showMessage.showAlert("Warning", "Please enter your exam date");

					//if you select date in past
					if(yearCurrent < year)
						showMessage.showAlert("Warning", "Please select valid year");

					else if((yearCurrent == year && monthCurrent < month))
						showMessage.showAlert("Warning", "Please select valid month");	

					else if(monthCurrent == month && dayCurrent < day)
						showMessage.showAlert("Warning", "Please select valid day");
					else if(daySelected.size() == 0)
						showMessage.showAlert("Incomplete Data", "You must select days of the week this activity happens at school.");
					else
					{	
						onTouchPickSellerButton=true;

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

						addSubjectActivity.setChildID(StaticVariables.currentChild.getChildID());
						addSubjectActivity.setActivityID(activityId);
						if(checkValidation.isNotNullOrBlank(date_text.getText().toString()))
							addSubjectActivity.setExamDate(date_text.getText().toString());
						else
							addSubjectActivity.setExamDate("");
						addSubjectActivity.setRemarks(note_text.getText().toString());
						addSubjectActivity.setActivityDays(days);

						new AddSchoolTask().execute();
					}

				}*/

				/*if(!checkValidation.isNotNullOrBlank(date_text.getText().toString()))
				{
					showMessage.showAlert("Warning", "Please enter your exam date");
				}

				//if you select date in past
				else if(yearCurrent < year)
					showMessage.showAlert("Warning", "Please select valid year");

				else if((yearCurrent == year && monthCurrent < month))
					showMessage.showAlert("Warning", "Please select valid month");	

				else if(monthCurrent == month && dayCurrent < day)
					showMessage.showAlert("Warning", "Please select valid day");*/

				if(daySelected.size() == 0)
					showMessage.showAlert("Incomplete Data", "You must select days of the week this activity happens at school.");
				else
				{	
					onTouchPickSellerButton=true;

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

					addSubjectActivity.setChildID(StaticVariables.currentChild.getChildID());
					addSubjectActivity.setActivityID(activityId);
					if(checkValidation.isNotNullOrBlank(date_text.getText().toString()))
						addSubjectActivity.setExamDate(date_text.getText().toString());
					else
					{	
						addSubjectActivity.setExamDate("");
					}
					//addSubjectActivity.setExamDate("01/01/1900");
					addSubjectActivity.setRemarks(note_text.getText().toString());
					addSubjectActivity.setActivityDays(days);

					new AddSchoolTask().execute();
				}
			}
		});

		setDateTimeField();

		return view;		
	}


	private void setDateTimeField() 
	{
		date_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				datePickerDialog.show();
			}
		});

		Calendar newCalendar = Calendar.getInstance();

		datePickerDialog=new DatePickerDialog(getActivity(), new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				Calendar newDate=Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				date_text.setText(dateFormatter.format(newDate.getTime()));
				yearCurrent=year;
				monthCurrent=monthOfYear;
				dayCurrent=dayOfMonth;
			}
		}, newCalendar
		.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

		datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 10000);
	}

	public static AddSchoolFragment addSchoolFragment;

	public static AddSchoolFragment getInstance()
	{
		if(addSchoolFragment==null)
		{
			addSchoolFragment = new AddSchoolFragment();
		}
		return addSchoolFragment;
	}

	private ProgressDialog progressDialog=null;	

	OnClickListener weeksClicks = new OnClickListener() {

		@Override 
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) 
			{
			case R.id.text_sunday:	
				if(!onTouchsunday)
				{
					onTouchsunday=true;

					day_selected_sunday.setTextColor(getResources().getColor(R.color.black_color));
					//day_selected_sunday.setPaintFlags(day_selected_sunday.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
					daySelected.add("1");

					text_alldays.setTextColor(getResources().getColor(R.color.gray));
					text_weekday.setTextColor(getResources().getColor(R.color.gray));
					text_weekend.setTextColor(getResources().getColor(R.color.gray));

					onTouchAllDay=false;
					onTouchWeekDay=false;
					onTouchWeekEnd=false;

				}
				else
				{
					day_selected_sunday.setTextColor(getResources().getColor(R.color.gray));

					text_alldays.setTextColor(getResources().getColor(R.color.gray));
					text_weekday.setTextColor(getResources().getColor(R.color.gray));
					text_weekend.setTextColor(getResources().getColor(R.color.gray));

					onTouchAllDay=false;
					onTouchWeekDay=false;
					onTouchWeekEnd=false;

					for(int i=0;i<daySelected.size();i++)
					{
						if(daySelected.get(i).equalsIgnoreCase("1"))
							daySelected.remove(i);
					}
					onTouchsunday=false;
				}
				break;

			case R.id.text_monday:
				if(!onTouchMonday)
				{
					onTouchMonday=true;
					day_selected_monday.setTextColor(getResources().getColor(R.color.black_color));
					daySelected.add("2");

					text_alldays.setTextColor(getResources().getColor(R.color.gray));
					text_weekday.setTextColor(getResources().getColor(R.color.gray));
					text_weekend.setTextColor(getResources().getColor(R.color.gray));

					onTouchAllDay=false;
					onTouchWeekDay=false;
					onTouchWeekEnd=false;
				}
				else
				{
					day_selected_monday.setTextColor(getResources().getColor(R.color.gray));

					text_alldays.setTextColor(getResources().getColor(R.color.gray));
					text_weekday.setTextColor(getResources().getColor(R.color.gray));
					text_weekend.setTextColor(getResources().getColor(R.color.gray));

					onTouchAllDay=false;
					onTouchWeekDay=false;
					onTouchWeekEnd=false;

					for(int i=0;i<daySelected.size();i++)
					{
						if(daySelected.get(i).equalsIgnoreCase("2"))
							daySelected.remove(i);
					}
					onTouchMonday=false;
				}
				break;

			case R.id.text_tuesday:
				if(!onTouchTuesday)
				{
					onTouchTuesday=true;
					day_selected_tuesday.setTextColor(getResources().getColor(R.color.black_color));
					daySelected.add("3");

					text_alldays.setTextColor(getResources().getColor(R.color.gray));
					text_weekday.setTextColor(getResources().getColor(R.color.gray));
					text_weekend.setTextColor(getResources().getColor(R.color.gray));

					onTouchAllDay=false;
					onTouchWeekDay=false;
					onTouchWeekEnd=false;
				}
				else
				{
					day_selected_tuesday.setTextColor(getResources().getColor(R.color.gray));

					text_alldays.setTextColor(getResources().getColor(R.color.gray));
					text_weekday.setTextColor(getResources().getColor(R.color.gray));
					text_weekend.setTextColor(getResources().getColor(R.color.gray));

					onTouchAllDay=false;
					onTouchWeekDay=false;
					onTouchWeekEnd=false;

					for(int i=0;i<daySelected.size();i++)
					{
						if(daySelected.get(i).equalsIgnoreCase("3"))
							daySelected.remove(i);
					}
					onTouchTuesday=false;
				}
				break;

			case R.id.text_wednesday:       
				if(!onTouchWednesday)
				{
					onTouchWednesday=true;
					day_selected_wednesday.setTextColor(getResources().getColor(R.color.black_color));
					daySelected.add("4");

					text_alldays.setTextColor(getResources().getColor(R.color.gray));
					text_weekday.setTextColor(getResources().getColor(R.color.gray));
					text_weekend.setTextColor(getResources().getColor(R.color.gray));

					onTouchAllDay=false;
					onTouchWeekDay=false;
					onTouchWeekEnd=false;
				}
				else
				{
					day_selected_wednesday.setTextColor(getResources().getColor(R.color.gray));

					text_alldays.setTextColor(getResources().getColor(R.color.gray));
					text_weekday.setTextColor(getResources().getColor(R.color.gray));
					text_weekend.setTextColor(getResources().getColor(R.color.gray));

					onTouchAllDay=false;
					onTouchWeekDay=false;
					onTouchWeekEnd=false;

					for(int i=0;i<daySelected.size();i++)
					{
						if(daySelected.get(i).equalsIgnoreCase("4"))
							daySelected.remove(i);
					}
					onTouchWednesday=false;
				}
				break;

			case R.id.text_thursday:
				if(!onTouchThursday)
				{
					onTouchThursday=true;
					day_selected_thursday.setTextColor(getResources().getColor(R.color.black_color));
					daySelected.add("5");

					text_alldays.setTextColor(getResources().getColor(R.color.gray));
					text_weekday.setTextColor(getResources().getColor(R.color.gray));
					text_weekend.setTextColor(getResources().getColor(R.color.gray));

					onTouchAllDay=false;
					onTouchWeekDay=false;
					onTouchWeekEnd=false;
				}
				else
				{
					day_selected_thursday.setTextColor(getResources().getColor(R.color.gray));

					text_alldays.setTextColor(getResources().getColor(R.color.gray));
					text_weekday.setTextColor(getResources().getColor(R.color.gray));
					text_weekend.setTextColor(getResources().getColor(R.color.gray));

					onTouchAllDay=false;
					onTouchWeekDay=false;
					onTouchWeekEnd=false;
					for(int i=0;i<daySelected.size();i++)
					{
						if(daySelected.get(i).equalsIgnoreCase("5"))
							daySelected.remove(i);
					}
					onTouchThursday=false;
				}
				break;

			case R.id.text_friday:
				if(!onTouchFriday)
				{
					onTouchFriday=true;
					day_selected_friday.setTextColor(getResources().getColor(R.color.black_color));
					daySelected.add("6");

					text_alldays.setTextColor(getResources().getColor(R.color.gray));
					text_weekday.setTextColor(getResources().getColor(R.color.gray));
					text_weekend.setTextColor(getResources().getColor(R.color.gray));

					onTouchAllDay=false;
					onTouchWeekDay=false;
					onTouchWeekEnd=false;
				}
				else
				{
					day_selected_friday.setTextColor(getResources().getColor(R.color.gray));

					text_alldays.setTextColor(getResources().getColor(R.color.gray));
					text_weekday.setTextColor(getResources().getColor(R.color.gray));
					text_weekend.setTextColor(getResources().getColor(R.color.gray));

					onTouchAllDay=false;
					onTouchWeekDay=false;
					onTouchWeekEnd=false;

					for(int i=0;i<daySelected.size();i++)
					{
						if(daySelected.get(i).equalsIgnoreCase("6"))
							daySelected.remove(i);
					}
					onTouchFriday=false;
				}
				break;

			case R.id.text_saturday:
				if(!onTouchSaturday)
				{
					onTouchSaturday=true;
					day_selected_saturday.setTextColor(getResources().getColor(R.color.black_color));
					daySelected.add("7");

					text_alldays.setTextColor(getResources().getColor(R.color.gray));
					text_weekday.setTextColor(getResources().getColor(R.color.gray));
					text_weekend.setTextColor(getResources().getColor(R.color.gray));

					onTouchAllDay=false;
					onTouchWeekDay=false;
					onTouchWeekEnd=false;
				}
				else
				{
					day_selected_saturday.setTextColor(getResources().getColor(R.color.gray));

					text_alldays.setTextColor(getResources().getColor(R.color.gray));
					text_weekday.setTextColor(getResources().getColor(R.color.gray));
					text_weekend.setTextColor(getResources().getColor(R.color.gray));

					onTouchAllDay=false;
					onTouchWeekDay=false;
					onTouchWeekEnd=false;
					for(int i=0;i<daySelected.size();i++)
					{
						if(daySelected.get(i).equalsIgnoreCase("7"))
							daySelected.remove(i);
					}
					onTouchSaturday=false;
				}
				break;

			case R.id.text_weekday:
				if(!onTouchWeekDay)
				{
					onTouchWeekDay=true;
					daySelected.clear();

					daySelected.add("2");
					daySelected.add("3");
					daySelected.add("4");
					daySelected.add("5");
					daySelected.add("6");

					onTouchMonday=true;
					onTouchTuesday=true;
					onTouchWednesday=true;
					onTouchThursday=true;
					onTouchFriday=true;
					onTouchSaturday=false;
					onTouchWeekEnd=false;
					onTouchsunday=false;
					onTouchAllDay=false;

					day_selected_monday.setTextColor(getResources().getColor(R.color.black_color));
					day_selected_tuesday.setTextColor(getResources().getColor(R.color.black_color));
					day_selected_wednesday.setTextColor(getResources().getColor(R.color.black_color));
					day_selected_thursday.setTextColor(getResources().getColor(R.color.black_color));
					day_selected_friday.setTextColor(getResources().getColor(R.color.black_color));
					day_selected_saturday.setTextColor(getResources().getColor(R.color.gray));
					day_selected_sunday.setTextColor(getResources().getColor(R.color.gray));
					text_weekend.setTextColor(getResources().getColor(R.color.gray));
					text_alldays.setTextColor(getResources().getColor(R.color.gray));
					text_weekday.setTextColor(getResources().getColor(R.color.black_color));
				}
				else
				{
					day_selected_monday.setTextColor(getResources().getColor(R.color.gray));
					day_selected_tuesday.setTextColor(getResources().getColor(R.color.gray));
					day_selected_wednesday.setTextColor(getResources().getColor(R.color.gray));
					day_selected_thursday.setTextColor(getResources().getColor(R.color.gray));
					day_selected_friday.setTextColor(getResources().getColor(R.color.gray));
					text_weekday.setTextColor(getResources().getColor(R.color.gray));

					onTouchMonday=false;
					onTouchTuesday=false;
					onTouchWednesday=false;
					onTouchThursday=false;
					onTouchFriday=false;

					for (int i = 0; i < daySelected.size(); i++) 
					{
						daySelected.remove(i);
					}
					onTouchWeekDay=false;
				}
				break;

			case R.id.text_weekend:
				if(!onTouchWeekEnd)
				{
					daySelected.clear();

					onTouchWeekEnd=true;

					daySelected.add("1");
					daySelected.add("7");

					onTouchSaturday=true;
					onTouchsunday=true;

					onTouchMonday=false;
					onTouchTuesday=false;
					onTouchWednesday=false;
					onTouchThursday=false;
					onTouchFriday=false;
					onTouchWeekDay=false;
					onTouchAllDay=false;

					day_selected_sunday.setTextColor(getResources().getColor(R.color.black_color));
					day_selected_saturday.setTextColor(getResources().getColor(R.color.black_color));
					day_selected_monday.setTextColor(getResources().getColor(R.color.gray));
					day_selected_tuesday.setTextColor(getResources().getColor(R.color.gray));
					day_selected_wednesday.setTextColor(getResources().getColor(R.color.gray));
					day_selected_thursday.setTextColor(getResources().getColor(R.color.gray));
					day_selected_friday.setTextColor(getResources().getColor(R.color.gray));
					text_weekday.setTextColor(getResources().getColor(R.color.gray));
					text_alldays.setTextColor(getResources().getColor(R.color.gray));
					text_weekend.setTextColor(getResources().getColor(R.color.black_color));
				}
				else
				{
					day_selected_sunday.setTextColor(getResources().getColor(R.color.gray));
					day_selected_saturday.setTextColor(getResources().getColor(R.color.gray));
					text_weekend.setTextColor(getResources().getColor(R.color.gray));

					onTouchSaturday=false;
					onTouchsunday=false;

					for (int i = 0; i < daySelected.size(); i++) 
					{
						daySelected.remove(i);
					}
					onTouchWeekEnd=false;
				}
				break;

			case R.id.text_alldays:
				if(!onTouchAllDay)
				{
					daySelected.clear();

					onTouchAllDay=true;

					daySelected.add("1");
					daySelected.add("2");
					daySelected.add("3");
					daySelected.add("4");
					daySelected.add("5");
					daySelected.add("6");
					daySelected.add("7");


					onTouchsunday=true;
					onTouchMonday=true;
					onTouchTuesday=true;
					onTouchWednesday=true;
					onTouchThursday=true;
					onTouchFriday=true;
					onTouchSaturday=true;

					onTouchWeekDay=false;
					onTouchWeekEnd=false;

					day_selected_saturday.setTextColor(getResources().getColor(R.color.black_color));
					day_selected_monday.setTextColor(getResources().getColor(R.color.black_color));
					day_selected_tuesday.setTextColor(getResources().getColor(R.color.black_color));
					day_selected_wednesday.setTextColor(getResources().getColor(R.color.black_color));
					day_selected_thursday.setTextColor(getResources().getColor(R.color.black_color));
					day_selected_friday.setTextColor(getResources().getColor(R.color.black_color));
					day_selected_sunday.setTextColor(getResources().getColor(R.color.black_color));

					text_weekday.setTextColor(getResources().getColor(R.color.gray));
					text_weekend.setTextColor(getResources().getColor(R.color.gray));
					text_alldays.setTextColor(getResources().getColor(R.color.black_color));
				}
				else
				{
					day_selected_saturday.setTextColor(getResources().getColor(R.color.gray));
					day_selected_monday.setTextColor(getResources().getColor(R.color.gray));
					day_selected_tuesday.setTextColor(getResources().getColor(R.color.gray));
					day_selected_wednesday.setTextColor(getResources().getColor(R.color.gray));
					day_selected_thursday.setTextColor(getResources().getColor(R.color.gray));
					day_selected_friday.setTextColor(getResources().getColor(R.color.gray));
					day_selected_sunday.setTextColor(getResources().getColor(R.color.gray));
					text_alldays.setTextColor(getResources().getColor(R.color.gray));

					onTouchsunday=false;
					onTouchMonday=false;
					onTouchTuesday=false;
					onTouchWednesday=false;
					onTouchThursday=false;
					onTouchFriday=false;
					onTouchSaturday=false;

					for (int i = 0; i < daySelected.size(); i++) 
					{
						daySelected.remove(i);
					}
					onTouchAllDay=false;
				}
				break;
			}
		}
	};

	private class AddSchoolTask extends AsyncTask<Void, Void, Integer>
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
				Errorcode=serviceMethod.AddSubjectActivity(addSubjectActivity);
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
					new AddSchoolTask().execute();
			}
			else
			{
				if(result!=-1)
				{
					//getError("SchoolActivityAdded");

					if(Errorcode==0)
					{
						StaticVariables.fragmentIndexCurrentTabSchedular = 13;
						if(sharePref.isFirstTimeActivityScheduled(StaticVariables.currentChild.getChildID()+"")==0)
						{
							sharePref.setFirstTimeActivitySchedule(1, StaticVariables.currentChild.getChildID()+"");
							social.scheduleFirstActivityFacebookLog();
							social.scheduleFirstActivityGoogleAnalyticsLog();
						}
						else
						{
							social.scheduleSchoolActivityFacebookLog();
							social.scheduleSchoolActivityGoogleAnalyticsLog();
						}

						switchingFragments(new SubjectActivityByChildIDFragment());
					}
					else
					{
						getError();
					}
				}
			}
		}
	}

	private class GetSchoolActivityDetail extends AsyncTask<Void, Void, Integer>
	{
		SchoolActivityDetails schoolActivityDetails;

		int childId;
		String activityId;

		public GetSchoolActivityDetail(int childID,String activityID)
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
				schoolActivityDetails =serviceMethod.getSchoolActivityDetail(childId, activityId);
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
					new GetSchoolActivityDetail(childId,activityId).execute();

			}
			else
			{
				if(result!=-1)
				{	
					if(schoolActivityDetails!=null)
					{  
						subjectName_text.setText(schoolActivityDetails.getName());
						note_text.setText(schoolActivityDetails.getRemarks());
						if(schoolActivityDetails.getExamDate()==null||schoolActivityDetails.getExamDate().equalsIgnoreCase("")||schoolActivityDetails.getExamDate().equalsIgnoreCase("null"))
						{
							date_text.setText("");
						}
						else
						{
							date_text.setText(schoolActivityDetails.getExamDate());
							String[] examdatemonth = schoolActivityDetails.getExamDate().split("/");
							for(int i=0;i<examdatemonth.length;i++)
							{
								dayCurrent=Integer.parseInt(examdatemonth[0]);
								monthCurrent=Integer.parseInt(examdatemonth[1]);
								yearCurrent=Integer.parseInt(examdatemonth[2]);
							}
						}
						String dayValue=schoolActivityDetails.getDayID();
						String[] daySelectedValue=dayValue.split(",");					

						boolean[] daysSelectedInAfterSchool = {false,false,false,false,false,false,false};

						for(int i=0;i<daySelectedValue.length;i++)
						{
							daySelected.add(daySelectedValue[i]);

							if(daySelectedValue[i].equalsIgnoreCase("1"))
							{
								day_selected_sunday.setTextColor(getResources().getColor(R.color.black_color));
								daysSelectedInAfterSchool[0] = true ;
							}

							else if(daySelectedValue[i].equalsIgnoreCase("2"))
							{
								day_selected_monday.setTextColor(getResources().getColor(R.color.black_color));

								daysSelectedInAfterSchool[1] = true ;
							}

							else if(daySelectedValue[i].equalsIgnoreCase("3"))
							{
								day_selected_tuesday.setTextColor(getResources().getColor(R.color.black_color));


								daysSelectedInAfterSchool[2] = true ;
							}

							else if(daySelectedValue[i].equalsIgnoreCase("4"))
							{
								day_selected_wednesday.setTextColor(getResources().getColor(R.color.black_color));

								daysSelectedInAfterSchool[3] = true ;
							}

							else if(daySelectedValue[i].equalsIgnoreCase("5"))
							{
								day_selected_thursday.setTextColor(getResources().getColor(R.color.black_color));

								daysSelectedInAfterSchool[4] = true ;
							}

							else if(daySelectedValue[i].equalsIgnoreCase("6"))
							{
								day_selected_friday.setTextColor(getResources().getColor(R.color.black_color));

								daysSelectedInAfterSchool[5] = true ;
							}

							else if(daySelectedValue[i].equalsIgnoreCase("7"))
							{
								day_selected_saturday.setTextColor(getResources().getColor(R.color.black_color));

								daysSelectedInAfterSchool[6] = true ;
							}
						}

						if(daysSelectedInAfterSchool[0] && daysSelectedInAfterSchool[6] && (!daysSelectedInAfterSchool[1] && !daysSelectedInAfterSchool[2] && !daysSelectedInAfterSchool[3] && !daysSelectedInAfterSchool[4] && !daysSelectedInAfterSchool[5]))
						{
							text_weekend.setTextColor(getResources().getColor(R.color.black_color));
						}
						else if(daysSelectedInAfterSchool[1] && daysSelectedInAfterSchool[2] && daysSelectedInAfterSchool[3] && daysSelectedInAfterSchool[4] && daysSelectedInAfterSchool[5]&& (!daysSelectedInAfterSchool[0] && !daysSelectedInAfterSchool[6]))
						{
							text_weekday.setTextColor(getResources().getColor(R.color.black_color));
						}
						else if(daysSelectedInAfterSchool[0] && daysSelectedInAfterSchool[1] && daysSelectedInAfterSchool[2] && daysSelectedInAfterSchool[3] && daysSelectedInAfterSchool[4] && daysSelectedInAfterSchool[5] && daysSelectedInAfterSchool[6])
						{
							text_alldays.setTextColor(getResources().getColor(R.color.black_color));	
						}
						else
						{
							text_weekday.setTextColor(getResources().getColor(R.color.gray));
							text_weekend.setTextColor(getResources().getColor(R.color.gray));
							text_alldays.setTextColor(getResources().getColor(R.color.gray));
						}


						/*	for(int getWeekDayValue=0;getWeekDayValue<daySelected.size();getWeekDayValue++)
						{
							if(getWeekDayValue>0 &&  getWeekDayValue<6)
							{
								text_weekday.setTextColor(getResources().getColor(R.color.black_color));
							}
							else
							{
								text_weekday.setTextColor(getResources().getColor(R.color.gray));
							}
						}

						for(int getAllDayValue=0;getAllDayValue<daySelected.size();getAllDayValue++)
						{
							if(getAllDayValue<7)
							{
								text_alldays.setTextColor(getResources().getColor(R.color.black_color));
							}
							else
							{
								text_alldays.setTextColor(getResources().getColor(R.color.gray));
							}
						}

						for(int getWeekEndValue=0;getWeekEndValue<daySelected.size();getWeekEndValue++)
						{
							if(getWeekEndValue==0 || getWeekEndValue==6)
							{
								text_weekend.setTextColor(getResources().getColor(R.color.black_color));
							}
							else
							{
								text_weekend.setTextColor(getResources().getColor(R.color.gray));
							}
						}*/
					}	
				}
				else
				{
					//getError("SubjectActivityByChildID");

					StaticVariables.fragmentIndexCurrentTabSchedular = 13;
					if(sharePref.isFirstTimeActivityScheduled(StaticVariables.currentChild.getChildID()+"")==0)
					{
						sharePref.setFirstTimeActivitySchedule(1, StaticVariables.currentChild.getChildID()+"");
						social.scheduleFirstActivityFacebookLog();
						social.scheduleFirstActivityGoogleAnalyticsLog();
					}
					else
					{
						social.scheduleSchoolActivityFacebookLog();
						social.scheduleSchoolActivityGoogleAnalyticsLog();
					}

					switchingFragments(new SubjectActivityByChildIDFragment());
				}
			}
		}	
	}

	private void getError()
	{
		Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());
	} 
	/*private void getError(String toMove)
	{
		Error err = serviceMethod.getError();	
		showMessage.showAlertAndMove("Warning", err.getErrorDesc(),toMove,getFragmentManager());
	}*/ 


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

					if(StaticVariables.fragmentIndexCurrentTabSchedular==26)
					{
						if(sharePref.isFirstTimeActivityScheduled(StaticVariables.currentChild.getChildID()+"")==0)
						{
							sharePref.setFirstTimeActivitySchedule(1, StaticVariables.currentChild.getChildID()+"");
							social.scheduleFirstActivityFacebookLog();
							social.scheduleFirstActivityGoogleAnalyticsLog();
						}
						else
						{
							social.scheduleSchoolActivityFacebookLog();
							social.scheduleSchoolActivityGoogleAnalyticsLog();
						}

						StaticVariables.fragmentIndexCurrentTabSchedular = 13;
						switchingFragments(new SubjectActivityByChildIDFragment());
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



}
