 package com.hatchtact.pinwi.adapter;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hatchtact.pinwi.fragment.GetStartedScreenSlidePageFragment;


 public class GetStartedScreenSlidePagerAdapter extends FragmentStatePagerAdapter
{
	 public static int NUM_PAGES = 6;
	 
	 public GetStartedScreenSlidePagerAdapter(FragmentManager fm) {
         super(fm);
     }

    /* public ScreenSlidePagerAdapter(android.app.FragmentManager fragmentManager) {
		// TODO Auto-generated constructor stub
	}*/

	@Override
     public Fragment getItem(int position) 
     {
		System.out.println("position" +position);
         return GetStartedScreenSlidePageFragment.create(position);
     }

     @Override
     public int getCount() {
         return NUM_PAGES;
     }

}
