package com.hatchtact.pinwi.view;

import java.util.ArrayList;

import android.content.Context;
import android.widget.ArrayAdapter;


public class AutoCompleteAdapter extends ArrayAdapter<String> 
{
	public AutoCompleteAdapter(Context context, int textViewResourceId,int textViewresource,
			ArrayList<String> timeArray) {
		super(context, textViewResourceId,textViewresource, timeArray);  
		this.items = timeArray;
	}

	private final ArrayList<String> items;

	public ArrayList<String> getItems() {
		return items;
	}
	
}