package com.example.drugsformarinemammals;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Listview_DrugResults extends Activity {
	
	private ArrayList<String> drugList;
	public Handler_Sqlite handler;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_drugresults);
		handler = new Handler_Sqlite(this);
		Bundle extra = this.getIntent().getExtras();
		drugList = (ArrayList<String>) extra.get("drugList");
		ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.item_drugresult, drugList);
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
		finish();
	}
	
	public void restartCombinedSearch() {
		Intent i = new Intent(this, Combined_Search.class);
		startActivity(i);
		finish();
	}
	
}
