package com.hatchtact.pinwi.fragment.whattodo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.AllyListAdapter;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.ListOfAllys;
import com.hatchtact.pinwi.classmodel.ListOfAllysList;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;

public class InformAllyFragment extends ParentFragment implements OnItemClickListener
{
	private View view;
	
	private ShowMessages showMessage=null;  
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;

	private ListOfAllysList allAllyNameList;

	private AllyListAdapter allyListAdapter=null;

	private ListView listView=null;
	private TextView afterschoolInformAlly_text=null;
	private TextView iformAlly_text=null;
	
	private ImageView addally_Image=null;
	private TextView addally_text=null;
	
	private EditText editsearch;
	private ImageView imageSearch;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.activity_list_ally, container, false);
		setHasOptionsMenu(true);
		mListener.onFragmentAttached(false,"  Scheduler");
		
		allAllyNameList=new ListOfAllysList();
		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(getActivity());
		checkNetwork=new CheckNetwork();
		
		StaticVariables.fragmentIndex=9;
		
		listView=(ListView) view.findViewById(R.id.containall_allyNameList);
		afterschoolInformAlly_text=(TextView) view.findViewById(R.id.afterschoolInformAlly_text);
		iformAlly_text=(TextView) view.findViewById(R.id.iformAlly_text);
			
		typeFace.setTypefaceRegular(afterschoolInformAlly_text);
		typeFace.setTypefaceRegular(iformAlly_text);
		
		addally_Image= (ImageView) view.findViewById(R.id.addally_Image);
		addally_text=(TextView) view.findViewById(R.id.addally_text);
		typeFace.setTypefaceLight(addally_text);

		addally_Image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*Intent intent=new Intent(getActivity(),AllyRegistrationActivity.class);
				Bundle bundleLocation=new Bundle();
				bundleLocation.putBoolean("ToAllyScreenFromAdd", true);
				intent.putExtras(bundleLocation);
				startActivityForResult(intent, 200);
				StaticVariables.fromInformAlly=true;	*/
			}
		});

		addally_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*Intent intent=new Intent(getActivity(),AllyRegistrationActivity.class);
				Bundle bundleLocation=new Bundle();
				bundleLocation.putBoolean("ToAllyScreenFromAdd", true);
				intent.putExtras(bundleLocation);
				StaticVariables.fromInformAlly=true;
				startActivityForResult(intent, 200);*/
				
			}
		});
		
		afterschoolInformAlly_text.setText(StaticVariables.WhatToDoActivityName);
		
		imageSearch=(ImageView) view.findViewById(R.id.imageSearch);
		editsearch=(EditText) view.findViewById(R.id.editSearch);
		typeFace.setTypefaceRegular(editsearch);
		imageSearch.setVisibility(View.VISIBLE);
		
		editsearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				//imageSearch.setVisibility(View.GONE);
				if(allAllyNameList!=null)
				{
					if(allAllyNameList.getListOfAllys().size()>0)
					{
						allyListAdapter.getFilter().filter(cs.toString()); 
						allyListAdapter.notifyDataSetChanged();
						listView.invalidate();
					}
				}
			}
		});
		
		
		new GetAllyList().execute();

		return view;
	}
	
	//private ProgressDialog progressDialog=null;

	private class GetAllyList extends AsyncTask<Void, Void, Integer>
	{
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
				allAllyNameList =serviceMethod.getListOfAllys(StaticVariables.currentChild.getChildID());
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
					new GetAllyList().execute();

			}
			else
			{
				if(allAllyNameList!=null && allAllyNameList.getListOfAllys().size()>0)
				{
					allyListAdapter=new AllyListAdapter(getActivity(), allAllyNameList.getListOfAllys());
					listView.setAdapter(allyListAdapter);
					listView.setOnItemClickListener(InformAllyFragment.this);
					/*
					for(int i=0;i<allAllyNameList.getListOfAllys().size();i++)
					{
						ListOfAllys ally = new ListOfAllys();
						
						ally.setAllyName(allAllyNameList.getListOfAllys().get(i).getAllyName());
						ally.setAllyProfileID(allAllyNameList.getListOfAllys().get(i).getAllyProfileID());
						ally.setAllyProfileImage(allAllyNameList.getListOfAllys().get(i).getAllyProfileImage());
						
						StaticVariables.allyInformation.add(ally);
					}	*/
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
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) 
	{
		// TODO Auto-generated method stub
		if(StaticVariables.fragmentIndexCurrentTabWhatToDo==317)
		{
		StaticVariables.fragmentIndexCurrentTabWhatToDo = 316;
	
		}
		else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==321)
		{
			StaticVariables.fragmentIndexCurrentTabWhatToDo = 320;
		
		}
		else if(StaticVariables.fragmentIndexCurrentTabWhatToDo==325)
		{
			StaticVariables.fragmentIndexCurrentTabWhatToDo = 324;
			
		}
		ListOfAllys model = (ListOfAllys) listView.getAdapter().getItem(position);//it contains filtered selected model
		
		StaticVariables.AllyName=model.getAllyName();
		StaticVariables.AllyId=model.getAllyProfileID();
		
		switchingFragments(new AllyDropPickFragment());
		/*getFragmentManager().beginTransaction().add(R.id.realtabcontent,
				new AllyDropPickFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();*/
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
		
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			
			//if (resultCode == -1) 
			{
				switch(requestCode) 
				{
				
				case 200:
					new GetAllyList().execute();

					break;
				}
			}
		}

}
