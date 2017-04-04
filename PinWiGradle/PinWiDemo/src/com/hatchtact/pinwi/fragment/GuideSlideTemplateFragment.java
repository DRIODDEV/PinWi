package com.hatchtact.pinwi.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.adapter.GuideSlideTemplateAdapter;
import com.hatchtact.pinwi.adapter.GuideSlideTemplateAdapter.SwipeCallback;
import com.hatchtact.pinwi.child.CustomDrawable;
import com.hatchtact.pinwi.child.FlipAnimation;
import com.hatchtact.pinwi.child.postcard.ChildPostcardActivity;
import com.hatchtact.pinwi.utility.SocialConstants;
import com.hatchtact.pinwi.utility.TypeFace;

@SuppressLint("ValidFragment")
public class GuideSlideTemplateFragment  extends Fragment
{
	public static final String ARG_PAGE = "page";
	private int mPageNumber;
	private TextView template_inner_text,template_back_text;
	//private RelativeLayout template_main_layout,template_back_layout,template_front_layout;
	private RelativeLayout template_front_layout;
	private TextView template_inner_text_why,lineView,template_inner_text_skip;
	private LinearLayout templatebackLayout;
	private ImageView crossImage,bgGuide,bgImage;
	private TextView stepsText,detailText;
	private TypeFace typeFace;
	private SwipeCallback swipeCallback;
	public static boolean isSlider=false;
	private Animation fadeCard,showCard;
	private View emptyView;
	private ImageView img_cancel;
	private SocialConstants social;


	public static GuideSlideTemplateFragment create(int pageNumber, SwipeCallback swipeCallback) {
		GuideSlideTemplateFragment fragment = new GuideSlideTemplateFragment(swipeCallback);
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);

		System.out.println("in screen slide" +pageNumber);
		return fragment;
	}

	public GuideSlideTemplateFragment(){
	}

	public GuideSlideTemplateFragment(SwipeCallback swipeCallback){
		this.swipeCallback = swipeCallback;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(social==null)
		{
			social=new SocialConstants(getActivity());
		}
		mPageNumber = getArguments().getInt(ARG_PAGE);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout containing a title and body text.
		final ViewGroup rootView = (ViewGroup) inflater
				.inflate(R.layout.fragment_guidefrag, container, false);
		if(social==null)
		{
			social=new SocialConstants(getActivity());
		}
		templatebackLayout = (LinearLayout)rootView.findViewById(R.id.templatebackLayout);
		template_front_layout = (RelativeLayout)rootView.findViewById(R.id.template_front_layout);
		template_inner_text_why = (TextView)rootView.findViewById(R.id.template_inner_text_why);
		template_inner_text_skip = (TextView)rootView.findViewById(R.id.template_inner_text_skip);
		crossImage = (ImageView)rootView.findViewById(R.id.crossImage);
		stepsText = (TextView)rootView.findViewById(R.id.stepsText);
		detailText = (TextView)rootView.findViewById(R.id.detailText);
		emptyView=(View)rootView.findViewById(R.id.emptyView);
		bgGuide = (ImageView)rootView.findViewById(R.id.bgGuide);
		bgImage = (ImageView)rootView.findViewById(R.id.bgImage);
		img_cancel = (ImageView)rootView.findViewById(R.id.img_cancel);

		//setBackgroundOfViews(template_front_layout,true);
		//setBackgroundOfViews(template_inner_text,false);
		//setBackgroundOfViews(template_back_layout);
		/*stepsText.setText(ChildPostcardActivity.templateArray.get(mPageNumber));
		detailText.setText(ChildPostcardActivity.templateArray.get(mPageNumber));*/
		if(mPageNumber==(GuideSlideTemplateAdapter.NUM_PAGES-1))
		{
			stepsText.setText("That's it!");
		}
		else
		{
			stepsText.setText("Step "+(mPageNumber+1) +" of "+GuideSlideTemplateAdapter.NUM_PAGES);
		}
		detailText.setText(getActivity().getResources().getStringArray(R.array.guidestepdetails)[mPageNumber]);


		switch (mPageNumber)
		{
			case 0:
				bgGuide.setBackgroundResource(R.drawable.stepone);
				//bgGuide.setBackgroundColor(getActivity().getResources().getColor(R.color.steponebackground));
				//bgImage.setBackgroundResource(R.drawable.guide_one);
				break;
			case 1:
				bgGuide.setBackgroundResource(R.drawable.steptwo);
				//bgGuide.setBackgroundColor(getActivity().getResources().getColor(R.color.steptwobackground));

				//bgImage.setBackgroundResource(R.drawable.guide_two);
				break;
			case 2:
				bgGuide.setBackgroundResource(R.drawable.stepthree);
				//bgGuide.setBackgroundColor(getActivity().getResources().getColor(R.color.stepthreebackground));

				//bgImage.setBackgroundResource(R.drawable.guide_three);
				break;
			case 3:
				bgGuide.setBackgroundResource(R.drawable.stepfour);
				//bgGuide.setBackgroundColor(getActivity().getResources().getColor(R.color.stepfourbackground));

				//bgImage.setBackgroundResource(R.drawable.guide_four);
				break;
			case 4:
				bgGuide.setBackgroundResource(R.drawable.stepfive);
				//bgGuide.setBackgroundColor(getActivity().getResources().getColor(R.color.stepfivebackground));

				//bgImage.setBackgroundResource(R.drawable.guide_five);
				break;
			case 5:
				bgGuide.setBackgroundResource(R.drawable.stepsix);
				//bgGuide.setBackgroundColor(getActivity().getResources().getColor(R.color.stepsixbackground));
				//bgImage.setBackgroundResource(R.drawable.guide_six);
				break;
			case 6:
				bgGuide.setBackgroundResource(R.drawable.stepseven);
				//bgGuide.setBackgroundColor(getActivity().getResources().getColor(R.color.stepsevenbackground));
				//bgImage.setBackgroundResource(R.drawable.guide_seven);
				break;
			default:
				break;
		}
		//setTextColor(template_inner_text);
		//setTopBackground(template_back_text);
		if(isSlider)
		{
			templatebackLayout.setVisibility(View.VISIBLE);
			if(mPageNumber==0)
			{
				if(showCard==null)
				{
					showCard = AnimationUtils.loadAnimation(getActivity(), R.anim.activity_open_scale);
				}
				templatebackLayout.startAnimation(showCard);
			}
			template_front_layout.setVisibility(View.GONE);
		}
		else
		{
			templatebackLayout.setVisibility(View.GONE);
			template_front_layout.setVisibility(View.VISIBLE);
			swipeCallback.isSwipeEnable(false);
		}

		new TypeFace(getActivity()).setTypefaceRegular(template_inner_text_why);
		new TypeFace(getActivity()).setTypefaceRegular(template_inner_text_skip);
		new TypeFace(getActivity()).setTypefaceRegular(stepsText);
		new TypeFace(getActivity()).setTypefaceRegular(detailText);


		crossImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(social!=null)
					social.parent_TutorialAnalyticsLog("Skipped");
				getActivity().finish();
				getActivity().overridePendingTransition(R.anim.shrink_from_topleft_to_bottomright, R.anim.shrink_from_topleft_to_bottomright);

			}
		});
		img_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(social!=null)
					social.parent_TutorialAnalyticsLog("Skipped");
				getActivity().finish();
				getActivity().overridePendingTransition(R.anim.shrink_from_topleft_to_bottomright, R.anim.shrink_from_topleft_to_bottomright);

			}
		});

		emptyView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//templatebackLayout.setVisibility(View.VISIBLE);
				//template_front_layout.setVisibility(View.VISIBLE);
				//if(shake==null)
				/*shake = AnimationUtils.loadAnimation(getActivity(), R.anim.disappear);
				template_front_layout.startAnimation(shake);*/
				if(social!=null)
				{
					social.parent_TutorialAnalyticsLog("Started");
				}
				flipCard();
				//swipeCallback.isSwipeEnable(true);
			}
		});
		template_inner_text_why.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//templatebackLayout.setVisibility(View.VISIBLE);
				//template_front_layout.setVisibility(View.VISIBLE);
				//if(shake==null)
				/*shake = AnimationUtils.loadAnimation(getActivity(), R.anim.disappear);
				template_front_layout.startAnimation(shake);*/

				flipCard();
				//swipeCallback.isSwipeEnable(true);
			}
		});
		template_inner_text_skip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(social!=null)
					social.parent_TutorialAnalyticsLog("Skipped");
				getActivity().finish();
				getActivity().overridePendingTransition(R.anim.shrink_from_topleft_to_bottomright, R.anim.shrink_from_topleft_to_bottomright);

			}
		});




		return rootView;
	}

	@SuppressLint("NewApi")
	private void setTopBackground(TextView viewToChange) {
		String borderHexColor = "#66ffffff";
		int bgColor = Color.parseColor(ChildPostcardActivity.colorArray.get(mPageNumber).getInnerColor()+"");

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
			viewToChange.setBackgroundDrawable(new CustomDrawable(new int[] {
					bgColor, bgColor},15,borderHexColor,2,true));

		} else {
			viewToChange.setBackground(new CustomDrawable(new int[] {
					bgColor,bgColor },15,borderHexColor,2,true));
		}
	}

	private void setTextColor(TextView tv){
		tv.setTextColor(Color.rgb(255,255,255));
		tv.setAlpha(0.7f);
	}
	private void flipCard()
	{
		if(fadeCard==null)
		{
			fadeCard = AnimationUtils.loadAnimation(getActivity(), R.anim.activity_close_scale);
			if(showCard==null)
			{
				showCard = AnimationUtils.loadAnimation(getActivity(), R.anim.activity_open_scale);
			}
			fadeCard.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation)
				{
					isSlider=true;
					swipeCallback.isSwipeEnable(true);
					//templatebackLayout.setVisibility(View.VISIBLE);
					//template_front_layout.setVisibility(View.GONE);
					

					/*if(showCard==null)
					{
						showCard = AnimationUtils.loadAnimation(getActivity(), R.anim.activity_open_scale);
					}*/

				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {

					//templatebackLayout.startAnimation(showCard);
					/*templatebackLayout.setVisibility(View.VISIBLE);
					//template_front_layout.setVisibility(View.GONE);
					isSlider=true;
					swipeCallback.isSwipeEnable(true);
					if(showCard==null)
					{
						showCard = AnimationUtils.loadAnimation(getActivity(), R.anim.activity_open_scale);
					}
					templatebackLayout.startAnimation(showCard);*/

				}
			});
			template_front_layout.startAnimation(fadeCard);
		}
		
		/*FlipAnimation flipAnimation = new FlipAnimation(templatebackLayout, template_front_layout);
		flipAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) 
			{				
			}

			@Override
			public void onAnimationRepeat(Animation animation) {				
			}

			@Override
			public void onAnimationEnd(Animation animation) {	
				if (template_front_layout.getVisibility() == View.VISIBLE){
					child_postcard_pages_bottom.setVisibility(View.VISIBLE);
				}
				//template_front_layout.setAlpha(0f);
				//template_front_layout.setVisibility(View.GONE);
				//if(shake==null)
				//shake = AnimationUtils.loadAnimation(getActivity(), R.anim.appear);

				//templatebackLayout.startAnimation(shake);
				//templatebackLayout.setVisibility(View.VISIBLE);
				templatebackLayout.setVisibility(View.VISIBLE);
				template_front_layout.setVisibility(View.GONE);
				isSlider=true;
				swipeCallback.isSwipeEnable(true);

			}
		});

		if (template_front_layout.getVisibility() == View.GONE){
			flipAnimation.reverse();
		}
		template_front_layout.startAnimation(flipAnimation);*/
	}

	@SuppressLint("NewApi")
	private void setBackgroundOfViews(View viewToChange,boolean isOuter)
	{
		String borderHexColor;
		int bgColor = 0;
		if(isOuter){
			borderHexColor = "#00ffffff";
			bgColor = Color.parseColor(ChildPostcardActivity.colorArray.get(mPageNumber).getOuterColor()+"");
		}else{
			borderHexColor = "#66ffffff";
			bgColor = Color.parseColor(ChildPostcardActivity.colorArray.get(mPageNumber).getInnerColor()+"");
		}

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
			viewToChange.setBackgroundDrawable(new CustomDrawable(new int[] {
					bgColor, bgColor},15,borderHexColor,2,false));

		} else {
			viewToChange.setBackground(new CustomDrawable(new int[] {
					bgColor,bgColor },15,borderHexColor,2,false));
		}
	}


	@SuppressLint("NewApi")
	private void setBackgroundOfViews(View viewToChange)
	{
		String borderHexColor = "#66ffffff";

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
			viewToChange.setBackgroundDrawable(new CustomDrawable(new int[] {
					Color.WHITE, Color.WHITE},15,borderHexColor,2,false));

		} else {
			viewToChange.setBackground(new CustomDrawable(new int[] {
					Color.WHITE,Color.WHITE },15,borderHexColor,2,false));
		}
	}

	public int getPageNumber() {
		return mPageNumber;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
}
