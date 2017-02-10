package com.hatchtact.pinwi.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;

public class WhatToDoFragment extends ParentFragment
{
	private View view;

	private TextView text_whattodo=null;
	private TextView text_whattodo1=null;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		if(savedInstanceState==null)
		{
			view=inflater.inflate(R.layout.activity_comingsoon, container, false);
			
			text_whattodo=(TextView) view.findViewById(R.id.text_comingsoon1);
			text_whattodo.setText(R.string.comingsoon_text_whattodo);
			
			text_whattodo1=(TextView) view.findViewById(R.id.text_comingsoon2);
			text_whattodo1.setText(R.string.comingsoon_text_whattodo1);
			
			typeFace.setTypefaceLight(text_whattodo);
			typeFace.setTypefaceRegular(text_whattodo1);
			
			mListener.onFragmentAttached(true,"  WhatToDo");
		}
		return view;		
	}
}
