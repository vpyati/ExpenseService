package com.vikram.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Category {
	
	private int catId;
	
	private Category parentCategory;
	
	private String catName;
	
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
}
