package com.hatchtact.pinwi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.utility.TypeFace;

public class SchoolMainFragment extends Fragment
{
	
	private TextView calender_textView=null;
	private TextView afterschool_textView=null;
	private TextView school_textView=null;

	private View view;
	private TypeFace typeFace=null;
	
	private Context mActivity=null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		view=inflater.inflate(R.layout.calender_activity, container, false);
		
		mActivity=getActivity();

		calender_textView=(TextView) view.findViewById(R.id.text_calendar);
		afterschool_textView=(TextView) view.findViewById(R.id.text_afterschool);
		school_textView=(TextView) view.findViewById(R.id.text_school);
		
		typeFace=new TypeFace(mActivity);
		
		typeFace.setTypefaceRegular(afterschool_textView);
		typeFace.setTypefaceRegular(school_textView);
		typeFace.setTypefaceRegular(calender_textView);

		calender_textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		afterschool_textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//new SchoolMainFragment();
			}
		});

		school_textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});


		return view;		
	}
}
