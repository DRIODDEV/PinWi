package com.hatchtact.pinwi.widgets;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.R;

public class CustomHeader extends LinearLayout
{
	private Context context;
	private Activity insActivity;
	
	private ImageView imageHeaderBg=null;
	private ImageView imagePinwiIcon=null;
	private ImageView imageActivityIcon=null;
	private TextView textheader=null;
	
	private String ModuleName=null;
	
	public CustomHeader(Context context,AttributeSet attr)
	{
		super(context, attr);
		this.context = context;
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.activity_header, this, true);
		
		onImagePinwiIconMethod();
		onImageHeaderBgMethod();
		OnImageActivityIconMethod();
		OnTextPinwiHeaderMethod();
	}

	public String getModuleName() {
		return ModuleName;
	}


	public void setModuleName(String moduleName) {
		ModuleName = moduleName;
	}

	private int ScreenIndex;
	
	public int getScreenIndex() {
		return ScreenIndex;
	}


	public void setScreenIndex(int screenIndex) {
		ScreenIndex = screenIndex;
	}
	
	public void setHeaderText(String headerText)
	{
		textheader.setText(headerText);
	}

	private void OnTextPinwiHeaderMethod() {
		// TODO Auto-generated method stub
		textheader = (TextView) findViewById(R.id.text_profileHeader);
		textheader.setOnClickListener(textListnerOnPinwiProfileText);
	}


	private void OnImageActivityIconMethod() {
		// TODO Auto-generated method stub
		imageActivityIcon = (ImageView) findViewById(R.id.image_ProfileIcon);
		imageActivityIcon.setOnClickListener(imageListnerOnPinwiProfileIcon);
	}


	private void onImageHeaderBgMethod() {
		// TODO Auto-generated method stub
		imageHeaderBg = (ImageView) findViewById(R.id.image_header_bg);
		imageHeaderBg.setOnClickListener(imageListnerOnPinwiHeaderBg);
	}


	private void onImagePinwiIconMethod() {
		// TODO Auto-generated method stub
			imagePinwiIcon = (ImageView) findViewById(R.id.image_pinwiIcon_header);
			imagePinwiIcon.setOnClickListener(imageListnerOnPinwiIcon);
	}
	
	public OnClickListener imageListnerOnPinwiIcon = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
		}
	};
	
	public OnClickListener imageListnerOnPinwiHeaderBg = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
		}
	};
	
	public OnClickListener imageListnerOnPinwiProfileIcon = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
		}
	};
	
	public OnClickListener textListnerOnPinwiProfileText = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
		}
	};
}
