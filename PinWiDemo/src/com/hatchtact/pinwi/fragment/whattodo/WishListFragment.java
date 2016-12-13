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
import com.hatchtact.pinwi.adapter.WhatToDoSearchWishListAdapter;
import com.hatchtact.pinwi.adapter.WhatToDoWishListAdapter;
import com.hatchtact.pinwi.classmodel.GetWishListsByChildIDList;
import com.hatchtact.pinwi.classmodel.SearchWishListByChildIDList;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;

public  class WishListFragment extends ParentFragment 
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
	private GetWishListsByChildIDList getWishListsByChildIDList;
	private SearchWishListByChildIDList searchWishListByChildIDList;
	private WhatToDoWishListAdapter adapter;
	private WhatToDoSearchWishListAdapter searchAdapter;

	private boolean flag_loading=false;
	private boolean isSearchList=false;
	private boolean isSearchDone=false;
	private CustomDialogClass customDialog;


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
			customDialog=new CustomDialogClass(WishListFragment.this);

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

			getWishListsByChildIDList=new GetWishListsByChildIDList();
			getWishListsByChildIDList.getWishListsByChildIDList().clear();
			searchWishListByChildIDList=new SearchWishListByChildIDList();
			searchWishListByChildIDList.getsearchWishListByChildIDList().clear();
			new fetchWishList(1).execute();	
			isSearchDone=false;


		}
		return view;		
	}

	private void initialize() 
	{

		// TODO Auto-generated method stub
		clusterName=(TextView) view.findViewById(R.id.clusterName);
		clusterName.setText("Wishlist");
		totallanguages=(TextView) view.findViewById(R.id.totallanguages);
		totallanguages.setVisibility(View.GONE);
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
		
		if(StaticVariables.ClusterName.startsWith("Recommended"))
		{
			
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

		}

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
								new fetchWishList((totalItemCount/8)+1).execute();	
							}
						}
						else
						{
							if(totalItemCount% 8==0)
							{
								new searchWishListGlobally((totalItemCount/8)+1).execute();
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
					StaticVariables.WhatToDoActivityName=getWishListsByChildIDList.getWishListsByChildIDList().get(position).getName();
					StaticVariables.WhatToDoActivityId=getWishListsByChildIDList.getWishListsByChildIDList().get(position).getActivityID();
					customDialog.isActivityScheduled(getWishListsByChildIDList.getWishListsByChildIDList().get(position).getIsScheduled());

				}
				else
				{
					StaticVariables.WhatToDoActivityName=searchWishListByChildIDList.getsearchWishListByChildIDList().get(position).getActivityName();
					StaticVariables.WhatToDoActivityId=searchWishListByChildIDList.getsearchWishListByChildIDList().get(position).getActivityID();
					customDialog.isActivityScheduled(searchWishListByChildIDList.getsearchWishListByChildIDList().get(position).getIsScheduled());
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
						if(searchWishListByChildIDList!=null)
						{
							searchWishListByChildIDList.getsearchWishListByChildIDList().clear();
						}
						else
						{
							searchWishListByChildIDList=new SearchWishListByChildIDList();
						}
						isSearchDone=true;
						new searchWishListGlobally(1).execute();

					}
					else
					{

						if(str.equalsIgnoreCase(""))
						{
							isSearchList=false;
							if(getWishListsByChildIDList!=null)
								getWishListsByChildIDList.getWishListsByChildIDList().clear();
							else
							{
								getWishListsByChildIDList=new GetWishListsByChildIDList();
							}
							isSearchDone=true;
							new fetchWishList(1).execute();	
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

	public static WishListFragment fr;

	public static WishListFragment getInstance()
	{
		if(fr==null)
		{
			fr = new WishListFragment();
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
			if(getWishListsByChildIDList==null)
			{
				getWishListsByChildIDList=new GetWishListsByChildIDList();
			}
			getWishListsByChildIDList.getWishListsByChildIDList().clear();

			if(searchWishListByChildIDList==null)
			{
				searchWishListByChildIDList=new SearchWishListByChildIDList();
			}
			searchWishListByChildIDList.getsearchWishListByChildIDList().clear();
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

			new fetchWishList(1).execute();	

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

	private class fetchWishList extends AsyncTask<Void, Void, Integer>
	{
		int pageIndex=1;

		public fetchWishList(int i) 
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
				if(getWishListsByChildIDList.getWishListsByChildIDList().size()==0)
				{
					getWishListsByChildIDList =servicemethod.getWishlist(StaticVariables.currentChild.getChildID()/*37*/,pageIndex,8);
					flag_loading=false;
				}
				else
				{
					GetWishListsByChildIDList list=servicemethod.getWishlist(StaticVariables.currentChild.getChildID()/*37*/,pageIndex,8);
					if(list!=null && list.getWishListsByChildIDList().size()>0)
					{
						flag_loading=false;
						getWishListsByChildIDList.getWishListsByChildIDList().addAll(list.getWishListsByChildIDList());
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
					new fetchWishList(1).execute();

			}
			else
			{
				if(getWishListsByChildIDList!=null && getWishListsByChildIDList.getWishListsByChildIDList().size()>0)
				{
					layout_nodata.setVisibility(View.GONE);
					noconnectionimage.setVisibility(View.GONE);
					noconnectiontext.setVisibility(View.GONE);
					listconnprofile.setVisibility(View.VISIBLE);
					//totallanguages.setText("Languages - "+getListOfActivitiesOnRecommendedByClusterIDList.getListOfActivitiesOnRecommendedByClusterID().get(0).getActivityCount());


					if(pageIndex==1)
					{
						adapter=new WhatToDoWishListAdapter(getActivity(),getWishListsByChildIDList,WishListFragment.this);
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

	private class searchWishListGlobally extends AsyncTask<Void, Void, Integer>
	{

		int pageIndexSearch=1;
		public searchWishListGlobally(int i) {
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
				if(searchWishListByChildIDList.getsearchWishListByChildIDList().size()==0)
				{
					searchWishListByChildIDList =servicemethod.searchWishList(StaticVariables.currentChild.getChildID()/*37*/, StaticVariables.ClusterId, editsearch.getText().toString().trim(), pageIndexSearch,8);
					flag_loading=false;
				}
				else
				{
					SearchWishListByChildIDList list=servicemethod.searchWishList(StaticVariables.currentChild.getChildID()/*37*/, StaticVariables.ClusterId, editsearch.getText().toString().trim(), pageIndexSearch,8);
					if(list!=null && list.getsearchWishListByChildIDList().size()>0)
					{
						flag_loading=false;
						searchWishListByChildIDList.getsearchWishListByChildIDList().addAll(list.getsearchWishListByChildIDList());
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
					new searchWishListGlobally(1).execute();

			}
			else
			{
				if(searchWishListByChildIDList!=null && searchWishListByChildIDList.getsearchWishListByChildIDList().size()>0)
				{
					layout_nodata.setVisibility(View.GONE);
					noconnectionimage.setVisibility(View.GONE);
					noconnectiontext.setVisibility(View.GONE);
					listconnprofile.setVisibility(View.VISIBLE);


					if(pageIndexSearch==1)
					{
						searchAdapter=new WhatToDoSearchWishListAdapter(getActivity(),searchWishListByChildIDList,WishListFragment.this);
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
