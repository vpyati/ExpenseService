package com.vikram.category;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vikram.autocomplete.AutoComplete;

public class CategoryAutoCompleteDictionary {
	
	private AutoComplete autoComplete = new AutoComplete("CategoryDictionary");
	
	@Autowired
	private CategoryTree categoryTree;
	
	@PostConstruct
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
