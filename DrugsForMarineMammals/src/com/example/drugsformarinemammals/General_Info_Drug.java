package com.example.drugsformarinemammals;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class General_Info_Drug extends Activity {

	private ListView listviewCodes;
	private ArrayList<String> options;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_info_drug);
		
		Bundle parameter = this.getIntent().getExtras();
    	if (parameter!=null) {
    		String drug_name = parameter.getString("drugName");
			Handler_Sqlite helper = new Handler_Sqlite(this);
    
			TextView drugTitle=(TextView)findViewById(R.id.drugTitle);
			//Aqui hay que leer de la BBDD
			drugTitle.setText("Furosemide");
			
			TextView headerBriefDescription=(TextView)findViewById(R.id.headerBriefDescription);
			
			TextView briefDescription=(TextView)findViewById(R.id.briefDescription);
			briefDescription.setText("Aquí va la descripción del medicamento");
			
			ImageView genericDrug=(ImageView)findViewById(R.id.genericDrug);
			genericDrug.setImageResource(R.drawable.tick_verde);
			
			ImageButton food_and_drug=(ImageButton)findViewById(R.id.food_and_drug);
			food_and_drug.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("http://www.fda.gov/"));
					startActivity(intent);
				}});
			
			ImageButton logo_aemps=(ImageButton)findViewById(R.id.logo_aemps);
			logo_aemps.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("http://www.aemps.gob.es/"));
					startActivity(intent);
				}});

			ImageButton european_medicines_agency=(ImageButton)findViewById(R.id.european_medicines_agency);
			european_medicines_agency.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("http://www.ema.europa.eu/ema/"));
					startActivity(intent);
				}});

			
			TextView anatomicalGroup=(TextView)findViewById(R.id.anatomicalGroup);
			anatomicalGroup.setText("Aquí va el grupo anatómico");
			
			TextView therapeuticGroup=(TextView)findViewById(R.id.therapeuticGroup);
			therapeuticGroup.setText("Aquí va el grupo terapéutico");
			
			for(int i=1; i<3;i++){
				Button buttonCodes = new Button(this);
				buttonCodes.setText("codigo 1");
				buttonCodes.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
					}});
				LinearLayout layout = (LinearLayout)findViewById(R.id.buttonCodes);
				layout.addView(buttonCodes);
				
			}
			
			Button cetaceansButton = new Button(this);
			cetaceansButton.setText("CETACEANS");
			cetaceansButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showDoseInformation();
					
				}});
			
			Button pinnipedsButton = new Button(this);
			pinnipedsButton.setText("PINNIPEDS");
			pinnipedsButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showDoseInformation();
				}});
			
			Button otherButton = new Button(this);
			otherButton.setText("OTHER MM");
			otherButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showDoseInformation();
				}});
			
			LinearLayout layout = (LinearLayout)findViewById(R.id.buttonAnimals);
			layout.addView(cetaceansButton);
			layout.addView(pinnipedsButton);
			layout.addView(otherButton);
			
    		//if drug exists in local database
    		if (helper.existDrug(drug_name)) {
    			int drug_priority = helper.getDrugPriority(drug_name);
    			for (int i=1;i<drug_priority;i++) {
    				String name = helper.getDrugName(i);
    				helper.setDrugPriority(name, i+1);
    			}
    			helper.setDrugPriority(drug_name, 1);
    		}
    		else {
    			
    			//Code when we have a server
    		}
    	}
	}

	public void showDoseInformation() {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, Dose_Information.class);
		startActivity(i);
	}

	
}
