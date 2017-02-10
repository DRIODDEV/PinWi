package com.hatchtact.pinwi.view;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.classmodel.AddAfterSchoolActivities;

public class DisplayAddSchoolActivity extends LinearLayout
{
	private int id;	

	private AddAfterSchoolActivities addAfterSchoolActivities;
	
	public DisplayAddSchoolActivity(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public DisplayAddSchoolActivity(Context context, AddAfterSchoolActivities addafterSchoolActivities, int idLayout, OnClickListener buttonClick)
	{
		super(context);
		
		this.id = idLayout*10;
		setOrientation(HORIZONTAL);
		addAfterSchoolActivities = addafterSchoolActivities;
	
		LinearLayout.LayoutParams parmslayoutText = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		
		parmslayoutText.weight = 1;
		
		parmslayoutText.setMargins(25, 10, 0, 0);
		
		TextView subjectNameText = new TextView(context);
		subjectNameText.setText("");
		subjectNameText.setTextSize(20);
		subjectNameText.setLayoutParams(parmslayoutText);
		
		this.addView(subjectNameText);
		
		TextView SundayText = new TextView(context);
		SundayText.setText("S");
		SundayText.setTextSize(20);
		SundayText.setLayoutParams(parmslayoutText);
		
		this.addView(SundayText);
		
		TextView mondayText = new TextView(context);
		mondayText.setText("M");
		mondayText.setTextSize(20);
		mondayText.setLayoutParams(parmslayoutText);
		
		this.addView(mondayText);
		
		TextView tuesdayText = new TextView(context);
		tuesdayText.setText("T");
		tuesdayText.setTextSize(20);
		tuesdayText.setLayoutParams(parmslayoutText);
		
		this.addView(tuesdayText);
		
		TextView wednesdayText = new TextView(context);
		wednesdayText.setText("W");
		wednesdayText.setTextSize(20);
		wednesdayText.setLayoutParams(parmslayoutText);
		
		this.addView(wednesdayText);
		
		TextView ThursdayText = new TextView(context);
		ThursdayText.setText("T");
		ThursdayText.setTextSize(20);
		ThursdayText.setLayoutParams(parmslayoutText);
		
		this.addView(ThursdayText);
		
		TextView FridayText = new TextView(context);
		FridayText.setText("F");
		FridayText.setTextSize(20);
		FridayText.setLayoutParams(parmslayoutText);
		
		this.addView(FridayText);
		
		TextView SaturdayText = new TextView(context);
		SaturdayText.setText("S");
		SaturdayText.setTextSize(20);
		SaturdayText.setLayoutParams(parmslayoutText);
		
		this.addView(SaturdayText);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AddAfterSchoolActivities getAddAfterSchoolActivities() {
		return addAfterSchoolActivities;
	}

	public void setAddAfterSchoolActivities(
			AddAfterSchoolActivities addAfterSchoolActivities) {
		this.addAfterSchoolActivities = addAfterSchoolActivities;
	}
}
