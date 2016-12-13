package com.hatchtact.pinwi.fragment.insights;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.utility.TypeFace;

public class DelightTrendsAdapter  extends BaseAdapter  
{
	private LayoutInflater layout_inflator;
	private TypeFace typeface;
	

	public DelightTrendsAdapter(Context context)
	{
		// TODO Auto-generated constructor stub
		layout_inflator=LayoutInflater.from(context);
		typeface=new TypeFace(context);
		

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return TypesInsightsFragment.getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight().size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return TypesInsightsFragment.getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight().get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup view_grp) 
	{
		// TODO Auto-generated method stub
		Holder holder=null;

		if(view==null)
		{
			holder=new Holder();
			view=layout_inflator.inflate(R.layout.insight_delighttrends, null);
			holder.seekBar=(SeekBar) view.findViewById(R.id.seekbardelighttrends);
			holder.imageTick=(ImageView) view.findViewById(R.id.imagedelighttrends);
			holder.txtView=(TextView)view.findViewById(R.id.textSeekBarDelightTrends);
			holder.txtViewName=(TextView)view.findViewById(R.id.textNameSeekBarDelightTrends);
			 typeface.setTypefaceRegular(holder.txtViewName);
             typeface.setTypefaceLight(holder.txtView);
			view.setTag(holder);

		}

		else
		{
			holder=(Holder)view.getTag();
		}

		holder.seekBar.setEnabled(false);
		holder.seekBar.setProgress((int)(TypesInsightsFragment.getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight().get(position).getRating()));
		holder.txtView.setText(TypesInsightsFragment.getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight().get(position).getRating() + "");
		holder.txtViewName.setText(TypesInsightsFragment.getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight().get(position).getName() + "");
		
		
		if(TypesInsightsFragment.getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight().get(position).getChange()==2.0)
		{
			holder.imageTick.setImageResource(R.drawable.down_arrow);
		}
		else if(TypesInsightsFragment.getDelightTraitsByChildIDOnInsightList.getGetDelightTraitsByChildIDOnInsight().get(position).getChange()==1.0)

		{

			holder.imageTick.setImageResource(R.drawable.up_arrow);

		}

		else
		{
			holder.imageTick.setVisibility(View.INVISIBLE);
		}
		
		
		return view;
	}



	private  class Holder
	{ 
		private SeekBar seekBar;
		private ImageView imageTick;
		private TextView txtView;
		private TextView txtViewName;

	}
}
