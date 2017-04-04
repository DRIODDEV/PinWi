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

public class InsightsErrorDummyFragment extends ParentFragment implements OnClickListener
{

	private View view;
	public static InsightsErrorDummyFragment insightfragment;
	private Button btnDashboard;
	private TextView notenoughdata;
	private TextView texterrorinsightfragment;
	//private String errorText="Insights are based on data received from you and your children. You use the Schdueler in the app to schedule and plan your child's activities and your children use their personal login ins to rate these activities everyday on a scale of happiness.\nOur research backed Interest Mapping Model examines children's activity rating data to draw insights into what drives their interests. The more data our Mapping Model has to work with, the more reliable the realistic and reliable Insights are.\nIf your child rated 5 activities he or she does at school everyday and 3 activities he or she does after school in a week, this report will activate in approximately 24 days. Go ahead, add more activities and encorurage your child to rate regularly.";

	//private String errorText="Insights are based on data received from you and your children - in the form of activities you plan for your children and the activity ratings they give to these activities everyday.Once the app captures a critical amount of activity ratings from children,the Insights will be available for you to view your children's Interest Mapping Reports.\n\nWe recommend you input at least 5 school subjects and 3 after school activities for your children to rate everyday to unlock their individual report in about 24 days.The Insights will automatically be refreshed weekly post that.\n\nThe more data our Interest Mapping Model receives,the more realistic and reliable Insights will be. So go ahead, add more activities and encourage your child to rate regularly..\n\n";
	private String errorText="Insights are generated using data we receive from you and your child. The more activities you add and your child rates, the more quickly Insights will be activated.\n\nWe recommend you add up to 5 school subjects and 3 after school activities for your child to activate Insights in 7 days. Remember to encourage your child to rate everyday.\n\n";
	//private String errorText="Insights are based on data received from you and\nyour children - in the form of activities you plan for\nyour children and the activity ratings they give\nto these activities everyday.Once the app captures\na critical amount of activity ratings from children,\nthe Insights will be available for you to view your\nchildren?s Interest Mapping Reports.We recommend you input at least 5 school subjects\nand 3 after school activities for your children to rate\neveryday to unlock their individual report in about 24\ndays. The Insights will automatically be refreshed\nweekly post that.The more data our Interest Mapping Model receives,\nthe more realistic and reliable Insights will be. So go\nahead, add more activities and encourage your child\nto rate regularly..";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		view=inflater.inflate(R.layout.insights_error_dummy_fragment, container, false);
		mListener.onFragmentAttached(false,"  Insights");
		setHasOptionsMenu(true);


		btnDashboard=(Button)view.findViewById(R.id.btndashboard);
		btnDashboard.setText("View Sample Report");
		btnDashboard.setOnClickListener(this);
		typeFace.setTypefaceRegular(btnDashboard);
		notenoughdata=(TextView) view.findViewById(R.id.notenoughdata);
		texterrorinsightfragment=(TextView) view.findViewById(R.id.texterrorinsightfragment);
		notenoughdata.setText("Activate Insights in 7 days");
		typeFace.setTypefaceLight(texterrorinsightfragment);
		typeFace.setTypefaceBold(notenoughdata);
		texterrorinsightfragment.setText(errorText);


		return view;
	}



	public static InsightsErrorDummyFragment getInstance()
	{
		if(insightfragment==null)
		{
			insightfragment = new InsightsErrorDummyFragment();
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
