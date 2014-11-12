package com.example.drugsformarinemammals;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Dose_Information extends Activity {
	
	private LinearLayout layoutDose;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dose_information);
		TextView textViewDrug = (TextView) findViewById(R.id.textView_drug_name);
		textViewDrug.setTypeface(Typeface.SANS_SERIF);
		TextView textViewGroupName = (TextView) findViewById(R.id.textView_group_name);
		textViewGroupName.setTypeface(Typeface.SANS_SERIF);
		layoutDose = (LinearLayout) findViewById(R.id.layout_dose);
		
		int numFamily = 2;
		for (int l=0;l<numFamily;l++) {
			//if exist animals for OTARIDS
			
			TextView testView_family = new TextView(this);
			testView_family.setText("OTARIDS");
			testView_family.setTextSize(20);
			testView_family.setTextColor(Color.BLACK);
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
			
			int numAnimals = 2;
			String animalName;
			for (int i=0;i<numAnimals;i++) {
				
				//if exist animal name
				animalName="Californian Sea Lion";
				
				//if exist category
				int numCategory = 3;
				String animalCategory;
				for (int j=0;j<numCategory;j++) {
						
					animalCategory="general";
					
					LinearLayout layout_animal_information = new LinearLayout(this);
					layout_animal_information.setOrientation(LinearLayout.HORIZONTAL);
					layout_animal_information.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					
					LinearLayout layout_dose_information_header = new LinearLayout(this);
					layout_dose_information_header.setOrientation(LinearLayout.HORIZONTAL);
					layout_dose_information_header.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					
					//Animal name
					
					TextView testView_animal_name = new TextView(this);
					testView_animal_name.setText(animalName);
					testView_animal_name.setTextColor(Color.BLACK);
					testView_animal_name.setTextSize(15);
					testView_animal_name.setTypeface(Typeface.SANS_SERIF);
					LinearLayout.LayoutParams paramsAnimalName = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					paramsAnimalName.leftMargin = 190;
					paramsAnimalName.topMargin = 25;
					layout_animal_information.addView(testView_animal_name,layout_animal_information.getChildCount(),paramsAnimalName);
					
					//Animal category
					
					TextView testView_animal_category = new TextView(this);
					testView_animal_category.setText(animalCategory);
					testView_animal_category.setTextColor(Color.BLACK);
					testView_animal_category.setTextSize(15);
					testView_animal_category.setTypeface(Typeface.SANS_SERIF);
					LinearLayout.LayoutParams paramsAnimalCategory = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					paramsAnimalCategory.leftMargin = 100;
					paramsAnimalCategory.topMargin = 25;
					paramsAnimalCategory.rightMargin = 60;
					layout_animal_information.addView(testView_animal_category,layout_animal_information.getChildCount(),paramsAnimalCategory);
					
					//Dose amount
					
					TextView testView_cant_dose = new TextView(this);
					testView_cant_dose.setText("Dose");
					testView_cant_dose.setTextColor(Color.BLACK);
					testView_cant_dose.setTextSize(16);
					testView_cant_dose.setTypeface(Typeface.SANS_SERIF);
					LinearLayout.LayoutParams paramsCantDose = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					paramsCantDose.leftMargin = 170;
					paramsCantDose.topMargin = 5;
					layout_dose_information_header.addView(testView_cant_dose,layout_dose_information_header.getChildCount(),paramsCantDose);
					
					//Posology
					TextView testView_posology = new TextView(this);
					testView_posology.setText("Posology");
					testView_posology.setTextColor(Color.BLACK);
					testView_posology.setTextSize(16);
					testView_posology.setTypeface(Typeface.SANS_SERIF);
					LinearLayout.LayoutParams paramsPosology = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					paramsPosology.leftMargin = 100;
					paramsPosology.topMargin = 5;
					layout_dose_information_header.addView(testView_posology,layout_dose_information_header.getChildCount(),paramsPosology);
					
					//Route
					
					TextView testView_route = new TextView(this);
					testView_route.setText("Route");
					testView_route.setTextColor(Color.BLACK);
					testView_route.setTextSize(16);
					testView_route.setTypeface(Typeface.SANS_SERIF);
					LinearLayout.LayoutParams paramsRoute = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					paramsRoute.leftMargin = 65;
					paramsRoute.topMargin = 5;
					layout_dose_information_header.addView(testView_route,layout_dose_information_header.getChildCount(),paramsRoute);
					
					//Reference
					
					TextView testView_reference = new TextView(this);
					testView_reference.setText("Ref");
					testView_reference.setTextColor(Color.BLACK);
					testView_reference.setTextSize(16);
					testView_reference.setTypeface(Typeface.SANS_SERIF);
					LinearLayout.LayoutParams paramsReference = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					paramsReference.leftMargin = 90;
					paramsReference.rightMargin = 60;
					paramsReference.topMargin = 5;
					layout_dose_information_header.addView(testView_reference,layout_dose_information_header.getChildCount(),paramsReference);
	
					layout_dose_information.addView(layout_animal_information,layout_dose_information.getChildCount());
					
					layout_dose_information.addView(layout_dose_information_header,layout_dose_information.getChildCount());
					
					int numDose = 2;
					String doseAmount;
					String dosePosology;
					String doseRoute;
					String doseReference;
					for (int k=0;k<numDose;k++) {
						doseAmount ="2.5-5.0 mg/kg";
						dosePosology="BID";
						doseRoute="IM,IV,PO";
						doseReference="CRC";
						
						LinearLayout layout_animal_dose = new LinearLayout(this);
						layout_animal_dose.setOrientation(LinearLayout.HORIZONTAL);
						layout_animal_dose.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
						
						//Dose amount data
						
						TextView testView_animal_dose_amount = new TextView(this);
						testView_animal_dose_amount.setText(doseAmount);
						testView_animal_dose_amount.setTextColor(Color.BLACK);
						testView_animal_dose_amount.setTextSize(15);
						testView_animal_dose_amount.setTypeface(Typeface.SANS_SERIF);
						LinearLayout.LayoutParams paramsDoseAmount = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						paramsDoseAmount.leftMargin = 100;
						paramsDoseAmount.topMargin = 5;
						layout_animal_dose.addView(testView_animal_dose_amount,layout_animal_dose.getChildCount(),paramsDoseAmount);
						
						//Dose posology data
						
						TextView testView_animal_dose_posology = new TextView(this);
						testView_animal_dose_posology.setText(dosePosology);
						testView_animal_dose_posology.setTextColor(Color.BLACK);
						testView_animal_dose_posology.setTextSize(15);
						testView_animal_dose_posology.setTypeface(Typeface.SANS_SERIF);
						LinearLayout.LayoutParams paramsDosePosology = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						paramsDosePosology.leftMargin = 70;
						paramsDosePosology.topMargin = 5;
						layout_animal_dose.addView(testView_animal_dose_posology,layout_animal_dose.getChildCount(),paramsDosePosology);
						
						//Dose route data
						
						TextView testView_animal_dose_route = new TextView(this);
						testView_animal_dose_route.setText(doseRoute);
						testView_animal_dose_route.setTextColor(Color.BLACK);
						testView_animal_dose_route.setTextSize(15);
						testView_animal_dose_route.setTypeface(Typeface.SANS_SERIF);
						LinearLayout.LayoutParams paramsDoseRoute = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						paramsDoseRoute.leftMargin = 100;
						paramsDoseRoute.topMargin = 5;
						layout_animal_dose.addView(testView_animal_dose_route,layout_animal_dose.getChildCount(),paramsDoseRoute);
						
						//Dose reference data
						
						TextView testView_animal_dose_reference = new TextView(this);
						testView_animal_dose_reference.setText(doseReference);
						testView_animal_dose_reference.setTextColor(Color.BLACK);
						testView_animal_dose_reference.setTextSize(15);
						testView_animal_dose_reference.setTypeface(Typeface.SANS_SERIF);
						LinearLayout.LayoutParams paramsDoseReference = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						paramsDoseReference.leftMargin = 60;
						paramsDoseReference.rightMargin = 60;
						paramsDoseReference.topMargin = 5;
						layout_animal_dose.addView(testView_animal_dose_reference,layout_animal_dose.getChildCount(),paramsDoseReference);
					
						layout_dose_information.addView(layout_animal_dose,layout_dose_information.getChildCount());

					}
					
				}
				
			}
			
			layoutDose.addView(layout_dose_information,layoutDose.getChildCount());
		}
		
		//General Notes
		TextView testView_generalNotes = new TextView(this);
		testView_generalNotes.setText("GENERAL NOTES");
		testView_generalNotes.setTextSize(20);
		testView_generalNotes.setTextColor(Color.BLACK);
		testView_generalNotes.setTypeface(Typeface.SANS_SERIF);
		LinearLayout.LayoutParams paramsGeneralNotes = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		paramsGeneralNotes.leftMargin = 30;
		paramsGeneralNotes.topMargin = 30;
		layoutDose.addView(testView_generalNotes,layoutDose.getChildCount(),paramsGeneralNotes);
		notes_interface();
		
		//Specific Notes
		TextView testView_specificNotes = new TextView(this);
		testView_specificNotes.setText("SPECIFIC NOTES");
		testView_specificNotes.setTextSize(20);
		testView_specificNotes.setTextColor(Color.BLACK);
		testView_specificNotes.setTypeface(Typeface.SANS_SERIF);
		LinearLayout.LayoutParams paramsSpecificNotes = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		paramsSpecificNotes.leftMargin = 30;
		paramsSpecificNotes.topMargin = 30;
		layoutDose.addView(testView_specificNotes,layoutDose.getChildCount(),paramsSpecificNotes);
		notes_interface();
		
	}
	
	public void notes_interface() {
		LinearLayout layout_notes = new LinearLayout(this);
		layout_notes.setOrientation(LinearLayout.VERTICAL);
		layout_notes.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		layout_notes.setBackgroundResource(R.drawable.layout_border);
		
		int numNotes = 2;
		for (int i=0;i<numNotes;i++) {
			TextView testView_note = new TextView(this);
			testView_note.setText("may increase ototoxicity and nephrotoxicity of cephalosporins (e.g. gentamicin)");
			testView_note.setTextColor(Color.BLACK);
			testView_note.setTextSize(15);
			testView_note.setTypeface(Typeface.SANS_SERIF);
			LinearLayout.LayoutParams paramsNote = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			paramsNote.leftMargin = 70;
			paramsNote.rightMargin = 60;
			paramsNote.topMargin = 30;
			layout_notes.addView(testView_note,layout_notes.getChildCount(),paramsNote);
		}
		
		layoutDose.addView(layout_notes,layoutDose.getChildCount());
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
