package com.example.drugsformarinemammals;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class Combined_Search extends Activity {

	private String userEntryAnatomicalTarget;
	private String userEntryTherapeuticTarget;
	private String userEntryAnimalTarget;
	private SpinnerAdapter adapterTherapeuticTarget;
	private Spinner spinnerTherapeuticTarget;
	private Handler_Sqlite helper;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combined_search);
        userEntryAnatomicalTarget = "";
    	userEntryTherapeuticTarget = "";
    	userEntryAnimalTarget = "";
    	helper=new Handler_Sqlite(this);
    	helper.open();
    	TextView title = (TextView)findViewById(R.id.CombinedSearch);
    	title.setTypeface(Typeface.SANS_SERIF);
        Button go=(Button)findViewById(R.id.goButton);
        go.setTypeface(Typeface.SANS_SERIF);
        go.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				search();
			}
        	
        });
		
        Spinner spinnerAnatomicalTarget = (Spinner)findViewById(R.id.SpinAnatomicalTarget);
        SpinnerAdapter adapterAnatomicalTarget = new SpinnerAdapter(this, R.layout.item_spinner, Arrays.asList(getResources().getStringArray(R.array.AnatomicalTarget)));
        adapterAnatomicalTarget.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerAnatomicalTarget.setAdapter(adapterAnatomicalTarget);
		spinnerAnatomicalTarget.setOnItemSelectedListener(new OnItemSelectedListener() {

		     public void onItemSelected(AdapterView<?> parent, View arg1,int arg2, long arg3) {
		    	 userEntryAnatomicalTarget = parent.getSelectedItem().toString(); 	 
		     }

		     public void onNothingSelected(AdapterView<?> arg0) {
		                // TODO Auto-generated method stub
		     }
		     });

		spinnerTherapeuticTarget = (Spinner)findViewById(R.id.SpinTherapeuticTarget);
		adapterTherapeuticTarget = new SpinnerAdapter(this, R.layout.item_spinner,helper.getAllTherapeuticGroup());	     
		adapterTherapeuticTarget.setDropDownViewResource(R.layout.spinner_dropdown_item);
		spinnerTherapeuticTarget.setAdapter(adapterTherapeuticTarget);
		spinnerTherapeuticTarget.setOnItemSelectedListener(new OnItemSelectedListener() {
			
		     public void onItemSelected(AdapterView<?> parent, View arg1,int arg2, long arg3) {
		    	 userEntryTherapeuticTarget = parent.getSelectedItem().toString();
		    	 if (userEntryTherapeuticTarget.equals("Add new group")){
		    		 open_Dialog();
		    	 }   	 
		     }

		     public void onNothingSelected(AdapterView<?> arg0) {
		                // TODO Auto-generated method stub
		     }
		     });
		
		
		Spinner spinnerAnimals = (Spinner)findViewById(R.id.SpinAnimals);    
		SpinnerAdapter adapterAnimals = new SpinnerAdapter(this, R.layout.item_spinner, Arrays.asList(getResources().getStringArray(R.array.Animals)));
		adapterAnimals.setDropDownViewResource(R.layout.spinner_dropdown_item);
		spinnerAnimals.setAdapter(adapterAnimals);
		
		spinnerAnimals.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View arg1,int arg2, long arg3) {
				userEntryAnimalTarget = parent.getSelectedItem().toString();	 
		    }

		    public void onNothingSelected(AdapterView<?> arg0) {
		                // TODO Auto-generated method stub
		    }
		    });
		
		helper.close();
    }

	

	
		public void open_Dialog() {
			
			LayoutInflater li = LayoutInflater.from(this);
			View promptsView = li.inflate(R.layout.dialog_therapeutic_class_combined_search, null);
			
			final EditText edittext = (EditText)promptsView.findViewById(R.id.editTextDialogUserInput);
			edittext.setHint(R.string.Enter_Therapeutic_Class);
			TextView url= (TextView)promptsView.findViewById(R.id.textViewUrl);
			url.setText(R.string.urlAtcVet);
			url.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("http://www.whocc.no/atcvet/atcvet_index/"));
					startActivity(intent);
				}});
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

			// set prompts.xml to alertdialog builder
			alertDialogBuilder.setView(promptsView);

			//userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
			/*switch (position) {
				case 0: userInput.setHint("Introduzca ingrediente");
						break;
				case 1: userInput.setHint("Introduzca nacionalidad");
						break;
				case 3: userInput.setHint("Introduzca ");
						break;			
			}*/
	        
			// set dialog message
			alertDialogBuilder.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener() {
						    public void onClick(DialogInterface dialog,int id) {
						    	
						    	/*switch (position){
						    	case 0: new GetTitleImageByIngredient().execute();			    
						    			break;
						    			
						    	case 1: new GetTitleImageByNationality().execute();
						    			break;
						    	}*/
						    	
						    
						    	userEntryTherapeuticTarget = edittext.getText().toString();
						    	
						    	adapterTherapeuticTarget.remove("Add new group");
						    	helper.deleteTherapeuticGroup("Add new group");
						    	adapterTherapeuticTarget.add(userEntryTherapeuticTarget);
						    	helper.insertTherapeuticGroup(userEntryTherapeuticTarget);
						    	//adapterTherapeuticTarget.add("Add new group");
						    	adapterTherapeuticTarget.notifyDataSetChanged();
						    	//spinnerTherapeuticTarget.bringToFront();
						    	spinnerTherapeuticTarget.setSelection(spinnerTherapeuticTarget.getCount()-1);
						    	adapterTherapeuticTarget.add("Add new group");
						    	helper.insertTherapeuticGroup("Add new group");
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
		
	public void search() {
		Handler_Sqlite handler = new Handler_Sqlite(this);
		String[] parameters = new String[3];
		parameters[0] = userEntryAnatomicalTarget;
		parameters[1] = userEntryTherapeuticTarget;
		parameters[2] = userEntryAnimalTarget;
		ArrayList<String> drugList = handler.combinedSearch(parameters);
		if (drugList != null && !drugList.isEmpty()) {
			Intent i = new Intent(this, Listview_DrugResults.class);
			i.putExtra("drugList", drugList);
			startActivity(i);
		}
		else {
			AlertDialog.Builder myalert = new AlertDialog.Builder(this);
			
			TextView title = new TextView(this);
			title.setTypeface(Typeface.SANS_SERIF);
			title.setTextSize(20);
			title.setTextColor(getResources().getColor(R.color.blue));
			title.setPadding(8, 8, 8, 8);
			title.setText("NOT FOUND");
			title.setGravity(Gravity.CENTER_VERTICAL);
			
			LinearLayout layout = new LinearLayout(this);
			TextView text = new TextView(this);
			text.setTypeface(Typeface.SANS_SERIF);
			text.setTextSize(20);
			text.setPadding(10, 10, 10, 10);
			text.setText("Drugs haven't been found with the specified parameters");
			layout.addView(text);
			
			myalert.setView(layout);
			myalert.setCustomTitle(title);
			myalert.setCancelable(true);
			myalert.show();
		}
			
	}
	
	
	
}
