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
import com.hatchtact.pinwi.classmodel.GetListOfActivitiesOnRecommendedByClusterID;
import com.hatchtact.pinwi.classmodel.GetListOfActivitiesOnRecommendedByClusterIDList;
import com.hatchtact.pinwi.classmodel.GetWishListsByChildID;
import com.hatchtact.pinwi.classmodel.GetWishListsByChildIDList;
import com.hatchtact.pinwi.fragment.whattodo.ActivityListFragment;
import com.hatchtact.pinwi.fragment.whattodo.WishListFragment;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.TypeFace;


public class WhatToDoWishListAdapter extends BaseAdapter
{

	public GetWishListsByChildIDList getWishlist;

	private LayoutInflater inflater;
	private TypeFace typeFace=null;
	private Context context;
	private ShowMessages showMessage;
	private WishListFragment fragmentContext;

	public WhatToDoWishListAdapter(Context context, GetWishListsByChildIDList list, WishListFragment networkConnectionsFragment)
	{
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.getWishlist = list;
		fragmentContext=networkConnectionsFragment;
		typeFace=new TypeFace(context);
		showMessage=new ShowMessages(context);
	}

	@Override
	public int getCount() {
		if (getWishlist.getWishListsByChildIDList() != null) 
		{
			return getWishlist.getWishListsByChildIDList().size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public GetWishListsByChildID getItem(int position) {

		return getWishlist.getWishListsByChildIDList().get(position);
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


		GetWishListsByChildID model=getWishlist.getWishListsByChildIDList().get(position);

		holder.txtClusterName.setText(model.getName());
		if(model.getIsScheduled()==0)
			holder.txtClusterName.setTextColor(context.getResources().getColor(R.color.font_blue_color));
		else
		{
			holder.txtClusterName.setTextColor(context.getResources().getColor(R.color.gray));

		}		holder.txtActivityCount.setText("Children doing this: "+model.getChildrenDoingThis());
		Spannable spanText = Spannable.Factory.getInstance().newSpannable(holder.txtActivityCount.getText().toString().trim());
		spanText.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.font_blue_color)),20,holder.txtActivityCount.getText().toString().length() ,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		holder.txtActivityCount.setText(spanText);


		holder.imgFav.setVisibility(View.VISIBLE);

		holder.imgFav.setTag(model);

		/*holder.imgFav.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//zoom screen activity call	
				//GetListOfActivitiesOnRecommendedByClusterID model= (GetListOfActivitiesOnRecommendedByClusterID) v.getTag();


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
