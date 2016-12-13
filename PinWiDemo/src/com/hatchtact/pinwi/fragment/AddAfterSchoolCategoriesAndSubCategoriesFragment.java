package com.hatchtact.pinwi.fragment;

import android.app.ProgressDialog;
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

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.AddAfterSchoolSubCategoriesAdapter;
import com.hatchtact.pinwi.classmodel.CategoriesAndSubCategories;
import com.hatchtact.pinwi.classmodel.CategoriesAndSubCategoriesList;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;

public class AddAfterSchoolCategoriesAndSubCategoriesFragment extends ParentFragment implements OnItemClickListener
{
	private View view;
	
	private ShowMessages showMessage=null;  
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;

	private CategoriesAndSubCategoriesList categoriesAndSubCategoriesList;

	private AddAfterSchoolSubCategoriesAdapter addAfterSchoolSubCategoriesAdapter=null;

	private ListView listView=null;
	private TextView category_text=null;
	private TextView afterschoolSubCatActivity_text=null;

	/*String subcategoryName=null;
	int subcategoryId=0;*/
	
	//private EditText editsearch;
	//private ImageView imageSearch;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view=inflater.inflate(R.layout.activity_afterschool_subcategorieslist, container, false);
		setHasOptionsMenu(true);
		mListener.onFragmentAttached(false,"  Scheduler");
		StaticVariables.fragmentIndex=6;
		
		categoriesAndSubCategoriesList=new CategoriesAndSubCategoriesList();
		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(getActivity());
		checkNetwork=new CheckNetwork();
		
		/*subcategoryName = getArguments().getString("CategoryName");  
		subcategoryId=getArguments().getInt("CategoryId");
*/
		listView=(ListView) view.findViewById(R.id.containallCatandSubCat_list);
		category_text=(TextView) view.findViewById(R.id.afterschoolSubCategory_text);
		afterschoolSubCatActivity_text=(TextView) view.findViewById(R.id.afterschoolSubCatActivity_text);
		
		typeFace.setTypefaceRegular(category_text);
		typeFace.setTypefaceRegular(afterschoolSubCatActivity_text);
		
		category_text.setText(StaticVariables.categoryName);
		
		/*imageSearch=(ImageView) view.findViewById(R.id.imageSearch);
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
				if(categoriesAndSubCategoriesList!=null)
				{
					if(categoriesAndSubCategoriesList.getCategoriesAndSubCategories().size()>0)
					{
						addAfterSchoolSubCategoriesAdapter.getFilter().filter(cs.toString()); 
						addAfterSchoolSubCategoriesAdapter.notifyDataSetChanged();
						listView.invalidate();
					}
				}
			}
		});*/
		
		
		new GetCategoryName().execute();
		
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
				categoriesAndSubCategoriesList =serviceMethod.getCategoriesAndSubCategories(StaticVariables.categoryId);
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
				if(categoriesAndSubCategoriesList!=null && categoriesAndSubCategoriesList.getCategoriesAndSubCategories().size()>0)
				{
					addAfterSchoolSubCategoriesAdapter=new AddAfterSchoolSubCategoriesAdapter(getActivity(),categoriesAndSubCategoriesList.getCategoriesAndSubCategories());
					listView.setAdapter(addAfterSchoolSubCategoriesAdapter);
					listView.setOnItemClickListener(AddAfterSchoolCategoriesAndSubCategoriesFragment.this);
				}
				else
				{	
					//getError();
				}	
			}	
		}	
	}

	public static AddAfterSchoolCategoriesAndSubCategoriesFragment addAfterSchoolCategoriesAndSubCategoriesFragment;

	public static AddAfterSchoolCategoriesAndSubCategoriesFragment getInstance()
	{
		if(addAfterSchoolCategoriesAndSubCategoriesFragment==null)
		{
			addAfterSchoolCategoriesAndSubCategoriesFragment = new AddAfterSchoolCategoriesAndSubCategoriesFragment();
		}
		return addAfterSchoolCategoriesAndSubCategoriesFragment;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub

		CategoriesAndSubCategories model = (CategoriesAndSubCategories) listView.getAdapter().getItem(position);

		StaticVariables.subCategoryName = model.getCategoryName();
		StaticVariables.subCategoryId = model.getCategoryID();

		/*bundle.putString("SubCategoryName", subcategoryName);
			bundle.putInt("SubCategoryId", subcategoryId);
			addAfterSchoolByCatIDFragment.setArguments(bundle);
		 */
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
		else if(StaticVariables.fragmentIndexCurrentTabSchedular==64)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=65;

		}


		switchingFragments(new AddAfterSchoolByCatIDFragment());
		/*getFragmentManager().beginTransaction().add(R.id.realtabcontent,
					new AddAfterSchoolByCatIDFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();*/
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
}
