package com.example.drugsformarinemammals;

public class Drug_Information {
	
	private String name;
	private String description;
	private Integer available;
	private String license_AEMPS;
	private String license_EMA;
	private String license_FDA;
	private Integer priority;
	
	public Drug_Information(String name, String description, Integer available, String license_AEMPS, String license_EMA,
			String license_FDA, Integer priority) {
		this.name = name;
		this.description = description;
		this.available = available;
		this.license_AEMPS = license_AEMPS;
		this.license_EMA = license_EMA;
		this.license_FDA = license_FDA;
		this.priority = priority;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Integer getAvailable() {
		return available;
	}
	
	public String getLicense_AEMPS() {
		return license_AEMPS;
	}
	
	public String getLicense_EMA() {
		return license_EMA;
	}
	
	public String getLicense_FDA() {
		return license_FDA;
	}
	
	public Integer getPriority() {
		return priority;
	}
	
	public void setName(String drugName) {
		name = drugName;
	}
	
	public void setDescription(String drugDescription) {
		description = drugDescription;
	}
	
	public void setAvailable(Integer drugAvailable) {
		available = drugAvailable;
	}
	
	public void setLicense_AEMPS(String AEMPS) {
		license_AEMPS = AEMPS;
	}
	
	public void setLicense_EMA(String EMA) {
		license_EMA = EMA;
	}
	
	public void setLicense_FDA(String FDA) {
		license_FDA = FDA;
	}
	
	public void setPriority(Integer number) {
		priority = number;
	}

}
