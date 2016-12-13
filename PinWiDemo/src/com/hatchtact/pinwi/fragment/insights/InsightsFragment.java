package com.hatchtact.pinwi.fragment.insights;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.utility.StaticVariables;

public class InsightsFragment extends ParentFragment implements OnClickListener 
{
	
	private View view;
	private ListView listView=null;
	private ListInsightsBenefitsAdapter adapterBenefitsInsight;
	private ArrayList<String> benefitsInsights=new ArrayList<String>();
	public static InsightsFragment insightfragment;
	private Button btnContinue;


	
    private TextView txtBenefits;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view=inflater.inflate(R.layout.insights_benefitsfragment, container, false);
		mListener.onFragmentAttached(false,"  Insights");
		setHasOptionsMenu(true);
		//getActivity().invalidateOptionsMenu();
		listView=(ListView) view.findViewById(R.id.list_insight_fragmentbenefits);
		benefitsInsights.add("vsdfsdfvsdfvsdvcsdvsdfvcsdvcsdvcsdvc");
		benefitsInsights.add("vsdfsdfvsdfvsdvcsdvsdfvcsdvcsdvcsdvc");
		benefitsInsights.add("vsdfsdfvsdfvsdvcsdvsdfvcsdvcsdvcsdvc");
		benefitsInsights.add("vsdfsdfvsdfvsdvcsdvsdfvcsdvcsdvcsdvc");
		benefitsInsights.add("vsdfsdfvsdfvsdvcsdvsdfvcsdvcsdvcsdvc");
		benefitsInsights.add("vsdfsdfvsdfvsdvcsdvsdfvcsdvcsdvcsdvc");
		adapterBenefitsInsight=new ListInsightsBenefitsAdapter(getActivity(), benefitsInsights);
		listView.setAdapter(adapterBenefitsInsight);
		txtBenefits=(TextView) view.findViewById(R.id.textinsightfragment1);
		
		typeFace.setTypefaceBold(txtBenefits);
		btnContinue=(Button)view.findViewById(R.id.continuebtninsights);
		btnContinue.setOnClickListener(this);
		typeFace.setTypefaceRegular(btnContinue);

		return view;		
	}






	


	public static InsightsFragment getInstance()
	{
		if(insightfragment==null)
		{
			insightfragment = new InsightsFragment();
		}
		return insightfragment;
	}









	@Override
	public void onClick(View v)
	{

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
