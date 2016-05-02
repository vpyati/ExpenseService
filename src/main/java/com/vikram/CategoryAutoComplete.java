package com.vikram;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vikram.autocomplete.v2.AutoCompleteElement;
import com.vikram.category.CategoryAutoCompleteDictionary2;
import com.vikram.category.CategoryTree;
import com.vikram.model.CategoryAutocompleteResult;

@RestController
@RequestMapping("/open/searchCategory")
public class CategoryAutoComplete {
		
	private static final int MAX_RESULTS = 10;

	@Autowired
	private CategoryAutoCompleteDictionary2 dictionary;
	
	@Autowired
	private CategoryTree categoryTree;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<CategoryAutocompleteResult> get(@RequestParam("term") String term) { 		
		
		List<CategoryAutocompleteResult> results = new ArrayList<CategoryAutocompleteResult>();
		
		List<AutoCompleteElement> keywords = dictionary.search(term.toLowerCase());
		for(AutoCompleteElement keyword:keywords){
			results.add(new CategoryAutocompleteResult(keyword.getValue(),keyword.getLabel()));
		}
				
		return results.subList(0, Math.min(MAX_RESULTS, results.size()));
	}
}
