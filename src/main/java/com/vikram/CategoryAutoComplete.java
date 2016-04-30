package com.vikram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vikram.category.CategoryAutoCompleteDictionary;
import com.vikram.model.CategoryAutocompleteResult;

@RestController
@RequestMapping("searchCategory")
public class CategoryAutoComplete {
		
	@Autowired
	private CategoryAutoCompleteDictionary dictionary;
	
	@RequestMapping(method = RequestMethod.GET)
	public CategoryAutocompleteResult get(@RequestParam("q") String q) { 		
		
		CategoryAutocompleteResult result = new CategoryAutocompleteResult();
		result.setResults(dictionary.search(q.toLowerCase()));
		
		return result;
	}
	

}
