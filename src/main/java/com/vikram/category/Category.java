package com.vikram.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vikram.autocomplete.v2.AutoCompleteElement;

public class Category implements AutoCompleteElement{
	
	private int catId;
	
	private Category parentCategory;
	
	private String catName;
	
	private String label;
	
	private List<Category> children = new ArrayList<Category>();
	
	
	public Category(int categoryId, String categoryName){
		this.catId=categoryId;
		this.catName=categoryName;
	}
	
	public void addChild(Category child){
		children.add(child);
		child.addParent(this);
	}
	
	private void addParent(Category parent){
		this.parentCategory = parent;
	}

	public int getCatId() {
		return catId;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public String getCatName() {
		return catName;
	}

	public List<Category> getChildren() {
		return Collections.unmodifiableList(children);
	}
	
	public boolean isLeaf(){
		return children.isEmpty();
	}

	@Override
	public String getValue() {
		return String.valueOf(getCatId());
	}

	@Override
	public String getLabel() {
		if(label==null){
			label = generateLable();
		}
		
		return label;
	}
	
	private String generateLable() {
		StringBuilder label = new StringBuilder();
		List<String> categoryNames = new ArrayList<String>();
		Category current = this;
		while(current.getCatId()!=0){
			categoryNames.add(current.getCatName());
			current = current.getParentCategory();
		}
		
		if(categoryNames.isEmpty()){
			return label.toString();
		}
		
		label.append(categoryNames.get(categoryNames.size()-1));
		for(int i=categoryNames.size()-2;i>=0;i--){
			label.append(" -> " + categoryNames.get(i));
		}
		
		return label.toString();
	}

}
