package com.hatchtact.pinwi.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.SplashActivity;
import com.hatchtact.pinwi.child.HexagonImageView;
import com.hatchtact.pinwi.classmodel.GetNotificationListByChildIDOnCI;
import com.hatchtact.pinwi.classmodel.GetNotificationListByChildIDOnCIList;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.TypeFace;


public class ChildAlertListAdapter extends ArrayAdapter<GetNotificationListByChildIDOnCI>
{
	public GetNotificationListByChildIDOnCIList listAlerts = new GetNotificationListByChildIDOnCIList();
	private LayoutInflater inflater;
	private TypeFace typeFace=null;
	private Context context;
	private ShowMessages showMessage;

	public ChildAlertListAdapter(Context context, GetNotificationListByChildIDOnCIList list)
	{
		super(context, R.layout.item_alerts);
		// TODO Auto-generated constructor stub
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.listAlerts = list;
		typeFace=new TypeFace(context);
		showMessage=new ShowMessages(context);

	}

	@Override
	public int getCount() 
	{
		if (listAlerts!= null && listAlerts.getNotificationListByChildIDOnCIList()!=null)  
		{
			return listAlerts.getNotificationListByChildIDOnCIList().size();
		} 
		else 
		{
			return 0;
		}          
	}

	@Override
	public GetNotificationListByChildIDOnCI getItem(int position) {

		return listAlerts.getNotificationListByChildIDOnCIList().get(position);
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
			view = inflater.inflate(R.layout.item_alerts, null);
			holder = createViewHolder(view);
			view.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) view.getTag();
		}

	if(listAlerts.getNotificationListByChildIDOnCIList().get(position).getProfileImage()!=null && listAlerts.getNotificationListByChildIDOnCIList().get(position).getProfileImage().trim().length()>100)
		{
			byte[] imageByteParent=Base64.decode(listAlerts.getNotificationListByChildIDOnCIList().get(position).getProfileImage(), 0);
			if(imageByteParent!=null)
			{
				Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(imageByteParent, 0, imageByteParent.length), dp2px(80), dp2px(80), false);
				holder.child_alert_image.setImageBitmap(bitmap);
			}
		}
		else
		{
			/*holder.child_buddies_image.setBackgroundResource(R.drawable.child_image);	
			holder.child_buddies_image.setImageBitmap(null);*/
			Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.default_buddies), dp2px(80), dp2px(80), false);
			holder.child_alert_image.setImageBitmap(bitmap);
		}




		if(listAlerts.getNotificationListByChildIDOnCIList().get(position).getChildName()!=null && listAlerts.getNotificationListByChildIDOnCIList().get(position).getDescription()!=null)
		{
			holder.child_alert_msg.setText(listAlerts.getNotificationListByChildIDOnCIList().get(position).getChildName()+listAlerts.getNotificationListByChildIDOnCIList().get(position).getDescription());	
			final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
			Spannable spanText = Spannable.Factory.getInstance().newSpannable(holder.child_alert_msg.getText().toString());
			spanText.setSpan(bss,0, listAlerts.getNotificationListByChildIDOnCIList().get(position).getChildName().length() ,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			
			if(spanText!=null && spanText.length()>0)
			holder.child_alert_msg.setText(spanText);
			else
			{
				holder.child_alert_msg.setText(listAlerts.getNotificationListByChildIDOnCIList().get(position).getChildName()+listAlerts.getNotificationListByChildIDOnCIList().get(position).getDescription());	
			}
			holder.child_alert_time.setText(listAlerts.getNotificationListByChildIDOnCIList().get(position).getTime());
		}

		return view;
	}

	private ViewHolder createViewHolder(View view)   
	{
		ViewHolder holder = new ViewHolder();

		holder.child_alert_image = (HexagonImageView) view.findViewById(R.id.child_alert_image);
		holder.child_alert_msg = (TextView) view.findViewById(R.id.child_alert_msg);
		holder.child_alert_time = (TextView) view.findViewById(R.id.child_alert_time);
		typeFace.setTypefaceGotham(holder.child_alert_msg);
		typeFace.setTypefaceGotham(holder.child_alert_time);
		return holder;
	}

	private  class ViewHolder 
	{
		private HexagonImageView child_alert_image;
		private TextView child_alert_msg;
		private TextView child_alert_time;
	}


	private int dp2px(int dp) 
	{
		
		if(SplashActivity.ScreenWidth >= 2000)
		{
			dp = 70;

		}

		else if(SplashActivity.ScreenWidth >= 1000)
		{
			dp = 70;

		}
		else if(SplashActivity.ScreenWidth >= 700)
		{
			dp = 60;
			
		}
		else if(SplashActivity.ScreenWidth >= 590)
		{
			dp = 80;
		}
		else
		{
			dp = 60;
		}
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}
}
