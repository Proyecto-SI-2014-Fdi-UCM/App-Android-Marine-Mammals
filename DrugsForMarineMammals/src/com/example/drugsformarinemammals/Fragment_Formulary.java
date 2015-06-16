package com.example.drugsformarinemammals;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class Fragment_Formulary extends Fragment {
	
	private ArrayList<ItemWithImage> options=new ArrayList<ItemWithImage>();
	private ListView list;
	private View rootView;
	private String userEntry;
	private AutoCompleteTextView actv;
	private Handler_Sqlite helper;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		rootView = inflater.inflate(R.layout.fragment_formulary, container, false);	
		initializeArrayListSearchs();
		helper = new Handler_Sqlite(rootView.getContext());
		list = (ListView)rootView.findViewById(R.id.listViewTypeSearch);
		ItemAdapterWithImageFormulary adapter;
		// Inicializamos el adapter.
		adapter = new ItemAdapterWithImageFormulary(getActivity(),options);
		// Asignamos el Adapter al ListView, en este punto hacemos que el
		// ListView muestre los datos que queremos.
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener(){	 
		    @Override
		    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {	    	
		    	switch(position){
		    		// Single Search
		    		case 0: open_dialog();
		        			break;
		        	// Combined Search	        		
		    		case 1: Intent intent = new Intent(getActivity(),Combined_Search.class);
        					startActivity(intent);
        					break;
        			// Five Last Searched    	
		    		case 2: Intent intentResults = new Intent(getActivity(),Listview_DrugResults.class);
		        			startActivity(intentResults);
		        			break;
		    		default:
		        			break;
		    	}
		    	
		    }
		});
		
		return rootView;	
	}	
	
	private void initializeArrayListSearchs() {
		options.add(new ItemWithImage(R.drawable.single_search,"Single Search"));
		options.add(new ItemWithImage(R.drawable.combined_search,"Combined Search"));
		options.add(new ItemWithImage(R.drawable.five_last_searched,"Five Last Searched"));
	}
	
	public void open_dialog() {
		String[] urls = { "http://formmulary.tk/Android/getDrugNames.php" };
		new GetDrugNames().execute(urls);
		
	}
		
	private class GetDrugNames extends AsyncTask<String, Void, String>{
		String jsonResponse;
		ArrayList<String> drugNames=new ArrayList<String>();
		ArrayAdapter<String> adapterDrugNames;
		@Override
		protected String doInBackground(String... urls) {
			HttpPost httppost;
			HttpClient httpclient = new DefaultHttpClient();
			httppost = new HttpPost(urls[0]);
			
			try{
				HttpResponse response = httpclient.execute(httppost);
		        jsonResponse = EntityUtils.toString(response.getEntity());		        
		
		  }catch (Exception e) {
		        Log.v("Error: ", e.getMessage());
		  }
		  return jsonResponse;
			
		}
		protected void onPostExecute(String result) {
			initializeDrugNames(result);
			LayoutInflater li = LayoutInflater.from(getActivity());
			View promptsView = li.inflate(R.layout.dialog_single_search, null);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

			alertDialogBuilder.setView(promptsView);
			
			actv = (AutoCompleteTextView) promptsView.findViewById(R.id.autoCompleteTextView1);
			actv.setTypeface(Typeface.SANS_SERIF);
			actv.setHint("Enter name of drug");
			
			alertDialogBuilder.setCancelable(false).setPositiveButton("Search",new DialogInterface.OnClickListener() {
						    public void onClick(DialogInterface dialog,int id) {
						    	userEntry=actv.getText().toString();
						    	if (!helper.existDrug(userEntry)){
							    	String[] urls={"http://formmulary.tk/Android/getGeneralInfoDrug.php?drug_name=","http://formmulary.tk/Android/getInfoCodes.php?drug_name="};
							    	new GetGeneralInfoDrug().execute(urls);
						    	}
						    	else{
						    		Intent intent = new Intent(rootView.getContext(), General_Info_Drug.class);
									intent.putExtra("drugName", userEntry);
									startActivity(intent);
						    	}
						    }
			})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				});
			
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
			actv.setAdapter(adapterDrugNames);
			// show it
			alertDialog.show();
		}
		
		public void initializeDrugNames(String result){			
			String []parse= result.split("\\[\"");
			int size=parse.length;
			for(int i=1;i<size;i++){
				String[] tmp=parse[i].split("\"\\]");
				drugNames.add(tmp[0]);
			}
			adapterDrugNames = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,drugNames);
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
		@Override
	    protected ArrayList<String> doInBackground(String... urls) {
		    	
			HttpClient httpclient = new DefaultHttpClient();    
			httppost1 = new HttpPost(urls[0]+userEntry);
			httppost2 = new HttpPost(urls[1]+userEntry);
			
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
				//Si se ejecuta la consulta al servidor => medicamento nuevo => hay que guardar en ddbb local
				initializeGeneralInfoDrug(result);
				helper.saveGeneralInfoDrug(generalInfo);
				initializeCodesInformation(result);
				helper.saveCodeInformation(codesInformation, drug_name);
				Intent intent = new Intent(rootView.getContext(), General_Info_Drug.class);
				intent.putStringArrayListExtra("generalInfoDrug", result);
				startActivity(intent);
		}

		public void initializeGeneralInfoDrug(ArrayList<String> result) {
			// {"drug_name":"Furosemide","description":"Loop diuretic to treat fluid retention","available":"Yes","license_AEMPS":"Nd","license_EMA":"Nd","license_FDA":"Yes"}
			String[] parse;
			// codesInformation=new ArrayList<Type_Code>();
			parse = result.get(0).split("\\{\"drug_name\":\"");
			parse = parse[1].split("\",\"description\":\"");
			drug_name = parse[0];
			generalInfo.add(drug_name);
			parse = parse[1].split("\",\"available\":\"");
			description = parse[0];
			generalInfo.add(description);
			parse = parse[1].split("\",\"license_AEMPS\":\"");
			available = parse[0];
			generalInfo.add(available);
			parse = parse[1].split("\",\"license_EMA\":\"");
			license_AEMPS = parse[0];
			generalInfo.add(license_AEMPS);
			parse = parse[1].split("\",\"license_FDA\":\"");
			license_EMA = parse[0];
			generalInfo.add(license_EMA);
			parse = parse[1].split("\"\\}");
			license_FDA = parse[0];
			generalInfo.add(license_FDA);
		}		
		
		public void initializeCodesInformation(ArrayList<String> result) {
			String[] tmp;
			String[] parse;

			// QA07AA91","QA Alimentary track and metabolism","Antidiarrheals,
			// intestinal anti-inflammatory"]
			tmp = result.get(1).split("\\[\"");
			int size = tmp.length;
			for (int i = 1; i < size; i++) {
				parse = tmp[i].split("\",\"");
				Type_Code type = new Type_Code();
				type.setCode(parse[0]);
				type.setAnatomic_group(parse[1]);
				parse = parse[2].split("\"\\]");
				type.setTherapeutic_group(parse[0]);
				codesInformation.add(type);
			}
		}

	}
}