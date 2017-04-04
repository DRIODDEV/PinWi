package com.hatchtact.pinwi.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.AfterSchoolActivitiesByDate;
import com.hatchtact.pinwi.classmodel.AfterSchoolActivitiesByDateList;
import com.hatchtact.pinwi.classmodel.GetSchoolActivitiesByDateList;
import com.hatchtact.pinwi.classmodel.SchoolActivitiesByDate;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.AppUtils;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;

@SuppressLint("SimpleDateFormat")
public class GetDataByCalendarDateFragment extends ParentFragment
{
	private View view;

	private TextView selectedDate_text=null;
	private LinearLayout layout_schoolCalendar=null; 
	private LinearLayout layout_afterschoolCalendar=null;

	private TextView schoolCalender_text=null;
	private TextView afterSchoolCalendar_text=null;

	private String dateTodisplayActivities="";

	private GetSchoolActivitiesByDateList getschoolActivitiesByDateList=null;
	private AfterSchoolActivitiesByDateList afterschoolActivitiesByDateList=null;

	private CheckNetwork checkNetwork=null;
	private ServiceMethod serviceMethod=null;
	private ShowMessages showMessage=null;

	private Context mActivity=null;

	public ArrayList<SchoolActivitiesByDate>  list_schoolActivitiesByDate = new ArrayList<SchoolActivitiesByDate>();

	String subjectName="";
	String activityName="";
	int activityId=0;

	private AddSchoolFragment addSchoolFragment=new AddSchoolFragment();
	private AddAfterSchoolFragment addafterSchoolFragment=new AddAfterSchoolFragment();

	private SimpleDateFormat dateFormatter=null;

	//private int getYear,getMonth,getDay;

	int currentYear;
	int currentMonth;
	int currentDay;

	private LinearLayout layout_addschoolActivityByCalender=null;
	private LinearLayout layout_afteraddschoolActivityByCalender=null;
	private ImageView add_imageCalendar=null;

	String currentDayValue;

	private TextView addschoolCalendar_text;

	private TextView addafterSchoolCalendar_text;

	private String dateToDisplay;

	private String todayDate; 
	
	private Date currentDate;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		StaticVariables.fragmentIndexCurrentTabSchedular=15;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view=inflater.inflate(R.layout.activity_getdatafromcalendar, container, false);

		setHasOptionsMenu(true);
		mListener.onFragmentAttached(false,"  Scheduler");

		AddAfterSchoolFragment.updatedDataFromAfterSchool = 0;
		mActivity=getActivity();

		//if(getArguments()!=null) 
		{
			//dateTodisplayActivities = getArguments().getString("SelectedDate");  
		}
		dateTodisplayActivities=StaticVariables.selectedDate;
		dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

		Date date;
		try {
			date = dateFormatter.parse(dateTodisplayActivities);

			dateFormatter = (new SimpleDateFormat("EEE, MMM dd, yyyy"));
			dateToDisplay = dateFormatter.format(date);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calendar calendar = Calendar.getInstance(); 

		currentYear = calendar.get(Calendar.YEAR);
		currentMonth = calendar.get(Calendar.MONTH)+1;
		currentDay = calendar.get(Calendar.DAY_OF_MONTH);
		
		SimpleDateFormat dateFormatter1 = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		todayDate=currentDay+"/"+currentMonth+"/"+currentYear;		
		
		try 
		{
			currentDate = dateFormatter1.parse(todayDate);
			//newDateValue=newDate.getTime();
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*String[] dateSelected= dateTodisplayActivities.split("/");

		for(int i=0;i<dateSelected.length;i++)
		{
			getDay=Integer.parseInt(dateSelected[0]);
			getMonth=Integer.parseInt(dateSelected[1]);
			getYear=Integer.parseInt(dateSelected[2]);
		}*/

		currentDayValue=AppUtils.getCurrentDay();

		checkNetwork=new CheckNetwork();
		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(mActivity);

		getschoolActivitiesByDateList =new GetSchoolActivitiesByDateList();
		afterschoolActivitiesByDateList=new AfterSchoolActivitiesByDateList();

		selectedDate_text=(TextView) view.findViewById(R.id.selectedDate_text);
		layout_schoolCalendar=(LinearLayout) view.findViewById(R.id.layout_schoolCalendar);
		layout_afterschoolCalendar=(LinearLayout) view.findViewById(R.id.layout_afterschoolCalendar);
		selectedDate_text.setText(dateToDisplay);
		layout_addschoolActivityByCalender=(LinearLayout) view.findViewById(R.id.layout_addschoolActivityByCalender);
		layout_afteraddschoolActivityByCalender=(LinearLayout) view.findViewById(R.id.layout_afteraddschoolActivityByCalender);
		add_imageCalendar=(ImageView) view.findViewById(R.id.image_addbtnCaendarTab);

		schoolCalender_text=(TextView) view.findViewById(R.id.schoolCalender_text);
		afterSchoolCalendar_text=(TextView) view.findViewById(R.id.afterSchoolCalendar_text);

		addschoolCalendar_text =(TextView) view.findViewById(R.id.addschoolCalendar_text);
		addafterSchoolCalendar_text=(TextView) view.findViewById(R.id.addafterSchoolCalendar_text);

		typeFace.setTypefaceRegular(addschoolCalendar_text);
		typeFace.setTypefaceRegular(addafterSchoolCalendar_text);

		typeFace.setTypefaceRegular(selectedDate_text);
		typeFace.setTypefaceRegular(schoolCalender_text);
		typeFace.setTypefaceRegular(afterSchoolCalendar_text);


		layout_addschoolActivityByCalender.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(CalenderFragment.newDate.before(currentDate))
				{
					showMessage.showAlert("Alert", "Sorry, you can't edit activities in the past.");
				}
				else
				{
					StaticVariables.fragmentIndexCurrentTabSchedular=16;
					switchingFragments(new AddSubjectFragment());
					/*getFragmentManager().beginTransaction().add(R.id.realtabcontent,
							new AddSubjectFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();*/
				}
			}
		});

		layout_afteraddschoolActivityByCalender.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(CalenderFragment.newDate.before(currentDate))
				{
					showMessage.showAlert("Alert", "Sorry, you can't edit activities in the past.");
				}
				else
				{
					StaticVariables.fragmentIndexCurrentTabSchedular=17;
					switchingFragments(new AddAfterSchoolCategoriesFragment());

					/*	getFragmentManager().beginTransaction().add(R.id.realtabcontent,
						new AddAfterSchoolCategoriesFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();*/	
				}
			}
		});

		add_imageCalendar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StaticVariables.fragmentIndexCurrentTabSchedular=18;
				switchingFragments(new AddActivityFragment());
				/*getFragmentManager().beginTransaction().add(R.id.realtabcontent,
						new AddActivityFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();*/
			}
		});
		new GetSchoolActivityByCalendarDate(StaticVariables.currentChild.getChildID(),dateTodisplayActivities).execute();
		new GetAfterSchoolActivityByCalendarDate(StaticVariables.currentChild.getChildID(),dateTodisplayActivities).execute();

		return view;		
	}


	private void toDisplaySchoolData()
	{
		for(int i=0;i<getschoolActivitiesByDateList.getSchoolActivitiesByDate().size();i++)
		{
			LayoutInflater layoutInflaterSchool = (LayoutInflater) getActivity()
					.getApplicationContext().getSystemService(
							Context.LAYOUT_INFLATER_SERVICE);
			final View viewSchoolCalendar = layoutInflaterSchool.inflate(R.layout.display_childsubjectbydate_information, null);

			TextView subjectName_textDisplay=(TextView) viewSchoolCalendar.findViewById(R.id.subjectName_textDisplay);
			TextView subject_sunday_text=(TextView) viewSchoolCalendar.findViewById(R.id.subject_sunday_text);
			TextView subject_monday_text=(TextView) viewSchoolCalendar.findViewById(R.id.subject_monday_text);
			TextView subject_tuesday_text=(TextView) viewSchoolCalendar.findViewById(R.id.subject_tuesday_text);
			TextView subject_wednesday_text=(TextView) viewSchoolCalendar.findViewById(R.id.subject_wednesday_text);
			TextView subject_thursday_text=(TextView) viewSchoolCalendar.findViewById(R.id.subject_thursday_text);
			TextView subject_friday_text=(TextView) viewSchoolCalendar.findViewById(R.id.subject_friday_text);
			TextView subject_saturday_text=(TextView) viewSchoolCalendar.findViewById(R.id.subject_saturday_text);
			ImageView School_verified_nonverified_image=(ImageView) viewSchoolCalendar.findViewById(R.id.School_verified_nonverified_image);

			typeFace.setTypefaceRegular(subjectName_textDisplay);
			typeFace.setTypefaceRegular(subject_sunday_text);
			typeFace.setTypefaceRegular(subject_monday_text);
			typeFace.setTypefaceRegular(subject_tuesday_text);
			typeFace.setTypefaceRegular(subject_wednesday_text);
			typeFace.setTypefaceRegular(subject_thursday_text);
			typeFace.setTypefaceRegular(subject_friday_text);
			typeFace.setTypefaceRegular(subject_saturday_text);


			SchoolActivitiesByDate schoolActivitiesByDate=getschoolActivitiesByDateList.getSchoolActivitiesByDate().get(i);

			viewSchoolCalendar.setTag(schoolActivitiesByDate);

			viewSchoolCalendar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(CalenderFragment.newDate.before(currentDate))
					{
						showMessage.showAlert("Alert", "Sorry, you can't edit activities in the past.");
					}
					else
					{
						SchoolActivitiesByDate schoolActivityModal=(SchoolActivitiesByDate) viewSchoolCalendar.getTag();

						Bundle bundle=new Bundle();

						subjectName = schoolActivityModal.getName();
						activityId = Integer.parseInt(schoolActivityModal.getActivityID());

						bundle.putString("SubjectName", subjectName);
						bundle.putInt("ActivityId", activityId);
						bundle.putString("ComingFromWhichScreen", "updateSchoolActivity");
						addSchoolFragment.setArguments(bundle);
						StaticVariables.fragmentIndexCurrentTabSchedular=19;
						switchingFragments(addSchoolFragment);
						/*getFragmentManager().beginTransaction().add(R.id.realtabcontent,
								addSchoolFragment).addToBackStack("school").commit();*/
					}
				}
			});

			subjectName_textDisplay.setText(schoolActivitiesByDate.getName());

			final String day=schoolActivitiesByDate.getDayID();

			final String[] daySelected=day.split(",");

			subject_sunday_text.setBackgroundResource(R.drawable.dot_gray);	
			subject_monday_text.setBackgroundResource(R.drawable.dot_gray);	
			subject_tuesday_text.setBackgroundResource(R.drawable.dot_gray);		
			subject_wednesday_text.setBackgroundResource(R.drawable.dot_gray);		
			subject_thursday_text.setBackgroundResource(R.drawable.dot_gray);		
			subject_friday_text.setBackgroundResource(R.drawable.dot_gray);		
			subject_saturday_text.setBackgroundResource(R.drawable.dot_gray);	

			subject_sunday_text.setTextColor(getResources().getColor(R.color.black_color));
			subject_monday_text.setTextColor(getResources().getColor(R.color.black_color));
			subject_tuesday_text.setTextColor(getResources().getColor(R.color.black_color));
			subject_wednesday_text.setTextColor(getResources().getColor(R.color.black_color));
			subject_thursday_text.setTextColor(getResources().getColor(R.color.black_color));
			subject_friday_text.setTextColor(getResources().getColor(R.color.black_color));
			subject_saturday_text.setTextColor(getResources().getColor(R.color.black_color));

			School_verified_nonverified_image.setVisibility(View.GONE);
			/*if(getschoolActivitiesByDateList.getSchoolActivitiesByDate().get(i).getIsVerified().equalsIgnoreCase("true"))
			{
				School_verified_nonverified_image.setImageResource(R.drawable.verified);
				School_verified_nonverified_image.setVisibility(View.VISIBLE);
			}
			else
			{
				School_verified_nonverified_image.setImageResource(R.drawable.nonverified);
				School_verified_nonverified_image.setVisibility(View.VISIBLE);
			}*/

			for(int j=0;j<daySelected.length;j++)
			{
				if(daySelected[j].equalsIgnoreCase("1"))
				{
					if(daySelected[j].equalsIgnoreCase(currentDayValue))
					{
						subject_sunday_text.setBackgroundResource(R.drawable.dot_darkblue);
						subject_sunday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
					else
					{
						subject_sunday_text.setBackgroundResource(R.drawable.dot_darkblue);
						subject_sunday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
				}

				else if(daySelected[j].equalsIgnoreCase("2"))
				{
					if(daySelected[j].equalsIgnoreCase(currentDayValue))
					{
						subject_monday_text.setBackgroundResource(R.drawable.dot_darkblue);	
						subject_monday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
					else
					{
						subject_monday_text.setBackgroundResource(R.drawable.dot_darkblue);	
						subject_monday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
				}

				else if(daySelected[j].equalsIgnoreCase("3"))
				{
					if(daySelected[j].equalsIgnoreCase(currentDayValue))
					{
						subject_tuesday_text.setBackgroundResource(R.drawable.dot_darkblue);
						subject_tuesday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
					else
					{
						subject_tuesday_text.setBackgroundResource(R.drawable.dot_darkblue);	
						subject_tuesday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
				}

				else if(daySelected[j].equalsIgnoreCase("4"))
				{
					if(daySelected[j].equalsIgnoreCase(currentDayValue))
					{
						subject_wednesday_text.setBackgroundResource(R.drawable.dot_darkblue);	 
						subject_wednesday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
					else
					{
						subject_wednesday_text.setBackgroundResource(R.drawable.dot_darkblue);	
						subject_wednesday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
				}

				else if(daySelected[j].equalsIgnoreCase("5"))
				{
					if(daySelected[j].equalsIgnoreCase(currentDayValue))
					{
						subject_thursday_text.setBackgroundResource(R.drawable.dot_darkblue);	 
						subject_thursday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
					else
					{
						subject_thursday_text.setBackgroundResource(R.drawable.dot_darkblue);	
						subject_thursday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
				}

				else if(daySelected[j].equalsIgnoreCase("6"))
				{
					if(daySelected[j].equalsIgnoreCase(currentDayValue))
					{
						subject_friday_text.setBackgroundResource(R.drawable.dot_darkblue);	 
						subject_friday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
					else
					{
						subject_friday_text.setBackgroundResource(R.drawable.dot_darkblue);	
						subject_friday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
				}

				else if(daySelected[j].equalsIgnoreCase("7"))
				{
					if(daySelected[j].equalsIgnoreCase(currentDayValue))
					{
						subject_saturday_text.setBackgroundResource(R.drawable.dot_darkblue);	 
						subject_saturday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
					else
					{
						subject_saturday_text.setBackgroundResource(R.drawable.dot_darkblue);	
						subject_saturday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
				}
			}
			layout_schoolCalendar.addView(viewSchoolCalendar);
		}
	}

	private void toDisplayAfterSchoolData()
	{
		for(int i=0;i<afterschoolActivitiesByDateList.getAfterSchoolActivitiesByDate().size();i++)
		{
			LayoutInflater layoutInflaterAfterSchool = (LayoutInflater) getActivity()
					.getApplicationContext().getSystemService(
							Context.LAYOUT_INFLATER_SERVICE);
			final View viewAfterSchoolCalendar = layoutInflaterAfterSchool.inflate(R.layout.display_afterschool_bycalendar, null);

			TextView subjectNameAfterSchool_textDisplay=(TextView) viewAfterSchoolCalendar.findViewById(R.id.subjectNameAfterSchool_textDisplay);
			TextView afterSchool_sunday_text=(TextView) viewAfterSchoolCalendar.findViewById(R.id.afterSchool_sunday_text);
			TextView afterSchool_monday_text=(TextView) viewAfterSchoolCalendar.findViewById(R.id.afterSchool_monday_text);
			TextView afterSchool_tuesday_text=(TextView) viewAfterSchoolCalendar.findViewById(R.id.afterSchool_tuesday_text);
			TextView afterSchool_wednesday_text=(TextView) viewAfterSchoolCalendar.findViewById(R.id.afterSchool_wednesday_text);
			TextView afterSchool_thursday_text=(TextView) viewAfterSchoolCalendar.findViewById(R.id.afterSchool_thursday_text);
			TextView afterSchool_friday_text=(TextView) viewAfterSchoolCalendar.findViewById(R.id.afterSchool_friday_text);
			TextView afterSchool_saturday_text=(TextView) viewAfterSchoolCalendar.findViewById(R.id.afterSchool_saturday_text);
			TextView afterSchool_startTime_text= (TextView) viewAfterSchoolCalendar.findViewById(R.id.starttimeAfterSchool_textDisplay);
			TextView afterSchool_endTime_text= (TextView) viewAfterSchoolCalendar.findViewById(R.id.endtimeAfterSchool_textDisplay);

			ImageView private_image=(ImageView) viewAfterSchoolCalendar.findViewById(R.id.afterSchool_private_image);;
			ImageView special_image=(ImageView) viewAfterSchoolCalendar.findViewById(R.id.afterSchool_special_image);;
			ImageView ver_nonverified_image= (ImageView) viewAfterSchoolCalendar.findViewById(R.id.afterSchool_verified_nonverified_image);

			typeFace.setTypefaceRegular(subjectNameAfterSchool_textDisplay);
			typeFace.setTypefaceRegular(afterSchool_sunday_text);
			typeFace.setTypefaceRegular(afterSchool_monday_text);
			typeFace.setTypefaceRegular(afterSchool_tuesday_text);
			typeFace.setTypefaceRegular(afterSchool_wednesday_text);
			typeFace.setTypefaceRegular(afterSchool_thursday_text);
			typeFace.setTypefaceRegular(afterSchool_friday_text);
			typeFace.setTypefaceRegular(afterSchool_saturday_text);
			typeFace.setTypefaceRegular(afterSchool_startTime_text);
			typeFace.setTypefaceRegular(afterSchool_endTime_text);

			/*if(afterschoolActivitiesByDateList.getAfterSchoolActivitiesByDate().get(i).getIsPrivate().equalsIgnoreCase("true"))
			{
				private_image.setVisibility(View.VISIBLE);
			}
			else
			{
				private_image.setVisibility(View.INVISIBLE);
			}

			if(afterschoolActivitiesByDateList.getAfterSchoolActivitiesByDate().get(i).getIsSpecial().equalsIgnoreCase("true"))
			{
				special_image.setVisibility(View.VISIBLE);
			}
			else
			{
				special_image.setVisibility(View.INVISIBLE);
			}

			if(afterschoolActivitiesByDateList.getAfterSchoolActivitiesByDate().get(i).getIsVerified().equalsIgnoreCase("true"))
			{

				ver_nonverified_image.setImageResource(R.drawable.verified);
			}

			else
			{
				ver_nonverified_image.setImageResource(R.drawable.nonverified);
			}*/
			if(afterschoolActivitiesByDateList.getAfterSchoolActivitiesByDate().get(i).getIsPrivate().equalsIgnoreCase("true"))
			{
				private_image.setImageResource(R.drawable.private_afterschool);
				private_image.setVisibility(View.VISIBLE);	
			}
			else
			{
				private_image.setVisibility(View.INVISIBLE);
			}

			if(afterschoolActivitiesByDateList.getAfterSchoolActivitiesByDate().get(i).getIsSpecial().equalsIgnoreCase("true"))
			{
				special_image.setImageResource(R.drawable.special);
				//special_image.setVisibility(View.VISIBLE);
				if(private_image.getVisibility()==View.VISIBLE)
				{
					special_image.setVisibility(View.GONE);
				}
				else
				{
					special_image.setVisibility(View.INVISIBLE);
				}
			}
			else
			{
				if(private_image.getVisibility()==View.VISIBLE)
				{
					special_image.setVisibility(View.GONE);
				}
				else
				{
					special_image.setVisibility(View.INVISIBLE);
				}
			}

			if(afterschoolActivitiesByDateList.getAfterSchoolActivitiesByDate().get(i).getIsVerified().equalsIgnoreCase("true"))
			{
				ver_nonverified_image.setImageResource(R.drawable.verified);
				ver_nonverified_image.setVisibility(View.GONE);
			}
			else
			{
				ver_nonverified_image.setImageResource(R.drawable.nonverified);
				ver_nonverified_image.setVisibility(View.VISIBLE);
			}


			AfterSchoolActivitiesByDate afterschoolActivitiesByDate=afterschoolActivitiesByDateList.getAfterSchoolActivitiesByDate().get(i);

			viewAfterSchoolCalendar.setTag(afterschoolActivitiesByDate);

			viewAfterSchoolCalendar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if(CalenderFragment.newDate.before(currentDate))
					{
						showMessage.showAlert("Alert", "Sorry, you can't edit activities in the past.");
					}
					else
					{
						AfterSchoolActivitiesByDate afterSchoolmodel=(AfterSchoolActivitiesByDate) viewAfterSchoolCalendar.getTag();
						Bundle bundle = new Bundle();

						activityName = afterSchoolmodel.getName();
						activityId = Integer.parseInt(afterSchoolmodel.getActivityID());
						
						 StaticVariables.subSubCategoryName=activityName;
						 StaticVariables.subSubCategoryId=activityId;

						bundle.putString("ActivityName", activityName);
						bundle.putInt("ActivityId", activityId);

						bundle.putString("ComingFromWhichScreen", "updateAfterSchoolActivity");
						addafterSchoolFragment.setArguments(bundle);
						StaticVariables.deleteAfterSchoolFromAfterSchool=false;
						StaticVariables.fragmentIndexCurrentTabSchedular=20;
						switchingFragments(addafterSchoolFragment);
						/*getFragmentManager().beginTransaction().add(R.id.realtabcontent,
								addafterSchoolFragment).addToBackStack("8").commit();*/
					}
				}
			});

			subjectNameAfterSchool_textDisplay.setText(afterschoolActivitiesByDate.getName());
			afterSchool_startTime_text.setText(afterschoolActivitiesByDate.getStartTime());
			afterSchool_endTime_text.setText(afterschoolActivitiesByDate.getEndTime());

			final String day=afterschoolActivitiesByDate.getDayID();

			final String[] daySelected=day.split(",");

			afterSchool_sunday_text.setBackgroundResource(R.drawable.dot_gray);	
			afterSchool_monday_text.setBackgroundResource(R.drawable.dot_gray);	
			afterSchool_tuesday_text.setBackgroundResource(R.drawable.dot_gray);		
			afterSchool_wednesday_text.setBackgroundResource(R.drawable.dot_gray);		
			afterSchool_thursday_text.setBackgroundResource(R.drawable.dot_gray);		
			afterSchool_friday_text.setBackgroundResource(R.drawable.dot_gray);		
			afterSchool_saturday_text.setBackgroundResource(R.drawable.dot_gray);		

			afterSchool_sunday_text.setTextColor(getResources().getColor(R.color.black_color));
			afterSchool_monday_text.setTextColor(getResources().getColor(R.color.black_color));
			afterSchool_tuesday_text.setTextColor(getResources().getColor(R.color.black_color));
			afterSchool_wednesday_text.setTextColor(getResources().getColor(R.color.black_color));
			afterSchool_thursday_text.setTextColor(getResources().getColor(R.color.black_color));
			afterSchool_friday_text.setTextColor(getResources().getColor(R.color.black_color));
			afterSchool_saturday_text.setTextColor(getResources().getColor(R.color.black_color));


			for(int j=0;j<daySelected.length;j++)
			{
				if(daySelected[j].equalsIgnoreCase("1"))
				{
					if(daySelected[j].equalsIgnoreCase(currentDayValue))
					{
						afterSchool_sunday_text.setBackgroundResource(R.drawable.dot_darkblue);
						afterSchool_sunday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
					else
					{
						afterSchool_sunday_text.setBackgroundResource(R.drawable.dot_darkblue);	
						afterSchool_sunday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
				}

				else if(daySelected[j].equalsIgnoreCase("2"))
				{
					if(daySelected[j].equalsIgnoreCase(currentDayValue))
					{
						afterSchool_monday_text.setBackgroundResource(R.drawable.dot_darkblue);
						afterSchool_monday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
					else
					{
						afterSchool_monday_text.setBackgroundResource(R.drawable.dot_darkblue);	
						afterSchool_monday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
				}

				else if(daySelected[j].equalsIgnoreCase("3"))
				{
					if(daySelected[j].equalsIgnoreCase(currentDayValue))
					{
						afterSchool_tuesday_text.setBackgroundResource(R.drawable.dot_darkblue);
						afterSchool_tuesday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
					else
					{
						afterSchool_tuesday_text.setBackgroundResource(R.drawable.dot_darkblue);	
						afterSchool_tuesday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
				}

				else if(daySelected[j].equalsIgnoreCase("4"))
				{
					if(daySelected[j].equalsIgnoreCase(currentDayValue))
					{
						afterSchool_wednesday_text.setBackgroundResource(R.drawable.dot_darkblue);	 
						afterSchool_wednesday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
					else
					{
						afterSchool_wednesday_text.setBackgroundResource(R.drawable.dot_darkblue);	
						afterSchool_wednesday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
				}

				else if(daySelected[j].equalsIgnoreCase("5"))
				{
					if(daySelected[j].equalsIgnoreCase(currentDayValue))
					{
						afterSchool_thursday_text.setBackgroundResource(R.drawable.dot_darkblue);
						afterSchool_thursday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
					else
					{
						afterSchool_thursday_text.setBackgroundResource(R.drawable.dot_darkblue);	
						afterSchool_thursday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
				}

				else if(daySelected[j].equalsIgnoreCase("6"))
				{
					if(daySelected[j].equalsIgnoreCase(currentDayValue))
					{
						afterSchool_friday_text.setBackgroundResource(R.drawable.dot_darkblue);
						afterSchool_friday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
					else
					{
						afterSchool_friday_text.setBackgroundResource(R.drawable.dot_darkblue);	
						afterSchool_friday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
				}

				else if(daySelected[j].equalsIgnoreCase("7"))
				{
					if(daySelected[j].equalsIgnoreCase(currentDayValue))
					{
						afterSchool_saturday_text.setBackgroundResource(R.drawable.dot_darkblue);
						afterSchool_saturday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
					else
					{
						afterSchool_saturday_text.setBackgroundResource(R.drawable.dot_darkblue);	
						afterSchool_saturday_text.setTextColor(getResources().getColor(R.color.font_white_color));
					}
				}
			}

			layout_afterschoolCalendar.addView(viewAfterSchoolCalendar);
		}

	}
	//private ProgressDialog progressDialog=null;

	private class GetSchoolActivityByCalendarDate extends AsyncTask<Void, Void, Integer>
	{
		int childId;
		String dateSelected;
		public GetSchoolActivityByCalendarDate(int childID,String date)
		{
			// TODO Auto-generated constructor stub
			childId=childID;
			dateSelected=date;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(customProgressLoader!=null)
			{
				customProgressLoader.startHandler();
			}
		/*	progressDialog = ProgressDialog.show(mActivity, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(mActivity))
			{
				getschoolActivitiesByDateList =serviceMethod.getSchoolActivityByDate(childId, dateSelected);
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
				customProgressLoader.removeCallbacksHandler();
				/*if (progressDialog.isShowing())
					progressDialog.cancel();*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(getActivity()))
					new GetSchoolActivityByCalendarDate(childId,dateSelected).execute();

			}
			else
			{
				if(getschoolActivitiesByDateList != null && getschoolActivitiesByDateList.getSchoolActivitiesByDate().size()>0)
				{
					toDisplaySchoolData();
				}
				else
				{
					getError();
				}
			}	
		}	
	}

	//private ProgressDialog progressDialogAfterSchool=null;

	private class GetAfterSchoolActivityByCalendarDate extends AsyncTask<Void, Void, Integer>
	{
		int childId;
		String dateSelected;
		public GetAfterSchoolActivityByCalendarDate(int childID,String date)
		{
			// TODO Auto-generated constructor stub
			childId=childID;
			dateSelected=date;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(customProgressLoader!=null)
			{
				customProgressLoader.startHandler();
			}
		/*	progressDialogAfterSchool = ProgressDialog.show(mActivity, "", StaticVariables.progressBarText, false);
			progressDialogAfterSchool.setCancelable(false);*/
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(mActivity))
			{
				afterschoolActivitiesByDateList =serviceMethod.getAfterSchoolActivityByDate(childId, dateSelected);
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
				customProgressLoader.removeCallbacksHandler();
				/*if (progressDialogAfterSchool.isShowing())
					progressDialogAfterSchool.cancel();*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(mActivity))
					new GetAfterSchoolActivityByCalendarDate(childId,dateSelected).execute();

			}
			else
			{
				if(afterschoolActivitiesByDateList != null && afterschoolActivitiesByDateList.getAfterSchoolActivitiesByDate().size()>0)
				{
					try {
						toDisplayAfterSchoolData();
					}
					catch (Exception e)
					{

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
}
