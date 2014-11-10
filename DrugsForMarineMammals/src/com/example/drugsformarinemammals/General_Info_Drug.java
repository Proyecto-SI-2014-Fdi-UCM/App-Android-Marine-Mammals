package com.example.drugsformarinemammals;

import java.util.ArrayList;

import android.app.Activity;
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
        briefDescription.setText("Aquí va la descripción del medicamento");
        
        ImageView genericDrug=(ImageView)findViewById(R.id.genericDrug);
        genericDrug.setImageResource(R.drawable.tick_verde);
        
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
        
        Button buttonAnimals1 = new Button(this);
        buttonAnimals1.setText("CETACEANS");
        buttonAnimals1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}});
        
        Button buttonAnimals2 = new Button(this);
        buttonAnimals2.setText("PINNIPEDS");
        buttonAnimals2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}});
        
        Button buttonAnimals3 = new Button(this);
        buttonAnimals3.setText("OTHER MM");
        buttonAnimals3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}});
        
    	LinearLayout layout = (LinearLayout)findViewById(R.id.buttonAnimals);
    	layout.addView(buttonAnimals1);
    	layout.addView(buttonAnimals2);
    	layout.addView(buttonAnimals3);
	}

	
}
