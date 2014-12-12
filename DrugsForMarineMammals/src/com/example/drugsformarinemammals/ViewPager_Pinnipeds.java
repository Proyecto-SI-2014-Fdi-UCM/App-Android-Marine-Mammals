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


public class ViewPager_Pinnipeds extends FragmentActivity {
	
	private MyPagerAdapter adapterViewPager;
	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extra = this.getIntent().getExtras();
        setContentView(R.layout.viewpager_pinnipeds);
        TextView drug = (TextView) findViewById(R.id.title);
        drug.setTypeface(Typeface.SANS_SERIF);
        drug.setText(extra.getCharSequence("drugName"));
        TextView group = (TextView) findViewById(R.id.subtitle);
        group.setTypeface(Typeface.SANS_SERIF);
        PagerTabStrip mPagerTabStrip = (PagerTabStrip) findViewById(R.id.tabs);
        int size = mPagerTabStrip.getChildCount();
        for (int i=0; i<size; i++) {
        	View child = mPagerTabStrip.getChildAt(i);
        	if (child instanceof TextView) {
        		textView = (TextView) child;
        		textView.setTypeface(Typeface.SANS_SERIF);
        	}
        }
        ViewPager vpPager = (ViewPager) findViewById(R.id.vp_pinnipeds);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setCurrentItem(1);
        
	}


	public static class MyPagerAdapter extends FragmentStatePagerAdapter {
		private static int NUM_ITEMS = 3;

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
				return new Fragment_Otharids();
			case 1: 
				return new Fragment_Phocids();
			case 2: 
				return new Fragment_Odobenids();
			default:
				return null;
			}
		}
		
		public CharSequence getPageTitle(int position){
			switch (position) {
			case 0:
				return "Odobenids";
			case 1: 
				return "Otharids";
			case 2: 
				return "Phocids";
			default:
				return null;
			}			
		}

	}
	
}
