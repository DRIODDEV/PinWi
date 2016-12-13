package com.hatchtact.pinwi.adapter;

import com.hatchtact.pinwi.fragment.GuideSlideTemplateFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class GuideSlideTemplateAdapter extends FragmentStatePagerAdapter
{
	public static int NUM_PAGES = 7;
	private SwipeCallback swipeCallback;

	public GuideSlideTemplateAdapter(FragmentManager fm,SwipeCallback mSwipeCallback) {
		super(fm); 
		GuideSlideTemplateFragment.isSlider=false;
		swipeCallback = mSwipeCallback;
	}

	@Override
	public Fragment getItem(final int position) {
		return GuideSlideTemplateFragment.create(position,swipeCallback);
	}

	@Override
	public int getCount() {
		return NUM_PAGES;
	}

	public interface SwipeCallback{
		public void isSwipeEnable(boolean isSwipeEnable);
	}
	public int getItemPosition(Object object) {
		   return POSITION_NONE;
		}
	

}
