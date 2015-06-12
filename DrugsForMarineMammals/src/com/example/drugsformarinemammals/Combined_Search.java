package com.example.drugsformarinemammals;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
		               
		     }
		     });

		String[] urls={"http://formmulary.tk/Android/getTherapeuticGroups.php"};
		new GetTherapeuticGroups(this).execute(urls);
		Spinner spinnerAnimals = (Spinner)findViewById(R.id.SpinAnimals);    
		SpinnerAdapter adapterAnimals = new SpinnerAdapter(this, R.layout.item_spinner, Arrays.asList(getResources().getStringArray(R.array.Animals)));
		adapterAnimals.setDropDownViewResource(R.layout.spinner_dropdown_item);
		spinnerAnimals.setAdapter(adapterAnimals);
		
		spinnerAnimals.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View arg1,int arg2, long arg3) {
				userEntryAnimalTarget = parent.getSelectedItem().toString();	 
		    }

		    public void onNothingSelected(AdapterView<?> arg0) {
		                
		    }
		    });
		
		helper.close();
    }	
	
	private class GetDrugsByCombinedSearch extends AsyncTask<String, Void, String>{

		String jsonResponse;
		ArrayList<String> drugList=new ArrayList<String>();		
		Context context;
		
		public GetDrugsByCombinedSearch(Context act){
			this.context=act;
		}
		protected String doInBackground(String... urls) {
			HttpPost httppost;
			HttpClient httpclient = new DefaultHttpClient();
			httppost = new HttpPost(urls[0]);
			try{
				JSONObject json = new JSONObject();
				json.put("therapeutic", userEntryTherapeuticTarget);
				json.put("anatomical", userEntryAnatomicalTarget);
				json.put("group", userEntryAnimalTarget);
				StringEntity se = new StringEntity(json.toString()); 
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		        httppost.setEntity(se);
		        HttpResponse response = httpclient.execute(httppost);
		        jsonResponse = EntityUtils.toString(response.getEntity());		        
		
		  }catch (Exception e) {
		        Log.v("Error:", e.getMessage());
		  }
		  return jsonResponse;
			
		}
		protected void onPostExecute(String result) {
			if(!result.isEmpty())
				initializeDrugList(result);
			if (drugList != null && !drugList.isEmpty()) {
				Intent i = new Intent(context, Listview_DrugResults.class);
				i.putExtra("drugList", drugList);
				startActivity(i);
			}
			else {
				AlertDialog.Builder myalert = new AlertDialog.Builder(context);
				
				TextView title = new TextView(context);
				title.setTypeface(Typeface.SANS_SERIF);
				title.setTextSize(20);
				title.setTextColor(getResources().getColor(R.color.blue));
				title.setPadding(8, 8, 8, 8);
				title.setText("NOT FOUND");
				title.setGravity(Gravity.CENTER_VERTICAL);
				
				LinearLayout layout = new LinearLayout(context);
				TextView text = new TextView(context);
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

		public void initializeDrugList(String result){
			String []parse= result.split("\\[\"");
			int size=parse.length;
			for(int i=1;i<size;i++){
				String[] tmp=parse[i].split("\"\\]");
				drugList.add(tmp[0]);
			}
		}
		
	}
		
	public void search(){
		String [] urls={"http://formmulary.tk/Android/getDrugsByCombinedSearch.php"};
		new GetDrugsByCombinedSearch(this).execute(urls);
		
	}
	
	private class GetTherapeuticGroups extends AsyncTask<String, Void, String>{
		String jsonResponse;
		ArrayList<String> therapeutic_groups=new ArrayList<String>();
		ArrayAdapter<String> adapterTherapeuticGroup;
		Context context;
		
		public GetTherapeuticGroups(Context act){
			this.context=act;
		}
		@Override
		protected String doInBackground(String... urls) {
			HttpPost httppost;
			HttpClient httpclient = new DefaultHttpClient();
			httppost = new HttpPost(urls[0]);
			
			try{
		        HttpResponse response = httpclient.execute(httppost);
		        jsonResponse = EntityUtils.toString(response.getEntity());		        
		
		  }catch (Exception e) {
		        Log.v("Error:", e.getMessage());
		  }
		  return jsonResponse;
			
		}
		protected void onPostExecute(String result) {
			initializeArrayWithTherapeuticGroups(result);
			actv = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
			actv.setTypeface(Typeface.SANS_SERIF);
			actv.setHint("Enter name of drug");
			actv.setAdapter(adapterTherapeuticGroup);
		}
		
		
		public void initializeArrayWithTherapeuticGroups(String result) {
			String []parse= result.split("\\[\"");
			int size=parse.length;
			for(int i=1;i<size;i++){
				String[] tmp=parse[i].split("\"\\]");
				therapeutic_groups.add(tmp[0]);
			}
			adapterTherapeuticGroup = new ArrayAdapter<String>(context,android.R.layout.two_line_list_item,android.R.id.text1,therapeutic_groups);
		}
		
	}

	
}
