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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Fragment_Formulary extends Fragment {
	
	private ArrayList<ItemWithImage> options=new ArrayList<ItemWithImage>();
	private ListView list;
	private View rootView;
	private Handler_Sqlite helper;
	private ArrayAdapter<String> adapterDrugNames;
	private String userEntry;
	private AutoCompleteTextView actv;
	
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
		    		case 2: Intent intentResults = new Intent(getActivity(),Listview_DrugResults.class);
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

		alertDialogBuilder.setView(promptsView);
		
		actv = (AutoCompleteTextView) promptsView.findViewById(R.id.autoCompleteTextView1);
		actv.setTypeface(Typeface.SANS_SERIF);
		actv.setHint("Enter name of drug");
		initializeArrayWithDrugName();
        
		alertDialogBuilder.setCancelable(false).setPositiveButton("Search",new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
					    	userEntry=actv.getText().toString();
					    	if(helper.existDrug(userEntry)){
					    		Intent intent = new Intent(getActivity(),General_Info_Drug.class);
					    		intent.putExtra("drugName", userEntry);
						    	startActivity(intent);
					    	}
					    	else{
					    		new AlertDialog.Builder(getActivity())
								.setTitle("NOT FOUND")
								.setMessage("Drug hasn't been found ")
								.setPositiveButton("OK", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {}
								})
								.show();
					    	}
					    		
					    	
					    	
					    }
				})
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}
					});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		actv.setAdapter(adapterDrugNames);
		
		// show it
		alertDialog.show();
	}
	
	public void initializeArrayWithDrugName() {
		// TODO Auto-generated method stub
		helper=new Handler_Sqlite(getActivity());
		helper.open();
		
		ArrayList<String> drugNames=helper.read_drugs_name();
		adapterDrugNames = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,drugNames);

		helper.close();
	}	
}