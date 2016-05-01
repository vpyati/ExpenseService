package com.vikram;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vikram.category.Category;
import com.vikram.category.CategoryAutoCompleteDictionary;
import com.vikram.category.CategoryTree;
import com.vikram.model.CategoryAutocompleteResult;

@RestController
@RequestMapping("searchCategory")
public class CategoryAutoComplete {
		
	private static final String MISCELLANEOUS_CATEGORY = "8";

	@Autowired
	private CategoryAutoCompleteDictionary dictionary;
	
	@Autowired
	private CategoryTree categoryTree;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<CategoryAutocompleteResult> get(@RequestParam("term") String term) { 		
		
		List<CategoryAutocompleteResult> results = new ArrayList<CategoryAutocompleteResult>();
		
		List<String> keywords = dictionary.search(term.toLowerCase());
		for(String keyword:keywords){
			results.add(new CategoryAutocompleteResult(getCatId(keyword),keyword));
		}
				
		return results;
	}

	private String getCatId(String keyword) {
		Category category = categoryTree.findByCatName(keyword);
		if(category == null) return MISCELLANEOUS_CATEGORY;
		
		return String.valueOf(categoryTree.findByCatName(keyword).getCatId());
	}
	

}
