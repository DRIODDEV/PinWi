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

public class AddCustomFragmentSchool extends ParentFragment
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
		subcategoryName_text.setVisibility(View.GONE);
		view.findViewById(R.id.line_belowsubcategoryName).setVisibility(View.GONE);
		categoryName_text.setText("School Activity");
		categoryName_text.setAlpha(.5f);
		subcategoryName_text.setEnabled(false);
		categoryName_text.setEnabled(false);
		submit_custom_button= (Button) view.findViewById(R.id.submit_custom_button);
		addcustom_text=(TextView) view.findViewById(R.id.addcustom_text);

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


		submit_custom_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub
				if(!validation.isNotNullOrBlank(activityName_text.getText().toString()))
					showMessage.showAlert("Incomplete Data", "You must enter a name to add a custom activity");
				else
				{

					addCustomActivity.setSubCategoryID(1);
					addCustomActivity.setName(activityName_text.getText().toString());
					addCustomActivity.setParentID(parentId);
					new AddNewCustomActivity().execute();
				}

			}
		});

		return view;
	}

	//private ProgressDialog progressDialog;
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
			/*progressDialog = ProgressDialog.show(mActivity, "", StaticVariables.progressBarText, false);
			progressDialog.setCancelable(false);*/
			if(customProgressLoader!=null)
			{
				customProgressLoader.startHandler();
			}
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {
				/*if (progressDialog.isShowing())
					progressDialog.cancel();*/
				try {
					customProgressLoader.removeCallbacksHandler();
				/*if (progressDialog.isShowing())
					progressDialog.cancel();*/
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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


	private void getErrorCustom()
	{
		StaticVariables.fragmentIndexFrequencyPage=0;
		switchingFragments(new AddSubjectFragment());
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
