package com.hatchtact.pinwi.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.AfterSchoolCategoriesAdapter;
import com.hatchtact.pinwi.adapter.SearchActivityAdapter;
import com.hatchtact.pinwi.classmodel.AfterSchoolCategoriesByOwnerID;
import com.hatchtact.pinwi.classmodel.AfterSchoolCategoriesByOwnerIDList;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.SearchActivitiesByActivityName;
import com.hatchtact.pinwi.classmodel.SearchActivitiesByActivityNameList;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;

public class AddAfterSchoolCategoriesFragment extends ParentFragment implements OnItemClickListener
{
	private View view;

	private ShowMessages showMessage=null;  
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;
	private ListView listView=null;


	private AfterSchoolCategoriesByOwnerIDList afterSchoolCategoriesByOwnerIDList;
	private SearchActivitiesByActivityNameList searchActivityList;
	private AfterSchoolCategoriesAdapter afterSchoolCategoriesAdapter=null;
	private SearchActivityAdapter searchAdapter=null;

	private TextView afterschoolActivity_text=null;

	/*String categoryName=null;
	int categoryId=0;*/

	private ImageView imageSearch;
	private EditText editsearch;
	private View editSchoolLayout;

	private boolean flag_loading=false;
	private boolean isSearchList=false;
	private boolean isSearchDone=false;
	
	private TextView cannotfindActivity_text;
	private TextView createCustomActivity_text;
	private LinearLayout layout_customadd=null;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view=inflater.inflate(R.layout.activity_afterschool_categorieslist, container, false);

		setHasOptionsMenu(true);
		mListener.onFragmentAttached(false,"  Scheduler");
		isSearchDone=false;
		flag_loading=false;
		isSearchList=false;
		StaticVariables.fragmentIndex=5;
		AddAfterSchoolFragment.updatedDataFromAfterSchool = 0;
		StaticVariables.addAfterSchoolActivities=null;

		for(int i=0;i<StaticVariables.daysSelectedInAfterSchool.length;i++)
		{
			StaticVariables.daysSelectedInAfterSchool[i]=false;

		}


		afterSchoolCategoriesByOwnerIDList=new AfterSchoolCategoriesByOwnerIDList();
		searchActivityList=new SearchActivitiesByActivityNameList();
		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(getActivity());
		checkNetwork=new CheckNetwork();
		cannotfindActivity_text=(TextView) view.findViewById(R.id.cannotfindActivity_text);
		createCustomActivity_text=(TextView) view.findViewById(R.id.createCustomActivity_text);
		layout_customadd=(LinearLayout) view.findViewById(R.id.layout_customadd);
		layout_customadd.setVisibility(View.GONE);
		listView=(ListView) view.findViewById(R.id.containallgameTution_list);
		afterschoolActivity_text=(TextView) view.findViewById(R.id.afterschoolActivity_text);

		typeFace.setTypefaceRegular(afterschoolActivity_text);
		typeFace.setTypefaceRegular(cannotfindActivity_text);
		typeFace.setTypefaceRegular(createCustomActivity_text);

		editSchoolLayout=view.findViewById(R.id.editSchoolLayout);
		editSchoolLayout.setVisibility(View.VISIBLE);
		imageSearch=(ImageView) view.findViewById(R.id.imageSearch);
		editsearch=(EditText) view.findViewById(R.id.editSearch);
		editsearch.setHint("Search Activity");
		typeFace.setTypefaceRegular(editsearch);
		imageSearch.setVisibility(View.VISIBLE);
		
		layout_customadd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				hideKeyBoard();

				
				if(StaticVariables.fragmentIndexCurrentTabSchedular==22)
				{
					StaticVariables.fragmentIndexCurrentTabSchedular=83;
				}
				else if(StaticVariables.fragmentIndexCurrentTabSchedular==37)
				{
					StaticVariables.fragmentIndexCurrentTabSchedular=85;

				}
				else if(StaticVariables.fragmentIndexCurrentTabSchedular==23)
				{
					StaticVariables.fragmentIndexCurrentTabSchedular=82;

				}

				else if(StaticVariables.fragmentIndexCurrentTabSchedular==17)
				{
					StaticVariables.fragmentIndexCurrentTabSchedular=84;

				}
				switchingFragments(new AddCustomFragment());
				/*getFragmentManager().beginTransaction().add(R.id.realtabcontent,
						new AddCustomFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();*/
			}
		});   

		/*editsearch.addTextChangedListener(new TextWatcher() {

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
				if(afterSchoolCategoriesByOwnerIDList!=null)
				{
					if(afterSchoolCategoriesByOwnerIDList.getAfterSchoolCategoriesByOwnerID().size()>0)
					{
						afterSchoolCategoriesAdapter.getFilter().filter(cs.toString()); 
						afterSchoolCategoriesAdapter.notifyDataSetChanged();
						listView.invalidate();
					}
				}
			}
		});*/

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
						if(searchActivityList!=null)
						{
							searchActivityList.getSearchActivitiesByActName().clear();
						}
						else
						{
							searchActivityList=new SearchActivitiesByActivityNameList ();
						}
						isSearchDone=true;
						new searchActivityGlobally(1).execute();

					}
					else
					{
						layout_customadd.setVisibility(View.GONE);

						if(str.equalsIgnoreCase(""))
						{
							isSearchList=false;
							/*if(afterSchoolCategoriesByOwnerIDList!=null)
								afterSchoolCategoriesByOwnerIDList.getAfterSchoolCategoriesByOwnerID().clear();
							else
							{
								afterSchoolCategoriesByOwnerIDList=new  AfterSchoolCategoriesByOwnerIDList();
							}*/
							isSearchDone=true;
							listView.setVisibility(View.VISIBLE);
							if(afterSchoolCategoriesByOwnerIDList!=null && afterSchoolCategoriesByOwnerIDList.getAfterSchoolCategoriesByOwnerID().size()>0)
							{
								afterSchoolCategoriesAdapter=new AfterSchoolCategoriesAdapter(getActivity(),afterSchoolCategoriesByOwnerIDList.getAfterSchoolCategoriesByOwnerID());
								listView.setAdapter(afterSchoolCategoriesAdapter);
								afterSchoolCategoriesAdapter.notifyDataSetChanged();
								isSearchDone=false;
								//listView.setOnItemClickListener(AddAfterSchoolCategoriesFragment.this);
							}
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




		new GetCategoryName().execute();//get all categories data

		return view;		
	}

	private ProgressDialog progressDialog=null;	

	private class GetCategoryName extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialog = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				afterSchoolCategoriesByOwnerIDList =serviceMethod.getAfterSchoolCategoriesByOwnerID(0);
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
					new GetCategoryName().execute();

			}
			else
			{
				if(afterSchoolCategoriesByOwnerIDList!=null && afterSchoolCategoriesByOwnerIDList.getAfterSchoolCategoriesByOwnerID().size()>0)
				{
					afterSchoolCategoriesAdapter=new AfterSchoolCategoriesAdapter(getActivity(),afterSchoolCategoriesByOwnerIDList.getAfterSchoolCategoriesByOwnerID());
					listView.setAdapter(afterSchoolCategoriesAdapter);
					listView.setOnItemClickListener(AddAfterSchoolCategoriesFragment.this);
				}
				else
				{	
					//getError();
				}	
			}	
		}	
	}

	public static AddAfterSchoolCategoriesFragment addAfterSchoolCategoriesFragment;

	public static AddAfterSchoolCategoriesFragment getInstance()
	{
		if(addAfterSchoolCategoriesFragment==null)
		{
			addAfterSchoolCategoriesFragment = new AddAfterSchoolCategoriesFragment();
		}
		return addAfterSchoolCategoriesFragment;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) 
	{
		// TODO Auto-generated method stub
		if(!isSearchList)
		{
			AfterSchoolCategoriesByOwnerID model = (AfterSchoolCategoriesByOwnerID) listView.getAdapter().getItem(position);

			StaticVariables.categoryName = model.getName();
			StaticVariables.categoryId = model.getCategoryID();

			if(StaticVariables.fragmentIndexCurrentTabSchedular==22)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=27;
			}
			else if(StaticVariables.fragmentIndexCurrentTabSchedular==37)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=39;

			}
			else if(StaticVariables.fragmentIndexCurrentTabSchedular==23)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=47;

			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==17)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=64;

			}

			switchingFragments(new AddAfterSchoolCategoriesAndSubCategoriesFragment());
		}
		else 
		{

			hideKeyBoard();
			SearchActivitiesByActivityName modelSearch = (SearchActivitiesByActivityName) listView.getAdapter().getItem(position);


			StaticVariables.categoryName = modelSearch.getCategoryName();
			StaticVariables.categoryId = Integer.parseInt(modelSearch.getCategoryID());
			StaticVariables.subCategoryName = modelSearch.getSubCategoryName();
			StaticVariables.subCategoryId = Integer.parseInt(modelSearch.getSubCategoryID());
			StaticVariables.subSubCategoryName = modelSearch.getActivityName();
			StaticVariables.subSubCategoryId = Integer.parseInt(modelSearch.getActivityID());

			if(StaticVariables.fragmentIndexCurrentTabSchedular==22)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=83;
			}
			else if(StaticVariables.fragmentIndexCurrentTabSchedular==37)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=85;

			}
			else if(StaticVariables.fragmentIndexCurrentTabSchedular==23)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=82;

			}

			else if(StaticVariables.fragmentIndexCurrentTabSchedular==17)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=84;

			}
			switchingFragments(new AddAfterSchoolFragment());
		}
		/*getFragmentManager().beginTransaction().add(R.id.realtabcontent,
				new AddAfterSchoolCategoriesAndSubCategoriesFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();*/
	}

	private void getError()
	{
		Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());
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


	private ProgressDialog progressDialogSearch=null;	

	private class searchActivityGlobally extends AsyncTask<Void, Void, Integer>
	{

		int pageIndexSearch=1;
		public searchActivityGlobally(int i) {
			// TODO Auto-generated constructor stub
			pageIndexSearch=i;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			progressDialogSearch = ProgressDialog.show(getActivity(), "", StaticVariables.progressBarText, false);
			progressDialogSearch.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(getActivity()))
			{
				if(searchActivityList.getSearchActivitiesByActName().size()==0)
				{
					searchActivityList =serviceMethod.searchActivityByName(editsearch.getText().toString().trim());
					flag_loading=false;
				}
				/*else
				{
					SearchActivitiesByActivityNameList list=serviceMethod.getsearchFriendListGloballyList(editsearch.getText().toString().trim(), StaticVariables.currentParentId, pageIndexSearch, 8);
					if(list!=null && list.getSearchActivitiesByActName().size()>0)
					{
						flag_loading=false;
						searchActivityList.getSearchActivitiesByActName().addAll(list.getSearchActivitiesByActName());
					}


				}}*/
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
				if (progressDialogSearch.isShowing())
					progressDialogSearch.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(result==-1)
			{
				showMessage.showToastMessage("Please check your network connection");

				if(checkNetwork.checkNetworkConnection(getActivity()))
					new searchActivityGlobally(1).execute();

			}
			else
			{
				if(searchActivityList!=null && searchActivityList.getSearchActivitiesByActName().size()>0)
				{
					/*layout_nodata.setVisibility(View.GONE);
					noconnectionimage.setVisibility(View.GONE);
					noconnectiontext.setVisibility(View.GONE);
					listconnections_network.setVisibility(View.VISIBLE);*/
					listView.setVisibility(View.VISIBLE);
					layout_customadd.setVisibility(View.VISIBLE);


					if(pageIndexSearch==1)
					{
						searchAdapter=new SearchActivityAdapter(getActivity(),searchActivityList);
						listView.setAdapter(searchAdapter);
						searchAdapter.notifyDataSetChanged();
					}
					else
					{
						searchAdapter.notifyDataSetChanged();
						listView.invalidate();
					}

				}
				else
				{
					layout_customadd.setVisibility(View.GONE);
					listView.setVisibility(View.GONE);
					//getError();
				}	
			}	
			isSearchDone=false;
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


}
