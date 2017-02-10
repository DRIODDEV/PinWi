package com.hatchtact.pinwi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.adapter.GuideSlideTemplateAdapter;
import com.hatchtact.pinwi.adapter.GuideSlideTemplateAdapter.SwipeCallback;
import com.hatchtact.pinwi.child.postcard.TemplateViewPager;
import com.hatchtact.pinwi.sync.ServiceMethod;
import com.hatchtact.pinwi.utility.CheckNetwork;
import com.hatchtact.pinwi.utility.SharePreferenceClass;
import com.hatchtact.pinwi.utility.ShowMessages;
import com.hatchtact.pinwi.utility.SocialConstants;
import com.hatchtact.pinwi.utility.TypeFace;

public class GuideSlideActivity extends FragmentActivity 
{
	private ServiceMethod serviceMethod=null;
	private ShowMessages showMessage=null;
	private CheckNetwork checkNetwork=null;
	private SharePreferenceClass sharepref = null;
	private TypeFace typeFace;
	private GuideSlideTemplateAdapter guideSlideTemplateAdapter;
	private LinearLayout layout_slide_bottomlayer;
	private TextView text_previous,text_next,text_why,text_getStarted;
	private TemplateViewPager guideSlideTemplate;
	private View emptyView;
	//protected SocialConstants social;



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

		setContentView(R.layout.activity_guideslide);
		GuideSlideActivity.this.overridePendingTransition(R.anim.grow_from_bottomright_to_topleft, R.anim.activity_close);
		typeFace = new TypeFace(GuideSlideActivity.this);
		//social=new SocialConstants(this);

		sharepref = new SharePreferenceClass(GuideSlideActivity.this);
		hideKeyBoard();
		guideSlideTemplate = (TemplateViewPager) findViewById(R.id.postcard_templates);
		layout_slide_bottomlayer = (LinearLayout) findViewById(R.id.layout_slide_bottomlayer);
		text_previous= (TextView) findViewById(R.id.text_previous);
		text_next= (TextView) findViewById(R.id.text_next);
		text_why= (TextView) findViewById(R.id.text_why);
		text_getStarted= (TextView) findViewById(R.id.text_getStarted);
		layout_slide_bottomlayer.setWeightSum(3f);
		text_getStarted.setVisibility(View.GONE);
		emptyView= (View) findViewById(R.id.emptyView);
		typeFace.setTypefaceRegular(text_next);
		typeFace.setTypefaceRegular(text_why);
		typeFace.setTypefaceRegular(text_previous);
		typeFace.setTypefaceRegular(text_getStarted);

		text_previous.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(guideSlideTemplate.getCurrentItem()>0){
					guideSlideTemplate.setCurrentItem(guideSlideTemplate.getCurrentItem()-1,true);
				}else{
					//guideSlideTemplate.setCurrentItem(ChildPostcardTemplateAdapter.NUM_PAGES-1,true);

				}
			}
		});

		text_next.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				/*if (guideSlideTemplate.getCurrentItem() == ChildPostcardTemplateAdapter.NUM_PAGES-1) {
					guideSlideTemplate.setCurrentItem(0,true);
			    }else*/ 
				if(guideSlideTemplate.getCurrentItem() < (GuideSlideTemplateAdapter.NUM_PAGES))
				{
					guideSlideTemplate.setCurrentItem(guideSlideTemplate.getCurrentItem()+1,true);
				}	
			}
		});
		text_why.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				startActivity(new Intent(GuideSlideActivity.this,WhyAmIDoingThisActivity.class));

			}
		});
		text_getStarted.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		text_next.setVisibility(View.GONE);
		text_previous.setVisibility(View.GONE);
		text_why.setVisibility(View.GONE);
		emptyView.setVisibility(View.GONE);
		guideSlideTemplate.setOnPageChangeListener(onPageChangeListener);

		guideSlideTemplateAdapter = new GuideSlideTemplateAdapter(getSupportFragmentManager(),new SwipeCallback() {
			@Override
			public void isSwipeEnable(boolean isSwipeEnable) {
				guideSlideTemplate.setPagingEnabled(isSwipeEnable);
				if(isSwipeEnable){
					layout_slide_bottomlayer.setWeightSum(2f);
					text_next.setVisibility(View.VISIBLE);
					text_previous.setVisibility(View.GONE);
					text_why.setVisibility(View.VISIBLE);
					guideSlideTemplateAdapter.notifyDataSetChanged();
					emptyView.setVisibility(View.VISIBLE);
				}else{
					text_next.setVisibility(View.GONE);
					text_previous.setVisibility(View.GONE);
					text_why.setVisibility(View.GONE);
					emptyView.setVisibility(View.GONE);
				}
			}
		});
		GuideSlideTemplateAdapter.NUM_PAGES = 7;
		guideSlideTemplate.setAdapter(guideSlideTemplateAdapter);

		/*if(StaticVariables.selectedPostcardIndex <= (ChildPostcardTemplateAdapter.NUM_PAGES - 1)){
			guideSlideTemplate.setCurrentItem(StaticVariables.selectedPostcardIndex, true);
		}*/
	}

	private int previousState, currentState;

	private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int pageSelected) {
		}

		@Override
		public void onPageScrolled(int pageSelected, float positionOffset,
				int positionOffsetPixel) {
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			int currentPage = guideSlideTemplate.getCurrentItem();
			if (currentPage == (GuideSlideTemplateAdapter.NUM_PAGES-1)/* || currentPage == 0*/) 
			{
				layout_slide_bottomlayer.setWeightSum(4f);
				text_previous.setVisibility(View.GONE);
				text_next.setVisibility(View.GONE);
				text_why.setVisibility(View.GONE);
				text_getStarted.setVisibility(View.VISIBLE);
			/*	social.CompletedTutorialFacebookLog();
				social.CompletedTutorialGoogleAnalyticsLog();*/
				/* previousState = currentState;
			    currentState = state;
			    if (previousState == 1 && currentState == 0) {
			    	//postcard_templates.setCurrentItem(currentPage == 0 ?  (ChildPostcardTemplateAdapter.NUM_PAGES-1) : 0,true);
			    }*/
			}
			else if (currentPage == 0) 
			{
				layout_slide_bottomlayer.setWeightSum(2f);
				text_previous.setVisibility(View.GONE);
				text_next.setVisibility(View.VISIBLE);
				text_why.setVisibility(View.VISIBLE);
				text_getStarted.setVisibility(View.GONE);

				/* previousState = currentState;
			    currentState = state;
			    if (previousState == 1 && currentState == 0) {
			    	//postcard_templates.setCurrentItem(currentPage == 0 ?  (ChildPostcardTemplateAdapter.NUM_PAGES-1) : 0,true);
			    }*/
			}
			else
			{
				layout_slide_bottomlayer.setWeightSum(3f);
				text_previous.setVisibility(View.VISIBLE);
				text_next.setVisibility(View.VISIBLE);
				text_why.setVisibility(View.VISIBLE);
				text_getStarted.setVisibility(View.GONE);

			}
		}
	};


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
		GuideSlideActivity.this.overridePendingTransition(R.anim.shrink_from_topleft_to_bottomright, R.anim.shrink_from_topleft_to_bottomright);
		//super.onBackPressed();
	} 

}
