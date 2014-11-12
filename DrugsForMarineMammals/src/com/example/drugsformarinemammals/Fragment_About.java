package com.example.drugsformarinemammals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class Fragment_About extends Fragment implements OnClickListener {

	private View rootView; 
	private ExpandableListView expandableAbout;
	private HashMap<Integer, List<ItemWithImage>> childAbout;
	private List<Integer> headers;
	private ItemExpandableListViewAdapter adapterAbout;

	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    	
			rootView = inflater.inflate(R.layout.fragment_about,container, false);
			initializeListViewWithImage();
			expandableAbout = (ExpandableListView) rootView.findViewById(R.id.expandableAbout);
			adapterAbout = new ItemExpandableListViewAdapter(getActivity(),headers,childAbout,expandableAbout);
			expandableAbout.setAdapter(adapterAbout);
			expandableAbout.setOnChildClickListener(new OnChildClickListener() {

				@Override
				public boolean onChildClick(ExpandableListView arg0, View arg1,

					int positionGroup, int positionChild, long arg4) {
					//modificarProducto(arg1,positionGroup,positionChild);
					return true;
				}
			});

			return rootView;
			
	    }
	
		private void initializeListViewWithImage() {
		
			childAbout = new HashMap<Integer,List<ItemWithImage>>();
			List<ItemWithImage> itemsAboutMarineMammals = new ArrayList<ItemWithImage>();
			headers=new ArrayList<Integer>();
			itemsAboutMarineMammals.add(new ItemWithImage(R.drawable.adobe_reader,"Marine Mammals: a general overview (by DGP)"));
			itemsAboutMarineMammals.add(new ItemWithImage(R.drawable.adobe_reader,"Pharmacological treatments in Marine Mammals (by UCM)"));
			itemsAboutMarineMammals.add(new ItemWithImage(R.drawable.adobe_reader,"Routes for administering drugs to Marine Mammals (by UCM)"));
			childAbout.put(1,itemsAboutMarineMammals);
			headers.add(1);
			
			List<ItemWithImage> itemsAboutFormulary=new ArrayList<ItemWithImage>();
			itemsAboutFormulary.add((new ItemWithImage(R.drawable.adobe_reader,"The MMF: User guide (by UCM)")));
			itemsAboutFormulary.add((new ItemWithImage(R.drawable.adobe_reader,"Disclaimer")));
			itemsAboutFormulary.add((new ItemWithImage(R.drawable.adobe_reader,"Authors")));
			itemsAboutFormulary.add((new ItemWithImage(R.drawable.adobe_reader,"Support")));
			itemsAboutFormulary.add((new ItemWithImage(R.drawable.adobe_reader,"Rate The App")));
			childAbout.put(2, itemsAboutFormulary);
			headers.add(2);
                
		}
		
	
}



