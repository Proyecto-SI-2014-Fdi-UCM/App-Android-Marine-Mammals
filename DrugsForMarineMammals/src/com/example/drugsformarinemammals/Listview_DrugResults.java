package com.example.drugsformarinemammals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class Listview_DrugResults extends Activity {
	
	private ArrayList<String> drugList;
	private Handler_Sqlite helper;
	//private String drugName;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_drugresults);
		helper = new Handler_Sqlite(this);
		Bundle extra = this.getIntent().getExtras();
		if (extra!=null) {
			//option Combined Search
			drugList =(ArrayList<String>) extra.get("drugList");
		}
		else {
			//option Five Last Searched
			List<Drug_Information> drugs_with_priority = new ArrayList<Drug_Information>();
			SQLiteDatabase tmp = helper.open();
			if (tmp!=null) {
				drugs_with_priority = helper.read_drugs_database();
				helper.close();
			}
			
			//sort drugs by priority
			Collections.sort(drugs_with_priority,new Comparator<Drug_Information>() {

				@Override
				public int compare(Drug_Information drug1, Drug_Information drug2) {
					// TODO Auto-generated method stub
					return drug1.getPriority().compareTo(drug2.getPriority());
				}
				
			});
			
			drugList = new ArrayList<String>();
			for (int i=0;i<drugs_with_priority.size();i++) {
				drugList.add(drugs_with_priority.get(i).getName());
			}
		}
		ListAdapter adapter = new ItemAdapterDrugResults(this, drugList);
		ListView listview = (ListView) findViewById(R.id.drugsresult);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String drugName = drugList.get(position);
				if (!helper.existDrug(drugName)){
					String[] urls={"http://formmulary.tk/Android/getGeneralInfoDrug.php?drug_name=","http://formmulary.tk/Android/getInfoCodes.php?drug_name="};
			    	boolean sync=false;
					new GetGeneralInfoDrug(drugName,sync).execute(urls);
				}
				else{
					Intent intent = new Intent(getApplicationContext(), General_Info_Drug.class);
					intent.putExtra("drugName", drugName);
					startActivity(intent);	
				}
			}
		});
		
		listview.setAdapter(adapter);
	}
	
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
					String[] urlsDoseInfo={"http://formmulary.tk/Android/getDoseInformation.php?drug_name=","http://formmulary.tk/Android/getGeneralNotesInformation.php?drug_name="};
					int size=drugList.size();
					for(int i=0;i<size;i++) {
						new GetGeneralInfoDrug(drugList.get(i),true).execute(urlsDrugInfo);
						new GetDoseInformation(i).execute(urlsDoseInfo);
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

	
	public void startGralInfo(String drugName) {
		Intent i = new Intent(this, General_Info_Drug.class);
		i.putExtra("drugName", drugName);
		startActivity(i);
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
		boolean sync;
		HttpPost httppost1;
		HttpPost httppost2;
		
		public GetGeneralInfoDrug(String drug, boolean sync){
			drug_name=drug;
			this.sync=sync;
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
			initializeCodesInformationForLocalDB(result);
			if(!sync){
				//Si se ejecuta la consulta al servidor => medicamento nuevo => hay que guardar en ddbb local
				helper.saveGeneralInfoDrug(generalInfo);
				helper.saveCodeInformation(codesInformation, drug_name);
				Intent intent = new Intent(getApplicationContext(), General_Info_Drug.class);
				intent.putStringArrayListExtra("generalInfoDrug", result);
				startActivity(intent);
			}
			else{
				helper.updateGeneralInfroDrug(generalInfo);
				helper.updateCodeInformation(codesInformation, drug_name);
			}
			
		}
		
		public void initializeGeneralInfoDrugForLocalDB(ArrayList<String> result){
			//{"drug_name":"Furosemide","description":"Loop diuretic to treat fluid retention","available":"Yes","license_AEMPS":"Nd","license_EMA":"Nd","license_FDA":"Yes"}
			String[] parse;
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
	
	private class GetDoseInformation extends AsyncTask<String, Integer, ArrayList<String>>{
		ArrayList<String> jsonResponse = new ArrayList<String>();
		HttpPost httppost1;
		HttpPost httppost2;
		int i;
		
		public GetDoseInformation(int index){
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
}
