package com.hatchtact.pinwi;

import com.hatchtact.pinwi.fragment.NotificationFragment;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class NotificationDetailActivity extends FragmentActivity 
{

	private ImageView imageNotificationNewFragment2;
	private TextView idNotificationTimeFragment2;
	private TextView idNotificationDescFragment2;
	private TypeFace typeFace;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		WindowManager.LayoutParams params = getWindow().getAttributes();  
		params.x =0;  
		params.height = (int) (SplashActivity.ScreenHeight*.4f);  
		params.width = (int)(SplashActivity.ScreenWidth* .9f) ;  
		params.y = SplashActivity.ScreenHeight/15;  
		this.getWindow().setAttributes(params); 

		setContentView(R.layout.dialog_notificationdetail);
		NotificationDetailActivity.this.overridePendingTransition(R.anim.popup_show, R.anim.activity_close_scale);
		
		typeFace=new TypeFace(this);
		imageNotificationNewFragment2=(ImageView)findViewById(R.id.imageNotificationNewFragment2);
		idNotificationTimeFragment2=(TextView)findViewById(R.id.idNotificationTimeFragment2);
		idNotificationDescFragment2=(TextView)findViewById(R.id.idNotificationDescFragment2);
		
		
		if(NotificationFragment.getNotificationListByParentIDList.getGetNotificationListByParentID().get(StaticVariables.positionNotificationSelected).getStatus()==1)

		{
			imageNotificationNewFragment2.setImageResource(R.drawable.profile_i);
		}
		
		else if(NotificationFragment.getNotificationListByParentIDList.getGetNotificationListByParentID().get(StaticVariables.positionNotificationSelected).getStatus()==2)

		{
			imageNotificationNewFragment2.setImageResource(R.drawable.setting_i);
		}
		
		else if(NotificationFragment.getNotificationListByParentIDList.getGetNotificationListByParentID().get(StaticVariables.positionNotificationSelected).getStatus()==3)

		{
			imageNotificationNewFragment2.setImageResource(R.drawable.insight_i);
		}
		
		else
		{
			imageNotificationNewFragment2.setImageResource(R.drawable.scheduler_i);
		}		
		
		
		
		idNotificationTimeFragment2.setText(NotificationFragment.getNotificationListByParentIDList.getGetNotificationListByParentID().get(StaticVariables.positionNotificationSelected).getTime());
		typeFace.setTypefaceLight(idNotificationTimeFragment2);
		
		idNotificationDescFragment2.setText(NotificationFragment.getNotificationListByParentIDList.getGetNotificationListByParentID().get(StaticVariables.positionNotificationSelected).getDescription());
		typeFace.setTypefaceRegular(idNotificationDescFragment2);
		
		
	}

	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		NotificationDetailActivity.this.overridePendingTransition(R.anim.activity_open_scale, R.anim.disappear);
		//super.onBackPressed();
	} 

}
