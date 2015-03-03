package com.example.drugsformarinemammals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.view.ViewGroup.LayoutParams;

public class Fragment_Calculator extends Fragment {
	private View rootView;
	private String userEntryDoseUnits;
	private String userEntryConcentrationsUnits;
	private EditText doseAmount;
	private EditText weightValue_kgs;
	private EditText concentrationValue;
	private Spinner spinnerDose;
	private Spinner spinnerConcentration;
	private Map<String,Float> conversionMap;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_calculator,container, false);
		userEntryDoseUnits="";
		userEntryConcentrationsUnits="";
				initializeMap();
		
		TextView weight_lbs = (TextView)rootView.findViewById(R.id.textview_WeightLbs);
		weight_lbs.setTypeface(Typeface.SANS_SERIF);
		
		EditText hint_weight_lbs=(EditText)rootView.findViewById(R.id.editText_WeightLbs);
		hint_weight_lbs.setTypeface(Typeface.SANS_SERIF);
		
		TextView weight_kgs = (TextView)rootView.findViewById(R.id.textview_WeightKgs);
		weight_kgs.setTypeface(Typeface.SANS_SERIF);
		
		weightValue_kgs=(EditText)rootView.findViewById(R.id.editText_WeightKgs);
		weightValue_kgs.setTypeface(Typeface.SANS_SERIF);
		
		TextView dose = (TextView)rootView.findViewById(R.id.textview_Dose);
		dose.setTypeface(Typeface.SANS_SERIF);
		
		doseAmount=(EditText)rootView.findViewById(R.id.editText_Dose);
		doseAmount.setTypeface(Typeface.SANS_SERIF);
		
		TextView concentration = (TextView)rootView.findViewById(R.id.textview_concentration);
		concentration.setTypeface(Typeface.SANS_SERIF);
		
		concentrationValue=(EditText)rootView.findViewById(R.id.editText_concentration);
		concentrationValue.setTypeface(Typeface.SANS_SERIF);
		
        Button calculate=(Button)rootView.findViewById(R.id.buttonCalculate);
        calculate.setTypeface(Typeface.SANS_SERIF);
        calculate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (weightValue_kgs.getText().toString().equals("") || doseAmount.getText().toString().equals("")
						|| concentrationValue.getText().toString().equals(""))
					showDialog("NO DATA", "Please enter all the data for the calculation");
				else if (Float.parseFloat(concentrationValue.getText().toString()) == 0.0f)
					showDialog("WRONG VALUE", "Enter a correct value of concentration");
				else if (spinnerDose.getSelectedItem().toString().equals("Choose an unit") || spinnerConcentration.getSelectedItem().toString().equals("Choose an unit"))
					showDialog("ERROR UNITS", "Please enter units");
				else
					calculateResult();
			}
        	
        });

		
		spinnerDose = (Spinner)rootView.findViewById(R.id.Spin_Dose);
        SpinnerAdapter adapterDose = new SpinnerAdapter(rootView.getContext(), R.layout.item_spinner, Arrays.asList(getResources().getStringArray(R.array.Doses_Units)));
        adapterDose.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerDose.setAdapter(adapterDose);
        spinnerDose.setOnItemSelectedListener(new OnItemSelectedListener() {

		     public void onItemSelected(AdapterView<?> parent, View arg1,int arg2, long arg3) {
		    	 userEntryDoseUnits = parent.getSelectedItem().toString(); 	 
		     }

		     public void onNothingSelected(AdapterView<?> arg0) {
		                // TODO Auto-generated method stub
		     }
		     });

        spinnerConcentration = (Spinner)rootView.findViewById(R.id.Spin_Concentration);
        SpinnerAdapter adapterConcentration = new SpinnerAdapter(rootView.getContext(), R.layout.item_spinner, Arrays.asList(getResources().getStringArray(R.array.Concentrations_Units)));
        adapterConcentration.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerConcentration.setAdapter(adapterConcentration);
        spinnerConcentration.setOnItemSelectedListener(new OnItemSelectedListener() {

		     public void onItemSelected(AdapterView<?> parent, View arg1,int arg2, long arg3) {
		    	 userEntryConcentrationsUnits = parent.getSelectedItem().toString(); 	 
		     }

		     public void onNothingSelected(AdapterView<?> arg0) {
		                // TODO Auto-generated method stub
		     }
		     });

		return rootView;
		
	}
	
	public void initializeMap() {
		conversionMap = new HashMap<String,Float>();
		conversionMap.put("mgTomg", 1.0f);
		conversionMap.put("mgTomcg", 1000.0f);
		conversionMap.put("mgTog", 0.001f);
		conversionMap.put("mcgTomg", 0.001f);
		conversionMap.put("mcgTomcg", 1.0f);
		conversionMap.put("mcgTog", 0.000001f);
		conversionMap.put("gTomg", 1000.0f);
		conversionMap.put("gTomcg", 1000000.0f);
		conversionMap.put("gTog", 1.0f);
		conversionMap.put("kgTomg", 1000000.0f);
		conversionMap.put("kgTomcg", 1000000000.0f);
		conversionMap.put("kgTog", 1000.0f);
		conversionMap.put("ozTomg", 28349.5231f);
		conversionMap.put("ozTomcg", 28349523.1f);
		conversionMap.put("ozTog", 28.3495231f);
		conversionMap.put("lbsTomg", 453592.37f);
		conversionMap.put("lbsTomcg", 453592370.0f);
		conversionMap.put("lbsTog", 453.59237f);
	}
	
	public void calculateResult(){
		//Results title
		LinearLayout resultsLayout = new LinearLayout(rootView.getContext());
		resultsLayout.setOrientation(LinearLayout.VERTICAL);
		resultsLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	        
	    TextView titleResults=new TextView(rootView.getContext());
	    titleResults.setText("Results");
	    titleResults.setTextSize(20);
	    titleResults.setTypeface(Typeface.SANS_SERIF);
	    titleResults.setTextColor(getResources().getColor(R.color.white));
	    titleResults.setGravity(Gravity.CENTER);
	    titleResults.setBackgroundResource(R.drawable.calculator_style_title_results);
	    
	    TextView results=new TextView(rootView.getContext());
	    results.setTextSize(15);
	    results.setTypeface(Typeface.SANS_SERIF);
	    results.setBackgroundResource(R.drawable.calculator_style_edittext);
	       
		LinearLayout.LayoutParams paramsTitleResults = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		//paramsTitleResults.topMargin = 40;
	        
		LinearLayout.LayoutParams paramsResults = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 90);
	    
		resultsLayout.addView(titleResults,resultsLayout.getChildCount(),paramsTitleResults);
		
		float weight = Float.parseFloat(weightValue_kgs.getText().toString());
		float dose = Float.parseFloat(doseAmount.getText().toString());
		float drugAmount = dose * weight;
		float concentration = Float.parseFloat(concentrationValue.getText().toString());
		String [] mlUnits =  userEntryConcentrationsUnits.split("/");
		String concentrationUnits;
		
		if (mlUnits.length < 2) {
			String [] tabletUnits = mlUnits[0].split("\\(");
			String [] tabletUnitsFinal = tabletUnits[1].split("\\)");
			concentrationUnits = tabletUnitsFinal[0];
		}
		else
			concentrationUnits = mlUnits[0];
		
		float resultValue = (drugAmount * conversionMap.get(userEntryDoseUnits + "To" + concentrationUnits)) / concentration;
		String resultUnits;
		
		if (mlUnits.length > 1 && mlUnits[1].equals("ml"))
			resultUnits = "ml";
		else
			resultUnits = "tablet";
		
		results.setText(resultValue + " " + resultUnits);
		
		resultsLayout.addView(results,resultsLayout.getChildCount(),paramsResults);
		
	        
		LinearLayout layoutResults=(LinearLayout)rootView.findViewById(R.id.layoutResults);
		layoutResults.addView(resultsLayout,layoutResults.getChildCount());
		
	}
	
	public void showDialog(String dialogTitle, String dialogText) {
		AlertDialog.Builder myalert = new AlertDialog.Builder(getActivity());
		
		TextView title = new TextView(getActivity());
		title.setTypeface(Typeface.SANS_SERIF);
		title.setTextSize(20);
		title.setTextColor(getResources().getColor(R.color.blue));
		title.setPadding(8, 8, 8, 8);
		title.setText(dialogTitle);
		title.setGravity(Gravity.CENTER_VERTICAL);
		
		LinearLayout layout = new LinearLayout(getActivity());
		TextView text = new TextView(getActivity());
		text.setTypeface(Typeface.SANS_SERIF);
		text.setTextSize(20);
		text.setPadding(10, 10, 10, 10);
		text.setText(dialogText);
		layout.addView(text);
		
		myalert.setView(layout);
		myalert.setCustomTitle(title);
		myalert.setCancelable(true);
		myalert.show();
	}
}
