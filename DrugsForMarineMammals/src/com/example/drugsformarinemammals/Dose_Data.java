package com.example.drugsformarinemammals;

public class Dose_Data {
	
	private String animalName;
	private String categoryName;
	private String amount;
	private String posology;
	private String route;
	private String reference;
	
	public Dose_Data(String animal_name, String category_name, String amount, String posology, String route, String reference) {
		this.animalName = animal_name;
		this.categoryName = category_name;
		this.amount = amount;
		this.posology = posology;
		this.route = route;
		this.reference = reference;
	}
	
	public String getAnimalName() {
		return animalName;
	}
	
	public void setAnimalName(String animal_name) {
		animalName = animal_name;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String category_name) {
		categoryName = category_name;
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
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (!(o instanceof Dose_Data))
			return false;
		Dose_Data dose = (Dose_Data) o;
		if (!animalName.equals(dose.getAnimalName()))		
			return false;
		if (!categoryName.equals(dose.getCategoryName()))		
			return false;
		if (!amount.equals(dose.getAmount()))		
			return false;
		if (!posology.equals(dose.getPosology()))
			return false;
		if (!route.equals(dose.getRoute()))
			return false;
		if (!reference.equals(dose.getReference()))
			return false;
		return true;
	 }

}
