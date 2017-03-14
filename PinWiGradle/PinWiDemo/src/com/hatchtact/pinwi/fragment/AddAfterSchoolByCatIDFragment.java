package com.hatchtact.pinwi.fragment;

import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.AfterSchoolSubCategoryByCatIDAdapter;
import com.hatchtact.pinwi.classmodel.AfterSchoolActivityByCatId;
import com.hatchtact.pinwi.classmodel.AfterSchoolActivityByCatIdList;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AddAfterSchoolByCatIDFragment extends ParentFragment implements OnItemClickListener
{
	private View view;

	private ShowMessages showMessage=null;  
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;

	private ListView listdata=null;

	private AfterSchoolActivityByCatIdList afterSchoolActivityByCatIdList;

	private ParentProfile parentCompleteInformation;
	private Gson gsonRegistration=null;
	private SharePreferenceClass sharePreferenceClass=null;

	private int parentId=0;

	private TextView subcategorybyCatid_text=null;
	private TextView afterschoolByCatIdActivity_text=null;

	private LinearLayout layout_customadd=null;

	private AfterSchoolSubCategoryByCatIDAdapter afterSchoolSubCategoryByCatIDAdapter;

	private boolean startAsync=false;

	private TextView cannotfindActivity_text;

	private TextView createCustomActivity_text;

	private EditText editsearch;
	private ImageView imageSearch;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view=inflater.inflate(R.layout.activity_afterschool_bycatid, container, false);

		setHasOptionsMenu(true);
		mListener.onFragmentAttached(false,"  Scheduler");

		StaticVariables.fragmentIndex=7;

		StaticVariables.addAfterSchoolActivities=null;

		for(int i=0;i<StaticVariables.daysSelectedInAfterSchool.length;i++)
		{
			StaticVariables.daysSelectedInAfterSchool[i]=false;

		}

		sharePreferenceClass=new SharePreferenceClass(getActivity());
		gsonRegistration=new GsonBuilder().create();

		parentCompleteInformation = gsonRegistration.fromJson(sharePreferenceClass.getParentProfile(), ParentProfile.class);
		parentId=parentCompleteInformation.getParentID();

		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(getActivity());
		checkNetwork=new CheckNetwork();

		afterSchoolActivityByCatIdList=new AfterSchoolActivityByCatIdList();

		listdata=(ListView) view.findViewById(R.id.containallByCatId_list);
		subcategorybyCatid_text=(TextView) view.findViewById(R.id.afterschoolByCatId_text);
		layout_customadd=(LinearLayout) view.findViewById(R.id.layout_customadd);
		afterschoolByCatIdActivity_text=(TextView) view.findViewById(R.id.afterschoolByCatIdActivity_text);
		cannotfindActivity_text=(TextView) view.findViewById(R.id.cannotfindActivity_text);
		createCustomActivity_text=(TextView) view.findViewById(R.id.createCustomActivity_text);

		typeFace.setTypefaceRegular(subcategorybyCatid_text);
		typeFace.setTypefaceRegular(afterschoolByCatIdActivity_text);
		typeFace.setTypefaceRegular(cannotfindActivity_text);
		typeFace.setTypefaceRegular(createCustomActivity_text);


		subcategorybyCatid_text.setText(StaticVariables.subCategoryName);

		layout_customadd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				/*Bundle bundle=new Bundle();
				bundle.putInt("SubCategoryId", subcategoryId);*/

				if(StaticVariables.fragmentIndexCurrentTabSchedular==31)
				{
					StaticVariables.fragmentIndexCurrentTabSchedular=34;
				}
				else if(StaticVariables.fragmentIndexCurrentTabSchedular==40)
				{
					StaticVariables.fragmentIndexCurrentTabSchedular=41;
				}

				else if(StaticVariables.fragmentIndexCurrentTabSchedular==48)
				{
					StaticVariables.fragmentIndexCurrentTabSchedular=49;
				}
				else if(StaticVariables.fragmentIndexCurrentTabSchedular==65)
				{
					StaticVariables.fragmentIndexCurrentTabSchedular=66;

				}
				switchingFragments(new AddCustomFragment());
				/*getFragmentManager().beginTransaction().add(R.id.realtabcontent,
						new AddCustomFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();*/
			}
		});   

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
				if(afterSchoolActivityByCatIdList!=null)
				{
					if(afterSchoolActivityByCatIdList.getAfterschoolActivityByCatId().size()>0)
					{
						afterSchoolSubCategoryByCatIDAdapter.getFilter().filter(cs.toString()); 
						afterSchoolSubCategoryByCatIDAdapter.notifyDataSetChanged();
						listdata.invalidate();
					}
				}
			}
		});


		return view;		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		if(!startAsync )
		{
			startAsync = true;
			new GetCategoryNameByCatId(StaticVariables.subCategoryId,parentId).execute();
		}
	}


	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		startAsync = false;
	}

	//private ProgressDialog progressDialog=null;

	private class GetCategoryNameByCatId extends AsyncTask<Void, Void, Integer>
	{
		int subcategoryID;
		int parentID;
		public GetCategoryNameByCatId(int subcategoryId, int parentId) 
		{
			// TODO Auto-generated constructor stub
			subcategoryID=subcategoryId;
			parentID=parentId;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(customProgressLoader!=null)
			{
				customProgressLoader.startHandler();
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
				afterSchoolActivityByCatIdList =serviceMethod.getactivityByCatId(subcategoryID, parentID);
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

				if(checkNetwork.checkNetworkConnection(getActivity()))
					new GetCategoryNameByCatId(subcategoryID, parentID).execute();

			}
			else
			{
				if(afterSchoolActivityByCatIdList!=null && afterSchoolActivityByCatIdList.getAfterschoolActivityByCatId().size()>0)
				{
					afterSchoolSubCategoryByCatIDAdapter=new AfterSchoolSubCategoryByCatIDAdapter(getActivity(),afterSchoolActivityByCatIdList.getAfterschoolActivityByCatId());
					listdata.setAdapter(afterSchoolSubCategoryByCatIDAdapter);
					listdata.setOnItemClickListener(AddAfterSchoolByCatIDFragment.this);
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

	//AddAfterSchoolFragment addAfterSchoolFragment=new AddAfterSchoolFragment();

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub

		//Bundle bundle = new Bundle();

		AfterSchoolActivityByCatId model = (AfterSchoolActivityByCatId) listdata.getAdapter().getItem(position);

		StaticVariables.subSubCategoryName = model.getName();
		StaticVariables.subSubCategoryId = model.getActivityID();

		/*bundle.putString("SubCategoryName", subcategoryName);
		bundle.putString("SubCategoryNameCatId", subCategoryNamebyCatId);
		bundle.putInt("SubCategoryId", subcategoryId);
		addAfterSchoolFragment.setArguments(bundle);
		 */
		resetDataForAddAfterSchoolAlly();

		if(StaticVariables.fragmentIndexCurrentTabSchedular==31)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=35;		}
		else if(StaticVariables.fragmentIndexCurrentTabSchedular==40)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=42;
		}

		else if(StaticVariables.fragmentIndexCurrentTabSchedular==48)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=50;
		}

		else if(StaticVariables.fragmentIndexCurrentTabSchedular==65)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=67;

		}

		switchingFragments(new AddAfterSchoolFragment());
		/*getFragmentManager().beginTransaction().add(R.id.realtabcontent,
				new AddAfterSchoolFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();*/
	}

	private void resetDataForAddAfterSchoolAlly() 
	{
		// TODO Auto-generated method stub
		StaticVariables.showAllyName1InAfterSchool = false;
		StaticVariables.showAllyName2InAfterSchool = false;

		/*	StaticVariables.allySetFor1 = null;
		StaticVariables.allySetFor2 = null;*/

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


}
