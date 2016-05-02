package com.vikram.category;

import java.util.List;

import com.vikram.autocomplete.AutoCompleteTrie;

public class CategoryAutoCompleteDictionary {
	
	private AutoCompleteTrie autoComplete = new AutoCompleteTrie("CategoryDictionary");
	
	private CategoryTree categoryTree;
	
	public void init(){		
		addCategoryToDictionary(categoryTree.getRootCategory());				
	}

	private void addCategoryToDictionary(Category category) {

		if(category.isLeaf()){
			autoComplete.addWord(category.getCatName().toLowerCase());
		}
		
		for(Category child:category.getChildren()){
			addCategoryToDictionary(child);
		}		
	}
	
	public List<String> search(String prefix){
		return autoComplete.search(prefix);
	}

}
