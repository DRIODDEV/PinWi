package com.hatchtact.pinwi.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;


public class CustomDialogBiWeekly extends Dialog implements View.OnClickListener {

	private TextView txtStartNextWeek, txtIncludeThisWeek,txtCancel;
	private TypeFace typeface;
	private FrequencyAfterSchoolFragment contextAfterSchool;



	public CustomDialogBiWeekly(FrequencyAfterSchoolFragment context) {
		super(context.getActivity());
		// TODO Auto-generated constructor stub
		this.contextAfterSchool = context;
		typeface=new TypeFace(context.getActivity());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_dialog_whattodo);


		txtStartNextWeek = (TextView) findViewById(R.id.txtwhoisdoingthis);
		txtIncludeThisWeek = (TextView) findViewById(R.id.txtSchedulethisAct);
		TextView txtViewWishlist = (TextView) findViewById(R.id.txtViewWishlist);
		View emptylinewishlist=(View) findViewById(R.id.emptylinewishlist);
		txtViewWishlist.setVisibility(View.GONE);
		emptylinewishlist.setVisibility(View.GONE);
		txtCancel = (TextView) findViewById(R.id.txtCancel);
		txtStartNextWeek.setText("Start Next Week");
		txtIncludeThisWeek.setText("Include this Week");
		txtStartNextWeek.setOnClickListener(this);
		txtIncludeThisWeek.setOnClickListener(this);
		txtCancel.setOnClickListener(this);


		typeface.setTypefaceRegular(txtStartNextWeek);
		typeface.setTypefaceRegular(txtIncludeThisWeek);
		typeface.setTypefaceRegular(txtCancel);
		if(contextAfterSchool!=null)
		{

		}
		setCancelable(true);
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.txtwhoisdoingthis:
			contextAfterSchool.biWeeklyImplementation(0);
			dismiss();
			break;
		case R.id.txtSchedulethisAct:
			contextAfterSchool.biWeeklyImplementation(1);
			dismiss();
			break;
		case R.id.txtCancel:
			contextAfterSchool.biWeeklyImplementation(2);
			dismiss();
			break;
		}
	}


}