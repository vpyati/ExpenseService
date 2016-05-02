package com.vikram.model;


public class CategoryAutocompleteResult {
	
	private String value;
	
	private String label;
	
	private String id;

	public CategoryAutocompleteResult(String value, String label, String id) {
		this.value = value;
		this.label = label;
		this.id = id;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
