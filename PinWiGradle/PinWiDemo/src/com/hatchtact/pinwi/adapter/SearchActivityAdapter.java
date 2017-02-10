package com.hatchtact.pinwi.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.SearchActivitiesByActivityName;
import com.hatchtact.pinwi.classmodel.SearchActivitiesByActivityNameList;
import com.hatchtact.pinwi.utility.TypeFace;

public class SearchActivityAdapter extends BaseAdapter 
{
	private SearchActivitiesByActivityNameList listSearchActivity=null;
	
	private LayoutInflater inflater;
	
	private TypeFace typeFace=null;

	public SearchActivityAdapter(Context context, SearchActivitiesByActivityNameList list)
	{
		// TODO Auto-generated constructor stub

		typeFace=new TypeFace(context);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.listSearchActivity = list;
	}  

	@Override
	public int getCount() {        
		if (listSearchActivity!= null) 
		{
			return listSearchActivity.getSearchActivitiesByActName().size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public SearchActivitiesByActivityName getItem(int position) {

		return listSearchActivity.getSearchActivitiesByActName().get(position);
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


		holder.categoryName_text.setText(listSearchActivity.getSearchActivitiesByActName().get(position).getActivityName());	
		
		return view;
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();

		holder.categoryName_text = (TextView) view.findViewById(R.id.item);
		typeFace.setTypefaceRegular(holder.categoryName_text);
		
		return holder;
	}

	private  class ViewHolder 
	{
		private TextView categoryName_text;
	}
	
	
	
	
}
