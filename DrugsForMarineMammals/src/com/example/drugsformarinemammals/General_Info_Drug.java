package com.example.drugsformarinemammals;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class General_Info_Drug extends Activity {

	private Handler_Sqlite helper;
	private TextView anatomicalGroup;
	private TextView therapeuticGroup;
	private LinearLayout borderTherapeuticGroup;
	private LinearLayout borderAnatomicalGroup;
	private LinearLayout.LayoutParams params;
	private LinearLayout layoutAnatomicalGroup;
	private LinearLayout layoutTherapeuticGroup;
	private String userEntryCode;
	private ArrayList<String> infoBundle;
	private String drug_name;
	private String available;
	private String description;
	private String license_AEMPS;
	private String license_EMA;
	private String license_FDA;
	private ArrayList<Type_Code> codesInformation;
	private ArrayList<String> codes;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_info_drug);
        helper=new Handler_Sqlite(this);
        helper.open();
        Bundle extras1= this.getIntent().getExtras();	
        if (extras1!=null){
        	infoBundle = extras1.getStringArrayList("generalInfoDrug");
        	initializeGeneralInfoDrug();
        	initializeCodesInformation();
        	initializeCodes();
		    //Title
			TextView drugTitle=(TextView)findViewById(R.id.drugTitle);       
			drugTitle.setText(drug_name);
			drugTitle.setTypeface(Typeface.SANS_SERIF);
				
			//Description 
			LinearLayout borderDescription = new LinearLayout(this);
		    borderDescription.setOrientation(LinearLayout.VERTICAL);
		    borderDescription.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		    borderDescription.setBackgroundResource(R.drawable.layout_border);
		        
		    TextView description=new TextView(this);
		    description.setText(this.description);
			description.setTextSize(18);
			description.setTypeface(Typeface.SANS_SERIF);
		       
			LinearLayout.LayoutParams paramsDescription = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			paramsDescription.leftMargin = 60;
			paramsDescription.rightMargin = 60;
			paramsDescription.topMargin = 20;	
		        
			borderDescription.addView(description,borderDescription.getChildCount(),paramsDescription);
		        
			LinearLayout layoutDescription=(LinearLayout)findViewById(R.id.layoutDescription);
			layoutDescription.addView(borderDescription,layoutDescription.getChildCount());
				   
			//Animals
		    TextView headerAnimals=(TextView)findViewById(R.id.headerAnimals);
		    headerAnimals.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
		        
		    Button cetaceansButton=(Button)findViewById(R.id.cetaceansButton);
		    cetaceansButton.setText("CETACEANS");
		    cetaceansButton.setTypeface(Typeface.SANS_SERIF);
		    cetaceansButton.setOnClickListener(new OnClickListener() {
		
		    	@Override
				public void onClick(View v) {
		    		showDoseInformation(drug_name, "Cetaceans");	
				}
		    });
		        
		    Button pinnipedsButton=(Button)findViewById(R.id.pinnipedsButton);
		    pinnipedsButton.setText("PINNIPEDS");
		    pinnipedsButton.setTypeface(Typeface.SANS_SERIF);
		    pinnipedsButton.setOnClickListener(new OnClickListener() {
		
		    	@Override
				public void onClick(View v) {
		    		SQLiteDatabase db = helper.open();
					ArrayList<String> families = new ArrayList<String>();
					if (db!=null)
						families = helper.read_animals_family(drug_name, "Pinnipeds");
					if ((families != null && families.size() == 1 && families.get(0).equals("")) || (families!=null && families.size() == 0))
						showDoseInformation(drug_name, "Pinnipeds");
					else 
						showDoseInformationPinnipeds(drug_name, families);
				}
		    });
		        
		        
		    Button otherButton=(Button)findViewById(R.id.otherButton);
		    otherButton.setText("OTHER MM");
		    otherButton.setTypeface(Typeface.SANS_SERIF);
		    otherButton.setOnClickListener(new OnClickListener() {
		
		    	@Override
				public void onClick(View v) {
		    		showDoseInformation(drug_name, "Other MM");
				}
		    });
		        		
			
		    //Codes & therapeutic target & anatomical target
		    TextView headerATCvetCodes=(TextView)findViewById(R.id.headerATCvetCodes);
		    headerATCvetCodes.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
		    		     
		    //Action
		    TextView headerActionAnatomical=(TextView)findViewById(R.id.headerActionAnatomical);
		    headerActionAnatomical.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
		        
		    createTextViewAnatomical();
		    createBorderAnatomicalGroup();
		    
		    TextView headerActionTherapeutic=(TextView)findViewById(R.id.headerActionTherapeutic);
		    headerActionTherapeutic.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
		    
		    createTextViewTherapeutic();
		    createBorderTherapeuticGroup();
		   
		    params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		    params.leftMargin = 60;
		    params.rightMargin =60;
		    params.topMargin = 20;         
		    
		    Spinner codesSpinner= (Spinner)findViewById(R.id.codesSpinner);	     
		    SpinnerAdapter adapterCodes = new SpinnerAdapter(this, R.layout.item_spinner,codes);
	        adapterCodes.setDropDownViewResource(R.layout.spinner_dropdown_item);
	    	codesSpinner.setAdapter(adapterCodes);
	    	codesSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
	
		    	public void onItemSelected(AdapterView<?> parent, View arg1,int arg2, long arg3) {
		    		userEntryCode = parent.getSelectedItem().toString();	
		    		int numCodes=codesInformation.size();
		    		
		    		layoutAnatomicalGroup.removeView(borderAnatomicalGroup);
		    		createBorderAnatomicalGroup();
		    		
		    		boolean founded=false;
		    		int i=0;
		    		while(!founded && i<numCodes){
		    			if(userEntryCode.equals(codesInformation.get(i).getCode())){
		    				createTextViewAnatomical();
			    			anatomicalGroup.setText(codesInformation.get(i).getAnatomic_group()+"\n");
			    			borderAnatomicalGroup.addView(anatomicalGroup,borderAnatomicalGroup.getChildCount(),params);
			    			founded=true;
		    			}
		    			i++;
		    		}
		    		
		    		layoutAnatomicalGroup=(LinearLayout)findViewById(R.id.layoutActionAnatomical);
		    		layoutAnatomicalGroup.addView(borderAnatomicalGroup,layoutAnatomicalGroup.getChildCount());
		    		layoutTherapeuticGroup.removeView(borderTherapeuticGroup);
		    		createBorderTherapeuticGroup();
		    		founded=false;
		    		i=0;
		    		while(!founded && i<numCodes){
		    			if(userEntryCode.equals(codesInformation.get(i).getCode())){
		    				createTextViewTherapeutic();
		    				therapeuticGroup.setText(codesInformation.get(i).getTherapeutic_group()+"\n");
			    			borderTherapeuticGroup.addView(therapeuticGroup,borderTherapeuticGroup.getChildCount(),params);
			    			founded=true;
		    			}
		    			i++;
		    		}
		    		layoutTherapeuticGroup=(LinearLayout)findViewById(R.id.layoutActionTherapeutic);
		    		layoutTherapeuticGroup.addView(borderTherapeuticGroup,layoutTherapeuticGroup.getChildCount());
				}
		
		    	public void onNothingSelected(AdapterView<?> arg0) {
		    	}
	        });
	    		
	    		
	    	layoutAnatomicalGroup=(LinearLayout)findViewById(R.id.layoutActionAnatomical);
		    layoutTherapeuticGroup=(LinearLayout)findViewById(R.id.layoutActionTherapeutic);
		        
		        
		    borderTherapeuticGroup.addView(therapeuticGroup,borderTherapeuticGroup.getChildCount(),params);
		    borderAnatomicalGroup.addView(anatomicalGroup,borderAnatomicalGroup.getChildCount(),params);
		        
		    layoutAnatomicalGroup.addView(borderAnatomicalGroup,layoutAnatomicalGroup.getChildCount());
		    layoutTherapeuticGroup.addView(borderTherapeuticGroup,layoutTherapeuticGroup.getChildCount());   
		    	
		     
		    //Generic Drug
		    TextView headerGenericDrug=(TextView)findViewById(R.id.headerGenericDrug);
			headerGenericDrug.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);    
		        
			if(available.equals("Yes")){
		    	ImageView genericDrug=new ImageView(this);
		    	genericDrug.setImageResource(R.drawable.tick_verde);
		    	LinearLayout layoutGenericDrug=(LinearLayout)findViewById(R.id.layoutGenericDrug);
		    	layoutGenericDrug.addView(genericDrug);
		    }
		    else{
		    	Typeface font=Typeface.createFromAsset(getAssets(), "Typoster_demo.otf");
		    	TextView genericDrug=new TextView(this);
		    	genericDrug.setTypeface(font);
		    	genericDrug.setText("Nd");
		    	genericDrug.setTextColor(getResources().getColor(R.color.maroon));
		    	genericDrug.setTextSize(20);
		    	DisplayMetrics metrics = new DisplayMetrics();
		    	getWindowManager().getDefaultDisplay().getMetrics(metrics);
		    	int size=metrics.widthPixels;
		    	int middle=size/2;
		    	int quarter=size/4;
		    	genericDrug.setTranslationX(middle-quarter);
		    	genericDrug.setTranslationY(-3);
		    	LinearLayout layoutGenericDrug=(LinearLayout)findViewById(R.id.layoutGenericDrug);
		    	layoutGenericDrug.addView(genericDrug);
		    }
		    
		    
		    //Licenses
		    TextView headerLicense=(TextView)findViewById(R.id.headerLicense);
		    headerLicense.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
		        
		    TextView fdaLicense=(TextView)findViewById(R.id.license1);
		    Typeface font=Typeface.createFromAsset(getAssets(), "Typoster_demo.otf");
		    fdaLicense.setTypeface(font);
		    if(license_FDA.equals("Yes")){
		    	fdaLicense.setText("Yes");
		        fdaLicense.setTextColor(getResources().getColor(R.color.lightGreen));
		    }
		    else{
		    	fdaLicense.setText("Nd");
		        fdaLicense.setTextColor(getResources().getColor(R.color.maroon));
		    }
	    
		    TextView emaLicense=(TextView)findViewById(R.id.license3);
		    emaLicense.setTypeface(font);
		    if(license_EMA.equals("Yes")){
		    	emaLicense.setText("Yes");
		        emaLicense.setTextColor(getResources().getColor(R.color.lightGreen));
		    }
		    else{
		    	emaLicense.setText("Nd");
		        emaLicense.setTextColor(getResources().getColor(R.color.maroon));
		    }
		    
		    TextView aempsLicense = (TextView) findViewById(R.id.license2);
			aempsLicense.setTypeface(font);
			if (license_AEMPS.equals("Yes")) {
				aempsLicense.setText("Yes");
				aempsLicense.setTextColor(getResources().getColor(R.color.lightGreen));
			} else {
				aempsLicense.setText("Nd");
				aempsLicense.setTextColor(getResources().getColor(R.color.maroon));
			}
		        
		    ImageButton food_and_drug=(ImageButton)findViewById(R.id.food_and_drug);
		    food_and_drug.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("http://www.fda.gov/animalveterinary/products/approvedanimaldrugproducts/default.htm"));
					startActivity(intent);
				}
			});
		
		    ImageButton european_medicines_agency=(ImageButton)findViewById(R.id.european_medicines_agency);
		    european_medicines_agency.setOnClickListener(new OnClickListener() {
		    	@Override
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("http://www.ema.europa.eu/ema/index.jsp?curl=pages/medicines/landing/vet_epar_search.jsp&mid=WC0b01ac058001fa1c"));
					startActivity(intent);
				}
		    });
		    
		    ImageButton logo_aemps=(ImageButton)findViewById(R.id.logo_aemps);
		    logo_aemps.setOnClickListener(new OnClickListener() {
		    	@Override
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("http://www.aemps.gob.es/medicamentosVeterinarios/Med-Vet-autorizados/home.htm"));
					startActivity(intent);
				}
		    	
		    });
		   

		        
		        if (helper.existDrug(drug_name)) {
				int drug_priority = helper.getDrugPriority(drug_name);
				ArrayList<String> sorted_drugs = new ArrayList<String>();
				sorted_drugs.add(0, drug_name);
				for (int i=1;i<drug_priority;i++) {
					String name = helper.getDrugName(i);
					sorted_drugs.add(i, name);
				}
				
				for (int i=0;i<sorted_drugs.size();i++)
					helper.setDrugPriority(sorted_drugs.get(i), i+1);
			}
			else {
					
					//Code when we have a server
			}	
		        
		        

		        
		    		    helper.close();
        }
        
	}

	
	public void showDoseInformation(String drugName, String groupName) {
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
	
	public void createTextViewAnatomical(){
		anatomicalGroup=new TextView(this);
	    anatomicalGroup.setTextSize(18);
	    anatomicalGroup.setTypeface(Typeface.SANS_SERIF);
	}
	
	public void createTextViewTherapeutic(){
		therapeuticGroup=new TextView(this);
		therapeuticGroup.setTextSize(18);
		therapeuticGroup.setTypeface(Typeface.SANS_SERIF);
	}
	
	public void createBorderAnatomicalGroup(){
		borderAnatomicalGroup = new LinearLayout(this);
	    borderAnatomicalGroup.setOrientation(LinearLayout.VERTICAL);
	    borderAnatomicalGroup.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	    borderAnatomicalGroup.setBackgroundResource(R.drawable.layout_border);
	}
	
	public void createBorderTherapeuticGroup(){
		borderTherapeuticGroup = new LinearLayout(this);
	    borderTherapeuticGroup.setOrientation(LinearLayout.VERTICAL);
	    borderTherapeuticGroup.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	    borderTherapeuticGroup.setBackgroundResource(R.drawable.layout_border);
	}
	
	
	public void initializeGeneralInfoDrug(){
		//{"drug_name":"Furosemide","description":"Loop diuretic to treat fluid retention","available":"Yes","license_AEMPS":"Nd","license_EMA":"Nd","license_FDA":"Yes"}
		String[] parse;
		codesInformation=new ArrayList<Type_Code>();
		parse= infoBundle.get(0).split("\\{\"drug_name\":\"");
		parse=parse[1].split("\",\"description\":\"");
		drug_name=parse[0];
		parse=parse[1].split("\",\"available\":\"");
		description=parse[0];
		parse=parse[1].split("\",\"license_AEMPS\":\"");
		available=parse[0];
		parse=parse[1].split("\",\"license_EMA\":\"");
		license_AEMPS=parse[0];
		parse=parse[1].split("\",\"license_FDA\":\"");
		license_EMA=parse[0];
		parse=parse[1].split("\"\\}");
		license_FDA=parse[0];
	}
	
	public void initializeCodesInformation() {
		String[] tmp;
		String[] parse;
		
		//QA07AA91","QA Alimentary track and metabolism","Antidiarrheals, intestinal anti-inflammatory"]
		tmp=infoBundle.get(1).split("\\[\""); 
		int size=tmp.length;
		for(int i=1; i<size; i++ ){
			parse=tmp[i].split("\",\"");
			Type_Code type=new Type_Code();
			type.setCode(parse[0]);
			type.setAnatomic_group(parse[1]);
			parse = parse[2].split("\"\\]");
			type.setTherapeutic_group(parse[0]);
			codesInformation.add(type);
			
		}
	}
	
	public void initializeCodes(){
		codes=new ArrayList<String>();
		int size=codesInformation.size();
		for(int i=0; i<size;i++){
			codes.add(codesInformation.get(i).getCode());
		}
	}
}