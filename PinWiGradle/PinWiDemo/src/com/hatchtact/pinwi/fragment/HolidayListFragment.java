package com.hatchtact.pinwi.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.ListView;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.HolidayListAdapter;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.SchedularHolidayList;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;

public class HolidayListFragment extends ParentFragment implements OnItemClickListener
{
	private View view;

	private ShowMessages showMessage=null;  
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;

	private SchedularHolidayList holidayList;
	private HolidayListAdapter adapter=null;
	private ListView listView=null;
	private ImageView addHolidayImage=null;
	private TextView addHoliday_text=null;
	static boolean isAddingHoliday=false;
	static String holidayDesc="";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.activity_list_holiday, container, false);
		setHasOptionsMenu(true);
		mListener.onFragmentAttached(false,"  Scheduler");
		isAddingHoliday=false;
		holidayList=new SchedularHolidayList();
		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(getActivity());
		checkNetwork=new CheckNetwork();



		listView=(ListView) view.findViewById(R.id.holidayNmaeList);




		addHolidayImage= (ImageView) view.findViewById(R.id.addHolidayImage);
		addHoliday_text=(TextView) view.findViewById(R.id.addHoliday_text);
		typeFace.setTypefaceLight(addHoliday_text);

		addHolidayImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				isAddingHoliday=true;
				holidayDesc="";
				if(!StaticVariables.isFromSettingsScreen)
				{
					StaticVariables.fragmentIndexCurrentTabSchedular=81;

					switchingFragments(new HolidayDetailsFragment());
				}
				else
				{
					StaticVariables.fragmentIndexCurrentTabSettings=202;
					switchingFragmentsSettings(new HolidayDetailsFragment());					
				}
			}
		});

		addHoliday_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				holidayDesc="";
				isAddingHoliday=true;
				if(!StaticVariables.isFromSettingsScreen)
				{
					StaticVariables.fragmentIndexCurrentTabSchedular=81;

					switchingFragments(new HolidayDetailsFragment());
				}
				else
				{
					StaticVariables.fragmentIndexCurrentTabSettings=202;
					switchingFragmentsSettings(new HolidayDetailsFragment());						
				}

			}
		});



		new GetHolidayList().execute();

		return view;
	}

	private ProgressDialog progressDialog=null;	

	private class GetHolidayList extends AsyncTask<Void, Void, Integer>
	{
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
				holidayList =serviceMethod.getListOfHolidays(StaticVariables.currentChild.getChildID());
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
					new GetHolidayList().execute();

			}
			else
			{
				if(holidayList!=null && holidayList.getHolidaylist().size()>0)
				{
					adapter=new HolidayListAdapter(getActivity(), holidayList.getHolidaylist());
					listView.setAdapter(adapter);
					listView.setOnItemClickListener(HolidayListFragment.this);

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
		Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) 
	{
		// TODO Auto-generated method stub

		holidayDesc=holidayList.getHolidaylist().get(position).getHolidayDescription();
		isAddingHoliday=false;

		if(!StaticVariables.isFromSettingsScreen)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=81;

			switchingFragments(new HolidayDetailsFragment());
		}
		else
		{
			StaticVariables.fragmentIndexCurrentTabSettings=202;
			switchingFragmentsSettings(new HolidayDetailsFragment());				
		}

	}


	// the create options menu with a MenuInflater to have the menu from your fragment
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		for(int i=0;i<menu.size();i++)
		{
			if(menu.getItem(i).getItemId()!=R.id.action_childName)
				menu.getItem(i).setVisible(true);
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
		else if(item.getItemId()!=R.id.action_childName)
		{
			StaticVariables.currentChild=StaticVariables.childInfo.get(item.getItemId());
			getActivity().invalidateOptionsMenu();

			//here we will refresh data of hoilday list
			if(holidayList!=null)
			holidayList.getHolidaylist().clear();
			if(listView!=null)
			{
			 listView.setAdapter(null);
			}
			holidayDesc="";
			new GetHolidayList().execute();
			


		} 
		return super.onOptionsItemSelected(item);
	}

	/*@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		//if (resultCode == -1) 
		{
			switch(requestCode) 
			{

			case 200:
				new GetHolidayList().execute();

				break;
			}
		}
	}*/
	
	protected void switchingFragmentsSettings(Fragment toFragment)
	{
		System.out.println("In fragment: "+StaticVariables.fragmentIndexCurrentTabSchedular);
		getFragmentManager().beginTransaction().replace(R.id.framelayout1,
				toFragment).commit();  
		getFragmentManager().executePendingTransactions();      // <----- This is the key 
	}

}
