package com.hatchtact.pinwi.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.classmodel.GetFriendsListByLoggedID;
import com.hatchtact.pinwi.classmodel.GetListOfClustersOnRecommendedByChildID;
import com.hatchtact.pinwi.classmodel.GetListOfClustersOnRecommendedByChildIDList;
import com.hatchtact.pinwi.classmodel.SearchActivitiesByChildID;
import com.hatchtact.pinwi.classmodel.SearchActivitiesByChildIDList;
import com.hatchtact.pinwi.fragment.whattodo.ActivityListFragment;
import com.hatchtact.pinwi.fragment.whattodo.WhatToDoRecommendedFragment;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.TypeFace;


public class WhatToDoSearchActivityListAdapter extends BaseAdapter
{

	public SearchActivitiesByChildIDList getListOfActivities;

	private LayoutInflater inflater;
	private TypeFace typeFace=null;
	private Context context;
	private ShowMessages showMessage;
	private ActivityListFragment fragmentContext;

	public WhatToDoSearchActivityListAdapter(Context context, SearchActivitiesByChildIDList list, ActivityListFragment networkConnectionsFragment)
	{
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.getListOfActivities = list;
		fragmentContext=networkConnectionsFragment;
		typeFace=new TypeFace(context);
		showMessage=new ShowMessages(context);

	}

	@Override
	public int getCount() {
		if (getListOfActivities.getsearchActivitiesByChildID() != null) 
		{
			return getListOfActivities.getsearchActivitiesByChildID().size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public SearchActivitiesByChildID getItem(int position) {

		return getListOfActivities.getsearchActivitiesByChildID().get(position);
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
			view = inflater.inflate(R.layout.list_whattodo_recommended_rowitem, null);
			holder = createViewHolder(view);
			view.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) view.getTag();
		}


		SearchActivitiesByChildID model=getListOfActivities.getsearchActivitiesByChildID().get(position);

		holder.txtClusterName.setText(model.getActivityName());
		if(model.getIsScheduled()==0)
		holder.txtClusterName.setTextColor(context.getResources().getColor(R.color.font_blue_color));
		else
		{
			holder.txtClusterName.setTextColor(context.getResources().getColor(R.color.gray));

		}
		holder.txtActivityCount.setText("Children doing this: "+model.getChildrenDoingThis());
		Spannable spanText = Spannable.Factory.getInstance().newSpannable(holder.txtActivityCount.getText().toString().trim());
		spanText.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.font_blue_color)),20,holder.txtActivityCount.getText().toString().length() ,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		holder.txtActivityCount.setText(spanText);

		if(model.getIsWished()==0)
		{
			holder.imgFav.setVisibility(View.GONE);
		}
		else
		{
			holder.imgFav.setVisibility(View.VISIBLE);
		}
		holder.imgFav.setTag(model);

		/*holder.imgFav.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//zoom screen activity call	
				SearchActivitiesByChildID model= (SearchActivitiesByChildID) v.getTag();


			}
		});*/

		return view;
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();

		holder.imgFav = (ImageView) view.findViewById(R.id.imgfavrowitem);
		holder.txtClusterName = (TextView) view.findViewById(R.id.txtClusterName);
		holder.txtActivityCount = (TextView) view.findViewById(R.id.txtActivityCount);

		typeFace.setTypefaceRegular(holder.txtClusterName);
		typeFace.setTypefaceRegular(holder.txtActivityCount);
		return holder;
	}

	private  class ViewHolder 
	{
		private ImageView imgFav;
		private TextView txtClusterName;
		private TextView txtActivityCount;
	}



}
