package com.example.drugsformarinemammals;

import java.util.ArrayList;
import java.util.HashMap;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Fragment_Pinnipeds extends Fragment {
	
	private View rootView;
	private Handler_Sqlite helper;
	private LinearLayout layout_dose;
	private int screenWidth;
	private char reference_index;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_pinnipeds, container, false);
		layout_dose = (LinearLayout) rootView.findViewById(R.id.layout_pinnipeds_dose);
		
		helper = new Handler_Sqlite(getActivity());
		SQLiteDatabase db = helper.open();
		
		LinearLayout layout_dose_information = new LinearLayout(getActivity());
		layout_dose_information.setOrientation(LinearLayout.VERTICAL);
		layout_dose_information.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		layout_dose_information.setBackgroundResource(R.drawable.layout_border);
		
		ArrayList<String> notes_index = new ArrayList<String>();
		ArrayList<String> references = new ArrayList<String>();
		ArrayList<Article_Reference> references_index = new ArrayList<Article_Reference>();
		reference_index = 'a';
		ArrayList<Dose_Data> dose = new ArrayList<Dose_Data>();
		if (db!=null) {
			dose = helper.read_dose_information(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), "", "");
		}
		TableLayout doseTable = new TableLayout(getActivity());
		doseTable.setStretchAllColumns(true);
		
		TableRow header = new TableRow(getActivity());
		
		screenWidth = Integer.parseInt(getString(R.string.display));
		
		//Amount
		
		TextView textView_dose_amount = new TextView(getActivity());
		textView_dose_amount.setText("Dose");
		textView_dose_amount.setSingleLine(true);
		textView_dose_amount.setTextColor(getResources().getColor(R.color.darkGray));
		textView_dose_amount.setTextSize(17);
		textView_dose_amount.setTypeface(Typeface.SANS_SERIF, Typeface.DEFAULT_BOLD.getStyle());
		TableRow.LayoutParams paramsAmount = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
		paramsAmount.gravity = Gravity.CENTER;
		header.addView(textView_dose_amount,paramsAmount);
		
		//Posology
		TextView textView_posology = new TextView(getActivity());
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
		if ((screenWidth < 600 && !isCollapsed(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), "Posology")) || screenWidth >= 600)
			header.addView(textView_posology,paramsPosology);
		
		//Route
		
		TextView textView_route = new TextView(getActivity());
		textView_route.setText("Route");
		textView_route.setSingleLine(true);
		textView_route.setTextColor(getResources().getColor(R.color.darkGray));
		textView_route.setTextSize(17);
		textView_route.setTypeface(Typeface.SANS_SERIF, Typeface.DEFAULT_BOLD.getStyle());
		TableRow.LayoutParams paramsRoute = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
		paramsRoute.gravity = Gravity.CENTER;
		header.addView(textView_route,paramsRoute);
		
		//Reference
		
		TextView textView_reference = new TextView(getActivity());
		textView_reference.setText("Ref");
		textView_reference.setSingleLine(true);
		textView_reference.setTextColor(getResources().getColor(R.color.darkGray));
		textView_reference.setTextSize(17);
		textView_reference.setTypeface(Typeface.SANS_SERIF, Typeface.DEFAULT_BOLD.getStyle());
		TableRow.LayoutParams paramsReference = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
		paramsReference.gravity = Gravity.CENTER;
		header.addView(textView_reference,paramsReference);
		
		//Specific Note
		
		TextView textView_specific_note = new TextView(getActivity());
		textView_specific_note.setText("Note");
		textView_specific_note.setSingleLine(true);
		textView_specific_note.setTextColor(getResources().getColor(R.color.darkGray));
		textView_specific_note.setTextSize(17);
		textView_specific_note.setTypeface(Typeface.SANS_SERIF, Typeface.DEFAULT_BOLD.getStyle());
		TableRow.LayoutParams paramsSpecificNote = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
		paramsSpecificNote.gravity = Gravity.CENTER;
		if ((screenWidth < 600 && !isCollapsed(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), "Note")) || screenWidth >= 600)
			header.addView(textView_specific_note,paramsSpecificNote);
		
		TableRow doseData = new TableRow(getActivity());
		doseTable.addView(header);
		
		//General Dose
		
		if (dose.size() > 0) {
			show_dose(dose, doseTable, doseData, getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), "", "", notes_index, references, references_index);
		}
		
		HashMap<String, ArrayList<String>> animal_information = new HashMap<String, ArrayList<String>>();
		if (db!=null)
			animal_information = helper.read_animal_information(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"));
		
		String animalName;
		Object [] animalsName = animal_information.keySet().toArray();
		for (int i=0;i<animalsName.length;i++) {
			doseData = new TableRow(getActivity());
			
			//if exists animal name
			animalName = (String) animalsName[i];
			TextView textView_animal_name = new TextView(getActivity());
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
					
					TextView textView_animal_category = new TextView(getActivity());
					textView_animal_category.setText(animalCategory);
					textView_animal_category.setSingleLine(false);
					textView_animal_category.setTextColor(Color.BLACK);
					textView_animal_category.setTextSize(15);
					textView_animal_category.setTypeface(Typeface.SANS_SERIF);
					if (!animalName.equals("")) {
						if (j == 0) {
							TableRow.LayoutParams paramsAnimalName = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
							if (screenWidth < 600 && isCollapsed(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), "Posology") && isCollapsed(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), "Note"))
								paramsAnimalName.span = 3;
							else if (screenWidth < 600 && (isCollapsed(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), "Posology") || isCollapsed(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), "Note")))
								paramsAnimalName.span = 4;
							else
								paramsAnimalName.span = 5;
							doseData.addView(textView_animal_name,paramsAnimalName);
							doseTable.addView(doseData);
						}
						
						if (db!=null) 
							dose = helper.read_dose_information(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), animalName, animalCategory);
						
						doseData = new TableRow(getActivity());
						textView_animal_category.setTypeface(Typeface.SANS_SERIF, Typeface.ITALIC);
						TableRow.LayoutParams paramsCategoryName = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
							
						if (screenWidth < 600 && isCollapsed(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), "Posology") && isCollapsed(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), "Note"))
							paramsCategoryName.span = 3;
						else if (screenWidth < 600 && (isCollapsed(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), "Posology") || isCollapsed(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), "Note")))
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
						doseData = new TableRow(getActivity());
						show_dose(dose, doseTable, doseData, getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), animalName, animalCategory, notes_index, references, references_index);
						
						doseData = new TableRow(getActivity());
					}
					else {
						if (db!=null) 
							dose = helper.read_dose_information(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), animalName, animalCategory);
						
						textView_animal_category.setTypeface(Typeface.SANS_SERIF, Typeface.DEFAULT_BOLD.getStyle());
						textView_animal_category.setTextColor(getResources().getColor(R.color.darkGray));
						TableRow.LayoutParams paramsCategoryName = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
						if (screenWidth < 600 && isCollapsed(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), "Posology") && isCollapsed(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), "Note"))
							paramsCategoryName.span = 3;
						else if (screenWidth < 600 && (isCollapsed(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), "Posology") || isCollapsed(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), "Note")))
							paramsCategoryName.span = 4;
						else
							paramsCategoryName.span = 5;
						doseData.addView(textView_animal_category,paramsCategoryName);
						doseTable.addView(doseData);
						doseData = new TableRow(getActivity());
						show_dose(dose, doseTable, doseData, getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), animalName, animalCategory, notes_index, references, references_index);
						
						doseData = new TableRow(getActivity());
					}
					
				}
				
				if (!animalName.equals("") && animalCategory.equals("")) {
					if (db!=null) 
						dose = helper.read_dose_information(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), animalName, animalCategory);
					
					TableRow.LayoutParams paramsAnimalName = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
					if (screenWidth < 600 && isCollapsed(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), "Posology") && isCollapsed(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), "Note"))
						paramsAnimalName.span = 3;
					else if (screenWidth < 600 && (isCollapsed(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), "Posology") || isCollapsed(getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), "Note")))
						paramsAnimalName.span = 4;
					else
						paramsAnimalName.span = 5;
					doseData.addView(textView_animal_name,paramsAnimalName);
					doseTable.addView(doseData);
					doseData = new TableRow(getActivity());
					show_dose(dose, doseTable, doseData, getArguments().getString("drugName"), "Pinnipeds", getArguments().getString("familyName"), animalName, animalCategory, notes_index, references, references_index);
					
					doseData = new TableRow(getActivity());
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
			paramsDoseTable.rightMargin = 60;
		}
		layout_dose_information.addView(doseTable, paramsDoseTable);
		
		LinearLayout.LayoutParams paramsLayoutDose = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		paramsLayoutDose.topMargin = 15;
		
		layout_dose.addView(layout_dose_information,layout_dose.getChildCount(), paramsLayoutDose);
	
		helper.close();
	
		//Notes
		additionalInformationInterface("GENERAL NOTES", getArguments().getString("drugName"), "Pinnipeds", notes_index, references_index);
		additionalInformationInterface("SPECIFIC NOTES", getArguments().getString("drugName"), "Pinnipeds", notes_index, references_index);
		//References
		additionalInformationInterface("REFERENCES", getArguments().getString("drugName"), "Pinnipeds", notes_index, references_index);
	
		return rootView;
	
	}


	public void additionalInformationInterface(String option, String drug_name, String group_name, ArrayList<String> notesIndex, ArrayList<Article_Reference> referencesIndex) {
		
		LinearLayout borderLayout = createBorderLayout();
		ArrayList<String> notes = new ArrayList<String>();
		if (option.equals("GENERAL NOTES")) {
			SQLiteDatabase db = helper.open();
			if (db!=null) {
				notes = helper.read_general_notes(drug_name, group_name);
				helper.close();
			}
		}
		else if (option.equals("SPECIFIC NOTES")) {
			notes = notesIndex;
		}	
		
		if (((option.equals("GENERAL NOTES") || option.equals("SPECIFIC NOTES")) && notes.size() > 0) || (option.equals("REFERENCES") && referencesIndex.size() > 0)) {	
			TextView titleTextView = createTitleTextView();
			titleTextView.setText(option);
			LinearLayout.LayoutParams params = createTextViewParams("Title TextView");
			layout_dose.addView(titleTextView,layout_dose.getChildCount(),params);
			if (option.equals("GENERAL NOTES") || option.equals("SPECIFIC NOTES")) {
				for (int i=0;i<notes.size();i++) {
					TextView informationTextView = createInformationTextView();
					if (option.equals("GENERAL NOTES"))
						informationTextView.setText("�	" + notes.get(i));
					else
						informationTextView.setText("(" + (i+1) + ")	" + notesIndex.get(i));
					LinearLayout.LayoutParams informationParams = createTextViewParams("Information TextView");
					borderLayout.addView(informationTextView,borderLayout.getChildCount(),informationParams);
				}
			}
			else {
				for (int i=0;i<referencesIndex.size();i++) {
					TextView informationTextView = createInformationTextView();
					informationTextView.setText("(" + referencesIndex.get(i).getIndex() + ")   " + referencesIndex.get(i).getArticle());
					LinearLayout.LayoutParams informationParams = createTextViewParams("Information TextView");
					borderLayout.addView(informationTextView,borderLayout.getChildCount(),informationParams);
				}
			}
		}
		
		layout_dose.addView(borderLayout,layout_dose.getChildCount());
		
	}
	
	public LinearLayout createBorderLayout() {
		LinearLayout border_layout = new LinearLayout(getActivity());
		border_layout.setOrientation(LinearLayout.VERTICAL);
		border_layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		border_layout.setBackgroundResource(R.drawable.layout_border);
		return border_layout;
	}
	
	public TextView createTitleTextView() {
		TextView title_textview = new TextView(getActivity());
		title_textview.setTextSize(20);
		title_textview.setTextColor(getResources().getColor(R.color.darkGray));
		title_textview.setTypeface(Typeface.SANS_SERIF, Typeface.DEFAULT_BOLD.getStyle());
		return title_textview;
	}
	
	public TextView createInformationTextView() {
		TextView information_textview = new TextView(getActivity());
		information_textview.setTextColor(Color.BLACK);
		information_textview.setTextSize(16);
		information_textview.setTypeface(Typeface.SANS_SERIF);
		return information_textview;
	}
	
	public LinearLayout.LayoutParams createTextViewParams(String option) {
		
		LinearLayout.LayoutParams textview_params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		if (option.equals("Title TextView")) {
			textview_params.leftMargin = 30;
			textview_params.topMargin = 60;
		}
		else {
			if (screenWidth >= 600) {
				textview_params.leftMargin = 45;
				textview_params.rightMargin = 45;
			}
			else {
				textview_params.leftMargin = 60;
				textview_params.rightMargin = 60;
			}
			textview_params.topMargin = 5;
		}
		return textview_params;
	}

	public void show_dose(ArrayList<Dose_Data> dose, TableLayout dose_table, TableRow dose_data, String drug_name, String group_name, String animal_family, String animal_name, String animal_category, ArrayList<String> notes, ArrayList<String> references, ArrayList<Article_Reference> references_index) {

		String doseAmount;
		String dosePosology;
		String doseRoute;
		String doseBookReference;
		String doseArticleReference;
		for (int k=0;k<dose.size();k++) {
			if (k > 0) {
				dose_data = new TableRow(getActivity());
			}
			
			doseAmount = dose.get(k).getAmount();
			dosePosology = dose.get(k).getPosology();
			doseRoute = dose.get(k).getRoute();
			doseBookReference = dose.get(k).getBookReference();
			doseArticleReference = dose.get(k).getArticleReference();
			
			//Dose amount data
			
			TextView textView_animal_dose_amount = new TextView(getActivity());
			textView_animal_dose_amount.setText(doseAmount);
			textView_animal_dose_amount.setSingleLine(false);
			textView_animal_dose_amount.setTextColor(Color.BLACK);
			textView_animal_dose_amount.setTextSize(15);
			textView_animal_dose_amount.setTypeface(Typeface.SANS_SERIF);
			TableRow.LayoutParams paramsDoseAmount = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
			paramsDoseAmount.gravity = Gravity.CENTER;
			dose_data.addView(textView_animal_dose_amount, paramsDoseAmount);
			
			//Dose posology data
			
			TextView textView_animal_dose_posology = new TextView(getActivity());
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
			
			TextView textView_animal_dose_route = new TextView(getActivity());
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
			
			TextView textView_animal_dose_reference = new TextView(getActivity());
			if (!doseBookReference.equals(""))
				textView_animal_dose_reference.setText(doseBookReference);
			else if (!doseArticleReference.equals("")) {
				if (!references.contains(doseArticleReference)) {
					references.add(references.size(),doseArticleReference);
					Article_Reference article_reference = new Article_Reference(reference_index, doseArticleReference);
					references_index.add(references_index.size(), article_reference);
					reference_index++;
				}
				int article_index = references.indexOf(doseArticleReference);
				textView_animal_dose_reference.setText("(" + references_index.get(article_index).getIndex() + ")");
			}
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
						doseAmount, dosePosology, doseRoute, doseBookReference, doseArticleReference);
			
			String index = "";
			for (int m=0;m<specific_notes.size();m++) {
				String note = specific_notes.get(m);
				if (!notes.contains(note)) {
					notes.add(notes.size(), note);
				}
				index+="(" + (notes.indexOf(note)+1) + ")  ";
			}
			
			TextView textView_specific_note_index = new TextView(getActivity());
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
						dose.getAmount(), dose.getPosology(), dose.getRoute(), dose.getBookReference(), dose.getArticleReference());
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
	
	public static Fragment_Pinnipeds newInstance(Bundle arguments){
		Fragment_Pinnipeds fragment = new Fragment_Pinnipeds();
        if(arguments != null){
            fragment.setArguments(arguments);
        }
        return fragment;
    }
			
}
