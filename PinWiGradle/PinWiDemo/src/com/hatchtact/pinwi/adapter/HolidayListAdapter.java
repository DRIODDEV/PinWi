package com.hatchtact.pinwi.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.SchedularHolidayModel;
import com.hatchtact.pinwi.utility.TypeFace;

public class HolidayListAdapter extends ArrayAdapter<SchedularHolidayModel> 
{

	private List<SchedularHolidayModel> getHolidayNameList=null;


	private LayoutInflater inflater;

	private TypeFace typeFace=null;

	public HolidayListAdapter(Context context, ArrayList<SchedularHolidayModel> list)
	{
		super(context, R.layout.list_item_holidays, list);
		// TODO Auto-generated constructor stub
		typeFace=new TypeFace(context);

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.getHolidayNameList = list;

	}

	@Override
	public int getCount() {
		if (getHolidayNameList != null) 
		{
			return getHolidayNameList.size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public SchedularHolidayModel getItem(int position) {

		return getHolidayNameList.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	ViewHolder holder;

	@Override
	public View getView(final int position, View view, ViewGroup parent) 
	{
		if (view == null)
		{
			view = inflater.inflate(R.layout.list_item_holidays, null);
			holder = createViewHolder(view);
			view.setTag(holder);

		} 
		else 
		{
			holder = (ViewHolder) view.getTag();
		}


		holder.holidaydesctext.setText(getHolidayNameList.get(position).getHolidayDescription());	

		return view;
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();

		holder.holidaydesctext = (TextView) view.findViewById(R.id.item);
		typeFace.setTypefaceRegular(holder.holidaydesctext);

		return holder;
	}

	private  class ViewHolder 
	{
		private TextView holidaydesctext;
	}



}
