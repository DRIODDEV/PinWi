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

import com.hatchtact.pinwi.ChildListActivity;
import com.hatchtact.pinwi.ChildRegistrationActivity;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.GetListofChildsByParentID;
import com.hatchtact.pinwi.utility.TypeFace;

public class ChildListAdapter extends ArrayAdapter<GetListofChildsByParentID>{

	public ArrayList<GetListofChildsByParentID>  list_childName = new ArrayList<GetListofChildsByParentID>();

	private LayoutInflater inflater;
	private TypeFace typeFace=null; 
	private Context context;
	private ChildListActivity childListActivity;

	public ChildListAdapter(Context context, ArrayList<GetListofChildsByParentID> list,ChildListActivity childListActivity)
	{
		super(context, R.layout.item_ally_list, list);
		// TODO Auto-generated constructor stub

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list_childName = list;
		this.context=context;
		this.childListActivity=childListActivity;
		typeFace= new TypeFace(context);
	}

	@Override
	public int getCount() {
		int count = 0;
		if (list_childName != null && list_childName.size()>0) 
		{
			count = list_childName.size();
		}
		return count;
	}

	@Override
	public GetListofChildsByParentID getItem(int position) {

		return list_childName.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	//	ViewHolder holder;

	@Override
	public View getView(final int position, View view, ViewGroup parent) 
	{
		final ViewHolder holder;
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


		holder.childName_text.setText(list_childName.get(position).getNickName());	

		holder.edit_child.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent childIntent=new Intent(context,ChildRegistrationActivity.class);
				Bundle bundleLocation=new Bundle();
				bundleLocation.putBoolean("ToChildScreen", true);
				bundleLocation.putInt("childId", list_childName.get(position).getChildID());
				childIntent.putExtras(bundleLocation);
				childListActivity.startActivity(childIntent);
				((ChildListActivity) context).finish();
			}
		});

		holder.cancel_child.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				childListActivity.deleteChild(list_childName.get(position).getChildID(),position);
			}
		});

		return view;
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();

		holder.childName_text = (TextView) view.findViewById(R.id.item);
		typeFace.setTypefaceRegular(holder.childName_text);
		holder.edit_child = (ImageView) view.findViewById(R.id.edit_ally);
		holder.cancel_child = (ImageView) view.findViewById(R.id.cancel_ally);

		return holder;
	}

	private  class ViewHolder 
	{
		private TextView childName_text;
		private ImageView edit_child;
		private ImageView cancel_child;
	}
}
