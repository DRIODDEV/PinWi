package com.hatchtact.pinwi;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.utility.StaticVariables;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class ApptTutorialActivity extends Activity 
{

	private ImageView appTutorialImage;
	private Integer[] arrayImages={R.drawable.screen1,R.drawable.screen2,R.drawable.screen3,R.drawable.screen4,R.drawable.screen1};

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apptutorial);
		/*appTutorialImage=(ImageView) findViewById(R.id.apptutorialimage);
		appTutorialImage.setImageResource(arrayImages[StaticVariables.currentTutorialValue - 1]);*/
		
	}
}