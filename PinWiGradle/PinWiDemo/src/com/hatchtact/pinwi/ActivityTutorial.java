package com.hatchtact.pinwi;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.ScreenSlidePagerAdapter;
import com.hatchtact.pinwi.adapter.TutorialAdapter;
import com.hatchtact.pinwi.classmodel.TutorialItems;
import com.hatchtact.pinwi.utility.StaticVariables;

public class ActivityTutorial extends MainActionBarActivity implements OnItemClickListener
{
	private ListView listData=null;

	private TutorialAdapter adapter=null;

	private ArrayList<TutorialItems> listDataValue=null;

	private int[] titleImage={R.drawable.tutorial_inside,R.drawable.tutorial_inside,R.drawable.tutorial_inside,
			R.drawable.tutorial_inside,R.drawable.tutorial_inside,R.drawable.tutorial_inside};

	private String[] tutorialTitle={"How PiNWi Works","How To Use The Scheduler","Add A New School Activity","Add A New After School Activity",
			"How To Read Insights?","How do children rate activities?"};

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		screenName="Tutorial";
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_tutorial);
		
		listData=(ListView) findViewById(R.id.list_tutorial); 
		
		listDataValue=new ArrayList<TutorialItems>();
    
		for(int i=0;i<6;i++)
		{   
			TutorialItems tutorialList=new TutorialItems();
			tutorialList.setImageTutorial(titleImage[i]);
			tutorialList.setTextTutorial(tutorialTitle[i]);     
			
			listDataValue.add(tutorialList);	
		}
		
		adapter=new TutorialAdapter(ActivityTutorial.this, listDataValue);
		listData.setAdapter(adapter);
		listData.setOnItemClickListener(ActivityTutorial.this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub

		StaticVariables.currentTutorialValue=position;

		switch (position)
		{
		case 0:
			ScreenSlidePagerAdapter.NUM_PAGES=4;
			break;
		case 1:
			ScreenSlidePagerAdapter.NUM_PAGES=8;

			break;
		case 2:
			ScreenSlidePagerAdapter.NUM_PAGES=6;

			break;
		case 3:
			ScreenSlidePagerAdapter.NUM_PAGES=7;

			break;
		case 4:
			ScreenSlidePagerAdapter.NUM_PAGES=6;

			break;
		case 5:
			ScreenSlidePagerAdapter.NUM_PAGES=8;

			break;

		/*case 6:
			ScreenSlidePagerAdapter.NUM_PAGES=5;

			break;

		case 7:
			ScreenSlidePagerAdapter.NUM_PAGES=5;

			break;
		case 8:
			ScreenSlidePagerAdapter.NUM_PAGES=5;

			break;
		case 9:
			ScreenSlidePagerAdapter.NUM_PAGES=5;

			break;*/


		}

		Intent intent=new Intent(ActivityTutorial.this,TutorialPageActivity.class);
		startActivity(intent);

	}
}
