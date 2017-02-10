package com.hatchtact.pinwi.fragment.insights;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.GetInterestTraitsByChildIDOnInsight;
import com.hatchtact.pinwi.utility.TypeFace;



@SuppressWarnings("serial")
public class InterestDriversAdapterGridView extends BaseAdapter implements Serializable
{


	//private static final String textDesc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ";
	private TypeFace typeface;
	private Context context;

	private LayoutInflater inflator;
	
	ArrayList<GetInterestTraitsByChildIDOnInsight> getInterestTraitsByChildIDForParticularBucketId;

	private Integer[] arrayInterestTrendsImages={R.drawable.ic_launcher,
    		R.drawable.one_big,
    		R.drawable.two_big,
    		R.drawable.three_big,
    		R.drawable.four_big,
    		R.drawable.five_big,
    		R.drawable.six_big,
    		R.drawable.seven_big,
    		R.drawable.eight_big,
    		R.drawable.nine_big,
    		R.drawable.ten_big,
    		R.drawable.eleven_big,
    		R.drawable.twelve_big,
    		R.drawable.thirteen_big,
    		R.drawable.fourteen_big,
    		R.drawable.fifteen_big,
    		R.drawable.sixteen_big,
    		R.drawable.seventeen_big,
    		
    		};
	
	
	public InterestDriversAdapterGridView(Context context, ArrayList<GetInterestTraitsByChildIDOnInsight> getInterestTraitsByChildIDOnInsightListForParticularBucketId) 
	{
		super();                  
		this.context = context;
		inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		getInterestTraitsByChildIDForParticularBucketId=getInterestTraitsByChildIDOnInsightListForParticularBucketId;
		typeface=new TypeFace(context);

	}




	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return getInterestTraitsByChildIDForParticularBucketId.size();
	}

	/*@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pins.get(position);
	}
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ViewHolderTrait holder=null;

		if (convertView == null) 
		{
			holder = new ViewHolderTrait();

			convertView=inflator.inflate(R.layout.interestdrivers_gridview, null);
			holder.interestDriverImage = (ImageView)convertView.findViewById(R.id.image_img1);
			holder.interestDriverName=(TextView)convertView.findViewById(R.id.event_title_txt1);
            holder.image_imgNew = (ImageView)convertView.findViewById(R.id.image_imgNew);


			holder.interestDriverDesc=(TextView)convertView.findViewById(R.id.event_title_txt2);
			holder.interestDriverArrow=(ImageView)convertView.findViewById(R.id.image_img2);
			typeface.setTypefaceRegular(holder.interestDriverName);
			typeface.setTypefaceLight(holder.interestDriverDesc);
			convertView.setTag(holder);

		}

		else
		{
			holder=(ViewHolderTrait)convertView.getTag();
		}


		holder.interestDriverName.setText(getInterestTraitsByChildIDForParticularBucketId.get(position).getName().toString());
		holder.interestDriverImage.setImageResource(arrayInterestTrendsImages[getInterestTraitsByChildIDForParticularBucketId.get(position).getInterestTraitID()]);
		holder.interestDriverArrow.setVisibility(View.VISIBLE);
		if(getInterestTraitsByChildIDForParticularBucketId.get(position).getStatus()==1)
		{
			holder.interestDriverArrow.setVisibility(View.VISIBLE);
			holder.interestDriverDesc.setVisibility(View.VISIBLE);
			holder.interestDriverArrow.setImageResource(R.drawable.up_arrow_big_i);
			holder.interestDriverDesc.setText(getInterestTraitsByChildIDForParticularBucketId.get(position).getDescription().toString());
			holder.image_imgNew.setImageResource(R.drawable.new_found);
			holder.image_imgNew.setVisibility(View.GONE);
		}
		else if(getInterestTraitsByChildIDForParticularBucketId.get(position).getStatus()==2)
		{
			holder.interestDriverArrow.setVisibility(View.VISIBLE);
			holder.interestDriverDesc.setVisibility(View.VISIBLE);
			holder.interestDriverArrow.setImageResource(R.drawable.down_arrow_big_i);

			//holder.interestDriverDesc.setText("This driver is old");
			holder.interestDriverDesc.setText(getInterestTraitsByChildIDForParticularBucketId.get(position).getDescription().toString());
			holder.image_imgNew.setVisibility(View.GONE);
		}
		else
		{
			holder.interestDriverArrow.setVisibility(View.INVISIBLE);
			holder.interestDriverDesc.setVisibility(View.VISIBLE);
			holder.interestDriverDesc.setText(getInterestTraitsByChildIDForParticularBucketId.get(position).getDescription().toString());
			holder.image_imgNew.setVisibility(View.GONE);

		}

		
		return convertView;
	}



	private class ViewHolderTrait
	{

		ImageView interestDriverImage;
		TextView  interestDriverName;
		TextView interestDriverDesc;
		ImageView  interestDriverArrow;
		ImageView image_imgNew;


	}



	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}



}
