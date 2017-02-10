package com.hatchtact.pinwi.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;

public class FrequencyAfterSchoolFragment extends ParentFragment
{
	private View view;
	private ShowMessages showMessage=null;
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;
	private int[] arrayIdViews={R.id.layoutAllDays,R.id.layoutWeekly,R.id.layoutBiWeekly
			,R.id.layoutMonday,R.id.layoutTuesday,R.id.layoutWednesday,R.id.layoutThurday,R.id.layoutFriday,
			R.id.layoutSaturday,R.id.layoutSunday,R.id.layoutAllWeekdays,R.id.layoutOnlyWeekends};
	private ArrayList<View>viewLayouts;
	private TextView txtAlldays;
	private CheckBox checkBoxAlldays;
	private TextView txtWeekly;
	private CheckBox checkBoxWeekly;
	private TextView txtBiWeekly;
	private CheckBox checkBoxBiWeekly;
	private TextView txtMonday;
	private ImageView checkBoxMonday;
	private TextView txtTuesday;
	private ImageView checkBoxTuesday;
	private TextView txtWednesday;
	private ImageView checkBoxWednesday;
	private TextView txtThurday;
	private ImageView checkBoxThurday;
	private TextView txtFriday;
	private ImageView checkBoxFriday;
	private TextView txtSaturday;
	private ImageView checkBoxSaturday;
	private TextView txtSunday;
	private ImageView checkBoxSunday;
	private TextView txtAllWeekdays;
	private ImageView checkBoxAllWeekdays;
	private TextView txtOnlyWeekends;
	private ImageView checkBoxOnlyWeekends;
	private View lineOnlyWeekends;
	private ScrollView scrollDaysOfWeek;
	private TextView txtRepeatMode,txtDaysOfWeek;
	private Button buttonSave;
	private Animation expand;
	//private CustomDialogBiWeekly customDialog;
	private boolean isCheckedMonday=false;
	private boolean isCheckedTuesday=false;
	private boolean isCheckedWednesday=false;
	private boolean isCheckedThursday=false;
	private boolean isCheckedFriday=false;
	private boolean isCheckedSaturday=false;
	private boolean isCheckedSunday=false;
	private boolean isCheckedAllWeekdays=false;
	private boolean isCheckedOnlyWeekends=false;
	private final int modeAllDays=0;
	private final int modeWeekly=1;
	private final int modeBiWeekly=2;
	private ArrayList<String> daySelected=null;
	private int BWFMode=0;

	private boolean isRefillData=false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.fragment_afterschool_frequency, container, false);
		mListener.onFragmentAttached(false,"  Scheduler");
		daySelected=new ArrayList<String>();
		isRefillData=false;
		daySelected.clear();
		setHasOptionsMenu(true);
		refactorDaysFlags();

		viewLayouts=new ArrayList<View>();
		viewLayouts.clear();
		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(getActivity());
		checkNetwork=new CheckNetwork();
		//customDialog=new CustomDialogBiWeekly(FrequencyAfterSchoolFragment.this);

		/*customDialog.setOnKeyListener(new DialogInterface.OnKeyListener()
		{
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
			{

				if (keyCode == KeyEvent.KEYCODE_BACK)
				{
					if (customDialog.isShowing())
					{
						//reset data if want
						customDialog.dismiss();

					}
				}
				return true;
			}
		});*/
		expand=AnimationUtils.loadAnimation(getActivity(), R.anim.abc_fade_in);
		initializeResources();
		setData();
		return view;
	}

	private void setData()
	{
		// TODO Auto-generated method stub
		if(StaticVariables.addAfterSchoolActivities!=null)
		{
			isRefillData=true;

			switch (StaticVariables.addAfterSchoolActivities.getfMode())
			{
				case 0:
					checkBoxAlldays.setChecked(true);
					break;
				case 1:
					checkBoxWeekly.setChecked(true);
					refillActivityDaysData();
					break;
				case 2:
					checkBoxBiWeekly.setChecked(true);
					BWFMode=StaticVariables.addAfterSchoolActivities.getBWFMode();
					refillActivityDaysData();
					break;
				case 3:
					/**Used for older activities case when all days**/
					/*if(StaticVariables.dayMode.trim().equalsIgnoreCase("All Days"))
					{
						checkBoxAlldays.setChecked(true);
					}
					else */{
						checkBoxWeekly.setChecked(true);
						refillActivityDaysData();
					}
					break;
				default:
					break;
			}
		}
	}

	private void initializeResources()
	{
		// TODO Auto-generated method stub
		for(int i=0;i<arrayIdViews.length;i++)
		{
			viewLayouts.add(view.findViewById(arrayIdViews[i]));
		}
		txtAlldays=(TextView) viewLayouts.get(0).findViewById(R.id.txtDay);
		txtWeekly=(TextView) viewLayouts.get(1).findViewById(R.id.txtDay);
		txtBiWeekly=(TextView) viewLayouts.get(2).findViewById(R.id.txtDay);
		txtMonday=(TextView) viewLayouts.get(3).findViewById(R.id.txtDay);
		txtTuesday=(TextView) viewLayouts.get(4).findViewById(R.id.txtDay);
		txtWednesday=(TextView) viewLayouts.get(5).findViewById(R.id.txtDay);
		txtThurday=(TextView) viewLayouts.get(6).findViewById(R.id.txtDay);
		txtFriday=(TextView) viewLayouts.get(7).findViewById(R.id.txtDay);
		txtSaturday=(TextView) viewLayouts.get(8).findViewById(R.id.txtDay);
		txtSunday=(TextView) viewLayouts.get(9).findViewById(R.id.txtDay);
		txtAllWeekdays=(TextView) viewLayouts.get(10).findViewById(R.id.txtDay);
		txtOnlyWeekends=(TextView)viewLayouts.get(11).findViewById(R.id.txtDay);

		setTextData(txtAlldays, 0);
		setTextData(txtWeekly, 1);
		setTextData(txtBiWeekly, 2);
		setTextData(txtMonday, 3);
		setTextData(txtTuesday, 4);
		setTextData(txtWednesday, 5);
		setTextData(txtThurday, 6);
		setTextData(txtFriday, 7);
		setTextData(txtSaturday, 8);
		setTextData(txtSunday, 9);
		setTextData(txtAllWeekdays, 10);
		setTextData(txtOnlyWeekends,11);

		checkBoxAlldays=(CheckBox) viewLayouts.get(0).findViewById(R.id.checkboxDay);
		//checkBoxAlldays.setChecked(true);
		checkBoxWeekly=(CheckBox) viewLayouts.get(1).findViewById(R.id.checkboxDay);
		checkBoxBiWeekly=(CheckBox) viewLayouts.get(2).findViewById(R.id.checkboxDay);
		checkBoxMonday=(ImageView) viewLayouts.get(3).findViewById(R.id.imageDay);
		checkBoxTuesday=(ImageView) viewLayouts.get(4).findViewById(R.id.imageDay);
		checkBoxWednesday=(ImageView) viewLayouts.get(5).findViewById(R.id.imageDay);
		checkBoxThurday=(ImageView) viewLayouts.get(6).findViewById(R.id.imageDay);
		checkBoxFriday=(ImageView) viewLayouts.get(7).findViewById(R.id.imageDay);
		checkBoxSaturday=(ImageView) viewLayouts.get(8).findViewById(R.id.imageDay);
		checkBoxSunday=(ImageView) viewLayouts.get(9).findViewById(R.id.imageDay);
		checkBoxAllWeekdays=(ImageView) viewLayouts.get(10).findViewById(R.id.imageDay);
		checkBoxOnlyWeekends=(ImageView) viewLayouts.get(11).findViewById(R.id.imageDay);

		lineOnlyWeekends=viewLayouts.get(11).findViewById(R.id.lineView);
		txtRepeatMode=(TextView) view.findViewById(R.id.txtRepeatMode);
		txtDaysOfWeek=(TextView) view.findViewById(R.id.txtDaysOfWeek);
		scrollDaysOfWeek=(ScrollView) view.findViewById(R.id.scrollDaysOfWeek);
		buttonSave=(Button) view.findViewById(R.id.button_Save_Frequency);

		typeFace.setTypefaceRegular(txtAlldays);
		typeFace.setTypefaceRegular(txtWeekly);
		typeFace.setTypefaceRegular(txtBiWeekly);
		typeFace.setTypefaceRegular(txtMonday);
		typeFace.setTypefaceRegular(txtTuesday);
		typeFace.setTypefaceRegular(txtWednesday);
		typeFace.setTypefaceRegular(txtThurday);
		typeFace.setTypefaceRegular(txtFriday);
		typeFace.setTypefaceRegular(txtSaturday);
		typeFace.setTypefaceRegular(txtSunday);
		typeFace.setTypefaceRegular(txtRepeatMode);
		typeFace.setTypefaceRegular(txtDaysOfWeek);
		typeFace.setTypefaceRegular(buttonSave);

		buttonSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if(!checkBoxAlldays.isChecked() && !checkBoxWeekly.isChecked() && !checkBoxBiWeekly.isChecked())
				{
					//if all three options  all these, weekly or bi-weekly are unselected and user tries to save there will be error msg
					showMessage.showAlert("Alert", "Please define the frequency for this activity.");
				}
				else if(checkIfDaysSelected())
				{
					//This Error pop up will pop in Both cases  not picking weekdays
					//In weekly or bi-weekly selection
					showMessage.showAlert("Alert", "Please pick days of week for the activity in order to schedule it.");
				}
				else
				{
					if(checkBoxAlldays.isChecked())
					{
						StaticVariables.addAfterSchoolActivities.setfMode(modeAllDays);
					}
					else if(checkBoxWeekly.isChecked())
					{
						StaticVariables.addAfterSchoolActivities.setfMode(modeWeekly);
						addDaySelected();
					}
					else if(checkBoxBiWeekly.isChecked())
					{
						StaticVariables.addAfterSchoolActivities.setfMode(modeBiWeekly);
						addDaySelected();
						StaticVariables.addAfterSchoolActivities.setBWFMode(BWFMode);
					}
					//showMessage.showAlert("Alert", "Data Saved.");

					StaticVariables.isFrequencySaveClicked=true;
					switch (StaticVariables.fragmentIndexFrequencyPage) {
						case 1000:
							StaticVariables.fragmentIndexFrequencyPage=0;
							switchingFragments(new AddAfterSchoolFragment());
							break;

						case 1001:
							StaticVariables.fragmentIndexFrequencyPage=0;
							switchingFragments(new com.hatchtact.pinwi.fragment.whattodo.AddAfterSchoolFragment());
							break;
					}


				}
			}
		});


		checkBoxAlldays.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(!isRefillData)
				{
					if(isChecked)
					{
						scrollDaysOfWeek.setVisibility(View.GONE);
						scrollDaysOfWeek.setScrollY(0);
						checkBoxAlldays.setChecked(true);
						checkBoxWeekly.setChecked(false);
						checkBoxBiWeekly.setChecked(false);
						checkBoxMonday.setImageResource(0);
						checkBoxTuesday.setImageResource(0);
						checkBoxWednesday.setImageResource(0);
						checkBoxThurday.setImageResource(0);
						checkBoxFriday.setImageResource(0);
						checkBoxSaturday.setImageResource(0);
						checkBoxSunday.setImageResource(0);
						checkBoxAllWeekdays.setImageResource(0);
						checkBoxOnlyWeekends.setImageResource(0);
						refactorDaysFlags();
					}
					else
					{

					}
				}
				else
				{
					isRefillData=false;
				}
			}
		});
		checkBoxWeekly.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(!isRefillData)
				{
					if(isChecked)
					{
						scrollDaysOfWeek.setVisibility(View.VISIBLE);
						scrollDaysOfWeek.setScrollY(0);

						scrollDaysOfWeek.startAnimation(expand);
						checkBoxAlldays.setChecked(false);
						checkBoxWeekly.setChecked(true);
						checkBoxBiWeekly.setChecked(false);
						checkBoxMonday.setImageResource(0);
						checkBoxTuesday.setImageResource(0);
						checkBoxWednesday.setImageResource(0);
						checkBoxThurday.setImageResource(0);
						checkBoxFriday.setImageResource(0);
						checkBoxSaturday.setImageResource(0);
						checkBoxSunday.setImageResource(0);
						checkBoxAllWeekdays.setImageResource(0);
						checkBoxOnlyWeekends.setImageResource(0);
						refactorDaysFlags();
					}
					else
					{
						if(!checkBoxBiWeekly.isChecked()) {
							scrollDaysOfWeek.setVisibility(View.GONE);
							scrollDaysOfWeek.setScrollY(0);
						}
					}
				}
				else
				{
					isRefillData=false;
				}
			}
		});

		checkBoxBiWeekly.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(!isRefillData)
				{
					if(isChecked)
					{
						//scrollDaysOfWeek.setVisibility(View.GONE);
						//scrollDaysOfWeek.setScrollY(0);
						//checkBoxAlldays.setChecked(false);
						//checkBoxWeekly.setChecked(false);
						//customDialog.show();
						//refactorDaysFlags();

						scrollDaysOfWeek.setVisibility(View.VISIBLE);
						scrollDaysOfWeek.setScrollY(0);

						scrollDaysOfWeek.startAnimation(expand);
						checkBoxAlldays.setChecked(false);
						checkBoxWeekly.setChecked(false);
						checkBoxMonday.setImageResource(0);
						checkBoxTuesday.setImageResource(0);
						checkBoxWednesday.setImageResource(0);
						checkBoxThurday.setImageResource(0);
						checkBoxFriday.setImageResource(0);
						checkBoxSaturday.setImageResource(0);
						checkBoxSunday.setImageResource(0);
						checkBoxAllWeekdays.setImageResource(0);
						checkBoxOnlyWeekends.setImageResource(0);
						refactorDaysFlags();
					}
					else
					{
						if(!checkBoxWeekly.isChecked())
							scrollDaysOfWeek.setVisibility(View.GONE);
					}
				}
				else
				{
					isRefillData=false;
				}
			}
		});

		viewLayouts.get(3).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isCheckedMonday=!isCheckedMonday;
				isCheckedAllWeekdays=false;
				isCheckedOnlyWeekends=false;
				if(isCheckedMonday)
				{
					checkBoxMonday.setImageResource(R.drawable.accept_i);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
				else
				{
					checkBoxMonday.setImageResource(0);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
			}
		});
		checkBoxMonday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isCheckedMonday=!isCheckedMonday;
				isCheckedAllWeekdays=false;
				isCheckedOnlyWeekends=false;
				if(isCheckedMonday)
				{
					checkBoxMonday.setImageResource(R.drawable.accept_i);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
				else
				{
					checkBoxMonday.setImageResource(0);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
			}
		});

		viewLayouts.get(4).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isCheckedTuesday=!isCheckedTuesday;
				isCheckedAllWeekdays=false;
				isCheckedOnlyWeekends=false;
				if(isCheckedTuesday)
				{
					checkBoxTuesday.setImageResource(R.drawable.accept_i);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
				else
				{
					checkBoxTuesday.setImageResource(0);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
			}
		});
		checkBoxTuesday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isCheckedTuesday=!isCheckedTuesday;
				isCheckedAllWeekdays=false;
				isCheckedOnlyWeekends=false;
				if(isCheckedTuesday)
				{
					checkBoxTuesday.setImageResource(R.drawable.accept_i);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
				else
				{
					checkBoxTuesday.setImageResource(0);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
			}
		});

		viewLayouts.get(5).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isCheckedWednesday=!isCheckedWednesday;
				isCheckedAllWeekdays=false;
				isCheckedOnlyWeekends=false;
				if(isCheckedWednesday)
				{
					checkBoxWednesday.setImageResource(R.drawable.accept_i);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
				else
				{
					checkBoxWednesday.setImageResource(0);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
			}
		});
		checkBoxWednesday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isCheckedWednesday=!isCheckedWednesday;
				isCheckedAllWeekdays=false;
				isCheckedOnlyWeekends=false;
				if(isCheckedWednesday)
				{
					checkBoxWednesday.setImageResource(R.drawable.accept_i);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
				else
				{
					checkBoxWednesday.setImageResource(0);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
			}
		});

		viewLayouts.get(6).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isCheckedThursday=!isCheckedThursday;
				isCheckedAllWeekdays=false;
				isCheckedOnlyWeekends=false;
				if(isCheckedThursday)
				{
					checkBoxThurday.setImageResource(R.drawable.accept_i);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
				else
				{
					checkBoxThurday.setImageResource(0);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
			}
		});

		checkBoxThurday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isCheckedThursday=!isCheckedThursday;
				isCheckedAllWeekdays=false;
				isCheckedOnlyWeekends=false;
				if(isCheckedThursday)
				{
					checkBoxThurday.setImageResource(R.drawable.accept_i);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
				else
				{
					checkBoxThurday.setImageResource(0);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
			}
		});

		viewLayouts.get(7).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isCheckedFriday=!isCheckedFriday;
				isCheckedAllWeekdays=false;
				isCheckedOnlyWeekends=false;
				if(isCheckedFriday)
				{
					checkBoxFriday.setImageResource(R.drawable.accept_i);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
				else
				{
					checkBoxFriday.setImageResource(0);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
			}
		});


		checkBoxFriday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isCheckedFriday=!isCheckedFriday;
				isCheckedAllWeekdays=false;
				isCheckedOnlyWeekends=false;
				if(isCheckedFriday)
				{
					checkBoxFriday.setImageResource(R.drawable.accept_i);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
				else
				{
					checkBoxFriday.setImageResource(0);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
			}
		});


		viewLayouts.get(8).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isCheckedSaturday=!isCheckedSaturday;
				isCheckedAllWeekdays=false;
				isCheckedOnlyWeekends=false;
				if(isCheckedSaturday)
				{
					checkBoxSaturday.setImageResource(R.drawable.accept_i);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
				else
				{
					checkBoxSaturday.setImageResource(0);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
			}
		});

		checkBoxSaturday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isCheckedSaturday=!isCheckedSaturday;
				isCheckedAllWeekdays=false;
				isCheckedOnlyWeekends=false;
				if(isCheckedSaturday)
				{
					checkBoxSaturday.setImageResource(R.drawable.accept_i);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
				else
				{
					checkBoxSaturday.setImageResource(0);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
			}
		});
		viewLayouts.get(9).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isCheckedSunday=!isCheckedSunday;
				isCheckedAllWeekdays=false;
				isCheckedOnlyWeekends=false;
				if(isCheckedSunday)
				{
					checkBoxSunday.setImageResource(R.drawable.accept_i);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
				else
				{
					checkBoxSunday.setImageResource(0);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}

			}
		});


		checkBoxSunday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isCheckedSunday=!isCheckedSunday;
				isCheckedAllWeekdays=false;
				isCheckedOnlyWeekends=false;
				if(isCheckedSunday)
				{
					checkBoxSunday.setImageResource(R.drawable.accept_i);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}
				else
				{
					checkBoxSunday.setImageResource(0);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(0);
				}

			}
		});
		viewLayouts.get(10).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isCheckedAllWeekdays=!isCheckedAllWeekdays;
				if(isCheckedAllWeekdays)
				{
					checkBoxMonday.setImageResource(R.drawable.accept_i);
					checkBoxTuesday.setImageResource(R.drawable.accept_i);
					checkBoxWednesday.setImageResource(R.drawable.accept_i);
					checkBoxThurday.setImageResource(R.drawable.accept_i);
					checkBoxFriday.setImageResource(R.drawable.accept_i);
					checkBoxSaturday.setImageResource(0);
					checkBoxSunday.setImageResource(0);
					checkBoxAllWeekdays.setImageResource(R.drawable.accept_i);
					checkBoxOnlyWeekends.setImageResource(0);
					isCheckedOnlyWeekends=false;
					isCheckedMonday=true;
					isCheckedTuesday=true;
					isCheckedWednesday=true;
					isCheckedThursday=true;
					isCheckedFriday=true;
					isCheckedSaturday=false;
					isCheckedSunday=false;
				}
				else
				{
					checkBoxAllWeekdays.setImageResource(0);
				}

			}
		});
		checkBoxAllWeekdays.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isCheckedAllWeekdays=!isCheckedAllWeekdays;
				if(isCheckedAllWeekdays)
				{
					checkBoxMonday.setImageResource(R.drawable.accept_i);
					checkBoxTuesday.setImageResource(R.drawable.accept_i);
					checkBoxWednesday.setImageResource(R.drawable.accept_i);
					checkBoxThurday.setImageResource(R.drawable.accept_i);
					checkBoxFriday.setImageResource(R.drawable.accept_i);
					checkBoxSaturday.setImageResource(0);
					checkBoxSunday.setImageResource(0);
					checkBoxAllWeekdays.setImageResource(R.drawable.accept_i);
					checkBoxOnlyWeekends.setImageResource(0);
					isCheckedOnlyWeekends=false;
					isCheckedMonday=true;
					isCheckedTuesday=true;
					isCheckedWednesday=true;
					isCheckedThursday=true;
					isCheckedFriday=true;
					isCheckedSaturday=false;
					isCheckedSunday=false;
				}
				else
				{
					checkBoxAllWeekdays.setImageResource(0);
				}

			}
		});

		viewLayouts.get(11).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isCheckedOnlyWeekends=!isCheckedOnlyWeekends;
				if(isCheckedOnlyWeekends)
				{
					checkBoxMonday.setImageResource(0);
					checkBoxTuesday.setImageResource(0);
					checkBoxWednesday.setImageResource(0);
					checkBoxThurday.setImageResource(0);
					checkBoxFriday.setImageResource(0);
					checkBoxSaturday.setImageResource(R.drawable.accept_i);
					checkBoxSunday.setImageResource(R.drawable.accept_i);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(R.drawable.accept_i);
					isCheckedAllWeekdays=false;
					isCheckedMonday=false;
					isCheckedTuesday=false;
					isCheckedWednesday=false;
					isCheckedThursday=false;
					isCheckedFriday=false;
					isCheckedSaturday=true;
					isCheckedSunday=true;
				}
				else
				{
					checkBoxOnlyWeekends.setImageResource(0);
				}

			}
		});

		checkBoxOnlyWeekends.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isCheckedOnlyWeekends=!isCheckedOnlyWeekends;
				if(isCheckedOnlyWeekends)
				{
					checkBoxMonday.setImageResource(0);
					checkBoxTuesday.setImageResource(0);
					checkBoxWednesday.setImageResource(0);
					checkBoxThurday.setImageResource(0);
					checkBoxFriday.setImageResource(0);
					checkBoxSaturday.setImageResource(R.drawable.accept_i);
					checkBoxSunday.setImageResource(R.drawable.accept_i);
					checkBoxAllWeekdays.setImageResource(0);
					checkBoxOnlyWeekends.setImageResource(R.drawable.accept_i);
					isCheckedAllWeekdays=false;
					isCheckedMonday=false;
					isCheckedTuesday=false;
					isCheckedWednesday=false;
					isCheckedThursday=false;
					isCheckedFriday=false;
					isCheckedSaturday=true;
					isCheckedSunday=true;
				}
				else
				{
					checkBoxOnlyWeekends.setImageResource(0);
				}

			}
		});

	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
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



	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		hideKeyBoard();
	}
	private void hideKeyBoard()
	{
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

	private void setTextData(TextView txtView,int i)
	{
		txtView.setText(getActivity().getResources().getStringArray(R.array.freq_page_txt)[i]);
	}


	public void biWeeklyImplementation(int i)
	{
		switch (i) {
			case 0:
				BWFMode=2;//this week
				scrollDaysOfWeek.setVisibility(View.VISIBLE);
				scrollDaysOfWeek.setScrollY(0);
				checkBoxAlldays.setChecked(false);
				checkBoxWeekly.setChecked(false);
				checkBoxBiWeekly.setChecked(true);
				checkBoxMonday.setImageResource(0);
				checkBoxTuesday.setImageResource(0);
				checkBoxWednesday.setImageResource(0);
				checkBoxThurday.setImageResource(0);
				checkBoxFriday.setImageResource(0);
				checkBoxSaturday.setImageResource(0);
				checkBoxSunday.setImageResource(0);
				checkBoxAllWeekdays.setImageResource(0);
				checkBoxOnlyWeekends.setImageResource(0);
				isCheckedAllWeekdays=false;
				refactorDaysFlags();
				break;
			case 1:
				BWFMode=1;//next week
				scrollDaysOfWeek.setVisibility(View.VISIBLE);
				scrollDaysOfWeek.setScrollY(0);
				checkBoxAlldays.setChecked(false);
				checkBoxWeekly.setChecked(false);
				checkBoxBiWeekly.setChecked(true);
				checkBoxMonday.setImageResource(0);
				checkBoxTuesday.setImageResource(0);
				checkBoxWednesday.setImageResource(0);
				checkBoxThurday.setImageResource(0);
				checkBoxFriday.setImageResource(0);
				checkBoxSaturday.setImageResource(0);
				checkBoxSunday.setImageResource(0);
				checkBoxAllWeekdays.setImageResource(0);
				checkBoxOnlyWeekends.setImageResource(0);
				isCheckedAllWeekdays=false;
				refactorDaysFlags();
				break;
			case 2:
				scrollDaysOfWeek.setVisibility(View.GONE);
				scrollDaysOfWeek.setScrollY(0);
				checkBoxAlldays.setChecked(false);
				checkBoxWeekly.setChecked(false);
				checkBoxBiWeekly.setChecked(false);
				checkBoxMonday.setImageResource(0);
				checkBoxTuesday.setImageResource(0);
				checkBoxWednesday.setImageResource(0);
				checkBoxThurday.setImageResource(0);
				checkBoxFriday.setImageResource(0);
				checkBoxSaturday.setImageResource(0);
				checkBoxSunday.setImageResource(0);
				checkBoxAllWeekdays.setImageResource(0);
				checkBoxOnlyWeekends.setImageResource(0);
				isCheckedAllWeekdays=false;
				refactorDaysFlags();
				break;

			default:
				break;
		}
	}

	private boolean checkIfDaysSelected()
	{
		if(checkBoxWeekly.isChecked()|| checkBoxBiWeekly.isChecked())
		{
			if(isCheckedMonday||isCheckedTuesday||isCheckedWednesday||isCheckedThursday||isCheckedFriday||isCheckedSaturday||isCheckedSunday)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		else
		{
			return false;
		}

	}

	private void addDaySelected()
	{
		daySelected.clear();
		if(isCheckedMonday)
		{
			daySelected.add("2");
		}
		if(isCheckedTuesday)
		{
			daySelected.add("3");
		}
		if(isCheckedWednesday)
		{
			daySelected.add("4");
		}
		if(isCheckedThursday)
		{
			daySelected.add("5");
		}
		if(isCheckedFriday)
		{
			daySelected.add("6");
		}
		if(isCheckedSaturday)
		{
			daySelected.add("7");
		}
		if(isCheckedSunday)
		{
			daySelected.add("1");
		}

		String days="";
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
		StaticVariables.addAfterSchoolActivities.setActivityDays(days);

	}


	private void refillActivityDaysData()
	{
		String dayValue=StaticVariables.addAfterSchoolActivities.getActivityDays();
		String[] daySelectedValue=dayValue.split(",");
		if(daySelectedValue.length>0)
		{
			scrollDaysOfWeek.setVisibility(View.VISIBLE);
			scrollDaysOfWeek.setScrollY(0);
			scrollDaysOfWeek.startAnimation(expand);
		}

		for(int i=0;i<daySelectedValue.length;i++)
		{
			if(daySelectedValue[i].toString().trim().equalsIgnoreCase("1"))
			{
				isCheckedSunday=true;
				checkBoxSunday.setImageResource(R.drawable.accept_i);
			}
			else if(daySelectedValue[i].toString().trim().equalsIgnoreCase("2"))
			{
				isCheckedMonday=true;
				checkBoxMonday.setImageResource(R.drawable.accept_i);
			}
			else if(daySelectedValue[i].toString().trim().equalsIgnoreCase("3"))
			{
				isCheckedTuesday=true;
				checkBoxTuesday.setImageResource(R.drawable.accept_i);
			}
			else if(daySelectedValue[i].toString().trim().equalsIgnoreCase("4"))
			{
				isCheckedWednesday=true;
				checkBoxWednesday.setImageResource(R.drawable.accept_i);
			}
			else if(daySelectedValue[i].toString().trim().equalsIgnoreCase("5"))
			{
				isCheckedThursday=true;
				checkBoxThurday.setImageResource(R.drawable.accept_i);
			}
			else if(daySelectedValue[i].toString().trim().equalsIgnoreCase("6"))
			{
				isCheckedFriday=true;
				checkBoxFriday.setImageResource(R.drawable.accept_i);
			}
			else if(daySelectedValue[i].toString().trim().equalsIgnoreCase("7"))
			{
				isCheckedSaturday=true;
				checkBoxSaturday.setImageResource(R.drawable.accept_i);
			}
		}


		if(isCheckedMonday&& isCheckedTuesday&& isCheckedWednesday&&isCheckedThursday&&isCheckedFriday&& !isCheckedSaturday&&!isCheckedSunday)
		{
			isCheckedAllWeekdays=true;
			checkBoxAllWeekdays.setImageResource(R.drawable.accept_i);
		}
		else if(isCheckedSaturday&& isCheckedSunday&&!isCheckedMonday&& !isCheckedTuesday&& !isCheckedWednesday&&!isCheckedThursday&&!isCheckedFriday)
		{
			isCheckedOnlyWeekends=true;
			checkBoxOnlyWeekends.setImageResource(R.drawable.accept_i);
		}


	}

	/**
	 *
	 */
	private void refactorDaysFlags() {
		isCheckedMonday=false;
		isCheckedTuesday=false;
		isCheckedWednesday=false;
		isCheckedThursday=false;
		isCheckedFriday=false;
		isCheckedSaturday=false;
		isCheckedSunday=false;
		isCheckedAllWeekdays=false;
		isCheckedOnlyWeekends=false;
	}
}