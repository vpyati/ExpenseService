package com.vikram.category;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vikram.autocomplete.v2.AutoCompleteBrute;
import com.vikram.autocomplete.v2.AutoCompleteElement;

public class CategoryAutoCompleteDictionary2 {
	
	private static List<String> excludedForIndex = new ArrayList<String>();
	static{
		excludedForIndex.add("->");
	}
	
	private AutoCompleteBrute autoComplete = new AutoCompleteBrute("CategoryDictionary",excludedForIndex);
	
	@Autowired
	private CategoryTree categoryTree;
	

	@PostConstruct
	public void init(){		
		addCategoryToDictionary(categoryTree.getRootCategory());				
	}

	public Collection<AutoCompleteElement> search(String prefix){
		return autoComplete.search(prefix);
	}

	private void addCategoryToDictionary(Category category) {
		if(category.isLeaf()){
			autoComplete.add(category);
		}
		
		for(Category child:category.getChildren()){
			addCategoryToDictionary(child);
		}				
	}

	
}
