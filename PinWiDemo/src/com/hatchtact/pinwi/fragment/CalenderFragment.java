package com.hatchtact.pinwi.fragment;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hatchtact.pinwi.GuideSlideActivity;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.calendar.Day;
import com.hatchtact.pinwi.calendar.ExtendedCalendarView;
import com.hatchtact.pinwi.calendar.ExtendedCalendarView.OnDayClickListener;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.StaticVariables;

public  class CalenderFragment extends ParentFragment 
{
	private TextView calender_textView=null;
	private TextView afterschool_textView=null;
	private TextView school_textView=null;

	private ImageView add_image=null;

	private View view;

	private ExtendedCalendarView extendCalendarView=null;

	private String selectedDate="";

	private int currentDay;
	private int currentMonth;
	private int currentYear;

	//	public static long newDateValue;

	public static Date newDate;
	private TextView enterHolidays;

	private GetDataByCalendarDateFragment getDataByCalendarDateFragment=new GetDataByCalendarDateFragment();
	private SharePreferenceClass sharePref;
	private ImageView image_infotnSchool;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		StaticVariables.fragmentIndexCurrentTabSchedular=11;
		sharePref=new SharePreferenceClass(getActivity());
		if(!sharePref.iscalenderTutorial())
		{
			/*sharePref.setCalenderTutorial(true);
			ScreenSlidePagerAdapter.NUM_PAGES=8;

			Intent tutorial=new Intent(getActivity(), TutorialPageActivity.class);
			startActivity(tutorial);
			StaticVariables.currentTutorialValue=StaticVariables.schedularTutorial;*/
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			if(StaticVariables.isChildUpdated)
			{
				StaticVariables.isChildUpdated=false;
				getActivity().invalidateOptionsMenu();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		if(savedInstanceState==null)
		{
			view=inflater.inflate(R.layout.calender_activity, container, false);
			setHasOptionsMenu(true);
			mListener.onFragmentAttached(true,"  Scheduler");
			image_infotnSchool=(ImageView) view.findViewById(R.id.image_infotnSchool);
			image_infotnSchool.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent tutorial=new Intent(getActivity(), GuideSlideActivity.class);
					startActivity(tutorial);
				}
			});
			StaticVariables.fragmentIndex=1;
			AddAfterSchoolFragment.updatedDataFromAfterSchool = 0;
			extendCalendarView = (ExtendedCalendarView) view.findViewById(R.id.calendar);
			extendCalendarView.setOnDayClickListener(new OnDayClickListener() {

				@Override
				public void onDayClicked(AdapterView<?> adapter, View view, int position,
						long id, Day day) {
					// TODO Auto-generated method stub

					currentDay=day.getDay();
					currentMonth=(day.getMonth())+1;
					currentYear=day.getYear();

					/*Calendar calender1=Calendar.getInstance();
					calender1.set(selectedStartYear, selectedStartMonth, selectedStartDay, selectedStartHour, selectedStartMinute);

					long startDateValue=calender1.getTime().getTime();*/


					if(currentMonth<10 && currentDay>9)
					{
						selectedDate=currentDay+"/"+"0"+currentMonth+"/"+currentYear;		
					}
					else if(currentDay<10 && currentMonth>9)
					{
						selectedDate="0"+currentDay+"/"+currentMonth+"/"+currentYear;		
					}
					else if(currentDay<10 && currentMonth<10)
					{
						selectedDate="0"+currentDay+"/"+"0"+currentMonth+"/"+currentYear;		
					}
					else
					{ 
						selectedDate=currentDay+"/"+currentMonth+"/"+currentYear;		
					}


					SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

					try 
					{
						newDate = dateFormatter.parse(selectedDate);
						//newDateValue=newDate.getTime();
					}
					catch (ParseException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					//Bundle bundle=new Bundle();
					//bundle.putString("SelectedDate", selectedDate);

					//getDataByCalendarDateFragment.setArguments(bundle);
					StaticVariables.selectedDate=selectedDate;
					StaticVariables.fragmentIndexCurrentTabSchedular=15;
					switchingFragments(getDataByCalendarDateFragment);

				}
			});


			calender_textView=(TextView) view.findViewById(R.id.text_calendar);
			afterschool_textView=(TextView) view.findViewById(R.id.text_afterschool);
			school_textView=(TextView) view.findViewById(R.id.text_school);
			add_image=(ImageView) view.findViewById(R.id.image_addbtn);
			add_image.setVisibility(View.VISIBLE);

			enterHolidays=(TextView) view.findViewById(R.id.txtEnterHoliday);
			enterHolidays.setVisibility(View.VISIBLE);

			typeFace.setTypefaceRegular(calender_textView);
			typeFace.setTypefaceRegular(afterschool_textView);
			typeFace.setTypefaceRegular(school_textView);
			typeFace.setTypefaceRegular(enterHolidays);


			enterHolidays.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					/*StaticVariables.isHolidayCalenderFromScheduler=true;
					Intent intentSettings =new Intent(getActivity(), SettingsActivity.class);
					startActivity(intentSettings);	*/
					StaticVariables.fragmentIndexCurrentTabSchedular=80;
					StaticVariables.isFromSettingsScreen=false;
					switchingFragments(new HolidayListFragment());

				}
			});

			add_image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					StaticVariables.fragmentIndexCurrentTabSchedular=14;
					switchingFragments(new AddActivityFragment());

				}
			});



			calender_textView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub





				}
			});

			afterschool_textView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					StaticVariables.fragmentIndexCurrentTabSchedular=12;
					switchingFragments(new AfterSchoolActivityByChildIdFragment());


				}
			});

			school_textView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					StaticVariables.fragmentIndexCurrentTabSchedular=13;
					switchingFragments(new SubjectActivityByChildIDFragment());


				}
			});

		}
		return view;		
	}

	public static CalenderFragment fr;

	public static CalenderFragment getInstance()
	{
		if(fr==null)
		{
			fr = new CalenderFragment();
		}
		return fr;
	}




	// the create options menu with a MenuInflater to have the menu from your fragment
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		for(int i=0;i<menu.size();i++)
		{
			menu.getItem(i).setVisible(true);
		}
		menu.findItem(R.id.action_childName).setTitle(StaticVariables.currentChild.getFirstName());



		super.onCreateOptionsMenu(menu, inflater);
	}  


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		if(item.getItemId()==android.R.id.home)
		{

			getActivity().onBackPressed();
		}

		else  if(item.getItemId()!=R.id.action_childName)
		{
			StaticVariables.currentChild=StaticVariables.childInfo.get(item.getItemId());
			getActivity().invalidateOptionsMenu();

			//here we have to refresh data according to child
			extendCalendarView.rebuildCalendar();

		}


		return super.onOptionsItemSelected(item);
	}

}
