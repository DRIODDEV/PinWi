package com.hatchtact.pinwi.fragment.insights;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.utility.TypeFace;

public class ListInsightsBenefitsAdapter  extends BaseAdapter  
{
	private LayoutInflater layout_inflator;
	ArrayList<String> array=new ArrayList<String>();
	private TypeFace typeface;

	public ListInsightsBenefitsAdapter(Context context, ArrayList<String> arrayBenefits)
	{
		// TODO Auto-generated constructor stub
		layout_inflator=LayoutInflater.from(context);
		array=arrayBenefits;
		typeface=new TypeFace(context);
		

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
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
			view=layout_inflator.inflate(R.layout.insight_benefitsfragment_listitem, null);
			holder.txtBenefits=(TextView) view.findViewById(R.id.benefitsInsights);
			typeface.setTypefaceRegular(holder.txtBenefits);
			view.setTag(holder);

		}

		else
		{
			holder=(Holder)view.getTag();
		}

		holder.txtBenefits.setText(array.get(position));

		return view;
	}



	private  class Holder
	{ 
		private TextView txtBenefits;
		private ImageView imageTick;

	}
}
