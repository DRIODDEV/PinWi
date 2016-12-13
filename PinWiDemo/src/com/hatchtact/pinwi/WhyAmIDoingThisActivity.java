package com.hatchtact.pinwi;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.TypeFace;

public class WhyAmIDoingThisActivity extends FragmentActivity 
{
	private CheckNetwork checkNetwork=null;
	private SharePreferenceClass sharepref = null;
	private TypeFace typeFace;
	private TextView titleText,stepsText,detailText,text_getStarted;
	private ImageView crossImage;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		WindowManager.LayoutParams params = getWindow().getAttributes();  
		params.x =0;  
		params.height = (int) (SplashActivity.ScreenHeight*.7f);  
		params.width = SplashActivity.ScreenWidth ;  
		params.y = SplashActivity.ScreenHeight/15;  
		this.getWindow().setAttributes(params); 

		setContentView(R.layout.fragment_guide_whydoingthis);
		WhyAmIDoingThisActivity.this.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
		typeFace = new TypeFace(WhyAmIDoingThisActivity.this);
		sharepref = new SharePreferenceClass(WhyAmIDoingThisActivity.this);
		hideKeyBoard();
	
		titleText= (TextView) findViewById(R.id.titleText);
		stepsText= (TextView) findViewById(R.id.stepsText);
		detailText= (TextView) findViewById(R.id.detailText);
		text_getStarted= (TextView) findViewById(R.id.text_getStarted);
		crossImage= (ImageView) findViewById(R.id.crossImage);
		typeFace.setTypefaceRegular(titleText);
		//typeFace.setTypefaceRegular(stepsText);
		typeFace.setTypefaceRegular(detailText);
		typeFace.setTypefaceRegular(text_getStarted);
		detailText.setTextColor(this.getResources().getColor(R.color.font_blackcoloralphanew));
		text_getStarted.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
		crossImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}


	private void hideKeyBoard() 
	{
		try {
			this.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			InputMethodManager inputManager = (InputMethodManager)this
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(this
					.getCurrentFocus().getWindowToken(), 0);
			this.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		} catch (Exception e) {
		}

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		WhyAmIDoingThisActivity.this.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);

		//WhyAmIDoingThisActivity.this.overridePendingTransition(R.anim.shrink_from_topleft_to_bottomright, R.anim.shrink_from_topleft_to_bottomright);
		//super.onBackPressed();
	} 

}
