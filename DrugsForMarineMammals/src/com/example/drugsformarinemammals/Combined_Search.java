package com.example.drugsformarinemammals;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Combined_Search extends Activity implements OnItemClickListener{

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combined_search);
        
        Spinner spinnerAnatomicalTarget = (Spinner)findViewById(R.id.SpinAnatomicalTarget);       
        ArrayAdapter<CharSequence> adapterAnatomicalTarget = ArrayAdapter.createFromResource(this, R.array.AnatomicalTarget, android.R.layout.simple_spinner_item);	     
		adapterAnatomicalTarget.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerAnatomicalTarget.setAdapter(adapterAnatomicalTarget);
		

		Spinner spinnerTherapeuticTarget = (Spinner)findViewById(R.id.SpinTherapeuticTarget);       
        ArrayAdapter<CharSequence> adapterTherapeuticTarget = ArrayAdapter.createFromResource(this, R.array.TherapeuticTarget, android.R.layout.simple_spinner_item);	     
		adapterTherapeuticTarget.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerTherapeuticTarget.setAdapter(adapterTherapeuticTarget);
		
		Spinner spinnerAnimals = (Spinner)findViewById(R.id.SpinAnimals);       
        ArrayAdapter<CharSequence> adapterAnimals = ArrayAdapter.createFromResource(this, R.array.Animals, android.R.layout.simple_spinner_item);	     
		adapterAnimals.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerAnimals.setAdapter(adapterAnimals);
    }

	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

}
