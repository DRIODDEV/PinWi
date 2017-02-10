package com.hatchtact.pinwi.fragment.whattodo;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.WhatToDoActivityListAdapter;
import com.hatchtact.pinwi.adapter.WhatToDoSearchActivityListAdapter;
import com.hatchtact.pinwi.classmodel.GetListOfActivitiesOnRecommendedByClusterIDList;
import com.hatchtact.pinwi.classmodel.SearchActivitiesByChildIDList;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;

public  class ActivityListFragment extends ParentFragment 
{
	private View view;
	private SharePreferenceClass sharePref;
	private TextView clusterName=null;
	private TextView totallanguages=null;
	private EditText editsearch;
	private ImageView imageSearch;
	private LinearLayout layout_nodata;
	private ImageView noconnectionimage;
	private TextView noconnectiontext;
	private ListView listconnprofile;

	private ServiceMethod servicemethod;
	private CheckNetwork checkNetwork;
	private ShowMessages showMessage;
	private GetListOfActivitiesOnRecommendedByClusterIDList getListOfActivitiesOnRecommendedByClusterIDList;
	private SearchActivitiesByChildIDList searchActivitiesByChildID;
	private WhatToDoActivityListAdapter adapter;
	private WhatToDoSearchActivityListAdapter searchAdapter;

	private boolean flag_loading=false;
	private boolean isSearchList=false;
	private boolean isSearchDone=false;
	private CustomDialogClass customDialog;
	private String searchWebserviceName="",ActivityListWebserviceName="";



	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//StaticVariables.fragmentIndexCurrentTabSchedular=201;
		sharePref=new SharePreferenceClass(getActivity());


	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		if(savedInstanceState==null)
		{
			view=inflater.inflate(R.layout.clusterdetail_fragment, container, false);
			setHasOptionsMenu(true);
			mListener.onFragmentAttached(false,"  What to do");
			initialize();//initialize all view items in fragment
			checkNetwork=new CheckNetwork();
			showMessage=new ShowMessages(getActivity());
			servicemethod=new ServiceMethod();
			customDialog=new CustomDialogClass(ActivityListFragment.this);

			customDialog.setOnKeyListener(new DialogInterface.OnKeyListener()
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
			});
			getListOfActivitiesOnRecommendedByClusterIDList=new GetListOfActivitiesOnRecommendedByClusterIDList();
			getListOfActivitiesOnRecommendedByClusterIDList.getListOfActivitiesOnRecommendedByClusterID().clear();
			searchActivitiesByChildID=new SearchActivitiesByChildIDList();
			searchActivitiesByChildID.getsearchActivitiesByChildID().clear();
			new fetchActivityList(1).execute();	
			isSearchDone=false;


		}
		return view;		
	}

	private void initialize() 
	{

		// TODO Auto-generated method stub
		clusterName=(TextView) view.findViewById(R.id.clusterName);
		clusterName.setText(StaticVariables.ClusterName);
		if(StaticVariables.ClusterName.startsWith("Recommended"))
		{
			searchWebserviceName="SearchActivitiesByChildID";
			ActivityListWebserviceName="GetListOfActivitiesOnRecommendedByClusterID";
			StaticVariables.screenIndex=0;
		}
		else
		{
			if(StaticVariables.ClusterName.startsWith("Explore"))
			{
				StaticVariables.screenIndex=2;
			}
			else
			{
				StaticVariables.screenIndex=1;
			}

			searchWebserviceName="SearchActivitiesOnNetworkByClusterID";
			ActivityListWebserviceName="GetListOfActivitiesOnNetworkByClusterID";
		}
		totallanguages=(TextView) view.findViewById(R.id.totallanguages);
		totallanguages.setText("");
		layout_nodata=(LinearLayout) view.findViewById(R.id.layout_nodata);
		noconnectionimage=(ImageView) view.findViewById(R.id.noconnectionimage);
		noconnectiontext=(TextView) view.findViewById(R.id.noconnectiontext);
		listconnprofile=(ListView) view.findViewById(R.id.listconnprofile);
		imageSearch=(ImageView) view.findViewById(R.id.imageSearch);
		editsearch=(EditText) view.findViewById(R.id.editSearch);
		editsearch.setHint("Search");
		//editsearch.setImeOptions(EditorInfo.IME_ACTION_DONE);
		isSearchList=false;

		imageSearch.setVisibility(View.VISIBLE);

		typeFace.setTypefaceRegular(clusterName);
		typeFace.setTypefaceRegular(totallanguages);
		typeFace.setTypefaceRegular(editsearch);
		typeFace.setTypefaceRegular(noconnectiontext);
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);



		listconnprofile.setOnScrollListener(new OnScrollListener() {



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
						//additems();//here we have to add items on scroll in search list and  
						if(!isSearchList)
						{
							if(totalItemCount% 8==0)
							{
								new fetchActivityList((totalItemCount/8)+1).execute();	
							}
						}
						else
						{
							if(totalItemCount% 8==0)
							{
								new searchActivityListGlobally((totalItemCount/8)+1).execute();
							}
						}
					}
				}
			}
		});


		listconnprofile.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub



				customDialog.show();
				if(!isSearchList)
				{
					StaticVariables.WhatToDoActivityName=getListOfActivitiesOnRecommendedByClusterIDList.getListOfActivitiesOnRecommendedByClusterID().get(position).getActivityName();
					StaticVariables.WhatToDoActivityId=getListOfActivitiesOnRecommendedByClusterIDList.getListOfActivitiesOnRecommendedByClusterID().get(position).getActivityID();

					customDialog.isActivityScheduled(getListOfActivitiesOnRecommendedByClusterIDList.getListOfActivitiesOnRecommendedByClusterID().get(position).getIsScheduled());
				}
				else
				{
					StaticVariables.WhatToDoActivityName=searchActivitiesByChildID.getsearchActivitiesByChildID().get(position).getActivityName();
					StaticVariables.WhatToDoActivityId=searchActivitiesByChildID.getsearchActivitiesByChildID().get(position).getActivityID();
					customDialog.isActivityScheduled(searchActivitiesByChildID.getsearchActivitiesByChildID().get(position).getIsScheduled());

				}

				//first check activity scheduled or not



			}
		});
		editsearch.addTextChangedListener(new TextWatcher() {

			int len = 0;
			@Override
			public void onTextChanged(CharSequence s, int start,
					int before, int count) {
				// TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start,
					int count, int after) {
				// TODO Auto-generated method stub
				String str = s.toString();
				len = str.length();
			}

			@Override
			public void afterTextChanged(Editable s) {
				if(!isSearchDone)
				{

					String str = s.toString();
					boolean hasChar = false;
					for (int k = 0; k < str.length(); k++) {
						if (Character.isLetter(str.charAt(k))) {
							hasChar = true;
						}
					}
					if(len>1)
					{
						hasChar=true;
					}
					if(hasChar)
					{
						isSearchList=true;
						if(searchActivitiesByChildID!=null)
						{
							searchActivitiesByChildID.getsearchActivitiesByChildID().clear();
						}
						else
						{
							searchActivitiesByChildID=new SearchActivitiesByChildIDList();
						}
						isSearchDone=true;
						new searchActivityListGlobally(1).execute();

					}
					else
					{

						if(str.equalsIgnoreCase(""))
						{
							isSearchList=false;
							if(getListOfActivitiesOnRecommendedByClusterIDList!=null)
								getListOfActivitiesOnRecommendedByClusterIDList.getListOfActivitiesOnRecommendedByClusterID().clear();
							else
							{
								getListOfActivitiesOnRecommendedByClusterIDList=new GetListOfActivitiesOnRecommendedByClusterIDList();
							}
							isSearchDone=true;
							new fetchActivityList(1).execute();	
						}
					}

				}
			}
		});






		editsearch.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {

				if (actionId == EditorInfo.IME_ACTION_NEXT)
				{
					return true;
				} else if (actionId == EditorInfo.IME_ACTION_DONE) 
				{
					return true;
				} else 
				{
					return true;
				}

			}
		});

	}

	public static ActivityListFragment fr;

	public static ActivityListFragment getInstance()
	{
		if(fr==null)
		{
			fr = new ActivityListFragment();
		}
		return fr;
	}




	/*// the create options menu with a MenuInflater to have the menu from your fragment
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

			//here we have to refresh data according to child
			if(getListOfActivitiesOnRecommendedByClusterIDList==null)
			{
				getListOfActivitiesOnRecommendedByClusterIDList=new GetListOfActivitiesOnRecommendedByClusterIDList();
			}
			getListOfActivitiesOnRecommendedByClusterIDList.getListOfActivitiesOnRecommendedByClusterID().clear();

			if(searchActivitiesByChildID==null)
			{
				searchActivitiesByChildID=new SearchActivitiesByChildIDList();
			}
			searchActivitiesByChildID.getsearchActivitiesByChildID().clear();
			if(editsearch!=null)
			{
				editsearch.setText("");
			}
			flag_loading=false;
			isSearchList=false;
			isSearchDone=false;
			try {
				hideKeyBoard();
				if (progressDialog!=null)
					progressDialog.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			new fetchActivityList(1).execute();	

		}


		return super.onOptionsItemSelected(item);
	}

	 */
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

	private ProgressDialog progressDialog=null;	

	private class fetchActivityList extends AsyncTask<Void, Void, Integer>
	{
		int pageIndex=1;

		public fetchActivityList(int i) 
		{
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

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				if(getListOfActivitiesOnRecommendedByClusterIDList.getListOfActivitiesOnRecommendedByClusterID().size()==0)
				{
					getListOfActivitiesOnRecommendedByClusterIDList =servicemethod.getListOfActivities(StaticVariables.ClusterId,pageIndex,8,ActivityListWebserviceName);
					flag_loading=false;
				}
				else
				{
					GetListOfActivitiesOnRecommendedByClusterIDList list=servicemethod.getListOfActivities(StaticVariables.ClusterId,pageIndex,8,ActivityListWebserviceName);
					if(list!=null && list.getListOfActivitiesOnRecommendedByClusterID().size()>0)
					{
						flag_loading=false;
						getListOfActivitiesOnRecommendedByClusterIDList.getListOfActivitiesOnRecommendedByClusterID().addAll(list.getListOfActivitiesOnRecommendedByClusterID());
					}

				}
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
				hideKeyBoard();
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
					new fetchActivityList(1).execute();

			}
			else
			{
				try {
					if(getListOfActivitiesOnRecommendedByClusterIDList!=null && getListOfActivitiesOnRecommendedByClusterIDList.getListOfActivitiesOnRecommendedByClusterID().size()>0)
					{
						layout_nodata.setVisibility(View.GONE);
						noconnectionimage.setVisibility(View.GONE);
						noconnectiontext.setVisibility(View.GONE);
						listconnprofile.setVisibility(View.VISIBLE);
						totallanguages.setText(getListOfActivitiesOnRecommendedByClusterIDList.getListOfActivitiesOnRecommendedByClusterID().get(0).getClusterName()+" - "+getListOfActivitiesOnRecommendedByClusterIDList.getListOfActivitiesOnRecommendedByClusterID().get(0).getActivityCount());


						if(pageIndex==1)
						{
							adapter=new WhatToDoActivityListAdapter(getActivity(),getListOfActivitiesOnRecommendedByClusterIDList,ActivityListFragment.this);
							listconnprofile.setAdapter(adapter);
							adapter.notifyDataSetChanged();
						}
						else
						{
							adapter.notifyDataSetChanged();
							listconnprofile.invalidate();
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
				isSearchDone=false;
			}	
		}	
	}

	private void getError()
	{

		layout_nodata.setVisibility(View.VISIBLE);
		listconnprofile.setVisibility(View.GONE);
		com.hatchtact.pinwi.classmodel.Error err = servicemethod.getError();	
		//showMessage.showAlert("Warning", err.getErrorDesc());
		noconnectionimage.setVisibility(View.VISIBLE);
		noconnectiontext.setVisibility(View.VISIBLE);
		noconnectiontext.setText(err.getErrorDesc());
	}


	private ProgressDialog progressDialogSearch=null;	

	private class searchActivityListGlobally extends AsyncTask<Void, Void, Integer>
	{

		int pageIndexSearch=1;
		public searchActivityListGlobally(int i) {
			// TODO Auto-generated constructor stub
			pageIndexSearch=i;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialogSearch = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialogSearch.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				if(searchActivitiesByChildID.getsearchActivitiesByChildID().size()==0)
				{
					searchActivitiesByChildID =servicemethod.searchActivitiesList(StaticVariables.currentChild.getChildID()/*37*/, StaticVariables.ClusterId, editsearch.getText().toString().trim(), pageIndexSearch,8,searchWebserviceName);
					flag_loading=false;
				}
				else
				{
					SearchActivitiesByChildIDList list=servicemethod.searchActivitiesList(StaticVariables.currentChild.getChildID()/*37*/, StaticVariables.ClusterId, editsearch.getText().toString().trim(), pageIndexSearch,8,searchWebserviceName);
					if(list!=null && list.getsearchActivitiesByChildID().size()>0)
					{
						flag_loading=false;
						searchActivitiesByChildID.getsearchActivitiesByChildID().addAll(list.getsearchActivitiesByChildID());
					}


				}}
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
				if (progressDialogSearch.isShowing())
					progressDialogSearch.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(getActivity()))
					new searchActivityListGlobally(1).execute();

			}
			else
			{
				if(searchActivitiesByChildID!=null && searchActivitiesByChildID.getsearchActivitiesByChildID().size()>0)
				{
					layout_nodata.setVisibility(View.GONE);
					noconnectionimage.setVisibility(View.GONE);
					noconnectiontext.setVisibility(View.GONE);
					listconnprofile.setVisibility(View.VISIBLE);


					if(pageIndexSearch==1)
					{
						searchAdapter=new WhatToDoSearchActivityListAdapter(getActivity(),searchActivitiesByChildID,ActivityListFragment.this);
						listconnprofile.setAdapter(searchAdapter);
						searchAdapter.notifyDataSetChanged();
					}
					else
					{
						searchAdapter.notifyDataSetChanged();
						listconnprofile.invalidate();
					}

				}
				else
				{
					getError();
				}	
			}	
			isSearchDone=false;
		}	
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


	public void setInviteButton()
	{
		layout_nodata.setVisibility(View.VISIBLE);
		listconnprofile.setVisibility(View.GONE);
		//com.demo.pinwi.classmodel.Error err = servicemethod.getError();	
		//showMessage.showAlert("Warning", err.getErrorDesc());
		noconnectionimage.setVisibility(View.VISIBLE);
		noconnectiontext.setVisibility(View.VISIBLE);
		noconnectiontext.setText("You have no parent connection.");

	}


	public void switchScreen(Fragment fragment)
	{
		switchingFragments(fragment);
	}



}
