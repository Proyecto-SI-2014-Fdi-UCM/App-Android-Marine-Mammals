package com.example.drugsformarinemammals;

public class Dose_Data {
	
	private String amount;
	private String posology;
	private String route;
	private String reference;
	
	public Dose_Data(String amount, String posology, String route, String reference) {
		this.amount = amount;
		this.posology = posology;
		this.route = route;
		this.reference = reference;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String dose_amount) {
		amount = dose_amount;
	}
	
	public String getPosology() {
		return posology;
	}
	
	public void setPosology(String dose_posology) {
		posology = dose_posology;
	}
	
	public String getRoute() {
		return route;
	}
	
	public void setRoute(String dose_route) {
		route = dose_route;
	}
	
	public String getReference() {
		return reference;
	}
	
	public void setReference(String dose_reference) {
		reference = dose_reference;
	}

}
