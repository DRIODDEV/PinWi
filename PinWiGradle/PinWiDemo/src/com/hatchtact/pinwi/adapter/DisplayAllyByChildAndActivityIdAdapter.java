package com.hatchtact.pinwi.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.DisplayAllyByChildAndActivityId;
import com.hatchtact.pinwi.utility.TypeFace;

public class DisplayAllyByChildAndActivityIdAdapter extends ArrayAdapter<DisplayAllyByChildAndActivityId>{

	public ArrayList<DisplayAllyByChildAndActivityId>  list_allyData = new ArrayList<DisplayAllyByChildAndActivityId>();

	private LayoutInflater inflater;
	
	private TypeFace typeFace=null;

	private Context context=null;

	public DisplayAllyByChildAndActivityIdAdapter(Context context, ArrayList<DisplayAllyByChildAndActivityId> list)
	{
		super(context, R.layout.ally_list_itemdata, list);
		// TODO Auto-generated constructor stub
		typeFace=new TypeFace(context);
		this.context=context;

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list_allyData = list;
	}

	@Override
	public int getCount() {
		if (list_allyData != null) 
		{
			return list_allyData.size();
		} 
		else 
		{
			return 0;
		}             
	}

	@Override
	public DisplayAllyByChildAndActivityId getItem(int position) {

		return list_allyData.get(position);
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
			view = inflater.inflate(R.layout.ally_list_itemdata, null);
			holder = createViewHolder(view);
			view.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) view.getTag();
		}

		try 
		{
			holder.allyname_textDisplay.setText(list_allyData.get(position).getAllyName());
			holder.allytime_textDisplay.setText(list_allyData.get(position).getTime());
			holder.datetodisplay_ally.setText(list_allyData.get(position).getDate());
			
			if(list_allyData.get(position).getPickUp().equalsIgnoreCase("1"))
				holder.allypickdrop_textDisplay.setText("Pick");		
			
			if(list_allyData.get(position).getDrop().equalsIgnoreCase("1"))
				holder.allypickdrop_textDisplay.setText("Drop");
				
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();

		holder.allyname_textDisplay = (TextView) view.findViewById(R.id.allyname_textDisplay);
		holder.datetodisplay_ally=(TextView) view.findViewById(R.id.datetodisplay_ally);
		holder.allypickdrop_textDisplay=(TextView) view.findViewById(R.id.allypickdrop_textDisplay);
		holder.allytime_textDisplay=(TextView) view.findViewById(R.id.allytime_textDisplay);
		
		typeFace.setTypefaceRegular(holder.allyname_textDisplay);
		typeFace.setTypefaceRegular(holder.datetodisplay_ally);
		typeFace.setTypefaceRegular(holder.allypickdrop_textDisplay);
		typeFace.setTypefaceRegular(holder.allytime_textDisplay);
		
		return holder;
	}

	private  class ViewHolder 
	{
		private TextView allyname_textDisplay;
		private TextView datetodisplay_ally;
		private TextView allypickdrop_textDisplay;
		private TextView allytime_textDisplay;
	}
}
