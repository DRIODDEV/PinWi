package com.hatchtact.pinwi.fragment.whattodo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.WhoIsDoingThisListAdapter;
import com.hatchtact.pinwi.classmodel.GetChildsDetailsOnRecommendedByActivityID;
import com.hatchtact.pinwi.classmodel.GetChildsDetailsOnRecommendedByActivityIDList;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;

public class WhoDoingThisFragment extends ParentFragment 
{
	private View view;

	private ShowMessages showMessage=null;  
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;

	private GetChildsDetailsOnRecommendedByActivityIDList gettChildDetailsList;

	private WhoIsDoingThisListAdapter adapter=null;

	private ListView listView=null;
	private TextView activityName=null;
	private TextView headingScreen=null;

	private boolean flag_loading=false;
	private LinearLayout layout_nodata;
	private ImageView noconnectionimage;
	private TextView noconnectiontext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view=inflater.inflate(R.layout.myprofileconnectionscreen, container, false);
		setHasOptionsMenu(true);
		mListener.onFragmentAttached(false,"  What to do");
		StaticVariables.fragmentIndex=6;

		gettChildDetailsList=new GetChildsDetailsOnRecommendedByActivityIDList();
		gettChildDetailsList.getChildsDetailsOnRecommendedByActivityID().clear();
		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(getActivity());
		checkNetwork=new CheckNetwork();


		listView=(ListView) view.findViewById(R.id.listconnprofile);
		activityName=(TextView) view.findViewById(R.id.childNameprofileconn);
		headingScreen=(TextView) view.findViewById(R.id.totalconnections);

		layout_nodata=(LinearLayout) view.findViewById(R.id.layout_nodata);
		noconnectionimage=(ImageView) view.findViewById(R.id.noconnectionimage);
		noconnectiontext=(TextView) view.findViewById(R.id.noconnectiontext);
		
		typeFace.setTypefaceRegular(activityName);
		typeFace.setTypefaceLight(headingScreen);

		activityName.setText(StaticVariables.WhatToDoActivityName);
		headingScreen.setText("WHO IS DOING THIS?");

		listView.setOnScrollListener(new OnScrollListener() {



			public void onScrollStateChanged(AbsListView view, int scrollState) 
			{

			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0 && totalItemCount>=8)
				{
					if(flag_loading == false)
					{
						flag_loading = true;

						if(totalItemCount% 8==0)
						{
							new getListOfNetworkConnectionsProfile((totalItemCount/8)+1).execute();
						}

					}
				}
			}
		});
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				GetChildsDetailsOnRecommendedByActivityID model=gettChildDetailsList.getChildsDetailsOnRecommendedByActivityID().get(position);
				StaticVariables.NetworkChildId=model.getChildID();
				StaticVariables.NetworkExhilaratorChildName=model.getChildName();
				
			
				if(StaticVariables.fragmentIndexCurrentTabWhatToDo==305)
					StaticVariables.fragmentIndexCurrentTabWhatToDo=308;
					else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==306)
						StaticVariables.fragmentIndexCurrentTabWhatToDo=310;
					else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==307)
						StaticVariables.fragmentIndexCurrentTabWhatToDo=312;
				switchingFragments(new ChildDetailFragment());
			}
		});



		new getListOfNetworkConnectionsProfile(1).execute();

		return view;		
	}

	private ProgressDialog progressDialog=null;	

	private class getListOfNetworkConnectionsProfile extends AsyncTask<Void, Void, Integer>
	{
		int pageIndex=1;

		public getListOfNetworkConnectionsProfile(int i) {
			// TODO Auto-generated constructor stub
			pageIndex=i;
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

			try {
				if(checkNetwork.checkNetworkConnection(getActivity()))
				{
					if(gettChildDetailsList.getChildsDetailsOnRecommendedByActivityID().size()==0)
					{
						gettChildDetailsList =serviceMethod.getChildDetails(StaticVariables.WhatToDoActivityId, pageIndex, 8);
						flag_loading=false;
					}
					else
					{
						GetChildsDetailsOnRecommendedByActivityIDList list=serviceMethod.getChildDetails(StaticVariables.WhatToDoActivityId, pageIndex, 8);
						if(list!=null && list.getChildsDetailsOnRecommendedByActivityID().size()>0)
						{
							flag_loading=false;
							gettChildDetailsList.getChildsDetailsOnRecommendedByActivityID().addAll(list.getChildsDetailsOnRecommendedByActivityID());
						}


					}
				}
				else 
				{
					ErrorCode=-1;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
					new getListOfNetworkConnectionsProfile(pageIndex).execute();

			}
			else
			{
				try {
					if(gettChildDetailsList!=null && gettChildDetailsList.getChildsDetailsOnRecommendedByActivityID().size()>0)
					{

						layout_nodata.setVisibility(View.GONE);
						noconnectionimage.setVisibility(View.GONE);
						noconnectiontext.setVisibility(View.GONE);
						listView.setVisibility(View.VISIBLE);
						
						if(pageIndex==1)
						{
							adapter=new WhoIsDoingThisListAdapter(getActivity(),gettChildDetailsList);
							listView.setAdapter(adapter);
							
							adapter.notifyDataSetChanged();
						}
						else
						{
							adapter.notifyDataSetChanged();
							listView.invalidate();
						}

					}
					else
					{	
						getError();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}	
		}	
	}

	public static WhoDoingThisFragment profileConnFrag;

	public static WhoDoingThisFragment getInstance()
	{
		if(profileConnFrag==null)
		{
			profileConnFrag = new WhoDoingThisFragment();
		}
		return profileConnFrag;
	}

	

	private void getError()
	{

		layout_nodata.setVisibility(View.VISIBLE);
		listView.setVisibility(View.GONE);
		com.hatchtact.pinwi.classmodel.Error err = serviceMethod.getError();	
		//showMessage.showAlert("Warning", err.getErrorDesc());
		noconnectionimage.setVisibility(View.VISIBLE);
		noconnectiontext.setVisibility(View.VISIBLE);
		noconnectiontext.setText(err.getErrorDesc());
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
	
	

	
	
}
