package com.hatchtact.pinwi.fragment;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

public class NotificationScreenFragment extends Fragment
{

	private View view;
	public static NotificationScreenFragment notificationScreenFragment;
	private ArrayList<Switch> switchArray=new ArrayList<Switch>();
	private SharePreferenceClass sharePreferenceMananger;

	private  final int[] info_ids_toggle= {
			R.id.toggleButton1,
			R.id.toggleButton2,
			R.id.toggleButton3,
			R.id.toggleButton4,
	};

	private TypeFace typeface;
	private TextView notification_text1;

	private  final int[] info_ids={R.id.notification_text2,R.id.notification_text3,R.id.notification_text4,R.id.notification_text5};


	private ShowMessages showMessage=null;  
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;
	private String string1,string2,string3,string4;
	private String status1,status2,status3,status4;
	private Button btndone;
	private boolean isNotification1,isNotification2,isNotification3,isNotification4=false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sharePreferenceMananger=new SharePreferenceClass(getActivity());
		typeface=new TypeFace(getActivity());
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{



		view=inflater.inflate(R.layout.notification_screen_settings, container, false);
		//mListener.onFragmentAttached(false,"  Settings");
		setHasOptionsMenu(true);
		switchArray.clear();
		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(getActivity());
		checkNetwork=new CheckNetwork();
		notification_text1=(TextView) view.findViewById(R.id.notification_text1);
		typeface.setTypefaceRegular(notification_text1);

		for(int i=0;i<info_ids.length;i++)
		{
			setTextViewId(info_ids[i],i);
		}

		for(int i=0;i<info_ids_toggle.length;i++)
		{
			setToggleViewId(info_ids_toggle[i]);
			typeface.setTypefaceRegular(switchArray.get(i));



			switchArray.get(i).setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {


					switch (buttonView.getId()) {
					case R.id.toggleButton1:
						isNotification1=isChecked;
						//sharePreferenceMananger.setActivitySetting_Notification(isChecked);
						status1="1";
						if(isChecked)
						{
							string1="1";
						}
						else
						{

							string1="0";

						}
						break;

					case R.id.toggleButton2:
						isNotification2=isChecked;
						//sharePreferenceMananger.setReportSetting_Notification(isChecked);
						status2="2";
						if(isChecked)
						{
							string2="1";
						}
						else
						{
							string2="0";							
						}
						break;
					case R.id.toggleButton3:
						isNotification3=isChecked;
						//sharePreferenceMananger.setNotification3Setting_Notification(isChecked);
						status3="3";
						if(isChecked)
						{
							string3="1";
						}
						else
						{


							string3="0";


						}
						break;
					case R.id.toggleButton4:
						isNotification4=isChecked;
						//sharePreferenceMananger.setNotification4Setting_Notification(isChecked);
						status4="4";

						if(isChecked)
						{
							string4="1";
						}
						else
						{


							string4="0";


						}
						break;


					}


				}
			});
		}

		switchArray.get(0).setChecked(sharePreferenceMananger.getActivitySetting_Notification());
		switchArray.get(1).setChecked(sharePreferenceMananger.getReportSetting_Notification());
		switchArray.get(2).setChecked(sharePreferenceMananger.getNotification3Setting_Notification());
		switchArray.get(3).setChecked(sharePreferenceMananger.getNotification4Setting_Notificationn());
		
		
			status1="1";
			if(sharePreferenceMananger.getActivitySetting_Notification())
			{
				string1="1";
			}
			else
			{

				string1="0";

			}
	
			status2="2";
			if(sharePreferenceMananger.getReportSetting_Notification())
			{
				string2="1";
			}
			else
			{
				string2="0";							
			}
			
			status3="3";
			if(sharePreferenceMananger.getNotification3Setting_Notification())
			{
				string3="1";
			}
			else
			{


				string3="0";


			}
			
			status4="4";

			if(sharePreferenceMananger.getNotification4Setting_Notificationn())
			{
				string4="1";
			}
			else
			{


				string4="0";


			}
	

		btndone=(Button) view.findViewById(R.id.buttonDoneNotificationSettting);
		btndone.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new addNotificationSettingAsync().execute();
			}
		});



		return view;

	}


	private void setFlags()
	{

		sharePreferenceMananger.setActivitySetting_Notification(isNotification1);

		sharePreferenceMananger.setReportSetting_Notification(isNotification2);

		sharePreferenceMananger.setNotification3Setting_Notification(isNotification3);

		sharePreferenceMananger.setNotification4Setting_Notification(isNotification4);




	}

	private void setToggleViewId(int id)
	{
		switchArray.add((Switch) view.findViewById(id));

	}

	public static NotificationScreenFragment getInstance()
	{
		if(notificationScreenFragment==null)
		{
			notificationScreenFragment = new NotificationScreenFragment();
		}
		return notificationScreenFragment;
	}


	// the create options menu with a MenuInflater to have the menu from your fragment
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		for(int i=0;i<menu.size();i++)
		{
			menu.getItem(i).setVisible(false);

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




	private void setTextViewId(int id,int i)
	{

		typeface.setTypefaceRegular((TextView) view.findViewById(id));

	}


	private void setNotificationType(String notificationtype) {
		if(status1!=null)
		{
			if(notificationtype!="")
			{
				status1="," + status1;
			}

		}
		if(status2!=null)
		{

			if(notificationtype!="")
			{
				status2="," + status2;
			}


		}
		if(status3!=null)
		{

			if(notificationtype!="")
			{
				status3="," + status3;
			}


		}
		if(status4!=null)
		{

			if(notificationtype!="")
			{
				status4="," + status4;
			}


		}
	}


	private void setStatus(String statusNotificationSetting) {
		if(string1!=null)
		{
			if(statusNotificationSetting!="")
			{
				string1="," + string1;
			}

		}
		if(string2!=null)
		{

			if(statusNotificationSetting!="")
			{
				string2="," + string2;
			}


		}
		if(string3!=null)
		{

			if(statusNotificationSetting!="")
			{
				string3="," + string3;
			}


		}
		if(string4!=null)
		{

			if(statusNotificationSetting!="")
			{
				string4="," + string4;
			}


		}
	}


	private ProgressDialog progressDialog;

	public class addNotificationSettingAsync extends AsyncTask<Void, Void, Integer>
	{


		public addNotificationSettingAsync()
		{



		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		String status;
		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				String notificationtype="";
				String statusNotificationSetting="";
				setNotificationType(notificationtype);
				setStatus(statusNotificationSetting);

				notificationtype=status1 + status2 + status3 + status4;
				statusNotificationSetting=string1 + string2 + string3 + string4;

				status=serviceMethod.addNotificationSettingsByProfileID(StaticVariables.currentParentId,notificationtype,statusNotificationSetting);


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
					new addNotificationSettingAsync().execute();

			}
			else
			{
				if(status.equalsIgnoreCase("0"))
				{
					setFlags();
					Toast.makeText(getActivity(), "Added Successfully.", Toast.LENGTH_LONG).show();

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


}
