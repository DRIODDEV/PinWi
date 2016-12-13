package com.hatchtact.pinwi;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.ScreenSlidePagerAdapter;
import com.hatchtact.pinwi.utility.TypeFace;

public class TutorialPageActivity  extends FragmentActivity
{
	ViewPager viewpager;

	private ScreenSlidePagerAdapter mPagerAdapter;
	TypeFace typefaceClass;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_screen_slide);
		typefaceClass=new TypeFace(TutorialPageActivity.this);
	
		viewpager=(ViewPager) findViewById(R.id.pager);
		
		
		
		
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		viewpager.setAdapter(mPagerAdapter);
	}
}
