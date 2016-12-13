package com.hatchtact.pinwi.fragment.insights;

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
import com.hatchtact.pinwi.classmodel.GetDelightTraitsByChildIDOnInsight;
import com.hatchtact.pinwi.utility.StaticVariables;

public class DelightTrendsFragment extends ParentFragment implements OnItemClickListener 
{
	private View view;
	private ListView listView=null;
	private DelightTrendsAdapter adapterDelightTrendsInsight;
	public static DelightTrendsFragment delightTrendsFragment;
	private TextView txtDelight;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view=inflater.inflate(R.layout.listviewdelighttrends, container, false);
		mListener.onFragmentAttached(false," Insights");
		setHasOptionsMenu(true);
		StaticVariables.fragmentIndexCurrentTabInsight=2;
		listView=(ListView) view.findViewById(R.id.listDelightTrends);
		adapterDelightTrendsInsight=new DelightTrendsAdapter(getActivity());
		listView.setAdapter(adapterDelightTrendsInsight);
		txtDelight=(TextView) view.findViewById(R.id.listDelightTrends_title);
		typeFace.setTypefaceRegular(txtDelight);
		
		listView.setOnItemClickListener(DelightTrendsFragment.this);
		
		return view;		
	}









	public static DelightTrendsFragment getInstance()
	{
		if(delightTrendsFragment==null)
		{
			delightTrendsFragment = new DelightTrendsFragment();
		}
		return delightTrendsFragment;
	}









	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id)
	{
		// TODO Auto-generated method stub
		/*GetDelightTraitsByChildIDOnInsight model = TypesInsightsFragment.getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight().get(position);

		StaticVariables.ActivityIdInsight=model.getActivityID();
		
		StaticVariables.fragmentIndexCurrentTabInsight=6;

		switchingFragments(new DelightTrendsByActivityFragment());*/
		
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
