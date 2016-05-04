package com.vikram.autocomplete.v2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AutoCompleteBrute {
	
	private String dictionaryName; 
	
	private List<String> excludeForIndexing = new ArrayList<String>();
	
	private Map<String, Set<AutoCompleteElement>> cache = new HashMap<String, Set<AutoCompleteElement>>();
	
	public AutoCompleteBrute(String dictionaryName, List<String> excludeForIndexing) {
		this.dictionaryName = dictionaryName;
		if(excludeForIndexing!=null){
			this.excludeForIndexing = excludeForIndexing;
		}
	}


	public void add(AutoCompleteElement element){
		
		String[] tokens = element.getLabel().split(" ");
		for(String token:tokens){
			
			if(excludeForIndexing.contains(token)){
				continue;
			}
			
			for(int i=0;i<=token.length();i++){
				
				String prefix = token.substring(0,i).toLowerCase();
				if(!cache.containsKey(prefix)){
					cache.put(prefix, new HashSet<AutoCompleteElement>());
				}
				
				cache.get(prefix).add(element);
			}			
		}		
	}
	
	
	public Collection<AutoCompleteElement> search(String prefix){
		if(cache.containsKey(prefix)){
			return cache.get(prefix);
		}
		
		return new ArrayList<AutoCompleteElement>();
	}
	
	public String toString(){
		return "Autocomplete for dictionary "+dictionaryName;
	}

}
