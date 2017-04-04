package com.hatchtact.pinwi.fragment.insights;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hatchtact.pinwi.ActivityAboutUS;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.utility.StaticVariables;

public class InsightsErrorDetailFragment extends ParentFragment implements OnClickListener 
{

	private View view;
	public static InsightsErrorDetailFragment insightfragment;
	private Button btnDashboard;
	private TextView notenoughdata;
	private TextView texterrorinsightfragment;
	private TextView headingdata;
	/*private String errorText=
			"Insights are generated using data we receive from you and your child. The more activities you add and your child rates, the more quickly Insights will be activated.\n\nWe recommend you add up to 5 school subjects and 3 after school activities for your child to activate Insights in 7 days. Remember to encourage your child to rate everyday.\n\n";
	 */
	private String errorText=
					"1. Add at least 5 subjects and 3 after school activities for your child.\n\n"+
					"2. Include hobbies and leisure time activities in this list too.\n\n"+
					"3. Remember to encourage your child to rate these activities every evening.\n\n"+
					"4. The more activities for children to rate, the faster Insights will unlock.\n\n";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view=inflater.inflate(R.layout.insights_error_detail_fragment, container, false);
		mListener.onFragmentAttached(false,"  Insights");
		setHasOptionsMenu(true);


		btnDashboard=(Button)view.findViewById(R.id.btndashboard);
		btnDashboard.setText("View Sample Report");
		btnDashboard.setOnClickListener(this);
		typeFace.setTypefaceRegular(btnDashboard);
		headingdata=(TextView) view.findViewById(R.id.headingdata);
		typeFace.setTypefaceLight(headingdata);

		notenoughdata=(TextView) view.findViewById(R.id.notenoughdata);
		texterrorinsightfragment=(TextView) view.findViewById(R.id.texterrorinsightfragment);
		notenoughdata.setText("Unlock Insights in 7 days or less");
		typeFace.setTypefaceLight(texterrorinsightfragment);
		typeFace.setTypefaceBold(notenoughdata);
		texterrorinsightfragment.setText(errorText);


		return view;		
	}



	public static InsightsErrorDetailFragment getInstance()
	{
		if(insightfragment==null)
		{
			insightfragment = new InsightsErrorDetailFragment();
		}
		return insightfragment;
	}


	@Override
	public void onClick(View v)
	{

		Intent intentAboutUs =new Intent(getActivity(), ActivityAboutUS.class);
		startActivity(intentAboutUs);
		StaticVariables.webUrl="http://demo.pinwi.in/Insight_Report.pdf";
		/*if (getFragmentManager().getBackStackEntryCount() > 0) {
			getFragmentManager().popBackStack();
		} else {
			getActivity().finish();
		}*/
	}



	// the create options menu with a MenuInflater to have the menu from your fragment
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
		for(int i=0;i<menu.size();i++)
		{
			if(menu.getItem(i).getItemId()!=R.id.action_childName)
				menu.getItem(i).setVisible(true);
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
		else if(item.getItemId()!=R.id.action_childName)
		{
			StaticVariables.currentChild=StaticVariables.childInfo.get(item.getItemId());
			getActivity().invalidateOptionsMenu();
			switchingFragments(new TypesInsightsFragment());
		}
		return super.onOptionsItemSelected(item);
	}
}
