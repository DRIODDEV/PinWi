package com.hatchtact.pinwi.child;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ChildTutorialScreenSlidePagerAdapter extends FragmentStatePagerAdapter
{
	 private static final int NUM_PAGES = 8;
	
	 public ChildTutorialScreenSlidePagerAdapter(FragmentManager fm) 
	 {
        super(fm);
        
    }

   /* public ScreenSlidePagerAdapter(android.app.FragmentManager fragmentManager) {
		// TODO Auto-generated constructor stub
	}*/

	@Override
    public Fragment getItem(final int position) 
    {
		 return ChildTutorialScreenSlidePageFragment.create(position);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    
}
