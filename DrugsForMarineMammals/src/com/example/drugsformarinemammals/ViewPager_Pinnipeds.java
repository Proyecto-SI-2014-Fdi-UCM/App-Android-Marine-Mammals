package com.example.drugsformarinemammals;

import java.util.ArrayList;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class ViewPager_Pinnipeds extends FragmentActivity {
	
	private MyPagerAdapter adapterViewPager;
	private TextView textView;
	private static Bundle extra;
	private static ArrayList<String> families;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		extra = this.getIntent().getExtras();
        setContentView(R.layout.viewpager_pinnipeds);
        TextView drug = (TextView) findViewById(R.id.title);
        if (extra != null) {
	        drug.setText(extra.getCharSequence("drugName"));
	        Handler_Sqlite helper = new Handler_Sqlite(this);
			SQLiteDatabase db = helper.open();
			families = new ArrayList<String>();
			if (db!=null)
				families = helper.read_animals_family(extra.getString("drugName"), "Pinnipeds");
        }
        drug.setTypeface(Typeface.SANS_SERIF);
        TextView group = (TextView) findViewById(R.id.subtitle);
        group.setTypeface(Typeface.SANS_SERIF);
        PagerTabStrip mPagerTabStrip = (PagerTabStrip) findViewById(R.id.tabs);
        if (families.size() == 1)
        	mPagerTabStrip.setTabIndicatorColor(getResources().getColor(android.R.color.white));
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
		
		private int size;
		
		public MyPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		// Returns total number of pages
		@Override
		public int getCount() {
			return families.size();
		}

		// Returns the fragment to display for that page
		@Override
		public Fragment getItem(int position) {
			size = families.size();
			Bundle arguments = new Bundle();
	        arguments.putCharSequence("drugName", extra.getCharSequence("drugName"));
			switch (position) {
			case 0:
				if (!(families.indexOf("ODOBENIDS") == -1))
					arguments.putCharSequence("familyName", "Odobenids");
				else if (!(families.indexOf("OTARIIDS") == -1))
					arguments.putCharSequence("familyName", "Otariids");
				else
					arguments.putCharSequence("familyName", "Phocids");
				
				return Fragment_Pinnipeds.newInstance(arguments);
				
			case 1:
				if (!(families.indexOf("") == -1))
					arguments.putCharSequence("familyName", "");
				else if (!(families.indexOf("ODOBENIDS") == -1) && !(families.indexOf("OTARIIDS") == -1))
					arguments.putCharSequence("familyName", "Otariids");
				else
					arguments.putCharSequence("familyName", "Phocids");
				
				return Fragment_Pinnipeds.newInstance(arguments);

			case 2:
				if (!(families.indexOf("") == -1) && !(families.indexOf("ODOBENIDS") == -1) &&
						!(families.indexOf("OTARIIDS") == -1))
					arguments.putCharSequence("familyName", "Otariids");
				else
					arguments.putCharSequence("familyName", "Phocids");
				
				return Fragment_Pinnipeds.newInstance(arguments);
				
			case 3:
				arguments.putCharSequence("familyName", "Phocids");
				return Fragment_Pinnipeds.newInstance(arguments);
				
			default:
				return null;
			}
		}
		
		public CharSequence getPageTitle(int position) {
			size = families.size();
			switch (position) {
			case 0: 
				if (!(families.indexOf("ODOBENIDS") == -1))
					return "ODOBENIDS";
				else if (!(families.indexOf("OTARIIDS") == -1))
					return "OTARIIDS";
				else
					return "PHOCIDS";
			case 1:
				if (!(families.indexOf("") == -1))
					return "GENERAL";
				else if (!(families.indexOf("ODOBENIDS") == -1) && !(families.indexOf("OTARIIDS") == -1))
					return "OTARIIDS";
				else
					return "PHOCIDS";
			case 2:
				if (!(families.indexOf("") == -1) && !(families.indexOf("ODOBENIDS") == -1) &&
						!(families.indexOf("OTARIIDS") == -1))
					return "OTARIIDS";
				else
					return "PHOCIDS";
			case 3:
				return "PHOCIDS";
			default:
				return null;
			}
		}

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dose_information, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
        case R.id.calculatorIcon:
        	Intent c = new Intent(this, ViewPager_MainMenu.class);        	
        	c.putExtra("dosis", 2);
			startActivity(c);
			return true;
        case R.id.reportIcon:
        	Intent r = new Intent(this, ViewPager_MainMenu.class);
        	r.putExtra("dosis", 3);
        	startActivity(r);
        	return true;
        default:
            return super.onOptionsItemSelected(item);
		}
    }
	
}
