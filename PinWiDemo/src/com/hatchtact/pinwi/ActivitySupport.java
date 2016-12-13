package com.hatchtact.pinwi;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ListView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.SupportAdapter;
import com.hatchtact.pinwi.classmodel.SupportItems;

public class ActivitySupport extends MainActionBarActivity
{
	private ListView listData=null;   

	private SupportAdapter adapter=null;

	private ArrayList<SupportItems> listDataValue=null;

	//private View view;

	private String[] titleSupport={"Support1","Support2","Support3","Support4","Support5","Support6","Support7","Support8","Support9","Support10"};

	private String[] supportDescriptionTitle={"Support Description 1..","Support Description 2..","Support Description 3..","Support Description 4..",
			"Support Description 5..","Support Description 6..","Support Description 7..","Support Description 8..","Support Description 9..","Support Description 10.."};

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		screenName="Support";
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_support);
		
		listData=(ListView) findViewById(R.id.list_support); 
		
		listDataValue=new ArrayList<SupportItems>();

		for(int i=0;i<10;i++)
		{
			SupportItems mainList=new SupportItems();
			mainList.setSupportText(titleSupport[i]);
			mainList.setSupportDescription(supportDescriptionTitle[i]);

			listDataValue.add(mainList);	
		}
		adapter=new SupportAdapter(ActivitySupport.this, listDataValue);
		listData.setAdapter(adapter);
	}
}
