package com.hatchtact.pinwi.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.utility.TypeFace;

@SuppressLint("ResourceAsColor")
public class NotificationFragmentOneAdapter  extends BaseAdapter  
{
	private LayoutInflater layout_inflator;
	private TypeFace typeface;

	public NotificationFragmentOneAdapter(Context context)
	{
		// TODO Auto-generated constructor stub
		layout_inflator=LayoutInflater.from(context);
		typeface=new TypeFace(context);


	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return NotificationFragment.getNotificationListByParentIDList.getGetNotificationListByParentID().size();
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
			view=layout_inflator.inflate(R.layout.notification_tab_one, null);
			holder.idNotificationDesc1=(TextView) view.findViewById(R.id.idNotificationDesc1);
			holder.idNotificationDesc2=(TextView) view.findViewById(R.id.idNotificationDesc2);
			holder.imageNotificationNewFragment=(ImageView) view.findViewById(R.id.imageNotificationNewFragment);

			typeface.setTypefaceRegular(holder.idNotificationDesc1);
			typeface.setTypefaceRegular(holder.idNotificationDesc2);
			view.setTag(holder);

		}

		else
		{
			holder=(Holder)view.getTag();
		}

		holder.idNotificationDesc1.setText(NotificationFragment.getNotificationListByParentIDList.getGetNotificationListByParentID().get(position).getDescription());
		holder.idNotificationDesc2.setText(NotificationFragment.getNotificationListByParentIDList.getGetNotificationListByParentID().get(position).getTime());


		if(NotificationFragment.getNotificationListByParentIDList.getGetNotificationListByParentID().get(position).getRead()!=0)
			holder.idNotificationDesc1.setTextColor(Color.BLACK);

		if(NotificationFragment.getNotificationListByParentIDList.getGetNotificationListByParentID().get(position).getStatus()==1)

		{
			holder.imageNotificationNewFragment.setImageResource(R.drawable.profile_i);
		}

		else if(NotificationFragment.getNotificationListByParentIDList.getGetNotificationListByParentID().get(position).getStatus()==2)

		{
			holder.imageNotificationNewFragment.setImageResource(R.drawable.setting_i);
		}

		else if(NotificationFragment.getNotificationListByParentIDList.getGetNotificationListByParentID().get(position).getStatus()==3)

		{
			holder.imageNotificationNewFragment.setImageResource(R.drawable.insight_i);
		}

		else//if needed check will be of zero
		{
			holder.imageNotificationNewFragment.setImageResource(R.drawable.scheduler_i);
		}

		return view;
	}



	private  class Holder
	{ 
		private TextView idNotificationDesc1;
		private TextView idNotificationDesc2;
		private ImageView imageNotificationNewFragment;

	}
}
