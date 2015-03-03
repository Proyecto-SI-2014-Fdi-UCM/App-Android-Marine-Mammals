package com.example.drugsformarinemammals;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemAdapterWithImageFormulary extends BaseAdapter {

	private Context context;
	private List<ItemWithImage> items;

	public ItemAdapterWithImageFormulary(Context context, List<ItemWithImage> items) {
		this.context = context;
		this.items = items;
	}

	@Override
	public int getCount() {
		return this.items.size();
	}

	@Override
	public Object getItem(int position) {
		return this.items.get(position);
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
			rowView = inflater.inflate(R.layout.item_with_image_formulary, parent, false);
		}

		// Set data into the view.
		ImageView imageItem = (ImageView) rowView.findViewById(R.id.imageItem);
		TextView textSearch = (TextView) rowView.findViewById(R.id.textSearch);

		ItemWithImage item = this.items.get(position);
		textSearch.setTypeface(Typeface.SANS_SERIF);
		textSearch.setText(item.getTitle());
		
		String tmp=item.getImage();
		if (tmp!=null)
			Picasso.with(context).load(item.getImage()).into(imageItem);
		else
			Picasso.with(context).load(item.getImageId()).into(imageItem);
		return rowView;
	}
	
}


