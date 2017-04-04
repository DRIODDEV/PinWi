package com.hatchtact.pinwi.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.AddActivityCalender;
import com.hatchtact.pinwi.utility.TypeFace;


public class AddNewActivityCalenderAdapter extends ArrayAdapter<AddActivityCalender>{

	public ArrayList<AddActivityCalender>  list_activity= new ArrayList<AddActivityCalender>();

	private LayoutInflater inflater;
	
	private TypeFace typeFace=null;

	public AddNewActivityCalenderAdapter(Context context, ArrayList<AddActivityCalender> list)
	{
		super(context, R.layout.list_item_subject, list);
		// TODO Auto-generated constructor stub
		typeFace=new TypeFace(context);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list_activity = list;
	}

	@Override
	public int getCount() {
		if (list_activity != null) 
		{
			return list_activity.size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public AddActivityCalender getItem(int position) {

		return list_activity.get(position);
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
			view = inflater.inflate(R.layout.list_item_subject, null);
			holder = createViewHolder(view);
			view.setTag(holder);

		} 
		else 
		{
			holder = (ViewHolder) view.getTag();
		}


		holder.subjectName_text.setText(list_activity.get(position).getActivity_name());
		typeFace.setTypefaceRegular(holder.subjectName_text);
		
		return view;
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();

		holder.subjectName_text = (TextView) view.findViewById(R.id.item);

		return holder;
	}

	private  class ViewHolder 
	{
		private TextView subjectName_text;
	}
}
