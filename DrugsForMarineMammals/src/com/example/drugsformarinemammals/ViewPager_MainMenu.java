package com.example.drugsformarinemammals;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;


public class ViewPager_MainMenu extends FragmentActivity {
	
	private MyPagerAdapter adapterViewPager;
	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_mainmenu);
        PagerTabStrip mPagerTabStrip = (PagerTabStrip) findViewById(R.id.title);
        int size = mPagerTabStrip.getChildCount();
        for (int i=0; i<size; i++) {
        	View child = mPagerTabStrip.getChildAt(i);
        	if (child instanceof TextView) {
        		textView = (TextView) child;
        		textView.setTypeface(Typeface.SANS_SERIF);
        	}
        }
        
        ViewPager vpPager = (ViewPager) findViewById(R.id.vp_mainmenu);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        Bundle extra = this.getIntent().getExtras();
        if (extra != null) {
			if (extra.getInt("dosis") == 2)
				vpPager.setCurrentItem(2);
			else 
				vpPager.setCurrentItem(3);
        }
		else
			vpPager.setCurrentItem(1);
        
	}



	public static class MyPagerAdapter extends FragmentStatePagerAdapter {
		private static int NUM_ITEMS = 4;

		public MyPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		// Returns total number of pages
		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

		// Returns the fragment to display for that page
		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return new Fragment_About();
			case 1: 
				return new Fragment_Formulary();
			case 2: 
				return new Fragment_Calculator();
			case 3: 
				return new Fragment_Report();
			default:
				return null;
			}
		}
		
		public CharSequence getPageTitle(int position){
			switch (position) {
			case 0:
				return "About formulary";
			case 1: 
				return "Formulary";
			case 2: 
				return "Calculator";
			case 3: 
				return "Report your experience";
			default:
				return null;
			}			
		}

	}
	
}