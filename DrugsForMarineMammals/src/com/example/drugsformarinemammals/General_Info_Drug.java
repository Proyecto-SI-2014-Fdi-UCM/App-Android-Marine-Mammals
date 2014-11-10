package com.example.drugsformarinemammals;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
        
        TextView drugTitle=(TextView)findViewById(R.id.drugTitle);
        //Aqui hay que leer de la BBDD
        drugTitle.setText("Furosemide");
        
        TextView headerBriefDescription=(TextView)findViewById(R.id.headerBriefDescription);
        
        TextView briefDescription=(TextView)findViewById(R.id.briefDescription);
        briefDescription.setText("Aqu� va la descripci�n del medicamento");
        
        ImageView genericDrug=(ImageView)findViewById(R.id.genericDrug);
        genericDrug.setImageResource(R.drawable.tick_verde);
        
        TextView anatomicalGroup=(TextView)findViewById(R.id.anatomicalGroup);
        anatomicalGroup.setText("Aqu� va el grupo anat�mico");
        
        TextView therapeuticGroup=(TextView)findViewById(R.id.therapeuticGroup);
        therapeuticGroup.setText("Aqu� va el grupo terap�utico");
        
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
	}

	public void showDoseInformation() {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, Dose_Information.class);
		startActivity(i);
	}

	
}
