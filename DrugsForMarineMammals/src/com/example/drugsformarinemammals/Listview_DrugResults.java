package com.example.drugsformarinemammals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Listview_DrugResults extends Activity {
	
	private ArrayList<String> drugList;
	private Handler_Sqlite helper;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_drugresults);
		helper = new Handler_Sqlite(this);
		Bundle extra = this.getIntent().getExtras();
		if (extra!=null) {
			//option Combined Search
			drugList = (ArrayList<String>) extra.get("drugList");
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
			for (int i=0;i<5;i++) {
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
				startGralInfo(drugName);
			}
		});
		
		listview.setAdapter(adapter);
	}
	

	public void startGralInfo(String drugName) {
		Intent i = new Intent(this, General_Info_Drug.class);
		i.putExtra("drugName", drugName);
		startActivity(i);
	}

}
