package com.example.drugsformarinemammals;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Fragment_Formulary extends Fragment {
	
	private ArrayList<ItemWithImage> options=new ArrayList<ItemWithImage>();
	private ListView list;
	private View rootView;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		rootView = inflater.inflate(R.layout.fragment_formulary, container, false);	
		initializeArrayListSearchs();
		list = (ListView)rootView.findViewById(R.id.listViewTypeSearch);
		ItemAdapterWithImageFormulary adapter;
		// Inicializamos el adapter.
		adapter = new ItemAdapterWithImageFormulary(getActivity(),options);
		// Asignamos el Adapter al ListView, en este punto hacemos que el
		// ListView muestre los datos que queremos.
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener(){	 
		    @Override
		    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		    	
		    	switch(position){
	    		
		    		// Single Search
		    		case 0: open_dialog();
		        			break;
		        
		        	// Combined Search	        		
		    		case 1: Intent intent = new Intent(getActivity(),Combined_Search.class);
        					startActivity(intent);
        					break;
		        
        			// Five Last Searched    	
		    		case 2: Intent intentResults = new Intent(getActivity(),Results_Search.class);
		        			startActivity(intentResults);
		        			break;
		        		
		    		default:
		        			break;
		    	}
		    	
		    }
		});
		
		return rootView;	
	}	
	
	private void initializeArrayListSearchs() {
		options.add(new ItemWithImage(R.drawable.single_search,"Single Search"));
		options.add(new ItemWithImage(R.drawable.combined_search,"Combined Search"));
		options.add(new ItemWithImage(R.drawable.five_last_searched,"Five Last Searched"));
	}
	
	public void open_dialog() {

		LayoutInflater li = LayoutInflater.from(getActivity());
		View promptsView = li.inflate(R.layout.dialog_single_search, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
		userInput.setTypeface(Typeface.SANS_SERIF);
		userInput.setHint("Enter name of drug");
        
		// set dialog message
		alertDialogBuilder.setCancelable(false).setPositiveButton("Search",new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
					    	Intent intent = new Intent(getActivity(),Results_Search.class);
					    	startActivity(intent);
					    	
					    }
				})
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
			    	dialog.cancel();
						}
					});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
	
		
}