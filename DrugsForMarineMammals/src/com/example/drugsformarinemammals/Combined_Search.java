package com.example.drugsformarinemammals;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Combined_Search extends Activity {

	private String userEntryAnatomicalTarget;
	private String userEntryTherapeuticTarget;
	private String userEntryAnimalTarget;

	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combined_search);
        
        Button go=(Button)findViewById(R.id.goButton);
        go.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				search();
			}
        	
        });
        
        
        Spinner spinnerAnatomicalTarget = (Spinner)findViewById(R.id.SpinAnatomicalTarget);       
        ArrayAdapter<CharSequence> adapterAnatomicalTarget = ArrayAdapter.createFromResource(this, R.array.AnatomicalTarget, android.R.layout.simple_spinner_item);	     
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

		Spinner spinnerTherapeuticTarget = (Spinner)findViewById(R.id.SpinTherapeuticTarget);       
        ArrayAdapter<CharSequence> adapterTherapeuticTarget = ArrayAdapter.createFromResource(this, R.array.TherapeuticTarget, android.R.layout.simple_spinner_item);	     
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
    }

	
//	@Override
//	public void onItemClick(AdapterView<?> parent, View view, int position,
//			long id) {
//		// TODO Auto-generated method stub
//		
//	}


//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		
//	}

	
		public void open_Dialog() {
			
			LayoutInflater li = LayoutInflater.from(this);
			View promptsView = li.inflate(R.layout.dialog_therapeutic_class_combined_search, null);
			TextView text= (TextView)promptsView.findViewById(R.id.Therapeutic_Class);
			text.setText(R.string.Enter_Therapeutic_Class);
			final EditText edittext = (EditText)promptsView.findViewById(R.id.editTextDialogUserInput);
			
			TextView url= (TextView)promptsView.findViewById(R.id.textViewUrl);
			url.setText(R.string.urlAtcVet);
			url.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
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
						    	
						    	//resto de opciones
						    	userEntryTherapeuticTarget = edittext.getText().toString();
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
			String[] parameters = new String[3];
			parameters[0] = userEntryAnatomicalTarget; 
			parameters[1] = userEntryTherapeuticTarget;
			parameters[2] = userEntryAnimalTarget;
			Intent i = new Intent(this, Listview_DrugResults.class);
			i.putExtra("parameters", parameters);
			startActivity(i);
		}
}
