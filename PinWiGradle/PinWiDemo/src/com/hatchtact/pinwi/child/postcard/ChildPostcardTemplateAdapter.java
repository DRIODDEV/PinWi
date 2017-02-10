package com.hatchtact.pinwi.child.postcard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ChildPostcardTemplateAdapter extends FragmentStatePagerAdapter
{
	public static int NUM_PAGES = 8;
	private SwipeCallback swipeCallback;
	
	public ChildPostcardTemplateAdapter(FragmentManager fm,SwipeCallback mSwipeCallback) {
		super(fm); 
		swipeCallback = mSwipeCallback;
	}

	@Override
	public Fragment getItem(final int position) {
		return ChildPostcardTemplateFragment.create(position,swipeCallback);
	}

	@Override
	public int getCount() {
		return NUM_PAGES;
	}
	
	interface SwipeCallback{
		public void isSwipeEnable(boolean isSwipeEnable);
	}

}
