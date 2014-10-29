package com.example.drugsformarinemammals;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;


public class ViewPager_MainMenu extends FragmentActivity {
	
	private MyPagerAdapter adapterViewPager;
	ViewPager viewPager = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_mainmenu);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vp_mainmenu);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
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
				return new Fragment_Report();
			case 3: 
				return new Fragment_Calculator();
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
				return "Report your experience";
			case 3: 
				return "Calculator";
			default:
				return null;
			}			
		}

	}

}
