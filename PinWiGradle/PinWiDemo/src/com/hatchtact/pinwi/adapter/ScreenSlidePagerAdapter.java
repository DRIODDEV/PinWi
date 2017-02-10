 package com.hatchtact.pinwi.adapter;

import com.hatchtact.pinwi.fragment.ScreenSlidePageFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter
{
	 public static int NUM_PAGES = 4;
	 
	 public ScreenSlidePagerAdapter(FragmentManager fm) {
         super(fm);
     }

    /* public ScreenSlidePagerAdapter(android.app.FragmentManager fragmentManager) {
		// TODO Auto-generated constructor stub
	}*/

	@Override
     public Fragment getItem(int position) 
     {
		System.out.println("position" +position);
         return ScreenSlidePageFragment.create(position);
     }

     @Override
     public int getCount() {
         return NUM_PAGES;
     }

}
