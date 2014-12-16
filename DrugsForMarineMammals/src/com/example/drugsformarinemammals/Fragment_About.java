package com.example.drugsformarinemammals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class Fragment_About extends Fragment {

	private View rootView; 
	private ExpandableListView expandableAbout;
	private HashMap<Integer, List<ItemWithImage>> childAbout;
	private List<Integer> headers;
	private ItemExpandableListViewAdapter adapterAbout;

		
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_about,container, false);
		initializeListViewWithImage();
		expandableAbout = (ExpandableListView) rootView.findViewById(R.id.expandableAbout);
		adapterAbout = new ItemExpandableListViewAdapter(getActivity(),headers, childAbout, expandableAbout);
		expandableAbout.setAdapter(adapterAbout);
		expandableAbout.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1, int positionGroup, int positionChild, long arg4) {
				openPdf(positionGroup,positionChild );
				
				return true;
			}
		});

		return rootView;
	}
	
	public void openPdf(int positionGroup, int positionChild ){
		String fileName="";
		if(positionGroup==0){
			switch(positionChild){
				case 0: fileName="aGeneralOverview.pdf";
						break;
				case 1:
				case 2:
			}
		}
		else{
			switch(positionChild){
				case 0: fileName="userGuide.pdf";
					break;
				case 1: fileName="disclaimer.pdf";
					break;
				case 2: fileName="authors.pdf";
					break;
			}
		}
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + rootView.getContext().getFilesDir()+ "/"+fileName), "application/pdf");
		startActivity(intent);
	}
		
		
	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}

	private void initializeListViewWithImage() {

		childAbout = new HashMap<Integer, List<ItemWithImage>>();
		List<ItemWithImage> itemsAboutMarineMammals = new ArrayList<ItemWithImage>();
		headers = new ArrayList<Integer>();
		itemsAboutMarineMammals.add(new ItemWithImage(R.drawable.adobe_reader, "Marine Mammals: a general overview"));
		itemsAboutMarineMammals.add(new ItemWithImage(R.drawable.adobe_reader, "Pharmacological treatments in Marine Mammals"));
		itemsAboutMarineMammals.add(new ItemWithImage(R.drawable.adobe_reader, "Routes for administering drugs to Marine Mammals"));
		childAbout.put(1, itemsAboutMarineMammals);
		headers.add(1);
		
		List<ItemWithImage> itemsAboutFormulary = new ArrayList<ItemWithImage>();
		itemsAboutFormulary.add((new ItemWithImage(R.drawable.adobe_reader, "The MMF: User guide")));
		itemsAboutFormulary.add((new ItemWithImage(R.drawable.adobe_reader, "Disclaimer")));
		itemsAboutFormulary.add((new ItemWithImage(R.drawable.adobe_reader, "Authors")));
		itemsAboutFormulary.add((new ItemWithImage(R.drawable.adobe_reader, "Support")));
		itemsAboutFormulary.add((new ItemWithImage(R.drawable.adobe_reader, "Rate The App")));
		childAbout.put(2, itemsAboutFormulary);
		headers.add(2);

	}		
	
}



