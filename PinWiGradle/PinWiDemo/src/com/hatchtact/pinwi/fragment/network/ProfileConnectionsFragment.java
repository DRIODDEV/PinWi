package com.hatchtact.pinwi.fragment.network;

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
import android.widget.ListView;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.ProfileConnectionsListAdapter;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.GetListOfPinWiNetworksByLoggedID;
import com.hatchtact.pinwi.classmodel.GetListOfPinWiNetworksByLoggedIDList;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;


public class ProfileConnectionsFragment extends ParentFragment implements OnItemClickListener
{
	private View view;

	private ShowMessages showMessage=null;  
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;

	private GetListOfPinWiNetworksByLoggedIDList getListOfPinWiNetworksByLoggedIDList;

	private ProfileConnectionsListAdapter adapter=null;

	private ListView listView=null;
	private TextView childNameprofileconn=null;
	private TextView totalconnections=null;

	private boolean flag_loading=false;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view=inflater.inflate(R.layout.myprofileconnectionscreen, container, false);
		setHasOptionsMenu(true);
		mListener.onFragmentAttached(false,"  Network");
		StaticVariables.fragmentIndex=6;

		getListOfPinWiNetworksByLoggedIDList=new GetListOfPinWiNetworksByLoggedIDList();
		getListOfPinWiNetworksByLoggedIDList.getGetListOfPinWiNetworksByLoggedIDList().clear();
		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(getActivity());
		checkNetwork=new CheckNetwork();


		listView=(ListView) view.findViewById(R.id.listconnprofile);
		childNameprofileconn=(TextView) view.findViewById(R.id.childNameprofileconn);
		totalconnections=(TextView) view.findViewById(R.id.totalconnections);

		typeFace.setTypefaceRegular(childNameprofileconn);
		typeFace.setTypefaceLight(totalconnections);

		childNameprofileconn.setText(StaticVariables.NetworkExhilaratorChildNameProfileConnections);
		totalconnections.setText("No Network Connections");


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



		new getListOfNetworkConnectionsProfile(1).execute();

		return view;		
	}

	//private ProgressDialog progressDialog=null;

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
			if(customProgressLoader!=null)
			{
				customProgressLoader.showProgressBar();
			}
			/*progressDialog = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				if(getListOfPinWiNetworksByLoggedIDList.getGetListOfPinWiNetworksByLoggedIDList().size()==0)
				{
					getListOfPinWiNetworksByLoggedIDList =serviceMethod.getListOfPinWiNetworksByLoggedID(StaticVariables.currentParentId, Integer.parseInt(StaticVariables.NetworkChildIdProfileConnections), pageIndex, 8);
					flag_loading=false;
				}
				else
				{
					GetListOfPinWiNetworksByLoggedIDList list=getListOfPinWiNetworksByLoggedIDList =serviceMethod.getListOfPinWiNetworksByLoggedID(StaticVariables.currentParentId, Integer.parseInt(StaticVariables.NetworkChildIdProfileConnections), pageIndex, 8);
					if(list!=null && list.getGetListOfPinWiNetworksByLoggedIDList().size()>0)
					{
						flag_loading=false;
						getListOfPinWiNetworksByLoggedIDList.getGetListOfPinWiNetworksByLoggedIDList().addAll(list.getGetListOfPinWiNetworksByLoggedIDList());
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
				customProgressLoader.dismissProgressBar();
				/*if (progressDialog.isShowing())
					progressDialog.cancel();*/
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
				if(getListOfPinWiNetworksByLoggedIDList!=null && getListOfPinWiNetworksByLoggedIDList.getGetListOfPinWiNetworksByLoggedIDList().size()>0)
				{

					if(pageIndex==1)
					{
						adapter=new ProfileConnectionsListAdapter(getActivity(),getListOfPinWiNetworksByLoggedIDList);
						listView.setAdapter(adapter);
						listView.setOnItemClickListener(ProfileConnectionsFragment.this);
						adapter.notifyDataSetChanged();
					}
					else
					{
						adapter.notifyDataSetChanged();
						listView.invalidate();
					}
					totalconnections.setText(+getListOfPinWiNetworksByLoggedIDList.getGetListOfPinWiNetworksByLoggedIDList().size()+" Network Connections");

				}
				else
				{	
					//getError();
				}	
			}	
		}	
	}

	public static ProfileConnectionsFragment profileConnFrag;

	public static ProfileConnectionsFragment getInstance()
	{
		if(profileConnFrag==null)
		{
			profileConnFrag = new ProfileConnectionsFragment();
		}
		return profileConnFrag;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
	{

		GetListOfPinWiNetworksByLoggedID model=getListOfPinWiNetworksByLoggedIDList.getGetListOfPinWiNetworksByLoggedIDList().get(position);
		StaticVariables.NetworkChildId=model.getChildID();
		StaticVariables.NetworkExhilaratorChildName=model.getChildName();
		StaticVariables.fragmentIndexCurrentTabNetwork=211;
		switchingFragments(new ChildDetailFragment());
	}

	private void getError()
	{
		Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());
	}

	// the create options menu with a MenuInflater to have the menu from your fragment
	/*@Override
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
	
}
