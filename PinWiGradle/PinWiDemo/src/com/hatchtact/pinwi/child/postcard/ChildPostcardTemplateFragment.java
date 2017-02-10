package com.hatchtact.pinwi.child.postcard;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.child.CustomDrawable;
import com.hatchtact.pinwi.child.FlipAnimation;
import com.hatchtact.pinwi.child.postcard.ChildPostcardTemplateAdapter.SwipeCallback;
import com.hatchtact.pinwi.utility.StaticVariables;
import com.hatchtact.pinwi.utility.TypeFace;

@SuppressLint("ValidFragment")
public class ChildPostcardTemplateFragment  extends Fragment 
{
	public static final String ARG_PAGE = "page";
	private int mPageNumber;
	private TextView template_inner_text,template_back_text;
	private RelativeLayout template_main_layout,template_back_layout,template_front_layout;
	private TypeFace typeFace;
	private SwipeCallback swipeCallback;
	private ImageView template_back_text_button,template_back_gallery_button,template_back_voice_button,child_postcard_pages_bottom;

	public static ChildPostcardTemplateFragment create(int pageNumber, SwipeCallback swipeCallback) {
		ChildPostcardTemplateFragment fragment = new ChildPostcardTemplateFragment(swipeCallback);
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);
		System.out.println("in screen slide" +pageNumber);
		return fragment;
	}

	public ChildPostcardTemplateFragment(){
	}
	
	public ChildPostcardTemplateFragment(SwipeCallback swipeCallback){
		this.swipeCallback = swipeCallback;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(ARG_PAGE);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout containing a title and body text.
		final ViewGroup rootView = (ViewGroup) inflater
				.inflate(R.layout.fragment_postcard_template, container, false);
		
		template_main_layout = (RelativeLayout)rootView.findViewById(R.id.template_main_layout);
		template_back_layout = (RelativeLayout)rootView.findViewById(R.id.template_back_layout);
		template_front_layout = (RelativeLayout)rootView.findViewById(R.id.template_front_layout);
		setBackgroundOfViews(template_front_layout,true);
		template_inner_text = (TextView)rootView.findViewById(R.id.template_inner_text);
		template_inner_text.setText(ChildPostcardActivity.templateArray.get(mPageNumber));
		setBackgroundOfViews(template_inner_text,false);
		setBackgroundOfViews(template_back_layout);

		child_postcard_pages_bottom = (ImageView) rootView.findViewById(R.id.child_postcard_pages_bottom);
		template_back_text = (TextView)rootView.findViewById(R.id.template_back_text);
		template_back_text.setText(ChildPostcardActivity.templateArray.get(mPageNumber));
		setTextColor(template_inner_text);
		setTopBackground(template_back_text);

		new TypeFace(getActivity()).setTypefaceGotham(template_inner_text);
		new TypeFace(getActivity()).setTypefaceGothamMedium(template_back_text);

		template_back_text_button = (ImageView)rootView.findViewById(R.id.template_back_text_button);
		template_back_gallery_button = (ImageView)rootView.findViewById(R.id.template_back_gallery_button);
		template_back_voice_button = (ImageView)rootView.findViewById(R.id.template_back_voice_button);
		
		template_front_layout.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				child_postcard_pages_bottom.setVisibility(View.INVISIBLE);
				swipeCallback.isSwipeEnable(false);
				flipCard();
			}
		});
		template_back_text.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				flipCard();
				swipeCallback.isSwipeEnable(true);
			}
		});

		template_back_text_button.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				StaticVariables.selectedPostcardIndex = mPageNumber;
				Intent openTextPostcard = new Intent(getActivity(), ChildPostcardDetailingTextActivity.class);
				openTextPostcard.putExtra("ARRAY_VALUE", mPageNumber);
				getActivity().startActivity(openTextPostcard);
				//getActivity().overridePendingTransition(R.anim.activity_open_scale,R.anim.fade_in);
				getActivity().finish();
			}
		});
		
		template_back_gallery_button.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				StaticVariables.selectedPostcardIndex = mPageNumber;
				Intent openTextPostcard = new Intent(getActivity(), ChildPostcardDetailingImageActivity.class);
				openTextPostcard.putExtra("ARRAY_VALUE", mPageNumber);
				getActivity().startActivity(openTextPostcard);
				getActivity().finish();
			}
		});
		
		template_back_voice_button.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				StaticVariables.selectedPostcardIndex = mPageNumber;
				Intent openTextPostcard = new Intent(getActivity(), ChildPostcardDetailingAudioActivity.class);
				openTextPostcard.putExtra("ARRAY_VALUE", mPageNumber);
				getActivity().startActivity(openTextPostcard);
				getActivity().finish();
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
		FlipAnimation flipAnimation = new FlipAnimation(template_front_layout, template_back_layout);
		flipAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {	
				if (template_front_layout.getVisibility() == View.VISIBLE){
					child_postcard_pages_bottom.setVisibility(View.VISIBLE);
			    }
			}
		});
		
	    if (template_front_layout.getVisibility() == View.GONE){
	        flipAnimation.reverse();
	    }
	    template_front_layout.startAnimation(flipAnimation);
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
