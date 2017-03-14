package com.hatchtact.pinwi.fragment;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.hatchtact.pinwi.adapter.DisplaySubjectActivityByChildIdAdapter;
import com.hatchtact.pinwi.classmodel.SubjectActivitiesByChildID;
import com.hatchtact.pinwi.classmodel.SubjectActivitiesByChildIDList;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.SocialConstants;
import com.hatchtact.pinwi.utility.StaticVariables;

@SuppressLint("ResourceAsColor")
public class SubjectActivityByChildIDFragment extends ParentFragment implements OnItemClickListener
{
	private View view;

	private ListView subjectDisplayList=null;
	private ImageView image_addNewActivity=null;

	private ShowMessages showMessage=null;
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;

	private DisplaySubjectActivityByChildIdAdapter displaysubjectActivityByChildIdAdapter;

	private SubjectActivitiesByChildIDList subjectActivitiesByChildIDList;
	private ArrayList<SubjectActivitiesByChildID> secondArrayList=new ArrayList<SubjectActivitiesByChildID>();

	String subjectName="";
	int activityId=0;

	private boolean startAsync = false;

	private TextView calender_textView=null;
	private TextView afterschool_textView=null;
	private TextView school_textView=null;
	private LinearLayout layout_addschoolActivityByTab=null;
	private TextView addschoolTab_text=null;
	private SharePreferenceClass sharePref;
	private ImageView image_infotnSchool;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		StaticVariables.fragmentIndexCurrentTabSchedular=13;

		sharePref=new SharePreferenceClass(getActivity());
		if(!sharePref.isatSchoolTutorial())
		{
			sharePref.setAtSchoolTutorial(true);
			// ScreenSlidePagerAdapter.NUM_PAGES=6;
			social.CompletedTutorialFacebookLog();
			social.CompletedTutorialGoogleAnalyticsLog();
			Intent tutorial=new Intent(getActivity(), GuideSlideActivity.class);
			startActivity(tutorial);
			//getActivity().overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_translate);
			//StaticVariables.currentTutorialValue=StaticVariables.atSchoolTutorial;
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		view=inflater.inflate(R.layout.subjectactivtiy_display, container, false);
		setHasOptionsMenu(true);
		mListener.onFragmentAttached(true,"  Scheduler");

		startAsync = false;

		StaticVariables.fragmentIndex=4;

		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(getActivity());
		checkNetwork=new CheckNetwork();

		subjectDisplayList=(ListView) view.findViewById(R.id.list_dataSchool);
		image_addNewActivity=(ImageView) view.findViewById(R.id.image_addbtnSchool);
		image_infotnSchool=(ImageView) view.findViewById(R.id.image_infotnSchool);
		layout_addschoolActivityByTab=(LinearLayout) view.findViewById(R.id.layout_addschoolActivityByTab);
		addschoolTab_text=(TextView) view.findViewById(R.id.addschoolTab_text);

		typeFace.setTypefaceRegular(addschoolTab_text);

		image_infotnSchool.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent tutorial=new Intent(getActivity(), GuideSlideActivity.class);
				startActivity(tutorial);
			}
		});

		image_addNewActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				StaticVariables.fragmentIndexCurrentTabSchedular=25;

				switchingFragments(new AddSubjectFragment());
				/*getFragmentManager().beginTransaction().replace(R.id.realtabcontent,
						new AddSubjectFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();*/
			}
		});

		layout_addschoolActivityByTab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StaticVariables.fragmentIndexCurrentTabSchedular=25;

				switchingFragments(new AddSubjectFragment());
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

		afterschool_textView.setBackgroundResource(R.drawable.rectangular_btnrelation);
		afterschool_textView.setTextColor(getResources().getColor(R.color.btnrelationselection_color));

		school_textView.setBackgroundResource(R.drawable.rectangular_btnrelation_selection);
		school_textView.setTextColor(getResources().getColor(R.color.font_white_color));




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

				switchingFragments(new AfterSchoolActivityByChildIdFragment());

				/*getFragmentManager().beginTransaction().replace(R.id.realtabcontent,
						new AfterSchoolActivityByChildIdFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();*/

			}
		});

		school_textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


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

		refreshAccordingToChildId();
	}

	/**
	 *
	 */
	private void refreshAccordingToChildId() {


		if(!startAsync )
		{
			startAsync = true;

			try {
				new GetActivityByChildId(StaticVariables.currentChild.getChildID()).execute();
			}
			catch (Exception e)
			{

			}
		}
	}

	//private ProgressDialog progressDialog=null;

	private class GetActivityByChildId extends AsyncTask<Void, Void, Integer>
	{
		int childId;

		public GetActivityByChildId(int childID)
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
					subjectActivitiesByChildIDList = serviceMethod.getSubjectActivitiesByChild(childId);
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
						new GetActivityByChildId(childId).execute();

				} else {
					if (result != -1) {
						// For same activity like math

						//I have to check here if i directly click on school then no data is available so it's a crash
						if (subjectActivitiesByChildIDList != null && subjectActivitiesByChildIDList.getSubjectActivitiesByChildID().size() > 0) {
							layout_addschoolActivityByTab.setVisibility(View.GONE);
							for (int i = 0; i < subjectActivitiesByChildIDList.getSubjectActivitiesByChildID().size(); i++) {
								boolean isAlreadyExist = false;

								for (int j = 0; j < secondArrayList.size(); j++) {
									if (secondArrayList.get(j).getActivityID() == subjectActivitiesByChildIDList.getSubjectActivitiesByChildID().get(i).getActivityID()) {
										String previousdayId = secondArrayList.get(j).getDayid();
										String currentdayId = subjectActivitiesByChildIDList.getSubjectActivitiesByChildID().get(i).getDayid();
										String finaldayIds = previousdayId + "," + currentdayId;
										SubjectActivitiesByChildID childIdModel = new SubjectActivitiesByChildID();

										childIdModel.setName(subjectActivitiesByChildIDList.getSubjectActivitiesByChildID().get(i).getName());
										childIdModel.setActivityID(subjectActivitiesByChildIDList.getSubjectActivitiesByChildID().get(i).getActivityID());
										childIdModel.setDayid(finaldayIds);

										secondArrayList.remove(j);

										secondArrayList.add(j, childIdModel);
										isAlreadyExist = true;
										break;
									}
								}
								if (!isAlreadyExist) {
									secondArrayList.add(subjectActivitiesByChildIDList.getSubjectActivitiesByChildID().get(i));
								}
							}

							displaysubjectActivityByChildIdAdapter = new DisplaySubjectActivityByChildIdAdapter(getActivity(), secondArrayList);
							subjectDisplayList.setAdapter(displaysubjectActivityByChildIdAdapter);
							subjectDisplayList.setOnItemClickListener(SubjectActivityByChildIDFragment.this);
						} else {
							getError();
							layout_addschoolActivityByTab.setVisibility(View.VISIBLE);
						}
					} else {
						getError();
						layout_addschoolActivityByTab.setVisibility(View.VISIBLE);
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

	public static SubjectActivityByChildIDFragment subjectActivityByChildIDFragment;

	public static SubjectActivityByChildIDFragment getInstance()
	{
		if(subjectActivityByChildIDFragment==null)
		{
			subjectActivityByChildIDFragment = new SubjectActivityByChildIDFragment();
		}
		return subjectActivityByChildIDFragment;
	}

	AddSchoolFragment addSchoolFragment=new AddSchoolFragment();

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();

		subjectName = secondArrayList.get(position).getName();
		activityId = secondArrayList.get(position).getActivityID();

		bundle.putString("SubjectName", subjectName);
		bundle.putInt("ActivityId", activityId);
		bundle.putString("ComingFromWhichScreen", "updateSchoolActivity");
		addSchoolFragment.setArguments(bundle);
		StaticVariables.fragmentIndexCurrentTabSchedular=26;
		switchingFragments(addSchoolFragment);


		/*getFragmentManager().beginTransaction().replace(R.id.realtabcontent,
				addSchoolFragment).addToBackStack("school").commit();*/
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
			//subjectActivitiesByChildIDList.getSubjectActivitiesByChildID().clear();

			secondArrayList.clear();
			subjectDisplayList.setAdapter(new DisplaySubjectActivityByChildIdAdapter(getActivity(), secondArrayList));
			refreshAccordingToChildId();
			//here we have to refresh data according to child

		}


		return super.onOptionsItemSelected(item);
	}


}
