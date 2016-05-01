package com.vikram.model;


public class CategoryAutocompleteResult {
	
	private String value;
	
	private String label;

	public CategoryAutocompleteResult(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String id) {
		this.value = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	
	
	

}
