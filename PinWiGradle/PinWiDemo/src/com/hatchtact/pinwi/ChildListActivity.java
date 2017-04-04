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
import com.hatchtact.pinwi.adapter.ChildListAdapter;
import com.hatchtact.pinwi.classmodel.ChildModel;
import com.hatchtact.pinwi.classmodel.ChildProfile;
import com.hatchtact.pinwi.classmodel.GetListofChildsByParentIDList;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.CustomLoader;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

public class ChildListActivity extends MainActionBarActivity implements OnItemClickListener
{
	private ShowMessages showMessage=null;
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;

	private GetListofChildsByParentIDList getListofChildsByParentIDList;

	private ChildListAdapter childListAdapter=null;

	private ListView listView=null;

	int parentId=0;

	private ParentProfile parentCompleteInformation;
	private Gson gsonRegistration=null;
	private SharePreferenceClass sharePreferenceClass=null;

	private ImageView addchild_Image=null;
	private TextView addchild_text=null;
	private TextView updatechild_text;

	private TypeFace typeFace=null;
	private CustomLoader customProgressLoader;
	private boolean isButtonTouched=false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		screenName="Settings";

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_childlist);
		isButtonTouched=false;
		customProgressLoader=new CustomLoader(ChildListActivity.this);

		getListofChildsByParentIDList=new GetListofChildsByParentIDList();
		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(ChildListActivity.this);
		checkNetwork=new CheckNetwork();
		typeFace= new TypeFace(ChildListActivity.this);
		sharePreferenceClass=new SharePreferenceClass(ChildListActivity.this);
		gsonRegistration=new GsonBuilder().create();

		listView=(ListView) findViewById(R.id.containallchildname_list);

		addchild_Image=(ImageView) findViewById(R.id.addchild_Image);
		addchild_text=(TextView) findViewById(R.id.addchild_text);
		updatechild_text =(TextView) findViewById(R.id.updatechild_text);

		typeFace.setTypefaceLight(addchild_text);
		typeFace.setTypefaceLight(updatechild_text);



		parentCompleteInformation = gsonRegistration.fromJson(sharePreferenceClass.getParentProfile(), ParentProfile.class);
		parentId=parentCompleteInformation.getParentID();

		addchild_Image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isButtonTouched) {
					isButtonTouched=true;
					Intent childIntent = new Intent(ChildListActivity.this, ChildRegistrationActivity.class);
					Bundle bundleLocation = new Bundle();
					bundleLocation.putBoolean("ToChildScreenFromAdd", true);
					childIntent.putExtras(bundleLocation);
					startActivity(childIntent);
					ChildListActivity.this.finish();
				}
			}
		});


		addchild_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isButtonTouched) {
					isButtonTouched = true;
					Intent childIntent = new Intent(ChildListActivity.this, ChildRegistrationActivity.class);
					Bundle bundleLocation = new Bundle();
					bundleLocation.putBoolean("ToChildScreenFromAdd", true);
					childIntent.putExtras(bundleLocation);
					startActivity(childIntent);
					ChildListActivity.this.finish();
				}
			}
		});

		new GetChildName().execute();
	}

	private ProgressDialog progressDialog=null;

	private class GetChildName extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			if(customProgressLoader!=null)
			{
				customProgressLoader.startHandler();
			}
			/*progressDialog = ProgressDialog.show(ChildListActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ChildListActivity.this))
			{
				getListofChildsByParentIDList =serviceMethod.getchildListByParentId(parentId);
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
				customProgressLoader.removeCallbacksHandler();
				/*if (progressDialog.isShowing())
					progressDialog.cancel();*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(ChildListActivity.this))
					new GetChildName().execute();

			}
			else
			{
				if(getListofChildsByParentIDList!=null && getListofChildsByParentIDList.getGetListofChildsByParentID().size()>0)
				{
					StaticVariables.childInfo.clear();
					StaticVariables.childArrayList.clear();
					StaticVariables.isChildUpdated=true;
					for(int i=0;i<getListofChildsByParentIDList.getGetListofChildsByParentID().size();i++)
					{

						ChildProfile child = new ChildProfile();
						child.setChildID(getListofChildsByParentIDList.getGetListofChildsByParentID().get(i).getChildID());
						child.setFirstName(getListofChildsByParentIDList.getGetListofChildsByParentID().get(i).getNickName());
						//child.setNickName(getListofChildsByParentIDList.getGetListofChildsByParentID().get(i).getNickName());
						//child.setProfileImage(accessProfileList.getAccessProfileList().get(i).getProfileImage());

						StaticVariables.childInfo.add(child);
						ChildModel model=new ChildModel();
						model.setChildID(getListofChildsByParentIDList.getGetListofChildsByParentID().get(i).getChildID());
						model.setFirstName(getListofChildsByParentIDList.getGetListofChildsByParentID().get(i).getFirstName());
						model.setNickName(getListofChildsByParentIDList.getGetListofChildsByParentID().get(i).getNickName());
						StaticVariables.childArrayList.add(model);

					}

					StaticVariables.currentChild=StaticVariables.childInfo.get(0);
					childListAdapter=new ChildListAdapter(ChildListActivity.this, getListofChildsByParentIDList.getGetListofChildsByParentID(),ChildListActivity.this);
					listView.setAdapter(childListAdapter);
					childListAdapter.notifyDataSetChanged();
					listView.setOnItemClickListener(ChildListActivity.this);
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

		if(!isButtonTouched) {
			isButtonTouched = true;
			Intent childIntent = new Intent(ChildListActivity.this, ChildRegistrationActivity.class);
			Bundle bundleLocation = new Bundle();
			bundleLocation.putBoolean("ToChildScreen", true);
			bundleLocation.putInt("childId", getListofChildsByParentIDList.getGetListofChildsByParentID().get(position).getChildID());
			childIntent.putExtras(bundleLocation);
			startActivity(childIntent);
			ChildListActivity.this.finish();
		}
	}



	public void deleteChild(int ChildId, int position)
	{
		new DeleteChildAsync(ChildId,position).execute();
	}



	private int positionList;

	public class DeleteChildAsync extends AsyncTask<Void, Void, Integer>
	{
		private int currentChildId;
		public DeleteChildAsync(int ChildId,int position)
		{
			currentChildId=ChildId;
			positionList=position;

		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			if(customProgressLoader!=null)
			{
				customProgressLoader.showProgressBar();
			}
			/*progressDialog = ProgressDialog.show(ChildListActivity.this, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
		}

		String status;
		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(ChildListActivity.this))
			{
				status=serviceMethod.deleteChildByChildId(currentChildId);
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

				if(checkNetwork.checkNetworkConnection(ChildListActivity.this))
					new DeleteChildAsync(currentChildId,positionList).execute();

			}
			else
			{
				if(status.equalsIgnoreCase("0"))
				{
					if(childListAdapter.list_childName.size()==1)
					{
						Toast.makeText(ChildListActivity.this, "At least one child is required.", Toast.LENGTH_LONG).show();
					}
					else
					{
						Toast.makeText(ChildListActivity.this, "Child Deleted Successfully.", Toast.LENGTH_LONG).show();

						childListAdapter.list_childName.remove(positionList);
						childListAdapter.notifyDataSetChanged();
					}
					StaticVariables.childInfo.clear();

					for(int i=0;i<childListAdapter.list_childName.size();i++)
					{

						ChildProfile child = new ChildProfile();
						child.setChildID(childListAdapter.list_childName.get(i).getChildID());
						child.setFirstName(childListAdapter.list_childName.get(i).getNickName());
						//child.setProfileImage(accessProfileList.getAccessProfileList().get(i).getProfileImage());

						StaticVariables.childInfo.add(child);

					}

					if(StaticVariables.childInfo!=null && StaticVariables.childInfo.size()>0)
					{
						StaticVariables.currentChild=StaticVariables.childInfo.get(0);
					}


					StaticVariables.childArrayList.clear();

					for(int i=0;i<childListAdapter.list_childName.size();i++)
					{


						ChildModel model=new ChildModel();
						model.setChildID(getListofChildsByParentIDList.getGetListofChildsByParentID().get(i).getChildID());
						model.setFirstName(getListofChildsByParentIDList.getGetListofChildsByParentID().get(i).getFirstName());
						model.setNickName(getListofChildsByParentIDList.getGetListofChildsByParentID().get(i).getNickName());;
						StaticVariables.childArrayList.add(model);

					}

					if(StaticVariables.childInfo!=null && StaticVariables.childInfo.size()>0)
					{
						StaticVariables.currentChild=StaticVariables.childInfo.get(0);
					}


				}
				else
				{
					getError();
				}
			}
		}
	}

}
