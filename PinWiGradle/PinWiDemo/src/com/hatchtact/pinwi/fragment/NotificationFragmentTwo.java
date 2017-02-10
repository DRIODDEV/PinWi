package com.hatchtact.pinwi.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.utility.StaticVariables;

public class NotificationFragmentTwo extends ParentFragment 
{
	private View view;
	private static NotificationFragmentTwo notificationFragmentTwo;
	
	private ImageView imageNotificationNewFragment2;
	private TextView idNotificationTimeFragment2;
	private TextView idNotificationDescFragment2;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		if(savedInstanceState==null)
		{
		view=inflater.inflate(R.layout.notification_tab_two, container, false);
		mListener.onFragmentAttached(false,"  Notifications");
		setHasOptionsMenu(true);
		imageNotificationNewFragment2=(ImageView) view.findViewById(R.id.imageNotificationNewFragment2);
		idNotificationTimeFragment2=(TextView) view.findViewById(R.id.idNotificationTimeFragment2);
		idNotificationDescFragment2=(TextView) view.findViewById(R.id.idNotificationDescFragment2);
		
		
		if(NotificationFragment.getNotificationListByParentIDList.getGetNotificationListByParentID().get(StaticVariables.positionNotificationSelected).getNotificationID()==1)

		{
			imageNotificationNewFragment2.setImageResource(R.drawable.profile_i);
		}
		
		else if(NotificationFragment.getNotificationListByParentIDList.getGetNotificationListByParentID().get(StaticVariables.positionNotificationSelected).getNotificationID()==2)

		{
			imageNotificationNewFragment2.setImageResource(R.drawable.setting_i);
		}
		
		else if(NotificationFragment.getNotificationListByParentIDList.getGetNotificationListByParentID().get(StaticVariables.positionNotificationSelected).getNotificationID()==3)

		{
			imageNotificationNewFragment2.setImageResource(R.drawable.insight_i);
		}
		
		else
		{
			imageNotificationNewFragment2.setImageResource(R.drawable.scheduler_i);
		}		
		}
		
		
		idNotificationTimeFragment2.setText(NotificationFragment.getNotificationListByParentIDList.getGetNotificationListByParentID().get(StaticVariables.positionNotificationSelected).getTime());
		typeFace.setTypefaceLight(idNotificationTimeFragment2);
		
		idNotificationDescFragment2.setText(NotificationFragment.getNotificationListByParentIDList.getGetNotificationListByParentID().get(StaticVariables.positionNotificationSelected).getDescription());
		typeFace.setTypefaceRegular(idNotificationDescFragment2);
		
		

		return view;		
	}
	
	
	
	



	public static NotificationFragmentTwo getInstance()
	{
		if(notificationFragmentTwo==null)
		{
			notificationFragmentTwo = new NotificationFragmentTwo();
		}
		return notificationFragmentTwo;
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

		
		
		
}
