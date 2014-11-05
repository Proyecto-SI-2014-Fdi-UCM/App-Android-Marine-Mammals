package com.example.drugsformarinemammals;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Listview_DrugResults extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_drugresults);
		List<String> rows = new ArrayList<String>(30);
		for (int i = 1; i < 20; i++) {
			rows.add("Furosemide");
		}
		ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.item_drugresults, rows);
		ListView listView = (ListView) findViewById(R.id.drugsresult);
		listView.setAdapter(adapter);
	}

//	protected void onListItemClick(ListView l, View v, int position, long id) {
//		l.oonListItemClick(l, v, position, id);
//
//		Toast.makeText(this, l.getItemAtPosition(position).toString(),
//				Toast.LENGTH_LONG).show();
//	}

}
