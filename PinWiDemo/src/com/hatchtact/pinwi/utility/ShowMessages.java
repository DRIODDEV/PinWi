package com.hatchtact.pinwi.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.widget.Toast;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.fragment.AddAfterSchoolByCatIDFragment;
import com.hatchtact.pinwi.fragment.AfterSchoolActivityByChildIdFragment;
import com.hatchtact.pinwi.fragment.SubjectActivityByChildIDFragment;
import com.hatchtact.pinwi.fragment.network.OnEventListener;

public class ShowMessages {
	
	private Context mContext;

	public ShowMessages(Context mContext) {
		super();
		this.mContext = mContext;
	}
	
	public void showToast(String message)
	{
		Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
	}
	
	public void showAlert(String title, String message)
	{
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
		
		alertBuilder.setTitle(title);
		alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
		alertBuilder.setMessage(message);
		alertBuilder.setPositiveButton(" OK ", new DialogInterface.OnClickListener() 
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				
			}
		});	
		alertBuilder.show();
	}
	
	public void showAlertAndMove(String title, String message, final String toMove,final FragmentManager fm)
	{
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
		
		alertBuilder.setTitle(title);
		alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
		alertBuilder.setMessage(message);
		alertBuilder.setPositiveButton(" OK ", new DialogInterface.OnClickListener() 
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				if(toMove.equalsIgnoreCase("FromAddCustom"))
				{
					if(StaticVariables.fragmentIndexCurrentTabSchedular==27)
					{
						StaticVariables.fragmentIndexCurrentTabSchedular=31;
					}
					else if(StaticVariables.fragmentIndexCurrentTabSchedular==39)
					{
						StaticVariables.fragmentIndexCurrentTabSchedular=40;
					}
					else if(StaticVariables.fragmentIndexCurrentTabSchedular==47)
					{
						StaticVariables.fragmentIndexCurrentTabSchedular=48;
					}
					
					fm.beginTransaction().replace(R.id.tabcontent_frame_layout,
							new AddAfterSchoolByCatIDFragment()).commit();
					dialog.dismiss();
					
					return;
					
				}
				else if(toMove.equalsIgnoreCase("SubjectActivityByChildID"))
				{
					//currently at SubjectActivityByChildID
					//if(StaticVariables.fragmentIndex == 4)
					{	
						StaticVariables.fragmentIndexCurrentTabSchedular = 13;
						fm.beginTransaction().replace(R.id.tabcontent_frame_layout,
								new SubjectActivityByChildIDFragment()).commit();
					}
					/*else
					{
						StaticVariables.fragmentIndexCurrentTabSchedular = 11;
						fm.beginTransaction().replace(R.id.realtabcontent,
								new CalenderFragment()).commit();
					
					}*/
					dialog.dismiss();
					
					return;
				}
				else if(toMove.equalsIgnoreCase("SchoolActivityAdded"))
				{
					//currently at SubjectActivityByChildID
					//if(StaticVariables.fragmentIndex == 4)
					{	
						StaticVariables.fragmentIndexCurrentTabSchedular = 13;
						fm.beginTransaction().replace(R.id.tabcontent_frame_layout,
								new SubjectActivityByChildIDFragment()).commit();
					}
					/*else
					{
						StaticVariables.fragmentIndexCurrentTabSchedular = 11;
						fm.beginTransaction().replace(R.id.realtabcontent,
								new CalenderFragment()).commit();
					
					}*/
					dialog.dismiss();
					
					return;
				}
				else if(toMove.equalsIgnoreCase("AfterSchoolActivityAdded"));
				{
					//currently at SubjectActivityByChildID
					//if(StaticVariables.fragmentIndex == 8)
					{	
						//Removed all the fragments previously added

						StaticVariables.fragmentIndexCurrentTabSchedular = 12;
						fm.beginTransaction().replace(R.id.tabcontent_frame_layout,
								new AfterSchoolActivityByChildIdFragment()).commit();
					}
					/*else
					{
						StaticVariables.fragmentIndexCurrentTabSchedular = 12;
						fm.beginTransaction().replace(R.id.realtabcontent,
								new CalenderFragment()).commit();
					
					}*/
					dialog.dismiss();
					
					return;
				}
			}
		});	
		alertBuilder.show();
	}
	
	public void showAlertDestroy(String title, String message)
	{
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
		
		alertBuilder.setTitle(title);
		alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
		alertBuilder.setMessage(message);
		alertBuilder.setPositiveButton(" OK ", new DialogInterface.OnClickListener() 
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				
				((Activity) mContext).finish();
				
			}
		});	
		alertBuilder.show();
	}
	
	public void showToastMessage(String text)
	{
		Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
	}

	
	
	public void showAlertInsights(String title, String message)
	{
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
		
		alertBuilder.setTitle(title);
		alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
		alertBuilder.setMessage(Html.fromHtml(message));
		alertBuilder.setPositiveButton(" OK ", new DialogInterface.OnClickListener() 
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				
			}
		});	
		alertBuilder.show();
	}

	public void showAlertNetwork( Context mContext,final OnEventListener<String> callback) 
	{
		// TODO Auto-generated method stub

		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);

		alertBuilder.setTitle("Warning");
		alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
		alertBuilder.setMessage("Are you sure you want to remove this connection from your Network?");

		alertBuilder.setPositiveButton(" Yes ", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(final DialogInterface dialog, int which) {
				dialog.dismiss();
				callback.onSuccess("");

			}
		});
		alertBuilder.setNegativeButton(" No ", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				callback.onFailure("");

			}
		});

		alertBuilder.show();
	
		
	}
	
	
	public void showAlertChild( Context mContext,final OnEventListener<String> callback) 
	{
		// TODO Auto-generated method stub

		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);

		alertBuilder.setTitle("Warning");
		alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
		alertBuilder.setMessage("Losing a buddy is sad. Sure you want to?");

		alertBuilder.setPositiveButton(" Yes ", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(final DialogInterface dialog, int which) {
				dialog.dismiss();
				callback.onSuccess("");

			}
		});
		alertBuilder.setNegativeButton(" No ", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				callback.onFailure("");

			}
		});

		alertBuilder.show();
	
		
	}
	
	
	public void showAlertChildInterface( Context mContext,String title, String message,final OnEventListener<String> callback) 
	{
		// TODO Auto-generated method stub

		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);

		alertBuilder.setTitle(title);
		alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
		alertBuilder.setMessage(message);

		alertBuilder.setPositiveButton(" Yes ", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(final DialogInterface dialog, int which) {
				dialog.dismiss();
				callback.onSuccess("");

			}
		});
		alertBuilder.setNegativeButton(" No ", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				callback.onFailure("");

			}
		});

		alertBuilder.show();
	
		
	}
	
	public void showAlertChildInterfaceDiscard( Context mContext,String title, String message,final OnEventListener<String> callback) 
	{
		// TODO Auto-generated method stub

		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);

		alertBuilder.setTitle(title);
		alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
		alertBuilder.setMessage(message);

		alertBuilder.setPositiveButton(" OK ", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(final DialogInterface dialog, int which) {
				dialog.dismiss();
				callback.onSuccess("");

			}
		});
		alertBuilder.setNegativeButton(" NO ", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				callback.onFailure("");

			}
		});

		alertBuilder.show();
	
		
	}
	
	public void showAlertFrequency( Context mContext,final OnEventListener<String> callback) 
	{
		// TODO Auto-generated method stub

		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);

		alertBuilder.setTitle("Alert");
		alertBuilder.setIcon(android.R.drawable.ic_menu_info_details);
		alertBuilder.setMessage("Continue without saving? ");

		alertBuilder.setPositiveButton(" No ", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(final DialogInterface dialog, int which) {
				
				dialog.dismiss();
				callback.onFailure("");
			}
		});
		alertBuilder.setNegativeButton(" Yes ", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				
				dialog.dismiss();
				callback.onSuccess("");

			}
		});

		alertBuilder.show();
	
		
	}
	
}
