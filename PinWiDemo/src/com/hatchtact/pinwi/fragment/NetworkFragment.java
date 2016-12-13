package com.hatchtact.pinwi.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.fragment.insights.ParentFragment;

public class NetworkFragment extends ParentFragment
{
	private View view;
	private TextView text_network=null;
	private TextView text_network1=null;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		if(savedInstanceState==null)
		{
			view=inflater.inflate(R.layout.activity_comingsoon, container, false);
			
			text_network=(TextView) view.findViewById(R.id.text_comingsoon1);
			text_network.setText(R.string.comingsoon_text_network);
			
			text_network1=(TextView) view.findViewById(R.id.text_comingsoon2);
			text_network1.setText(R.string.comingsoon_text_network1);
			
			typeFace.setTypefaceLight(text_network);
			typeFace.setTypefaceRegular(text_network1);
			
			mListener.onFragmentAttached(true,"  Network");
		}
		return view;		
	}
}
