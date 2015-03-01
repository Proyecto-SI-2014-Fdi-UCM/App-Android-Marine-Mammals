package com.example.drugsformarinemammals;

import java.util.Arrays;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Fragment_Calculator extends Fragment {
	private View rootView;
	private String userEntryDosesUnits;
	private String userEntryConcentrationsUnits;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_calculator,container, false);
		userEntryDosesUnits="";
		userEntryConcentrationsUnits="";
		
		TextView weight_lbs = (TextView)rootView.findViewById(R.id.textview_WeightLbs);
		weight_lbs.setTypeface(Typeface.SANS_SERIF);
		
		EditText hint_weight_lbs=(EditText)rootView.findViewById(R.id.editText_WeightLbs);
		hint_weight_lbs.setTypeface(Typeface.SANS_SERIF);
		
		TextView weight_kgs = (TextView)rootView.findViewById(R.id.textview_WeightKgs);
		weight_kgs.setTypeface(Typeface.SANS_SERIF);
		
		EditText hint_weight_kgs=(EditText)rootView.findViewById(R.id.editText_WeightKgs);
		hint_weight_kgs.setTypeface(Typeface.SANS_SERIF);
		
		TextView dose = (TextView)rootView.findViewById(R.id.textview_Dose);
		dose.setTypeface(Typeface.SANS_SERIF);
		
		EditText hint_dose=(EditText)rootView.findViewById(R.id.editText_Dose);
		hint_dose.setTypeface(Typeface.SANS_SERIF);
		
		TextView concentration = (TextView)rootView.findViewById(R.id.textview_concentration);
		concentration.setTypeface(Typeface.SANS_SERIF);
		
		EditText hint_concentration=(EditText)rootView.findViewById(R.id.editText_concentration);
		hint_concentration.setTypeface(Typeface.SANS_SERIF);
		
        Button calculate=(Button)rootView.findViewById(R.id.buttonCalculate);
        calculate.setTypeface(Typeface.SANS_SERIF);
        calculate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				calculate();
			}
        	
        });

		
		Spinner spinnerDose = (Spinner)rootView.findViewById(R.id.Spin_Dose);
        SpinnerAdapter adapterDose = new SpinnerAdapter(rootView.getContext(), R.layout.item_spinner, Arrays.asList(getResources().getStringArray(R.array.Doses_Units)));
        adapterDose.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerDose.setAdapter(adapterDose);
        spinnerDose.setOnItemSelectedListener(new OnItemSelectedListener() {

		     public void onItemSelected(AdapterView<?> parent, View arg1,int arg2, long arg3) {
		    	 userEntryDosesUnits = parent.getSelectedItem().toString(); 	 
		     }

		     public void onNothingSelected(AdapterView<?> arg0) {
		                // TODO Auto-generated method stub
		     }
		     });

        Spinner spinnerConcentration = (Spinner)rootView.findViewById(R.id.Spin_Concentration);
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
	
	public void calculate(){
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
		paramsTitleResults.topMargin = 20;
	        
		LinearLayout.LayoutParams paramsResults = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 160);
	    
		resultsLayout.addView(titleResults,resultsLayout.getChildCount(),paramsTitleResults);
		resultsLayout.addView(results,resultsLayout.getChildCount(),paramsResults);
		
	        
		LinearLayout layoutResults=(LinearLayout)rootView.findViewById(R.id.layoutResults);
		layoutResults.addView(resultsLayout,layoutResults.getChildCount());
		
	}
}
