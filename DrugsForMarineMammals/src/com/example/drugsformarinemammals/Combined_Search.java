package com.example.drugsformarinemammals;

import java.util.ArrayList;
import java.util.Arrays;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class Combined_Search extends Activity {

	
	private String userEntryAnatomicalTarget;
	private String userEntryTherapeuticTarget;
	private String userEntryAnimalTarget;
	private Handler_Sqlite helper;
	private AutoCompleteTextView actv;
	private ArrayAdapter<String> adapterTherapeuticGroup;
	
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
				userEntryTherapeuticTarget=actv.getText().toString();	
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

		actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		actv.setTypeface(Typeface.SANS_SERIF);
		actv.setHint("Enter a therapeutic target");		
		initializeArrayWithTherapeuticGroups();
		actv.setAdapter(adapterTherapeuticGroup);
			
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

	
	public void initializeArrayWithTherapeuticGroups() {
				
		helper=new Handler_Sqlite(this);
		helper.open();
		
		ArrayList<String> therapeutic_groups=helper.getAllTherapeuticGroup();
		adapterTherapeuticGroup = new ArrayAdapter<String>(this,android.R.layout.two_line_list_item,android.R.id.text1,therapeutic_groups);
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
