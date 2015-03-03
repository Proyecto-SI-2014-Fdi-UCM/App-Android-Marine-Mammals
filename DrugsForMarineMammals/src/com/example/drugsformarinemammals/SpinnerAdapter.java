package com.example.drugsformarinemammals;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends ArrayAdapter<String> {
	
	public SpinnerAdapter (Context context, int resource, List<String> items) {
		super(context, resource, items);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view = (TextView) super.getView(position, convertView, parent);
		view.setTypeface(Typeface.SANS_SERIF);
		return view;
	}

	public View getDropDownView (int position, View convertView, ViewGroup parent) {
		TextView view = (TextView) super.getDropDownView(position, convertView, parent);
		view.setTypeface(Typeface.SANS_SERIF);
		return view;
	}
}
