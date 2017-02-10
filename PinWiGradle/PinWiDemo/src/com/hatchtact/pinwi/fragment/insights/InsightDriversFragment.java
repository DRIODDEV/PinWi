package com.hatchtact.pinwi.fragment.insights;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.GetInterestPatternByChildIDOnInsight;
import com.hatchtact.pinwi.classmodel.GetInterestTraitsByChildIDOnInsight;
import com.hatchtact.pinwi.utility.StaticVariables;


public class InsightDriversFragment extends ParentFragment
{
	private ListView gridView_InterestDrivers;
	private ArrayList<GetInterestTraitsByChildIDOnInsight> getInterestTraitsByChildIDOnInsightListForParticularBucketId;
	private ArrayList<GetInterestPatternByChildIDOnInsight> getInterestPatternByChildIDOnInsightList;

	private InterestDriversAdapterGridView adapter;

	private InterestPatternAdapterGridView adapterPattern;

	private Context mContext;

	private TextView interestDriverSpecificName;
	private TextView interestDriverSpecificDetail;

	private final String[] arrayInterestDrivers={"Exhilarators   ",
			"Amusers        ",
			"Unexciting     ",
			"Non-Influencers",
	"Unexplored     "};

	private final String[] arrayInterestDriverDescription={"Exhilarators are key Interest Drivers common across activities that make your child most happy. These are the primary building blocks of activities your child consistently rates higher on the scale.",
			"Amusers are key Interest Drivers common across activities that keep your child amused and occupied. These are the primary building blocks of activities your child consistently rates medium on the scale.", 
			"Unexciting are key Interest Drivers common across activities that are not in your child's good books. These are the primary building blocks of activities your child consistently rates lower on the scale.", 
			"Non-influencers are Interest Drivers that are essential but not influential in defining your child's interests. These are secondary building blocks recurring across all the activities that your child does.",
	"Unexplored are Interest Drivers or building blocks present in categories of activities your child has never been exposed to. "};


	private final String[] arrayInterestPattern={"Long Held",
			"New Found",
	"SEE-SAW"};


	private final String[] arrayInterestPatternDescription={"Interests that consistently exhilarate your child over a period of time.",
			"Never showed up as exhilarators before, these are interests driving activities your child recently started.",
	"These are interests that are inconsistent as Exhilarators. They come and go!"};
	private RelativeLayout relativeParent;

	String[] backgroundColorInterestDrivers={"#6a18b6","#ff6c00","#ffae00","#90b316","#7f7f7f"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.interestdrivers_event, container, false);
		mListener.onFragmentAttached(false," Insights");
		setHasOptionsMenu(true);
		init(view);

		return view;
	}





	public void init(View view)
	{
		mContext=getActivity();


		gridView_InterestDrivers=(ListView)view.findViewById(R.id.live_gridview);

		interestDriverSpecificName = (TextView) view.findViewById(R.id.interestDriverSpecificName);
		interestDriverSpecificDetail = (TextView) view.findViewById(R.id.interestDriverSpecificDetail);
		typeFace.setTypefaceBold(interestDriverSpecificName);
		typeFace.setTypefaceRegular(interestDriverSpecificDetail);

		if(StaticVariables.isInterestDriver)
			interestDriverSpecificName.setText(arrayInterestDrivers[StaticVariables.BucketIdInsight - 1]);
		else
		{
			interestDriverSpecificDetail.setTextColor(getActivity().getResources().getColor(R.color.black_color));
			interestDriverSpecificName.setText(arrayInterestPattern[StaticVariables.BucketIdInsight - 1]);
			ColorDrawable sage = new ColorDrawable(getActivity().getResources().getColor(R.color.dark_gray));
			gridView_InterestDrivers.setDivider(sage);
			gridView_InterestDrivers.setDividerHeight(1);
			//gridView_InterestDrivers.setDivider(getActivity().getResources().getDrawable(R.drawable.divider_pattern));
		}

		relativeParent=(RelativeLayout) view.findViewById(R.id.parent_driverslayout);
		relativeParent.setBackgroundColor(Color.parseColor(backgroundColorInterestDrivers[StaticVariables.BucketIdInsight - 1]));
		if(StaticVariables.isInterestDriver)
			interestDriverSpecificDetail.setText(arrayInterestDriverDescription[StaticVariables.BucketIdInsight - 1]);
		else
		{
			interestDriverSpecificDetail.setText(arrayInterestPatternDescription[StaticVariables.BucketIdInsight - 1]);	
			relativeParent.setBackgroundColor(Color.parseColor("#ffffff"));
		}


		getInterestTraitsByChildIDOnInsightListForParticularBucketId=new ArrayList<GetInterestTraitsByChildIDOnInsight>();
		getInterestPatternByChildIDOnInsightList=new ArrayList<GetInterestPatternByChildIDOnInsight>();


		if(StaticVariables.isInterestDriver)
		{
			for(int i=0;i<TypesInsightsFragment.getInterestTraitsByChildIDOnInsightList.getGetInterestTraitsByChildIDOnInsight().size();i++)
			{
				GetInterestTraitsByChildIDOnInsight model=TypesInsightsFragment.getInterestTraitsByChildIDOnInsightList.getGetInterestTraitsByChildIDOnInsight().get(i);
				if(model.getBucketID()==StaticVariables.BucketIdInsight)
				{
					getInterestTraitsByChildIDOnInsightListForParticularBucketId.add(model);
				}
			}
			adapter=new InterestDriversAdapterGridView(mContext,getInterestTraitsByChildIDOnInsightListForParticularBucketId);
			gridView_InterestDrivers.setAdapter(adapter);
		}
		else
		{
			try {
				for(int i=0;i<TypesInsightsFragment.getInterestPatternByChildIDOnInsightList.getInterestPatternByChildIDList().size();i++)
				{
					GetInterestPatternByChildIDOnInsight model=TypesInsightsFragment.getInterestPatternByChildIDOnInsightList.getInterestPatternByChildIDList().get(i);
					if(Integer.parseInt(model.getPatternID().trim())==StaticVariables.BucketIdInsight)
					{
						getInterestPatternByChildIDOnInsightList.add(model);
					}
				}
				adapterPattern=new InterestPatternAdapterGridView(mContext,getInterestPatternByChildIDOnInsightList);
				gridView_InterestDrivers.setAdapter(adapterPattern);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				gridView_InterestDrivers.setAdapter(null);
				e.printStackTrace();
			}
		}


		gridView_InterestDrivers.setHorizontalScrollBarEnabled(false);


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
