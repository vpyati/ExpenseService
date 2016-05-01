package com.vikram;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vikram.category.CategoryAutoCompleteDictionary;
import com.vikram.category.CategoryTree;
import com.vikram.model.CategoryAutocompleteResult;

@RestController
@RequestMapping("/open/searchCategory")
public class CategoryAutoComplete {
		
	@Autowired
	private CategoryAutoCompleteDictionary dictionary;
	
	@Autowired
	private CategoryTree categoryTree;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<CategoryAutocompleteResult> get(@RequestParam("term") String term) { 		
		
		List<CategoryAutocompleteResult> results = new ArrayList<CategoryAutocompleteResult>();
		
		List<String> keywords = dictionary.search(term.toLowerCase());
		for(String keyword:keywords){
			results.add(new CategoryAutocompleteResult(keyword,keyword));
		}
				
		return results;
	}
}
