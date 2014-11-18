package com.example.drugsformarinemammals;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Combined_Search extends Activity {

	private String userEntryAnatomicalTarget;
	private String userEntryTherapeuticTarget;
	private String userEntryAnimalTarget;
	private ArrayAdapter<String> adapterTherapeuticTarget;
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
        Button go=(Button)findViewById(R.id.goButton);
        go.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				search();
			}
        	
        });
        
        
        Spinner spinnerAnatomicalTarget = (Spinner)findViewById(R.id.SpinAnatomicalTarget);  
        ArrayAdapter<CharSequence> adapterAnatomicalTarget = ArrayAdapter.createFromResource(this,R.array.AnatomicalTarget, android.R.layout.simple_spinner_item);	     
		adapterAnatomicalTarget.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
        
        
		adapterTherapeuticTarget = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,helper.getAllTherapeuticGroup());	     
		adapterTherapeuticTarget.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
        ArrayAdapter<CharSequence> adapterAnimals = ArrayAdapter.createFromResource(this, R.array.Animals, android.R.layout.simple_spinner_item);	     
		adapterAnimals.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
			new AlertDialog.Builder(this)
			.setTitle("NOT FOUND")
			.setMessage("Drugs haven't been found with the specified parameters")
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			})
			.show();
		}
			
	}
	
	
}
