package com.hatchtact.pinwi.fragment.network;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
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
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.NetworkDiscoverListAdapter;
import com.hatchtact.pinwi.adapter.NetworkSearchListAdapter;
import com.hatchtact.pinwi.classmodel.GetPeopleYouMayKnowListByLoggedIDList;
import com.hatchtact.pinwi.classmodel.SearchFriendListGloballyList;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.PopupHelper;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;

public  class NetworkDiscoverFragment extends ParentFragment 
{
	private View view;
	private SharePreferenceClass sharePref;
	private TextView connections_textView=null;
	private TextView request_textView=null;
	private TextView discover_textView=null;
	private ImageView imgMyProfile=null;
	private EditText editsearch;
	private ImageView imageSearch;
	private LinearLayout layout_nodata;
	private ImageView noconnectionimage;
	private TextView noconnectiontext;
	private ListView listconnections_network;
	private TextView inviteFriends;

	private ServiceMethod servicemethod;
	private CheckNetwork checkNetwork;
	private ShowMessages showMessage;
	private GetPeopleYouMayKnowListByLoggedIDList getPeopleYouMayKnowListByLoggedIDList;
	private SearchFriendListGloballyList searchFriendListGlobally;
	private NetworkDiscoverListAdapter adapter;
	private NetworkSearchListAdapter searchAdapter;

	private boolean flag_loading=false;
	private boolean isSearchList=false;
	private boolean isSearchDone=false;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//StaticVariables.fragmentIndexCurrentTabSchedular=202;
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
			view=inflater.inflate(R.layout.connections_network, container, false);
			setHasOptionsMenu(true);
			mListener.onFragmentAttached(true,"  Network");
			initialize();//initialize all view items in fragment
			checkNetwork=new CheckNetwork();
			showMessage=new ShowMessages(getActivity());
			servicemethod=new ServiceMethod();
			getPeopleYouMayKnowListByLoggedIDList=new GetPeopleYouMayKnowListByLoggedIDList();
			getPeopleYouMayKnowListByLoggedIDList.getPeopleYouMayKnowListByLoggedID().clear();
			searchFriendListGlobally=new SearchFriendListGloballyList();
			searchFriendListGlobally.getsearchFriendListGloballyList().clear();
			new fetchPendingRequestListByLoggedID(1).execute();	
			isSearchDone=false;

		}
		return view;		
	}

	private void initialize() 
	{
		// TODO Auto-generated method stub
		connections_textView=(TextView) view.findViewById(R.id.text_connectionheader);
		request_textView=(TextView) view.findViewById(R.id.text_requestheader);
		discover_textView=(TextView) view.findViewById(R.id.text_discoverheader);
		imgMyProfile=(ImageView) view.findViewById(R.id.image_profile_network);
		layout_nodata=(LinearLayout) view.findViewById(R.id.layout_nodata);
		noconnectionimage=(ImageView) view.findViewById(R.id.noconnectionimage);
		noconnectiontext=(TextView) view.findViewById(R.id.noconnectiontext);
		listconnections_network=(ListView) view.findViewById(R.id.listconnections_network);
		imageSearch=(ImageView) view.findViewById(R.id.imageSearch);
		editsearch=(EditText) view.findViewById(R.id.editSearch);
		editsearch.setHint("Search parents on PiNWi");
		inviteFriends=(TextView) view.findViewById(R.id.txtInviteFriends);
		inviteFriends.setVisibility(View.INVISIBLE);
		isSearchList=false;
		imgMyProfile.setVisibility(View.VISIBLE);
		imageSearch.setVisibility(View.VISIBLE);

		typeFace.setTypefaceRegular(connections_textView);
		typeFace.setTypefaceRegular(request_textView);
		typeFace.setTypefaceRegular(discover_textView);
		typeFace.setTypefaceRegular(editsearch);
		typeFace.setTypefaceRegular(noconnectiontext);
		typeFace.setTypefaceRegular(inviteFriends);

		connections_textView.setBackgroundResource(R.drawable.rectangular_btnrelation);
		connections_textView.setTextColor(getResources().getColor(R.color.btnrelationselection_color));

		request_textView.setBackgroundResource(R.drawable.rectangular_btnrelation);
		request_textView.setTextColor(getResources().getColor(R.color.btnrelationselection_color));

		discover_textView.setBackgroundResource(R.drawable.rectangular_btnrelation_selection);
		discover_textView.setTextColor(getResources().getColor(R.color.font_white_color));

		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


		listconnections_network.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//here put friend id and open friend details fragment
				 // showQuickActionMenu(position,view);
				if(!isSearchList)
				{
					StaticVariables.FriendId=getPeopleYouMayKnowListByLoggedIDList.getPeopleYouMayKnowListByLoggedID().get(position).getFriendID()+"";
				}
				else
				{
					StaticVariables.FriendId=searchFriendListGlobally.getsearchFriendListGloballyList().get(position).getParentID()+"";
				}
				StaticVariables.fragmentIndexCurrentTabNetwork=204;
				StaticVariables.ClusterName="Discover";
				switchingFragments(new FriendDetailFragment());

			}
		});
		


		listconnections_network.setOnScrollListener(new OnScrollListener() {



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
								new fetchPendingRequestListByLoggedID((totalItemCount/8)+1).execute();	
							}
						}
						else
						{
							if(totalItemCount% 8==0)
							{
								new searchFriendListGlobally((totalItemCount/8)+1).execute();
							}
						}
					}
				}
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
						if(searchFriendListGlobally!=null)
							searchFriendListGlobally.getsearchFriendListGloballyList().clear();
						else
						{
							searchFriendListGlobally=new SearchFriendListGloballyList();
						}
						isSearchDone=true;
						new searchFriendListGlobally(1).execute();

					}
					else
					{
						if(str.equalsIgnoreCase(""))
						{
							isSearchList=false;
							if(getPeopleYouMayKnowListByLoggedIDList!=null)
								getPeopleYouMayKnowListByLoggedIDList.getPeopleYouMayKnowListByLoggedID().clear();
							else
							{
								getPeopleYouMayKnowListByLoggedIDList=new GetPeopleYouMayKnowListByLoggedIDList();
							}
							isSearchDone=true;
							new fetchPendingRequestListByLoggedID(1).execute();	

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




		imgMyProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StaticVariables.fragmentIndexCurrentTabNetwork=207;
				StaticVariables.ClusterName="Discover";

				switchingFragments(new MyProfileScreenFragment());
			}
		});



		connections_textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StaticVariables.fragmentIndexCurrentTabNetwork=201;
				switchingFragments(new NetworkConnectionsFragment());

			}
		});

		request_textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StaticVariables.fragmentIndexCurrentTabNetwork=202;
				switchingFragments(new NetworkRequestFragment());
			}
		});

		discover_textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*StaticVariables.fragmentIndexCurrentTabSchedular=13;
				switchingFragments(new SubjectActivityByChildIDFragment());*/


			}
		});


	}

	public static NetworkConnectionsFragment fr;

	public static NetworkConnectionsFragment getInstance()
	{
		if(fr==null)
		{
			fr = new NetworkConnectionsFragment();
		}
		return fr;
	}




	// the create options menu with a MenuInflater to have the menu from your fragment
	/*@Override
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

		}


		return super.onOptionsItemSelected(item);
	}*/

	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		for(int i=0;i<menu.size();i++)
		{
			if(menu.getItem(i).getItemId()!=R.id.action_childName)
				menu.getItem(i).setVisible(false);
			else
			{
				menu.findItem(R.id.action_childName).setTitle(StaticVariables.currentParentName);
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

	private class fetchPendingRequestListByLoggedID extends AsyncTask<Void, Void, Integer>
	{
		int pageIndex=1;

		public fetchPendingRequestListByLoggedID(int i) 
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
				if(getPeopleYouMayKnowListByLoggedIDList.getPeopleYouMayKnowListByLoggedID().size()==0)
				{
					getPeopleYouMayKnowListByLoggedIDList =servicemethod.getPeopleYouMayKnowListByLoggedID(StaticVariables.currentParentId,pageIndex,8);
					flag_loading=false;
				}
				else
				{
					GetPeopleYouMayKnowListByLoggedIDList list=servicemethod.getPeopleYouMayKnowListByLoggedID(StaticVariables.currentParentId,pageIndex,8);
					if(list!=null && list.getPeopleYouMayKnowListByLoggedID().size()>0)
					{
						flag_loading=false;
						getPeopleYouMayKnowListByLoggedIDList.getPeopleYouMayKnowListByLoggedID().addAll(list.getPeopleYouMayKnowListByLoggedID());
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
					new fetchPendingRequestListByLoggedID(pageIndex).execute();

			}
			else
			{
				if(getPeopleYouMayKnowListByLoggedIDList!=null && getPeopleYouMayKnowListByLoggedIDList.getPeopleYouMayKnowListByLoggedID().size()>0)
				{
					layout_nodata.setVisibility(View.GONE);
					noconnectionimage.setVisibility(View.GONE);
					noconnectiontext.setVisibility(View.GONE);
					listconnections_network.setVisibility(View.VISIBLE);

					if(pageIndex==1)
					{
						adapter=new NetworkDiscoverListAdapter(getActivity(),getPeopleYouMayKnowListByLoggedIDList);
						listconnections_network.setAdapter(adapter);
						adapter.notifyDataSetChanged();
					}
					else
					{
						adapter.notifyDataSetChanged();
						listconnections_network.invalidate();
					}

					inviteFriends.setVisibility(View.INVISIBLE);
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
		listconnections_network.setVisibility(View.GONE);
		com.hatchtact.pinwi.classmodel.Error err = servicemethod.getError();	
		//showMessage.showAlert("Warning", err.getErrorDesc());
		noconnectionimage.setVisibility(View.VISIBLE);
		noconnectiontext.setVisibility(View.VISIBLE);
		noconnectiontext.setText(err.getErrorDesc());
	}


	private ProgressDialog progressDialogSearch=null;
	private PopupWindow window;	

	private class searchFriendListGlobally extends AsyncTask<Void, Void, Integer>
	{
		int pageIndexSearch=1;

		public searchFriendListGlobally(int i) {
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

				if(searchFriendListGlobally.getsearchFriendListGloballyList().size()==0)
				{
					searchFriendListGlobally =servicemethod.getsearchFriendListGloballyList(editsearch.getText().toString().trim(), StaticVariables.currentParentId, pageIndexSearch, 8);
					flag_loading=false;
				}
				else
				{
					SearchFriendListGloballyList list=servicemethod.getsearchFriendListGloballyList(editsearch.getText().toString().trim(), StaticVariables.currentParentId, pageIndexSearch, 8);
					if(list!=null && list.getsearchFriendListGloballyList().size()>0)
					{
						flag_loading=false;
						searchFriendListGlobally.getsearchFriendListGloballyList().addAll(list.getsearchFriendListGloballyList());
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
					new searchFriendListGlobally(pageIndexSearch).execute();

			}
			else
			{
				if(searchFriendListGlobally!=null && searchFriendListGlobally.getsearchFriendListGloballyList().size()>0)
				{
					layout_nodata.setVisibility(View.GONE);
					noconnectionimage.setVisibility(View.GONE);
					noconnectiontext.setVisibility(View.GONE);
					listconnections_network.setVisibility(View.VISIBLE);

					if(pageIndexSearch==1)
					{
						searchAdapter=new NetworkSearchListAdapter(getActivity(),searchFriendListGlobally);
						listconnections_network.setAdapter(searchAdapter);
						searchAdapter.notifyDataSetChanged();
					}
					else
					{
						searchAdapter.notifyDataSetChanged();

						listconnections_network.invalidate();
					}

					inviteFriends.setVisibility(View.INVISIBLE);


				}
				else
				{


					getError();
				}	
				isSearchDone=false;
			}	
		}	
	}

	


	private void showQuickActionMenu(int pos, View v){
	    LayoutInflater inflater = 
	            (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	    //This is just a view with buttons that act as a menu.  
	    View popupView = inflater.inflate(R.layout.dialog_menu, null);
	    popupView.findViewById(R.id.item_dropbox).setTag(pos);
	    popupView.findViewById(R.id.item_save).setTag(pos);
	   /* popupView.findViewById(R.id.menu_add_note).setTag(pos);
	    popupView.findViewById(R.id.menu_add_attachment).setTag(pos);*/

	    window = PopupHelper.newBasicPopupWindow(getActivity());
	    window.setContentView(popupView);
	    int totalHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
	    int[] location = new int[2];
	    v.getLocationOnScreen(location);

	    if (location[1] < (totalHeight / 2.0)) {
	        PopupHelper.showLikeQuickAction(window, popupView, v
	                , getActivity().getWindowManager(),0,0,PopupHelper.UPPER_HALF);
	    } else {
	        PopupHelper.showLikeQuickAction(window, popupView, v
	                ,getActivity(). getWindowManager(),0, 0,PopupHelper.LOWER_HALF);
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

}
