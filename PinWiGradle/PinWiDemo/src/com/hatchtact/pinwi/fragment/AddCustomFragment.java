package com.hatchtact.pinwi.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.AddCustomActivity;
import com.hatchtact.pinwi.classmodel.AfterSchoolCategoriesByOwnerIDList;
import com.hatchtact.pinwi.classmodel.CategoriesAndSubCategoriesList;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.ParentProfile;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.Validation;
import com.hatchtact.pinwi.view.AutoCompleteAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AddCustomFragment extends ParentFragment implements OnTouchListener
{
	private View view;

	private int parentId=0;

	private SharePreferenceClass sharePreferenceClass=null;

	private ServiceMethod serviceMethod=null;
	private Gson gsonRegistration=null;
	private ShowMessages showMessage=null;
	private CheckNetwork checkNetwork=null;
	private ParentProfile parentCompleteInformation;

	private Validation validation=null;

	private EditText activityName_text=null;
	private TextView addcustom_text=null;
	private AutoCompleteTextView categoryName_text=null;
	private AutoCompleteTextView subcategoryName_text=null;
	private Button submit_custom_button=null;

	Activity mActivity;

	private int CategoryId=0;

	private ArrayList<String> afterSchoolCategoriesStringList=new ArrayList<String>();
	private ArrayList<String> categoriesStringList=new ArrayList<String>();

	private AfterSchoolCategoriesByOwnerIDList afterSchoolCategoriesByOwnerIDList;
	private CategoriesAndSubCategoriesList categoriesAndSubCategoriesList;

	private AddCustomActivity addCustomActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view=inflater.inflate(R.layout.activity_addcustom, container, false);

		setHasOptionsMenu(true);
		mListener.onFragmentAttached(false,"  Scheduler");

		mActivity=getActivity();

		activityName_text=(EditText) view.findViewById(R.id.activityName_text);
		categoryName_text= (AutoCompleteTextView) view.findViewById(R.id.categoryName_text);
		subcategoryName_text= (AutoCompleteTextView) view.findViewById(R.id.subcategoryName_text);
		submit_custom_button= (Button) view.findViewById(R.id.submit_custom_button);
		addcustom_text=(TextView) view.findViewById(R.id.addcustom_text);


		if(StaticVariables.fragmentIndexCurrentTabSchedular==83||StaticVariables.fragmentIndexCurrentTabSchedular==84||
				StaticVariables.fragmentIndexCurrentTabSchedular==82||StaticVariables.fragmentIndexCurrentTabSchedular==85)
		{

		}
		else if(StaticVariables.fragmentIndexCurrentTabSchedular==86||StaticVariables.fragmentIndexCurrentTabSchedular==87||
				StaticVariables.fragmentIndexCurrentTabSchedular==88||StaticVariables.fragmentIndexCurrentTabSchedular==89)
		{

		}
		else
		{
			categoryName_text.setText(StaticVariables.categoryName);
			subcategoryName_text.setText(StaticVariables.subCategoryName);
			CategoryId=StaticVariables.categoryId;
		}




		//new GetCategory("subCategory",CategoryId).execute();	

		addCustomActivity=new AddCustomActivity();

		sharePreferenceClass=new SharePreferenceClass(mActivity);
		checkNetwork=new CheckNetwork();
		showMessage=new ShowMessages(mActivity);
		serviceMethod=new ServiceMethod();
		gsonRegistration=new GsonBuilder().create();
		validation=new Validation();

		typeFace.setTypefaceRegular(activityName_text);
		typeFace.setTypefaceRegular(submit_custom_button);
		typeFace.setTypefaceRegular(addcustom_text);
		typeFace.setTypefaceRegular(categoryName_text);
		typeFace.setTypefaceRegular(subcategoryName_text);

		parentCompleteInformation = gsonRegistration.fromJson(sharePreferenceClass.getParentProfile(), ParentProfile.class);
		parentId=parentCompleteInformation.getParentID();

		categoryName_text.setOnTouchListener(this);
		subcategoryName_text.setOnTouchListener(this);



		categoryName_text.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				String textSelected = arg0.getItemAtPosition(arg2).toString();	
				int currentCategoryId = 0;

				for(int i=0;i<afterSchoolCategoriesByOwnerIDList.getAfterSchoolCategoriesByOwnerID().size();i++)
				{
					if(textSelected.trim().equalsIgnoreCase(afterSchoolCategoriesByOwnerIDList.getAfterSchoolCategoriesByOwnerID().get(i).getName().trim()))
					{
						currentCategoryId=afterSchoolCategoriesByOwnerIDList.getAfterSchoolCategoriesByOwnerID().get(i).getCategoryID();
					}

				}
				if(currentCategoryId==CategoryId)
				{
					System.out.println("current CtegoryId"+currentCategoryId+"previous id"+CategoryId);
				}
				else
				{
					new GetCategory("subCategory",currentCategoryId).execute();	
					CategoryId=currentCategoryId;
				}
			}
		});

		subcategoryName_text.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

			}
		});


		submit_custom_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub
				if(!validation.isNotNullOrBlank(activityName_text.getText().toString()))
					showMessage.showAlert("Incomplete Data", "You must enter a name to add a custom activity");
				else if(!validation.isNotNullOrBlank(activityName_text.getText().toString()) && !validation.isNotNullOrBlank(categoryName_text.getText().toString()))
					showMessage.showAlert("Incomplete Data", "You must enter a name and choose category/sub actegory to add a custom activity");
				else if(!validation.isNotNullOrBlank(categoryName_text.getText().toString()))
					showMessage.showAlert("Incomplete Data", "You must choose a category/sub category to add a custom activity");
				else if(!validation.isNotNullOrBlank(subcategoryName_text.getText().toString()))
					showMessage.showAlert("Incomplete Data", "You must choose a category/sub category to add a custom activity");
				else
				{
					String textSelectedSubCategory = subcategoryName_text.getText().toString();	

					for(int i=0;i<categoriesAndSubCategoriesList.getCategoriesAndSubCategories().size();i++)
					{
						if(textSelectedSubCategory.trim().equalsIgnoreCase(categoriesAndSubCategoriesList.getCategoriesAndSubCategories().get(i).getCategoryName().trim()))
						{
							addCustomActivity.setSubCategoryID(categoriesAndSubCategoriesList.getCategoriesAndSubCategories().get(i).getCategoryID());	
						}
					}

					addCustomActivity.setName(activityName_text.getText().toString());
					addCustomActivity.setParentID(parentId);

					new AddNewCustomActivity().execute();
				}

			}
		});
		//new GetCategory("category",0).execute();
		new DefaultAsyncTask().execute();

		return view;		
	}

	private class AddNewCustomActivity extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(mActivity))
			{
				ErrorCode =serviceMethod.AddCustomActivity(addCustomActivity);
			}
			else  
			{
				ErrorCode=-1;
			}

			return ErrorCode;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = ProgressDialog.show(mActivity, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected void onPostExecute(Integer result) {
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

			}
			else
			{				
				//getError();
				getErrorCustom();
			}			
		}	
	}
	private ProgressDialog progressDialog=null;	

	private class GetCategory extends AsyncTask<Void, Void, Integer>
	{
		private String getWebservice;
		private int categoryID ;

		public GetCategory(String getWebservice, int categoryId)
		{
			// TODO Auto-generated constructor stub 
			this.getWebservice = getWebservice;
			this.categoryID = categoryId;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = ProgressDialog.show(mActivity, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(mActivity))
			{
				if(getWebservice.equalsIgnoreCase("category"))
				{
					afterSchoolCategoriesByOwnerIDList= serviceMethod.getAfterSchoolCategoriesByOwnerID(0);

					if(afterSchoolCategoriesByOwnerIDList!=null)
					{
						afterSchoolCategoriesStringList = getCategoryStringList();
					}
					else
					{

					}
				}

				else
				{
					categoriesAndSubCategoriesList = serviceMethod.getCategoriesAndSubCategories(categoryID);

					if(categoriesAndSubCategoriesList!=null)
					{
						categoriesStringList = getSubCategoryStringList();
					}
					else
					{

					}
				}
			}
			else
			{
				ErrorCode=-1;
			}
			return ErrorCode;
		}


		@Override
		protected void onPostExecute(Integer result) {
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
			}
			else
			{
				if(getWebservice.equalsIgnoreCase("category"))
				{
					if(afterSchoolCategoriesStringList!=null)
					{
						if(afterSchoolCategoriesStringList!=null && afterSchoolCategoriesStringList.size()>0)
						{
							AutoCompleteAdapter   checkUpAdapter = new AutoCompleteAdapter(mActivity, R.layout.list_item, R.id.item,afterSchoolCategoriesStringList);
							categoryName_text.setAdapter(checkUpAdapter);
							categoryName_text.setValidator(new ValidateText(afterSchoolCategoriesStringList,0));
						}
						else
						{

						}
					}
					else
					{	
						getError();
						new GetCategory("category",0).execute();
					}	
				}
				else
				{
					if(categoriesStringList!=null)
					{
						if(categoriesStringList!=null && categoriesStringList.size()>0)
						{
							AutoCompleteAdapter checkUpAdapter = new AutoCompleteAdapter(mActivity, R.layout.list_item, R.id.item,categoriesStringList);
							subcategoryName_text.setAdapter(checkUpAdapter);
							subcategoryName_text.setText("");
							subcategoryName_text.setValidator(new ValidateText(categoriesStringList,1));
						}
					}
					else
					{	
						getError();
						new GetCategory("subCategory",categoryID).execute();	
					}	
				}	
			}
		}	
	}

	class ValidateText implements AutoCompleteTextView.Validator
	{
		private ArrayList<String> arrayObj;

		private int autoCompleteTextView;

		public ValidateText(ArrayList<String> arrayOfObjectsToValidate, int autoCompleteTextView) {
			this.arrayObj = arrayOfObjectsToValidate;
			this.autoCompleteTextView=autoCompleteTextView;
		}
		public boolean isValid(CharSequence text) {
			if (arrayObj.contains(text.toString())) {
				setFocuesToNextView();
				System.out.println("in validation caheck");
				return true;
			}
			return false;
		}

		private void setFocuesToNextView()
		{
			// TODO Auto-generated method stub
			if(autoCompleteTextView==0)
			{
				subcategoryName_text.setFocusable(true);
			}
			else
			{
				submit_custom_button.setFocusable(true);
			}
		}

		public CharSequence fixText(CharSequence invalidText) 
		{
			Log.v("Test", "Returning fixed text");
			System.out.println(invalidText+"  text in change");
			return "";
		}
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		// TODO Auto-generated method stub

		if(event.getAction()==MotionEvent.ACTION_UP)
		{
			if(view instanceof AutoCompleteTextView)

				((AutoCompleteTextView) view).showDropDown();
		}

		else if(event.getAction()==MotionEvent.ACTION_DOWN)
			hideKeyBoard();

		return false;
	}

	private void hideKeyBoard()
	{
		try  
		{
			mActivity.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

			InputMethodManager inputManager = (InputMethodManager) mActivity
					.getSystemService(Context.INPUT_METHOD_SERVICE);

			inputManager.hideSoftInputFromWindow(mActivity
					.getCurrentFocus().getWindowToken(), 0);

			mActivity.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		} 
		catch (Exception e) 
		{

		}
	}

	private void getError()
	{
		Error err = serviceMethod.getError();	
		showMessage.showAlert("Warning", err.getErrorDesc());
	}

	private void getErrorCustom()
	{
		/*Error err = serviceMethod.getError();	
		showMessage.showAlertAndMove("Warning", err.getErrorDesc(),"FromAddCustom",getFragmentManager());*/
		/*if(StaticVariables.fragmentIndexCurrentTabSchedular==27)
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
		 */
		if(StaticVariables.fragmentIndexCurrentTabSchedular==83)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=22;
			switchingFragments(new AddAfterSchoolCategoriesFragment());
		}
		else if(StaticVariables.fragmentIndexCurrentTabSchedular==85)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=37;
			switchingFragments(new AddAfterSchoolCategoriesFragment());
		}
		else if(StaticVariables.fragmentIndexCurrentTabSchedular==82)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=23;
			switchingFragments(new AddAfterSchoolCategoriesFragment());
		}

		else if(StaticVariables.fragmentIndexCurrentTabSchedular==84)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=17;
			switchingFragments(new AddAfterSchoolCategoriesFragment());
		}
		
		if(StaticVariables.fragmentIndexCurrentTabSchedular==87)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=27;
			switchingFragments(new AddAfterSchoolCategoriesAndSubCategoriesFragment());
		}
		else if(StaticVariables.fragmentIndexCurrentTabSchedular==89)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=39;
			switchingFragments(new AddAfterSchoolCategoriesAndSubCategoriesFragment());
		}
		else if(StaticVariables.fragmentIndexCurrentTabSchedular==86)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=47;
			switchingFragments(new AddAfterSchoolCategoriesAndSubCategoriesFragment());
		}

		else if(StaticVariables.fragmentIndexCurrentTabSchedular==88)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=64;
			switchingFragments(new AddAfterSchoolCategoriesAndSubCategoriesFragment());
		}

		
		if(StaticVariables.fragmentIndexCurrentTabSchedular==34)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=31;
			switchingFragments(new AddAfterSchoolByCatIDFragment());
		}
		else if(StaticVariables.fragmentIndexCurrentTabSchedular==41)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=40;
			switchingFragments(new AddAfterSchoolByCatIDFragment());
		}

		else if(StaticVariables.fragmentIndexCurrentTabSchedular==49)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=48;
			switchingFragments(new AddAfterSchoolByCatIDFragment());
		}
		else if(StaticVariables.fragmentIndexCurrentTabSchedular==66)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=65;
			switchingFragments(new AddAfterSchoolByCatIDFragment());
		}

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






	private class DefaultAsyncTask extends AsyncTask<Void, Void, Integer>
	{

		public DefaultAsyncTask()
		{
			// TODO Auto-generated constructor stub 

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			try {
				progressDialog = ProgressDialog.show(mActivity, "", StaticVariables.progressBarText, false);
				progressDialog.setCancelable(false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected Integer doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			int ErrorCode=0;

			if(checkNetwork.checkNetworkConnection(mActivity))
			{						{
				afterSchoolCategoriesByOwnerIDList= serviceMethod.getAfterSchoolCategoriesByOwnerID(0);

				if(afterSchoolCategoriesByOwnerIDList!=null)
				{
					afterSchoolCategoriesStringList = getCategoryStringList();
				}

				categoriesAndSubCategoriesList = serviceMethod.getCategoriesAndSubCategories(StaticVariables.categoryId);

				if(categoriesAndSubCategoriesList!=null)
				{
					categoriesStringList = getSubCategoryStringList();
				}
				else
				{

				}
			}
			}
			else
			{
				ErrorCode=-1;
			}
			return ErrorCode;
		}


		@Override
		protected void onPostExecute(Integer result) {
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
			}
			else
			{

				if(afterSchoolCategoriesStringList!=null)
				{
					if(afterSchoolCategoriesStringList!=null && afterSchoolCategoriesStringList.size()>0)
					{
						AutoCompleteAdapter   checkUpAdapter = new AutoCompleteAdapter(mActivity, R.layout.list_item, R.id.item,afterSchoolCategoriesStringList);
						categoryName_text.setAdapter(checkUpAdapter);
						categoryName_text.setValidator(new ValidateText(afterSchoolCategoriesStringList,0));
					}
					else
					{

					}
				}


				{
					if(categoriesStringList!=null)
					{
						if(categoriesStringList!=null && categoriesStringList.size()>0)
						{
							AutoCompleteAdapter checkUpAdapter = new AutoCompleteAdapter(mActivity, R.layout.list_item, R.id.item,categoriesStringList);
							subcategoryName_text.setAdapter(checkUpAdapter);
							subcategoryName_text.setValidator(new ValidateText(categoriesStringList,1));
						}
					}

				}	
			}
		}	
	}

	private ArrayList<String> getCategoryStringList() {
		// TODO Auto-generated method stub
		afterSchoolCategoriesStringList=new ArrayList<String>();

		for(int i=0;i<afterSchoolCategoriesByOwnerIDList.getAfterSchoolCategoriesByOwnerID().size();i++)
		{
			afterSchoolCategoriesStringList.add(afterSchoolCategoriesByOwnerIDList.getAfterSchoolCategoriesByOwnerID().get(i).getName());
		}

		return afterSchoolCategoriesStringList;
	}

	private ArrayList<String> getSubCategoryStringList() {
		// TODO Auto-gen																																																																																																														erated method stub

		categoriesStringList=new ArrayList<String>();

		for(int i=0;i<categoriesAndSubCategoriesList.getCategoriesAndSubCategories().size();i++)
		{
			categoriesStringList.add(categoriesAndSubCategoriesList.getCategoriesAndSubCategories().get(i).getCategoryName());
		}

		return categoriesStringList;
	}


}
