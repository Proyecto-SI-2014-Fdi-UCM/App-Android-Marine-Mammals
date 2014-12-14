package com.example.drugsformarinemammals;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class General_Info_Drug extends Activity {

	private Handler_Sqlite helper;
	private String titleBundle;
	private TextView anatomicalGroup;
	private TextView therapeuticGroup;
	private LinearLayout borderTherapeuticGroup;
	private LinearLayout borderAnatomicalGroup;
	private LinearLayout.LayoutParams params;
	private LinearLayout layoutAnatomicalGroup;
	private LinearLayout layoutTherapeuticGroup;
	private String userEntryCode;
	
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
	        drugTitle.setTypeface(Typeface.SANS_SERIF);
			
		//Description 
		TextView headerBriefDescription=(TextView)findViewById(R.id.headerBriefDescription);
	        //headerBriefDescription.setTypeface(Typeface.SANS_SERIF);
	        
	        LinearLayout borderDescription = new LinearLayout(this);
	        borderDescription.setOrientation(LinearLayout.VERTICAL);
	        borderDescription.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	        borderDescription.setBackgroundResource(R.drawable.layout_border);
	        
	        TextView description=new TextView(this);
			description.setText(helper.getDescription(titleBundle));
			description.setTextSize(18);
			description.setTypeface(Typeface.SANS_SERIF);
	       
			LinearLayout.LayoutParams paramsDescription = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			paramsDescription.leftMargin = 60;
			paramsDescription.rightMargin = 60;
			paramsDescription.topMargin = 20;
	        
			borderDescription.addView(description,borderDescription.getChildCount(),paramsDescription);
	        
			LinearLayout layoutDescription=(LinearLayout)findViewById(R.id.layoutDescription);
			layoutDescription.addView(borderDescription,layoutDescription.getChildCount());
			        
	        //Generic Drug
	        TextView headerGenericDrug=(TextView)findViewById(R.id.headerGenericDrug);
		headerGenericDrug.setTypeface(Typeface.SANS_SERIF);
	        
	        
	        ImageView genericDrug=(ImageView)findViewById(R.id.genericDrug);
	        if(helper.isAvalaible(titleBundle)){
	        	genericDrug.setImageResource(R.drawable.tick_verde);
	        }
	        else{
	        	genericDrug.setImageResource(R.drawable.red_cross);
	        }
	        
	        
	        //Licenses
	        TextView headerLicense=(TextView)findViewById(R.id.headerLicense);
	        headerLicense.setTypeface(Typeface.SANS_SERIF);
	        
	        TextView fdaLicense=(TextView)findViewById(R.id.license1);
	        Typeface font=Typeface.createFromAsset(getAssets(), "Typoster_demo.otf");
	        String fda=helper.getLicense_FDA(titleBundle);
	        fdaLicense.setTypeface(font);
	        if(fda.equals("Yes")){
	        	fdaLicense.setText("Yes");
	        	fdaLicense.setTextColor(getResources().getColor(R.color.lightGreen));
	        }
	        else{
	        	fdaLicense.setText("Nd");
	        	fdaLicense.setTextColor(getResources().getColor(R.color.maroon));
	        }
	        
	        TextView aempsLicense=(TextView)findViewById(R.id.license2);
	        String aemps=helper.getLicense_AEMPS(titleBundle);
	        aempsLicense.setTypeface(font);
	        if(aemps.equals("Yes")){
	        	aempsLicense.setText("Yes");	
	        	aempsLicense.setTextColor(getResources().getColor(R.color.lightGreen));
	        }
	        else{
	        	aempsLicense.setText("Nd");
	        	aempsLicense.setTextColor(getResources().getColor(R.color.maroon));
	        }
	        
	        TextView emaLicense=(TextView)findViewById(R.id.license3);
	        String ema=helper.getLicense_EMA(titleBundle);
	        emaLicense.setTypeface(font);
	        if(ema.equals("Yes")){
	        	emaLicense.setText("Yes");
	        	emaLicense.setTextColor(getResources().getColor(R.color.lightGreen));
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
	        TextView headerActionAnatomical=(TextView)findViewById(R.id.headerActionAnatomical);
	        headerActionAnatomical.setTypeface(Typeface.SANS_SERIF);
	        
	        anatomicalGroup=new TextView(this);
	        anatomicalGroup.setTextSize(18);
	        anatomicalGroup.setTypeface(Typeface.SANS_SERIF);
	        
	        borderAnatomicalGroup = new LinearLayout(this);
	        borderAnatomicalGroup.setOrientation(LinearLayout.VERTICAL);
	        borderAnatomicalGroup.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	        borderAnatomicalGroup.setBackgroundResource(R.drawable.layout_border);
	        
	        TextView headerActionTherapeutic=(TextView)findViewById(R.id.headerActionTherapeutic);
	        headerActionTherapeutic.setTypeface(Typeface.SANS_SERIF);
	        
	        therapeuticGroup=new TextView(this);
	        therapeuticGroup.setTextSize(18);
	        therapeuticGroup.setTypeface(Typeface.SANS_SERIF);
	        
	        borderTherapeuticGroup = new LinearLayout(this);
	        borderTherapeuticGroup.setOrientation(LinearLayout.VERTICAL);
	        borderTherapeuticGroup.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	        borderTherapeuticGroup.setBackgroundResource(R.drawable.layout_border);
	        
	        
	        params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	        params.leftMargin = 60;
	        params.rightMargin =60;
	        params.topMargin = 20;         
	    	
	        
	        //Codes & therapeutic target & anatomical target
	        TextView headerATCvetCodes=(TextView)findViewById(R.id.headerATCvetCodes);
	        headerATCvetCodes.setTypeface(Typeface.SANS_SERIF);
	        
	    	Spinner codesSpinner= (Spinner)findViewById(R.id.codesSpinner);
			SpinnerAdapter adapterCodes = new SpinnerAdapter(this, R.layout.item_spinner,helper.getCodes(titleBundle));	     
        	adapterCodes.setDropDownViewResource(R.layout.spinner_dropdown_item);
    		codesSpinner.setAdapter(adapterCodes);
	    	
    		codesSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

    			public void onItemSelected(AdapterView<?> parent, View arg1,int arg2, long arg3) {
    				userEntryCode = parent.getSelectedItem().toString();	
    				anatomicalGroup.setText(helper.getAnatomicTarget(titleBundle,userEntryCode));
			        anatomicalGroup.setTextSize(18);
			        therapeuticGroup.setText(helper.getTherapeuticTarget(titleBundle,userEntryCode));
			        therapeuticGroup.setTextSize(18); 
			       
    		    }

    		   public void onNothingSelected(AdapterView<?> arg0) {
    		                // TODO Auto-generated method stub
    		    }
    		    });
    		
    		
    		layoutAnatomicalGroup=(LinearLayout)findViewById(R.id.layoutActionAnatomical);
	        layoutTherapeuticGroup=(LinearLayout)findViewById(R.id.layoutActionTherapeutic);
	        
	        
	        borderTherapeuticGroup.addView(therapeuticGroup,borderTherapeuticGroup.getChildCount(),params);
	        borderAnatomicalGroup.addView(anatomicalGroup,borderAnatomicalGroup.getChildCount(),params);
	        
	        layoutAnatomicalGroup.addView(borderAnatomicalGroup,layoutAnatomicalGroup.getChildCount());
	    	layoutTherapeuticGroup.addView(borderTherapeuticGroup,layoutTherapeuticGroup.getChildCount());   
	    	
	        	    	
	        
	        //Animals
	        TextView headerAnimals=(TextView)findViewById(R.id.headerAnimals);
	    	headerAnimals.setTypeface(Typeface.SANS_SERIF);
	        
	    	Button cetaceansButton=(Button)findViewById(R.id.cetaceansButton);
	        cetaceansButton.setText("CETACEANS");
	        cetaceansButton.setTypeface(Typeface.SANS_SERIF);
	        cetaceansButton.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showDoseInformation(titleBundle, "Cetaceans");
					
				}});
	        
	        
	        Button pinnipedsButton=(Button)findViewById(R.id.pinnipedsButton);
	        pinnipedsButton.setText("PINNIPEDS");
	        pinnipedsButton.setTypeface(Typeface.SANS_SERIF);
	        pinnipedsButton.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					SQLiteDatabase db = helper.open();
					ArrayList<String> families = new ArrayList<String>();
					if (db!=null)
						families = helper.read_animals_family(titleBundle, "Pinnipeds");
					if (families != null && families.get(0).equals(""))
						showDoseInformation(titleBundle, "Pinnipeds");
					else 
						showDoseInformationPinnipeds(titleBundle, families);
				}});
	        
	        
	        Button otherButton=(Button)findViewById(R.id.otherButton);
	        otherButton.setText("OTHER MM");
	        otherButton.setTypeface(Typeface.SANS_SERIF);
	        otherButton.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showDoseInformation(titleBundle, "Other MM");
				}});
	        
	        
	    	
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

	public void showDoseInformation(String drugName, String groupName) {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, Dose_Information.class);
		i.putExtra("drugName", drugName);
		i.putExtra("groupName", groupName);
		startActivity(i);
	}

	public void showDoseInformationPinnipeds(String drugName, ArrayList<String> families) {
		Intent j = new Intent(this, ViewPager_Pinnipeds.class);
		j.putExtra("drugName", drugName);
		j.putExtra("families", families);
		startActivity(j);
	}
}
