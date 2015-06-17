package com.example.drugsformarinemammals;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
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
	private boolean isStoredInLocal;
	private ArrayList<String> drugList;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_info_drug);
        isStoredInLocal=false;
        helper=new Handler_Sqlite(this);
        helper.open();
        Bundle extras1= this.getIntent().getExtras();	
        if (extras1!=null){
        	infoBundle = extras1.getStringArrayList("generalInfoDrug");
        	if(infoBundle==null)
        		isStoredInLocal=true;
        	if(!isStoredInLocal){
	        	initializeGeneralInfoDrug();
	        	initializeCodesInformation();
	        	initializeCodes();
        	}
        	else{
        		drug_name=extras1.getString("drugName");
        		codes=helper.getCodes(drug_name);
        		codesInformation=helper.getCodesInformation(drug_name);
        	}
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
		    if(isStoredInLocal)
		    	description.setText(helper.getDescription(drug_name));
		    else
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
			boolean isAvalaible=false;
		    if(isStoredInLocal)
		    	isAvalaible=helper.isAvalaible(drug_name);
		    else
		    	isAvalaible=available.equals("Yes");
			if(isAvalaible){
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
		    
		    String fda;
		    if(isStoredInLocal)
		    	fda=helper.getLicense_FDA(drug_name);
		    else
		    	fda=license_FDA;
		    if(fda.equals("Yes")){
		    	fdaLicense.setText("Yes");
		        fdaLicense.setTextColor(getResources().getColor(R.color.lightGreen));
		    }
		    else{
		    	fdaLicense.setText("Nd");
		        fdaLicense.setTextColor(getResources().getColor(R.color.maroon));
		    }
	    
		    TextView emaLicense=(TextView)findViewById(R.id.license3);
		    emaLicense.setTypeface(font);
		    String ema;
		    if(isStoredInLocal)
		    	ema=helper.getLicense_EMA(drug_name);
		    else
		    	ema=license_EMA;
		    if(ema.equals("Yes")){
		    	emaLicense.setText("Yes");
		        emaLicense.setTextColor(getResources().getColor(R.color.lightGreen));
		    }
		    else{
		    	emaLicense.setText("Nd");
		        emaLicense.setTextColor(getResources().getColor(R.color.maroon));
		    }
		    
		    TextView aempsLicense = (TextView) findViewById(R.id.license2);
			aempsLicense.setTypeface(font);
			String aemps;
			if(isStoredInLocal)
				aemps=helper.getLicense_AEMPS(drug_name);
			else
				aemps=license_AEMPS;
			if (aemps.equals("Yes")) {
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
			
		    if (!isStoredInLocal) {
				//Server code
				String[] urls={"http://formmulary.tk/Android/getDoseInformation.php?drug_name=","http://formmulary.tk/Android/getGeneralNotesInformation.php?drug_name="};
		    	new GetDoseInformation(this).execute(urls);
			}	
		               
		helper.close();
        }
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.viewpager_mainmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
			case R.id.sync:
				getDrugNamesLocalDB();
				if(drugList.size()>0){
					String[] urlsDrugInfo={"http://formmulary.tk/Android/getGeneralInfoDrug.php?drug_name=","http://formmulary.tk/Android/getInfoCodes.php?drug_name="};
					String[] urlsUpdateDoseInfo={"http://formmulary.tk/Android/getDoseInformation.php?drug_name=","http://formmulary.tk/Android/getGeneralNotesInformation.php?drug_name="};
					int size=drugList.size();
					for(int i=0;i<size;i++) {
						new GetGeneralInfoDrug(i).execute(urlsDrugInfo);
						new GetUpdatedDoseInformation(i).execute(urlsUpdateDoseInfo);
					}
					displayMessage("Drugs of your last searches have been updated");
				}
				else
					displayMessage("No drug has been updated, please do any search and try again");
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void displayMessage(String message) {
		AlertDialog.Builder myalert = new AlertDialog.Builder(this);
		
		TextView title = new TextView(this);
		title.setTypeface(Typeface.SANS_SERIF);
		title.setTextSize(20);
		title.setTextColor(getResources().getColor(R.color.blue));
		title.setPadding(8, 8, 8, 8);
		title.setText("Synchronization");
		title.setGravity(Gravity.CENTER_VERTICAL);
		
		LinearLayout layout = new LinearLayout(this);
		TextView text = new TextView(this);
		text.setTypeface(Typeface.SANS_SERIF);
		text.setTextSize(20);
		text.setPadding(10, 10, 10, 10);
		text.setText(message);
		layout.addView(text);
		
		myalert.setView(layout);
		myalert.setCustomTitle(title);
		myalert.setCancelable(true);
		myalert.show();
		
	}

	public void getDrugNamesLocalDB(){
		List<Drug_Information> drugs_with_priority = new ArrayList<Drug_Information>();
		SQLiteDatabase tmp = helper.open();
		if (tmp!=null) {
			drugs_with_priority = helper.read_drugs_database();
			helper.close();
		}
		int size=drugs_with_priority.size();
		drugList = new ArrayList<String>();
		for (int i=0;i<size;i++) {
			drugList.add(drugs_with_priority.get(i).getName());
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
		codesInformation=new ArrayList<Type_Code>();
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
	
	private class GetGeneralInfoDrug extends AsyncTask<String, Integer, ArrayList<String>>{
		ArrayList<String> jsonResponse=new ArrayList<String>();
		ArrayList<String> generalInfo=new ArrayList<String>();
		ArrayList<Type_Code> codesInformation=new ArrayList<Type_Code>();
		String drug_name;
		String available;
		String description;
		String license_AEMPS;
		String license_EMA;
		String license_FDA;
		
		HttpPost httppost1;
		HttpPost httppost2;
		int i;
		
		public GetGeneralInfoDrug(int index){
			i=index;
		}
		@Override
	    protected ArrayList<String> doInBackground(String... urls) {
		    	
			HttpClient httpclient = new DefaultHttpClient();    
			httppost1 = new HttpPost(urls[0]+drugList.get(i));
			httppost2 = new HttpPost(urls[1]+drugList.get(i));
			
			try {
			        //send the POST request
			        HttpResponse response1 = httpclient.execute(httppost1);
			        HttpResponse response2 = httpclient.execute(httppost2);
			
			        //read the response from Services endpoint
			        String jsonResponse1 = EntityUtils.toString(response1.getEntity());
			        String jsonResponse2 = EntityUtils.toString(response2.getEntity());
			        if (!jsonResponse1.equals("")){
			        	jsonResponse.add(jsonResponse1);
			        	jsonResponse.add(jsonResponse2);        	
			        }
			        
			        return jsonResponse;
			        
			
			  }catch (Exception e) {
			        Log.v("Error: ", e.getMessage());
			  }
			
			  return jsonResponse;
		}
		
		@Override
		protected void onPostExecute(ArrayList<String> result) {
			initializeGeneralInfoDrugForLocalDB(result);
			helper.updateGeneralInfroDrug(generalInfo);
			initializeCodesInformationForLocalDB(result);
			helper.updateCodeInformation(codesInformation, drug_name);
			
		}

		public void initializeGeneralInfoDrugForLocalDB(ArrayList<String> result){
			//{"drug_name":"Furosemide","description":"Loop diuretic to treat fluid retention","available":"Yes","license_AEMPS":"Nd","license_EMA":"Nd","license_FDA":"Yes"}
			String[] parse;
			//codesInformation=new ArrayList<Type_Code>();
			parse= result.get(0).split("\\{\"drug_name\":\"");
			parse=parse[1].split("\",\"description\":\"");
			drug_name=parse[0];
			generalInfo.add(drug_name);
			parse=parse[1].split("\",\"available\":\"");
			description=parse[0];
			generalInfo.add(description);
			parse=parse[1].split("\",\"license_AEMPS\":\"");
			available=parse[0];
			generalInfo.add(available);
			parse=parse[1].split("\",\"license_EMA\":\"");
			license_AEMPS=parse[0];
			generalInfo.add(license_AEMPS);
			parse=parse[1].split("\",\"license_FDA\":\"");
			license_EMA=parse[0];
			generalInfo.add(license_EMA);
			parse=parse[1].split("\"\\}");
			license_FDA=parse[0];
			generalInfo.add(license_FDA);
		}
		
		public void initializeCodesInformationForLocalDB(ArrayList<String> result) {
			String[] tmp;
			String[] parse;
			
			//QA07AA91","QA Alimentary track and metabolism","Antidiarrheals, intestinal anti-inflammatory"]
			tmp=result.get(1).split("\\[\""); 
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
	}
	
	private class GetUpdatedDoseInformation extends AsyncTask<String, Integer, ArrayList<String>>{
		ArrayList<String> jsonResponse = new ArrayList<String>();
		HttpPost httppost1;
		HttpPost httppost2;
		int i;
		
		public GetUpdatedDoseInformation(int index){
			this.i=index;
		}
		
		@Override
	    protected ArrayList<String> doInBackground(String... urls) {
		    	
			HttpClient httpclient = new DefaultHttpClient();    
			httppost1 = new HttpPost(urls[0]+drugList.get(i));
			httppost2 = new HttpPost(urls[1]+drugList.get(i));
			
			try {
			        //send the POST request
			        HttpResponse response1 = httpclient.execute(httppost1);
			        HttpResponse response2 = httpclient.execute(httppost2);
			
			        //read the response from Services endpoint
			        String jsonResponse1 = EntityUtils.toString(response1.getEntity());   
			        String jsonResponse2 = EntityUtils.toString(response2.getEntity());
			        
			        jsonResponse.add(jsonResponse1);
			        jsonResponse.add(jsonResponse2);        	
			
			  }catch (Exception e) {
			        Log.v("Error: ", e.getMessage());
			  }
			
			  return jsonResponse;
		}
		
		@Override
		protected void onPostExecute(ArrayList<String> result) {
			helper.open();
			//delete old dose in local database
			helper.deleteDose(drugList.get(i));
			//delete old notes in local database
			helper.deleteNotes(drugList.get(i));
			helper.close();
			parseAndInsertDose(result.get(0));
			parseAndInsertGeneralNote(result.get(1));
		}
		
		public void parseAndInsertDose(String result) {
			String [] parse= result.split("\\[\"");
			int size=parse.length;
			for(int j=1;j<size;j++) {
				//parse result
				String [] oneDose = parse[j].split("\",\"");
				String animal_name=oneDose[0];
				String family=oneDose[1];
				String group_name=oneDose[2];
				String category_name=oneDose[3];
				String book_reference=oneDose[4];
				String article_reference=oneDose[5];
				String specific_note=oneDose[6];
				String posology=oneDose[7];
				String route=oneDose[8];
				String [] parseDose = oneDose[9].split("\"\\]");
				String [] dose = parseDose[0].split("/");
				String final_dose=dose[0].substring(0,dose[0].length()-1) + "/" + dose[1];
				
				helper.open();
				//insert new dose in local database
				helper.insertGroup(group_name);
				helper.insertAnimal(animal_name, family, group_name, drugList.get(i));
				helper.insertCategory(category_name);
				helper.insertDose(animal_name, drugList.get(i), family, group_name, category_name, book_reference, article_reference, specific_note, posology, route, final_dose);
				helper.close();
			}
			
		}
		
		
		public void parseAndInsertGeneralNote(String result) {
			String [] parse= result.split("\\[\"");
			int size=parse.length;
			for(int j=1;j<size;j++) {
				//parse result
				String [] oneNote = parse[j].split("\",\"");
				//String drug_name=oneNote[0];
				String group_name=oneNote[0];
				String [] parseGeneralNote = oneNote[1].split("\"\\]");
				String general_note=parseGeneralNote[0];
				
				//insert in local database
				helper.open();
				helper.insertGroup(group_name);
				helper.insertGeneralNote(drugList.get(i), group_name, general_note);
				helper.close();
			}
		}
		
	}
	
	private class GetDoseInformation extends AsyncTask<String, Integer, ArrayList<String>>{
		ArrayList<String> jsonResponse = new ArrayList<String>();
		HttpPost httppost1;
		HttpPost httppost2;
		Context context;
		
		public GetDoseInformation(Context act){
			this.context=act;
		}
		
		@Override
	    protected ArrayList<String> doInBackground(String... urls) {
		    	
			HttpClient httpclient = new DefaultHttpClient();    
			httppost1 = new HttpPost(urls[0]+drug_name);
			httppost2 = new HttpPost(urls[1]+drug_name);
			
			try {
			        //send the POST request
			        HttpResponse response1 = httpclient.execute(httppost1);
			        HttpResponse response2 = httpclient.execute(httppost2);
			
			        //read the response from Services endpoint
			        String jsonResponse1 = EntityUtils.toString(response1.getEntity());   
			        String jsonResponse2 = EntityUtils.toString(response2.getEntity());
			        
			        jsonResponse.add(jsonResponse1);
			        jsonResponse.add(jsonResponse2);        	
			
			  }catch (Exception e) {
			        Log.v("Error: ", e.getMessage());
			  }
			
			  return jsonResponse;
		}
		
		@Override
		protected void onPostExecute(ArrayList<String> result) {
			parseAndInsertDose(result.get(0));
			parseAndInsertGeneralNote(result.get(1));
		}
		
	}
	
	public void parseAndInsertDose(String result) {
		String [] parse= result.split("\\[\"");
		int size=parse.length;
		for(int i=1;i<size;i++) {
			//parse result
			String [] oneDose = parse[i].split("\",\"");
			String animal_name=oneDose[0];
			//String drug_name=oneDose[1];
			String family=oneDose[1];
			String group_name=oneDose[2];
			String category_name=oneDose[3];
			String book_reference=oneDose[4];
			String article_reference=oneDose[5];
			String specific_note=oneDose[6];
			String posology=oneDose[7];
			String route=oneDose[8];
			String [] parseDose = oneDose[9].split("\"\\]");
			String [] dose = parseDose[0].split("/");
			String final_dose=dose[0].substring(0,dose[0].length()-1) + "/" + dose[1];
			
			//insert in local database
			helper.open();
			helper.insertGroup(group_name);
			helper.insertAnimal(animal_name, family, group_name, drug_name);
			helper.insertCategory(category_name);
			helper.insertDose(animal_name, drug_name, family, group_name, category_name, book_reference, article_reference, specific_note, posology, route, final_dose);
			helper.close();
		}
		
	}
	
	
	public void parseAndInsertGeneralNote(String result) {
		String [] parse= result.split("\\[\"");
		int size=parse.length;
		for(int i=1;i<size;i++) {
			//parse result
			String [] oneNote = parse[i].split("\",\"");
			//String drug_name=oneNote[0];
			String group_name=oneNote[0];
			String [] parseGeneralNote = oneNote[1].split("\"\\]");
			String general_note=parseGeneralNote[0];
			
			//insert in local database
			helper.open();
			helper.insertGroup(group_name);
			helper.insertGeneralNote(drug_name, group_name, general_note);
			helper.close();
		}
	}

}