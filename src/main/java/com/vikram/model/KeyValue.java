package com.vikram.model;

import java.util.ArrayList;
import java.util.List;

public class KeyValue {
	
	private List<KeyValuePair> keyValuePairs = new ArrayList<KeyValue.KeyValuePair>();
	
	public List<KeyValuePair> getKeyValuePairs() {
		return keyValuePairs;
	}


	public void addKeyValuePairs(String key, String value){
		keyValuePairs.add(new KeyValuePair(key,value));
	}
		
	
	class KeyValuePair{
		String key;
		String value;
			
		KeyValuePair(String key,String value){
			this.key = key;
			this.value = value;
		}

		public String getName() {
			return key;
		}

		public String getValue() {
			return value;
		}
	}
}
