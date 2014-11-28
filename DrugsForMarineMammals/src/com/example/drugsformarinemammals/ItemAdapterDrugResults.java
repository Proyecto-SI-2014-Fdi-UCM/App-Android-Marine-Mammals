package com.example.drugsformarinemammals;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemAdapterDrugResults extends BaseAdapter {
	
	private Context context;
	private List<String> items;
	
	public ItemAdapterDrugResults(Context context, List<String> items) {
		this.context = context;
		this.items = items;		
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public String getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View rowView = convertView;

		if (convertView == null) {
			// Create a new view into the list.
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.item_drugresult, parent, false);
		}

		// Set data into the view.
		TextView nameDrug = (TextView) rowView.findViewById(R.id.textview_drugs);
		nameDrug.setText(items.get(position));
		nameDrug.setTypeface(Typeface.SANS_SERIF);
	
		return rowView;
	}

}
