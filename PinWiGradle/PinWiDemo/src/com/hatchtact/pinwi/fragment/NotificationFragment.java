package com.hatchtact.pinwi.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.hatchtact.pinwi.NotificationDetailActivity;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.GetNotificationListByParentIDList;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;

public class NotificationFragment extends ParentFragment implements OnItemClickListener
{
	private View view;
	private static NotificationFragment notificationFragment;
	private ListView listView=null;
	public static GetNotificationListByParentIDList getNotificationListByParentIDList=new GetNotificationListByParentIDList();
	private ShowMessages showMessage=null;
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;

	private NotificationFragmentOneAdapter adapter;
	private TextView textNotificactionmsg;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		if(savedInstanceState==null)
		{
			view=inflater.inflate(R.layout.notification_tab_listview, container, false);
			mListener.onFragmentAttached(true,"  Notifications");
			setHasOptionsMenu(true);
			serviceMethod=new ServiceMethod();
			showMessage=new ShowMessages(getActivity());
			checkNetwork=new CheckNetwork();

			listView=(ListView) view.findViewById(R.id.list_notificationScreen);
			textNotificactionmsg=(TextView) view.findViewById(R.id.textNotificactionmsg);

			StaticVariables.notificationCountInvisible=true;


			if(getNotificationListByParentIDList==null)
			{
				getNotificationListByParentIDList=new GetNotificationListByParentIDList();
			}


			refreshDataAccordingToParent();


			listView.setHorizontalScrollBarEnabled(false);

			listView.setOnItemClickListener(NotificationFragment.this);
		}

		return view;
	}



	private void refreshDataAccordingToParent()

	{
		// TODO Auto-generated method stub



		//getNotificationListByParentIDList.getGetNotificationListByParentID().clear();
		

		/*...................NotificationListByParentID........................*/

		try {
			new AsyncNotificationListByParentID().execute();
		}
		catch (Exception e)
		{

		}

		/*...................NotificationListByParentID.........................*/


	}



	public static NotificationFragment getInstance()
	{
		if(notificationFragment==null)
		{
			notificationFragment = new NotificationFragment();
		}
		return notificationFragment;
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
				menu.findItem(R.id.action_childName).setTitle(StaticVariables.currentParentName);//Here we have to set parent name
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



	//private ProgressDialog progressDialogNotification=null;

	private class AsyncNotificationListByParentID extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(customProgressLoader!=null)
				customProgressLoader.startHandler();
				/*progressDialogNotification = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
				progressDialogNotification.setCancelable(false);*/
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			try {
				if (checkNetwork.checkNetworkConnection(getActivity())) {
					if (getNotificationListByParentIDList.getGetNotificationListByParentID() != null) {
						if (getNotificationListByParentIDList.getGetNotificationListByParentID().size() == 0) {
							getNotificationListByParentIDList.getGetNotificationListByParentID().clear();

							getNotificationListByParentIDList = serviceMethod.getNotificationListByParentIDList(StaticVariables.currentParentId);
						}
					}
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
					/*if (progressDialogNotification.isShowing())
						progressDialogNotification.cancel();*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				if (result == -1) {
					showMessage.showToastMessage("Please check your network connection");
					if (checkNetwork.checkNetworkConnection(getActivity()))
						new AsyncNotificationListByParentID().execute();

				} else {
					if (getNotificationListByParentIDList != null && getNotificationListByParentIDList.getGetNotificationListByParentID().size() > 0) {
						setNotificationListData();
					} else {
						textNotificactionmsg.setVisibility(View.VISIBLE);
						listView.setVisibility(View.GONE);
						//getError();
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
		com.hatchtact.pinwi.classmodel.Error err = serviceMethod.getError();
		showMessage.showAlert("Warning", err.getErrorDesc());
	}



	public void setNotificationListData() {
		// TODO Auto-generated method stub
		adapter=new NotificationFragmentOneAdapter(getActivity());
		listView.setAdapter(adapter);

	}



	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
		// TODO Auto-generated method stub


		// TODO Auto-generated method stub
		StaticVariables.positionNotificationSelected=position;
		//StaticVariables.fragmentIndexCurrentTabNotification=102;
		getNotificationListByParentIDList.getGetNotificationListByParentID().get(position).setRead(1);
		adapter.notifyDataSetChanged();
		listView.invalidate();
		Intent notificationDetail=new Intent(getActivity(), NotificationDetailActivity.class);
		startActivity(notificationDetail);
		//switchingFragments(new NotificationFragmentTwo());



	}
}
