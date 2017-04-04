package com.hatchtact.pinwi.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hatchtact.pinwi.AllyListActivity;
import com.hatchtact.pinwi.AllyRegistrationActivity;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.GetListofAllysByParentID;
import com.hatchtact.pinwi.utility.TypeFace;


public class AllyListByParentIdAdapter extends ArrayAdapter<GetListofAllysByParentID>{

	public ArrayList<GetListofAllysByParentID>  list_allyName = new ArrayList<GetListofAllysByParentID>();

	private LayoutInflater inflater;
	private TypeFace typeFace=null;
	private Context context;
	private AllyListActivity allyListActivity;
	
	public AllyListByParentIdAdapter(Context context, ArrayList<GetListofAllysByParentID> list, AllyListActivity allyListActivity)
	{
		super(context, R.layout.item_ally_list, list);
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list_allyName = list;
		typeFace= new TypeFace(context);
		this.allyListActivity=allyListActivity;

		
	}

	@Override
	public int getCount() {
		if (list_allyName != null) 
		{
			return list_allyName.size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public GetListofAllysByParentID getItem(int position) {

		return list_allyName.get(position);
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
			view = inflater.inflate(R.layout.item_ally_list, null);
			holder = createViewHolder(view);
			view.setTag(holder);

		} 
		else 
		{
			holder = (ViewHolder) view.getTag();
		}


		holder.allyName_text.setText(list_allyName.get(position).getFirstName());	
		
		holder.edit_ally.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent allyIntent=new Intent(context,AllyRegistrationActivity.class);
				Bundle bundleLocation=new Bundle();
				bundleLocation.putBoolean("ToAllyScreen", true);
				bundleLocation.putInt("allyId", list_allyName.get(position).getAllyID());
				allyIntent.putExtras(bundleLocation);
				context.startActivity(allyIntent);
				((AllyListActivity) context).finish();
			}
		});
		
		holder.cancel_ally.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				allyListActivity.deleteAlly(list_allyName.get(position).getAllyID(),position);
			}
		});
		return view;
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();

		holder.allyName_text = (TextView) view.findViewById(R.id.item);
		typeFace.setTypefaceRegular(holder.allyName_text);
		holder.edit_ally = (ImageView) view.findViewById(R.id.edit_ally);
		holder.cancel_ally = (ImageView) view.findViewById(R.id.cancel_ally);
		return holder;
	}

	private  class ViewHolder 
	{
		private TextView allyName_text;
		private ImageView edit_ally;
		private ImageView cancel_ally;
		
	}
}
