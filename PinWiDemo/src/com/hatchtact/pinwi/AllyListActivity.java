package com.hatchtact.pinwi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.AllyListByParentIdAdapter;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.GetListofAllysByParentIDList;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

public class AllyListActivity extends MainActionBarActivity implements OnItemClickListener
{
	private ShowMessages showMessage=null;  
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;

	private GetListofAllysByParentIDList getListofAllysByParentIDList;

	private AllyListByParentIdAdapter allyListByParentIdAdapter=null;

	private ListView listView=null;

	int parentId=0;

	private ParentProfile parentCompleteInformation;
	private Gson gsonRegistration=null;
	private SharePreferenceClass sharePreferenceClass=null;

	private ImageView addally_Image=null;
	private TextView addally_text=null;

	private TypeFace typeFace=null;
	private TextView updateally_text; 

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		screenName="Settings";

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allylist);

		getListofAllysByParentIDList=new GetListofAllysByParentIDList();
		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(AllyListActivity.this);
		checkNetwork=new CheckNetwork();
		sharePreferenceClass=new SharePreferenceClass(AllyListActivity.this);
		typeFace= new TypeFace(AllyListActivity.this);

		listView=(ListView) findViewById(R.id.containallallyname_list);

		addally_Image= (ImageView) findViewById(R.id.addally_Image);
		addally_text=(TextView) findViewById(R.id.addally_text);
		updateally_text = (TextView) findViewById(R.id.updateally_text);

		typeFace.setTypefaceLight(updateally_text);
		typeFace.setTypefaceLight(addally_text);



		gsonRegistration=new GsonBuilder().create();

		parentCompleteInformation = gsonRegistration.fromJson(sharePreferenceClass.getParentProfile(), ParentProfile.class);
		parentId=parentCompleteInformation.getParentID();

		addally_Image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(AllyListActivity.this,AllyRegistrationActivity.class);
				Bundle bundleLocation=new Bundle();
				bundleLocation.putBoolean("ToAllyScreenFromAdd", true);
				intent.putExtras(bundleLocation);
				startActivity(intent);
				AllyListActivity.this.finish();
			}
		});

		addally_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(AllyListActivity.this,AllyRegistrationActivity.class);
				Bundle bundleLocation=new Bundle();
				bundleLocation.putBoolean("ToAllyScreenFromAdd", true);
				intent.putExtras(bundleLocation);
				startActivity(intent);
				AllyListActivity.this.finish();
			}
		});

		new GetAllyName().execute();
	}

	private ProgressDialog progressDialog=null;	

	private class GetAllyName extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog = ProgressDialog.show(AllyListActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(AllyListActivity.this))
			{
				getListofAllysByParentIDList =serviceMethod.getAllyListByParentId(parentId);
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

				if(checkNetwork.checkNetworkConnection(AllyListActivity.this))
					new GetAllyName().execute();

			}
			else
			{
				if(getListofAllysByParentIDList!=null && getListofAllysByParentIDList.getGetListofAllysByParentID().size()>0)
				{
					allyListByParentIdAdapter=new AllyListByParentIdAdapter(AllyListActivity.this, getListofAllysByParentIDList.getGetListofAllysByParentID(),AllyListActivity.this);
					listView.setAdapter(allyListByParentIdAdapter);
					listView.setOnItemClickListener(AllyListActivity.this);
				}
				else
				{	
					getError();
				}	
			}	
		}	
	}

	private void getError()
	{/*
		Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());
	*/}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub

		Intent allyIntent=new Intent(AllyListActivity.this,AllyRegistrationActivity.class);
		Bundle bundleLocation=new Bundle();
		bundleLocation.putBoolean("ToAllyScreen", true);
		bundleLocation.putInt("allyId", getListofAllysByParentIDList.getGetListofAllysByParentID().get(position).getAllyID());
		allyIntent.putExtras(bundleLocation);
		startActivity(allyIntent);
		AllyListActivity.this.finish();
	}






	public void deleteAlly(int AllyId, int position)
	{
		new DeleteAllyAsync(AllyId,position).execute();
	}



	private int positionList;

	public class DeleteAllyAsync extends AsyncTask<Void, Void, Integer>
	{
		private int currentAllyId;
		public DeleteAllyAsync(int AllyId,int position)
		{
			currentAllyId=AllyId;
			positionList=position;

		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog = ProgressDialog.show(AllyListActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		String status;
		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(AllyListActivity.this))
			{
				status=serviceMethod.deleteAllyByAllyid(currentAllyId);


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

				if(checkNetwork.checkNetworkConnection(AllyListActivity.this))
					new DeleteAllyAsync(currentAllyId,positionList).execute();

			}
			else
			{
				if(status.equalsIgnoreCase("0"))
				{
					Toast.makeText(AllyListActivity.this, "Ally Deleted Successfully.", Toast.LENGTH_LONG).show();

					allyListByParentIdAdapter.list_allyName.remove(positionList);
					allyListByParentIdAdapter.notifyDataSetChanged();


				}
				else
				{
					getError();
				}
			}	
		}	
	}

}
