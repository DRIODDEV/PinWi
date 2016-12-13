package com.hatchtact.pinwi.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.hatchtact.pinwi.ActivityAboutUS;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.SupportItems;
import com.hatchtact.pinwi.utility.TypeFace;


public class SupportAdapter extends ArrayAdapter<SupportItems>{

	public ArrayList<SupportItems>  list_supportValue = new ArrayList<SupportItems>();

	private LayoutInflater inflater;
	private TypeFace typeFace=null;
	public SupportAdapter(Context context, ArrayList<SupportItems> list)
	{
		super(context, R.layout.list_item_support, list);
		// TODO Auto-generated constructor stub
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list_supportValue = list;
		typeFace= new TypeFace(context); 

	}

	@Override
	public int getCount() {
		if (list_supportValue != null) 
		{
			return list_supportValue.size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public SupportItems getItem(int position) {

		return list_supportValue.get(position);
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
			view = inflater.inflate(R.layout.list_item_support, null);
			holder = createViewHolder(view);
			view.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) view.getTag();
		}

		holder.support_text.setText(list_supportValue.get(position).getSupportText());	
		holder.supportDescription_text.setText(list_supportValue.get(position).getSupportDescription());	
		
		return view;
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();

		holder.support_text = (TextView) view.findViewById(R.id.text_support);
		holder.supportDescription_text=(TextView) view.findViewById(R.id.text_supportDescription);
		typeFace.setTypefaceRegular(holder.support_text);
		typeFace.setTypefaceLight(holder.supportDescription_text);
		
		return holder;
	}

	private  class ViewHolder 
	{
		private TextView support_text;
		private TextView supportDescription_text;
	}
}
