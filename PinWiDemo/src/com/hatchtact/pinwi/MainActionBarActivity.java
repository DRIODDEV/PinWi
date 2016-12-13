package com.hatchtact.pinwi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.widget.TextView;
import com.hatchtact.pinwi.R;

public class MainActionBarActivity extends ActionBarActivity
{
	String screenName;
	private Bitmap bitmapHeader;
	
	private void getDisplayWidth(Activity a)
	{

		Display display = a.getWindowManager().getDefaultDisplay();

		// creating an empty Point so that the compiler
		// does not complain about null reference
		Point displaySize = new Point();
		display.getSize(displaySize);
		SplashActivity.ScreenWidth =  displaySize.x;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getDisplayWidth(MainActionBarActivity.this);
		bitmapHeader = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.header_bg), SplashActivity.ScreenWidth, BitmapFactory.decodeResource(getResources(), R.drawable.header_bg).getHeight(), false);

		setActionbar();
	}

	@SuppressLint("ResourceAsColor")
	protected void setActionbar() 
	{	
		ActionBar actionBar = getSupportActionBar();

		if (actionBar != null) 
		{
			actionBar.setDisplayShowHomeEnabled(false);

			actionBar.setBackgroundDrawable(new Drawable() 
			{	
				@Override
				public void setColorFilter(ColorFilter cf) {
					// TODO Auto-generated method stub	
				}

				@Override
				public void setAlpha(int alpha) {
					// TODO Auto-generated method stub	
				}

				@Override
				public int getOpacity() {
					// TODO Auto-generated method stub
					return 0;
				}

				@Override
				public void draw(Canvas canvas) {
					// TODO Auto-generated method stub
					//canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.header_bg), 0, 0, null);
					canvas.drawBitmap(bitmapHeader, 0, 0, null);

				}
			});

			actionBar.setTitle(screenName);

			int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
			if (actionBarTitleId > 0) {
				TextView title = (TextView) findViewById(actionBarTitleId);
				if (title != null) {
					title.setTextColor(Color.WHITE); 
				}
			}
		}
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		
	}
}
