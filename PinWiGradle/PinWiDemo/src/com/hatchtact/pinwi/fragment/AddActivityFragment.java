package com.hatchtact.pinwi.fragment;

import java.util.ArrayList;

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
import com.hatchtact.pinwi.adapter.AddNewActivityCalenderAdapter;
import com.hatchtact.pinwi.classmodel.AddActivityCalender;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;
import com.hatchtact.pinwi.utility.StaticVariables;

public class AddActivityFragment extends ParentFragment implements OnItemClickListener
{
	private View view;

	private AddNewActivityCalenderAdapter addnewActivityCalenderAdapter=null;

	private ListView listView=null;

	private ArrayList<AddActivityCalender> listDataValue=null;

	private String data[]={"At School","After School"};
	
	private TextView addnewActivity_text=null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view=inflater.inflate(R.layout.add_schoolafterholiday, container, false);
		
		setHasOptionsMenu(true);
		mListener.onFragmentAttached(false,"  Scheduler");
	
		AddAfterSchoolFragment.updatedDataFromAfterSchool = 0;
		StaticVariables.fragmentIndex=2;
		
		listView=(ListView) view.findViewById(R.id.listitem_addnew);
		addnewActivity_text=(TextView) view.findViewById(R.id.addnewActivity_text);
		
		typeFace.setTypefaceRegular(addnewActivity_text);

		listDataValue=new ArrayList<AddActivityCalender>();


		for(int i=0;i<2;i++)
		{
			AddActivityCalender addActivityCalender=new AddActivityCalender();
			addActivityCalender.setActivity_name(data[i]);
			listDataValue.add(addActivityCalender);	
		}

		addnewActivityCalenderAdapter=new AddNewActivityCalenderAdapter(getActivity(), listDataValue);
		listView.setAdapter(addnewActivityCalenderAdapter);
		listView.setOnItemClickListener(this);

		return view;		
	}
	
	public static AddActivityFragment addActivityFragment;

	public static AddActivityFragment getInstance()
	{
		if(addActivityFragment==null)
		{
			addActivityFragment = new AddActivityFragment();
		}
		return addActivityFragment;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub

		switch (position) 
		{
		case 0:
			if(StaticVariables.fragmentIndexCurrentTabSchedular==14)//by calender date
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=21;
			}
			else if(StaticVariables.fragmentIndexCurrentTabSchedular==18)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=36;
			}
			switchingFragments(new AddSubjectFragment());
			/*getFragmentManager().beginTransaction().add(R.id.realtabcontent,
					new AddSubjectFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();*/
			break;

		case 1:
			if(StaticVariables.fragmentIndexCurrentTabSchedular==18)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=37;
			}
			else if(StaticVariables.fragmentIndexCurrentTabSchedular==14)
			{
				StaticVariables.fragmentIndexCurrentTabSchedular=22;
			}
			
			switchingFragments(new AddAfterSchoolCategoriesFragment());
			/*getFragmentManager().beginTransaction().add(R.id.realtabcontent,
					new AddAfterSchoolCategoriesFragment()).addToBackStack(StaticVariables.fragmentIndex+"").commit();*/
			break;
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
}
