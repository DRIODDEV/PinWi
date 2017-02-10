package com.hatchtact.pinwi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.TypeFace;

public class GetStartedActivity extends MainActionBarActivity
{
	private TextView welcome_textView=null;
	private TextView getStartedText_textView=null;
	private Button getStarted_button=null;

	private TypeFace typeFace=null;
	private SharePreferenceClass sharePreferenceclass=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub

		screenName="Welcome to PiNWi";
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.getstarted_activity);
		
		typeFace= new TypeFace(this);

		sharePreferenceclass=new SharePreferenceClass(GetStartedActivity.this);

		welcome_textView=(TextView) findViewById(R.id.text_welcome);
		getStarted_button=(Button) findViewById(R.id.button_getStarted);
		getStartedText_textView=(TextView) findViewById(R.id.text_started);

		typeFace.setTypefaceRegular(welcome_textView);
		typeFace.setTypefaceLight(getStartedText_textView);
		typeFace.setTypefaceRegular(getStarted_button);

		getStarted_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharePreferenceclass.setIsLogin(true);
				System.out.println("value of is registered in parentprofile"+sharePreferenceclass.getParentIsRegistered());
				System.out.println("value of is login in parentprofile"+sharePreferenceclass.getIsLogin());
				
				Intent intent=new Intent(GetStartedActivity.this, AccessProfileActivity.class);
				startActivity(intent);
				sharePreferenceclass.setCurrentScreen(4);
				GetStartedActivity.this.finish();
			}
		});
	}
	
	/*@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	}*/
}
