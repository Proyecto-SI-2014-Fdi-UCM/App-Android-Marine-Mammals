package com.example.drugsformarinemammals;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Dose_Information extends Activity {
	
	private LinearLayout layoutDose;
	private Handler_Sqlite helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dose_information);
		
		Bundle parameters = this.getIntent().getExtras();
		if (parameters!=null) {
			TextView textViewDrug = (TextView) findViewById(R.id.textView_drug_name);
			textViewDrug.setTypeface(Typeface.SANS_SERIF);
			textViewDrug.setText(parameters.getString("drugName"));
			TextView textViewGroupName = (TextView) findViewById(R.id.textView_group_name);
			textViewGroupName.setTypeface(Typeface.SANS_SERIF);
			textViewGroupName.setText("(" + parameters.getString("groupName") + ")");
			layoutDose = (LinearLayout) findViewById(R.id.layout_dose);
			
			helper = new Handler_Sqlite(this);
			SQLiteDatabase db = helper.open();
			ArrayList<String> families = new ArrayList<String>();
			if (db!=null)
				families = helper.read_animals_family(parameters.getString("drugName"), parameters.getString("groupName"));
		
			for (int l=0;l<families.size();l++) {
				//if exist animals for OTARIDS
				
				TextView testView_family = new TextView(this);
				testView_family.setText(families.get(l));
				testView_family.setTextSize(20);
				testView_family.setTextColor(R.color.darkGray);
				testView_family.setTypeface(Typeface.SANS_SERIF);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.leftMargin = 30;
				params.topMargin = 20;
				layoutDose.addView(testView_family,layoutDose.getChildCount(),params);
				
				//dose information
				
				LinearLayout layout_dose_information = new LinearLayout(this);
				layout_dose_information.setOrientation(LinearLayout.VERTICAL);
				layout_dose_information.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				layout_dose_information.setBackgroundResource(R.drawable.layout_border);
				HashMap<String, ArrayList<String>> animal_information = new HashMap<String, ArrayList<String>>();
				if (db!=null)
					animal_information = helper.read_animal_information(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l));
				
				String animalName;
				Object [] animalsName = animal_information.keySet().toArray();
				for (int i=0;i<animalsName.length;i++) {
					
					//if exist animal name
					animalName = (String) animalsName[i];
					
					//if exist category
					ArrayList<String> categories = animal_information.get(animalName);
					String animalCategory;
					for (int j=0;j<categories.size();j++) {
							
						animalCategory = categories.get(j);
						
						if (!animalName.equals("") || !animalCategory.equals("")) {
						
							TableLayout animalTable = new TableLayout(this);
							animalTable.setStretchAllColumns(true);
							
							TableRow animalInformation = new TableRow(this);
							
							//Animal name
							
							TextView testView_animal_name = new TextView(this);
							testView_animal_name.setText(animalName);
							testView_animal_name.setTextColor(Color.BLACK);
							testView_animal_name.setTextSize(17);
							testView_animal_name.setTypeface(Typeface.SANS_SERIF);
							TableRow.LayoutParams paramsAnimalName = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
							paramsAnimalName.gravity = Gravity.CENTER;
							animalInformation.addView(testView_animal_name,paramsAnimalName);
							
							//Animal category
							
							TextView testView_animal_category = new TextView(this);
							testView_animal_category.setText(animalCategory);
							testView_animal_category.setTextColor(Color.BLACK);
							testView_animal_category.setTextSize(17);
							testView_animal_category.setTypeface(Typeface.SANS_SERIF);
							TableRow.LayoutParams paramsAnimalCategory = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
							paramsAnimalCategory.gravity = Gravity.CENTER;
							animalInformation.addView(testView_animal_category,paramsAnimalCategory);
							
							animalTable.addView(animalInformation);
							
							LinearLayout.LayoutParams paramsAnimalTable = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
							paramsAnimalTable.leftMargin = 60;
							paramsAnimalTable.rightMargin = 60;
							if (i==0 && j==0)
								paramsAnimalTable.topMargin = 5;
							else
								paramsAnimalTable.topMargin = 70;
			
							layout_dose_information.addView(animalTable,paramsAnimalTable);
						}
						
						ArrayList<Dose_Data> dose = new ArrayList<Dose_Data>();
						if (db!=null) 
							dose = helper.read_dose_information(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), animalName, animalCategory);
						
						TableLayout doseTable = new TableLayout(this);
						doseTable.setStretchAllColumns(true);
						
						TableRow header = new TableRow(this);
						
						TextView testView_dose_amount = new TextView(this);
						testView_dose_amount.setText("Dose");
						testView_dose_amount.setTextColor(Color.BLACK);
						testView_dose_amount.setTextSize(17);
						testView_dose_amount.setTypeface(Typeface.SANS_SERIF);
						TableRow.LayoutParams paramsAmount = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
						paramsAmount.gravity = Gravity.CENTER;
						header.addView(testView_dose_amount,paramsAmount);
						
						//Posology
						TextView testView_posology = new TextView(this);
						testView_posology.setText("Posology");
						testView_posology.setTextColor(Color.BLACK);
						testView_posology.setTextSize(17);
						testView_posology.setTypeface(Typeface.SANS_SERIF);
						TableRow.LayoutParams paramsPosology = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
						paramsPosology.gravity = Gravity.CENTER;
						header.addView(testView_posology, paramsPosology);
						
						//Route
						
						TextView testView_route = new TextView(this);
						testView_route.setText("Route");
						testView_route.setTextColor(Color.BLACK);
						testView_route.setTextSize(17);
						testView_route.setTypeface(Typeface.SANS_SERIF);
						TableRow.LayoutParams paramsRoute = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
						paramsRoute.gravity = Gravity.CENTER;
						header.addView(testView_route, paramsRoute);
						
						//Reference
						
						TextView testView_reference = new TextView(this);
						testView_reference.setText("Ref");
						testView_reference.setTextColor(Color.BLACK);
						testView_reference.setTextSize(17);
						testView_reference.setTypeface(Typeface.SANS_SERIF);
						TableRow.LayoutParams paramsReference = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
						paramsReference.gravity = Gravity.CENTER;
						header.addView(testView_reference, paramsReference);
						
						doseTable.addView(header);
						
						String doseAmount;
						String dosePosology;
						String doseRoute;
						String doseReference;
						for (int k=0;k<dose.size();k++) {
							doseAmount = dose.get(k).getAmount();
							dosePosology = dose.get(k).getPosology();
							doseRoute = dose.get(k).getRoute();
							doseReference = dose.get(k).getReference();
							
							TableRow doseInformation = new TableRow(this);
							//Dose amount data
							
							TextView testView_animal_dose_amount = new TextView(this);
							testView_animal_dose_amount.setText(doseAmount);
							testView_animal_dose_amount.setTextColor(Color.BLACK);
							testView_animal_dose_amount.setTextSize(15);
							testView_animal_dose_amount.setTypeface(Typeface.SANS_SERIF);
							TableRow.LayoutParams paramsDoseAmount = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
							paramsDoseAmount.gravity = Gravity.CENTER;
							doseInformation.addView(testView_animal_dose_amount, paramsDoseAmount);
							
							//Dose posology data
							
							TextView testView_animal_dose_posology = new TextView(this);
							testView_animal_dose_posology.setText(dosePosology);
							testView_animal_dose_posology.setTextColor(Color.BLACK);
							testView_animal_dose_posology.setTextSize(15);
							testView_animal_dose_posology.setTypeface(Typeface.SANS_SERIF);
							TableRow.LayoutParams paramsDosePosology = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
							paramsDosePosology.gravity = Gravity.CENTER;
							doseInformation.addView(testView_animal_dose_posology, paramsDosePosology);
							
							//Dose route data
							
							TextView testView_animal_dose_route = new TextView(this);
							testView_animal_dose_route.setText(doseRoute);
							testView_animal_dose_route.setTextColor(Color.BLACK);
							testView_animal_dose_route.setTextSize(15);
							testView_animal_dose_route.setTypeface(Typeface.SANS_SERIF);
							TableRow.LayoutParams paramsDoseRoute = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
							paramsDoseRoute.gravity = Gravity.CENTER;
							doseInformation.addView(testView_animal_dose_route, paramsDoseRoute);
							
							//Dose reference data
							
							TextView testView_animal_dose_reference = new TextView(this);
							testView_animal_dose_reference.setText(doseReference);
							testView_animal_dose_reference.setTextColor(Color.BLACK);
							testView_animal_dose_reference.setTextSize(15);
							testView_animal_dose_reference.setTypeface(Typeface.SANS_SERIF);
							TableRow.LayoutParams paramsDoseReference = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
							paramsDoseReference.gravity = Gravity.CENTER;
							doseInformation.addView(testView_animal_dose_reference, paramsDoseReference);
				
							doseTable.addView(doseInformation);
	
						}
						
						LinearLayout.LayoutParams paramsDoseTable = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						paramsDoseTable.leftMargin = 60;
						paramsDoseTable.rightMargin = 60;
						if ((i==0 && j==0) && (animalName.equals("") && animalCategory.equals("")))
							paramsDoseTable.topMargin = 5;
						else if (animalName.equals("") && animalCategory.equals(""))
							paramsDoseTable.topMargin = 70;
						
						layout_dose_information.addView(doseTable,layout_dose_information.getChildCount(), paramsDoseTable);
						
					}
					
				}
				
				layoutDose.addView(layout_dose_information,layoutDose.getChildCount());
			}
			helper.close();
			
			//Notes
			
			notes_interface("GENERAL NOTES", parameters.getString("drugName"), parameters.getString("groupName"));
			notes_interface("SPECIFIC NOTES", parameters.getString("drugName"), parameters.getString("groupName"));
			
		}
		
	}
	
	public void notes_interface(String notesOption, String drug_name, String group_name) {
		
		ArrayList<String> notes = new ArrayList<String>();
		SQLiteDatabase db = helper.open();
		if (db!=null) {
			notes = helper.read_notes(drug_name, group_name, notesOption);
			helper.close();
		}
		if (notes.size() > 0) {
			TextView testView_notes = new TextView(this);
			testView_notes.setText(notesOption);
			testView_notes.setTextSize(20);
			testView_notes.setTextColor(R.color.darkGray);
			testView_notes.setTypeface(Typeface.SANS_SERIF);
			LinearLayout.LayoutParams paramsNotes = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			paramsNotes.leftMargin = 30;
			paramsNotes.topMargin = 30;
			layoutDose.addView(testView_notes,layoutDose.getChildCount(),paramsNotes);
			
			LinearLayout layout_notes = new LinearLayout(this);
			layout_notes.setOrientation(LinearLayout.VERTICAL);
			layout_notes.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			layout_notes.setBackgroundResource(R.drawable.layout_border);
			
			for (int i=0;i<notes.size();i++) {
				TextView testView_note = new TextView(this);
				testView_note.setText(notes.get(i));
				testView_note.setTextColor(Color.BLACK);
				testView_note.setTextSize(16);
				testView_note.setTypeface(Typeface.SANS_SERIF);
				LinearLayout.LayoutParams paramsNote = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				paramsNote.leftMargin = 70;
				paramsNote.rightMargin = 60;
				paramsNote.topMargin = 30;
				layout_notes.addView(testView_note,layout_notes.getChildCount(),paramsNote);
			}
			
			layoutDose.addView(layout_notes,layoutDose.getChildCount());
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dose_information, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
