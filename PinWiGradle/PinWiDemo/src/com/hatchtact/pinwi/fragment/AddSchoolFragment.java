package com.hatchtact.pinwi.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.Exchanger;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.media.Image;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.TabChildActivities;
import com.hatchtact.pinwi.classmodel.AddSubjectActivity;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.GetAtCurrentSemesterByChildID;
import com.hatchtact.pinwi.classmodel.SchoolActivityDetails;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.fragment.network.OnEventListener;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.SocialConstants;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.Validation;

import static com.hatchtact.pinwi.utility.StaticVariables.addAfterSchoolActivities;

public class AddSchoolFragment extends ParentFragment
{
	private View view;

	private String subjectName;
	private int activityId;
	private String comesfromWhichScreen="";

	private DatePickerDialog datePickerDialogExamDate=null;
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

	private LinearLayout layout_startDate=null;
	private LinearLayout layout_endDate=null;
	private LinearLayout layout_daysofWeek=null;
	private TextView start_date_text=null;
	private TextView end_date_text=null;
	private TextView start_dateValue_text=null;
	private TextView end_dateValue_text=null;
	private TextView daysOfWeek_text=null;
	private TextView daysOfWeekValue_text=null;
	private DatePickerDialog datePickerDialogDate=null;
	private SimpleDateFormat dateFormatterDate=null;
	private int selectedStartYear=0,selectedStartMonth=0,selectedStartDay=0;
	private int selectedEndYear=0,selectedEndMonth=0,selectedEndDay=0;
	private boolean onTouchStartDate=false;
	private boolean onTouchEndDate=false;
	private View layoutUseCurrentSemester,layoutSetNewDates,layoutOnlyForThisActivity;
	private TextView txtCurrentSemester,txtSetNewDates,txtOnlyForthisActivity;
	private ImageView imageTickCurrentSemester,imageSetNewDates,imageOnlyForthisActivity;
	private boolean isTickCurrentSemester,isTickSetNewDates,isTickOnlyForThisActivity=false;
	private int SemesterMode=0;//0:current sem,1:set new dates,2:only for this activity

	public static String subjectNameSchool="";
	public static int activityIdSchool=0;
	public static String comesfromWhichScreenSchool="";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		view=inflater.inflate(R.layout.addsubject_activity, container, false);
		setHasOptionsMenu(true);
		mListener.onFragmentAttached(false,"  Scheduler");
		StaticVariables.fragmentIndexFrequencyPage=0;
		StaticVariables.isFrequencySaveClicked=false;

		onTouchPickSellerButton=false;

		checkNetwork=new CheckNetwork();
		showMessage=new ShowMessages(getActivity());
		checkValidation=new Validation();
		serviceMethod=new ServiceMethod();
		sharePref=new SharePreferenceClass(getActivity());

		addSubjectActivity=new AddSubjectActivity();

		try {
			subjectName = getArguments().getString("SubjectName");
			activityId = getArguments().getInt("ActivityId");
			comesfromWhichScreen = getArguments().getString("ComingFromWhichScreen");
			subjectNameSchool=subjectName;
			activityIdSchool=activityId;
			comesfromWhichScreenSchool=comesfromWhichScreen;
		}
		catch(Exception e)
		{
			subjectName=subjectNameSchool;
			activityId=activityIdSchool;
			comesfromWhichScreen=comesfromWhichScreenSchool;
		}

		dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);


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
		dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		layout_startDate=(LinearLayout) view.findViewById(R.id.layout_startDate);
		layout_endDate=(LinearLayout) view.findViewById(R.id.layout_endDate);
		layout_daysofWeek=(LinearLayout) view.findViewById(R.id.layout_daysofWeek);
		start_dateValue_text=(TextView) view.findViewById(R.id.start_dateValue_text);
		end_dateValue_text=(TextView) view.findViewById(R.id.end_dateValue_text);
		daysOfWeekValue_text=(TextView) view.findViewById(R.id.daysOfWeekValue_text);
		start_date_text=(TextView) view.findViewById(R.id.start_date_text);
		end_date_text=(TextView) view.findViewById(R.id.end_date_text);
		daysOfWeek_text=(TextView) view.findViewById(R.id.daysOfWeek_text);

		layoutUseCurrentSemester=view.findViewById(R.id.layoutUseCurrentSemester);
		layoutSetNewDates=view.findViewById(R.id.layoutSetNewDates);
		layoutOnlyForThisActivity=view.findViewById(R.id.layoutOnlyForThisActivity);
		txtCurrentSemester= (TextView) layoutUseCurrentSemester.findViewById(R.id.txtDay);
		txtSetNewDates= (TextView) layoutSetNewDates.findViewById(R.id.txtDay);
		txtOnlyForthisActivity= (TextView) layoutOnlyForThisActivity.findViewById(R.id.txtDay);

		imageTickCurrentSemester= (ImageView) layoutUseCurrentSemester.findViewById(R.id.imageDay);
		imageSetNewDates= (ImageView) layoutSetNewDates.findViewById(R.id.imageDay);
		imageOnlyForthisActivity= (ImageView) layoutOnlyForThisActivity.findViewById(R.id.imageDay);


		typeFace.setTypefaceRegular(start_date_text);
		typeFace.setTypefaceRegular(end_date_text);
		typeFace.setTypefaceRegular(daysOfWeek_text);
		typeFace.setTypefaceRegular(start_dateValue_text);
		typeFace.setTypefaceRegular(end_dateValue_text);
		typeFace.setTypefaceRegular(daysOfWeekValue_text);
		typeFace.setTypefaceRegular(txtCurrentSemester);
		typeFace.setTypefaceRegular(txtSetNewDates);
		typeFace.setTypefaceRegular(txtOnlyForthisActivity);
		//txtCurrentSemester.setTextSize(16);
		//txtSetNewDates.setTextSize(16);
		//txtOnlyForthisActivity.setTextSize(16);

		txtCurrentSemester.setText("Use Current Semester");
		txtSetNewDates.setText("Set New Semester");
		txtOnlyForthisActivity.setText("Only for this Activity");

		layout_daysofWeek.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				showChoiceForDays();
			}
		});
		setDateField();
		layout_startDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onTouchStartDate=true;
				datePickerDialogDate.show();
			}
		});

		layout_endDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onTouchEndDate=true;
				datePickerDialogDate.show();
			}
		});
		if(comesfromWhichScreen.equalsIgnoreCase("updateSchoolActivity"))
		{
			activity_doneImage.setVisibility(View.VISIBLE);
			button_deleteSchool.setVisibility(View.VISIBLE);
			button_deleteSchool.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showAlertDelete("Confirmation","Are you sure you want to delete activity?");

					//new DeleteScheduledActivityAsync().execute();

				}
			});
			new GetSemesterTask(StaticVariables.currentChild.getChildID()).execute();
			if(StaticVariables.addSchoolActivities==null)
			{
				new GetSchoolActivityDetail(StaticVariables.currentChild.getChildID(),String.valueOf(activityId)).execute();
			}
		}
		else
		{
			new GetSemesterTask(StaticVariables.currentChild.getChildID()).execute();
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

		imageTickCurrentSemester.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(modelCurrentSemester!=null) {
					isTickCurrentSemester = !isTickCurrentSemester;
					if (isTickCurrentSemester) {
						isTickSetNewDates = false;
						isTickOnlyForThisActivity = false;
						imageTickCurrentSemester.setImageResource(R.drawable.school_tick);
						imageSetNewDates.setImageResource(R.drawable.school_untick);
						imageOnlyForthisActivity.setImageResource(R.drawable.school_untick);

						layoutOnlyForThisActivity.setAlpha(.5f);
						layoutSetNewDates.setAlpha(.5f);
						layoutUseCurrentSemester.setAlpha(1f);

						layout_startDate.setEnabled(false);
						layout_endDate.setEnabled(false);

						txtCurrentSemester.setTextColor(getActivity().getResources().getColor(R.color.font_blue_color));
						txtSetNewDates.setTextColor(getActivity().getResources().getColor(R.color.black_color));
						txtOnlyForthisActivity.setTextColor(getActivity().getResources().getColor(R.color.black_color));
						if (start_dateValue_text != null) {
							start_dateValue_text.setText(modelCurrentSemester.getStartDate());
							start_dateValue_text.setAlpha(.5f);
						}
						if (end_dateValue_text != null) {
							end_dateValue_text.setText(modelCurrentSemester.getEndDate());
							end_dateValue_text.setAlpha(.5f);
						}
					} else {
						imageTickCurrentSemester.setImageResource(R.drawable.school_untick);
						imageSetNewDates.setImageResource(R.drawable.school_untick);
						imageOnlyForthisActivity.setImageResource(R.drawable.school_untick);
						isTickSetNewDates = false;
						isTickOnlyForThisActivity = false;
						layout_startDate.setEnabled(true);
						layout_endDate.setEnabled(true);

						layoutOnlyForThisActivity.setAlpha(1f);
						layoutSetNewDates.setAlpha(1f);
						layoutUseCurrentSemester.setAlpha(.5f);

						txtCurrentSemester.setTextColor(getActivity().getResources().getColor(R.color.black_color));
						txtSetNewDates.setTextColor(getActivity().getResources().getColor(R.color.black_color));
						txtOnlyForthisActivity.setTextColor(getActivity().getResources().getColor(R.color.black_color));
						if (start_dateValue_text != null) {
							start_dateValue_text.setText("");
							start_dateValue_text.setAlpha(1f);

						}
						if (end_dateValue_text != null) {
							end_dateValue_text.setText("");
							end_dateValue_text.setAlpha(1f);
						}
					}
				}
			}
		});

		imageSetNewDates.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isTickCurrentSemester) {
					isTickSetNewDates = !isTickSetNewDates;
					isTickCurrentSemester = false;
					isTickOnlyForThisActivity = false;


					if (isTickSetNewDates) {
						if(modelCurrentSemester!=null) {
							showMessage.showAlertSetNewDates(getActivity(), new OnEventListener<String>() {

								@Override
								public void onSuccess(String object) {
									// TODO Auto-generated method stub
									imageTickCurrentSemester.setImageResource(R.drawable.school_untick);
									imageSetNewDates.setImageResource(R.drawable.school_tick);
									imageOnlyForthisActivity.setImageResource(R.drawable.school_untick);

									layoutOnlyForThisActivity.setAlpha(1f);
									layoutSetNewDates.setAlpha(1f);
									layoutUseCurrentSemester.setAlpha(.5f);

									layout_startDate.setEnabled(true);
									layout_endDate.setEnabled(true);

									txtCurrentSemester.setTextColor(getActivity().getResources().getColor(R.color.black_color));
									txtSetNewDates.setTextColor(getActivity().getResources().getColor(R.color.font_blue_color));
									txtOnlyForthisActivity.setTextColor(getActivity().getResources().getColor(R.color.black_color));
								}

								@Override
								public void onFailure(String object) {
									// TODO Auto-generated method stub
									isTickCurrentSemester = true;
									isTickOnlyForThisActivity = false;
									isTickSetNewDates = false;
									imageTickCurrentSemester.setImageResource(R.drawable.school_tick);
									imageSetNewDates.setImageResource(R.drawable.school_untick);
									imageOnlyForthisActivity.setImageResource(R.drawable.school_untick);

									layoutOnlyForThisActivity.setAlpha(.5f);
									layoutSetNewDates.setAlpha(.5f);
									layoutUseCurrentSemester.setAlpha(1f);

									layout_startDate.setEnabled(false);
									layout_endDate.setEnabled(false);

									txtCurrentSemester.setTextColor(getActivity().getResources().getColor(R.color.font_blue_color));
									txtSetNewDates.setTextColor(getActivity().getResources().getColor(R.color.black_color));
									txtOnlyForthisActivity.setTextColor(getActivity().getResources().getColor(R.color.black_color));
									if (start_dateValue_text != null) {
										start_dateValue_text.setText(modelCurrentSemester.getStartDate());
										start_dateValue_text.setAlpha(.5f);
									}
									if (end_dateValue_text != null) {
										end_dateValue_text.setText(modelCurrentSemester.getEndDate());
										end_dateValue_text.setAlpha(.5f);
									}
								}
							});

						}
						else
						{
							imageTickCurrentSemester.setImageResource(R.drawable.school_untick);
							imageSetNewDates.setImageResource(R.drawable.school_tick);
							imageOnlyForthisActivity.setImageResource(R.drawable.school_untick);

							layoutOnlyForThisActivity.setAlpha(1f);
							layoutSetNewDates.setAlpha(1f);
							layoutUseCurrentSemester.setAlpha(.5f);

							layout_startDate.setEnabled(true);
							layout_endDate.setEnabled(true);

							txtCurrentSemester.setTextColor(getActivity().getResources().getColor(R.color.black_color));
							txtSetNewDates.setTextColor(getActivity().getResources().getColor(R.color.font_blue_color));
							txtOnlyForthisActivity.setTextColor(getActivity().getResources().getColor(R.color.black_color));
						}
					} else {
						imageTickCurrentSemester.setImageResource(R.drawable.school_untick);
						imageSetNewDates.setImageResource(R.drawable.school_untick);
						imageOnlyForthisActivity.setImageResource(R.drawable.school_untick);

						layout_startDate.setEnabled(true);
						layout_endDate.setEnabled(true);

						layoutOnlyForThisActivity.setAlpha(1f);
						layoutSetNewDates.setAlpha(1f);
						layoutUseCurrentSemester.setAlpha(1f);

						txtCurrentSemester.setTextColor(getActivity().getResources().getColor(R.color.black_color));
						txtSetNewDates.setTextColor(getActivity().getResources().getColor(R.color.black_color));
						txtOnlyForthisActivity.setTextColor(getActivity().getResources().getColor(R.color.black_color));

					}
				}
			}
		});
		imageOnlyForthisActivity.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isTickCurrentSemester) {
					isTickOnlyForThisActivity = !isTickOnlyForThisActivity;
					isTickCurrentSemester = false;
					isTickSetNewDates = false;
					if (isTickOnlyForThisActivity) {
						imageTickCurrentSemester.setImageResource(R.drawable.school_untick);
						imageSetNewDates.setImageResource(R.drawable.school_untick);
						imageOnlyForthisActivity.setImageResource(R.drawable.school_tick);

						layoutOnlyForThisActivity.setAlpha(1f);
						layoutSetNewDates.setAlpha(1f);
						layoutUseCurrentSemester.setAlpha(.5f);

						layout_startDate.setEnabled(true);
						layout_endDate.setEnabled(true);

						txtCurrentSemester.setTextColor(getActivity().getResources().getColor(R.color.black_color));
						txtSetNewDates.setTextColor(getActivity().getResources().getColor(R.color.black_color));
						txtOnlyForthisActivity.setTextColor(getActivity().getResources().getColor(R.color.font_blue_color));
					} else {
						imageTickCurrentSemester.setImageResource(R.drawable.school_untick);
						imageSetNewDates.setImageResource(R.drawable.school_untick);
						imageOnlyForthisActivity.setImageResource(R.drawable.school_untick);

						layout_startDate.setEnabled(true);
						layout_endDate.setEnabled(true);

						layoutOnlyForThisActivity.setAlpha(1f);
						layoutSetNewDates.setAlpha(1f);
						layoutUseCurrentSemester.setAlpha(1f);

						txtCurrentSemester.setTextColor(getActivity().getResources().getColor(R.color.black_color));
						txtSetNewDates.setTextColor(getActivity().getResources().getColor(R.color.black_color));
						txtOnlyForthisActivity.setTextColor(getActivity().getResources().getColor(R.color.black_color));
					}
				}
			}
		});

		layout_examdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				datePickerDialogExamDate.show();
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

				Calendar calender1=Calendar.getInstance();
				calender1.set(selectedStartYear, selectedStartMonth, selectedStartDay);

				long startDateValue=calender1.getTime().getTime();

				calender1.set(selectedEndYear, selectedEndMonth, selectedEndDay);
				long endDateValue=calender1.getTime().getTime();

				long totalValue=(endDateValue - startDateValue);



			/*	if(StaticVariables.addSchoolActivities!=null) {
					String dayValue = StaticVariables.addAfterSchoolActivities.getActivityDays();

					String[] daySelectedValue = dayValue.split(",");
					for(int i=0;i<daySelectedValue.length;i++)
					{
						daySelected.add(daySelectedValue[i]);
					}
				}*/

				if(!checkValidation.isNotNullOrBlank(start_dateValue_text.getText().toString()))
					showMessage.showAlert("Warning", "Oops! You left a few important fields blank.");
				/*else if(selectedStartYear < year)
					showMessage.showAlert("Invalid Data", "Activities cannot be scheduled for a date or  time in the past.");

				else if(selectedStartYear <= year && selectedStartMonth < month)
					showMessage.showAlert("Invalid Data", "Activities cannot be scheduled for a date or  time in the past.");

				else if(selectedStartMonth <= month && selectedStartDay < day)
					showMessage.showAlert("Invalid Data", "Activities cannot be scheduled for a date or  time in the past.");
*/
				else if(!checkValidation.isNotNullOrBlank(end_dateValue_text.getText().toString()))
					showMessage.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");

			/*	else if(selectedEndYear < selectedStartYear)
					showMessage.showAlert("Invalid Data", "Activities cannot be scheduled for a date or  time in the past.");

				else if(selectedEndYear <= selectedStartYear && selectedEndMonth < selectedStartMonth)
					showMessage.showAlert("Invalid Data", "Activities cannot be scheduled for a date or  time in the past.");

				else if(selectedEndMonth <= selectedStartMonth && selectedEndDay<selectedStartDay)
					showMessage.showAlert("Invalid Data", "Activities cannot be scheduled for a date or  time in the past.");

*/
				else if(totalValue<0)
				{
					showMessage.showAlert("Invalid Data", "Activities cannot be scheduled for a date or  time in the past.");

				}
				else if(!isTickCurrentSemester && !isTickSetNewDates&& !isTickOnlyForThisActivity)
				{
					showMessage.showAlert("Invalid Data", "Please select semester options.");
				}
				else if(!checkValidation.isNotNullOrBlank(daysOfWeekValue_text.getText().toString()))
					showMessage.showAlert("Incomplete Data", "Oops! You left a few important fields blank.");

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
					{
						addSubjectActivity.setExamDate("");
					}
					//addSubjectActivity.setExamDate("01/01/1900");
					addSubjectActivity.setRemarks(note_text.getText().toString());
					addSubjectActivity.setActivityDays(days);
					if (start_dateValue_text.getText()!=null&& start_dateValue_text.getText().toString().trim().length()>0)
					{
						addSubjectActivity.setStartDate(start_dateValue_text.getText().toString().trim());
					}
					if (end_dateValue_text.getText()!=null&& end_dateValue_text.getText().toString().trim().length()>0)
					{
						addSubjectActivity.setEndDate(end_dateValue_text.getText().toString().trim());
					}

					if(isTickSetNewDates)
					{
						SemesterMode=1;
					}
					else if(isTickCurrentSemester)
					{
						SemesterMode=0;

					}
					else if(isTickOnlyForThisActivity)
					{
						SemesterMode=2;
					}

					/***Fix for sem mode 3*/
					try {
						if (modelCurrentSemester != null) {
							if (SemesterMode == 1) {
								if (modelCurrentSemester.getEndDate().toString().trim().length() > 0) {

									String[] endDate = modelCurrentSemester.getEndDate().split("/");
									int oldSelectedEndDate = 0, oldSelectedEndMonth = 0, oldSelectedEndYear = 0;
									for (int i = 0; i < endDate.length; i++) {
										oldSelectedEndDate = Integer.parseInt(endDate[0]);
										oldSelectedEndMonth = (Integer.parseInt(endDate[1])) - 1;
										oldSelectedEndYear = Integer.parseInt(endDate[2]);
									}

									Calendar calenderSemMode = Calendar.getInstance();
									calenderSemMode.set(oldSelectedEndDate, oldSelectedEndMonth, oldSelectedEndYear);

									long oldEndDate = calenderSemMode.getTime().getTime();

									calenderSemMode.set(selectedStartDay, selectedStartMonth, selectedStartYear);
									long startValueNewSem = calenderSemMode.getTime().getTime();

									long totalValueSem = (oldEndDate - startValueNewSem);

									if (totalValueSem > 0) {
										SemesterMode = 3;
									}
								}
							}
						}
					}
					catch (Exception e)
					{

					}
					/***Fix for sem mode 3*/
					addSubjectActivity.setSemesterMode(SemesterMode);
					if(StaticVariables.addSchoolActivities!=null) {
						addSubjectActivity.setFMode(StaticVariables.addSchoolActivities.getFMode());
						addSubjectActivity.setBWFMode(StaticVariables.addSchoolActivities.getBWFMode());
					}
					new AddSchoolTask().execute();
				}
			}
		});

		setDateTimeField();
		reFillValueIfAny();
		settingDefaultValueDaysDialog();
		return view;
	}


	private void setDateTimeField()
	{
		date_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				datePickerDialogExamDate.show();
			}
		});

		Calendar newCalendar = Calendar.getInstance();

		datePickerDialogExamDate=new DatePickerDialog(getActivity(), new OnDateSetListener() {

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

		datePickerDialogExamDate.getDatePicker().setMinDate(System.currentTimeMillis() - 10000);
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

	//private ProgressDialog progressDialog=null;

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
			if(customProgressLoader!=null)
			{
				customProgressLoader.showProgressBar();
			}
			/*progressDialog = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
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
				customProgressLoader.dismissProgressBar();
				/*if (progressDialog.isShowing())
					progressDialog.cancel();*/
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
							/*social.scheduleFirstActivityFacebookLog();
							social.scheduleFirstActivityGoogleAnalyticsLog();*/
						}
						else
						{
							/*social.scheduleSchoolActivityFacebookLog();
							social.scheduleSchoolActivityGoogleAnalyticsLog();*/
						}
						social.SchedulerSubjectAddedClevertap(StaticVariables.currentChild.getFirstName(),subjectName);
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
			if(customProgressLoader!=null)
			{
				customProgressLoader.startHandler();
			}
			/*progressDialog = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
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
					new GetSchoolActivityDetail(childId,activityId).execute();

			}
			else
			{
				if(result!=-1)
				{
					if(schoolActivityDetails!=null)
					{
						addSubjectActivity.setFMode(schoolActivityDetails.getFMode());
						addSubjectActivity.setBWFMode(schoolActivityDetails.getBWFMode());

						if(schoolActivityDetails.getStartDate().toString().trim().length()==0)
						{
							if(modelCurrentSemester!=null) {
								schoolActivityDetails.setStartDate(modelCurrentSemester.getStartDate());
								schoolActivityDetails.setSemesterMode("0");
							}
							else
							{
								schoolActivityDetails.setSemesterMode("2");
							}
						}
						if(schoolActivityDetails.getEndDate().toString().trim().length()==0)
						{
							if(modelCurrentSemester!=null) {
								schoolActivityDetails.setEndDate(modelCurrentSemester.getEndDate());
							}
						}

						/****Change  done after build 46 for few issues****/
						if(schoolActivityDetails.getSemesterMode().trim().length()==0)
						{
							//schoolActivityDetails.setSemesterMode("0");
							schoolActivityDetails.setSemesterMode("2");//fix if we do not get any semester mode ""
						}
						else if(schoolActivityDetails.getSemesterMode().trim().equalsIgnoreCase("1"))//fix if new semester is set
						{
							schoolActivityDetails.setSemesterMode("0");
							if(modelCurrentSemester!=null)
							{
								schoolActivityDetails.setStartDate(modelCurrentSemester.getStartDate());
								schoolActivityDetails.setEndDate(modelCurrentSemester.getEndDate());
							}
						}
						else if(schoolActivityDetails.getSemesterMode().trim().equalsIgnoreCase("0"))//fix for multiple semester always last semester will overide all other values
						{
							if(modelCurrentSemester!=null)
							{
								schoolActivityDetails.setStartDate(modelCurrentSemester.getStartDate());
								schoolActivityDetails.setEndDate(modelCurrentSemester.getEndDate());
							}
						}
						else if(schoolActivityDetails.getSemesterMode().trim().equalsIgnoreCase("3"))//fix for  sem mode 3
						{
							schoolActivityDetails.setSemesterMode("0");
							if(modelCurrentSemester!=null)
							{
								schoolActivityDetails.setStartDate(modelCurrentSemester.getStartDate());
								schoolActivityDetails.setEndDate(modelCurrentSemester.getEndDate());
							}
						}
						/**** Changes done after build 46 for few issues****/



						imageTickCurrentSemester.setImageResource(R.drawable.school_tick);
						imageSetNewDates.setImageResource(R.drawable.school_untick);
						imageOnlyForthisActivity.setImageResource(R.drawable.school_untick);
						isTickCurrentSemester = true;
						isTickSetNewDates = false;
						isTickOnlyForThisActivity = false;
						layoutOnlyForThisActivity.setAlpha(.5f);
						layoutSetNewDates.setAlpha(.5f);
						layout_startDate.setEnabled(false);
						layout_endDate.setEnabled(false);
						txtCurrentSemester.setTextColor(getActivity().getResources().getColor(R.color.font_blue_color));

						//addSubjectActivity.setSemesterMode(schoolActivityDetails.getSemesterMode());


						/*if(schoolActivityDetails.getSemesterMode().equalsIgnoreCase("1"))
						{
							isTickCurrentSemester=false;
							isTickSetNewDates=true;
							isTickOnlyForThisActivity=false;

							imageTickCurrentSemester.setImageResource(R.drawable.school_untick);
							imageSetNewDates.setImageResource(R.drawable.school_tick);
							imageOnlyForthisActivity.setImageResource(R.drawable.school_untick);

							layoutOnlyForThisActivity.setAlpha(1f);
							layoutSetNewDates.setAlpha(1f);
							layoutUseCurrentSemester.setAlpha(.5f);

							layout_startDate.setEnabled(true);
							layout_endDate.setEnabled(true);

							txtCurrentSemester.setTextColor(getActivity().getResources().getColor(R.color.black_color));
							txtSetNewDates.setTextColor(getActivity().getResources().getColor(R.color.font_blue_color));
							txtOnlyForthisActivity.setTextColor(getActivity().getResources().getColor(R.color.black_color));
						}
						else*/ /*if(schoolActivityDetails.getSemesterMode().equalsIgnoreCase("2"))
					{
						isTickCurrentSemester=false;
						isTickSetNewDates=false;
						isTickOnlyForThisActivity=true;

						imageTickCurrentSemester.setImageResource(R.drawable.school_untick);
						imageSetNewDates.setImageResource(R.drawable.school_untick);
						imageOnlyForthisActivity.setImageResource(R.drawable.school_tick);

						layoutOnlyForThisActivity.setAlpha(1f);
						layoutSetNewDates.setAlpha(1f);
						layoutUseCurrentSemester.setAlpha(.5f);

						layout_startDate.setEnabled(true);
						layout_endDate.setEnabled(true);

						txtCurrentSemester.setTextColor(getActivity().getResources().getColor(R.color.black_color));
						txtSetNewDates.setTextColor(getActivity().getResources().getColor(R.color.black_color));
						txtOnlyForthisActivity.setTextColor(getActivity().getResources().getColor(R.color.font_blue_color));
						if (start_dateValue_text!=null) {
							start_dateValue_text.setAlpha(1f);
						}
						if(end_dateValue_text!=null)
						{
							end_dateValue_text.setAlpha(1f);
						}
					}
					else
					{*/
						if(schoolActivityDetails.getSemesterMode().equalsIgnoreCase("0"))
						{
							isTickCurrentSemester=true;
							isTickSetNewDates=false;
							isTickOnlyForThisActivity=false;

							imageTickCurrentSemester.setImageResource(R.drawable.school_tick);
							imageSetNewDates.setImageResource(R.drawable.school_untick);
							imageOnlyForthisActivity.setImageResource(R.drawable.school_untick);

							layoutOnlyForThisActivity.setAlpha(.5f);
							layoutSetNewDates.setAlpha(.5f);
							layoutUseCurrentSemester.setAlpha(1f);

							layout_startDate.setEnabled(false);
							layout_endDate.setEnabled(false);

							txtCurrentSemester.setTextColor(getActivity().getResources().getColor(R.color.font_blue_color));
							txtSetNewDates.setTextColor(getActivity().getResources().getColor(R.color.black_color));
							txtOnlyForthisActivity.setTextColor(getActivity().getResources().getColor(R.color.black_color));
							if (start_dateValue_text!=null) {
								start_dateValue_text.setAlpha(.5f);
							}
							if(end_dateValue_text!=null)
							{
								end_dateValue_text.setAlpha(.5f);
							}
						}
						else
						{
							//if(schoolActivityDetails.getSemesterMode().equalsIgnoreCase("2"))
							{
								isTickCurrentSemester=false;
								isTickSetNewDates=false;
								isTickOnlyForThisActivity=true;

								imageTickCurrentSemester.setImageResource(R.drawable.school_untick);
								imageSetNewDates.setImageResource(R.drawable.school_untick);
								imageOnlyForthisActivity.setImageResource(R.drawable.school_tick);

								layoutOnlyForThisActivity.setAlpha(1f);
								layoutSetNewDates.setAlpha(1f);
								layoutUseCurrentSemester.setAlpha(.5f);

								layout_startDate.setEnabled(true);
								layout_endDate.setEnabled(true);

								txtCurrentSemester.setTextColor(getActivity().getResources().getColor(R.color.black_color));
								txtSetNewDates.setTextColor(getActivity().getResources().getColor(R.color.black_color));
								txtOnlyForthisActivity.setTextColor(getActivity().getResources().getColor(R.color.font_blue_color));
								if (start_dateValue_text!=null) {
									start_dateValue_text.setAlpha(1f);
								}
								if(end_dateValue_text!=null)
								{
									end_dateValue_text.setAlpha(1f);
								}
							}
						}
						//}

						if(schoolActivityDetails.getFMode()==0)
						{
							daysOfWeekValue_text.setText("All days");
						}
						else if(schoolActivityDetails.getFMode()==1)
						{
							daysOfWeekValue_text.setText("Weekly");
						}
						else if(schoolActivityDetails.getFMode()==2)
						{
							daysOfWeekValue_text.setText("Bi-Weekly");
						}
						else
						{
							/**Used for older activities case**/
							////daysOfWeekValue_text.setText(schoolActivityDetails.getDayMode());
							daysOfWeekValue_text.setText("Weekly");
							//StaticVariables.dayMode=afterSchoolActivityDetails.getDayMode();
							/**Used for older activities case**/
						}
						start_dateValue_text.setText(schoolActivityDetails.getStartDate());
						end_dateValue_text.setText(schoolActivityDetails.getEndDate());

						/*addAfterSchoolActivities.setStartDate(afterSchoolActivityDetails.getStartDate());
						addAfterSchoolActivities.setEnddate(afterSchoolActivityDetails.getEndDate());
						addAfterSchoolActivities.setStartTime(afterSchoolActivityDetails.getStartTime());
						addAfterSchoolActivities.setEndTime(afterSchoolActivityDetails.getEndTime());*/
						addSubjectActivity.setActivityDays(schoolActivityDetails.getDayID());
						addSubjectActivity.setActivityID(Integer.parseInt(activityId));



						//if(schoolActivityDetails.getStartDate()!=null||!schoolActivityDetails.getStartDate().equalsIgnoreCase("")||!schoolActivityDetails.getStartDate().equalsIgnoreCase("null"))
						if(schoolActivityDetails.getStartDate().toString().trim().length()>0)
						{
							String[] startDate = schoolActivityDetails.getStartDate().split("/");

							for (int i = 0; i < startDate.length; i++) {
								selectedStartDay = Integer.parseInt(startDate[0]);
								selectedStartMonth = (Integer.parseInt(startDate[1])) - 1;
								selectedStartYear = Integer.parseInt(startDate[2]);
							}
						}

						//if(schoolActivityDetails.getEndDate()!=null||!schoolActivityDetails.getEndDate().equalsIgnoreCase("")||!schoolActivityDetails.getEndDate().equalsIgnoreCase("null"))
						if(schoolActivityDetails.getEndDate().toString().trim().length()>0)
						{

							String[] endDate = schoolActivityDetails.getEndDate().split("/");

							for (int i = 0; i < endDate.length; i++) {
								selectedEndDay = Integer.parseInt(endDate[0]);
								selectedEndMonth = (Integer.parseInt(endDate[1])) - 1;
								selectedEndYear = Integer.parseInt(endDate[2]);
							}
						}
						//convert this value to 24 hr format
						//11:01 AM
						/*selectedStartHour = Integer.parseInt(afterSchoolActivityDetails.getStartTime().split(":")[0]);
						selectedStartMinute = Integer.parseInt(afterSchoolActivityDetails.getStartTime().split(":")[1].split(" ")[0]);
						String AMOrPM = afterSchoolActivityDetails.getStartTime().split(":")[1].split(" ")[1];
						if(AMOrPM.equalsIgnoreCase("PM"))
						{
							selectedStartHour+=12;
						}*/


						/*selectedEndHour = Integer.parseInt(afterSchoolActivityDetails.getEndTime().split(":")[0]);
						selectedEndMinute = Integer.parseInt(afterSchoolActivityDetails.getEndTime().split(":")[1].split(" ")[0]);
						String AMOrPMEnd = afterSchoolActivityDetails.getEndTime().split(":")[1].split(" ")[1];
						if(AMOrPMEnd.equalsIgnoreCase("PM"))
						{
							selectedEndHour+=12;
						}
						 */
						// A

						subjectName_text.setText(schoolActivityDetails.getName());
						if(schoolActivityDetails.getRemarks()!=null) {
							if (schoolActivityDetails.getRemarks().trim().length() > 0)
								note_text.setText(schoolActivityDetails.getRemarks());
						}
						else {
							note_text.setText("");
						}

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

					}
				}
				else
				{
					//getError("SubjectActivityByChildID");

					StaticVariables.fragmentIndexCurrentTabSchedular = 13;
					/*if(sharePref.isFirstTimeActivityScheduled(StaticVariables.currentChild.getChildID()+"")==0)
					{
						sharePref.setFirstTimeActivitySchedule(1, StaticVariables.currentChild.getChildID()+"");
						*//*social.scheduleFirstActivityFacebookLog();
						social.scheduleFirstActivityGoogleAnalyticsLog();*//*
					}
					else
					{
						*//*social.scheduleSchoolActivityFacebookLog();
						social.scheduleSchoolActivityGoogleAnalyticsLog();*//*
					}
					social.SchedulerSubjectAddedClevertap(StaticVariables.currentChild.getFirstName(),subjectName);
*/
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
			if(customProgressLoader!=null)
			{
				customProgressLoader.showProgressBar();
			}
			/*progressDialog = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
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
				customProgressLoader.dismissProgressBar();
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
					new DeleteScheduledActivityAsync().execute();

			}
			else
			{
				if(status.equalsIgnoreCase("0"))
				{
					//Toast.makeText(getActivity(), "Scheduled Activity Deleted Successfully.", Toast.LENGTH_LONG).show();

					if(StaticVariables.fragmentIndexCurrentTabSchedular==26)
					{
						/*if(sharePref.isFirstTimeActivityScheduled(StaticVariables.currentChild.getChildID()+"")==0)
						{
							sharePref.setFirstTimeActivitySchedule(1, StaticVariables.currentChild.getChildID()+"");
							*//*social.scheduleFirstActivityFacebookLog();
							social.scheduleFirstActivityGoogleAnalyticsLog();*//*
						}
						else
						{
							*//*social.scheduleSchoolActivityFacebookLog();
							social.scheduleSchoolActivityGoogleAnalyticsLog();*//*
						}
						social.SchedulerSubjectAddedClevertap(StaticVariables.currentChild.getFirstName(),subjectName);
*/
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

	private void setDateField()
	{
		layout_startDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onTouchStartDate=true;
				datePickerDialogDate.show();
			}
		});

		layout_endDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onTouchEndDate=true;
				datePickerDialogDate.show();
			}
		});


		Calendar newCalendar = Calendar.getInstance();

		datePickerDialogDate=new DatePickerDialog(getActivity(), new OnDateSetListener() {

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

					//addAfterSchoolActivities.setStartDate(start_dateValue_text.getText().toString());
				}

				if(onTouchEndDate)
				{
					end_dateValue_text.setText(dateFormatter.format(newDate.getTime()));
					onTouchEndDate=false;
					selectedEndYear=year;
					selectedEndMonth=monthOfYear;
					selectedEndDay=dayOfMonth;

					//addAfterSchoolActivities.setEnddate(end_dateValue_text.getText().toString());
				}//


			}
		}, newCalendar
				.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

		//datePickerDialogDate.getDatePicker().setMinDate(System.currentTimeMillis() - 10000);

	}


	GetAtCurrentSemesterByChildID modelCurrentSemester;

	private class GetSemesterTask extends AsyncTask<Void, Void, Integer>
	{

		int childId;

		public GetSemesterTask(int childID)
		{
			// TODO Auto-generated constructor stub
			childId=childID;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			/*if(customProgressLoader!=null)
			{
				customProgressLoader.startHandler();
			}*/

		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				modelCurrentSemester =serviceMethod.getAtCurrentSemesterByChildID(childId);
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

			/*try {
				customProgressLoader.removeCallbacksHandler();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(getActivity()))
					new GetSemesterTask(childId).execute();

			}
			else
			{
				if(!comesfromWhichScreen.equalsIgnoreCase("updateSchoolActivity")) {
					if (result != -1 && StaticVariables.addSchoolActivities == null) {
						if (modelCurrentSemester != null) {
							if (start_dateValue_text != null) {
								start_dateValue_text.setText(modelCurrentSemester.getStartDate());
								start_dateValue_text.setAlpha(.5f);
							}
							if (end_dateValue_text != null) {
								end_dateValue_text.setText(modelCurrentSemester.getEndDate());
								end_dateValue_text.setAlpha(.5f);
							}
							imageTickCurrentSemester.setImageResource(R.drawable.school_tick);
							imageSetNewDates.setImageResource(R.drawable.school_untick);
							imageOnlyForthisActivity.setImageResource(R.drawable.school_untick);
							isTickCurrentSemester = true;
							isTickSetNewDates = false;
							isTickOnlyForThisActivity = false;
							layoutOnlyForThisActivity.setAlpha(.5f);
							layoutSetNewDates.setAlpha(.5f);
							layout_startDate.setEnabled(false);
							layout_endDate.setEnabled(false);
							txtCurrentSemester.setTextColor(getActivity().getResources().getColor(R.color.font_blue_color));
							if(modelCurrentSemester.getStartDate().toString().trim().length()>0)
							{
								String[] startDate = modelCurrentSemester.getStartDate().split("/");

								for (int i = 0; i < startDate.length; i++) {
									selectedStartDay = Integer.parseInt(startDate[0]);
									selectedStartMonth = (Integer.parseInt(startDate[1])) - 1;
									selectedStartYear = Integer.parseInt(startDate[2]);
								}
							}

							//if(schoolActivityDetails.getEndDate()!=null||!schoolActivityDetails.getEndDate().equalsIgnoreCase("")||!schoolActivityDetails.getEndDate().equalsIgnoreCase("null"))
							if(modelCurrentSemester.getEndDate().toString().trim().length()>0)
							{

								String[] endDate = modelCurrentSemester.getEndDate().split("/");

								for (int i = 0; i < endDate.length; i++) {
									selectedEndDay = Integer.parseInt(endDate[0]);
									selectedEndMonth = (Integer.parseInt(endDate[1])) - 1;
									selectedEndYear = Integer.parseInt(endDate[2]);
								}
							}
						} else {
							if (start_dateValue_text != null) {
								start_dateValue_text.setText("");
							}
							if (end_dateValue_text != null) {
								end_dateValue_text.setText("");
							}
							//txtCurrentSemester.setAlpha(.5f);

							isTickCurrentSemester = false;
							isTickSetNewDates = true;
							isTickOnlyForThisActivity = false;
							SemesterMode = 1;
							imageTickCurrentSemester.setImageResource(R.drawable.school_untick);
							imageSetNewDates.setImageResource(R.drawable.school_tick);
							imageOnlyForthisActivity.setImageResource(R.drawable.school_untick);

							layoutOnlyForThisActivity.setAlpha(1f);
							layoutSetNewDates.setAlpha(1f);
							layoutUseCurrentSemester.setAlpha(.5f);

							layout_startDate.setEnabled(true);
							layout_endDate.setEnabled(true);

							txtCurrentSemester.setTextColor(getActivity().getResources().getColor(R.color.black_color));
							txtSetNewDates.setTextColor(getActivity().getResources().getColor(R.color.font_blue_color));
							txtOnlyForthisActivity.setTextColor(getActivity().getResources().getColor(R.color.black_color));
						}
					} else if (StaticVariables.addSchoolActivities != null) {
						if (StaticVariables.addSchoolActivities.getSemesterMode() == 0) {
							if (modelCurrentSemester != null) {
								if (start_dateValue_text != null) {
									start_dateValue_text.setText(modelCurrentSemester.getStartDate());
									start_dateValue_text.setAlpha(.5f);
								}
								if (end_dateValue_text != null) {
									end_dateValue_text.setText(modelCurrentSemester.getEndDate());
									end_dateValue_text.setAlpha(.5f);
								}
								if(modelCurrentSemester.getStartDate().toString().trim().length()>0)
								{
									String[] startDate = modelCurrentSemester.getStartDate().split("/");

									for (int i = 0; i < startDate.length; i++) {
										selectedStartDay = Integer.parseInt(startDate[0]);
										selectedStartMonth = (Integer.parseInt(startDate[1])) - 1;
										selectedStartYear = Integer.parseInt(startDate[2]);
									}
								}

								//if(schoolActivityDetails.getEndDate()!=null||!schoolActivityDetails.getEndDate().equalsIgnoreCase("")||!schoolActivityDetails.getEndDate().equalsIgnoreCase("null"))
								if(modelCurrentSemester.getEndDate().toString().trim().length()>0)
								{

									String[] endDate = modelCurrentSemester.getEndDate().split("/");

									for (int i = 0; i < endDate.length; i++) {
										selectedEndDay = Integer.parseInt(endDate[0]);
										selectedEndMonth = (Integer.parseInt(endDate[1])) - 1;
										selectedEndYear = Integer.parseInt(endDate[2]);
									}
								}
							}
						}
					}
				}
			}
		}
	}



	private void showChoiceForDays() {
		/**Added for frequency page  school new changes */
		AddSubjectActivity modelSchool ;
		if(StaticVariables.addSchoolActivities==null)
		{
			modelSchool=new AddSubjectActivity();
			if(addSubjectActivity!=null)
			{
				modelSchool.setFMode(addSubjectActivity.getFMode());
				modelSchool.setBWFMode(addSubjectActivity.getBWFMode());
				modelSchool.setActivityDays(addSubjectActivity.getActivityDays());
			}
		}
		else
		{
			modelSchool=StaticVariables.addSchoolActivities;
		}


		if (start_dateValue_text.getText()!=null&& start_dateValue_text.getText().toString().trim().length()>0)
		{
			modelSchool.setStartDate(start_dateValue_text.getText().toString().trim());
		}
		else
		{
			modelSchool.setStartDate("");
		}
		if (end_dateValue_text.getText()!=null&& end_dateValue_text.getText().toString().trim().length()>0)
		{
			modelSchool.setEndDate(end_dateValue_text.getText().toString().trim());
		}
		else
		{
			modelSchool.setEndDate("");
		}
		if(isTickSetNewDates)
		{
			SemesterMode=1;
		}
		else if(isTickCurrentSemester)
		{
			SemesterMode=0;

		}
		else if(isTickOnlyForThisActivity)
		{
			SemesterMode=2;
		}
		modelSchool.setSemesterMode(SemesterMode);
		if (date_text.getText()!=null&& date_text.getText().toString().trim().length()>0)
		{
			modelSchool.setExamDate(date_text.getText().toString().trim());
		}
		else
		{
			modelSchool.setExamDate("");
		}

		if (note_text.getText()!=null&& note_text.getText().toString().trim().length()>0)
		{
			modelSchool.setRemarks(note_text.getText().toString().trim());
		}
		else
		{
			modelSchool.setRemarks("");
		}

		StaticVariables.addSchoolActivities = modelSchool;
		StaticVariables.ActivityIdScheduler=activityId;
		StaticVariables.fragmentIndexFrequencyPage=1003;


		switchingFragments(new FrequencySchoolFragment());
		/**Added for frquency page  school new changes */

	}


	private void reFillValueIfAny()
	{
		// TODO Auto-generated method stub
		if(StaticVariables.addSchoolActivities!=null)
		{
			addSubjectActivity = StaticVariables.addSchoolActivities;
			if(StaticVariables.addSchoolActivities.getStartDate()!=null && !StaticVariables.addSchoolActivities.getStartDate().trim().equalsIgnoreCase(""))
			{
				start_dateValue_text.setText(StaticVariables.addSchoolActivities.getStartDate());
				String[] startDate = StaticVariables.addSchoolActivities.getStartDate().split("/");

				for(int i=0;i<startDate.length;i++)
				{
					selectedStartDay=Integer.parseInt(startDate[0]);
					selectedStartMonth=(Integer.parseInt(startDate[1]))-1;
					selectedStartYear=Integer.parseInt(startDate[2]);
				}
			}

			if(StaticVariables.addSchoolActivities.getEndDate()!=null && !StaticVariables.addSchoolActivities.getEndDate().trim().equalsIgnoreCase(""))
			{
				end_dateValue_text.setText(StaticVariables.addSchoolActivities.getEndDate());

				String[] endDate = StaticVariables.addSchoolActivities.getEndDate().split("/");

				for(int i=0;i<endDate.length;i++)
				{
					selectedEndDay=Integer.parseInt(endDate[0]);
					selectedEndMonth=(Integer.parseInt(endDate[1]))-1;
					selectedEndYear=Integer.parseInt(endDate[2]);
				}
			}


			if(StaticVariables.addSchoolActivities.getSemesterMode()==0)
			{
				isTickCurrentSemester=true;
				isTickSetNewDates=false;
				isTickOnlyForThisActivity=false;

				imageTickCurrentSemester.setImageResource(R.drawable.school_tick);
				imageSetNewDates.setImageResource(R.drawable.school_untick);
				imageOnlyForthisActivity.setImageResource(R.drawable.school_untick);

				layoutOnlyForThisActivity.setAlpha(.5f);
				layoutSetNewDates.setAlpha(.5f);
				layoutUseCurrentSemester.setAlpha(1f);

				layout_startDate.setEnabled(false);
				layout_endDate.setEnabled(false);

				txtCurrentSemester.setTextColor(getActivity().getResources().getColor(R.color.font_blue_color));
				txtSetNewDates.setTextColor(getActivity().getResources().getColor(R.color.black_color));
				txtOnlyForthisActivity.setTextColor(getActivity().getResources().getColor(R.color.black_color));
				if (start_dateValue_text!=null) {
					start_dateValue_text.setAlpha(.5f);
				}
				if(end_dateValue_text!=null)
				{
					end_dateValue_text.setAlpha(.5f);
				}

			}
			else if(StaticVariables.addSchoolActivities.getSemesterMode()==1)
			{
				isTickCurrentSemester=false;
				isTickSetNewDates=true;
				isTickOnlyForThisActivity=false;

				imageTickCurrentSemester.setImageResource(R.drawable.school_untick);
				imageSetNewDates.setImageResource(R.drawable.school_tick);
				imageOnlyForthisActivity.setImageResource(R.drawable.school_untick);

				layoutOnlyForThisActivity.setAlpha(1f);
				layoutSetNewDates.setAlpha(1f);
				layoutUseCurrentSemester.setAlpha(.5f);

				layout_startDate.setEnabled(true);
				layout_endDate.setEnabled(true);

				txtCurrentSemester.setTextColor(getActivity().getResources().getColor(R.color.black_color));
				txtSetNewDates.setTextColor(getActivity().getResources().getColor(R.color.font_blue_color));
				txtOnlyForthisActivity.setTextColor(getActivity().getResources().getColor(R.color.black_color));
				if (start_dateValue_text!=null) {
					start_dateValue_text.setAlpha(1f);
				}
				if(end_dateValue_text!=null)
				{
					end_dateValue_text.setAlpha(1f);
				}
			}
			else if(StaticVariables.addSchoolActivities.getSemesterMode()==2)
			{
				isTickCurrentSemester=false;
				isTickSetNewDates=false;
				isTickOnlyForThisActivity=true;

				imageTickCurrentSemester.setImageResource(R.drawable.school_untick);
				imageSetNewDates.setImageResource(R.drawable.school_untick);
				imageOnlyForthisActivity.setImageResource(R.drawable.school_tick);

				layoutOnlyForThisActivity.setAlpha(1f);
				layoutSetNewDates.setAlpha(1f);
				layoutUseCurrentSemester.setAlpha(.5f);

				layout_startDate.setEnabled(true);
				layout_endDate.setEnabled(true);

				txtCurrentSemester.setTextColor(getActivity().getResources().getColor(R.color.black_color));
				txtSetNewDates.setTextColor(getActivity().getResources().getColor(R.color.black_color));
				txtOnlyForthisActivity.setTextColor(getActivity().getResources().getColor(R.color.font_blue_color));
				if (start_dateValue_text!=null) {
					start_dateValue_text.setAlpha(1f);
				}
				if(end_dateValue_text!=null)
				{
					end_dateValue_text.setAlpha(1f);
				}
			}
			if(StaticVariables.addSchoolActivities.getActivityDays()!=null && !StaticVariables.addSchoolActivities.getActivityDays().trim().equalsIgnoreCase(""))
			{
				daysOfWeekValue_text.setText(StaticVariables.addSchoolActivities.getActivityDays());
				String dayValue=StaticVariables.addSchoolActivities.getActivityDays();

				String[] daySelectedValue=dayValue.split(",");

				for(int i=0;i<daySelectedValue.length;i++)
				{
					daySelected.add(daySelectedValue[i]);
				}
			}



			/**Frequency mode implementation*/
			if(StaticVariables.addSchoolActivities.getFMode()==0)
			{
				daysOfWeekValue_text.setText("All days");
			}
			else if(StaticVariables.addSchoolActivities.getFMode()==1)
			{
				daysOfWeekValue_text.setText("Weekly");
			}
			else if(StaticVariables.addSchoolActivities.getFMode()==2)
			{
				daysOfWeekValue_text.setText("Bi-Weekly");
			}

			/**Frequency mode implementation*/

			addSubjectActivity.setActivityDays(StaticVariables.addSchoolActivities.getActivityDays());

			if (StaticVariables.addSchoolActivities!=null&& StaticVariables.addSchoolActivities.getExamDate().toString().trim().length()>0)
			{
				date_text.setText(StaticVariables.addSchoolActivities.getExamDate());
			}
			else
			{
			}
			note_text.setText(StaticVariables.addSchoolActivities.getRemarks());
		}

	}

	/**
	 *
	 */
	private void settingDefaultValueDaysDialog() {

		/**Frequency mode implementation*/
		if(StaticVariables.addSchoolActivities!=null)
		{
			if(StaticVariables.addSchoolActivities.getFMode()==0)
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
			else if(StaticVariables.addSchoolActivities.getFMode()==1)
			{
				daysOfWeekValue_text.setText("Weekly");
			}
			else if(StaticVariables.addSchoolActivities.getFMode()==2)
			{
				daysOfWeekValue_text.setText("Bi-Weekly");
			}
			else
			{
				/**Used for older activities case**/
				//daysOfWeekValue_text.setText(StaticVariables.dayMode);
				daysOfWeekValue_text.setText("Weekly");

				/**Used for older activities case**/

			}
		}
		else
		{
			if(!comesfromWhichScreen.equalsIgnoreCase("updateSchoolActivity"))
			{
				daySelected.clear();
				daySelected.add("1");
				daySelected.add("2");
				daySelected.add("3");
				daySelected.add("4");
				daySelected.add("5");
				daySelected.add("6");
				daySelected.add("7");
				//daysOfWeekValue_text.setText("All days");
			}
		}
	}
	public void showAlertDelete(String title,String message)
	{
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());

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
				new DeleteScheduledActivityAsync().execute();
			}
		});
		alertBuilder.show();
	}
}
