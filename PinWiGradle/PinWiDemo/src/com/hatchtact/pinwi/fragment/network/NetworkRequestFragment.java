package com.hatchtact.pinwi.fragment.network;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
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
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.NetworkConnectionsListAdapter;
import com.hatchtact.pinwi.adapter.NetworkRequestListAdapter;
import com.hatchtact.pinwi.adapter.NetworkSearchListAdapter;
import com.hatchtact.pinwi.classmodel.GetFriendsListByLoggedID;
import com.hatchtact.pinwi.classmodel.GetFriendsListByLoggedIDList;
import com.hatchtact.pinwi.classmodel.GetListOfPendingRequestsByLoggedID;
import com.hatchtact.pinwi.classmodel.GetListOfPendingRequestsByLoggedIDList;
import com.hatchtact.pinwi.classmodel.SearchFriendListGloballyList;
import com.hatchtact.pinwi.database.DataSource;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.AppUtils;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;

import java.util.ArrayList;

public  class NetworkRequestFragment extends ParentFragment
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
	private GetListOfPendingRequestsByLoggedIDList getListOfPendingRequestsByLoggedID;
	private SearchFriendListGloballyList searchFriendListGlobally;
	private NetworkRequestListAdapter adapter;
	private NetworkSearchListAdapter searchAdapter;

	private boolean flag_loading=false;
	private boolean isSearchList=false;
	private boolean isSearchDone=false;
	/**For database*/
	private DataSource source;//initializing  database
	private final int BACKGROUNDSYNCFLAG=1;//background syncing
	private final int PROGRESSSYNCFLAG=2;//progress syncing
	private AppUtils appUtils;
	/**For database*/

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
			appUtils=new AppUtils(getActivity());
			try {
				checkIfDataExistInDatabase();//checking if database contains value for access profile data
			}
			catch (Exception e)
			{

			}

			//new fetchPendingRequestListByLoggedID(1).execute();
			isSearchDone=false;
		}
		return view;
	}
	private void checkIfDataExistInDatabase() {
		source.open();
		//GetListOfPendingRequestsByLoggedIDList
		ArrayList<GetListOfPendingRequestsByLoggedID> listRequest=source.getNetworkModuleRequestList();
		if(getListOfPendingRequestsByLoggedID==null)
		{
			getListOfPendingRequestsByLoggedID=new GetListOfPendingRequestsByLoggedIDList();
		}
		if(listRequest!=null &&listRequest.size()>0)
		{
			getListOfPendingRequestsByLoggedID.setGetInterestTraitsByChildIDOnInsight(listRequest);
			layout_nodata.setVisibility(View.GONE);
			noconnectionimage.setVisibility(View.GONE);
			noconnectiontext.setVisibility(View.GONE);
			listconnections_network.setVisibility(View.VISIBLE);
			inviteFriends.setVisibility(View.INVISIBLE);
			setNetworkRequestData(1,PROGRESSSYNCFLAG);
			new fetchPendingRequestListByLoggedID(1,BACKGROUNDSYNCFLAG).execute();
			//new NetworkConnectionsFragment.fetchFriendsListByLoggedID(1,BACKGROUNDSYNCFLAG).execute();
		}
		else
		{
			new fetchPendingRequestListByLoggedID(1,PROGRESSSYNCFLAG).execute();
			//new NetworkConnectionsFragment.fetchFriendsListByLoggedID(1,PROGRESSSYNCFLAG).execute();
		}
		source.close();
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

		request_textView.setBackgroundResource(R.drawable.rectangular_btnrelation_selection);
		request_textView.setTextColor(getResources().getColor(R.color.font_white_color));

		discover_textView.setBackgroundResource(R.drawable.rectangular_btnrelation);
		discover_textView.setTextColor(getResources().getColor(R.color.btnrelationselection_color));

		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);



		listconnections_network.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub
				//here put friend id and open friend details fragment
				if(!isSearchList)
				{
					StaticVariables.FriendId=getListOfPendingRequestsByLoggedID.getListOfPendingRequestsByLoggedID().get(position).getFriendID();
				}
				else
				{
					StaticVariables.FriendId=searchFriendListGlobally.getsearchFriendListGloballyList().get(position).getParentID()+"";
				}
				StaticVariables.fragmentIndexCurrentTabNetwork=204;
				StaticVariables.ClusterName="Request";

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
								new fetchPendingRequestListByLoggedID((totalItemCount/8)+1,PROGRESSSYNCFLAG).execute();
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
							if(getListOfPendingRequestsByLoggedID!=null)
							{
								getListOfPendingRequestsByLoggedID.getListOfPendingRequestsByLoggedID().clear();
							}
							else
							{
								getListOfPendingRequestsByLoggedID=new GetListOfPendingRequestsByLoggedIDList();
							}
							isSearchDone=true;
							new fetchPendingRequestListByLoggedID(1,PROGRESSSYNCFLAG).execute();
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
				StaticVariables.ClusterName="Request";
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

			}
		});

		discover_textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StaticVariables.fragmentIndexCurrentTabNetwork=203;
				switchingFragments(new NetworkDiscoverFragment());


			}
		});

		checkNetwork=new CheckNetwork();
		showMessage=new ShowMessages(getActivity());
		servicemethod=new ServiceMethod();
		getListOfPendingRequestsByLoggedID=new GetListOfPendingRequestsByLoggedIDList();
		getListOfPendingRequestsByLoggedID.getListOfPendingRequestsByLoggedID().clear();
		searchFriendListGlobally=new SearchFriendListGloballyList();
		searchFriendListGlobally.getsearchFriendListGloballyList().clear();
		source = new DataSource(getActivity());

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

	//private ProgressDialog progressDialog=null;

	private class fetchPendingRequestListByLoggedID extends AsyncTask<Void, Void, Integer>
	{

		int pageIndex=1;
		int flagConnections=0;

		public fetchPendingRequestListByLoggedID(int i,int flag) {
			// TODO Auto-generated constructor stub
			pageIndex=i;
			flagConnections=flag;

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(flagConnections!=BACKGROUNDSYNCFLAG) {
				if (customProgressLoader != null) {
					customProgressLoader.showProgressBar();
				}
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
				if(checkNetwork.checkNetworkConnection(getActivity()))
				{

					if (getListOfPendingRequestsByLoggedID.getListOfPendingRequestsByLoggedID().size()==0 || flagConnections == BACKGROUNDSYNCFLAG)
					{
						source.open();
						getListOfPendingRequestsByLoggedID =servicemethod.getPendingRequestsListByLoggedID(StaticVariables.currentParentId,pageIndex,8);
						flag_loading = false;
						if (getListOfPendingRequestsByLoggedID != null && getListOfPendingRequestsByLoggedID.getListOfPendingRequestsByLoggedID().size() > 0) {
							if (flagConnections == BACKGROUNDSYNCFLAG)
								source.deleteNetworkModuleRequestData();

							for (int i = 0; i < getListOfPendingRequestsByLoggedID.getListOfPendingRequestsByLoggedID().size(); i++)
							{
								GetListOfPendingRequestsByLoggedID model=getListOfPendingRequestsByLoggedID.getListOfPendingRequestsByLoggedID().get(i);
								byte[] imageByte= Base64.decode(model.getProfileImage(), 0);

								if(imageByte!=null)
								{
									try {
										String imagePath= AppUtils.PATHNETWORKIMAGES+model.getFriendID()+".jpeg"/*+"_"+System.currentTimeMillis()+""*/;
										getListOfPendingRequestsByLoggedID.getListOfPendingRequestsByLoggedID().get(i).setProfileImage(appUtils.bitmapStoreInSDCard(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length),imagePath));
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								source.addNetworkModuleRequestData(getListOfPendingRequestsByLoggedID.getListOfPendingRequestsByLoggedID().get(i));
							}
						}

						source.close();
					}

				/*if(getListOfPendingRequestsByLoggedID.getListOfPendingRequestsByLoggedID().size()==0)
				{
					getListOfPendingRequestsByLoggedID =servicemethod.getPendingRequestsListByLoggedID(StaticVariables.currentParentId,pageIndex,8);
					flag_loading=false;
				}*/
					else
					{
						GetListOfPendingRequestsByLoggedIDList list=servicemethod.getPendingRequestsListByLoggedID(StaticVariables.currentParentId,pageIndex,8);
						if(list!=null && list.getListOfPendingRequestsByLoggedID().size()>0)
						{
							flag_loading=false;
							getListOfPendingRequestsByLoggedID.getListOfPendingRequestsByLoggedID().addAll(list.getListOfPendingRequestsByLoggedID());
						}


					}
				}
				else
				{
					ErrorCode=-1;
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
				hideKeyBoard();
				if(flagConnections!=BACKGROUNDSYNCFLAG)
					customProgressLoader.dismissProgressBar();
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
						new fetchPendingRequestListByLoggedID(pageIndex, flagConnections).execute();

				} else {
					if (getListOfPendingRequestsByLoggedID != null && getListOfPendingRequestsByLoggedID.getListOfPendingRequestsByLoggedID().size() > 0) {
						layout_nodata.setVisibility(View.GONE);
						noconnectionimage.setVisibility(View.GONE);
						noconnectiontext.setVisibility(View.GONE);
						listconnections_network.setVisibility(View.VISIBLE);
						setNetworkRequestData(pageIndex, flagConnections);
						inviteFriends.setVisibility(View.INVISIBLE);
					} else {


						getError();
					}
					isSearchDone = false;
				}
			}
			catch (Exception e)
			{

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

	private void setNetworkRequestData(int pageIndex, int flag) {

		if(pageIndex==1)
		{
			if(flag==PROGRESSSYNCFLAG)
			{
				adapter=new NetworkRequestListAdapter(getActivity(),getListOfPendingRequestsByLoggedID,NetworkRequestFragment.this);
				listconnections_network.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
			else
			{
				if(getListOfPendingRequestsByLoggedID.getListOfPendingRequestsByLoggedID().size()<=8)
				{
					adapter=new NetworkRequestListAdapter(getActivity(),getListOfPendingRequestsByLoggedID,NetworkRequestFragment.this);
					listconnections_network.setAdapter(adapter);
					adapter.notifyDataSetChanged();
				}
				else
				{
					adapter.notifyDataSetChanged();
					listconnections_network.invalidate();
				}
			}
		}
		else
		{
			adapter.notifyDataSetChanged();
			listconnections_network.invalidate();
		}

	}
	//private ProgressDialog progressDialogSearch=null;

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
			if(customProgressLoader!=null)
			{
				customProgressLoader.showProgressBar();
			}
			/*progressDialogSearch = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialogSearch.setCancelable(false);*/
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
				customProgressLoader.dismissProgressBar();
				/*if (progressDialogSearch.isShowing())
					progressDialogSearch.cancel();*/
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
		listconnections_network.setVisibility(View.GONE);
		//com.demo.pinwi.classmodel.Error err = servicemethod.getError();	
		//showMessage.showAlert("Warning", err.getErrorDesc());
		noconnectionimage.setVisibility(View.VISIBLE);
		noconnectiontext.setVisibility(View.VISIBLE);
		noconnectiontext.setText("You have no new request.");


	}

}
