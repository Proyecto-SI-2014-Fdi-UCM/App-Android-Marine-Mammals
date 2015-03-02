package com.example.drugsformarinemammals;

import java.util.HashMap;
import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemExpandableListViewAdapter extends BaseExpandableListAdapter{

	private Context _context;
    private List<Integer> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<Integer, List<ItemWithImage>> _listDataChild;
    private ExpandableListView _expandableAbout;
    
    public ItemExpandableListViewAdapter(Context context, List<Integer> listDataHeader,
    		HashMap<Integer, List<ItemWithImage>> listChildData,ExpandableListView expandableAbout) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this._expandableAbout=expandableAbout;
    }
 
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
 
        final ItemWithImage childText = (ItemWithImage) getChild(groupPosition, childPosition);
 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_with_image, null);
        }
        
        
     // Set data into the view.
 		ImageView imageItem = (ImageView) convertView.findViewById(R.id.imageItem);
 		TextView textSearch = (TextView) convertView.findViewById(R.id.textSearch);

 		
 		//textSearch.setPaintFlags(textSearch.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
 		
 		
 		//ItemWithImage item = (ItemWithImage) _listDataChild.get(groupPosition).get(childPosition);
 		textSearch.setTypeface(Typeface.SANS_SERIF);
 		//textSearch.setText(item.getTitle());
 		textSearch.setText(childText.getTitle());
 		
 		String tmp=childText.getImage();
 		if(tmp!=null)
 			Picasso.with(_context).load(childText.getImage()).into(imageItem);
 		else
 			Picasso.with(_context).load(childText.getImageId()).into(imageItem);  
          		
        
        
        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
    	 return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                 .size();
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        Integer headerTitle = (Integer) getGroup(groupPosition);
        if (convertView == null) {
        	_expandableAbout.expandGroup(groupPosition);
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.header_expandable_listview, null);
        }
        String categoryHeader = "";
        switch (headerTitle) {
        	case 1:
        		categoryHeader = "About Marine Mammals";
        		break;
        	case 2:
        		categoryHeader = "About Formulary";
        		break;
        	/*case 3:
        		categoryHeader = "Pan y Bolleria";
        		break;
        	case 4:
        		categoryHeader = "Bebidas";
        		break;
        	case 5:
        		categoryHeader = "Carnes";
        		break;
        	case 6:
        		categoryHeader = "Pescados";
        		break;
        	case 7:
        		categoryHeader = "Condimentos";
        		break;
        	case 8:
        		categoryHeader = "Pastas y Arroces";
        		break;
        	case 9:
        		categoryHeader = "Congelados";
        		break;
        	case 10:
        		categoryHeader = "Salsas";
        		break;
        	case 11:
        		categoryHeader = "Drogueria";
        		break;
        	case 12:
        		categoryHeader = "Varios";
        		break;*/
        	
        }
 
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.textViewProductsList);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(categoryHeader);
 
        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
	
}
