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
	private int screenWidth;

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
			ArrayList<String> notes_index = new ArrayList<String>();
			ArrayList<String> families = new ArrayList<String>();
			if (db!=null)
				families = helper.read_animals_family(parameters.getString("drugName"), parameters.getString("groupName"));
		
			for (int l=0;l<families.size();l++) {
				//if exists animals family
				
				TextView textView_family = new TextView(this);
				textView_family.setText(families.get(l));
				textView_family.setTextSize(20);
				textView_family.setTextColor(getResources().getColor(R.color.darkGray));
				textView_family.setTypeface(Typeface.SANS_SERIF, Typeface.DEFAULT_BOLD.getStyle());

				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.leftMargin = 30;
				params.topMargin = 20;
				layoutDose.addView(textView_family,layoutDose.getChildCount(),params);
				
				//dose information
				
				LinearLayout layout_dose_information = new LinearLayout(this);
				layout_dose_information.setOrientation(LinearLayout.VERTICAL);
				layout_dose_information.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				layout_dose_information.setBackgroundResource(R.drawable.layout_border);
				
				ArrayList<Dose_Data> dose = new ArrayList<Dose_Data>();
				if (db!=null) {
					dose = helper.read_dose_information(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), "", "");
				}
				TableLayout doseTable = new TableLayout(this);
				doseTable.setStretchAllColumns(true);
				
				screenWidth = Integer.parseInt(getString(R.string.display));
				
				TableRow header = new TableRow(this);
				
				//Amount
				
				TextView textView_dose_amount = new TextView(this);
				textView_dose_amount.setText("Dose");
				textView_dose_amount.setSingleLine(true);
				textView_dose_amount.setTextColor(getResources().getColor(R.color.darkGray));
				textView_dose_amount.setTextSize(17);
				textView_dose_amount.setTypeface(Typeface.SANS_SERIF, Typeface.DEFAULT_BOLD.getStyle());
				TableRow.LayoutParams paramsAmount = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
				paramsAmount.gravity = Gravity.CENTER;
				header.addView(textView_dose_amount,paramsAmount);
				
				//Posology
				TextView textView_posology = new TextView(this);
				if (screenWidth >= 720)
					textView_posology.setText("Posology");
				else
					textView_posology.setText("Pos");
				textView_posology.setSingleLine(true);
				textView_posology.setTextColor(getResources().getColor(R.color.darkGray));
				textView_posology.setTextSize(17);
				textView_posology.setTypeface(Typeface.SANS_SERIF, Typeface.DEFAULT_BOLD.getStyle());
				TableRow.LayoutParams paramsPosology = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
				paramsPosology.gravity = Gravity.CENTER;
				if ((screenWidth < 600 && !isCollapsed(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), "Posology")) || screenWidth >= 600)
					header.addView(textView_posology,paramsPosology);
				
				//Route
				
				TextView textView_route = new TextView(this);
				textView_route.setText("Route");
				textView_route.setSingleLine(true);
				textView_route.setTextColor(getResources().getColor(R.color.darkGray));
				textView_route.setTextSize(17);
				textView_route.setTypeface(Typeface.SANS_SERIF, Typeface.DEFAULT_BOLD.getStyle());
				TableRow.LayoutParams paramsRoute = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
				paramsRoute.gravity = Gravity.CENTER;
				header.addView(textView_route,paramsRoute);
				
				//Reference
				
				TextView textView_reference = new TextView(this);
				textView_reference.setText("Ref");
				textView_reference.setSingleLine(true);
				textView_reference.setTextColor(getResources().getColor(R.color.darkGray));
				textView_reference.setTextSize(17);
				textView_reference.setTypeface(Typeface.SANS_SERIF, Typeface.DEFAULT_BOLD.getStyle());
				TableRow.LayoutParams paramsReference = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
				paramsReference.gravity = Gravity.CENTER;
				header.addView(textView_reference,paramsReference);
				
				//Specific Note
				
				TextView textView_specific_note = new TextView(this);
				textView_specific_note.setText("Note");
				textView_specific_note.setSingleLine(true);
				textView_specific_note.setTextColor(getResources().getColor(R.color.darkGray));
				textView_specific_note.setTextSize(17);
				textView_specific_note.setTypeface(Typeface.SANS_SERIF, Typeface.DEFAULT_BOLD.getStyle());
				TableRow.LayoutParams paramsSpecificNote = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
				paramsSpecificNote.gravity = Gravity.CENTER;
				if ((screenWidth < 600 && !isCollapsed(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), "Note")) || screenWidth >= 600)
					header.addView(textView_specific_note,paramsSpecificNote);
				
				TableRow doseData = new TableRow(this);
				doseTable.addView(header);
				
				//General Dose
				
				if (dose.size() > 0) {
					show_dose(dose, doseTable, doseData, parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), "", "", notes_index);
				}
				
				HashMap<String, ArrayList<String>> animal_information = new HashMap<String, ArrayList<String>>();
				if (db!=null)
					animal_information = helper.read_animal_information(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l));
				
				String animalName;
				Object [] animalsName = animal_information.keySet().toArray();
				for (int i=0;i<animalsName.length;i++) {
					doseData = new TableRow(this);
					
					//if exists animal name
					animalName = (String) animalsName[i];
					TextView textView_animal_name = new TextView(this);
					if (!animalName.equals("")) {
						
						//Animal name
						
						textView_animal_name.setText(animalName);
						textView_animal_name.setSingleLine(false);
						textView_animal_name.setTextColor(getResources().getColor(R.color.darkGray));
						textView_animal_name.setTextSize(15);
						textView_animal_name.setTypeface(Typeface.SANS_SERIF, Typeface.DEFAULT_BOLD.getStyle());
					}
					
					//if exists category
					ArrayList<String> categories = animal_information.get(animalName);
					String animalCategory;
					for (int j=0;j<categories.size();j++) {
							
						animalCategory = categories.get(j);
						
						if (!animalCategory.equals("")) {
							
							//Animal category
							
							TextView textView_animal_category = new TextView(this);
							textView_animal_category.setText(animalCategory);
							textView_animal_category.setSingleLine(false);
							textView_animal_category.setTextColor(Color.BLACK);
							textView_animal_category.setTextSize(15);
							textView_animal_category.setTypeface(Typeface.SANS_SERIF);
							if (!animalName.equals("")) {
								if (j == 0) {
									TableRow.LayoutParams paramsAnimalName = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
									if (screenWidth < 600 && isCollapsed(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), "Posology") && isCollapsed(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), "Note"))
										paramsAnimalName.span = 3;
									else if (screenWidth < 600 && (isCollapsed(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), "Posology") || isCollapsed(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), "Note")))
										paramsAnimalName.span = 4;
									else
										paramsAnimalName.span = 5;
									
									doseData.addView(textView_animal_name,paramsAnimalName);
									doseTable.addView(doseData);
								}
								
								if (db!=null) 
									dose = helper.read_dose_information(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), animalName, animalCategory);
								
								doseData = new TableRow(this);
								textView_animal_category.setTypeface(Typeface.SANS_SERIF, Typeface.ITALIC);
								TableRow.LayoutParams paramsCategoryName = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
								if (screenWidth < 600 && isCollapsed(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), "Posology") && isCollapsed(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), "Note"))
									paramsCategoryName.span = 3;
								else if (screenWidth < 600 && (isCollapsed(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), "Posology") || isCollapsed(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), "Note")))
									paramsCategoryName.span = 4;
								else
									paramsCategoryName.span = 5;
								if (screenWidth < 600)
									paramsCategoryName.leftMargin = 15;
								else if (screenWidth >= 600 && screenWidth < 720)
									paramsCategoryName.leftMargin = 20;
								else
									paramsCategoryName.leftMargin = 30;
								doseData.addView(textView_animal_category,paramsCategoryName);
								doseTable.addView(doseData);
								doseData = new TableRow(this);
								show_dose(dose, doseTable, doseData, parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), animalName, animalCategory, notes_index);
								
								doseData = new TableRow(this);
							}
							else {
								if (db!=null) 
									dose = helper.read_dose_information(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), animalName, animalCategory);
								
								textView_animal_category.setTypeface(Typeface.SANS_SERIF, Typeface.DEFAULT_BOLD.getStyle());
								textView_animal_category.setTextColor(getResources().getColor(R.color.darkGray));
								TableRow.LayoutParams paramsCategoryName = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
								if (screenWidth < 600 && isCollapsed(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), "Posology") && isCollapsed(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), "Note"))
									paramsCategoryName.span = 3;
								else if (screenWidth < 600 && (isCollapsed(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), "Posology") || isCollapsed(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), "Note")))
									paramsCategoryName.span = 4;
								else
									paramsCategoryName.span = 5;
								doseData.addView(textView_animal_category,paramsCategoryName);
								doseTable.addView(doseData);
								doseData = new TableRow(this);
								show_dose(dose, doseTable, doseData, parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), animalName, animalCategory, notes_index);
								
								doseData = new TableRow(this);
							}
							
						}
						
						if (!animalName.equals("") && animalCategory.equals("")) {
							if (db!=null) 
								dose = helper.read_dose_information(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), animalName, animalCategory);
							
							TableRow.LayoutParams paramsAnimalName = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
							if (screenWidth < 600 && isCollapsed(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), "Posology") && isCollapsed(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), "Note"))
								paramsAnimalName.span = 3;
							else if (screenWidth < 600 && (isCollapsed(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), "Posology") || isCollapsed(parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), "Note")))
								paramsAnimalName.span = 4;
							else
								paramsAnimalName.span = 5;
							doseData.addView(textView_animal_name,paramsAnimalName);
							doseTable.addView(doseData);
							doseData = new TableRow(this);
							show_dose(dose, doseTable, doseData, parameters.getString("drugName"), parameters.getString("groupName"), families.get(l), animalName, animalCategory, notes_index);
							
							doseData = new TableRow(this);
						}
					}
					
				}
					
				LinearLayout.LayoutParams paramsDoseTable = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				if (screenWidth >= 600) {
					paramsDoseTable.topMargin = 5;
					paramsDoseTable.leftMargin = 50;
					paramsDoseTable.rightMargin = 50;
				}
				else {
					paramsDoseTable.topMargin = 5;
					paramsDoseTable.leftMargin = 60;
					paramsDoseTable.rightMargin = 30;
				}
				layout_dose_information.addView(doseTable, paramsDoseTable);
				
				layoutDose.addView(layout_dose_information,layoutDose.getChildCount());
			}
			helper.close();
			
			//Notes
			
			notes_interface("GENERAL NOTES", parameters.getString("drugName"), parameters.getString("groupName"), notes_index);
			notes_interface("SPECIFIC NOTES", parameters.getString("drugName"), parameters.getString("groupName"), notes_index);
			
		}
		
	}
	
	public void notes_interface(String notesOption, String drug_name, String group_name, ArrayList<String> notesIndex) {
		
		LinearLayout layout_notes = new LinearLayout(this);
		layout_notes.setOrientation(LinearLayout.VERTICAL);
		layout_notes.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		layout_notes.setBackgroundResource(R.drawable.layout_border);
		
		if (notesOption.equals("GENERAL NOTES")) {
			ArrayList<String> notes = new ArrayList<String>();
			SQLiteDatabase db = helper.open();
			if (db!=null) {
				notes = helper.read_general_notes(drug_name, group_name);
				helper.close();
			}
			if (notes.size() > 0) {
				TextView textView_notes = new TextView(this);
				textView_notes.setText(notesOption);
				textView_notes.setTextSize(20);
				textView_notes.setTextColor(getResources().getColor(R.color.darkGray));
				textView_notes.setTypeface(Typeface.SANS_SERIF, Typeface.DEFAULT_BOLD.getStyle());
				LinearLayout.LayoutParams paramsNotes = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				paramsNotes.leftMargin = 30;
				paramsNotes.topMargin = 60;
				layoutDose.addView(textView_notes,layoutDose.getChildCount(),paramsNotes);
				
				for (int i=0;i<notes.size();i++) {
					TextView textView_note = new TextView(this);
					textView_note.setText("�	" + notes.get(i));
					textView_note.setTextColor(Color.BLACK);
					textView_note.setTextSize(16);
					textView_note.setTypeface(Typeface.SANS_SERIF);
					LinearLayout.LayoutParams paramsNote = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					if (screenWidth >= 600) {
						paramsNote.leftMargin = 45;
						paramsNote.rightMargin = 45;
					}
					else {
						paramsNote.leftMargin = 60;
						paramsNote.rightMargin = 60;
					}
					paramsNote.topMargin = 5;
					layout_notes.addView(textView_note,layout_notes.getChildCount(),paramsNote);
				}
				
			}
		}
		else {
			if (notesIndex.size() > 0) {
				TextView textView_notes = new TextView(this);
				textView_notes.setText(notesOption);
				textView_notes.setTextSize(20);
				textView_notes.setTextColor(getResources().getColor(R.color.darkGray));
				textView_notes.setTypeface(Typeface.SANS_SERIF, Typeface.DEFAULT_BOLD.getStyle());
				LinearLayout.LayoutParams paramsNotes = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				paramsNotes.leftMargin = 30;
				paramsNotes.topMargin = 60;
				layoutDose.addView(textView_notes,layoutDose.getChildCount(),paramsNotes);
				
				for (int i=0;i<notesIndex.size();i++) {
					TextView textView_note = new TextView(this);
					textView_note.setText("(" + (i+1) + ")	" + notesIndex.get(i));
					textView_note.setTextColor(Color.BLACK);
					textView_note.setTextSize(16);
					textView_note.setTypeface(Typeface.SANS_SERIF);
					LinearLayout.LayoutParams paramsNote = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					if (screenWidth >= 600) {
						paramsNote.leftMargin = 45;
						paramsNote.rightMargin = 45;
					}
					else {
						paramsNote.leftMargin = 60;
						paramsNote.rightMargin = 60;
					}
					paramsNote.topMargin = 5;
					layout_notes.addView(textView_note,layout_notes.getChildCount(),paramsNote);
				}
			}
		}
		
		layoutDose.addView(layout_notes,layoutDose.getChildCount());
		
	}
	
	public void show_dose(ArrayList<Dose_Data> dose, TableLayout dose_table, TableRow dose_data, String drug_name, String group_name, String animal_family, String animal_name, String animal_category, ArrayList<String> notes) {
		
		String doseAmount;
		String dosePosology;
		String doseRoute;
		String doseReference;
		for (int k=0;k<dose.size();k++) {
			if (k > 0) {
				dose_data = new TableRow(this);
			}
			
			doseAmount = dose.get(k).getAmount();
			dosePosology = dose.get(k).getPosology();
			doseRoute = dose.get(k).getRoute();
			doseReference = dose.get(k).getReference();
			
			//Dose amount data
			
			TextView textView_animal_dose_amount = new TextView(this);
			textView_animal_dose_amount.setText(doseAmount);
			textView_animal_dose_amount.setSingleLine(false);
			textView_animal_dose_amount.setTextColor(Color.BLACK);
			textView_animal_dose_amount.setTextSize(15);
			textView_animal_dose_amount.setTypeface(Typeface.SANS_SERIF);
			TableRow.LayoutParams paramsDoseAmount = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
			paramsDoseAmount.gravity = Gravity.CENTER;
			dose_data.addView(textView_animal_dose_amount, paramsDoseAmount);
			
			//Dose posology data
			
			TextView textView_animal_dose_posology = new TextView(this);
			textView_animal_dose_posology.setText(dosePosology);
			textView_animal_dose_posology.setSingleLine(false);
			textView_animal_dose_posology.setTextColor(Color.BLACK);
			textView_animal_dose_posology.setTextSize(15);
			textView_animal_dose_posology.setTypeface(Typeface.SANS_SERIF);
			TableRow.LayoutParams paramsDosePosology = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
			paramsDosePosology.gravity = Gravity.CENTER;
			if ((screenWidth < 600 && !isCollapsed(drug_name, group_name, animal_family, "Posology")) || screenWidth >= 600)
				dose_data.addView(textView_animal_dose_posology, paramsDosePosology);
			
			//Dose route data
			
			TextView textView_animal_dose_route = new TextView(this);
			textView_animal_dose_route.setText(doseRoute);
			textView_animal_dose_route.setSingleLine(false);
			textView_animal_dose_route.setTextColor(Color.BLACK);
			textView_animal_dose_route.setTextSize(15);
			textView_animal_dose_route.setTypeface(Typeface.SANS_SERIF);
			if (screenWidth >= 600) {
				TableRow.LayoutParams paramsDoseRoute = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
				paramsDoseRoute.gravity = Gravity.CENTER;
				dose_data.addView(textView_animal_dose_route, paramsDoseRoute);		
			}
			else {
				TableRow.LayoutParams paramsDoseRoute = new TableRow.LayoutParams(30, TableRow.LayoutParams.WRAP_CONTENT);
				paramsDoseRoute.gravity = Gravity.CENTER;
				dose_data.addView(textView_animal_dose_route, paramsDoseRoute);
			}
			
			//Dose reference data
			
			TextView textView_animal_dose_reference = new TextView(this);
			textView_animal_dose_reference.setText(doseReference);
			textView_animal_dose_reference.setSingleLine(false);
			textView_animal_dose_reference.setTextColor(Color.BLACK);
			textView_animal_dose_reference.setTextSize(15);
			textView_animal_dose_reference.setTypeface(Typeface.SANS_SERIF);
			if (screenWidth >= 600) {
				TableRow.LayoutParams paramsDoseReference = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
				paramsDoseReference.gravity = Gravity.CENTER;
				dose_data.addView(textView_animal_dose_reference, paramsDoseReference);	
			}
			else {
				TableRow.LayoutParams paramsDoseReference = new TableRow.LayoutParams(150, TableRow.LayoutParams.WRAP_CONTENT);
				paramsDoseReference.gravity = Gravity.CENTER;
				dose_data.addView(textView_animal_dose_reference, paramsDoseReference);
			}
			
			//Specific note index
			
			ArrayList<String> specific_notes = new ArrayList<String>();
			specific_notes = helper.read_specific_notes(drug_name, group_name, animal_name, animal_family, animal_category, 
						dosePosology, doseRoute, doseReference);
			
			String index = "";
			for (int m=0;m<specific_notes.size();m++) {
				String note = specific_notes.get(m);
				if (!notes.contains(note)) {
					notes.add(notes.size(), note);
				}
				index+="(" + (notes.indexOf(note)+1) + ")  ";
			}
			
			TextView textView_specific_note_index = new TextView(this);
			textView_specific_note_index.setText(index);
			textView_specific_note_index.setSingleLine(false);
			textView_specific_note_index.setTextColor(Color.BLACK);
			textView_specific_note_index.setTextSize(15);
			textView_specific_note_index.setTypeface(Typeface.SANS_SERIF);
			if (screenWidth >= 600 && screenWidth < 720) {
				TableRow.LayoutParams paramsSpecificNoteIndex = new TableRow.LayoutParams(150, TableRow.LayoutParams.WRAP_CONTENT);
				paramsSpecificNoteIndex.gravity = Gravity.CENTER;
				dose_data.addView(textView_specific_note_index, paramsSpecificNoteIndex);
			}
			else if (screenWidth >= 720) {
				TableRow.LayoutParams paramsSpecificNoteIndex = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
				paramsSpecificNoteIndex.gravity = Gravity.CENTER;
				dose_data.addView(textView_specific_note_index, paramsSpecificNoteIndex);
			}	
			else {
				TableRow.LayoutParams paramsSpecificNoteIndex = new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT);
				paramsSpecificNoteIndex.gravity = Gravity.CENTER;
				if ((screenWidth < 600 && !isCollapsed(drug_name, group_name, animal_family, "Note")) || screenWidth >= 600)
					dose_data.addView(textView_specific_note_index, paramsSpecificNoteIndex);
			}
				
			dose_table.addView(dose_data);

		}
	}
	
	public boolean isCollapsed(String drug_name, String group_name, String family, String option) {
		ArrayList<Dose_Data> everyDose = new ArrayList<Dose_Data>();
		everyDose = helper.read_every_dose(drug_name, group_name, family);
		for (int i=0;i<everyDose.size();i++) {
			Dose_Data dose = everyDose.get(i);
			if ( option.equals("Note")) {
				ArrayList<String> specific_notes = new ArrayList<String>();
				specific_notes = helper.read_specific_notes(drug_name, group_name, dose.getAnimalName(), family, dose.getCategoryName(), 
						dose.getPosology(), dose.getRoute(), dose.getReference());
				if (specific_notes.size() > 0)
					return false;
			
			}
			else {
				if (!dose.getPosology().equals(""))
					return false;
			}
		}
		return true;
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