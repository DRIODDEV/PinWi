package com.hatchtact.pinwi.fragment;

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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.DisplayAllyByChildAndActivityIdAdapter;
import com.hatchtact.pinwi.classmodel.DisplayAllyListByChildAndActivityId;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;

public class DisplayAllyInformationFragment extends ParentFragment implements OnItemClickListener
{
	private View view;

	private LinearLayout layout_addAllyNotification=null;
	private TextView text_addallyNotify=null;

	private ListView allyDataDisplayList=null;
	private TextView activityName_text=null;
	private TextView informAlly_text=null;

	private ShowMessages showMessage=null;  
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;

	private DisplayAllyByChildAndActivityIdAdapter displayAllyByChildAndActivityIdAdapter;

	private DisplayAllyListByChildAndActivityId displayAllyListByChildAndActivityId;

	private boolean startAsync = false;
	
	public static boolean updatedDataFromDisplayAlly = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		view=inflater.inflate(R.layout.activity_showallybyid, container, false);
		mListener.onFragmentAttached(false,"  Scheduler");
		setHasOptionsMenu(true);
		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(getActivity());
		checkNetwork=new CheckNetwork();

		startAsync = false;
		
		StaticVariables.AllyName="";
		
		layout_addAllyNotification=(LinearLayout) view.findViewById(R.id.layout_addAllyNotification);
		text_addallyNotify=(TextView) view.findViewById(R.id.text_addallyNotify);
		allyDataDisplayList= (ListView) view.findViewById(R.id.containall_allyData);
		activityName_text=(TextView) view.findViewById(R.id.activityName_text);
		informAlly_text=(TextView) view.findViewById(R.id.informAlly_text);

		typeFace.setTypefaceRegular(text_addallyNotify);
		typeFace.setTypefaceRegular(activityName_text);
		typeFace.setTypefaceRegular(informAlly_text);
		
		activityName_text.setText(StaticVariables.subSubCategoryName);

		layout_addAllyNotification.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				updatedDataFromDisplayAlly=false;
				
				if(StaticVariables.fragmentIndexCurrentTabSchedular==28)
				{
					StaticVariables.fragmentIndexCurrentTabSchedular=32;
				} 
				else if(StaticVariables.fragmentIndexCurrentTabSchedular==43)
				{
					StaticVariables.fragmentIndexCurrentTabSchedular=45;
				}
				
				else if(StaticVariables.fragmentIndexCurrentTabSchedular==51)
				{
					StaticVariables.fragmentIndexCurrentTabSchedular=53;
				}
				
				else if(StaticVariables.fragmentIndexCurrentTabSchedular==58)
				{
					StaticVariables.fragmentIndexCurrentTabSchedular=60;
				}
				
				else if(StaticVariables.fragmentIndexCurrentTabSchedular==68)
				{
					StaticVariables.fragmentIndexCurrentTabSchedular=70;
				}
				
				else if(StaticVariables.fragmentIndexCurrentTabSchedular==76)
				{
					StaticVariables.fragmentIndexCurrentTabSchedular=77;

				}
				switchingFragments(new AllyDropPickFragment());
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
			//new GetAllyDataByChildIdAndActivityId(2804/*StaticVariables.currentChild.getChildID()*/,16/*StaticVariables.subSubCategoryId*/).execute();
			
			new GetAllyDataByChildIdAndActivityId(StaticVariables.currentChild.getChildID(),StaticVariables.subSubCategoryId).execute();
		}
	}

	private ProgressDialog progressDialog=null;	

	private class GetAllyDataByChildIdAndActivityId extends AsyncTask<Void, Void, Integer>
	{
		int childId;
		int activityId;

		public GetAllyDataByChildIdAndActivityId(int childID,int activityID)
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
				displayAllyListByChildAndActivityId =serviceMethod.getAllyListByChildAndActivityId(childId, activityId);
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
					new GetAllyDataByChildIdAndActivityId(childId,activityId).execute();

			}
			else
			{
				if(result!=-1)
				{
					// For same activity like math

					//I have to check here if i directly click on school then no data is available so it's a crash
					if(displayAllyListByChildAndActivityId != null && displayAllyListByChildAndActivityId.getDisplayAllyByChildAndActivityId().size()>0)
					{
						displayAllyByChildAndActivityIdAdapter=new DisplayAllyByChildAndActivityIdAdapter(getActivity(), displayAllyListByChildAndActivityId.getDisplayAllyByChildAndActivityId());
						allyDataDisplayList.setAdapter(displayAllyByChildAndActivityIdAdapter);
						allyDataDisplayList.setOnItemClickListener(DisplayAllyInformationFragment.this);
					}
					else
					{
						//getError();
					}
				}	
				else
				{
					//getError();
				}
			}
		}	
	}
	
	private void getError()
	{
		Error err=serviceMethod.getError();
		showMessage.showAlert("Warning", err.getErrorDesc());
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) 
	{
		// TODO Auto-generated method stub
		
		updatedDataFromDisplayAlly=true;
		
		if(StaticVariables.fragmentIndexCurrentTabSchedular==28)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=32;
		}
		else if(StaticVariables.fragmentIndexCurrentTabSchedular==43)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=45;
		}
		
		else if(StaticVariables.fragmentIndexCurrentTabSchedular==51)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=53;
		}
		
		else if(StaticVariables.fragmentIndexCurrentTabSchedular==58)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=60;
		}
		
		else if(StaticVariables.fragmentIndexCurrentTabSchedular==68)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=70;

		}
		
		else if(StaticVariables.fragmentIndexCurrentTabSchedular==76)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=77;
		}
		
		StaticVariables.AllyId=displayAllyListByChildAndActivityId.getDisplayAllyByChildAndActivityId().get(position).getAllyID();
		switchingFragments(new AllyDropPickFragment());	
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
