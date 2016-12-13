package com.hatchtact.pinwi.fragment.whattodo;


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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.WhatToDoRecommendedListAdapter;
import com.hatchtact.pinwi.classmodel.GetListOfClustersOnRecommendedByChildIDList;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;

public  class WhatToDoRecommendedFragment extends ParentFragment 
{
	private View view;
	private SharePreferenceClass sharePref;
	private TextView recommendations_textView=null;
	private TextView network_textView=null;
	private TextView explore_textView=null;
	private ImageView imgMyFav=null;
	private LinearLayout layout_nodata;
	private ImageView noconnectionimage;
	private TextView noconnectiontext,txtDescWhattodo;
	private ListView listrecommended_whattodo;

	private ServiceMethod servicemethod;
	private CheckNetwork checkNetwork;
	private ShowMessages showMessage;
	private GetListOfClustersOnRecommendedByChildIDList getListOfClustersOnRecommendedByChildID;
	private WhatToDoRecommendedListAdapter adapter;

	private boolean flag_loading=false;
	


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
			view=inflater.inflate(R.layout.recommended_whattodo, container, false);
			setHasOptionsMenu(true);
			mListener.onFragmentAttached(true,"  What to do");
			initialize();//initialize all view items in fragment
			checkNetwork=new CheckNetwork();
			showMessage=new ShowMessages(getActivity());
			servicemethod=new ServiceMethod();
			getListOfClustersOnRecommendedByChildID=new GetListOfClustersOnRecommendedByChildIDList();
			getListOfClustersOnRecommendedByChildID.getListOfClustersOnRecommendedByChildID().clear();
			StaticVariables.webserviceName="GetListOfClustersOnRecommendedByChildID";

			new fetchListOfClustersOnRecommendedByChildID(1).execute();	

		}
		return view;		
	}

	private void initialize() 
	{
		// TODO Auto-generated method stub
		recommendations_textView=(TextView) view.findViewById(R.id.text_connectionheader);
		network_textView=(TextView) view.findViewById(R.id.text_requestheader);
		explore_textView=(TextView) view.findViewById(R.id.text_discoverheader);
		imgMyFav=(ImageView) view.findViewById(R.id.image_favwhattodo);
		layout_nodata=(LinearLayout) view.findViewById(R.id.layout_nodata);
		noconnectionimage=(ImageView) view.findViewById(R.id.noconnectionimage);
		noconnectiontext=(TextView) view.findViewById(R.id.noconnectiontext);
		listrecommended_whattodo=(ListView) view.findViewById(R.id.listrecommended_whattodo);
		txtDescWhattodo=(TextView) view.findViewById(R.id.txtDescWhattodo);


		imgMyFav.setVisibility(View.VISIBLE);
		txtDescWhattodo.setText("Nurture interests that exhilarate this child with these clusters of activities.");

		typeFace.setTypefaceRegular(recommendations_textView);
		typeFace.setTypefaceRegular(network_textView);
		typeFace.setTypefaceRegular(explore_textView);
		typeFace.setTypefaceRegular(noconnectiontext);
		typeFace.setTypefaceRegular(txtDescWhattodo);
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);



		listrecommended_whattodo.setOnScrollListener(new OnScrollListener() {



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


						if(totalItemCount% 8==0)
						{
							new fetchListOfClustersOnRecommendedByChildID((totalItemCount/8)+1).execute();	
						}


					}
				}
			}
		});


		listrecommended_whattodo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//here put friend id and open friend details fragment

				StaticVariables.ClusterId=getListOfClustersOnRecommendedByChildID.getListOfClustersOnRecommendedByChildID().get(position).getClusterID();

				StaticVariables.ClusterName="Recommended Activities";
				StaticVariables.fragmentIndexCurrentTabWhatToDo=302;
				
				switchingFragments(new ActivityListFragment());

			}
		});




		imgMyFav.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StaticVariables.fragmentIndexCurrentTabWhatToDo=303;
				StaticVariables.ClusterName="Recommended Activities";

				switchingFragments(new WishListFragment());

			}
		});



		recommendations_textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


			}
		});

		network_textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StaticVariables.fragmentIndexCurrentTabNetwork=301;
				switchingFragments(new WhatToDoNetworkFragment());
			}
		});

		explore_textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StaticVariables.fragmentIndexCurrentTabNetwork=301;
				switchingFragments(new WhatToDoExploreFragment());
				 

			}
		});


	}

	public static WhatToDoRecommendedFragment fr;

	public static WhatToDoRecommendedFragment getInstance()
	{
		if(fr==null)
		{
			fr = new WhatToDoRecommendedFragment();
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
			if(getListOfClustersOnRecommendedByChildID==null)
			{
				getListOfClustersOnRecommendedByChildID=new GetListOfClustersOnRecommendedByChildIDList();
			}
			getListOfClustersOnRecommendedByChildID.getListOfClustersOnRecommendedByChildID().clear();

			new fetchListOfClustersOnRecommendedByChildID(1).execute();	

		}


		return super.onOptionsItemSelected(item);
	}


	private ProgressDialog progressDialog=null;	

	private class fetchListOfClustersOnRecommendedByChildID extends AsyncTask<Void, Void, Integer>
	{
		int pageIndex=1;

		public fetchListOfClustersOnRecommendedByChildID(int i) 
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
				if(getListOfClustersOnRecommendedByChildID.getListOfClustersOnRecommendedByChildID().size()==0)
				{
					getListOfClustersOnRecommendedByChildID =servicemethod.getListOfClustersOnRecommendedByChildID(StaticVariables.currentChild.getChildID()/*37*/,pageIndex,8);
					flag_loading=false;
				}
				else
				{
					GetListOfClustersOnRecommendedByChildIDList list=servicemethod.getListOfClustersOnRecommendedByChildID(StaticVariables.currentChild.getChildID(),pageIndex,8);
					if(list!=null && list.getListOfClustersOnRecommendedByChildID().size()>0)
					{
						flag_loading=false;
						getListOfClustersOnRecommendedByChildID.getListOfClustersOnRecommendedByChildID().addAll(list.getListOfClustersOnRecommendedByChildID());
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
					new fetchListOfClustersOnRecommendedByChildID(1).execute();

			}
			else
			{
				if(getListOfClustersOnRecommendedByChildID!=null && getListOfClustersOnRecommendedByChildID.getListOfClustersOnRecommendedByChildID().size()>0)
				{
					layout_nodata.setVisibility(View.GONE);
					noconnectionimage.setVisibility(View.GONE);
					noconnectiontext.setVisibility(View.GONE);
					listrecommended_whattodo.setVisibility(View.VISIBLE);

					if(pageIndex==1)
					{
						adapter=new WhatToDoRecommendedListAdapter(getActivity(),getListOfClustersOnRecommendedByChildID);
						listrecommended_whattodo.setAdapter(adapter);
						adapter.notifyDataSetChanged();
					}
					else
					{
						adapter.notifyDataSetChanged();
						listrecommended_whattodo.invalidate();
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
	{

		layout_nodata.setVisibility(View.VISIBLE);
		listrecommended_whattodo.setVisibility(View.GONE);
		com.hatchtact.pinwi.classmodel.Error err = servicemethod.getError();	
		//showMessage.showAlert("Warning", err.getErrorDesc());
		noconnectionimage.setVisibility(View.VISIBLE);
		noconnectiontext.setVisibility(View.VISIBLE);
		noconnectiontext.setText(err.getErrorDesc());
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
		listrecommended_whattodo.setVisibility(View.GONE);
		//com.demo.pinwi.classmodel.Error err = servicemethod.getError();	
		//showMessage.showAlert("Warning", err.getErrorDesc());
		noconnectionimage.setVisibility(View.VISIBLE);
		noconnectiontext.setVisibility(View.VISIBLE);
		noconnectiontext.setText("You have no parent connection.");

	}

}
