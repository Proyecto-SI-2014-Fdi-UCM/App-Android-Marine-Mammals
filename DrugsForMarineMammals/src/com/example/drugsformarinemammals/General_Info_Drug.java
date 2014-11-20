package com.example.drugsformarinemammals;

import java.util.ArrayList;

import com.example.drugsformarinemammals.R.color;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class General_Info_Drug extends Activity {

	
	private ArrayList<String> options;
	private Handler_Sqlite helper;
	private String titleBundle;
	private String anatomicTarget;
	private String therapeuticTarget;
	private ArrayList<String> codes;
	private Button buttonCodes;
	private TextView anatomicalGroup;
	private TextView therapeuticGroup;
	private LinearLayout borderTherapeuticGroup;
	private LinearLayout borderAnatomicalGroup;
	private LinearLayout.LayoutParams params;
	private LinearLayout layoutAnatomicalGroup;
	private LinearLayout layoutTherapeuticGroup;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_info_drug);
        helper=new Handler_Sqlite(this);
        helper.open();
        
        Bundle extras1= this.getIntent().getExtras();	
        if (extras1!=null){
			titleBundle=extras1.getString("drugName");
			       
	        //Title
			TextView drugTitle=(TextView)findViewById(R.id.drugTitle);       
	        drugTitle.setText(titleBundle);    
			
			//Description 
	        LinearLayout borderDescription = new LinearLayout(this);
	        borderDescription.setOrientation(LinearLayout.VERTICAL);
	        borderDescription.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	        borderDescription.setBackgroundResource(R.drawable.layout_border);
	        
			//TextView description=(TextView)findViewById(R.id.description);
	        TextView description=new TextView(this);
			description.setText(helper.getDescription(titleBundle));
			description.setTextSize(18);
	       
			LinearLayout.LayoutParams paramsDescription = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			paramsDescription.leftMargin = 60;
			paramsDescription.rightMargin = 60;
			paramsDescription.topMargin = 20;
	        
			borderDescription.addView(description,borderDescription.getChildCount(),paramsDescription);
	        
			LinearLayout layoutDescription=(LinearLayout)findViewById(R.id.layoutDescription);
			layoutDescription.addView(borderDescription,layoutDescription.getChildCount());
			        
	        //Generic Drug
	        ImageView genericDrug=(ImageView)findViewById(R.id.genericDrug);
	        if(helper.isAvalaible(titleBundle)){
	        	genericDrug.setImageResource(R.drawable.tick_verde);
	        }
	        else{
	        	genericDrug.setImageResource(R.drawable.red_cross);
	        }
	        
	        
	        //Licenses
	        
	        TextView fdaLicense=(TextView)findViewById(R.id.license1);
	        Typeface font=Typeface.createFromAsset(getAssets(), "Typoster_demo.otf");
	        String fda=helper.getLicense_FDA(titleBundle);
	        fdaLicense.setTypeface(font);
	        if(fda.equals("Yes")){
	        	fdaLicense.setText("Yes");
	        	fdaLicense.setTextColor(getResources().getColor(R.color.lightGreen));
	        }
	        else if(fda.equals("No")){
	        	fdaLicense.setText("No");
	        	fdaLicense.setTextColor(getResources().getColor(R.color.red));
	        }
	        else{
	        	fdaLicense.setText("N.d.");
	        	fdaLicense.setTextColor(getResources().getColor(R.color.maroon));
	        }
	        
	        TextView aempsLicense=(TextView)findViewById(R.id.license2);
	        String aemps=helper.getLicense_AEMPS(titleBundle);
	        aempsLicense.setTypeface(font);
	        if(aemps.equals("Yes")){
	        	aempsLicense.setText("Yes");	
	        	aempsLicense.setTextColor(getResources().getColor(R.color.lightGreen));
	        }
	        else if(aemps.equals("No")){
	        	aempsLicense.setText("No");
	        	aempsLicense.setTextColor(getResources().getColor(R.color.red));
	        }
	        else{
	        	aempsLicense.setText("N.d.");
	        	aempsLicense.setTextColor(getResources().getColor(R.color.maroon));
	        }
	        
	        TextView emaLicense=(TextView)findViewById(R.id.license3);
	        String ema=helper.getLicense_EMA(titleBundle);
	        emaLicense.setTypeface(font);
	        if(ema.equals("Yes")){
	        	emaLicense.setText("Yes");
	        	emaLicense.setTextColor(getResources().getColor(R.color.lightGreen));
	        }
	        else if(ema.equals("No")){
	        	emaLicense.setText("No");
	        	emaLicense.setTextColor(getResources().getColor(R.color.red));
	        }
	        else{
	        	emaLicense.setText("Nd");
	        	emaLicense.setTextColor(getResources().getColor(R.color.maroon));
	        }
	        
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
	
	        
	       //Action
	        
	        anatomicalGroup=new TextView(this);
	        anatomicalGroup.setText("Push any ATCvet Code to view Anatomic Target");
	        anatomicalGroup.setTextSize(18);
	        
	        borderAnatomicalGroup = new LinearLayout(this);
	        borderAnatomicalGroup.setOrientation(LinearLayout.VERTICAL);
	        borderAnatomicalGroup.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	        borderAnatomicalGroup.setBackgroundResource(R.drawable.layout_border);
	        
	        
	        therapeuticGroup=new TextView(this);
	        therapeuticGroup.setText("Push any ATCvet Code to view Therapeutic Target");
	        therapeuticGroup.setTextSize(18);
	        
	        borderTherapeuticGroup = new LinearLayout(this);
	        borderTherapeuticGroup.setOrientation(LinearLayout.VERTICAL);
	        borderTherapeuticGroup.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	        borderTherapeuticGroup.setBackgroundResource(R.drawable.layout_border);
	        
	        
	        params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	        params.leftMargin = 60;
	        params.rightMargin =60;
	        params.topMargin = 20; 
	        
	        layoutAnatomicalGroup=(LinearLayout)findViewById(R.id.layoutActionAnatomical);
	        layoutTherapeuticGroup=(LinearLayout)findViewById(R.id.layoutActionTherapeutic);
	        
	        //Codes
	        int totalCodes=helper.getCodes(titleBundle).size();
	        codes=new ArrayList<String>();
	        for(int i=0; i<totalCodes;i++){
	        	buttonCodes = new Button(this);
	        	//Add id each button
	        	buttonCodes.setId(i);
	        	//Save codes
	        	codes.add(helper.getCodes(titleBundle).get(i));
	        	
	        	buttonCodes.setText(helper.getCodes(titleBundle).get(i));
	        	buttonCodes.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
				        anatomicalGroup.setText(helper.getAnatomicTarget(titleBundle,codes.get(v.getId())));
				        anatomicalGroup.setTextSize(18);
				        therapeuticGroup.setText(helper.getTherapeuticTarget(titleBundle,codes.get(v.getId())));
				        therapeuticGroup.setTextSize(18); 
				       
					}});
	        	
	        	
	        	
	        	LinearLayout layout = (LinearLayout)findViewById(R.id.buttonCodes);
	        	layout.addView(buttonCodes);
	        	
	        }
	        borderTherapeuticGroup.addView(therapeuticGroup,borderTherapeuticGroup.getChildCount(),params);
	        borderAnatomicalGroup.addView(anatomicalGroup,borderAnatomicalGroup.getChildCount(),params);
	        
	        layoutAnatomicalGroup.addView(borderAnatomicalGroup,layoutAnatomicalGroup.getChildCount());
	    	layoutTherapeuticGroup.addView(borderTherapeuticGroup,layoutTherapeuticGroup.getChildCount());     
	    	
	        
	        //Animals
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
	        
	    	LinearLayout layout2 = (LinearLayout)findViewById(R.id.buttonAnimals);
	    	layout2.addView(cetaceansButton);
	    	layout2.addView(pinnipedsButton);
	    	layout2.addView(otherButton);
	    	
	    	if (helper.existDrug(titleBundle)) {
				int drug_priority = helper.getDrugPriority(titleBundle);
				for (int i=1;i<drug_priority;i++) {
					String name = helper.getDrugName(i);
					helper.setDrugPriority(name, i+1);
				}
				helper.setDrugPriority(titleBundle, 1);
			}
			else {
				
				//Code when we have a server
			}
	    	
	    	helper.close();
        }
	}

	public void showDoseInformation() {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, Dose_Information.class);
		startActivity(i);
	}

	
}
