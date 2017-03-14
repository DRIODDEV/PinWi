package com.hatchtact.pinwi.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hatchtact.pinwi.GuideSlideActivity;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.DisplayAfterSchoolActivityByChildIdAdapter;
import com.hatchtact.pinwi.classmodel.AfterSchoolActivityByChildID;
import com.hatchtact.pinwi.classmodel.AfterSchoolActivityByChildIDList;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;

@SuppressLint("ResourceAsColor")
public class AfterSchoolActivityByChildIdFragment extends ParentFragment implements OnItemClickListener
{
	private View view;

	private ListView afterSchoolDisplayList=null;
	private ImageView image_addNewAfterSchoolActivity=null;

	private ShowMessages showMessage=null;
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;

	private DisplayAfterSchoolActivityByChildIdAdapter displayafterSchoolActivityByChildIdAdapter;

	private AfterSchoolActivityByChildIDList afterschoolActivityByChildIDList;
	private ArrayList<AfterSchoolActivityByChildID> secondArrayList=new ArrayList<AfterSchoolActivityByChildID>();

	String activityName="";
	int activityId=0;

	private boolean startAsync = false;

	private TextView calender_textView=null;
	private TextView afterschool_textView=null;
	private TextView school_textView=null;

	private LinearLayout layout_addafterschoolActivityByTab=null;
	private TextView addafterschoolTab_text=null;
	private SharePreferenceClass sharePref;
	private ImageView image_infotnSchool;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		StaticVariables.fragmentIndexCurrentTabSchedular=12;

		sharePref=new SharePreferenceClass(getActivity());
		if(!sharePref.isafterschoolTutorial())
		{/*
				sharePref.setafterschoolTutorial(true);
		           ScreenSlidePagerAdapter.NUM_PAGES=7;

				Intent tutorial=new Intent(getActivity(), TutorialPageActivity.class);
				startActivity(tutorial);
				StaticVariables.currentTutorialValue=StaticVariables.afterSchoolTutorial;
			*/}

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.afterschoolactivtiy_display, container, false);
		setHasOptionsMenu(true);
		mListener.onFragmentAttached(true,"  Scheduler");
		startAsync = false;
		image_infotnSchool=(ImageView) view.findViewById(R.id.image_infotnSchool);
		image_infotnSchool.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent tutorial=new Intent(getActivity(), GuideSlideActivity.class);
				startActivity(tutorial);
			}
		});
		StaticVariables.fragmentIndex=11;
		StaticVariables.addAfterSchoolActivities=null;

		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(getActivity());
		checkNetwork=new CheckNetwork();

		afterSchoolDisplayList=(ListView) view.findViewById(R.id.list_dataafterSchool);
		image_addNewAfterSchoolActivity=(ImageView) view.findViewById(R.id.image_addbtnAfterSchool);

		layout_addafterschoolActivityByTab=(LinearLayout) view.findViewById(R.id.layout_addafterschoolActivityByTab);
		addafterschoolTab_text=(TextView) view.findViewById(R.id.addafterschoolTab_text);

		typeFace.setTypefaceRegular(addafterschoolTab_text);


		image_addNewAfterSchoolActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				StaticVariables.fragmentIndexCurrentTabSchedular=23;
				switchingFragments(new AddAfterSchoolCategoriesFragment());
				
			/*	getFragmentManager().beginTransaction().replace(R.id.realtabcontent,
						new AddAfterSchoolCategoriesFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();*/
			}
		});

		layout_addafterschoolActivityByTab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StaticVariables.fragmentIndexCurrentTabSchedular=23;
				switchingFragments(new AddAfterSchoolCategoriesFragment());
				/*getFragmentManager().beginTransaction().add(R.id.realtabcontent,
						new AddSubjectFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();*/
			}
		});

		calender_textView=(TextView) view.findViewById(R.id.text_calendar);
		afterschool_textView=(TextView) view.findViewById(R.id.text_afterschool);
		school_textView=(TextView) view.findViewById(R.id.text_school);


		typeFace.setTypefaceRegular(calender_textView);
		typeFace.setTypefaceRegular(school_textView);
		typeFace.setTypefaceRegular(afterschool_textView);

		calender_textView.setBackgroundResource(R.drawable.rectangular_btnrelation);
		calender_textView.setTextColor(getResources().getColor(R.color.btnrelationselection_color));

		afterschool_textView.setBackgroundResource(R.drawable.rectangular_btnrelation_selection);
		afterschool_textView.setTextColor(getResources().getColor(R.color.font_white_color));

		school_textView.setBackgroundResource(R.drawable.rectangular_btnrelation);
		school_textView.setTextColor(getResources().getColor(R.color.btnrelationselection_color));



		calender_textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				switchingFragments(new CalenderFragment());
				/*getFragmentManager().beginTransaction().replace(R.id.realtabcontent,
						new CalenderFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();*/



			}
		});

		afterschool_textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


			}
		});

		school_textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				switchingFragments(new SubjectActivityByChildIDFragment());
				/*getFragmentManager().beginTransaction().replace(R.id.realtabcontent,
						new SubjectActivityByChildIDFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();*/
			}
		});



		return view;
	}


	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		startAsync = false;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		refreshDataAccordingToChildId();
	}


	/**
	 *
	 */
	private void refreshDataAccordingToChildId() {
		if(!startAsync )
		{
			startAsync = true;

			new GetAfterSchoolActivityByChildId(StaticVariables.currentChild.getChildID()).execute();
		}
	}

	//private ProgressDialog progressDialog=null;

	private class GetAfterSchoolActivityByChildId extends AsyncTask<Void, Void, Integer>
	{
		int childId;

		public GetAfterSchoolActivityByChildId(int childID)
		{
			// TODO Auto-generated constructor stub
			childId=childID;
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

			try {


				if (checkNetwork.checkNetworkConnection(getActivity())) {
					afterschoolActivityByChildIDList = serviceMethod.getAfterSchoolActivityByChildId(childId);
				} else {
					ErrorCode = -1;
				}
			}
			catch (Exception e)
			{

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
			try {


				if (result == -1) {
					showMessage.showToastMessage("Please check your network connection");

					if (checkNetwork.checkNetworkConnection(getActivity()))
						new GetAfterSchoolActivityByChildId(childId).execute();

				} else {
					if (result != -1) {
						// For same activity like math

						//I have to check here if i directly click on school then no data is available so it's a crash
						if (afterschoolActivityByChildIDList != null && afterschoolActivityByChildIDList.getAfterSchoolActivityByChildID().size() > 0) {
							for (int i = 0; i < afterschoolActivityByChildIDList.getAfterSchoolActivityByChildID().size(); i++) {

								layout_addafterschoolActivityByTab.setVisibility(View.GONE);

								boolean isAlreadyExist = false;

								for (int j = 0; j < secondArrayList.size(); j++) {
									if (secondArrayList.get(j).getActivityID() == afterschoolActivityByChildIDList.getAfterSchoolActivityByChildID().get(i).getActivityID()) {
										String previousdayId = secondArrayList.get(j).getDaysValue();
										String currentdayId = "" + afterschoolActivityByChildIDList.getAfterSchoolActivityByChildID().get(i).getDayID();
										String finaldayIds = previousdayId + "," + currentdayId;

										AfterSchoolActivityByChildID childIdModel = new AfterSchoolActivityByChildID();

										childIdModel.setActivityName(afterschoolActivityByChildIDList.getAfterSchoolActivityByChildID().get(i).getActivityName());
										childIdModel.setActivityID(afterschoolActivityByChildIDList.getAfterSchoolActivityByChildID().get(i).getActivityID());
										childIdModel.setDaysValue(finaldayIds);
										childIdModel.setStartTime(afterschoolActivityByChildIDList.getAfterSchoolActivityByChildID().get(i).getStartTime());
										childIdModel.setStartDate(afterschoolActivityByChildIDList.getAfterSchoolActivityByChildID().get(i).getStartDate());
										childIdModel.setEnddate(afterschoolActivityByChildIDList.getAfterSchoolActivityByChildID().get(i).getEnddate());
										childIdModel.setEndTime(afterschoolActivityByChildIDList.getAfterSchoolActivityByChildID().get(i).getEndTime());
										childIdModel.setExamDate(afterschoolActivityByChildIDList.getAfterSchoolActivityByChildID().get(i).getExamDate());
										childIdModel.setIsPrivate(afterschoolActivityByChildIDList.getAfterSchoolActivityByChildID().get(i).isIsPrivate());
										childIdModel.setIsSpecial(afterschoolActivityByChildIDList.getAfterSchoolActivityByChildID().get(i).isIsSpecial());
										childIdModel.setIsVerified(afterschoolActivityByChildIDList.getAfterSchoolActivityByChildID().get(i).isIsVerified());

										childIdModel.setRemarks(afterschoolActivityByChildIDList.getAfterSchoolActivityByChildID().get(i).getRemarks());

										secondArrayList.remove(j);

										secondArrayList.add(j, childIdModel);
										isAlreadyExist = true;
										break;
									}
								}
								if (!isAlreadyExist) {
									AfterSchoolActivityByChildID afterSchoolByChildId = afterschoolActivityByChildIDList.getAfterSchoolActivityByChildID().get(i);
									afterSchoolByChildId.setDaysValue("" + afterSchoolByChildId.getDayID());
									secondArrayList.add(afterSchoolByChildId);
								}
							}

							displayafterSchoolActivityByChildIdAdapter = new DisplayAfterSchoolActivityByChildIdAdapter(getActivity(), secondArrayList);
							afterSchoolDisplayList.setAdapter(displayafterSchoolActivityByChildIdAdapter);
							afterSchoolDisplayList.setOnItemClickListener(AfterSchoolActivityByChildIdFragment.this);
						} else {
							getError();
							layout_addafterschoolActivityByTab.setVisibility(View.VISIBLE);
						}
					} else {
						getError();
						layout_addafterschoolActivityByTab.setVisibility(View.VISIBLE);
					}
				}
			}
			catch (Exception e)
			{

			}
		}
	}

	private void getError()
	{
		/*Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());*/
	}

	AddAfterSchoolFragment addafterSchoolFragment=new AddAfterSchoolFragment();

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub

		Bundle bundle = new Bundle();

		activityName = secondArrayList.get(position).getActivityName();
		activityId = secondArrayList.get(position).getActivityID();
		StaticVariables.subSubCategoryName=activityName;
		StaticVariables.subSubCategoryId=activityId;
		bundle.putString("ActivityName", activityName);
		bundle.putInt("ActivityId", activityId);
		//By default sbke liye updated ki value 0.....bundle milne pr 1st tym whi p hi set kr denge...
		AddAfterSchoolFragment.updatedDataFromAfterSchool = 0;

		bundle.putString("ComingFromWhichScreen", "updateAfterSchoolActivity");
		addafterSchoolFragment.setArguments(bundle);
		StaticVariables.deleteAfterSchoolFromAfterSchool=true;
		StaticVariables.fragmentIndexCurrentTabSchedular=24;
		switchingFragments(addafterSchoolFragment);

		/*getFragmentManager().beginTransaction().replace(R.id.realtabcontent,
				addafterSchoolFragment).addToBackStack("8").commit();*/
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


		if(item.getItemId()!=R.id.action_childName)
		{
			StaticVariables.currentChild=StaticVariables.childInfo.get(item.getItemId());
			getActivity().invalidateOptionsMenu();

			startAsync = false;
			//afterschoolActivityByChildIDList.getAfterSchoolActivityByChildID().clear();

			secondArrayList.clear();
			afterSchoolDisplayList.setAdapter(new DisplayAfterSchoolActivityByChildIdAdapter(getActivity(), secondArrayList));
			refreshDataAccordingToChildId();
			//here we have to refresh data according to child

		}


		return super.onOptionsItemSelected(item);
	}
}

