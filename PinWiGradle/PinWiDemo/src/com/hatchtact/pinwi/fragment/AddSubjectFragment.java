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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.SubjectNameAdapter;
import com.hatchtact.pinwi.classmodel.Error;
import com.hatchtact.pinwi.classmodel.SubjectActivities;
import com.hatchtact.pinwi.classmodel.SubjectActivitiesList;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.StaticVariables;

public class AddSubjectFragment extends ParentFragment implements OnItemClickListener
{
	private View view;

	private ShowMessages showMessage=null;  
	private ServiceMethod serviceMethod=null;
	private CheckNetwork checkNetwork=null;

	private SubjectActivitiesList subjectActivityList;

	private SubjectNameAdapter subjectNameAdapter=null;

	private ListView listView=null;
	private TextView schoolActivity_text=null;

	String subjectName=null;
	
	int activityId=0;
	private ImageView imageSearch;
	private EditText editsearch;

	//public static  String filterString="";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view=inflater.inflate(R.layout.activity_subjectnamelist, container, false);
		setHasOptionsMenu(true);
		mListener.onFragmentAttached(false,"  Scheduler");

		StaticVariables.fragmentIndex=3;
	//	filterString="";
		
		subjectActivityList=new SubjectActivitiesList();
		serviceMethod=new ServiceMethod();
		showMessage=new ShowMessages(getActivity());
		checkNetwork=new CheckNetwork();

		listView=(ListView) view.findViewById(R.id.containallname_list);
		schoolActivity_text=(TextView) view.findViewById(R.id.schoolActivity_text);
		
		typeFace.setTypefaceRegular(schoolActivity_text);

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
				if(subjectActivityList!=null)
				{
					if(subjectActivityList.getSubjectActivitiesList().size()>0)
					{
						//filterString=cs.toString().trim();
						subjectNameAdapter.getFilter().filter(cs.toString()); 
						subjectNameAdapter.notifyDataSetChanged();
						listView.invalidate();
					}
				}
			}
		});

		new GetSubjectName().execute();

		return view;		
	}

	private ProgressDialog progressDialog=null;	

	private class GetSubjectName extends AsyncTask<Void, Void, Integer>
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
				subjectActivityList =serviceMethod.getSubjectActivities();
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
					new GetSubjectName().execute();

			}
			else
			{
				if(subjectActivityList!=null && subjectActivityList.getSubjectActivitiesList().size()>0)
				{
					subjectNameAdapter=new SubjectNameAdapter(getActivity(), subjectActivityList.getSubjectActivitiesList());
					listView.setAdapter(subjectNameAdapter);
					listView.setOnItemClickListener(AddSubjectFragment.this);
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

	public static AddSubjectFragment addSubjectFragment;

	public static AddSubjectFragment getInstance()
	{
		if(addSubjectFragment==null)
		{
			addSubjectFragment = new AddSubjectFragment();
		}
		return addSubjectFragment;
	}

	AddSchoolFragment addSchoolFragment=new AddSchoolFragment();


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();

		SubjectActivities model = (SubjectActivities) listView.getAdapter().getItem(position);
		subjectName=model.getSubjectName();
		activityId = model.getSubjectID();
		/*subjectName = subjectActivityList.getSubjectActivitiesList().get(position).getSubjectName();
		activityId = subjectActivityList.getSubjectActivitiesList().get(position).getSubjectID();*/

		bundle.putString("SubjectName", subjectName);
		bundle.putInt("ActivityId", activityId);
		bundle.putString("ComingFromWhichScreen", "addSchoolActivity");
		addSchoolFragment.setArguments(bundle);

		if(StaticVariables.fragmentIndexCurrentTabSchedular==21)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=30;
		}
		else if(StaticVariables.fragmentIndexCurrentTabSchedular==36)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=38;
		}
		
		else if(StaticVariables.fragmentIndexCurrentTabSchedular==25)
		{
			StaticVariables.fragmentIndexCurrentTabSchedular=63;
		}
		
		switchingFragments(addSchoolFragment);
		/*getFragmentManager().beginTransaction().add(R.id.realtabcontent,
				addSchoolFragment).addToBackStack(StaticVariables.fragmentIndex+"").commit();*/
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
